package project;

import processing.core.*;

public class MainSketch extends PApplet {
	
	public void setup(){
		Utils.globalProcessing = this;
		Utils.globalProcessing.size(1024, 768);
		Utils.globalProcessing.smooth();
	}
}
