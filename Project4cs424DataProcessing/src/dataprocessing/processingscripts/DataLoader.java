package dataprocessing.processingscripts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import dataprocessing.data.User;

public class DataLoader {
	HashMap<String, User> uniqueUsers = new HashMap<String, User>();

	public static void main(String args[]) throws Exception {

		DataLoader d = new DataLoader();
		System.out.println("loading user data");
//		d.loadUserData();
		System.out.println("loading artist data");
		d.loadArtistData();
		System.out.println("loading track data");
	//	d.loadTrackData();
		System.out.println("done");
	}

	void loadUserData() throws Exception, IOException 
	{
		String inputFile = "/home/vivek/projects/workspace/Project4cs424DataProcessing/lastfm-dataset-360K/usersha1-profile.tsv";
		BufferedReader inputReader = new BufferedReader(new FileReader(
				new File(inputFile)));
		
		Connection conn =null;
		try
		{
			String userName="root";
			String password="tigger";
			String url="jdbc:mysql://localhost/gaga?user="+userName+"&password="+password;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn=DriverManager.getConnection(url);
			Statement dropStatement=conn.createStatement();
			dropStatement.execute("DROP table IF EXISTS user_schema");
			dropStatement.close();
			Statement createStatement=conn.createStatement();
			createStatement.execute("CREATE table user_schema( user_id varchar(512) NOT NULL PRIMARY KEY, gender varchar(1), age INTEGER, country varchar(255), member_since date)");
			
			while (inputReader.ready()) 
			{
				Statement statement=conn.createStatement();
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
				if (inputLineParts[2].trim().equals("")) {
					age = 0;
				} else {
					age = Integer.parseInt(inputLineParts[2]);
				}

				String country = inputLineParts[3].replaceAll("\'", "");
				String memberSinceStr = inputLineParts[4];
				String memberSinceDate;
				if(!memberSinceStr.trim().equals(""))
				{
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

					memberSinceDate=year+"-"+month+"-"+day;	
				}
				else
				{
					memberSinceDate=2012+"-"+1+"-"+1;
				}
				
				ResultSet userQuery=statement.executeQuery("SELECT * FROM user_schema WHERE user_id = \'"+userId+"\'");
				
				if(!userQuery.next())
				{
					statement.execute("INSERT into user_schema(user_id,gender, age, country, member_since) VALUES "+
							"(\'"+userId+"\', \'"+gender+"\', "+age+", \'"+country+"\', \'"+memberSinceDate+"\')");	
				}
				
				
				
				statement.close();
				//Date memberSince = new Date(year, month, day);
//				System.out.println(memberSinceStr);
//				User user = new User();
//				user.setAge(age);
//				user.setCountry(country);
//				user.setGender(gender);
//				user.setMemberSince(memberSince);
//				user.setUserId(userId);

//				if (!uniqueUsers.containsKey(userId)) {
//					uniqueUsers.put(userId, user);
//				}
				
				

			}	
			
			
			inputFile="/home/vivek/projects/workspace/Project4cs424DataProcessing/lastfm-dataset-1K/userid-profile.tsv";
			inputReader=new BufferedReader(new FileReader(new File(inputFile)));
			while(inputReader.ready())
			{
				Statement statement=conn.createStatement();
				String inputLine = inputReader.readLine();
			//	System.out.println(inputLine);
				String inputLineParts[] = inputLine.split("\t");
				if(inputLineParts.length!=5)
				{
					continue;
				}
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
				if (inputLineParts[2].trim().equals("")) {
					age = 0;
				} else {
				//	System.out.println(inputLineParts[2]);
					age = Integer.parseInt(inputLineParts[2]);
				}

				String country = inputLineParts[3].replaceAll("\'", "");
				String memberSinceStr = inputLineParts[4];
				String memberSinceDate;
				if(!memberSinceStr.trim().equals(""))
				{
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

					memberSinceDate=year+"-"+month+"-"+day;	
				}
				else
				{
					memberSinceDate=2012+"-"+1+"-"+1;
				}
				
				ResultSet userQuery=statement.executeQuery("SELECT * FROM user_schema WHERE user_id =\'"+userId+"\'");
				
				if(!userQuery.next())
				{
					statement.execute("INSERT into user_schema(user_id,gender, age, country, member_since) VALUES "+
							"(\'"+userId+"\', \'"+gender+"\', "+age+", \'"+country+"\', \'"+memberSinceDate+"\')");	
				}
				
				
				
				statement.close();
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("connection failed");
		}

		
	}

	void loadArtistData() throws Exception {

		String inputFile = "/home/vivek/projects/workspace/Project4cs424DataProcessing/lastfm-dataset-360K/usersha1-artmbid-artname-plays.tsv";

		BufferedReader inputReader=new BufferedReader(new FileReader(new File(inputFile)));
		
		Connection conn=null;
		try
		{
			String userName="root";
			String password="tigger";
			String url="jdbc:mysql://localhost/gaga?user="+userName+"&password="+password;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn=DriverManager.getConnection(url);
			Statement dropStatement=conn.createStatement();
			dropStatement.execute("DROP table IF EXISTS artist_schema");
			dropStatement.execute("DROP table IF EXISTS listens_to_schema1");
			dropStatement.close();
			Statement createStatement=conn.createStatement();
			createStatement.execute("CREATE table artist_schema( artist_id varchar(400) NOT NULL PRIMARY KEY, artist_name varchar(255))");
			createStatement.execute("CREATE table listens_to_schema1( user_id varchar(512) NOT NULL, artist_id varchar(400) NOT NULL, air_play INTEGER, FOREIGN KEY(user_id) REFERENCES user_schema(user_id), FOREIGN KEY(artist_id) REFERENCES artist_schema(artist_id), PRIMARY KEY (user_id,artist_id))"); 			
			createStatement.close();
			long count=0;
			Statement statement=null;
			while(inputReader.ready())
			{
				if(count==0)
				{
					statement=conn.createStatement();	
				}
				count++;
				
				
				String inputLine=inputReader.readLine();
				//System.out.println(inputLine);
				String inputLineParts[]=inputLine.split("\t");
				String userId=inputLineParts[0];
				String artistId=inputLineParts[1];
				
				
				String artistName=inputLineParts[2].replaceAll("\'", "");
				int airPlay=Integer.parseInt(inputLineParts[3]);
				if(artistId.trim().equals(""))
				{
					artistId=artistName.trim().replaceAll(" ","_");
				}
//				Artist artist=new Artist();	
//				artist.setArtistId(artistId);
//				artist.setArtistName(artistName);
//				artist.setArtistPlays(userId, airPlay);
//				MiniArtist miniArtist=new MiniArtist();
//				miniArtist.setAirPlay(airPlay);
//				miniArtist.setArtistId(artistId);
//				miniArtist.setArtistName(artistName);
//				if(uniqueUsers.containsKey(userId))
//				{
//					User user=uniqueUsers.get(userId);
//					user.addArtist(miniArtist);
//					
//				}
				if(artistId.equals(""))
				{
					continue;
				}
				
				try
				{
					//System.out.println("here");
					ResultSet artistQuery=statement.executeQuery("SELECT * FROM artist_schema WHERE artist_id=\'"+artistId+"\'");
					if(!artistQuery.next())
					{
						statement.execute("INSERT into artist_schema(artist_id, artist_name) VALUES (\'"+artistId+"\', \'"+artistName+"\')");	
					}
					
					//ResultSet listensQuery=statement.executeQuery("SELECT * FROM listens_to_schema1 WHERE user_id= \'"+userId+"\' AND artist_id = \'"+artistId+"\'");
					//if(!listensQuery.next())
					{
						statement.execute("INSERT into listens_to_schema1(user_id,artist_id,air_play) VALUES (\'"+userId+"\',\'"+artistId+"\',\'"+airPlay+"\')");	
					}
						
					if(count==100000)
					{
						statement.close();
						count=0;
						System.out.println(count);	
					}
					
				}
				catch(SQLException e)
				{
					e.printStackTrace();
					System.out.println("sql error");
					continue;
				}
			
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	void loadTrackData() throws IOException
	{
		
		String fileName="/home/vivek/projects/workspace/Project4cs424DataProcessing/lastfm-dataset-1K/userid-timestamp-artid-artname-traid-traname.tsv";
		
		BufferedReader inputReader=new BufferedReader(new FileReader(new File(fileName)));
		Connection conn= null;
		
		try
		{
			
			String userName="root";
			String password="tigger";
			String url="jdbc:mysql://localhost/gaga?user="+userName+"&password="+password;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn=DriverManager.getConnection(url);
			
			Statement dropStatement=conn.createStatement();
			dropStatement.execute("DROP table IF EXISTS track_schema");
			dropStatement.execute("DROP table IF EXISTS listens_to_track_schema");
			dropStatement.close();
			
			Statement createStatement=conn.createStatement();
			createStatement.execute("CREATE TABLE track_schema(track_id varchar(512) NOT NULL PRIMARY KEY, track_name varchar(255));");
			createStatement.execute("CREATE TABLE listens_to_track_schema(user_id varchar(512) not null, artist_id varchar(512) not null, track_id varchar(512) not null,timestamp DATE, FOREIGN KEY (user_id) REFERENCES user_schema(user_id), FOREIGN KEY (artist_id) REFERENCES artist_schema(artist_id), FOREIGN KEY (track_id) REFERENCES track_schema(track_id));");
			
			createStatement.close();
			
			while(inputReader.ready())
			{
				
				try
				{
					Statement statement=conn.createStatement();
					String inputLine=inputReader.readLine();
					String inputLineParts[]=inputLine.split("\t");
					String userId=inputLineParts[0];
					String timeStamp=inputLineParts[1];
					String artistId=inputLineParts[2];
					String artistName=inputLineParts[3];
					String trackId=inputLineParts[4];
					String trackName=inputLineParts[5];
					ResultSet artistQuery=statement.executeQuery("Select * from artist_schema WHERE artist_id=\'"+artistId+"\'");
					if(!artistQuery.next())
					{
						statement.execute("INSERT into artist_schema (artist_id, artist_name) VALUES (\'"+artistId+"\', \'"+artistName+"\')");
						
					}
									
					statement.execute("INSERT into track_schema(track_id, track_name) VALUES (\'"+trackId+"\',\'"+trackName+"\')");
					
					statement.execute("INSERT into listens_to_track_schema (user_id,artist_id,track_id,timestamp) VALUES " +
							"(\'"+userId+"\', \'"+artistName+"\', \'"+trackId+"\', \'"+timeStamp+"\')");
					
					statement.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
					continue;
				}
				
				
			}
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			
		} catch (InstantiationException e) {
			
			e.printStackTrace();
			
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		
	}

}
