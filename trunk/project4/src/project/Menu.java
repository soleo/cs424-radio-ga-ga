package project;
import processing.core.PFont;
import controlP5.Textfield;
import processing.core.PImage;

public class Menu {
		  float wdth;
		  PFont font;
		  PImage lastFmImage;
		  Textfield myTextfieldAge;
		  Textfield myTextfieldCty;

		public Menu(float w)
		  {
			wdth = w;
			setup();
		  }
		
		public void setup(){
			
			
			Utils.controlP5.addBang("How to Use",(int)(Utils.globalProcessing.getWidth() - wdth + 20), 40+0*60,40,40).setId(0);
			Utils.controlP5.addBang("Map View",(int)(Utils.globalProcessing.getWidth() - wdth + 20), 40+1*60,40,40).setId(3);
			Utils.controlP5.addBang("Comparison Graph",(int)(Utils.globalProcessing.getWidth() - wdth + 20), 40+2*60,40,40).setId(4);
			lastFmImage = Utils.globalProcessing.loadImage("Last_FM_1.jpg");
			
		    Utils.controlP5.addBang("RESET",(int)(Utils.globalProcessing.getWidth() - wdth + 20), 600,40,40).setId(5);
		  }
		
		public void drawContent(){
			Utils.globalProcessing.fill(0,0,0);
			Utils.globalProcessing.rect(Utils.globalProcessing.getWidth() - wdth, 0, wdth, Utils.globalProcessing.getHeight());
			lastFmImage.resize(0,110);
			Utils.globalProcessing.image(lastFmImage, (Utils.globalProcessing.getWidth() - wdth + 20), 350);

		}

		
}