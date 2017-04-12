package fr.uha.graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Map;
import java.util.TreeMap;

import fr.uha.graphics.shapes.attributes.Attributes;

public abstract class Shape {

    private Map<String, Attributes> attributes = new TreeMap<String, Attributes>();

    public void addAttributes(Attributes attrs){
	attributes.put(attrs.getId(), attrs);
    }

    public Attributes getAttributes(String str){
	return attributes.get(str);
    }
    
    public abstract void translate(int dx,int dy);
    public abstract Point getLoc();
    public abstract void setLoc(Point p);
    public abstract Rectangle getBounds();
    public abstract void accept(ShapeVisitor sv);
}
