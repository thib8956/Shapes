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
		// TODO Auto-generated method stub
		return "<div class=\"triangle"+this.hashCode()+"\"></div>";
	}

	@Override
	public String cssShape() {
		// TODO Auto-generated method stub
		String fontColor=null;
		ColorAttributes colAttrs = (ColorAttributes) this.getAttributes(ColorAttributes.ID);
		/*if(colAttrs.filled)*/ fontColor="("+colAttrs.filledColor.getRed()+","+colAttrs.filledColor.getGreen()+","+colAttrs.filledColor.getBlue()+")";
		//if(colAttrs.stroked) fontColor="("+colAttrs.strokedColor.getRed()+","+colAttrs.strokedColor.getGreen()+","+colAttrs.strokedColor.getBlue()+")";
		return ".triangle"+this.hashCode()+"{position:absolute;top:"+this.loc.getY()+";left:"+this.loc.getX()+";width:0px;height:0px;border:25px solid #069;border-color:transparent transparent rgb"+fontColor+" transparent;border-right-width:"+((this.size/0.86)/2)+"px;border-left-width:"+((this.size/0.86)/2)+"px;border-bottom:"+this.size+"px solid rgb"+fontColor+";}";
	}
	
}
