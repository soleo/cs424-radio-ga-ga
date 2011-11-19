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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import data.Country;




public class CountriesSerializer {
	
	
	
	public static void main(String args[])throws IOException
	{
		BufferedReader inputReader=new BufferedReader(new FileReader(new File("outputs/countries")));
		//List<String> countriesList=new ArrayList<String>();
		HashMap<String, Country> countryList = new HashMap<String, Country>();
		
		HashMap<String, String> iso = new HashMap<String, String>();
		BufferedReader cnIsoReader=new BufferedReader(new FileReader(new File("outputs/country.tsv")));
		while(cnIsoReader.ready())
		{
			String inputLine=cnIsoReader.readLine().trim();
			String[] data = inputLine.split("\t");
			if(data.length != 2){
				for(int i=0; i<data.length; i++) 
					System.out.print(data[i]+" ");
				System.out.println();
				//continue;
			}
			else
				iso.put(data[1].trim().toLowerCase(), data[0].trim().toLowerCase());
		}
		
		//for (String key : iso.keySet())
		//	System.out.println(key + " " + iso.get(key));
		
		while(inputReader.ready())
		{
			String inputLine=inputReader.readLine().trim();
			String[] data = inputLine.split("\t");
			Country c = new Country();
			
			c.name = new String(data[0].trim());
			
			if(iso.containsKey(c.name))
			{
				c.isoCode = new String((String)iso.get(data[0].trim()));
				c.timeZoneOffset = new String(data[1].trim());
			
				//countriesList.add(inputLine);
				countryList.put(c.name, c);
			}
			else
			{
				System.out.println(data[0].trim() + "\t" + data[1].trim());
			}
			
		}
		
		/*Collections.sort(countriesList);
		for(int i=0;i<countriesList.size();i++)
		{
			System.out.println(countriesList.get(i));
		}*/
		//Collections.sort(countryList);
		
		ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File("/home/vivek/projects/workspace/visproj4/project4/DataStore/countries.ser")));
		//ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File("countries.ser")));
		oos.writeObject(countryList);
		oos.close();
	}

}
