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
	int g; 
	int b;
	PieChart_ pieSex;
	PieChart_ pieAge;
	
	String iso = "";
	
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
				currentShape.scale(1/.3f,1/.3f);
			}
		  pg.endDraw();
		  Graphics2D g2d = img.createGraphics();
		  g2d.drawImage((java.awt.Image)pg.image, 0, 0, width, height, Utils.globalProcessing);
		  g2d.finalize();
		  g2d.dispose();
		  //File f = new File("myimage.png");
		  //try{ImageIO.write(img, "png", f);}catch(Exception e){}

		counter = 0;
		 float[] data1 = {25,75};
		 float[] data2 = {50,10,40};
		  String[] l1 = {"Male", "Female"};
		  String[] l2 = {"Male", "Female", "sdf"};
		  //adding piecharts 
		  pieSex = new PieChart_(Utils.globalProcessing,Utils.globalProcessing.getWidth() - 320,Utils.globalProcessing.getHeight() - 225,100,100);
		  pieSex.loadData(data1);
		  pieSex.setLegend(l1);
		  pieAge = new PieChart_(Utils.globalProcessing,Utils.globalProcessing.getWidth() - 320,Utils.globalProcessing.getHeight() - 115,100,100);
		  pieAge.loadData(data2);
		  pieAge.setLegend(l2);
	}
	
	public void setDataClass(DataClass d)
	{
		this.dataClass=d;
	}
	void drawContent(){
		//Utils.globalProcessing.background(0,42,192,190);
		Utils.globalProcessing.background(20,60,130);
		myshape.disableStyle();
		Utils.globalProcessing.fill(0);
		Utils.globalProcessing.stroke(0);
		long maxListeners=dataClass.getMaxListeners();
		double logMaxListeners=Math.log10(maxListeners);
		
			for(int i=0;i<theStates.size();i++)
			{
				PShape currentShape=theStates.get(i);
				//Utils.globalProcessing.fill(0,205,0,240);
				float scaledColorValue;
				g = 0; 
				b = 0;
				if(dataClass.isCountryCodePresent(currentShape.getName()))
				{
					Country currentCountry=dataClass.getCountryByCode(currentShape.getName());
					long listeners=currentCountry.getTotalListeners();
					double logListeners = Math.log10(listeners);
						
//					scaledColorValue=Utils.globalProcessing.map(listeners,0,maxListeners, 0,180);
//					scaledColorValue = 255 - scaledColorValue;
//					float normalizedListeners=Utils.globalProcessing.norm(listeners,0,255);
					
					scaledColorValue=(float) (255 + logListeners * (0 - 200) / ( logMaxListeners - 0 ) );
					//scaledColorValue = 255 - scaledColorValue;
					
					if (listeners < 1000){
					
						scaledColorValue = 100 + 40 * listeners / 1000;
						g = b = (int)scaledColorValue;
						System.out.print(listeners);
					}
					
					
					System.out.println("Scaled Color Val b4 lerp:"+scaledColorValue + listeners);
					
					//System.out.println("Scaled Color Val b4 lerp:"+scaledColorValue+ ":" +listeners +"/"+ maxListeners);
					//scaledColorValue=Utils.globalProcessing.lerpColor( 0xF62817,0x000000 , listeners);
					
				}
				else
				{
					scaledColorValue=0;
				}
				Utils.globalProcessing.fill(scaledColorValue, g, b);
				currentShape.disableStyle();
				currentShape.scale(.3f,.3f);
				Utils.globalProcessing.shape(currentShape);
				currentShape.scale(1/.3f,1/.3f);
			}
			drawBottom();
	}
			void drawBottom(){

			// Line Graph for play count
				//Utils.globalProcessing.fill(0);
			    //Utils.globalProcessing.rect(0,440, Utils.globalProcessing.getWidth() - 200,Utils.globalProcessing.getHeight() - 440);
			    Utils.globalProcessing.fill(0,0,0,128);
			    Utils.globalProcessing.rect(0,440, Utils.globalProcessing.getWidth() - 200,Utils.globalProcessing.getHeight() - 440);
			    Utils.globalProcessing.textAlign(Utils.globalProcessing.CENTER,Utils.globalProcessing.CENTER);
		    
		    int graphX = 30, graphY = 445, graphWidth = Utils.globalProcessing.getWidth() - 380, graphHeight = Utils.globalProcessing.height - 20 - graphY;
		    
		    //int[] hourlyData=null;//dataClass.getHourlyListenCount();
		    int[] hourlyData=null; // comment out everything from here to the if(hourlyData == null)
            System.out.println(iso);
            String dataOrigin = "whole world";
            if(dataClass.isCountryCodePresent(iso))
            {
                Country c = dataClass.getCountryByCode(iso);
                hourlyData = new int[24];
                int count = 0;
                for(int i=0; i<24; i++)
                {
                    hourlyData[i] = c.getHourlyPlayCount(i);
                    count += hourlyData[i];
                }
                //System.out.println(c.name + " : hourly count : " + Arrays.toString(hourlyData));
                if(count < 100) // just a threshold, 0 should be fine
                {
                	hourlyData = null;
                }
                dataOrigin = (hourlyData == null) ? "whole world" : c.name;
                
            }
           
            if(hourlyData == null)
            {
                hourlyData=dataClass.getHourlyListenCount();
            }
            
		    int[] tempData=(int[])hourlyData.clone();
		    Arrays.sort(tempData);
		    int minData=tempData[0];
		    int maxData=tempData[tempData.length-1];//+400;
		    maxData=((maxData/10000)+1)*10000;
		    
		    Utils.globalProcessing.fill(0,205,0,240);
		    Utils.globalProcessing.stroke(0,205,0);
		    Utils.globalProcessing.strokeWeight(1);
		    
		    
		    Utils.globalProcessing.textSize(13);
		    //Utils.globalProcessing.text("Number\nof\nlisteners",20,(450+720)/2);
		    Utils.globalProcessing.translate(graphX/2-2,graphY+graphHeight/2);
		    Utils.globalProcessing.rotate(-(float)Math.PI/2);
		    Utils.globalProcessing.text("Number of listeners (in thousands)",0,0);
		    Utils.globalProcessing.rotate((float)Math.PI/2);
		    Utils.globalProcessing.translate(-graphX/2+2, -graphY-graphHeight/2);
		    
		    Utils.globalProcessing.fill(20,60,130);
		    Utils.globalProcessing.rect(graphX, graphY, graphWidth, graphHeight);
		    Utils.globalProcessing.fill(0,0,0,196);
		    Utils.globalProcessing.rect(graphX, graphY, graphWidth, graphHeight);
		    Utils.globalProcessing.fill(0,205,0,240);
		    Utils.globalProcessing.strokeWeight(2);
		    Utils.globalProcessing.beginShape();
		    Utils.globalProcessing.noFill();
		    for(int i=0;i<hourlyData.length;i++)
		    {
		    	long count=hourlyData[i];
		    	float pointY=Utils.globalProcessing.map(count,0,maxData,graphY+graphHeight, graphY+5);
		    	float pointX=Utils.globalProcessing.map(i, 0, hourlyData.length-1, graphX+5, graphX+graphWidth-5);
		    	//Utils.globalProcessing.point(pointX,pointY);
		    	//int xoffset = 0;//(i==0)?0:(graphWidth)/48;
		    	Utils.globalProcessing.vertex(pointX,pointY);
		    	//System.out.println("Y : " + pointY + " count: " + count);
		    	if(i==0) pointX += 4;
		    	else if(i==hourlyData.length-1) pointX -= 4; // move the last label in
		    	Utils.globalProcessing.text(""+i,pointX,graphY+graphHeight-9);
		    }
		    Utils.globalProcessing.endShape();
		    Utils.globalProcessing.text("Hours of day",graphX+graphWidth/2,graphY+graphHeight+9);
		    
		    int v = 100000;//((minData/100000)+1)*100000;
		    int legendCount = 10;
		    int i=0, k=maxData/legendCount;
		    for (; k/10 > 0 ; i++) k = k / 10; 
		    int legendStep = k * (int)Math.pow(10, i);
		    for (v=legendStep,i=0 ; v <= maxData; v += legendStep) {
		    	float y = Utils.globalProcessing.map(v, 0, maxData, graphY+graphHeight, graphY+5);
		    	
		    	float textOffset = Utils.globalProcessing.textAscent()/2;  // Center vertically
		            if (v == minData) {
		              textOffset = 0;                   // Align by the bottom
		            } else if (v == maxData) {
		              textOffset = Utils.globalProcessing.textAscent();        // Align by the top
		            }
		            if(i++%2 == 1)Utils.globalProcessing.text(v/1000, graphX + 20, y );//+ textOffset);
		            Utils.globalProcessing.line(graphX , y, graphX+4, y);     // Draw major tick
		    }
		    
		    Utils.globalProcessing.fill(0,205,0,240);
		    Utils.globalProcessing.strokeWeight(1);
		    
		  // end of Line Graph for play count
		  Utils.globalProcessing.textAlign(Utils.globalProcessing.LEFT);

          Utils.globalProcessing.text("Listeners counts for " + dataOrigin , graphX+graphWidth/2+graphWidth/6, graphY+graphHeight - 25);
          
		  pieSex.show();
		  pieAge.show();
	}
	void mouseClicked()
	{
		int mouseX = Utils.globalProcessing.mouseX, mouseY= Utils.globalProcessing.mouseY;
		iso = "";
		if(mouseX<=824 && mouseY<=440)
		{
			
			System.out.print("img " + (0xff & img.getRGB(mouseX, mouseY)) + " at " + mouseX + ","+ mouseY+"\n");
			   
			try {
				int index = (0xff & img.getRGB(mouseX, mouseY)); // 0 is black, for unshaped map location
				if(index > 0)
				{
					int state_number = index - 1;
					//System.out.println(" " +isoCodeForShape.get(state_number));
					iso = isoCodeForShape.get(state_number);
					//pieSex.updatePieChart(null, null, state_number);
					//pieAge.updatePieChart(isoCodeForShape.get(state_number), details, type);
					updatePieChart(iso);
				}
			}catch(IndexOutOfBoundsException outboundException)
			{
				System.out.println(outboundException.toString());
			}	
			drawBottom();
		}
	}
	
	void updatePieChart(String iso)
	{
		Country c = dataClass.getCountryByCode(iso);
		if(c == null || iso =="") return;
		
		float ageCount[] = new float[6];
		float sexCount[] = new float[3];
		String ages[] = {"13-18","19-24","25-35","36-45","46-64","above 65"};
		String sexes[] = {"Male","Female","Unknown"};
		
		ageCount[0] = c.getAgeGroupCount(13);
		ageCount[1] = c.getAgeGroupCount(19);
		ageCount[2] = c.getAgeGroupCount(25);
		ageCount[3] = c.getAgeGroupCount(36);
		ageCount[4] = c.getAgeGroupCount(46);
		ageCount[5] = c.getAgeGroupCount(65);
		
		int total = 0;
		for(int i=0; i <6; i++) total += ageCount[i];
		for(int i=0; i <6; i++) ageCount[i] = ageCount[i] * 100 /total;
		
		sexCount[0] = c.getMaleListeners();
		sexCount[1] = c.getFemaleListeners();
		sexCount[2] = c.getUnknownListeners();
		total = 0;
		for(int i=0; i <3; i++) total += sexCount[i];
		for(int i=0; i <3; i++) sexCount[i] = sexCount[i] * 100 /total;
		
		pieSex.loadData(sexCount);
		pieSex.setLegend(sexes);
		
		pieAge.loadData(ageCount);
		pieAge.setLegend(ages);
		
		pieSex.show();
		pieAge.show();
		
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