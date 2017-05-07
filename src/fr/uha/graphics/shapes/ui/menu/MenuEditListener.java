package fr.uha.graphics.shapes.ui.menu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.swing.JCheckBoxMenuItem;

import fr.uha.graphics.shapes.SCollection;
import fr.uha.graphics.shapes.Shape;
import fr.uha.graphics.shapes.attributes.ColorAttributes;
import fr.uha.graphics.shapes.attributes.SelectionAttributes;
import fr.uha.graphics.shapes.ui.ShapesView;

public class MenuEditListener implements ActionListener {
	private static final Logger LOGGER = Logger.getLogger(SCollection.class.getName());
	
	private SCollection model;
	private ShapesView view;
	
	public MenuEditListener(SCollection model, ShapesView view) {
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (e.getActionCommand().equals("Change color")) changeColor();
		if (source instanceof JCheckBoxMenuItem){
			JCheckBoxMenuItem item = (JCheckBoxMenuItem) source;
			if (item.getText().equals("Draw border")) setBorder(item.isSelected());
			if (item.getText().equals("Fill Shape")) setShapeFilled(item.getState());
		}
	}
	
	private Color randomColor(){
		return new Color((int)(Math.random() * 0x1000000));
	}
	
	/*
	 * Replace the color of all selected shapes with a random color.
	 */
	private void changeColor(){
		for (Iterator<Shape> it = model.getIterator(); it.hasNext();) {
			Shape current = (Shape)it.next();
			SelectionAttributes selAttrs = (SelectionAttributes)current.getAttributes(SelectionAttributes.ID);
			if ((selAttrs == null) || (! selAttrs.isSelected())) continue;
			current.addAttributes(new ColorAttributes(true, true, randomColor(), randomColor()));
		}
		view.repaint();
	}
	
	/*
	 * Change the state of the border of all selected shapes.
	 */
	private void setBorder(boolean state){
		for (Iterator<Shape> it=model.getIterator(); it.hasNext();){
			Shape current = (Shape)it.next();
			SelectionAttributes selAttrs = (SelectionAttributes)current.getAttributes(SelectionAttributes.ID);
			if ((selAttrs == null) || (! selAttrs.isSelected())) continue;
			ColorAttributes colAttrs = (ColorAttributes) current.getAttributes(ColorAttributes.ID);
			colAttrs.stroked = state;
			current.addAttributes(new ColorAttributes(colAttrs));
		}
		view.repaint();
	}
	
	/*
	 * 
	 */
	private void setShapeFilled(boolean state){
		LOGGER.info("setShapeFilled(" + state + ")");
		for (Iterator<Shape> it=model.getIterator(); it.hasNext();){
			Shape current = (Shape)it.next();
			SelectionAttributes selAttrs = (SelectionAttributes)current.getAttributes(SelectionAttributes.ID);
			if ((selAttrs == null) || (!selAttrs.isSelected())) continue;
			ColorAttributes colAttrs = (ColorAttributes) current.getAttributes(ColorAttributes.ID);
			colAttrs.filled = state;
			current.addAttributes(new ColorAttributes(colAttrs));
		}
		view.repaint();
	}

}
