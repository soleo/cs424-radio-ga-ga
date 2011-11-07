package dataprocessing.data;

import java.util.HashMap;

public class Artist {
	
	String artistId;
	String artistName;
	HashMap<String,Integer> artistPlays=new HashMap<String,Integer>();
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
	public int getArtistPlays(String userId) {
		return artistPlays.get(userId);
	}
	public void setArtistPlays(String userId, int count) {
		this.artistPlays.put(userId, count);
	}

}
