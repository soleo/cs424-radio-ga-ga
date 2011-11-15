package dataprocessing.data;

public class Track {
	
	String trackId;
	String trackName;
	String listenedAtUserId;
	String trackByArtistName;
	String trackByArtistId;
	public String getListenedAt() {
		return listenedAtUserId;
	}
	public void setListenedAt(String listenedAt) {
		this.listenedAtUserId = listenedAt;
	}
	public String getTrackBy() {
		return trackByArtistName;
	}
	public void setTrackBy(String trackBy) {
		this.trackByArtistName = trackBy;
	}
	public String getTrackByArtistId() {
		return trackByArtistId;
	}
	public void setTrackByArtistId(String trackByArtistId) {
		this.trackByArtistId = trackByArtistId;
	}
	public String getListenedBy() {
		return listenedBy;
	}
	public void setListenedBy(String listenedBy) {
		this.listenedBy = listenedBy;
	}
	String listenedBy;
	public String getTrackId() {
		return trackId;
	}
	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}
	public String getTrackName() {
		return trackName;
	}
	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}
	

}
