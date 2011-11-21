package project;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;

import controlP5.ControlFont;
import controlP5.ControlGroup;
import controlP5.ControlP5;
import controlP5.DropdownList;
import controlP5.ListBox;
import data.ArtistDetails;
import data.DataClass;

public class ListTable {
  int                 cellHeight      = 30;
  int                 cellPaddingLeft = 5;
  int                 cellWidth       = 60;
  int                 maxRowsToShow   = -1;
  ArrayList<String[]> content;
  int                 lx, ly;
  String              title;
  processing.core.PApplet parent;
  int fillColor;
  int strokeColor;
  public ControlP5 ui;
  int flag;
  public DropdownList p1, p2, p3;
  public ListBox l;
  public controlP5.Button s;
  DataClass dataClass;
  static ArrayList<ArtistDetails> artistDetails;
  
  ListTable(processing.core.PApplet p, ControlP5 ui, String[] header, int x, int y, int flag,DataClass d) {
    this.parent = p;
    dataClass=d;
    content     = new ArrayList<String[]>();
    content.add(header);
    lx = x;
    ly = y;
    this.flag = flag;
    this.ui = ui;
    //p1 = new DropdownList();
    //p2 = new DropdownList();
    setupUi();
  }

  public void addRow(String[] row) {
    content.add(row);
  }

  public int height() {
    return ((maxRowsToShow == -1)
      ? content.size()
      : PApplet.min(content.size(), maxRowsToShow)) * cellHeight;
  }

  public int width() {
    if (content.size() > 0) {
      return content.get(0).length * cellWidth;
    } 
    else {
      return 0;
    }
  }

  public void render() {
    PFont header_font  = parent.createFont("SansSerif.bold", 12);
    PFont regular_font = parent.createFont("SansSerif.plain", 12);

    fillColor   = parent.color(255);
    strokeColor = parent.color(255);

    parent.stroke(parent.color(255));
    parent.fill(parent.color(255));
    parent.line(lx, ly, lx, ly + height());                        // Vertical left line
    parent.line(lx + width(), ly, lx + width(), ly + height());    // Vertical right line

    int h = 0;

    for (String[] row : content) {
      if ((maxRowsToShow > -1) && (maxRowsToShow <= h)) {
        break;
      }

      if (h == 0) {
        parent.textFont(header_font);
      } 
      else {
        parent.textFont(regular_font);
      }

      for (int i = 0; i < row.length; ++i) {
        parent.text(row[i], lx + cellPaddingLeft + i * cellWidth, ly + h * cellHeight + cellHeight / 2);
      }

      parent.line(lx, ly + h * cellHeight, lx + width(), ly + h * cellHeight);
      ++h;
    }

    parent.line(lx, ly + h * cellHeight, lx + width(), ly + h * cellHeight);
    //System.out.println("Rendering....");
    parent.stroke(strokeColor);
    parent.fill(fillColor);
  }

  public void clear() {
    parent.stroke(parent.color(0));
    parent.fill(parent.color(0));
    parent.rect(lx, ly, lx + width(), ly + height());
    parent.stroke(strokeColor);
    parent.fill(fillColor);
  }

  public void mouseClicked()
  {
    //println(height()+" "+width()+" "+content.size());

    for (int index = 0 ; index < content.size(); index ++)
    {
      if (parent.mouseX < lx+width()*index && parent.mouseX > lx && parent.mouseY > ly && parent.mouseY < ly+height()*index)
       ; //println("index:"+ index);
    }
  }
  public void hide(){
	  p1.hide();
	  p2.hide();
	  p3.hide();
	  l.hide();
	  s.hide();
  }
  public void setupUi() {
    ui.setControlFont(new ControlFont(parent.createFont("Arial", 13), 13));

    
    p1 = ui.addDropdownList("ages"+flag, lx, ly, 110, 50);
    p1.captionLabel().set("ages");
    //p1.setGroup("ages"+flag);
    p1.addItem("ALL", 0);
    p1.addItem("13-18",1);
    p1.addItem("19-24",2);
    p1.addItem("25-35",3);
    p1.addItem("36-45",4);
    p1.addItem("46-64",5);
    p1.addItem("above 65", 6);
    p1.setItemHeight(18);
    customize(p1);
    
    p2 = ui.addDropdownList("gender"+flag, lx+120, ly, 110, 50);
    //p2.setGroup("gender"+flag);
    p2.captionLabel().set("gender");
    p2.addItem("ALL", 0);
    p2.addItem("Male",1);
    p2.addItem("Female", 2);
    p2.setItemHeight(18);
    customize(p2);
    
    p3 = ui.addDropdownList("nationality"+flag, lx+240, ly, 110, 50);
    p3.captionLabel().set("nationality");
    //p3.setGroup("nationality"+flag);
    ArrayList<String>countryNames=dataClass.getListOfCountries();
    p3.addItem("ALL", 0);
    for(int i=0;i<countryNames.size();i++)
    {
    	if(countryNames.get(i).length()>14)
    	{
    		p3.addItem(countryNames.get(i).substring(0, 14),i+1);	
    	}
    	else
    	{
    		p3.addItem(countryNames.get(i),i+1);
    	}
    	
    }
    
//    p3.addItem("Male",1);
//    p3.addItem("Female", 2);
    p3.setItemHeight(18);
    customize(p3);
    s = ui.addButton("submit"+flag, flag, lx+355, ly-18, 25, 18);
    s.setCaptionLabel("Go");
    s.hide();
    
    l = ui.addListBox("ArtistList"+flag,lx,ly+64,360,120);
    //l.setGroup("ArtistList"+flag);
    l.setItemHeight(25);
    l.setBarHeight(25);
    l.setHeight(400);
    
    l.captionLabel().toUpperCase(true);
    l.captionLabel().set("Artist Search Result");
    l.captionLabel().style().marginTop = 3;
    l.valueLabel().style().marginTop = 3; // the +/- sign
    //l.setBackgroundColor(color(100,0,0));
    
     artistDetails=dataClass.getTopArtistAllTime();
//    for(int i=0;i<100;i++) {
//      l.addItem("artist name "+i+"\tCount:"+(100-i),i);
//    }
    System.out.println(artistDetails.size());
    for(int i=0;i<artistDetails.size();i++)
    {
    	System.out.println(artistDetails.get(i).getArtistId());
    	l.addItem(artistDetails.get(i).getArtistName()+"     "+artistDetails.get(i).getCurrentCount(), i);
    }
    l.setColorBackground(parent.color(255,128));
    l.setColorActive(parent.color(0,0,255,128));
    
  }
  
  void customize(ControlGroup cgrp)
  {
    cgrp.setBackgroundColor(parent.color(0));
    cgrp.setBarHeight(18);
    cgrp.captionLabel().style().marginTop = 4;
    cgrp.captionLabel().style().marginLeft = 3;
    cgrp.valueLabel().style().marginTop = 4;
    cgrp.setColorBackground(parent.color(82, 2, 2));
    cgrp.setColorActive(parent.color(255, 128));
  }
  
  void updateList(int age_index, int gender_index, int nation_index)
  {
	String country="";
	if(nation_index==0)
	{
		country="";
	}
	else
	{
		ArrayList<String>countryNames=dataClass.getListOfCountries();
		country = countryNames.get(nation_index-1);
	}
	String gender="";
	if(gender_index==0)
	{
		gender="";
	}
	else if(gender_index==1)
	{
		gender="m";
	}
	else if(gender_index==2)
	{
		gender="f";
	}
	
	String ageGroup = "";
	
	if(age_index==0)
	{
		ageGroup="";
	}
	else if(age_index==1)
	{
		ageGroup="13-18";
	}
	else if(age_index==2)
	{
		ageGroup="19-24";
	}
	else if(age_index==3)
	{
		ageGroup="25-35";
	}
	else if(age_index==4)
	{
		ageGroup="36-45";
	}
	else if(age_index==5)
	{
		ageGroup="46-64";
	}
	else if(age_index==6)
	{
		ageGroup="above 65";
	}
	
    artistDetails=dataClass.getTop100Artists(gender, ageGroup, country);
    l.clear();
    if(artistDetails!=null)
    {
    	for(int i=0; i<artistDetails.size();i++)
        {
        	l.addItem(artistDetails.get(i).getArtistName()+"\t"+artistDetails.get(i).getCurrentCount(),i);
        }	
    }
    else
    {
    	l.addItem("No artists found", 0);
    }
    
  }
  
  public static ArrayList<ArtistDetails> getArtistList()
  {
	  return artistDetails;
  }
  
  public void show(){
	  p1.show();
	  p2.show();
	  p3.show();
	  l.show();
	  s.show();
  }
}
