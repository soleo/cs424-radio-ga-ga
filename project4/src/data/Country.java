package data;

import java.io.Serializable;
import java.util.Arrays;

public class Country implements Serializable{
	public String name;
	public String isoCode;
	public String timeZoneOffset;
	
	String hourly_count;
	String age_group_count;
	
	int totalListeners;
	int maleListeners;
	int femaleListeners;
	int unknownListeners;
	
	public Country()
	{
		hourly_count = new String();
		age_group_count = new String();
	}
	
	public int getUnknownListeners() {
		return unknownListeners;
	}
	public void setUnknownListeners(int unknownListeners) {
		this.unknownListeners = unknownListeners;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIsoCode() {
		return isoCode;
	}
	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}
	public String getTimeZoneOffset() {
		return timeZoneOffset;
	}
	public void setTimeZoneOffset(String timeZoneOffset) {
		this.timeZoneOffset = timeZoneOffset;
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
	
	/**
	 * 
	 * @param hour : hour of day from 0 to 23
	 */
	public int getHourlyPlayCount(int hour)
	{
		if(hourly_count.length() == 0) return 0;
		
		return Integer.parseInt((hourly_count.split(","))[hour]);
	}
	
	/**
	 * 
	 * @param hour : hour from 0 to 23
	 * @param count : integer, added to previous list
	 */
	public void addtoHourlyPlayCount(int hour, int count)
	{
		String[] hrs = hourly_count.split(",");
		int c = Integer.parseInt(hrs[hour]);
		c += count;
		hrs[hour] = String.valueOf(c);
		hourly_count = new String(Arrays.toString(hrs));
	}
	
	/**
	 * 
	 * @param hourly_count : a comma separated 0 to 23 hour's paly count
	 */
	public void setHourlyCount(String hourlyCount)
	{
		this.hourly_count = new String(hourlyCount);
	}/**
	 * 
	 * @param age : age groups start at 13, 19, 25, 36, 46, 65
	 */
	public int getAgeGroupCount(int age)
	{
		if(age_group_count.length() == 0) return 0;
		
		switch(age)
		{
		case 13: return Integer.parseInt((age_group_count.split(","))[0]);
		case 19: return Integer.parseInt((age_group_count.split(","))[1]);
		case 25: return Integer.parseInt((age_group_count.split(","))[2]);
		case 36: return Integer.parseInt((age_group_count.split(","))[3]);
		case 46: return Integer.parseInt((age_group_count.split(","))[4]);
		case 65: return Integer.parseInt((age_group_count.split(","))[5]);
		}
		return 0;
	}
	
	/**
	 * 
	 * @param age_group_count : a comma separated counts for age-groups starting at 13, 19, 25, 36, 46, 65
	 */
	public void setAgeGroupCount(String age_group_count)
	{
		this.age_group_count = new String(age_group_count);
	}
}
