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

import data.User;

public class UserSerializer {
	
	public static void main(String args[])throws IOException
	{
		HashMap<String,data.User> userMap=new HashMap<String,User>();
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
			ResultSet rs=userStatement.executeQuery("Select * from user_schema");
			while(rs.next())
			{
				String userId=rs.getString(1);
				String gender=rs.getString(2);
				int age=rs.getInt(3);
				String country=rs.getString(4);
				String memberSince=rs.getDate(5).toString();				
				User user=new User();
				user.setUserId(userId);
				user.setCountry(country);
				user.setGender(gender);
				user.setMemberSince(memberSince);
				user.setAge(age);
				userMap.put(userId, user);						
			}
			
			System.out.println(userMap.keySet().size());
			ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File("../project4/DataStore/userMap.ser")));
			oos.writeObject(userMap);
			oos.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}

}
