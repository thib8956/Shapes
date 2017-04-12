package fr.uha.graphics.shapes;

import java.awt.Canvas;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.Rectangle;

import fr.uha.graphics.shapes.attributes.FontAttributes;

public class SText extends Shape {
    
    private String text;
    private Point loc;
    
    public SText(Point loc, String string) {
	this.text = string;
	this.loc = loc;
    }

    public String getText(){
	return this.text;
    }

    @Override
    public Point getLoc() {
	return this.loc;
    }

    @Override
    public void setLoc(Point loc) {
	this.loc = loc;
    }

    @Override
    public void translate(int dx, int dy) {
	this.loc.translate(dx, dy);
    }
    
    @Override
    public Rectangle getBounds(){
	FontAttributes attrs = (FontAttributes)this.getAttributes(FontAttributes.ID);
	Canvas can = new Canvas();
	FontMetrics fontMetrics = can.getFontMetrics(attrs.font);

	int w = fontMetrics.stringWidth(this.text);
	int h = attrs.font.getSize();
	return new Rectangle(loc.x, loc.y-h, w, h);
    }

    @Override
    public void accept(ShapeVisitor sv) {
	sv.visitText(this);
    }

}
