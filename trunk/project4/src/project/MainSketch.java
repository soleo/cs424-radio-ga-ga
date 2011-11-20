package project;

import controlP5.ControlP5;
import processing.core.*;
import data.*;
import geomerative.*;

public class MainSketch extends PApplet {
	
Menu theMenu;
	
	/*public void setup(){
		Utils.globalProcessing = this;
		Utils.globalProcessing.size(1024, 768);
		Utils.globalProcessing.smooth();
		Utils.controlP5 = new ControlP5(this);
		theMenu = new Menu(200);	
		DataClass d=new DataClass();
	}
	public void draw(){
		theMenu.drawContent();
		
	}*/
RShape s;
RShape polyshp;
RPoint[] points;


public void setup() {
size(640, 360); 
RG.init(this);
s = RG.loadShape("../../BlankMap-World6.svg");
points = s.getPoints();
for(int i=0; i<points.length; i++){
println("(" +points[i].x + ", " + points[i].y +")");
}
}
public void draw() {
background(255);
polyshp = RG.polygonize(s);
RG.shape(polyshp, width/4, 50); 
}
		
	
}
