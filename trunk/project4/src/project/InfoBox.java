package project;

import org.gicentre.utils.gui.Tooltip;

import processing.core.PFont;
import processing.core.*;

//Information Box

class InfoBox
{
  float BoxWidth;
  float BoxHeight;
  PFont font;
  String DetailedMessage;
  int curX;
  int curY; 
  Tooltip tooltip;
  processing.core.PApplet parent;
  
  InfoBox(processing.core.PApplet parent, float x_, float y_)
  {
    font = parent.createFont("Helvetica-Light-14", 14);
    tooltip = new Tooltip(parent, font, 14, 350);
    
    int c = parent.color(0,0,0,200);
    curX = (int)x_;
    curY = (int)y_;
    tooltip.setIsCurved(true);
    tooltip.setBackgroundColour(c);
    tooltip.setTextColour(255);
    tooltip.setText("Artist Information\n"+
                    "Birthday: unknown\n"+
                    "Shape: unknown\n"+
                    "Location: unknown\n"+
                    "State: unknown\n"+
                    "Summary\n"+ 
                    "unknown");
  }

  
  void show()
  {   
   tooltip.draw(curX,curY);
  }
  
  public void updateInfo(String DetailedMessage)
  {
    tooltip.setText(DetailedMessage);
  }
  
  public void mouseClicked()
  {
    //println(mouseX+" "+mouseY);
    
    // get a list of postions on current map, and find the nearest sighting and show the detailed information on map.
   
    tooltip.setIsActive(true);
    
    // click on the box area to close the box
    if (parent.mouseX>30 && parent.mouseY>600 && parent.mouseX<430 && parent.mouseY<680)
    {
      tooltip.setIsActive(false);
    }
  }

  
}