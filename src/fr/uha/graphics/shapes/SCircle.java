package fr.uha.graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.logging.Logger;

import fr.uha.graphics.shapes.ui.ShapesController;

public class SCircle extends Shape {

	private static final int DEFAULT_RADIUS = 1;
	private int radius;
	private Point loc;

	public SCircle() {
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

	public int getRadius() {
		return this.radius;
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(loc.x, loc.y, radius, radius);
	}

	@Override
	public void accept(ShapeVisitor sv) {
		sv.visitCircle(this);
	}

	@Override
	public String toString() {
		return "SCircle [radius=" + radius + ", loc=" + loc + "]";
	}

	@Override
	public String htmlShape() {
		return "<div class=\"circle"+this.hashCode()+"\"></div>";
	}

	@Override
	public String cssShape() {
		StringBuilder strBuilder = new StringBuilder(".circle" + this.hashCode() + "{ ");
		strBuilder.append("position: absolute;");
		strBuilder.append("top:" + this.loc.y + "px;");
		strBuilder.append("left:" + this.loc.x + "px;");
		strBuilder.append("width:" + this.radius + "px;");
		strBuilder.append("height:" + this.radius + "px;");
		strBuilder.append("border-radius:" + this.radius/2 + "px;");
		strBuilder.append("-webkit-border-radius:" + this.radius/2 + "px;");
		strBuilder.append("-o-border-radius:" + this.radius/2 + "px;");
		strBuilder.append("-moz-border-radius:" + this.radius/2 + "px;");
		strBuilder.append(this.attributesCss());
		strBuilder.append(" }");
		return strBuilder.toString();
	}

}
