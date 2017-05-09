package fr.uha.graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;

public class SSelection extends Shape {
	private Rectangle rect;

	public SSelection(Point loc, int width, int length) {
		this.rect = new Rectangle(loc.x, loc.y, width, length);
	}

	public Rectangle getRect() {
		return this.rect;
	}

	public void resize(int width, int height){
		this.rect.setSize(width, height);	
	}

	@Override
	public Point getLoc() {
		return this.rect.getLocation();
	}

	@Override
	public void setLoc(Point p) {
		this.rect.setLocation(p);
	}

	@Override
	public void translate(int dx, int dy) {
		this.rect.translate(dx, dy);

	}

	@Override
	public Rectangle getBounds() {
		return this.rect.getBounds();
	}

	@Override
	public void accept(ShapeVisitor sv) {
		sv.visitSelection(this);
	}

	@Override
	public String toString() {
		return "SSelection [x=" + rect.x + " y=" + rect.y + " height=" + rect.height + " width=" + rect.width + "]";
	}
}