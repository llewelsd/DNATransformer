FROM ubuntu:18.04

RUN apt-get update --fix-missing

# install dependencies from apt
RUN apt-get install -y openjdk-8-jdk
ENV PATH $PATH:$JAVA_HOME/bin

RUN apt-get install -y git

RUN apt-get -y install curl

RUN apt-get install -y p7zip-full

RUN apt install -y maven

RUN apt-get install -y build-essential cmake python3.6 python3-pip python3-dev
RUN pip3 --q install pip --upgrade

RUN pip3 install jupyter
RUN pip3 install torch===1.7.1+cu110 torchvision===0.8.2+cu110 torchaudio===0.7.2 -f https://download.pytorch.org/whl/torch_stable.html
RUN pip3 install --user numpy scipy matplotlib pandas sympy nose
RUN pip3 install transformers

RUN git clone https://github.com/llewelsd/DNATransformer

# install nupack

WORKDIR /DNATransformer/src/nupack3.2.2
RUN mkdir build
WORKDIR ./build
RUN cmake ../
RUN make install

WORKDIR /DNATransformer/src

# install spark

ARG SPARK_ARCHIVE=https://archive.apache.org/dist/spark/spark-2.4.4/spark-2.4.4-bin-hadoop2.7.tgz
RUN curl -s $SPARK_ARCHIVE | tar -xz -C /usr/local/

ENV SPARK_HOME /usr/local/spark-2.4.4-bin-hadoop2.7
ENV PATH $PATH:$SPARK_HOME/bin

WORKDIR /DNATransformer/src/NupackSpark

RUN ln -s /usr/local/spark-2.4.4-bin-hadoop2.7/jars spark-jars

# compile NupackSpark

RUN javac -cp ".:./spark-jars/spark-core_2.11-2.4.4.jar:./spark-jars/scala-library-2.11.12.jar:./spark-jars/commons-cli-1.2.jar:./spark-jars/log4j-1.2.17.jar" ./src/*.java -d ./bin 

WORKDIR ./bin
RUN jar cvf nps.jar *.class
RUN cp nps.jar /DNATransformer/Notebooks


# extract the large dataset
WORKDIR /DNATransformer/DataSets
RUN 7z e dna_big_sim_output.7z

WORKDIR /DNATransformer

RUN chmod +x startup.sh

ENTRYPOINT ["./startup.sh"]