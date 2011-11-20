package dataprocessing.processingscripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream.GetField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

		ObjectInputStream ois=new ObjectInputStream(new FileInputStream(new File("/home/vivek/projects/workspace/visproj4/project4/DataStore/artistMap.ser")));
		artistMap=(HashMap<String,Artist>)ois.readObject();
		ois.close();
					
		Set<String> keys=artistMap.keySet();
		Iterator<String> keysIterator=keys.iterator();
		Connection conn;
		try
		{
			String userName="root";
			String password="tigger";
			String url="jdbc:mysql://localhost/gaga?user="+userName+"&password="+password;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn=DriverManager.getConnection(url);
			Statement userStatement=conn.createStatement();
			
			int lineCount=0;
			
			while(keysIterator.hasNext())
			{
				lineCount++;
				String key=keysIterator.next();
				Artist artist=artistMap.get(key);
				ArtistDetails artistDetails=new ArtistDetails();
				artistDetails.setArtistId(artist.getArtistId());
				artistDetails.setArtistName(artist.getArtistName());
				artistDetails.setTotalListeners(artist.getTotalListeners());
				artistDetails.setFemaleListeners(artist.getFemaleListeners());
				artistDetails.setMaleListeners(artist.getMaleListeners());
				artistDetails.setUnknownListeners(artist.getUnknownListeners());
				artistDetails.setBirthDate(artist.getBirthDate());
				artistDetails.setCountry(artist.getCountry());
				artistDetails.setGender(artist.getGender());
				artistDetails.setType(artist.getType());				

				try
				{
					//query to compute top countries				
					ResultSet result=userStatement.executeQuery("select country, count(users.user_id) user_count from user_schema," +
							" ( select user_id, artist_id from listens_to_track_schema where artist_id=\'"+artist.getArtistId().replaceAll("'", "\\\\'")+"\' ) users " +
									"where users.user_id = user_schema.user_id group by country order by user_count desc");
					
					int count=0;
					int othersTotal=0;
					while(result.next())
					{
						count++;
						if(count<=10)
						{
							String country=result.getString(1);
							int countryCount=result.getInt(2);
							artistDetails.addTopCountry(country+"\t"+countryCount);	
						}
						else
						{
							int countryCount=result.getInt(2);
							othersTotal+=countryCount;
						}					
					}
					artistDetails.addTopCountry("Others\t"+othersTotal);				
					System.out.println(lineCount+"\t"+artistDetails.getTopCoutries().size());				
					artistDetailsMap.put(key, artistDetails);
				}
				catch(SQLException e)
				{
					
					e.printStackTrace();
					continue;
					
				}
				
												
			}
	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
				
		ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("/home/vivek/projects/workspace/visproj4/project4/DataStore/artistDetails.ser"));
		oos.writeObject(artistDetailsMap);
		oos.close();
			
	}

}
