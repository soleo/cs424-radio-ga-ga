package dataprocessing.processingscripts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import dataprocessing.data.MiniArtist;

public class ArtistsFileGenerator {
	
	public static void main(String args[])throws IOException
	{
		HashMap<String,MiniArtist> uniqueArtists=new HashMap<String,MiniArtist>();
		String inputFile="/home/vivek/projects/workspace/Project4cs424DataProcessing/lastfm-dataset-360K/usersha1-artmbid-artname-plays.tsv";
		BufferedReader inputReader=new BufferedReader(new FileReader(new File(inputFile)));
		
		while(inputReader.ready())
		{
			String inputLine=inputReader.readLine();
			String inputLineParts[]=inputLine.split("\t");
			String uniqueId=inputLineParts[1].trim()+"."+inputLineParts[2].toLowerCase().trim();
			String artistId=inputLineParts[1];
			String artistName=inputLineParts[2];
			MiniArtist artist=new MiniArtist();
			artist.setArtistId(artistId);
			artist.setArtistName(artistName);
			artist.setAirPlay(0);
			if(uniqueArtists.containsKey(uniqueId))
			{
				;
			}
			else
			{
				uniqueArtists.put(uniqueId, artist);	
			}
			
		}
		
		BufferedWriter outputWriter=new BufferedWriter(new FileWriter(new File("outputs/artistlist")));
		Set<String> keys=uniqueArtists.keySet();
		Iterator<String> keysIterator=keys.iterator();
		while(keysIterator.hasNext())
		{
			String key=keysIterator.next();
			MiniArtist artist=uniqueArtists.get(key);
			outputWriter.write(artist.getArtistId()+"\t"+artist.getArtistName()+"\n");
		}
		outputWriter.close();
		
	}

}
