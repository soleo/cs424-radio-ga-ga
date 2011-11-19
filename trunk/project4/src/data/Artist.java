package data;

import java.util.ArrayList;

public class Artist {
	
	String artistId;
	String artistName;
	int totalListeners;
	int maleListeners;
	int femaleListeners;
	ArrayList<String> listenersList=new ArrayList<String>();
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
	
	public void addListener(String userId)
	{
		this.listenersList.add(userId);
	}
	
	public ArrayList<String> listListeners()
	{
		return listenersList;
	}
	

}
