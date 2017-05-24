package fr.uha.graphics.shapes.ui.menu;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.uha.graphics.shapes.SCircle;
import fr.uha.graphics.shapes.SCollection;
import fr.uha.graphics.shapes.SRectangle;
import fr.uha.graphics.shapes.SText;
import fr.uha.graphics.shapes.STriangle;
import fr.uha.graphics.shapes.Shape;
import fr.uha.graphics.shapes.attributes.ColorAttributes;
import fr.uha.graphics.shapes.attributes.FontAttributes;
import fr.uha.graphics.shapes.attributes.SelectionAttributes;
import fr.uha.graphics.shapes.ui.ShapesView;

public class MenuAddListener implements ActionListener {
	private String shape;
	private ShapesView view;
	private SCollection model;

	public MenuAddListener(String shape, SCollection model, ShapesView view) {
		this.shape = shape;
		this.model = model;
		this.view = view;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Shape s;
		Rectangle bounds = model.getBounds();
		// All new shapes will be in the center of the panel.
		Point loc = new Point((int)bounds.getCenterX(), (int)bounds.getCenterY());
		
		if (this.shape.equals("SRectangle")){
			s = new SRectangle(loc, 50, 50);
		} else if (this.shape.equals("SCircle")){
			s = new SCircle(loc, 50);
		} else if (this.shape.equals("STriangle")){
			s = new STriangle(loc, 50);
		} else if (this.shape.equals("SText")){
			s = new SText(loc, "Lorem ipsum");
			s.addAttributes(new FontAttributes());
		}
		else return;
		
		s.addAttributes(new SelectionAttributes());
		s.addAttributes(new ColorAttributes(true, false, randomColor(), null));
		model.add(s);
		view.repaint();
	}
	
	private Color randomColor(){
		return new Color((int)(Math.random() * 0x1000000));
	}

}
