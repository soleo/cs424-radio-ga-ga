package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class DataClass {
	
	ArrayList<String> countries;
	
	
	public DataClass()
	{
		try
		{
			loadCountries();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	void loadCountries() throws IOException, ClassNotFoundException
	{
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream(new File("DataStore/countries.ser")));
		countries=(ArrayList<String>)ois.readObject();
	}

}
