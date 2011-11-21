package project;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;

import controlP5.ControlFont;
import controlP5.ControlGroup;
import controlP5.ControlP5;
import controlP5.DropdownList;
import controlP5.ListBox;

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
  
  ListTable(processing.core.PApplet p, ControlP5 ui, String[] header, int x, int y, int flag) {
    this.parent = p;
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
  }
  public void setupUi() {
    ui.setControlFont(new ControlFont(parent.createFont("Arial", 13), 13));

    
    p1 = ui.addDropdownList("ages"+flag, lx, ly, 110, 50);
    p1.captionLabel().set("ages");
    //p1.setGroup("ages"+flag);
    p1.addItem("ALL", 0);
    p1.addItem("13-18",1);
    p1.addItem("19-24",2);
    p1.addItem("36-45",3);
    p1.addItem("40-64",4);
    p1.addItem("above 65", 5);
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
    p3.addItem("ALL", 0);
    p3.addItem("Male",1);
    p3.addItem("Female", 2);
    p3.setItemHeight(18);
    customize(p3);
    
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
    
    for(int i=0;i<100;i++) {
      l.addItem("artist name "+i+"\tCount:"+(100-i),i);
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
  
  void updateList()
  {
    
  }
  
  public void show(){
	  p1.show();
	  p2.show();
	  p3.show();
	  l.show();
  }
}
