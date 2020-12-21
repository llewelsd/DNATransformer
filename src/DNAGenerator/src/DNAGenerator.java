import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;



public class DNAGenerator {
	final static String[] DNA_BASES = {"A","C","T","G"};
	final static String[] RNA_BASES = {"A","C","U","G"};
	final static int ZERO = 0;
	final static int BASECOUNT = 4;
	
	
	public static void main(String[] args) throws ParseException, IOException {
		
		// length
		// number
		// seed
		// RNA or DNA
		
		Options options = new Options();
		options.addOption("dna",false,"DNA");
    	options.addOption("s","seed",true,"Seed for RNG");
    	options.addOption("o",true,"OutputFile");
    	options.addOption("h","help",false,"List options");
    	
    	Random rand = new Random();
    	
    	CommandLineParser parser = new DefaultParser();
    	HelpFormatter formatter = new HelpFormatter();
    	CommandLine cmd = parser.parse(options, args);
    	if(args.length < 2 || cmd.hasOption("h")) {
			formatter.printHelp("Nupack DNA Generator",options);
    		System.exit(1);
		}
    	
		if(cmd.hasOption("s")) {
			rand.setSeed(Long.parseLong(cmd.getOptionValue("s")));
		}
		
		String[] bases = DNA_BASES;
		String outputfile;
		
		long size = Long.parseLong(args[args.length-2]);
    	long count = Long.parseLong(args[args.length-1]);
		
		if(cmd.hasOption("rna")) {
			bases = RNA_BASES;
		}
		else if(cmd.hasOption("dna")) {
			bases = DNA_BASES;
		}
		
		
		if(cmd.hasOption("o")) {
    		outputfile = cmd.getOptionValue("o");
    	}
		else {
			outputfile = "GENERATOR_"+size+"_"+count + ".txt";
		}
		
		// Now build the random sequences
		ArrayList<String> sequences = new ArrayList<String>();
		StringBuilder sb;
		for(long i = 0; i < count; i++) {
				sb = new StringBuilder();
				int n;
				for(long j = 0; j < size; j++) {
					n = rand.nextInt(BASECOUNT);
					sb.append(bases[n]);
				}
				sequences.add(sb.toString());
			
		}
		
		// print to file
		PrintWriter pw = new PrintWriter(new FileWriter(outputfile));
		System.out.println("Printing ...");
		String toWrite = "";
		for (String s : sequences) {
			toWrite =  s + "\n";
			pw.write(toWrite);
		}
		pw.close();
	}
}
