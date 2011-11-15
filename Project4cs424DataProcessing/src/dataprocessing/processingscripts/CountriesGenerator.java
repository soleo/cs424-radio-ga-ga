package dataprocessing.processingscripts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CountriesGenerator {
	
	public static void main(String args[])throws IOException
	{
		ArrayList<String> uniqueCountries=new ArrayList<String>();
		
		String inputFile="/home/vivek/projects/workspace/Project4cs424DataProcessing/lastfm-dataset-360K/usersha1-profile.tsv";
		
		BufferedReader inputReader=new BufferedReader(new FileReader(new File(inputFile)));
		while(inputReader.ready())
		{
			String inputLine=inputReader.readLine();
			String inputLineParts[]=inputLine.split("\t");
			
			if(inputLineParts.length==5)
			{
				String country=inputLineParts[3].trim().toLowerCase();
				if(!uniqueCountries.contains(country)&& !country.equals(""))
				{
					uniqueCountries.add(country);
				}	
			}
			
		}
		inputReader.close();
		
		inputFile="/home/vivek/projects/workspace/Project4cs424DataProcessing/lastfm-dataset-1K/userid-profile.tsv";
		inputReader=new BufferedReader(new FileReader(new File(inputFile)));
		
		while(inputReader.ready())
		{
			String inputLine=inputReader.readLine();
			String inputLineParts[]=inputLine.split("\t");
			if(inputLineParts.length==5)
			{
				String country=inputLineParts[3].trim().toLowerCase();
				if(!uniqueCountries.contains(country) && !country.equals(""))
				{
					uniqueCountries.add(country);
				}	
			}
			
		}
		
		BufferedWriter outputWriter=new BufferedWriter(new FileWriter(new File("outputs/countries")));
		
		for(int i=0;i<uniqueCountries.size();i++)
		{
			String country=uniqueCountries.get(i);
			outputWriter.write(country+"\n");
		}
		
		outputWriter.close();
	}

}
