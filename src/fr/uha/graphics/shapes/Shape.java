package fr.uha.graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.uha.graphics.shapes.attributes.Attributes;
import fr.uha.graphics.shapes.attributes.ColorAttributes;

public abstract class Shape {
	
	private static final Logger LOGGER = Logger.getLogger(SCollection.class.getName());
	private Map<String, Attributes> attributes = new TreeMap<String, Attributes>();

	public void addAttributes(Attributes attrs) {
		attributes.put(attrs.getId(), attrs);
	}

	public Attributes getAttributes(String str) {
		return attributes.get(str);
	}
	
	public String attributesCss(){
		
		ColorAttributes colAttrs = (ColorAttributes) this.getAttributes(ColorAttributes.ID);
		String strokedColor = "#ffffff";
		String filledColor = "#ffffff";
		if (colAttrs.filledColor != null){
			filledColor = String.format("#%02x%02x%02x", colAttrs.filledColor.getRed(), colAttrs.filledColor.getGreen(), colAttrs.filledColor.getBlue());
		} 
		if (colAttrs.strokedColor != null){
			strokedColor = String.format("#%02x%02x%02x", colAttrs.strokedColor.getRed(), colAttrs.strokedColor.getGreen(), colAttrs.strokedColor.getBlue());
		}

		if(colAttrs.stroked && colAttrs.filled){
			return "background: " + filledColor + ";border:1px solid " + strokedColor + ";";
		}
		if (colAttrs.stroked){
			return "background:#ffffff;border:1px solid "+strokedColor+";";
		}
		if (colAttrs.filled) {
			return "background: "+filledColor+";";
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
