package dataprocessing.processingscripts;

import java.io.File;
import java.io.FileOutputStream;
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
			
			Set<String> keys=artistMap.keySet();
			Iterator<String> keysIterator=keys.iterator();
			while(keysIterator.hasNext())
			{
				String artistId = keysIterator.next();
				System.out.println(artistId);
				Artist artist=artistMap.get(artistId);
				String artistKey=artist.getArtistId().replaceAll("\'", "\\\\'").replaceAll("/", "\\\\/");
				
				ResultSet listenerRs=userStatement.executeQuery("Select count(*) from artist_schema join listens_to_schema1 on artist_schema.artist_id=listens_to_schema1.artist_id where artist_schema.artist_id=\'"+artistKey+"\'");
				listenerRs.first();
				int totalListeners=listenerRs.getInt(1);
//				System.out.println(artistId+"\t"+totalListeners);
				artist.setTotalListeners(totalListeners);
				listenerRs.close();
				
				ResultSet maleListenerRs=userStatement.executeQuery("Select count(*) from artist_schema join listens_to_schema1 on artist_schema.artist_id=listens_to_schema1.artist_id join user_schema on listens_to_schema1.user_id= user_schema.user_id where artist_schema.artist_id=\'"+artistKey+"\' and user_schema.gender=\'m\'");
				maleListenerRs.first();
				int maleListeners=maleListenerRs.getInt(1);
	//			System.out.println(artistId+"\t"+maleListeners);
				artist.setMaleListeners(maleListeners);
				maleListenerRs.close();
				
				ResultSet femaleListenerRs=userStatement.executeQuery("Select count(*) from artist_schema join listens_to_schema1 on artist_schema.artist_id=listens_to_schema1.artist_id join user_schema on listens_to_schema1.user_id= user_schema.user_id where artist_schema.artist_id=\'"+artistKey+"\' and user_schema.gender=\'f\'");
				femaleListenerRs.first();
				int femaleListeners=femaleListenerRs.getInt(1);
	//			System.out.println(artistId+"\t"+femaleListeners);
				artist.setFemaleListeners(femaleListeners);
				femaleListenerRs.close();

				artist.setUnknownListeners(totalListeners-(maleListeners+femaleListeners));
				//list of listeners
				
				ResultSet listeners=userStatement.executeQuery("Select user_schema.user_id from user_schema join listens_to_schema1 on user_schema.user_id=listens_to_schema1.user_id join artist_schema on listens_to_schema1.artist_id=artist_schema.artist_id where artist_schema.artist_id=\'"+artistKey+"\'");
				while(listeners.next())
				{
					artist.addListener(listeners.getString(1));
				}
				listeners.close();
				System.out.println(artist.getTotalListeners()+"\t"+artist.listListeners().size());
				artistMap.put(artistId, artist);
				
				
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
