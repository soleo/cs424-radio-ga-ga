package project;

import java.util.ArrayList;
import java.util.Arrays;

import processing.core.PApplet;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import data.ArtistDetails;
import data.DataClass;


public class MainSketch extends PApplet {
	
Menu theMenu;
Map theMap;
Instructions theInstructions;
Boolean mapOpened = false;
Boolean mapClickable = false;
DataClass d;
//for Comparison View
boolean comparisonViewSelected;
PieChart_ p1, p2, p3, p4, p5, p6;
ListTable lt1, lt2;
boolean startScreen = true;
boolean loadData = true;

boolean artist1_age_selected,artist1_gender_selected,artist1_nation_selected;
boolean artist2_age_selected,artist2_gender_selected,artist2_nation_selected;
boolean artist1_selected, artist2_selected;
int artist1_index,artist2_index;
int artist1_age_index, artist1_gender_index, artist1_nation_index;
int artist2_age_index, artist2_gender_index, artist2_nation_index;
InfoBox tip1,tip2;
static ArrayList<ArtistDetails> upToDateList1,upToDateList2;
// for weekly top artist
boolean howToUseSelected;
boolean weeklyTopArtistSelected;
	public void setup(){
		
		
		theMap=null;
		Utils.globalProcessing = this;
		Utils.globalProcessing.background(0);
		Utils.globalProcessing.size(1024, 768);
		Utils.globalProcessing.smooth();
		Utils.controlP5 = new ControlP5(this);
		theInstructions = new Instructions();
		theMenu = new Menu(200);	
		theMenu.drawContent();
		theInstructions.drawContent();

		

		
	}
	void setupWeeklyTopArtist(){
		weeklyTopArtistSelected =  false;
	}
	void setupComparisonView(){
		// comparison setup
		  comparisonViewSelected = false;
		  float[] data1 = {25,75};
		  float[] data2 = {50,10,40};
		  float[] data3 = {10,10,10,10,10,10,10,30};
		  String[] l1 = {"Age13", "Age2"};
		  String[] l2 = {"Male", "Female", "Unknown"};
		  String[] l3 = {"AC", "SD", "DD","US","CH","TH","AS","NO"};

		  
		  initBool();
		  String[] header1 = {"c1", "c2", "c3"};
		  lt1 = new ListTable(this, Utils.controlP5, header1, 20,50, 1,d);
		  lt1.addRow(header1);
		  lt1.addRow(header1);
		  
		  String[] header2 = {"ss1", "ss2", "ss3"};
		  lt2 = new ListTable(this, Utils.controlP5, header2, 430,50, 2,d);
		  lt2.addRow(header2);
		  lt2.addRow(header2);
		  
		  p1 = new PieChart_(this,10,510,100,100);
		  p1.loadData(data1);
		  p1.setLegend(l1);
		  p2 = new PieChart_(this,140,510,100,100);
		  p2.loadData(data2);
		  p2.setLegend(l2);
		  p3 = new PieChart_(this,260,510,100,100);
		  p3.loadData(data3);
		  p3.setLegend(l3);

		  p4 = new PieChart_(this,430,510,100,100);
		  p4.loadData(data1);
		  p4.setLegend(l1);
		  p5 = new PieChart_(this,560,510,100,100);
		  p5.loadData(data2);
		  p5.setLegend(l2);
		  p6 = new PieChart_(this,690,510,100,100);
		  p6.loadData(data3);
		  p6.setLegend(l3);
		  
		  tip1 = new InfoBox(this,20,660);
		  tip2 = new InfoBox(this,430,660);
	}
	void initBool()
	{
	  artist1_age_selected = false;
	  artist1_gender_selected = false;
	  artist1_nation_selected = false;
	  
	  artist2_age_selected = false;
	  artist2_gender_selected = false;
	  artist2_nation_selected = false;
	  
	  artist1_selected = false;
	  artist2_selected = false;
	  
	  artist1_index  = -1;
	  artist1_age_index = -1;
	  artist1_gender_index = -1;
	  artist1_nation_index = -1;
	  
	  artist2_index  = -1;
	  artist2_age_index = -1;
	  artist2_gender_index = -1;
	  artist2_nation_index = -1;
	}
	void checkBool()
	{
//	  if(artist1_age_selected || artist1_gender_selected || artist1_nation_selected)
//	  {
//	    // update top artist list
//	    if(!artist1_age_selected)
//	    	artist1_age_index = 0;
//	    if(!artist1_gender_selected)
//	    	artist1_gender_index = 0;
//	    if(!artist1_nation_selected)
//	    	artist1_nation_index = 0;
//		  //lt1.updateList(artist1_age_index, artist1_gender_index, artist1_nation_index);
//	  }
//	  
//	  if(artist2_age_selected || artist2_gender_selected || artist2_nation_selected)
//	  {
//	    // update top artist list
//	    //lt2.updateList();
//	  }
	  
	  if(artist1_selected)
	  {
	    // update the user breakdown informaiton
	    p1.updatePieChart(artist1_index,upToDateList1,0);
	    p2.updatePieChart(artist1_index,upToDateList1,1);
	    p3.updatePieChart(artist1_index,upToDateList1,2);
	    
	    // update info box from musicbrainz and last.fm api
	    String[] s = d.getSimilarArtist(upToDateList1.get(artist1_index).getArtistName());
	    String Mesg = "Name:"+upToDateList1.get(artist1_index).getArtistName()+"\n"+"BirthDate: ";
	    if(upToDateList1.get(artist1_index).getBirthDate().isEmpty())
	    	Mesg += "Unknown\n";
	    else
	    	Mesg += upToDateList1.get(artist1_index).getBirthDate()+"\n";
	    if(upToDateList1.get(artist1_index).getCountry().isEmpty())
	    	Mesg += "Country: Unknown\n";
	    else
	    	Mesg += "Country: "+upToDateList1.get(artist1_index).getCountry()+"\n";
	    if(upToDateList1.get(artist1_index).getGender().isEmpty())
	    	Mesg += "Gender: Unknown\n";
	    else
	    	Mesg += "Gender: "+upToDateList1.get(artist1_index).getGender()+"\n";
	    
	    Mesg += "Similar Arsist: " + Arrays.toString(s) + "\n";

	    			  
	    tip1.updateInfo(Mesg);
	    artist1_selected = false;
	  }
	  
	  if(artist2_selected)
	  {
	    p4.updatePieChart(artist2_index,upToDateList2,0);
	    p5.updatePieChart(artist2_index,upToDateList2,1);
	    p6.updatePieChart(artist2_index,upToDateList2,2);
	    
	    String[] s = d.getSimilarArtist(upToDateList2.get(artist2_index).getArtistName());
	    
	    String Mesg = "Name:"+upToDateList2.get(artist2_index).getArtistName()+"\n"+"BirthDate: ";
	    if(upToDateList2.get(artist2_index).getBirthDate().isEmpty())
	    	Mesg += "Unknown\n";
	    else
	    	Mesg += upToDateList2.get(artist2_index).getBirthDate()+"\n";
	    if(upToDateList2.get(artist2_index).getCountry().isEmpty())
	    	Mesg += "Country: Unknown\n";
	    else
	    	Mesg += "Country: "+upToDateList2.get(artist2_index).getCountry()+"\n";
	    if(upToDateList2.get(artist2_index).getGender().isEmpty())
	    	Mesg += "Gender: Unknown\n";
	    else
	    	Mesg += "Gender: "+upToDateList2.get(artist2_index).getGender()+"\n";
	    Mesg += "Similar Arsist: " + Arrays.toString(s) + "\n";
	    tip2.updateInfo(Mesg);
	    artist2_selected = false;
	  }
	
	}
	public void draw(){
		if (loadData){
			d=new DataClass();
			System.out.println("setting up");
		theMap = new Map(d);
		theMap.setDataClass(d);		
		setupComparisonView();
		
		setupWeeklyTopArtist();
		loadData = false;}
		
		theMenu.drawContent();
		
		
		if ((howToUseSelected)||(startScreen)){
			
			theInstructions.drawContent();
		}
		if (weeklyTopArtistSelected){
			lt1.hide();
			lt2.hide();
			fill(255);
			rect(0,0,824,768);
			fill(0);
			rect(0,0,824,768);
			
			
			
		}
		if (comparisonViewSelected){
			  fill(255);
			  rect(0,0,824,768);
			  fill(0);
			  rect(0,0,822,768);
			  //background(0);
			  checkBool();
			  lt1.show();
			  lt2.show();
			  p1.show();
			  p2.show();
			  p3.show();
			  p4.show();
			  p5.show();
			  p6.show();
			 
			  tip1.show();
			  tip2.show();
			}
			else{
				
			  lt1.hide();
			  lt2.hide();
			}
	}
	public void mouseClicked(){
		if(mapOpened)
		{	
			if (mapClickable){
				theMap.mouseClicked();
			}
			mapClickable = true;
		}
		if(comparisonViewSelected){
			
			p1.mouseClicked();
			p2.mouseClicked();
			p3.mouseClicked();
			p4.mouseClicked();
			p5.mouseClicked();
			p6.mouseClicked();
			
			tip1.mouseClicked();
			tip2.mouseClicked();
		}
		
	}
	public void controlEvent(ControlEvent theEvent) {
		 if(theEvent.isController()){
			 if(theEvent.controller().name().equals("Map View")){
					System.out.println("yay");
					if(comparisonViewSelected)
						comparisonViewSelected = false;
					weeklyTopArtistSelected = false;
					howToUseSelected = false;
					startScreen = false;
					try
					{
						lt1.hide();
						lt2.hide();
						theMap.drawContent();
						mapOpened = true;	
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					 
				 }
				 else if(theEvent.controller().name().equals("Comparison Graph") == true){
					 System.out.println("CompareView");
					 System.out.println("comparsion:"+comparisonViewSelected);
					 comparisonViewSelected = true;	 
					 weeklyTopArtistSelected = false;
					 howToUseSelected = false;
					 startScreen = false;
				 }
				 else if(theEvent.controller().name().equals("Top Artists of the Week") == true){
					 System.out.println("Top Artists of the Week");
					 weeklyTopArtistSelected = true;
					 comparisonViewSelected = false;
					 howToUseSelected = false;
					 startScreen = false;
				 }
				 else if(theEvent.controller().name().equals("How to Use") == true){
					 System.out.println("How to Use");
					 weeklyTopArtistSelected = false;
					 comparisonViewSelected = false;
					 howToUseSelected = true;
					 startScreen = false;
					 
				 }
		 }
		 
		 
	 if(comparisonViewSelected){
	   	if(theEvent.isGroup())
				 {
				    String name = theEvent.group().name();
				    System.out.println("Event Name: " + name);
				    System.out.println("Value?: "+ theEvent.group().value());
				    System.out.println("Group?: " + theEvent.group());
				    if(name.equals("ages1") == true){
				      artist1_age_selected = true;
				      artist1_age_index = (int)(theEvent.group().value());
				      System.out.println("age:"+artist1_age_selected+"\tgender:"+
				      artist1_gender_selected+"\tnation:"+
				      artist1_nation_selected+"\tindex:"+
				      theEvent.group().value());
				    }
				    
				    if(name.equals("gender1") == true){
				      artist1_gender_selected = true;
				      artist1_gender_index = (int)(theEvent.group().value());
				      System.out.println("age:"+artist1_age_selected+"\tgender:"+
				      artist1_gender_selected+"\tnation:"+
				      artist1_nation_selected+"\tindex:"+
				      theEvent.group().value());
				    }
				    
				    if(name.equals("nationality1") == true){
				      artist1_nation_selected = true;
				      artist1_nation_index =  (int)(theEvent.group().value());
				      println("age:"+artist1_age_selected+"\tgender:"+
				      artist1_gender_selected+"\tnation:"+
				      artist1_nation_selected+"\tindex:"+
				      theEvent.group().value());
				    }
				    
				    if(name.equals("ages2") == true){
				      artist2_age_selected = true;
				      artist2_age_index = (int)(theEvent.group().value());
				      println("age:"+artist2_age_selected+"\tgender:"+artist2_gender_selected+"\tnation:"+artist2_nation_selected);
				    }
				    
				    if(name.equals("gender2") == true){
				      artist2_gender_selected = true;
				      artist2_gender_index = (int)(theEvent.group().value());
				     println("age:"+artist2_age_selected+"\tgender:"+artist2_gender_selected+"\tnation:"+artist2_nation_selected);
				    }
				    
				    if(name.equals("nationality2") == true){
				      artist2_nation_selected = true;
				      artist2_nation_index =  (int)(theEvent.group().value());
				      println("age:"+artist2_age_selected+"\tgender:"+artist2_gender_selected+"\tnation:"+artist2_nation_selected);
				    }
				    
				    if(name.equals("ArtistList1") == true)
				    {
				      artist1_selected = true;
				      artist1_index = (int)theEvent.group().value();
				      upToDateList1=lt1.getArtistList();
				      println("index of artist1:"+theEvent.group().value());
				    }
				    
				    if(name.equals("ArtistList2") == true)
				    {
				      artist2_selected = true;
				      artist2_index = (int)theEvent.group().value();
				      upToDateList2=lt2.getArtistList();
				      println("index of artist2:"+theEvent.group().value());
				    }
				 }
		  }
	 

	}
 	public void submit1(int theValue) {
 		
 		try
 		{
 			println("a button event from buttonA: "+theValue);
 			  if(artist1_age_selected && artist1_gender_selected && artist1_nation_selected)
 			  {
 			    // update top artist list
 				lt1.updateList(artist1_age_index, artist1_gender_index, artist1_nation_index);
 				
 			  }

 		}
 		catch (Exception e)
 		{
 			e.printStackTrace();
 		}
	}
 	public void submit2(int theValue) {
 		
 		try
 		{
 			println("a button event from buttonA: "+theValue);
 			  if(artist2_age_selected && artist2_gender_selected && artist2_nation_selected)
 			  {
 			    // update top artist list
 				lt2.updateList(artist2_age_index, artist2_gender_index, artist2_nation_index);
 			  }

 		}
 		catch (Exception e)
 		{
 			e.printStackTrace();
 		}
	}	

}