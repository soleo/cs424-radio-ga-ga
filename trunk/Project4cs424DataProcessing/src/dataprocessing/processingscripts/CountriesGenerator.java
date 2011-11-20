package dataprocessing.processingscripts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class CountriesGenerator {
	
	public static void main(String args[])throws IOException
	{
		ArrayList<String> uniqueCountries=new ArrayList<String>();
		HashMap<String,String> actualCountryMap=new HashMap<String, String>();
		
		
		String inputFile="/media/New Volume/Visproj4data/lastfm-dataset-360K/usersha1-profile.tsv";
		
		BufferedReader inputReader=new BufferedReader(new FileReader(new File(inputFile)));
		while(inputReader.ready())
		{
			String inputLine=inputReader.readLine();
			String inputLineParts[]=inputLine.split("\t");
			
			if(inputLineParts.length==5)
			{
				String country=inputLineParts[3].trim().toLowerCase();
				String actualCountry=inputLineParts[3];
				if(!uniqueCountries.contains(country)&& !country.equals(""))
				{
					uniqueCountries.add(country);
					actualCountryMap.put(country, actualCountry);
				}	
			}
			
		}
		inputReader.close();
		
		inputFile="/media/New Volume/Visproj4data/lastfm-dataset-1K/userid-profile.tsv";
		inputReader=new BufferedReader(new FileReader(new File(inputFile)));
		
		while(inputReader.ready())
		{
			String inputLine=inputReader.readLine();
			String inputLineParts[]=inputLine.split("\t");
			if(inputLineParts.length==5)
			{
				String country=inputLineParts[3].trim().toLowerCase();
				String actualCountry=inputLineParts[3];
				if(!uniqueCountries.contains(country) && !country.equals(""))
				{
					uniqueCountries.add(country);
					actualCountryMap.put(country, actualCountry);
				}	
			}
			
		}
		
		BufferedWriter outputWriter=new BufferedWriter(new FileWriter(new File("outputs/countrieslist")));
		
		for(int i=0;i<uniqueCountries.size();i++)
		{
			String country=uniqueCountries.get(i);
			outputWriter.write(country+"\n");
		}
		
		outputWriter.close();
		
		ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File("countryMap.ser")));
		oos.writeObject(actualCountryMap);
		oos.close();
	}

}
