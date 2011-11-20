package project;

import controlP5.*;
import data.DataClass;
import processing.core.*;


public class MainSketch extends PApplet {
	
Menu theMenu;
Map theMap;
	
	public void setup(){
		Utils.globalProcessing = this;
		Utils.globalProcessing.size(1024, 768);
		Utils.globalProcessing.smooth();
		Utils.controlP5 = new ControlP5(this);
		theMenu = new Menu(200);	
		DataClass d=new DataClass();
	}
	public void draw(){
		theMenu.drawContent();
	}
	public void mousePressed(){
	   System.out.print(Utils.globalProcessing.getMousePosition());
	}
	public void controlEvent(ControlEvent theEvent) {
		 
		 if(theEvent.controller().name().equals("Map View")){
			System.out.print("yay");
			 theMap = new Map();
			 theMap.drawContent();
		 }
	}
		
	
}