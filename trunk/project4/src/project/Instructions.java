package project;
import processing.core.PFont;
public class Instructions {
	PFont font;
	PFont font2;
	public Instructions(){
		font = Utils.globalProcessing.loadFont("Helvetica-Light-14.vlw"); 
		font2 = Utils.globalProcessing.loadFont("Helvetica-Light-18.vlw");
	}
	
	void drawContent(){
		
		// background
		Utils.globalProcessing.fill(100,149,237);
		Utils.globalProcessing.rect(0,0,824,Utils.globalProcessing.getHeight());
		//text box
		Utils.globalProcessing.fill(0,50,140,30);
		Utils.globalProcessing.rect(80,80,824 - 160, Utils.globalProcessing.getHeight() - 160);
		
		Utils.globalProcessing.textAlign(Utils.globalProcessing.CENTER);
		Utils.globalProcessing.textFont(font2); 
		Utils.globalProcessing.fill(255,255,255,200);
		String s1 = "Instructions on How to Use the Last.fm User and Artist Vizualization\n\n\n";
		Utils.globalProcessing.text(s1,120, 120, 824 - 240, 40);
		Utils.globalProcessing.textFont(font);
		String s2 = 
				"Map View\n\n" +
				"The Map View displays a world wide clickable map.  By clicking on a country you can view " +
				"the corresponding age and gender distribution for the selected country.  It will also display" +
				"a graph which shows the time of day in which the users are most active on Last.fm\n\n\n"+
				"Comparison View\n\n" +
				"The Comparison View allows you to compare top artists amongst different filters including age, gender, and nationality.  The artist specific information is displayed at the bottom.\n\n"+
				"Project By\n\n" +
				"Kaiser Asif\nVivek Hariharan\nJenny Kinahan\nXinjiang Shao\n";
				
		Utils.globalProcessing.text(s2, 120, 160, 824-240, Utils.globalProcessing.getHeight() - 240);
		Utils.globalProcessing.textAlign(Utils.globalProcessing.LEFT);
		Utils.globalProcessing.fill(255);
	}
}
