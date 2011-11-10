package dataprocessing.data;


/**
 * Used in User class to store the artists that the user listens to
 * if artistId is not present, id is computed as artistName.toLower().replaceAll(" ","_"); OR get id from musicbrainz(undecided)
 * @author vivek
 *
 */
public class MiniArtist {

	String artistId;
	String artistName;
	int airPlay;
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
	public int getAirPlay() {
		return airPlay;
	}
	public void setAirPlay(int airPlay) {
		this.airPlay = airPlay;
	}

}
