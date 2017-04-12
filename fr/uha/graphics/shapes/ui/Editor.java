package fr.uha.graphics.shapes.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JFrame;

import fr.uha.graphics.shapes.SCollection;
import fr.uha.graphics.shapes.SRectangle;
import fr.uha.graphics.shapes.attributes.ColorAttributes;
import fr.uha.graphics.shapes.attributes.SelectionAttributes;

public class Editor extends JFrame
{
    private static final Logger LOGGER = Logger.getLogger(Editor.class.getName());
    private static FileHandler fh = null;
    
    private static final int DEFAULT_WIN_WIDTH = 800;
    private static final int DEFAULT_WIN_HEIGHT = 600;

    ShapesView sview;
    SCollection model;

    public Editor()
    {	
	super("Shapes Editor");
	
	this.addWindowListener(new java.awt.event.WindowAdapter()
	{
	    public void windowClosing(java.awt.event.WindowEvent evt)
	    {
		System.exit(0);
	    }
	});

	this.buildModel();

	this.sview = new ShapesView(this.model);
	this.sview.setPreferredSize(new Dimension(DEFAULT_WIN_WIDTH, DEFAULT_WIN_HEIGHT));
	this.getContentPane().add(this.sview, java.awt.BorderLayout.CENTER);
    }

    public static void initLogger(){
	try {
	    fh = new FileHandler("Shapes.log", false);
	} catch (SecurityException | IOException e) {
	    e.printStackTrace();
	}
	Logger l = Logger.getLogger("");
	fh.setFormatter(new SimpleFormatter());
	l.addHandler(fh);
	l.setLevel(Level.INFO);
    }

    private void buildModel()
    {
	//this.model = new SRectangle(new Point(0, 0), 50, 50);
	this.model = new SCollection();
	this.model.addAttributes(new SelectionAttributes());
	//this.model.addAttributes(new ColorAttributes(true, false, Color.BLUE, Color.BLUE));


	SRectangle r = new SRectangle(new Point(10,10),20,30);
	r.addAttributes(new ColorAttributes(true, false,Color.BLUE,Color.BLUE));
	r.addAttributes(new SelectionAttributes());
	this.model.add(r);
	/*
	SCircle c = new SCircle(new Point(100,100),10);
	c.addAttributes(new ColorAttributes(false,true,Color.BLUE,Color.BLUE));
	c.addAttributes(new SelectionAttributes());
	this.model.add(c);

	SText t= new SText(new Point(100,100),"hello");
	t.addAttributes(new ColorAttributes(true,true,Color.YELLOW,Color.BLUE));
	t.addAttributes(new FontAttributes());
	t.addAttributes(new SelectionAttributes());
	this.model.add(t);

	SCollection sc = new SCollection();
	sc.addAttributes(new SelectionAttributes());
	r= new SRectangle(new Point(20,30),30,30);
	r.addAttributes(new ColorAttributes(true,false,Color.MAGENTA,Color.BLUE));
	r.addAttributes(new SelectionAttributes());
	sc.add(r);
	c = new SCircle(new Point(150,100),20);
	c.addAttributes(new ColorAttributes(false,true,Color.BLUE,Color.DARK_GRAY));
	c.addAttributes(new SelectionAttributes());
	sc.add(c);
	this.model.add(sc);
	 */

    }

    public static void main(String[] args)
    {
	Editor.initLogger();
	
	Editor self = new Editor();
	self.pack();
	self.setVisible(true);
    }
}
