package dataprocessing.processingscripts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class SimilarArtistSerializer {
	
	public static void main(String args[]) throws Exception {

		String inputFile="../LastfmAPI/openmatch3";
		String outputFile="../project4/DataStore/similarArtist.ser";
		
		HashMap<String, String> similarArtist = new HashMap<String, String>();
		
		BufferedReader inputReader=new BufferedReader(new FileReader(new File(inputFile)));
		while(inputReader.ready())
		{
			String inputLine=inputReader.readLine().trim();
			String[] data = inputLine.split("\t");
			if(data.length == 2)
			{
				similarArtist.put(data[0].trim(), (data[1].length()>0) ? data[1].substring(1).trim() : "");
			}
			else if (data.length == 1)
			{
				similarArtist.put(data[0].trim(), "");
			}
		}
		inputReader.close();
		
		ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File(outputFile)));
		oos.writeObject(similarArtist);
		oos.close();
	}
	
}
