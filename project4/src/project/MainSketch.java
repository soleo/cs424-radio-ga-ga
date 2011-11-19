package project;

import controlP5.ControlP5;
import processing.core.*;


public class MainSketch extends PApplet {
	
Menu theMenu;
	
	public void setup(){
		Utils.globalProcessing = this;
		Utils.globalProcessing.size(1024, 768);
		Utils.globalProcessing.smooth();
		Utils.controlP5 = new ControlP5(this);
		theMenu = new Menu(200);	
	}
	public void draw(){
		theMenu.drawContent();
	}
		
	
}
