package fr.uha.graphics.shapes.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.uha.graphics.shapes.SCircle;
import fr.uha.graphics.shapes.SCollection;
import fr.uha.graphics.shapes.SRectangle;
import fr.uha.graphics.shapes.SText;
import fr.uha.graphics.shapes.Shape;
import fr.uha.graphics.shapes.ShapeVisitor;
import fr.uha.graphics.shapes.attributes.ColorAttributes;

public class ShapeDraftman implements ShapeVisitor {
    
    private static final Logger LOGGER = Logger.getLogger(ShapeDraftman.class.getName());
    
    private static final ColorAttributes DEFAULTCOLORATTRIBUTES = new ColorAttributes();
    private Graphics2D graph;
    
    public ShapeDraftman(Graphics graph) {
	this.graph = (Graphics2D)graph;
    }

    @Override
    public void visitRectangle(SRectangle rect) {
	
	LOGGER.log(Level.INFO, "Calling visitRectangle");
	Rectangle r = rect.getRect();
	ColorAttributes attrs = (ColorAttributes)rect.getAttributes(ColorAttributes.ID);
	if (attrs == null) attrs = DEFAULTCOLORATTRIBUTES;
	if (attrs.filled){
	    this.graph.setColor(attrs.filledColor);
	    this.graph.fillRect(r.x, r.y, r.width, r.height);
	}
	this.graph.drawRect(r.x, r.y, r.width, r.height);
	if (attrs.stroked){
	    this.graph.setColor(attrs.strokedColor);
	    
	} else {
	    this.graph.setColor(Color.BLACK);
	}
    }

    @Override
    public void visitCircle(SCircle circle) {
	// TODO Auto-generated method stub

    }

    @Override
    public void visitText(SText text) {
	// TODO Auto-generated method stub

    }

    @Override
    public void visitCollection(SCollection col) {
	Iterator<Shape> it = col.getIterator();
	while (it.hasNext()) it.next().accept(this);
    }

    public void drawHandler(Rectangle bounds){
	
    }
    
    public void setGraphics(Graphics g){
	this.graph = (Graphics2D)g;
    }
}
