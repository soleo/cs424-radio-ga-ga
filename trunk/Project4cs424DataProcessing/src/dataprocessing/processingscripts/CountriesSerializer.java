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
import java.util.List;

public class CountriesSerializer {
	
	
	public static void main(String args[])throws IOException
	{
		BufferedReader inputReader=new BufferedReader(new FileReader(new File("outputs/countries")));
		List<String> countriesList=new ArrayList<String>();
		while(inputReader.ready())
		{
			String inputLine=inputReader.readLine().trim();
			countriesList.add(inputLine);
			
			
		}
		
		Collections.sort(countriesList);
		for(int i=0;i<countriesList.size();i++)
		{
			System.out.println(countriesList.get(i));
		}
		
		ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File("/home/vivek/projects/workspace/visproj4/project4/DataStore/countries.ser")));
		oos.writeObject(countriesList);
		oos.close();
	}

}
