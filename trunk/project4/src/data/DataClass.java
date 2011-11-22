package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
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
	long maxListeners;
	
	HashMap<String,Country> countryCodeMap=new HashMap<String,Country>();
	
	HashMap<String, Country> countryMap = new HashMap<String, Country>();
	Connection conn;
	HashMap<String,ArtistDetails> artistMap=new HashMap<String,ArtistDetails>();
	HashMap<String, User> userMap=new HashMap<String, User>();
	
	ArrayList<String> countriesStrList=new ArrayList<String>();
	
	ArrayList<String> genderList=new ArrayList<String>();
	ArrayList<String> ageGroups=new ArrayList<String>();
	
	HashMap<String, String> similarArtist = new HashMap<String, String>();
	
	int[] hourlyListenCount = {732066, 587913, 471951, 382781, 319063, 284651, 289368, 329384, 414420, 540122, 684994, 806465, 874527, 944086, 1016169, 1054434, 1069057, 1064072, 1059258, 1062602, 1071687, 1069503, 1014440, 894799};
	
	static ArrayList<ArtistDetails> topArtistAllTime=new ArrayList<ArtistDetails>();
	public DataClass()
	{
		try
		{
			loadSimilarArtist();
			
			loadConnection();
			System.out.println("setting up");
			loadCountryMap();
			loadCountryCodeMap();
			computeMaxListeners();
			System.out.println("setting up");
			loadArtists();
			loadTopArtistsAllTime();
			loadUsers();
			loadGenders();
			loadAgeGroups();
			System.out.println(countryMap.keySet().size());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	public long getMaxListeners()
	{
		return maxListeners;
	}
	
	void computeMaxListeners()
	{
		Set<String> countryCodes=countryCodeMap.keySet();
		Iterator<String> countryCodesIterator=countryCodes.iterator();
		long maxListeners=0;
		while(countryCodesIterator.hasNext())
		{
			String code=countryCodesIterator.next();
			if(maxListeners<=countryCodeMap.get(code).getTotalListeners())
			{
				maxListeners=countryCodeMap.get(code).getTotalListeners();
			}
			
		}
		this.maxListeners=maxListeners;
	}
	
	public boolean isCountryCodePresent(String countryCode)
	{
		return countryCodeMap.containsKey(countryCode);
	}
	void loadCountryCodeMap() throws FileNotFoundException, IOException, ClassNotFoundException
	{
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream(new File("../DataStore/countriesCodeMap.ser")));
		countryCodeMap=(HashMap<String, Country>) ois.readObject();
		ois.close();
				
	}
	
	public Country getCountryByCode(String code)
	{
		if(countryCodeMap.containsKey(code))
		{
			return countryCodeMap.get(code);	
		}
		else
		{
			return null;
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
	
	void loadTopArtistsAllTime() throws IOException
	{
		BufferedReader inputReader=new BufferedReader(new FileReader(new File("../DataStore/topartist")));
		while(inputReader.ready())
		{
			String inputLine=inputReader.readLine();
			String inputLineParts[]=inputLine.trim().split(" ");
			String artistId=inputLineParts[0].trim();
			String count=inputLineParts[1].trim();
			
			if(!count.equals(""))
			{
				if(artistMap.containsKey(artistId))
				{
					System.out.println(artistId+"\t"+count);
					ArtistDetails artist=artistMap.get(artistId);
					artist.setCurrentCount(Integer.parseInt(count));
					topArtistAllTime.add(artist);	
				}
					
			}
			
			
		}
		inputReader.close();
	}
	
	
	void loadUsers() throws FileNotFoundException, IOException, ClassNotFoundException
	{
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream(new File("../DataStore/userMap.ser")));
		userMap=(HashMap<String,User>)ois.readObject();
		ois.close();		
	}
	
	void loadConnection() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		String userName="root";//enter username
		String password="";//enter password
		String url="jdbc:mysql://localhost/gaga?user="+userName+"&password="+password;
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn=DriverManager.getConnection(url);
		
		
	}
	
	void loadArtists() throws IOException, ClassNotFoundException
	{		
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream(new File("../DataStore/artistDetails.ser")));
		artistMap=(HashMap<String,ArtistDetails>)ois.readObject();
		ois.close();		
	}
	
	void loadCountryMap() throws IOException, ClassNotFoundException
	{
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream(new File("../DataStore/countries.ser")));
		countryMap=(HashMap<String, data.Country>)ois.readObject();
		ois.close();
		Set<String> countryKeys=countryMap.keySet();
		Iterator<String> countryIterator=countryKeys.iterator();
		while(countryIterator.hasNext())
		{
			Country country=countryMap.get(countryIterator.next());
			countriesStrList.add(country.getName());
		}
		Collections.sort(countriesStrList);
				
	}
	
	public ArrayList<String> getListOfCountries()
	{
		return countriesStrList;		
	}
	
	public ArrayList<String> getGenders()
	{		
		return genderList;
	}
	
	public ArrayList<String> getAgeGroups()
	{
		return ageGroups;
	}
		
	ArrayList<ArtistDetails> getTop100ArtistByGender(String gender) throws Exception
	{
		ArrayList<ArtistDetails> artistList=new ArrayList<ArtistDetails>();
		
		Statement statement=conn.createStatement();
		ResultSet result=statement.executeQuery("select artist_schema.artist_id,subartist.user_count from artist_schema, (select artist_id,count(listens_to_schema1.user_id) user_count from listens_to_schema1, (select user_id from user_schema where user_schema.gender=\'"+gender.toLowerCase()+"\') as users where users.user_id=listens_to_schema1.user_id group by listens_to_schema1.artist_id) as subartist where artist_schema.artist_id=subartist.artist_id order by subartist.user_count desc limit 0,10");
		while(result.next())
		{
			String artistId=result.getString(1);
			int currentCount=result.getInt(2);
			ArtistDetails artistDetails=artistMap.get(artistId);
			artistDetails.setCurrentCount(currentCount);
			artistList.add(artistDetails);
			
		}		
		return artistList;		
	}
	ArrayList<ArtistDetails> getTop100ArtistByAgeGroup(String ageGroup) throws SQLException
	{
		ArrayList<ArtistDetails> artistList=new ArrayList<ArtistDetails>();
		int lowerLimit;
		int upperLimit;
		if(ageGroup.contains("-"))
		{
			String ageGroupParts[]=ageGroup.split("-");
			String lowerStr=ageGroupParts[0].trim();
			String upperStr=ageGroupParts[1].trim();
			lowerLimit=Integer.parseInt(lowerStr);
			upperLimit=Integer.parseInt(upperStr);
		}
		else
		{
			lowerLimit=65;
			upperLimit=200;
		}
		
		Statement statement=conn.createStatement();
		ResultSet result=statement.executeQuery("select artist_schema.artist_id,subartist.user_count from artist_schema, (select artist_id,count(listens_to_schema1.user_id) user_count from listens_to_schema1, (select user_id from user_schema where user_schema.age>="+lowerLimit+" and user_schema.age<="+upperLimit+") as users where users.user_id=listens_to_schema1.user_id group by listens_to_schema1.artist_id) as subartist where artist_schema.artist_id=subartist.artist_id order by subartist.user_count desc limit 0,100");
		while(result.next())
		{
			String artistId=result.getString(1);
			int currentCount=result.getInt(2);
			ArtistDetails artistDetails=artistMap.get(artistId);
			artistDetails.setCurrentCount(currentCount);
			artistList.add(artistDetails);
			
		}		
		return artistList;
	}
	
	public ArrayList<ArtistDetails> getTop100ArtistByCountry(String country) throws SQLException
	{
		ArrayList<ArtistDetails> artistList=new ArrayList<ArtistDetails>();
		
		Statement statement=conn.createStatement();
		ResultSet result=statement.executeQuery("select artist_schema.artist_id,subartist.user_count from artist_schema, (select artist_id,count(listens_to_schema1.user_id) user_count from listens_to_schema1, (select user_id from user_schema where user_schema.country=\'"+country+"\') as users where users.user_id=listens_to_schema1.user_id group by listens_to_schema1.artist_id) as subartist where artist_schema.artist_id=subartist.artist_id order by subartist.user_count desc limit 0,100");
		while(result.next())
		{
			String artistId=result.getString(1);
			int currentCount=result.getInt(2);
			ArtistDetails artistDetails=artistMap.get(artistId);
			artistDetails.setCurrentCount(currentCount);
			artistList.add(artistDetails);
			
		}		
		return artistList;
		
	}
	
	public ArrayList<ArtistDetails> getTop100ArtistByAgeGroupAndCountry(String ageGroup,String country) throws SQLException
	{
		ArrayList<ArtistDetails> artistList=new ArrayList<ArtistDetails>();
		int lowerLimit;
		int upperLimit;
		if(ageGroup.contains("-"))
		{
			String ageGroupParts[]=ageGroup.split("-");
			String lowerStr=ageGroupParts[0].trim();
			String upperStr=ageGroupParts[1].trim();
			lowerLimit=Integer.parseInt(lowerStr);
			upperLimit=Integer.parseInt(upperStr);
		}
		else
		{
			lowerLimit=65;
			upperLimit=200;
		}
		
		Statement statement=conn.createStatement();
		ResultSet result=statement.executeQuery("select artist_schema.artist_id,subartist.user_count from artist_schema, (select artist_id,count(listens_to_schema1.user_id) user_count from listens_to_schema1, (select user_id from user_schema where user_schema.age>="+lowerLimit+" and user_schema.age<="+upperLimit+" and user_schema.country=\'"+country+"\') as users where users.user_id=listens_to_schema1.user_id group by listens_to_schema1.artist_id) as subartist where artist_schema.artist_id=subartist.artist_id order by subartist.user_count desc limit 0,100");
		while(result.next())
		{
			String artistId=result.getString(1);
			int currentCount=result.getInt(2);
			ArtistDetails artistDetails=artistMap.get(artistId);
			artistDetails.setCurrentCount(currentCount);
			artistList.add(artistDetails);
			
		}		
		return artistList;
		
	}
	
	public ArrayList<ArtistDetails> getTop100ArtistByAgeGroupAndGender(String ageGroup,String gender) throws SQLException
	{
		ArrayList<ArtistDetails> artistList=new ArrayList<ArtistDetails>();
		int lowerLimit;
		int upperLimit;
		if(ageGroup.contains("-"))
		{
			String ageGroupParts[]=ageGroup.split("-");
			String lowerStr=ageGroupParts[0].trim();
			String upperStr=ageGroupParts[1].trim();
			lowerLimit=Integer.parseInt(lowerStr);
			upperLimit=Integer.parseInt(upperStr);
		}
		else
		{
			lowerLimit=65;
			upperLimit=200;
		}
		
		Statement statement=conn.createStatement();
		ResultSet result=statement.executeQuery("select artist_schema.artist_id,subartist.user_count from artist_schema, (select artist_id,count(listens_to_schema1.user_id) user_count from listens_to_schema1, (select user_id from user_schema where user_schema.age>="+lowerLimit+" and user_schema.age<="+upperLimit+" and user_schema.gender=\'"+gender.trim()+"\') as users where users.user_id=listens_to_schema1.user_id group by listens_to_schema1.artist_id) as subartist where artist_schema.artist_id=subartist.artist_id order by subartist.user_count desc limit 0,100");
		while(result.next())
		{
			String artistId=result.getString(1);
			int currentCount=result.getInt(2);
			ArtistDetails artistDetails=artistMap.get(artistId);
			artistDetails.setCurrentCount(currentCount);
			artistList.add(artistDetails);
			
		}		
		return artistList;
		
	}
	public ArrayList<ArtistDetails> getTop100ArtistByGenderAndCountry(String gender,String country) throws SQLException
	{
		ArrayList<ArtistDetails> artistList=new ArrayList<ArtistDetails>();
		
		Statement statement=conn.createStatement();
		ResultSet result=statement.executeQuery("select artist_schema.artist_id,subartist.user_count from artist_schema, (select artist_id,count(listens_to_schema1.user_id) user_count from listens_to_schema1, (select user_id from user_schema where user_schema.gender=\'"+gender.toLowerCase()+"\' and user_schema.country=\'"+country+"\') as users where users.user_id=listens_to_schema1.user_id group by listens_to_schema1.artist_id) as subartist where artist_schema.artist_id=subartist.artist_id order by subartist.user_count desc limit 0,100");
		while(result.next())
		{
			String artistId=result.getString(1);
			int currentCount=result.getInt(2);
			ArtistDetails artistDetails=artistMap.get(artistId);
			artistDetails.setCurrentCount(currentCount);
			artistList.add(artistDetails);
			
		}		
		return artistList;
		
	}
	public ArrayList<ArtistDetails> getTop100ArtistByGenderAndAgeGroupAndCountry(String gender,String ageGroup,String country) throws SQLException
	{
		ArrayList<ArtistDetails> artistList=new ArrayList<ArtistDetails>();
		int lowerLimit;
		int upperLimit;
		if(ageGroup.contains("-"))
		{
			String ageGroupParts[]=ageGroup.split("-");
			String lowerStr=ageGroupParts[0].trim();
			String upperStr=ageGroupParts[1].trim();
			lowerLimit=Integer.parseInt(lowerStr);
			upperLimit=Integer.parseInt(upperStr);
		}
		else
		{
			lowerLimit=65;
			upperLimit=200;
		}
		
		Statement statement=conn.createStatement();
		ResultSet result=statement.executeQuery("select artist_schema.artist_id,subartist.user_count from artist_schema, (select artist_id,count(listens_to_schema1.user_id) user_count from listens_to_schema1, (select user_id from user_schema where user_schema.age>="+lowerLimit+" and user_schema.age<="+upperLimit+" and user_schema.country=\'"+country+"\' and user_schema.gender=\'"+gender.toLowerCase()+"\') as users where users.user_id=listens_to_schema1.user_id group by listens_to_schema1.artist_id) as subartist where artist_schema.artist_id=subartist.artist_id order by subartist.user_count desc limit 0,100");
		while(result.next())
		{
			String artistId=result.getString(1);
			int currentCount=result.getInt(2);
			ArtistDetails artistDetails=artistMap.get(artistId);
			artistDetails.setCurrentCount(currentCount);
			artistList.add(artistDetails);
			
		}		
		return artistList;
		
	}
	
	public ArrayList<ArtistDetails> getTopArtistAllTime()
	{
		return topArtistAllTime;
	}
	
	public ArrayList<ArtistDetails> getTop100Artists(String gender,String ageGroup,String country)
	{
		ArrayList<ArtistDetails> artistList=null;
		try {		
		
			if(gender.trim().equals("")&& ageGroup.trim().equals("")&&country.trim().equals(""))
			{
//				Statement s;				
//				s = conn.createStatement();				
//				ResultSet rs=s.executeQuery("select artist_id, count() from artist_schema, listens_to_schema1, user_schema where artist_schema.artist_id=listens_to_schema1.artist_id and user_schema.user_id=listens_to_schema1.user_id ");
//				while(rs.next())
//				{
//					String artistId=rs.getString(1);
//					
//				}
				artistList=getTopArtistAllTime();
			}
			else if(!gender.trim().equals("") && country.trim().equals("")&& ageGroup.trim().equals(""))
			{
				artistList=getTop100ArtistByGender(gender.trim().toLowerCase());
			}
			else if(gender.trim().equals("") && !country.trim().equals("")&& ageGroup.trim().equals(""))
			{
				artistList=getTop100ArtistByCountry(country.trim());
			}
			else if(gender.trim().equals("") && country.trim().equals("") && !ageGroup.trim().equals(""))
			{
				artistList=getTop100ArtistByAgeGroup(ageGroup.trim());
			}
			else if(!gender.trim().equals("") && !country.trim().equals("") && ageGroup.trim().equals(""))
			{
				artistList=getTop100ArtistByGenderAndCountry(gender.trim(), country.trim());
			}
			else if(!gender.trim().equals("") && country.trim().equals("") && !ageGroup.trim().equals(""))
			{
				artistList=getTop100ArtistByAgeGroupAndGender(ageGroup.trim(), gender.trim());
				
			}
			else if(gender.trim().equals("") && !country.trim().equals("") && !country.trim().equals(""))
			{
				artistList=getTop100ArtistByAgeGroupAndCountry(ageGroup.trim(), country.trim());
			}			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return artistList;
	}
	
	/**
	 * 
	 * @param artistName : the name of the artist, all lowercase
	 * @return returns a array of Strings, with the similar artists' name
	 */
	public String[] getSimilarArtist(String artistName)
	{
		String[] sim = new String[0];
		if(similarArtist.containsKey(artistName.toLowerCase())) sim = similarArtist.get(artistName.toLowerCase()).split(";");
		return sim;
	}
	
	void loadSimilarArtist() throws IOException, ClassNotFoundException
	{
		String inputFile="../DataStore/similarArtist.ser";
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream(new File(inputFile)));
		similarArtist=(HashMap<String, String>)ois.readObject();
		ois.close();
	}
	
	
	public int[] getHourlyListenCount()
	{
		return this.hourlyListenCount;
	}

}
