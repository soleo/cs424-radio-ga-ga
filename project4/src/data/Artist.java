package data;

import java.io.Serializable;
import java.util.ArrayList;

public class Artist implements Serializable{
	
	String artistId;
	String artistName;
	int totalListeners;
	int maleListeners;
	int femaleListeners;
	int unknownListeners;
	
	
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
	
	
	
	
	

}
