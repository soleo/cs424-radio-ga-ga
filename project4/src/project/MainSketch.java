package project;

import controlP5.*;
import data.DataClass;
import processing.core.*;


public class MainSketch extends PApplet {
	
Menu theMenu;
Map theMap;
Boolean mapOpened = false;
Boolean mapClickable = false;
DataClass d;

	
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
	}
	public void draw(){
		theMenu.drawContent();
	}
	public void mouseClicked(){
		if(mapOpened)
		{	
			if (mapClickable){
				theMap.mouseClicked();
			}
			mapClickable = true;
		}
	}
	public void controlEvent(ControlEvent theEvent) {
		 
		 if(theEvent.controller().name().equals("Map View")){
			System.out.print("yay");
			try
			{
				
				 theMap.drawContent();
				 mapOpened = true;	
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			 
		 }
	}
		
	
}