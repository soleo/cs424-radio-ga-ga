package dataprocessing.processingscripts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import data.Country;




public class CountriesSerializer {
	
	
	
	public static void main(String args[])throws IOException, ClassNotFoundException
	{
		
		HashMap<String,String> countryMapSer;
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream(new File("countryMap.ser")));
		countryMapSer=(HashMap<String,String>)ois.readObject();
		
		BufferedReader inputReader=new BufferedReader(new FileReader(new File("outputs/countries")));
		//List<String> countriesList=new ArrayList<String>();
		HashMap<String, Country> countryMap = new HashMap<String, Country>();
		
		HashMap<String, String> iso = new HashMap<String, String>();
		BufferedReader cnIsoReader=new BufferedReader(new FileReader(new File("outputs/country.tsv")));
		while(cnIsoReader.ready())
		{
			String inputLine=cnIsoReader.readLine().trim();
			String[] data = inputLine.split("\t");
			if(data.length != 2){
				for(int i=0; i<data.length; i++) 
					System.out.print(data[i]+" ");
				System.out.println();
				//continue;
			}
			else
				iso.put(data[1].trim().toLowerCase(), data[0].trim().toLowerCase());
		}
		
		//for (String key : iso.keySet())
		//	System.out.println(key + " " + iso.get(key));
		
		while(inputReader.ready())
		{
			String inputLine=inputReader.readLine().trim();
			String[] data = inputLine.split("\t");
			Country c = new Country();
			
			c.name = new String(data[0].trim());
			
			if(iso.containsKey(c.name))
			{
				c.isoCode = new String((String)iso.get(data[0].trim()));
				c.timeZoneOffset = new String(data[1].trim());
			
				//countriesList.add(inputLine);
				countryMap.put(c.name, c);
			}
			else
			{
				System.out.println(data[0].trim() + "\t" + data[1].trim());
			}
			
		}
		
		/*Collections.sort(countriesList);
		for(int i=0;i<countriesList.size();i++)
		{
			System.out.println(countriesList.get(i));
		}*/
		//Collections.sort(countryList);
		
		Connection conn;
		
		try
		{
			String userName="root";
			String password="tigger";
			String url="jdbc:mysql://localhost/gaga?user="+userName+"&password="+password;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn=DriverManager.getConnection(url);
			Statement userStatement=conn.createStatement();
			
			Set<String> countryKeys=countryMap.keySet();
			Iterator<String> countryKeysIterator=countryKeys.iterator();
			
			int totalListeners;
			int maleListeners;
			int femaleListeners;
			int count=0;
			while(countryKeysIterator.hasNext())
			{
				count++;
				System.out.println(count);
				String key=countryKeysIterator.next();
				Country country=countryMap.get(key);
				String countryKey=countryMapSer.get(country.getName()).replaceAll("'", "\\\\'");
							
				ResultSet rs=userStatement.executeQuery("Select count(*) from user_schema join listens_to_schema1 on user_schema.user_id=listens_to_schema1.user_id where user_schema.country=\'"+countryKey+"\'");
				rs.first();	
				totalListeners=rs.getInt(1);
				
				country.setTotalListeners(totalListeners);
							
				ResultSet maleRs=userStatement.executeQuery("Select count(*) from user_schema join listens_to_schema1 on user_schema.user_id=listens_to_schema1.user_id where user_schema.country=\'"+countryKey+"\' AND user_schema.gender=\'m\'");
				maleRs.first();			
				maleListeners=maleRs.getInt(1);
				country.setMaleListeners(maleListeners);
								
				ResultSet femaleRs=userStatement.executeQuery("Select count(*) from user_schema join listens_to_schema1 on user_schema.user_id=listens_to_schema1.user_id where user_schema.country=\'"+countryKey+"\' AND user_schema.gender=\'f\'");
				femaleRs.first();		
				femaleListeners=femaleRs.getInt(1);
				country.setFemaleListeners(femaleListeners);
								
				country.setUnknownListeners(totalListeners-(maleListeners+femaleListeners));
			
				rs.close();
				maleRs.close();
				femaleRs.close();
				
				countryMap.put(key, country);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		
		ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File("/home/vivek/projects/workspace/visproj4/project4/DataStore/countries.ser")));
		//ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File("countries.ser")));
		oos.writeObject(countryMap);
		oos.close();
	}

}
