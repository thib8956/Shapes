package fr.uha.graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Map;
import java.util.TreeMap;

import fr.uha.graphics.shapes.attributes.Attributes;
import fr.uha.graphics.shapes.attributes.ColorAttributes;

public abstract class Shape {

	private Map<String, Attributes> attributes = new TreeMap<String, Attributes>();

	public void addAttributes(Attributes attrs) {
		attributes.put(attrs.getId(), attrs);
	}

	public Attributes getAttributes(String str) {
		return attributes.get(str);
	}
	
	public String attributesCss(){
		
		ColorAttributes colAttrs = (ColorAttributes) this.getAttributes(ColorAttributes.ID);
		String strokedColor="("+colAttrs.strokedColor.getRed()+","+colAttrs.strokedColor.getGreen()+","+colAttrs.strokedColor.getBlue()+")";
		String filledColor="("+colAttrs.filledColor.getRed()+","+colAttrs.filledColor.getGreen()+","+colAttrs.filledColor.getBlue()+")";

		if(colAttrs.stroked && colAttrs.filled){
			return "background:rgb"+filledColor+";border:1px solid rgb"+strokedColor+";";
		}
		if (colAttrs.stroked){
			//System.out.println(strokedColor);
			return "background:#fff;border:1px solid rgb"+strokedColor+";";
		}
		if (colAttrs.filled) {
			//System.out.println(filledColor);
			return "background:rgb"+filledColor+";";
		}
		return null;

		
		
	}

	public abstract void translate(int dx, int dy);

	public abstract Point getLoc();

	public abstract void setLoc(Point p);

	public abstract Rectangle getBounds();

	public abstract void accept(ShapeVisitor sv);
	
	public abstract String htmlShape();
	
	public abstract String cssShape();
}
