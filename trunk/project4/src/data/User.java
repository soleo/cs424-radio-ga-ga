package data;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
	
	String userId;
	String country;
	int age;
	String gender;
	String memberSince;
	
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMemberSince() {
		return memberSince;
	}
	public void setMemberSince(String memberSince) {
		this.memberSince = memberSince;
	}
	ArrayList<String> listensTo=new ArrayList<String>();
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void addArtist(String artistId)
	{
		listensTo.add(artistId);
	}
	public ArrayList<String> listensList()
	{
		return listensTo;
	}

}
