package dataprocessing.processingscripts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import data.Artist;
import data.ArtistDetails;

public class ArtistDataLoader {
	
	
	public static void main(String args[]) throws IOException, ClassNotFoundException
	{
		HashMap<String,Artist> artistMap=new HashMap<String,Artist>();
		HashMap<String,ArtistDetails> artistDetailsMap=new HashMap<String,ArtistDetails>();
		HashMap<String,String> artistMb=new HashMap<String,String>();
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream(new File("/home/vivek/projects/workspace/visproj4/project4/DataStore/artistMap.ser")));
		artistMap=(HashMap<String,Artist>)ois.readObject();
		
		BufferedReader inputReader=new BufferedReader(new FileReader(new File("/home/vivek/projects/workspace/Project4cs424DataProcessing/outputs/musicbrainz_new.tsv")));
		
		while(inputReader.ready())
		{
			String inputLine=inputReader.readLine();
			String artistId=inputLine.split("\t")[1].trim();
			artistMb.put(artistId, inputLine);
		}
		
		Set<String> keys=artistMap.keySet();
		Iterator<String> keysIterator=keys.iterator();
		
		while(keysIterator.hasNext())
		{
			String key=keysIterator.next();
			Artist artist=artistMap.get(key);
			ArtistDetails artistDetails=new ArtistDetails();
			artistDetails.setArtistId(artist.getArtistId());
			artistDetails.setArtistName(artist.getArtistName());
			artistDetails.setTotalListeners(artist.getTotalListeners());
			artistDetails.setFemaleListeners(artist.getFemaleListeners());
			artistDetails.setMaleListeners(artist.getMaleListeners());
			artistDetails.setUnknownListeners(artist.getUnknownListeners());
			
			if(artistMb.containsKey(key))
			{
				String line=artistMb.get(key);
				String birthDate=line.split("\t")[2].trim();
				String country=line.split("\t")[5].trim();
				String type=line.split("\t")[4].trim();
				String gender=line.split("\t")[6].trim();
				System.out.println(artist.getArtistName());
				artistDetails.setBirthDate(birthDate);
				artistDetails.setCountry(country);
				artistDetails.setType(type);
				artistDetails.setGender(gender);
			}
			else
			{
				artistDetails.setBirthDate("");
				artistDetails.setCountry("");
				artistDetails.setType("");
				artistDetails.setGender("");
			}
			
			artistDetailsMap.put(key, artistDetails);
			
			
		}
		
		ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("/home/vivek/projects/workspace/visproj4/project4/DataStore/artistDetails.ser"));
		oos.writeObject(artistDetailsMap);
		oos.close();
			
	}

}
