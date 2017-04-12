package fr.uha.graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;

public class SCircle extends Shape {
    private static final int DEFAULT_RADIUS = 1;
    private int radius;
    private Point loc;
    
    public SCircle(){
	this.radius = DEFAULT_RADIUS;
	this.loc = new Point();
    }

    public SCircle(Point loc, int radius) {
	this.loc = loc;
	this.radius = radius;
    }

    @Override
    public Point getLoc() {
	return this.loc;
    }

    @Override
    public void setLoc(Point p) {
	this.loc = p;
    }

    @Override
    public void translate(int dx, int dy) {
	this.loc.translate(dx, dy);
    }

    @Override
    public Rectangle getBounds() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void accept(ShapeVisitor sv) {
	sv.visitCircle(this);
    }

}
