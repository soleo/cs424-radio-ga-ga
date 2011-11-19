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
import dataprocessing.data.Track;

public class TrackFileGenerator {
	
	public static void main(String args[])throws IOException
	{
			
//		HashMap<String,MiniArtist> uniqueArtists=new HashMap<String,MiniArtist>();
//		String inputFile="/home/vivek/projects/workspace/Project4cs424DataProcessing/lastfm-dataset-360K/usersha1-artmbid-artname-plays.tsv";
//		BufferedReader inputReader=new BufferedReader(new FileReader(new File(inputFile)));
//		BufferedWriter artistOutputWriter=new BufferedWriter(new FileWriter(new File("artist.tsv")));
//		BufferedWriter listenOutputWriter=new BufferedWriter(new FileWriter(new File("listen.tsv")));
//		while(inputReader.ready())
//		{
//			String inputLine=inputReader.readLine();
//			String inputLineParts[]=inputLine.split("\t");
//			String uniqueId=inputLineParts[1].trim()+"."+inputLineParts[2].toLowerCase().trim();
//			
//			String userId=inputLineParts[0];
//			String airPlay=inputLineParts[3];
//			String artistId=inputLineParts[1].trim();
//			
//			String artistName=inputLineParts[2];
//			if(!artistId.equals(""))
//			{
//				//artistId=artistName.toLowerCase().replaceAll(" ", "_");
//				MiniArtist artist=new MiniArtist();
//				artist.setArtistId(artistId);
//				artist.setArtistName(artistName);
//				artist.setAirPlay(0);
//				if(uniqueArtists.containsKey(artistId))
//				{
//					;
//				}
//				else
//				{
//					uniqueArtists.put(artistId, artist);	
//				}
//	//		    listenOutputWriter.write(userId+"\t"+artistId+"\t"+airPlay+"\n");
//
//			}
//						
//			
//	
//			
//		}
		String inputFile="/home/vivek/projects/workspace/Project4cs424DataProcessing/lastfm-dataset-1K/userid-timestamp-artid-artname-traid-traname.tsv";
		
		BufferedReader inputReader=new BufferedReader(new FileReader(new File(inputFile)));
		HashMap<String,Track> uniqueTracks=new HashMap<String,Track>();
		BufferedWriter outputWriter=new BufferedWriter(new FileWriter(new File("listeningtracks")));
		while(inputReader.ready())
		{
			String inputLine=inputReader.readLine();
			String inputLineParts[]=inputLine.split("\t");
			String userId=inputLineParts[0];
			String timeStamp=inputLineParts[1];
			String artistId=inputLineParts[2];
			String artistName=inputLineParts[3];
			String trackId=inputLineParts[4];
			String trackName=inputLineParts[5];
			
			if(artistId.equals(""))
			{
				if(!artistName.trim().equals(""))
				{
					artistId=artistName.trim().toLowerCase().replaceAll(" ", "_");	
				}
				else
				{
					continue;
				}				
			}			
			if(trackId.equals(""))
			{
				if(!trackName.trim().equals("") && trackName.trim().length()<=2)
				{
					trackId=trackName.trim().toLowerCase().replaceAll(" ", "_");	
				}
				else
				{
					continue;
				}				
			}
			Track track=new Track();
			track.setTrackId(trackId.trim());
			track.setTrackName(trackName);			
			if(!uniqueTracks.containsKey(trackId.trim()))
			{
				uniqueTracks.put(trackId.trim(), track);
			}			
			outputWriter.write(userId+"\t"+artistId+"\t"+trackId+"\t"+timeStamp+"\n");			
		}
		outputWriter.close();		
		outputWriter=new BufferedWriter(new FileWriter(new File("tracklist")));
		Set<String> keys=uniqueTracks.keySet();
		Iterator<String> keyIterator=keys.iterator();
		while(keyIterator.hasNext())
		{
			String key=keyIterator.next();
			System.out.println(key);
			Track track=uniqueTracks.get(key);
			outputWriter.write(track.getTrackId().trim()+"\t"+track.getTrackName()+"\n");
		}		
		outputWriter.close();
	}

}
