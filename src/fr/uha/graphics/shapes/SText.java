package fr.uha.graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;

public class SText extends Shape {
    
    private String text;
    private Point loc;
    
    public SText(Point point, String string) {
	// TODO Auto-generated constructor stub
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
	// TODO Auto-generated method stub
	return new Rectangle(150, 150);
    }

    @Override
    public void accept(ShapeVisitor sv) {
	sv.visitText(this);
    }

}
