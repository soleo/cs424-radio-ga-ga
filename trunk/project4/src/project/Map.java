package project;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import data.Country;
import data.DataClass;

import processing.core.PGraphics;
import processing.core.PShape;

public class Map { 
	
	PShape myshape;
	PShape piece;
	String[] data;
	ArrayList<PShape> theStates;
	ArrayList<String> isoCodeForShape;
	int counter; 
	int theX;
	int theY;
	DataClass dataClass;
	int width = 824;
	int height = 440;
	BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE  );
	
	
	public Map(DataClass d){
		dataClass=d;
		theStates=new ArrayList<PShape>();
		isoCodeForShape = new ArrayList<String>();
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
		    	isoCodeForShape.add(new String(isoCode));
		    }
		    
		  }
		//theStates = new PShape[238];
		  PGraphics pg = Utils.globalProcessing.createGraphics(width, height, Utils.globalProcessing.JAVA2D);
		  pg.beginDraw();
		  pg.background(0);
		  for(int i=0;i<theStates.size();i++)
			{
				PShape currentShape=theStates.get(i);
				pg.fill(i+1);//,i,i);
				currentShape.disableStyle();
				currentShape.scale(.3f,.3f);
				pg.shape(currentShape);
				currentShape.scale(3.333f,3.333f);
			}
		  pg.endDraw();
		  Graphics2D g2d = img.createGraphics();
		  g2d.drawImage((java.awt.Image)pg.image, 0, 0, width, height, Utils.globalProcessing);
		  g2d.finalize();
		  g2d.dispose();
		  //File f = new File("myimage.png");
		  //try{ImageIO.write(img, "png", f);}catch(Exception e){}

		counter = 0;
	}
	
	public void setDataClass(DataClass d)
	{
		this.dataClass=d;
	}
	void drawContent(){
		//Utils.globalProcessing.background(0,42,192,190);
		Utils.globalProcessing.background(255);
		myshape.disableStyle();
		Utils.globalProcessing.fill(0);
		Utils.globalProcessing.stroke(0);
		long maxListeners=dataClass.getMaxListeners();
		
			for(int i=0;i<theStates.size();i++)
			{
				PShape currentShape=theStates.get(i);
				//Utils.globalProcessing.fill(0,205,0,240);
				float scaledColorValue;
				if(dataClass.isCountryCodePresent(currentShape.getName()))
				{
					Country currentCountry=dataClass.getCountryByCode(currentShape.getName());
					long listeners=currentCountry.getTotalListeners();
					
					//scaledColorValue=Utils.globalProcessing.map(listeners,0,maxListeners,255,100);
					float normalizedListeners=Utils.globalProcessing.norm(listeners,0,255);
					
					scaledColorValue=Utils.globalProcessing.lerpColor( 0xF62817,0x000000 , listeners);
					System.out.println(scaledColorValue);
					
				}
				else
				{
					scaledColorValue=0;
				}
				Utils.globalProcessing.fill(scaledColorValue);
				currentShape.disableStyle();
				currentShape.scale(.3f,.3f);
				Utils.globalProcessing.shape(currentShape);
				currentShape.scale(3.333f,3.333f);
			}

		    Utils.globalProcessing.fill(0,0,0,128);
		    Utils.globalProcessing.rect(0,440, Utils.globalProcessing.getWidth() - 200,Utils.globalProcessing.getHeight() - 440);
		    int[] hourlyData=dataClass.getHourlyListenCount();
		    int[] tempData=hourlyData;
		    Arrays.sort(tempData);
		    int minData=tempData[0];
		    int maxData=tempData[tempData.length-1]+400;
		    Utils.globalProcessing.fill(0,205,0,240);
		    Utils.globalProcessing.stroke(25);
		    Utils.globalProcessing.strokeWeight(1);
		    
		    
		    Utils.globalProcessing.textSize(13);
		    //Utils.globalProcessing.textAlign(Utils.globalProcessing.CENTER,Utils.globalProcessing.CENTER);
		    Utils.globalProcessing.text("Number\nof\nlisteners",20,(450+720)/2);
		    Utils.globalProcessing.fill(255);
		    Utils.globalProcessing.rect(40, 450, 780, 320);
		    Utils.globalProcessing.fill(0,205,0,240);
		    Utils.globalProcessing.strokeWeight(2);
		    Utils.globalProcessing.beginShape();
		    Utils.globalProcessing.noFill();
		    for(int i=0;i<hourlyData.length;i++)
		    {
		    	long count=hourlyData[i];
		    	float pointY=Utils.globalProcessing.map(count,0,maxData,Utils.globalProcessing.getHeight()-45,455);
		    	float pointX=Utils.globalProcessing.map(i, 0, hourlyData.length-1, 45, 795);
		    	//Utils.globalProcessing.point(pointX,pointY);
		    	Utils.globalProcessing.vertex(pointX,pointY);
		    }
		    Utils.globalProcessing.endShape();
		    Utils.globalProcessing.fill(0,0,0,128);
		    Utils.globalProcessing.strokeWeight(1);
		  
	}
	void mouseClicked(){
		int mouseX = Utils.globalProcessing.mouseX, mouseY= Utils.globalProcessing.mouseY;
		
		if(mouseX<=824 && mouseY<=440)
		{
			
			System.out.print("img " + (0xff & img.getRGB(mouseX, mouseY)) + " at " + mouseX + ","+ mouseY+"\n");
			   
			try {
				int index = (0xff & img.getRGB(mouseX, mouseY)); // 0 is black, for unshaped map location
				if(index > 0)
				{
					int state_number = index - 1;
					System.out.println(" " +isoCodeForShape.get(state_number));	
				}
			}catch(IndexOutOfBoundsException outboundException)
			{
				System.out.println(outboundException.toString());
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