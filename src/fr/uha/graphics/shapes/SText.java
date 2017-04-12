package fr.uha.graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

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
    public Rectangle getBounds() {
	FontAttributes attrs = (FontAttributes)this.getAttributes(FontAttributes.ID);
	// FIXME: There's got to be a better way to get the SText's bounds
	Rectangle2D bounds2d = attrs.font.getStringBounds(this.text, new FontRenderContext(new AffineTransform(), true, true));
	return bounds2d.getBounds();
    }

    @Override
    public void accept(ShapeVisitor sv) {
	sv.visitText(this);
    }

}
