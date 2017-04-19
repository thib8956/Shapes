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

import fr.uha.graphics.shapes.SCircle;
import fr.uha.graphics.shapes.SCollection;
import fr.uha.graphics.shapes.SRectangle;
import fr.uha.graphics.shapes.SText;
import fr.uha.graphics.shapes.attributes.ColorAttributes;
import fr.uha.graphics.shapes.attributes.FontAttributes;
import fr.uha.graphics.shapes.attributes.SelectionAttributes;

public class Editor extends JFrame
{
    private static final Logger LOGGER = Logger.getLogger(Editor.class.getName());
    private static FileHandler fh = null;
    private static final Dimension WIN_SIZE = new Dimension(400, 300);

    private ShapesView sview;
    private SCollection model;

    public Editor(){	
	super("Shapes Editor");

	this.addWindowListener(new java.awt.event.WindowAdapter()
	{
	    @Override
	    public void windowClosing(java.awt.event.WindowEvent evt)
	    {
		System.exit(0);
	    }
	});

	this.buildModel();

	this.sview = new ShapesView(this.model);
	this.sview.setPreferredSize(WIN_SIZE);
	this.getContentPane().add(this.sview, java.awt.BorderLayout.CENTER);

	this.getContentPane().addKeyListener(this.sview.getController()); // FIXME : this shouldn't be here (cf. View.java)
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
	this.model = new SCollection();
	this.model.addAttributes(new SelectionAttributes());

//	SRectangle r = new SRectangle(new Point(10,10), 40, 60);
//	r.addAttributes(new ColorAttributes(true, true,Color.BLUE,Color.BLACK));
//	r.addAttributes(new SelectionAttributes());
//	this.model.add(r);
//
//	SCircle c = new SCircle(new Point(100,100), 30);
//	c.addAttributes(new ColorAttributes(false,true,Color.RED,Color.RED));
//	c.addAttributes(new SelectionAttributes());
//	this.model.add(c);
//
//	SText t= new SText(new Point(150,150),"hello");
//	t.addAttributes(new ColorAttributes(true,true,Color.YELLOW,Color.BLUE));
//	t.addAttributes(new FontAttributes());
//	t.addAttributes(new SelectionAttributes());
//	this.model.add(t);
//
	// TODO : gérer déplacement de SCollection
	SCollection sc = new SCollection();
	sc.addAttributes(new SelectionAttributes());
	SRectangle r= new SRectangle(new Point(20,30), 60, 60);
	r.addAttributes(new ColorAttributes(true,false,Color.MAGENTA,Color.BLUE));
	r.addAttributes(new SelectionAttributes());
	sc.add(r);
	SCircle c = new SCircle(new Point(150,100), 40);
	c.addAttributes(new ColorAttributes(false,true,Color.BLUE,Color.DARK_GRAY));
	c.addAttributes(new SelectionAttributes());
	sc.add(c);
	this.model.add(sc);

//	Rectangle bounds = sc.getBounds();
//	LOGGER.info(bounds.toString());
//	SRectangle rect = new SRectangle(new Point(bounds.x, bounds.y), bounds.width, bounds.height);
//	rect.addAttributes(new ColorAttributes(false, true, Color.BLACK, Color.BLUE));
//	rect.addAttributes(new SelectionAttributes());
//	this.model.add(rect);

    }

    public static void main(String[] args)
    {
	Editor.initLogger();

	Editor self = new Editor();
	self.pack();
	self.setVisible(true);
	self.getContentPane().requestFocus();
    }
}
