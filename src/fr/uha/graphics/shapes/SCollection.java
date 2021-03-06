package fr.uha.graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import fr.uha.graphics.shapes.attributes.Attributes;
import fr.uha.graphics.shapes.ui.Editor;

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
	public void addAttributes(Attributes attrs) {
		super.addAttributes(attrs);
		// Propagate all atrributes to the members of SCollection.
		for (Shape s : this.childShapes) s.addAttributes(attrs);
	}

	@Override
	public Rectangle getBounds() {
		Rectangle bounds;
		try {
			bounds = childShapes.get(0).getBounds();
			for (Shape s : childShapes)
				bounds = bounds.union(s.getBounds());
		} catch (IndexOutOfBoundsException e){
			// If the SCollection is empty, set the bounds to fill the window
			return new Rectangle(Editor.WIN_SIZE);
		}
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
