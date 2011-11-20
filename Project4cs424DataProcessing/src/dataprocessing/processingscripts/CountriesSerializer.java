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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import data.Country;




public class CountriesSerializer {
	
	
	
	public static void main(String args[])throws IOException, ClassNotFoundException
	{
		String path_to_timestamps_folder="/Users/kaiser/Desktop/Desktop/way2/";
		//String inputFile="../project4/DataStore/countries.ser";
		String outputFile="../project4/DataStore/countries.ser";
		
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
			
				File timeStampFile = new File(path_to_timestamps_folder+c.name);
				if(timeStampFile.exists())
				{
					BufferedReader tsReader = new BufferedReader(new FileReader(timeStampFile));
					int tz_hr = Integer.parseInt(c.timeZoneOffset.substring(0,c.timeZoneOffset.indexOf(':')));
					int tz_min = Integer.parseInt(c.timeZoneOffset.substring(c.timeZoneOffset.indexOf(':')+1));
					int hourly_count[] = new int[24];
					while(tsReader.ready())
					{
						String timeStamp = tsReader.readLine().trim();
						
						String hr_min = timeStamp.substring(timeStamp.indexOf('T')+1, timeStamp.indexOf('T')+6);
						float hr = Float.parseFloat(hr_min.substring(0, hr_min.indexOf(':')));
						float min = Float.parseFloat(hr_min.substring(hr_min.indexOf(':')+1));
						if(tz_hr < 0)
						{
							hr = tz_hr + (min - tz_min) / 60;
							if (hr < 0) hr += 24;
						}
						else
						{
							hr = tz_hr + (min + tz_min) / 60;
							if (hr >= 24) hr -= 24;
						}
						hourly_count[(int)Math.floor(hr)]++;
					}
					c.setHourlyCount(Arrays.toString(hourly_count));
				}
				else
				{
					System.out.println("Timestamps not found for:\t" + c.name);
				}
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
			//String password="tigger";
			String password="";
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
				
				// get data for age start at 13, 19, 25, 36, 46, 65
				int age_count[] = new int[6];
				rs=userStatement.executeQuery("SELECT ag.age_group, count(ag.age_group) "
						+ " From "
						+ "  (SELECT" +
						" CASE " +
						"  when g.age >= 13 and g.age <=18 then 13" +
						" when g.age >= 19 and g.age <=24 then 19" +
						" when g.age >= 25 and g.age <=35 then 25" +
						" when g.age >= 36 and g.age <=45 then 36" +
						" when g.age >= 46 and g.age <=64 then 46" +
						" when g.age >= 65 then 65" +
						" END as age_group" +
						" From user_schema g " +
						" WHERE g.age >= 13 AND g.country=\'"+countryKey+"\'" +
						" ) as ag " +
						" Group by age_group");
				while(rs.next())
				{
					int age=rs.getInt(1);
					switch(age)
					{
					case 13 : age_count[0] = rs.getInt(2); break;
					case 19 : age_count[1] = rs.getInt(2); break;
					case 25 : age_count[2] = rs.getInt(2); break;
					case 36 : age_count[3] = rs.getInt(2); break;
					case 46 : age_count[4] = rs.getInt(2); break;
					case 65 : age_count[5] = rs.getInt(2); break;
					default: System.out.println("skiped age " + age + " with user " + rs.getInt(2) + " for country " + country.name );
					}
				}
				country.setAgeGroupCount(Arrays.toString(age_count));
				rs.close();
				
				countryMap.put(key, country);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		
		//ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File("/home/vivek/projects/workspace/visproj4/project4/DataStore/countries.ser")));
		ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File(outputFile)));
		oos.writeObject(countryMap);
		oos.close();
	}

}
