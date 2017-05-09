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
import fr.uha.graphics.shapes.Shape;
import fr.uha.graphics.shapes.ShapeVisitor;
import fr.uha.graphics.shapes.attributes.ColorAttributes;
import fr.uha.graphics.shapes.attributes.FontAttributes;
import fr.uha.graphics.shapes.attributes.SelectionAttributes;

public class ShapeDraftman implements ShapeVisitor {

	private static final Logger LOGGER = Logger.getLogger(ShapeDraftman.class.getName());

	// Default attributes
	private static final ColorAttributes DEFAULTCOLORATTRIBUTES = new ColorAttributes(false, true, Color.BLACK,
			Color.BLACK);

	private Graphics2D graph;

	public ShapeDraftman(Graphics graph) {
		this.graph = (Graphics2D) graph;
	}

	@Override
	public void visitRectangle(SRectangle rect) {

		// LOGGER.log(Level.INFO, "Calling visitRectangle");
		Rectangle r = rect.getRect();
		ColorAttributes attrs = (ColorAttributes) rect.getAttributes(ColorAttributes.ID);
		SelectionAttributes selAttrs = (SelectionAttributes) rect.getAttributes(SelectionAttributes.ID);

		// LOGGER.log(Level.INFO, "ColorAttributes : \n{0}", attrs);
		if (attrs == null)
			attrs = DEFAULTCOLORATTRIBUTES;
		else if (attrs.filled) {
			this.graph.setColor(attrs.filledColor);
			this.graph.fillRect(r.x, r.y, r.width, r.height);
		}
		if (attrs.stroked) {
			this.graph.setColor(attrs.strokedColor);
			this.graph.drawRect(r.x, r.y, r.width, r.height);
		}
		if (selAttrs.isSelected()) drawHandler(rect.getBounds());
	}

	@Override
	public void visitCircle(SCircle circle) {
		ColorAttributes attrs = (ColorAttributes) circle.getAttributes(ColorAttributes.ID);
		SelectionAttributes selAttrs = (SelectionAttributes) circle.getAttributes(SelectionAttributes.ID);
		if (attrs == null)
			attrs = DEFAULTCOLORATTRIBUTES;
		else if (attrs.filled) {
			this.graph.setColor(attrs.filledColor);
			this.graph.fillOval(circle.getLoc().x, circle.getLoc().y, circle.getRadius(), circle.getRadius());
		}
		if (attrs.stroked) this.graph.setColor(attrs.strokedColor);
		this.graph.drawOval(circle.getLoc().x, circle.getLoc().y, circle.getRadius(), circle.getRadius());

		if (selAttrs.isSelected()) drawHandler(circle.getBounds());
	}

	@Override
	public void visitText(SText text) {
		Point loc = text.getLoc();
		Rectangle bounds = text.getBounds();
		// Fetch SText attributes
		ColorAttributes colAttrs = (ColorAttributes) text.getAttributes(ColorAttributes.ID);
		SelectionAttributes selAttrs = (SelectionAttributes) text.getAttributes(SelectionAttributes.ID);
		FontAttributes fontAttrs = (FontAttributes) text.getAttributes(FontAttributes.ID);

		if (colAttrs == null)
			colAttrs = DEFAULTCOLORATTRIBUTES;
		else if (colAttrs.filled) {
			this.graph.setColor(colAttrs.filledColor);
			// The reference point for Rectangle is the upper-left corner,
			// whereas it is the bottom-left corner for Font.drawString().
			this.graph.fillRect(loc.x, loc.y - bounds.height, bounds.width, bounds.height);
		}
		this.graph.setFont(fontAttrs.font);
		this.graph.setPaint(fontAttrs.fontColor);
		this.graph.drawString(text.getText(), loc.x, loc.y);

		if (selAttrs.isSelected()) drawHandler(text.getBounds());
	}

	@Override
	public void visitCollection(SCollection col) {
		boolean colSelected = ((SelectionAttributes) col.getAttributes(SelectionAttributes.ID)).isSelected();

		for (Iterator<Shape> it = col.getIterator(); it.hasNext();) {
			Shape current = it.next();
			current.accept(this);
			// TODO : is it useful to propagate the "selected" attribute to members ?
		}
		if (colSelected) drawHandler(col.getBounds());
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
		// LOGGER.log(Level.INFO, "Calling visitRectangle");
		Rectangle r = sel.getRect();
		ColorAttributes attrs = (ColorAttributes) sel.getAttributes(ColorAttributes.ID);
		SelectionAttributes selAttrs = (SelectionAttributes) sel.getAttributes(SelectionAttributes.ID);

		// LOGGER.log(Level.INFO, "ColorAttributes : \n{0}", attrs);
		if (attrs == null)
			attrs = DEFAULTCOLORATTRIBUTES;
		else if (attrs.filled) {
			this.graph.setColor(attrs.filledColor);
			this.graph.fillRect(r.x, r.y, r.width, r.height);
		}
		if (attrs.stroked) {
			this.graph.setColor(attrs.strokedColor);
			this.graph.drawRect(r.x, r.y, r.width, r.height);
		}
		if (selAttrs.isSelected()) drawHandler(sel.getBounds());
		
	}
}
