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
//		BufferedWriter artistOutputWriter=new BufferedWriter(new FileWriter(new File("artist.tsv")));
		BufferedWriter listenOutputWriter=new BufferedWriter(new FileWriter(new File("listen.tsv")));
		while(inputReader.ready())
		{
			String inputLine=inputReader.readLine();
			String inputLineParts[]=inputLine.split("\t");
			String uniqueId=inputLineParts[1].trim()+"."+inputLineParts[2].toLowerCase().trim();
			
			String userId=inputLineParts[0];
			String airPlay=inputLineParts[3];
			String artistId=inputLineParts[1].trim();
			
			String artistName=inputLineParts[2];
			if(!artistId.equals(""))
			{
				//artistId=artistName.toLowerCase().replaceAll(" ", "_");
				MiniArtist artist=new MiniArtist();
				artist.setArtistId(artistId);
				artist.setArtistName(artistName);
				artist.setAirPlay(0);
				if(uniqueArtists.containsKey(artistId.trim()))
				{
					;
				}
				else
				{
					uniqueArtists.put(artistId.trim(), artist);	
				}
			    

			}
			else
			{
				if(!artistName.trim().equals(""))
				{
					artistId=artistName.trim().toLowerCase().replaceAll(" ", "_");
					MiniArtist artist=new MiniArtist();
					artist.setArtistId(artistId);
					artist.setArtistName(artistName);
					artist.setAirPlay(0);
				}
			}
			
			listenOutputWriter.write(userId+"\t"+artistId+"\t"+airPlay+"\n");
						
			
	
			
		}

		listenOutputWriter.close();
		
		inputReader=new BufferedReader(new FileReader(new File("/home/vivek/projects/workspace/Project4cs424DataProcessing/lastfm-dataset-1K/userid-timestamp-artid-artname-traid-traname.tsv")));
		while(inputReader.ready())
		{
			String inputLine=inputReader.readLine();
			String inputLineParts[]=inputLine.split("\t");
			String userId=inputLineParts[0];
			String timeStamp=inputLineParts[1];
			String artistId=inputLineParts[2];
			String artistName=inputLineParts[3];
			if(!artistId.trim().equals(""))
			{
				MiniArtist artist=new MiniArtist();
				artist.setArtistId(artistId);
				artist.setArtistName(artistName);
				if(!uniqueArtists.containsKey(artistId.trim()))
				{
					uniqueArtists.put(artistId.trim(), artist);	
				}
				
			}
			else
			{
				if(!artistName.trim().equals(""))
				{
					artistId=artistName.trim().toLowerCase().replaceAll(" ", "_");
					MiniArtist artist=new MiniArtist();
					artist.setArtistId(artistId);
					artist.setArtistName(artistName);
					if(!uniqueArtists.containsKey(artistId))
					{
						uniqueArtists.put(artistId, artist);
					}
				}
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
