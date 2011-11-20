package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.util.HashMap;

public class DataClass {
	
	//ArrayList<String> countries;
	HashMap<String, Country> countries = new HashMap<String, Country>();
	Connection conn;
	HashMap<String,ArtistDetails> artistMap=new HashMap<String,ArtistDetails>();
	HashMap<String, User> userMap=new HashMap<String, User>();
	
	
	public DataClass()
	{
		try
		{
			loadConnection();
			loadCountries();
			loadArtists();
			loadUsers();
			System.out.println(countries.keySet().size());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	void loadUsers() throws FileNotFoundException, IOException, ClassNotFoundException
	{
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream(new File("../DataStore/userMap.ser")));
		userMap=(HashMap<String,User>)ois.readObject();
		ois.close();
		
	}
	
	void loadConnection()
	{
		
	}
	
	void loadArtists() throws IOException, ClassNotFoundException
	{
		
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream(new File("../DataStore/artistDetails.ser")));
		artistMap=(HashMap<String,ArtistDetails>)ois.readObject();
		ois.close();
		
	}
	
	void loadCountries() throws IOException, ClassNotFoundException
	{
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream(new File("../DataStore/countries.ser")));
		countries=(HashMap<String, data.Country>)ois.readObject();
		ois.close();
	}

}
