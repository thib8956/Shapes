package fr.uha.graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;

import fr.uha.graphics.shapes.attributes.ColorAttributes;

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
	}

	@Override
	public String htmlShape() {
		return "<div class=\"triangle"+this.hashCode()+"\"></div>";
	}

	@Override
	public String cssShape() {
		ColorAttributes colAttrs = (ColorAttributes) this.getAttributes(ColorAttributes.ID);
		String colorString = String.format("#%02x%02x%02x", 
											colAttrs.filledColor.getRed(), 
											colAttrs.filledColor.getGreen(), 
											colAttrs.filledColor.getBlue());
		StringBuilder strBuilder = new StringBuilder(".triangle" + this.hashCode() + "{ ");
		strBuilder.append("position: absolute;");
		strBuilder.append("top: " + this.loc.y + "px;");
		strBuilder.append("left: " + this.loc.x + "px;");
		strBuilder.append("width: 0px;");
		strBuilder.append("height: 0px;");
		strBuilder.append("border: 0 solid transparent;");
		strBuilder.append("border-left-width: " + Math.ceil(this.size/1.72) + "px;");
		strBuilder.append("border-right-width: " + Math.ceil(this.size/1.72) + "px;");
		strBuilder.append("border-bottom: " + this.size + "px solid " + colorString + ";");
		strBuilder.append("}");

		return strBuilder.toString();
	}
	
}
