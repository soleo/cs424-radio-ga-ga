package dataprocessing.data;

import java.util.Date;

public class User {
	
	String userId;
	char gender;
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
	int age;
	String country;
	Date memberSince;

}
