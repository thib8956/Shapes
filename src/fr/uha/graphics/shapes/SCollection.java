package fr.uha.graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class SCollection extends Shape {

	private static final Logger LOGGER = Logger.getLogger(SCollection.class.getName());

	private List<Shape> childShapes = new ArrayList<Shape>();
	private Point loc;

	public Iterator<Shape> getIterator() {
		return childShapes.listIterator();
	}

	public void add(Shape s) {
		// LOGGER.log(Level.INFO, "Adding to collection : {0}", s.toString());
		childShapes.add(s);
		relocate();
	}

	public void remove(Shape s) {
		if (childShapes.remove(s)) relocate();
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
		for (Shape s : this.childShapes) {
			s.translate(dx, dy);
		}
		relocate();
	}

	@Override
	public Rectangle getBounds() {
		Rectangle bounds = childShapes.get(0).getBounds();
		for (Shape s : childShapes)
			bounds = bounds.union(s.getBounds());
		return bounds;
	}

	@Override
	public void accept(ShapeVisitor sv) {
		sv.visitCollection(this);
	}

	private void relocate() {
		// Recalculate the location of the SCollection (Upper-left corner)
		this.loc = this.getBounds().getLocation();
	}

	@Override
	public String htmlShape() {
		StringBuilder prepare= new StringBuilder();
		for(Iterator<Shape> it=childShapes.iterator();it.hasNext();){		
			Shape current = it.next();
			prepare.append(current.htmlShape());
		}
		return prepare.toString();
	}

	@Override
	public String cssShape() {
		StringBuilder prepare= new StringBuilder();
		for(Iterator<Shape> it=childShapes.iterator();it.hasNext();){			
			Shape current = it.next();
			prepare.append(current.cssShape());
		}
		return prepare.toString();
	}

}
