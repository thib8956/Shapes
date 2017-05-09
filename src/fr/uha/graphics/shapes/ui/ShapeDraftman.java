package fr.uha.graphics.shapes.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.logging.Logger;

import fr.uha.graphics.shapes.SCircle;
import fr.uha.graphics.shapes.SCollection;
import fr.uha.graphics.shapes.SRectangle;
import fr.uha.graphics.shapes.SSelection;
import fr.uha.graphics.shapes.SText;
import fr.uha.graphics.shapes.STriangle;
import fr.uha.graphics.shapes.Shape;
import fr.uha.graphics.shapes.ShapeVisitor;
import fr.uha.graphics.shapes.attributes.ColorAttributes;
import fr.uha.graphics.shapes.attributes.FontAttributes;
import fr.uha.graphics.shapes.attributes.SelectionAttributes;

public class ShapeDraftman implements ShapeVisitor {

	private static final Logger LOGGER = Logger.getLogger(ShapeDraftman.class.getName());

	// Default attributes
	// TODO : externalize constants ?
	private static final ColorAttributes DEFAULT_COLOR_ATTRIBUTES = new ColorAttributes(false, true, Color.BLACK, Color.BLACK);

	private Graphics2D graph;

	public ShapeDraftman(Graphics graph) {
		this.graph = (Graphics2D) graph;
	}

	@Override
	public void visitRectangle(SRectangle rect) {
		Rectangle r = rect.getRect();
		ColorAttributes colAttrs = (ColorAttributes) rect.getAttributes(ColorAttributes.ID);

		if (colAttrs == null){
			colAttrs = DEFAULT_COLOR_ATTRIBUTES;
		}
		if (colAttrs.filled) {
			this.graph.setColor(colAttrs.filledColor);
			this.graph.fillRect(r.x, r.y, r.width, r.height);
		}
		if (colAttrs.stroked) {
			this.graph.setColor(colAttrs.strokedColor);
			this.graph.drawRect(r.x, r.y, r.width, r.height);
		}
		drawHandlerIfSelected(rect);
	}

	@Override
	public void visitCircle(SCircle circle) {
		ColorAttributes colAttrs = (ColorAttributes) circle.getAttributes(ColorAttributes.ID);
		
		if (colAttrs == null) {
			colAttrs = DEFAULT_COLOR_ATTRIBUTES;
		}
		if (colAttrs.filled) {
			this.graph.setColor(colAttrs.filledColor);
			this.graph.fillOval(circle.getLoc().x, circle.getLoc().y, circle.getRadius(), circle.getRadius());
		}
		if (colAttrs.stroked) this.graph.setColor(colAttrs.strokedColor);
		this.graph.drawOval(circle.getLoc().x, circle.getLoc().y, circle.getRadius(), circle.getRadius());

		drawHandlerIfSelected(circle);
}

	@Override
	public void visitText(SText text) {
		Point loc = text.getLoc();
		Rectangle bounds = text.getBounds();
		// Fetch SText attributes
		ColorAttributes colAttrs = (ColorAttributes) text.getAttributes(ColorAttributes.ID);
		FontAttributes fontAttrs = (FontAttributes) text.getAttributes(FontAttributes.ID);

		if (colAttrs == null){
			colAttrs = DEFAULT_COLOR_ATTRIBUTES;
		}
		if (colAttrs.filled) {
			this.graph.setColor(colAttrs.filledColor);
			// The reference point for Rectangle is the upper-left corner,
			// whereas it is the bottom-left corner for Font.drawString().
			this.graph.fillRect(loc.x, loc.y - bounds.height, bounds.width, bounds.height);
		}
		this.graph.setFont(fontAttrs.font);
		this.graph.setPaint(fontAttrs.fontColor);
		this.graph.drawString(text.getText(), loc.x, loc.y);

		drawHandlerIfSelected(text);
	}

	@Override
	public void visitCollection(SCollection col) {
		for (Iterator<Shape> it = col.getIterator(); it.hasNext();) {
			Shape current = it.next();
			current.accept(this);
		}
		
		drawHandlerIfSelected(col);
	}

	// TODO : refactor visitTriangle
	public void visitTriangle(STriangle tri){
		Point loc = tri.getLoc();
		int size = tri.getSize();
		ColorAttributes attrs = (ColorAttributes) tri.getAttributes(ColorAttributes.ID);
		SelectionAttributes selAttrs = (SelectionAttributes) tri.getAttributes(SelectionAttributes.ID);
		
		if (attrs == null)
			attrs = DEFAULT_COLOR_ATTRIBUTES;
		else if (attrs.filled) {
			this.graph.setColor(attrs.filledColor);
			this.graph.fillPolygon(new int[]{loc.x, loc.x+(size/2), loc.x + size},
								   new int[]{loc.y+size, loc.y, loc.y+size}, 
								   3);
		}
		if (attrs.stroked) this.graph.setColor(attrs.strokedColor);
		this.graph.drawPolygon(new int[]{loc.x, loc.x+(size/2), loc.x + size},
							   new int[]{loc.y+size, loc.y, loc.y+size}, 
							   3);

		if (selAttrs.isSelected()) drawHandler(tri.getBounds());
	}
	
	private void drawHandlerIfSelected(Shape s){
		SelectionAttributes selAttrs = (SelectionAttributes) s.getAttributes(SelectionAttributes.ID);
		if ((selAttrs != null)&&(selAttrs.isSelected())){
			Rectangle bounds = s.getBounds();
			drawHandler(bounds);
		}
	}

	public void drawHandler(Rectangle bounds) {
		this.graph.setColor(Color.RED);
		this.graph.drawRect(bounds.x - 5, bounds.y - 5, 5, 5);
		this.graph.drawRect(bounds.x + bounds.width, bounds.y + bounds.height, 5, 5);
	}

	public void setGraphics(Graphics g) {
		this.graph = (Graphics2D) g;
	}

	@Override
	public void visitSelection(SSelection sel) {
		Rectangle r = sel.getRect();
		ColorAttributes attrs = (ColorAttributes) sel.getAttributes(ColorAttributes.ID);

		if (attrs == null) attrs = DEFAULT_COLOR_ATTRIBUTES;
		this.graph.drawRect(r.x, r.y, r.width, r.height);
	}
}
