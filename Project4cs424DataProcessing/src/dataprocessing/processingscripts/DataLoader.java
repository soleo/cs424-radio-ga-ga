package dataprocessing.processingscripts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;

import dataprocessing.data.Artist;
import dataprocessing.data.MiniArtist;
import dataprocessing.data.User;

public class DataLoader {
	HashMap<String, User> uniqueUsers = new HashMap<String, User>();

	public static void main(String args[]) throws Exception {

		DataLoader d = new DataLoader();
		d.loadUserData();
		d.loadArtistData();

	}

	void loadUserData() throws Exception, IOException {
		String inputFile = "/home/vivek/projects/workspace/Project4cs424DataProcessing/lastfm-dataset-360K/usersha1-profile.tsv";
		BufferedReader inputReader = new BufferedReader(new FileReader(
				new File(inputFile)));

		while (inputReader.ready()) {
			String inputLine = inputReader.readLine();
			String inputLineParts[] = inputLine.split("\t");
			String userId = inputLineParts[0];
			String genderStr = inputLineParts[1];
			char gender;
			if (genderStr.trim().equalsIgnoreCase("m")) {
				gender = 'm';
			} else if (genderStr.trim().equalsIgnoreCase("f")) {
				gender = 'f';
			} else {
				gender = ' ';
			}
			int age;
			if (inputLineParts[2].equals("")) {
				age = 0;
			} else {
				age = Integer.parseInt(inputLineParts[2]);
			}

			String country = inputLineParts[3];
			String memberSinceStr = inputLineParts[4];
			String[] memberSinceStrParts = memberSinceStr.split(",");
			int year = Integer.parseInt(memberSinceStrParts[1].trim());
			String[] dateParts = memberSinceStrParts[0].split(" ");
			int day = Integer.parseInt(dateParts[1]);
			int month = 0;
			if (dateParts[0].equalsIgnoreCase("jan")) {
				month = 1;
			} else if (dateParts[0].equalsIgnoreCase("feb")) {
				month = 2;
			} else if (dateParts[0].equalsIgnoreCase("mar")) {
				month = 3;
			} else if (dateParts[0].equalsIgnoreCase("apr")) {
				month = 4;
			} else if (dateParts[0].equalsIgnoreCase("may")) {
				month = 5;
			} else if (dateParts[0].equalsIgnoreCase("jun")) {
				month = 6;
			} else if (dateParts[0].equalsIgnoreCase("jul")) {
				month = 7;
			} else if (dateParts[0].equalsIgnoreCase("aug")) {
				month = 8;
			} else if (dateParts[0].equalsIgnoreCase("sep")) {
				month = 9;
			} else if (dateParts[0].equalsIgnoreCase("oct")) {
				month = 10;
			} else if (dateParts[0].equalsIgnoreCase("nov")) {
				month = 11;
			} else if (dateParts[0].equalsIgnoreCase("dec")) {
				month = 12;
			}

			Date memberSince = new Date(year, month, day);
			System.out.println(memberSinceStr);
			User user = new User();
			user.setAge(age);
			user.setCountry(country);
			user.setGender(gender);
			user.setMemberSince(memberSince);
			user.setUserId(userId);

			if (!uniqueUsers.containsKey(userId)) {
				uniqueUsers.put(userId, user);
			}

		}
	}

	void loadArtistData() throws Exception {

		String inputFile = "/home/vivek/projects/workspace/Project4cs424DataProcessing/lastfm-dataset-360K/usersha1-artmbid-artname-plays.tsv";

		BufferedReader inputReader=new BufferedReader(new FileReader(new File(inputFile)));
		
		while(inputReader.ready())
		{
			String inputLine=inputReader.readLine();
			String inputLineParts[]=inputLine.split("\t");
			String userId=inputLineParts[0];
			String artistId=inputLineParts[1];
			
			
			String artistName=inputLineParts[2];
			int airPlay=Integer.parseInt(inputLineParts[3]);
			if(artistId.trim().equals(""))
			{
				artistId=artistName.trim().replaceAll(" ","_");
			}
			Artist artist=new Artist();	
			artist.setArtistId(artistId);
			artist.setArtistName(artistName);
			artist.setArtistPlays(userId, airPlay);
			MiniArtist miniArtist=new MiniArtist();
			miniArtist.setAirPlay(airPlay);
			miniArtist.setArtistId(artistId);
			miniArtist.setArtistName(artistName);
			if(uniqueUsers.containsKey(userId))
			{
				User user=uniqueUsers.get(userId);
				user.addArtist(miniArtist);
				
			}
			
			
		}
		
		
	}

}
