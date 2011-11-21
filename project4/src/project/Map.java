package project;
import java.util.ArrayList;

import processing.core.PShape;

public class Map { 
	
	PShape myshape;
	PShape piece;
	String[] data;
	ArrayList<PShape> theStates;
	int counter; 
	int theX;
	int theY;
	
	
	public Map(){
		theStates=new ArrayList<PShape>();
		data = Utils.globalProcessing.loadStrings("country.tsv");
		myshape = Utils.globalProcessing.loadShape("map.svg");
		System.out.print(myshape.getChildCount());
		int rowCount = data.length;
		  for (int row = 0; row < rowCount; row++) {
		    String abbrev = data[row];
		    String abbrevParts[]=Utils.globalProcessing.split(abbrev,'\t');
		    String isoCode=abbrevParts[0].trim().toLowerCase();
		    PShape state = myshape.getChild(isoCode);
		    if(state!=null)
		    {
		    	theStates.add(state);	
		    }
		    
		  }
		//theStates = new PShape[238];
		counter = 0;
	}
	
	void drawContent(){
		Utils.globalProcessing.background(0,42,192,190);
		myshape.disableStyle();
		Utils.globalProcessing.fill(0);
		Utils.globalProcessing.stroke(0);
		//Utils.globalProcessing.shape(myshape);
		//Utils.globalProcessing.stroke(0);
//		int rowCount = data.length;
//		  for (int row = 0; row < rowCount; row++) {
//		    String abbrev = data[row];
//		    String abbrevParts[]=Utils.globalProcessing.split(abbrev,'\t');
//		    String isoCode=abbrevParts[0].trim().toLowerCase();
//		    PShape state = myshape.getChild(isoCode);
//		    if (state!= null) {
//		    		theStates[counter] = state;
//		    		Utils.globalProcessing.fill(0,205,0,240);
//			        myshape.disableStyle();
//			        state.scale(.3f,.3f);
//			        System.out.println(theStates[counter].getName());
//			        Utils.globalProcessing.shape(state);
//			        counter++;
//		    }       
		    //System.out.print(counter);
		
			for(int i=0;i<theStates.size();i++)
			{
				PShape currentShape=theStates.get(i);
				Utils.globalProcessing.fill(0,205,0,240);
				currentShape.disableStyle();
				currentShape.scale(.3f,.3f);
				Utils.globalProcessing.shape(currentShape);
				currentShape.scale(3.333f,3.333f);
			}

		    Utils.globalProcessing.fill(0,0,0,128);
		    Utils.globalProcessing.rect(0,440, Utils.globalProcessing.getWidth() - 200,Utils.globalProcessing.getHeight() - 440);
		  
	}
	void mouseClicked(){
		   theX = Utils.globalProcessing.getX();
		   theY = Utils.globalProcessing.getY();
		   System.out.println(theX + theY);
//		   for (int j = 0; j < theStates.length; j++){
//			   theStates[j].getParams();
//				   System.out.println(theStates[j].getParams());
//			   
//		   }
		   for(int i=0;i<theStates.size();i++)
		   {
			   PShape currentShape=theStates.get(i);
			   //currentShape.contains(theX, theY);
			   if(currentShape.X<=theX && currentShape.X>=theX+currentShape.width && currentShape.Y<=theY && currentShape.Y<=theY+currentShape.height)
			   {
				   currentShape.getParams();
				   System.out.println("clicked");   
			   }
			   
		   }
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