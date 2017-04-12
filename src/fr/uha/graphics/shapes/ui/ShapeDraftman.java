package fr.uha.graphics.shapes.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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
import fr.uha.graphics.shapes.attributes.FontAttributes;

public class ShapeDraftman implements ShapeVisitor {
    
    private static final Logger LOGGER = Logger.getLogger(ShapeDraftman.class.getName());
    
    // Default attributes
    private static final ColorAttributes DEFAULTCOLORATTRIBUTES = new ColorAttributes(false, true, Color.BLACK, Color.BLACK);
    
    private Graphics2D graph;
    
    public ShapeDraftman(Graphics graph) {
	this.graph = (Graphics2D)graph;
    }

    @Override
    public void visitRectangle(SRectangle rect) {
	
	LOGGER.log(Level.INFO, "Calling visitRectangle");
	Rectangle r = rect.getRect();
	ColorAttributes attrs = (ColorAttributes)rect.getAttributes(ColorAttributes.ID);
	LOGGER.log(Level.INFO, "ColorAttributes : \n{0}", attrs);
	if (attrs == null) attrs = DEFAULTCOLORATTRIBUTES;
	else if (attrs.filled){
	    this.graph.setColor(attrs.filledColor);
	    this.graph.fillRect(r.x, r.y, r.width, r.height);
	}
	if (attrs.stroked){
	    this.graph.setColor(attrs.strokedColor);
	    this.graph.drawRect(r.x, r.y, r.width, r.height);
	    
	} else {
	    this.graph.setColor(Color.BLACK);
	}
    }

    @Override
    public void visitCircle(SCircle circle) {
    	LOGGER.log(Level.INFO, "Calling visitCircle");

    	ColorAttributes attrs = (ColorAttributes)circle.getAttributes(ColorAttributes.ID);
    	if (attrs == null) attrs = DEFAULTCOLORATTRIBUTES;
    	else if (attrs.filled){
    	    this.graph.setColor(attrs.filledColor);
    	    this.graph.fillOval(circle.getLoc().x, circle.getLoc().y, circle.getRadius(), circle.getRadius());
    	}
    	if (attrs.stroked) this.graph.setColor(attrs.strokedColor);
    	this.graph.drawOval(circle.getLoc().x, circle.getLoc().y, circle.getRadius(), circle.getRadius());
    }

    @Override
    public void visitText(SText text) {
    	LOGGER.log(Level.INFO, "Calling visitText");
    	Point loc = text.getLoc();
    	Rectangle bounds = text.getBounds();
    	
    	// Fetch SText attributes
    	ColorAttributes colAttrs = (ColorAttributes)text.getAttributes(ColorAttributes.ID);
    	FontAttributes fontAttrs = (FontAttributes)text.getAttributes(FontAttributes.ID);
    	
    	if (colAttrs == null) colAttrs = DEFAULTCOLORATTRIBUTES;
    	else if (colAttrs.filled){
    	    this.graph.setColor(colAttrs.filledColor);
    	    // The reference point for Rectangle is the upper-left corner,
    	    // whereas it is the bottom-left corner for Font (drawString()). 
    	    this.graph.fillRect(loc.x, loc.y - bounds.height, bounds.width, bounds.height);
    	}
    	this.graph.setFont(fontAttrs.font);
    	this.graph.setPaint(fontAttrs.fontColor);
    	this.graph.drawString(text.getText(), loc.x, loc.y);
    	
    	
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
