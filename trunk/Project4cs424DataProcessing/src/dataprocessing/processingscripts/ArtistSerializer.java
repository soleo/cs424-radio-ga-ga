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

import dataprocessing.data.Artist;

public class ArtistSerializer {
	
	public static void main(String args[]) throws IOException
	{
		HashMap<String, Artist> artistMap=new HashMap<String, Artist>();
		Connection conn;
		
		try
		{
			String userName="root";
			String password="tigger";
			String url="jdbc:mysql://localhost/gaga?user="+userName+"&password="+password;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn=DriverManager.getConnection(url);
			Statement userStatement=conn.createStatement();
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
