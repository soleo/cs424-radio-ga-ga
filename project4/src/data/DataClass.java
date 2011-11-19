package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.util.HashMap;

public class DataClass {
	
	//ArrayList<String> countries;
	HashMap<String, Country> countries = new HashMap<String, Country>();
	Connection conn;
	HashMap<String,Artist> artistMap=new HashMap<String,Artist>();
	HashMap<String, User> userMap=new HashMap<String, User>();
	
	public DataClass()
	{
		try
		{
			loadCountries();	
			System.out.println(countries.keySet().size());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	void loadCountries() throws IOException, ClassNotFoundException
	{
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream(new File("../DataStore/countries.ser")));
		countries=(HashMap<String, data.Country>)ois.readObject();
	}

}
