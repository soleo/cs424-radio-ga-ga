package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class DataClass {
	
	//ArrayList<String> countries;
	HashMap<String, Country> countries = new HashMap<String, Country>();
	Connection conn;
	HashMap<String,Artist> artistMap=new HashMap<String,Artist>();
	HashMap<String, User> userMap=new HashMap<String, User>();
	
	ArrayList<String> countriesStrList=new ArrayList<String>();
	
	ArrayList<String> genderList=new ArrayList<String>();
	ArrayList<String> ageGroups=new ArrayList<String>();
		
	public DataClass()
	{
		try
		{
			loadConnection();
			loadCountries();
			loadArtists();
			loadUsers();
			loadGenders();
			loadAgeGroups();
			System.out.println(countries.keySet().size());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	
	void loadGenders()
	{		
		genderList.add("Male");
		genderList.add("Female");
	}
	
	void loadAgeGroups()
	{
		ageGroups.add("13 - 18");
		ageGroups.add("19 - 24");
		ageGroups.add("25 - 35");
		ageGroups.add("36 - 45");
		ageGroups.add("46 - 64");
		ageGroups.add("65 and above");
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
		artistMap=(HashMap<String,Artist>)ois.readObject();
		ois.close();		
	}
	
	void loadCountries() throws IOException, ClassNotFoundException
	{
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream(new File("../DataStore/countries.ser")));
		countries=(HashMap<String, data.Country>)ois.readObject();
		ois.close();
		Set<String> countryKeys=countries.keySet();
		Iterator<String> countryIterator=countryKeys.iterator();
		while(countryIterator.hasNext())
		{
			Country country=countries.get(countryIterator.next());
			countriesStrList.add(country.getName());
		}
		Collections.sort(countriesStrList);
				
	}
	
	ArrayList<String> getListOfCountries()
	{
		return countriesStrList;		
	}
	
	ArrayList<String> getGenders()
	{		
		return genderList;
	}
	
	ArrayList<String> getAgeGroups()
	{
		return ageGroups;
	}
		
	ArrayList<Artist> getTop100ArtistByGender(String gender)
	{
		
	}
	ArrayList<Artist> getTop100ArtistByAgeGroup(String ageGroup)
	{
		
	}
	
	ArrayList<Artist> getTop100ArtistByCountry(String country)
	{
		
	}
	
	ArrayList<Artist> getTop100ArtistByAgeGroupAndCountry(String ageGroup,String country)
	{
		
	}
	
	ArrayList<Artist> getTop100ArtistByAgeGroupAndGender(String ageGroup,String Gender)
	{
		
	}
	ArrayList<Artist> getTop100ArtistByGenderAndCountry(String gender,String country)
	{
		
	}
	ArrayList<Artist> getTop100ArtistByGenderAndAgeGroupAndCountry(String gender,String ageGroup,String country)
	{
		
	}
	
	
	ArrayList<Artist> getTop100Artists(String gender,String ageGroup,String country)
	{
		ArrayList<Artist> artistList;
		try {
			
		
			if(gender.trim().equals("")&& ageGroup.trim().equals("")&&country.trim().equals(""))
			{
				Statement s;				
				s = conn.createStatement();				
				ResultSet rs=s.executeQuery("");
				while(rs.next())
				{
					String artistId=rs.getString(1);
					
				}
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	

}
