package project;
import processing.core.PShape;

public class Map { 
	
	PShape myshape;
	PShape piece;
	String[] data;
	
	Map(){
		data = Utils.globalProcessing.loadStrings("country.tsv");
		myshape = Utils.globalProcessing.loadShape("map.svg");
		System.out.print(myshape.getChildCount());
	}
	
	void drawContent(){
		Utils.globalProcessing.background(0,42,230,190);
		myshape.disableStyle();
		Utils.globalProcessing.fill(0);
		Utils.globalProcessing.stroke(0);
		//Utils.globalProcessing.shape(myshape);
		//Utils.globalProcessing.stroke(0);
		int rowCount = data.length;
		  for (int row = 0; row < rowCount; row++) {
		    String abbrev = data[row];
		    String abbrevParts[]=Utils.globalProcessing.split(abbrev,'\t');
		    String isoCode=abbrevParts[0].trim().toLowerCase();
		    PShape state = myshape.getChild(isoCode);
		    
		    if (state!= null) {
//		      float value = data.getFloat(row, 1);
//		      if (value >= 0) {
//		        float amt = norm(value, 0, dataMax);
//		        float c = map(amt, 0, 1, 255, 0);
//		        fill(100, c, 100);
		    	Utils.globalProcessing.fill(0,192,30,240);
		        myshape.disableStyle();
		        state.scale(.3f,.3f);
		        Utils.globalProcessing.shape(state);
		        }
		    Utils.globalProcessing.fill(0,0,0,128);
		    Utils.globalProcessing.rect(10,450, Utils.globalProcessing.getWidth() - 220,Utils.globalProcessing.getHeight() - 460);
	/*	for(int i = 4; i<= 230; i++){	
			piece = myshape.getChild(i);
			Utils.globalProcessing.stroke(255);
			piece.disableStyle();
			Utils.globalProcessing.fill(0,40,192);
			Utils.globalProcessing.shape(piece);
		*/}
	}
}

/*
PShape svg;
String[] data;

Map(){
float dataMin = 0;
float dataMax = 50000;
}

public void setup () {
	System.out.print("OKAYYYY");
  svg = Utils.globalProcessing.loadShape("worldmap.svg");
  data = Utils.globalProcessing.loadStrings("country.tsv");
}

public void drawContent() {
  Utils.globalProcessing.smooth();

  int rowCount = data.length;
  for (int row = 0; row < rowCount; row++) {
    String abbrev = data[row];
    String abbrevParts[]=Utils.globalProcessing.split(abbrev,'\t');
    String isoCode=abbrevParts[0].trim().toLowerCase();
    PShape state = svg.getChild(isoCode);
    if (state == null) {
//      println("no state found for " + abbrev);

    } 
    else {
//      float value = data.getFloat(row, 1);
//      if (value >= 0) {
//        float amt = norm(value, 0, dataMax);
//        float c = map(amt, 0, 1, 255, 0);
//        fill(100, c, 100);
    	Utils.globalProcessing.fill(row);
        System.out.println("found" +state);
        svg.disableStyle();
        Utils.globalProcessing.shape(state, 35, 30);
     // }
    }
  }
PShape state_lines = svg.getChild("State_Lines");
PShape separator = svg.getChild("separator");
//  noFill();
//  stroke(255);
//  shape(state_lines, 35, 30);
//  stroke(200);
//  shape(separator, 35, 30);
}
}*/