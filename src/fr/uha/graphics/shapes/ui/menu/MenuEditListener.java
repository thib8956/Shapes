package fr.uha.graphics.shapes.ui.menu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;

import fr.uha.graphics.shapes.SCollection;
import fr.uha.graphics.shapes.Shape;
import fr.uha.graphics.shapes.attributes.ColorAttributes;
import fr.uha.graphics.shapes.attributes.SelectionAttributes;
import fr.uha.graphics.shapes.ui.ShapesController;
import fr.uha.graphics.shapes.ui.ShapesView;
import fr.uha.graphics.ui.Controller;

public class MenuEditListener implements ActionListener {
	private static final Logger LOGGER = Logger.getLogger(SCollection.class.getName());
	
	private SCollection model;
	private ShapesView view;
	private ShapesController controller;
	
	public MenuEditListener(SCollection model, ShapesView view, Controller controller) {
		this.model = model;
		this.view = view;
		this.controller = (ShapesController) controller;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (e.getActionCommand().equals("Change color")){
			Color newColor = JColorChooser.showDialog(null, "Choose a color", Color.RED);
			changeColor(newColor);
		} else if (e.getActionCommand().equals("Change border color")){
			Color newColor = JColorChooser.showDialog(null, "Choose a color", Color.RED);
			changeBorderColor(newColor);
		} else if (e.getActionCommand().equals("Delete")) this.controller.deleteSelected();
		else if (e.getActionCommand().equals("Undo")) this.controller.undo();
		else if (source instanceof JCheckBoxMenuItem) {
			JCheckBoxMenuItem item = (JCheckBoxMenuItem) source;
			if (item.getText().equals("Draw border")) setBorder(item.isSelected());
			if (item.getText().equals("Fill Shape")) setShapeFilled(item.getState());
		}
		view.repaint();
	}
	
	private Color randomColor(){
		return new Color((int)(Math.random() * 0x1000000));
	}
	
	private void changeColor(Color filledColor){
		for (Iterator<Shape> it = model.getIterator(); it.hasNext();) {
			Shape current = (Shape)it.next();
			SelectionAttributes selAttrs = (SelectionAttributes) current.getAttributes(SelectionAttributes.ID);
			if ((selAttrs == null) || (! selAttrs.isSelected())) continue;
			
			ColorAttributes currentColAttrs = (ColorAttributes) current.getAttributes(ColorAttributes.ID);
			if (currentColAttrs != null) {
				current.addAttributes(new ColorAttributes(currentColAttrs.filled, currentColAttrs.stroked, filledColor, currentColAttrs.strokedColor));
			} else {
				current.addAttributes(new ColorAttributes(true, true, filledColor, Color.BLACK));
			}
		}
	}
	
	private void changeBorderColor(Color strockedColor){
		for (Iterator<Shape> it = model.getIterator(); it.hasNext();) {
			Shape current = (Shape)it.next();
			SelectionAttributes selAttrs = (SelectionAttributes) current.getAttributes(SelectionAttributes.ID);
			if ((selAttrs == null) || (! selAttrs.isSelected())) continue;
			
			ColorAttributes currentColAttrs = (ColorAttributes) current.getAttributes(ColorAttributes.ID);
			if (currentColAttrs != null) {
				current.addAttributes(new ColorAttributes(currentColAttrs.filled, currentColAttrs.stroked, currentColAttrs.filledColor, strockedColor));
			} else {
				current.addAttributes(new ColorAttributes(true, true, Color.WHITE, strockedColor));
			}
		}
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
	}
	
	/*
	 * Change the filled state for all selected shapes.
	 */
	private void setShapeFilled(boolean state){
		LOGGER.info("setShapeFilled(" + state + ")");
		for (Iterator<Shape> it=model.getIterator(); it.hasNext();){
			Shape current = it.next();
			SelectionAttributes selAttrs = (SelectionAttributes)current.getAttributes(SelectionAttributes.ID);
			if ((selAttrs == null) || (!selAttrs.isSelected())) continue;
			ColorAttributes colAttrs = (ColorAttributes) current.getAttributes(ColorAttributes.ID);
			colAttrs.filled = state;
			current.addAttributes(new ColorAttributes(colAttrs));
		}
	}

}
