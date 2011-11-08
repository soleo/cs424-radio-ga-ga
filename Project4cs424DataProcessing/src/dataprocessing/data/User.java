package dataprocessing.data;

import java.util.ArrayList;
import java.util.Date;

public class User {
	
	String userId;
	char gender;
	int age;
	String country;
	Date memberSince;
	ArrayList<Artist> listensToArtists=new ArrayList<Artist>();
	
	public void addArtist(Artist artist)
	{
		listensToArtists.add(artist);
	}
	public ArrayList<Artist> getListensToArtists()
	{
		return listensToArtists;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Date getMemberSince() {
		return memberSince;
	}
	public void setMemberSince(Date memberSince) {
		this.memberSince = memberSince;
	}
	

}
