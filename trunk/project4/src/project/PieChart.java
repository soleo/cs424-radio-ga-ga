package project;

import org.gicentre.utils.io.*;
import org.gicentre.utils.gui.*;
import org.gicentre.utils.move.*;
import org.gicentre.utils.multisketch.*;
import org.gicentre.utils.stat.*;
import org.gicentre.utils.*;
import org.gicentre.utils.network.*;
import org.gicentre.utils.spatial.*;
import org.gicentre.utils.geom.*;

import processing.core.PFont;
import processing.core.*;


class PieChart_ {
  
  float centerX,centerY;
  float height;
  float diameter;
  float StartingX,StartingY;
  int[] pieColor;
  int categries;
  float[] data;
  float prevAngle, currentAngle;
  boolean setAutomateColor;
  boolean showLegend;
  String[] legends;
  PFont font;
  float[] g_radian;
  float g_start = 0;
  int savedIndex = -1;
  float tipX,tipY;
  Tooltip tooltip;
  processing.core.PApplet parent;
  //Color piesColor = {#000000, #FFFFFF};
  PieChart_(processing.core.PApplet parent,float x_, float y_, float w_, float h_)
  {
    StartingX = x_;
    StartingY = y_;
    centerX= (w_)/2+x_;
    centerY= (h_)/2+y_;
    height = h_;
    setAutomateColor = true;
    showLegend = false;
    diameter = parent.min(w_,h_);
    font = parent.createFont("Helvetica-Light-14", 14);
    tooltip = new Tooltip(parent, font, 14, 120);
    this.parent = parent;
  }
  
  PieChart_(float x_, float y_, float w_, float h_, int amount)
  {
    StartingX = x_;
    StartingY = y_;
    centerX= (w_)/2+x_;
    centerY= (h_)/2+y_;
    categries = amount;
    diameter=parent.min(w_,h_);
  }
  
  void show()
  {
    if (setAutomateColor)
    {
      int[] Colors = {parent.color(153,255,51), 
    		  		  parent.color(255,255,51), 
    		  		  parent.color(255,51,153), 
    		  		  parent.color(153,51,255), 
    		  		  parent.color(51,51,255), 
    		  		  parent.color(51,255,255), 
    		  		  parent.color(0,184,92), 
    		  		  parent.color(204,51,51)};
      pieColor = Colors;
    }
    
    drawPie();
    
    if(showLegend)
      drawLegend();
      
    tooltip.draw(tipX+1,tipY+1);
  }
  
  void hide(){
	  
  }
  void drawPie()
  {
    prevAngle = 1;
    currentAngle = 0;
    for (int index= 0; index < categries; index ++)
    {
      parent.fill(pieColor[index]);
      parent.noStroke();
      //println("data:"+data[index]+"\tcategries:"+categries);
      currentAngle = (data[index]/100)*360;
      //println("angle:"+currentAngle);
      parent.arc(centerX,centerY,diameter,diameter,parent.radians(prevAngle),parent.radians(prevAngle)+parent.radians(currentAngle));
      prevAngle += currentAngle; 
      //currentAngle = 180;
    }
  }
  
  void loadData(float[] data)
  {
    categries = data.length;
    this.data = data;
    g_radian = toRadians(data);
    //println(g_radian);
  }
  
  void drawLegend()
  {
	parent.fill(255);
    parent.noStroke();
    parent.rect(StartingX, StartingY+height, 100,100);
    int textLineHeight = 0;
    for (int index = 0; index < categries; index++)
    {
      parent.fill(pieColor[index]);
      parent.textFont(font);
      parent.textAlign(parent.LEFT);
      textLineHeight += parent.textAscent();
      //smooth();
      parent.text(legends[index], StartingX, StartingY+height+textLineHeight);
    }
    
  }
  
  void setLegend(String[] l)
  {
    legends = l;
    font = parent.createFont("Helvetica-Light-14", 14);
    
  }
  
  void pickColors(int[] c)
  {
    //setAutomateColor = 0;
    pieColor = c;
  }
  
  void diableAutomateColor()
  {
    this.setAutomateColor = false; 
  }
  // from http://www.openprocessing.org/visuals/?visualID=37563
  /*
   * The pie chart is drawn in radians. Given an array of values that are
   * fractions of 100%, return an array of radians that map those values to
   * circle coordinates
   */
  float[] toRadians(float[] xs) {
      int n = xs.length;
      float[] ys = new float[n];
      for (int i = 0; i < n; ++i) { ys[i] = parent.radians(xs[i]/100*360); }
      return ys;
  }
 
  /*
   * Determine if the mouse cursor is currently within the pie chart area
   * Euclidian distance for a circle
   * (x - x_origin)**2 + (y - y_origin)**2 <= radius**2
   * This also returns true for points *on* the circumfrence
   */
  boolean inCircle () {
      float px = parent.pow(parent.mouseX - centerX, 2);
      float py = parent.pow(parent.mouseY - centerY, 2);
      float pr = parent.pow(diameter / 2, 2);
      return (px + py <= pr);
  }
 
  /*
   * Determine if the mouse is currently bound by the angle of the arc defined
   * between the two radian values
   * atan2 returns values from -PI to PI the positive values being the lower
   * hemisphere. Add 2*PI to negative values to compensate for the upper
   * hemisphere
   */
  boolean inArc (float radian1, float radian2) {
      float v = parent.atan2(parent.mouseY - centerY, parent.mouseX - centerX);
      if (v < 0) { v += parent.TWO_PI; }
      return (v >= radian1 && v < radian2);
  }
 
  /*
   * Return the index of the 'slice' that the cursor is currently in.
   * Return -1 if the mouse is not currently in a slice
   */
  int getArcIndex () {
      float theta = g_start;
      if (! inCircle ()) { return -1; }
      for (int i = 0; i < g_radian.length; ++i) {
        //println("i:"+i+"\tg_radian:"+g_radian[i]);
          if (inArc (theta, theta + g_radian[i])) {
              
              return i;
          }
          theta += g_radian[i];
      }
      return -1;
  }

  void mouseClicked()
  {
    
    savedIndex = getArcIndex ();
    if (savedIndex != -1)
    {
      setupLabel(savedIndex);
      tooltip.setAnchor(Direction.NORTH);
      tooltip.setText(legends[savedIndex]+":"+data[savedIndex]+"%");
      tooltip.setIsActive(true);
      parent.println(savedIndex);
    }
    else
    { 
      tooltip.setIsActive(false);
      tooltip.setText("-1");
    }
  }
  
  void setupLabel(int index)
  {
    tooltip.showCloseIcon(false);
    tooltip.setBackgroundColour(parent.color(0,0,0,233));
    tooltip.setTextColour(255);
    tipX=parent.mouseX;
    tipY=parent.mouseY;
  }
  
  void updatePieChart()
  {
    
  }
}

