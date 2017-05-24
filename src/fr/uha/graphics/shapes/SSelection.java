package fr.uha.graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;

public class SSelection extends SRectangle {

	public SSelection(){
		super(new Point(0, 0), 0, 0);
	}

	public SSelection(Point loc, int width, int length) {
		super(loc, width, length);
	}
	
	public void resize(int width, int height){
		this.getRect().setSize(width, height);	
	}
	
	@Override
	public String toString() {
		Rectangle rect = this.getRect();
		return "SSelection [x=" + rect.x + " y=" + rect.y + " height=" + rect.height + " width=" + rect.width + "]";
	}
	
	@Override
	public String cssShape() {
		return "";
	}
	
	@Override
	public String htmlShape() {
		return "";
	}
}
