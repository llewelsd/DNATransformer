Hello and welcome to DNA Transformers

To use, create a docker image
docker build --tag <desired name> .

and run with

docker run -ti -p 8888:8888 <image you chose> bash

This will initiate the jupyter notebook server (using startup.sh)

/Notebooks : contains relevant jupyter notebooks

Each notebook does something different

	- Builder : creates the data to train on
	- Trainer : creates and trains a BART model on that data
	- Tester : tests the created model

There are some already prebuilt data-sets and models to work with

/DataSets : contains data sets

	- dna_small.txt
		100,000 sequences of length 32
		Use for testing the Builder
	- dna_small_sim_output.txt
		simulation output from dna_small.txt
	- dna_big_output.txt
		simulation output for 1,000,000 sequences of length 198
		Use for testing the DNA_BART_198 model
		or to test the trainer (if you dare)
		
/Models : contains some models
	
	DNA_BART_32 : pre_trained BART model for length 32 sequences
	
	DNA_BART_198 : pre_trained BART model for length 198 sequences

/src : contains source files for programs used by builder
	
	- nupack3.2.2
		source files for Nupack http://nupack.org/
		is compiled by Dockerfile
	- NupackSpark
		java project
		runs nupack in parallel to quickly simulate multiple sequences
	- DNAGenerator
		java project
		creates random DNA sequences for NupackSpark
		see builder on usage