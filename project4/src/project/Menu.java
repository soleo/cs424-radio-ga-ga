package project;
import processing.core.PFont;
import controlP5.Textfield;

public class Menu {
		  float wdth;
		  PFont font;
		  Textfield myTextfieldAge;
		  Textfield myTextfieldCty;

		public Menu(float w)
		  {
			wdth = w;
			setup();
		  }
		
		public void setup(){
			Utils.controlP5.addBang("Overall",(int)(Utils.globalProcessing.getWidth() - wdth + 20), 40+0*60,40,40).setId(0);
			Utils.controlP5.addBang("Similar Artists",(int)(Utils.globalProcessing.getWidth() - wdth + 20), 40+1*60,40,40).setId(1);
			Utils.controlP5.addBang("Map View",(int)(Utils.globalProcessing.getWidth() - wdth + 20), 40+2*60,40,40).setId(2);
			Utils.controlP5.addBang("Top Artists of the Week",(int)(Utils.globalProcessing.getWidth() - wdth + 20), 40+3*60,40,40).setId(3);
			Utils.controlP5.addBang("Comparison Graph",(int)(Utils.globalProcessing.getWidth() - wdth + 20), 40+4*60,40,40).setId(4);
			
			myTextfieldAge = Utils.controlP5.addTextfield("AGE",(int)(Utils.globalProcessing.getWidth() - wdth + 20),400,140,20);
			myTextfieldAge.setFocus(true);
			myTextfieldCty = Utils.controlP5.addTextfield("COUNTRY",(int)(Utils.globalProcessing.getWidth() - wdth + 20),440,140,20);
			myTextfieldCty.setFocus(false);
			Utils.controlP5.addToggle("MALE",true,(int)(Utils.globalProcessing.getWidth() - wdth + 20),500,20,20);
			Utils.controlP5.addToggle("FEMALE",true,(int)(Utils.globalProcessing.getWidth() - wdth + 60),500,20,20);
			//reset button
		    Utils.controlP5.addBang("RESET",(int)(Utils.globalProcessing.getWidth() - wdth + 20), 600,40,40).setId(5);
		  }
		
		public void drawContent(){
			Utils.globalProcessing.fill(0,0,0,128);
			Utils.globalProcessing.rect(Utils.globalProcessing.getWidth() - wdth, 0, wdth, Utils.globalProcessing.getHeight());
		  }

		
}