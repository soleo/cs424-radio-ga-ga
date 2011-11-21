package dataprocessing.processingscripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import data.Country;

public class CountryCodeMapSerializer {
	
	public static void main(String args[]) throws IOException, ClassNotFoundException
	{
		HashMap<String,Country> countryMap=new HashMap<String,Country>();
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream("/home/vivek/projects/workspace/visproj4/project4/DataStore/countries.ser"));
		
		HashMap<String,Country> countryCodeMap=new HashMap<String,Country>();
		countryMap=(HashMap<String,Country>)ois.readObject();
		
		Set<String> countryKeys=countryMap.keySet();
		Iterator<String> countryKeysIterator=countryKeys.iterator();
		
		while(countryKeysIterator.hasNext())
		{
			String country=countryKeysIterator.next();
			Country currentCountry=countryMap.get(country);
			countryCodeMap.put(currentCountry.getIsoCode(), currentCountry);
			
		}
		ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File("/home/vivek/projects/workspace/visproj4/project4/DataStore/countriesCodeMap.ser")));
		oos.writeObject(countryCodeMap);
		oos.close();
		
		
	}

}
