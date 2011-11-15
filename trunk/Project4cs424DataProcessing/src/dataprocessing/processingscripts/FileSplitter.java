package dataprocessing.processingscripts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileSplitter {
	
	public static void main(String args[])throws IOException
	{
		String inputFile="/home/vivek/projects/workspace/Project4cs424DataProcessing/lastfm-dataset-360K/usersha1-artmbid-artname-plays.tsv";
		
		BufferedReader inputReader=new BufferedReader(new FileReader(new File(inputFile)));
		long count=0;
		int part=0;
		while(inputReader.ready())
		{
			String inputLine=inputReader.readLine();
			File file=new File(inputFile);
			
			count++;
			if(count>=1000000)
			{
				count=0;
				part++;
			}
			BufferedWriter outputWriter=new BufferedWriter(new FileWriter(new File("splitartist/"+file.getName()+"part"+part),true));
			outputWriter.write(inputLine+"\n");
			outputWriter.close();
		}
		
	}

}
