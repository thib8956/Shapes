package fr.uha.graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;

public class STriangle extends Shape {
	
	private static final int DEFAULT_SIZE = 1;
	private Point loc;
	private int size;
	
	public STriangle(){
		this.size = DEFAULT_SIZE;
		this.loc = new Point();
	}
	
	public STriangle(Point loc, int size){
		this.loc = loc;
		this.size = size;
	}

	@Override
	public void translate(int dx, int dy) {
		this.loc.translate(dx, dy);
	}

	@Override
	public Point getLoc() {
		return this.loc;
	}

	@Override
	public void setLoc(Point p) {
		this.loc = p;
	}
	
	public int getSize(){
		return this.size;
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(loc.x, loc.y, size, size);
	}

	@Override
	public void accept(ShapeVisitor sv) {
		sv.visitTriangle(this);
	}}
