package project;

import controlP5.*;
import data.DataClass;
import processing.core.*;
import project.Map;
import project.Menu;
import project.Utils;


public class MainSketch extends PApplet {
	
Menu theMenu;
Map theMap;
Boolean mapOpened = false;
Boolean mapClickable = false;
DataClass d;
//for Comparison View
boolean comparisonViewSelected;
PieChart_ p1, p2, p3, p4, p5, p6;
ListTable lt1, lt2;

boolean artist1_age_selected,artist1_gender_selected,artist1_nation_selected;
boolean artist2_age_selected,artist2_gender_selected,artist2_nation_selected;
boolean artist1_selected, artist2_selected;
int artist1_index,artist2_index;
int artist1_age_index, artist1_gender_index, artist1_nation_index;
int artist2_age_index, artist2_gender_index, artist2_nation_index;
InfoBox tip1,tip2;
	
	public void setup(){
		
		theMap=null;
		Utils.globalProcessing = this;
		Utils.globalProcessing.size(1024, 768);
		Utils.globalProcessing.smooth();
		Utils.controlP5 = new ControlP5(this);
		theMenu = new Menu(200);	
		d=new DataClass();
		theMap = new Map();
		theMap.setDataClass(d);
		
		setupComparisonView();
	}
	
	void setupComparisonView(){
		// comparison setup
		  comparisonViewSelected = false;
		  float[] data1 = {25,75};
		  float[] data2 = {50,10,40};
		  float[] data3 = {10,10,10,10,10,10,10,30};
		  String[] l1 = {"Male", "Female"};
		  String[] l2 = {"Male", "Female", "sdf"};
		  String[] l3 = {"Male", "Female", "sdf","sd","asdf","asdf","asdf","asdf"};

		  
		  initBool();
		  String[] header1 = {"c1", "c2", "c3"};
		  lt1 = new ListTable(this, Utils.controlP5, header1, 20,50, 1);
		  lt1.addRow(header1);
		  lt1.addRow(header1);
		  
		  String[] header2 = {"ss1", "ss2", "ss3"};
		  lt2 = new ListTable(this, Utils.controlP5, header2, 430,50, 2);
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
		  
		  tip1 = new InfoBox(this,20,680);
		  tip2 = new InfoBox(this,430,680);
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
	  if(artist1_age_selected || artist1_gender_selected || artist1_nation_selected)
	  {
	    // update top artist list
	    lt1.updateList();
	  }
	  
	  if(artist2_age_selected || artist2_gender_selected || artist2_nation_selected)
	  {
	    // update top artist list
	    lt2.updateList();
	  }
	  
	  if(artist1_selected)
	  {
	    // update the user breakdown informaiton
	    p1.updatePieChart();
	    p2.updatePieChart();
	    p3.updatePieChart();
	  }
	  
	  if(artist2_selected)
	  {
	    p4.updatePieChart();
	    p5.updatePieChart();
	    p6.updatePieChart();
	  }
	
	}
	public void draw(){
		theMenu.drawContent();
		
		if (comparisonViewSelected){
			  
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
		}
		
	}
	public void controlEvent(ControlEvent theEvent) {
		 if(theEvent.isController()){
			 if(theEvent.controller().name().equals("Map View")){
					System.out.println("yay");
					if(comparisonViewSelected)
						comparisonViewSelected = false;
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
				      println("age:"+artist2_age_selected+"\tgender:"+artist2_gender_selected+"\tnation:"+artist2_nation_selected);
				    }
				    
				    if(name.equals("gender2") == true){
				      artist2_gender_selected = true;
				     println("age:"+artist2_age_selected+"\tgender:"+artist2_gender_selected+"\tnation:"+artist2_nation_selected);
				    }
				    
				    if(name.equals("nationality2") == true){
				      artist2_nation_selected = true;
				      println("age:"+artist2_age_selected+"\tgender:"+artist2_gender_selected+"\tnation:"+artist2_nation_selected);
				    }
				    
				    if(name.equals("ArtistList1") == true)
				    {
				      artist1_selected = true;
				      println("index of artist1:"+theEvent.group().value());
				    }
				    
				    if(name.equals("ArtistList2") == true)
				    {
				      artist2_selected = true;
				      println("index of artist2:"+theEvent.group().value());
				    }
				 }
		  }
	}
		

}