package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ArtistDetails implements Serializable{
	
	String artistId;
	String artistName;
	int totalListeners;
	int maleListeners;
	int femaleListeners;
	int unknownListeners;
	
	String birthDate;
	String type;
	String country;
	String gender;
	
	int currentCount;
	//shows top 10 countries in country\tcount format
	ArrayList<String> topCountries=new ArrayList<String>();
		
	HashMap<String,Integer> ageGroupListeners=new HashMap<String,Integer>();
	
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}	
	public void addGroupListeners(String key,int listenerCount)
	{
		ageGroupListeners.put(key, listenerCount);
	}	
	public int getGroupListenersByGroup(String key)
	{
		return ageGroupListeners.get(key);
	}	
	public int getUnknownListeners() {
		return unknownListeners;
	}
	public void setUnknownListeners(int unknownListeners) {
		this.unknownListeners = unknownListeners;
	}	
	public String getArtistId() {
		return artistId;
	}
	public void setArtistId(String artistId) {
		this.artistId = artistId;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public int getTotalListeners() {
		return totalListeners;
	}
	public void setTotalListeners(int totalListeners) {
		this.totalListeners = totalListeners;
	}
	public int getMaleListeners() {
		return maleListeners;
	}
	public void setMaleListeners(int maleListeners) {
		this.maleListeners = maleListeners;
	}
	public int getFemaleListeners() {
		return femaleListeners;
	}
	public void setFemaleListeners(int femaleListeners) {
		this.femaleListeners = femaleListeners;
	}
	
	public void setCurrentCount(int count)
	{
		currentCount=count;
	}
	public int getCurrentCount()
	{
		return currentCount;
	}
	
	public void addTopCountry(String country)
	{
		topCountries.add(country);
	}
	public ArrayList<String> getTopCoutries()
	{
		return topCountries;
	}
}
