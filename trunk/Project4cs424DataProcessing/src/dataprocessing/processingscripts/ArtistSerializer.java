package dataprocessing.processingscripts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import data.Artist;



public class ArtistSerializer {
	
	public static void main(String args[]) throws IOException
	{
		HashMap<String, Artist> artistMap=new HashMap<String, data.Artist>();
		Connection conn;
		
		try
		{
			String userName="root";
			String password="tigger";
			String url="jdbc:mysql://localhost/gaga?user="+userName+"&password="+password;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn=DriverManager.getConnection(url);
			Statement userStatement=conn.createStatement();
			
			//to load the artist id and artist name
			ResultSet rs=userStatement.executeQuery("Select * from artist_schema");
			
			while(rs.next())
			{
				String artistId=rs.getString(1);
				String artistName=rs.getString(2);
				Artist artist=new Artist();
				artist.setArtistId(artistId);
				artist.setArtistName(artistName);
				artistMap.put(artistId, artist);
			}
			rs.close();
			
			HashMap<String,String> artistMb=new HashMap<String,String>();
			BufferedReader inputReader=new BufferedReader(new FileReader(new File("/home/vivek/projects/workspace/Project4cs424DataProcessing/outputs/musicbrainz_new.tsv")));
			
			while(inputReader.ready())
			{
				String inputLine=inputReader.readLine();
				String artistId=inputLine.split("\t")[1].trim();
				artistMb.put(artistId, inputLine);
			}
			
			
			
			Set<String> keys=artistMap.keySet();
			Iterator<String> keysIterator=keys.iterator();
			
			int totalListeners;
			int maleListeners;
			int femaleListeners;
			
			int count=0;
			
			while(keysIterator.hasNext())
			{
				try
				{
					count++;
					System.out.println(count);
					String artistId = keysIterator.next();
					//System.out.println(artistId);
					Artist artist=artistMap.get(artistId);
					String artistKey=artist.getArtistId().replaceAll("\'", "\\\\'").replaceAll("/", "\\\\/");
					
					ResultSet listenerRs=userStatement.executeQuery("Select count(*) from artist_schema join listens_to_schema1 on artist_schema.artist_id=listens_to_schema1.artist_id where artist_schema.artist_id=\'"+artistKey+"\'");
					listenerRs.first();	
					totalListeners=listenerRs.getInt(1);
//					System.out.println(artistId+"\t"+totalListeners);
					artist.setTotalListeners(totalListeners);
					listenerRs.close();
					
					ResultSet maleListenerRs=userStatement.executeQuery("Select count(*) from artist_schema join listens_to_schema1 on artist_schema.artist_id=listens_to_schema1.artist_id join user_schema on listens_to_schema1.user_id= user_schema.user_id where artist_schema.artist_id=\'"+artistKey+"\' and user_schema.gender=\'m\'");
					maleListenerRs.first();				
					maleListeners=maleListenerRs.getInt(1);
		//			System.out.println(artistId+"\t"+maleListeners);
					artist.setMaleListeners(maleListeners);
					maleListenerRs.close();
					
					ResultSet femaleListenerRs=userStatement.executeQuery("Select count(*) from artist_schema join listens_to_schema1 on artist_schema.artist_id=listens_to_schema1.artist_id join user_schema on listens_to_schema1.user_id= user_schema.user_id where artist_schema.artist_id=\'"+artistKey+"\' and user_schema.gender=\'f\'");
					femaleListenerRs.first();
					femaleListeners=femaleListenerRs.getInt(1);
		//			System.out.println(artistId+"\t"+femaleListeners);
					artist.setFemaleListeners(femaleListeners);
					femaleListenerRs.close();

					artist.setUnknownListeners(totalListeners-(maleListeners+femaleListeners));
					//age group
					ResultSet ageGroup=userStatement.executeQuery("Select count(*) from user_schema , listens_to_schema1, artist_schema where user_schema.user_id=listens_to_schema1.user_id and artist_schema.artist_id = listens_to_schema1.artist_id and artist_schema.artist_id=\'"+artistKey+"\' and user_schema.age>=13 and user_schema.age<=18");
					ageGroup.first();
					artist.addGroupListeners("13-18", ageGroup.getInt(1));
					
					ageGroup=userStatement.executeQuery("Select count(*) from user_schema , listens_to_schema1, artist_schema where user_schema.user_id=listens_to_schema1.user_id and artist_schema.artist_id = listens_to_schema1.artist_id and artist_schema.artist_id=\'"+artistKey+"\' and user_schema.age>=19 and user_schema.age<=24");
					ageGroup.first();
					artist.addGroupListeners("19-24", ageGroup.getInt(1));
					
					ageGroup=userStatement.executeQuery("Select count(*) from user_schema , listens_to_schema1, artist_schema where user_schema.user_id=listens_to_schema1.user_id and artist_schema.artist_id = listens_to_schema1.artist_id and artist_schema.artist_id=\'"+artistKey+"\' and user_schema.age>=25 and user_schema.age<=35");
					ageGroup.first();
					artist.addGroupListeners("25-35", ageGroup.getInt(1));
					
					ageGroup=userStatement.executeQuery("Select count(*) from user_schema , listens_to_schema1, artist_schema where user_schema.user_id=listens_to_schema1.user_id and artist_schema.artist_id = listens_to_schema1.artist_id and artist_schema.artist_id=\'"+artistKey+"\' and user_schema.age>=36 and user_schema.age<=45");
					ageGroup.first();
					artist.addGroupListeners("36-45", ageGroup.getInt(1));
					
					ageGroup=userStatement.executeQuery("Select count(*) from user_schema , listens_to_schema1, artist_schema where user_schema.user_id=listens_to_schema1.user_id and artist_schema.artist_id = listens_to_schema1.artist_id and artist_schema.artist_id=\'"+artistKey+"\' and user_schema.age>=46 and user_schema.age<=64");
					ageGroup.first();
					artist.addGroupListeners("46-64", ageGroup.getInt(1));
					
					ageGroup=userStatement.executeQuery("Select count(*) from user_schema , listens_to_schema1, artist_schema where user_schema.user_id=listens_to_schema1.user_id and artist_schema.artist_id = listens_to_schema1.artist_id and artist_schema.artist_id=\'"+artistKey+"\' and user_schema.age>=65");
					ageGroup.first();
					artist.addGroupListeners("65 and above", ageGroup.getInt(1));
														
					
					if(artistMb.containsKey(artistId))
					{
						String line=artistMb.get(artistId);
						String birthDate=line.split("\t")[2].trim();
						String country=line.split("\t")[5].trim();
						String type=line.split("\t")[4].trim();
						String gender=line.split("\t")[6].trim();
						System.out.println(artist.getArtistName());
						artist.setBirthDate(birthDate);
						artist.setCountry(country);
						artist.setType(type);
						artist.setGender(gender);
					}
					else
					{
						artist.setBirthDate("");
						artist.setCountry("");
						artist.setType("");
						artist.setGender("");
					}
										
					artistMap.put(artistId, artist);
	 
				}
				catch(Exception e)
				{
					e.printStackTrace();
					continue;
				}
								
				
			}
			
			
			ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File("/home/vivek/projects/workspace/visproj4/project4/DataStore/artistMap.ser")));
			oos.writeObject(artistMap);
			oos.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
				
	}

}
