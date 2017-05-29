package fr.uha.graphics.shapes.ui;

import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.uha.graphics.shapes.Shape;
import fr.uha.graphics.ui.Controller;
import fr.uha.graphics.ui.View;

public class ShapesView extends View {

	private static final Logger LOGGER = Logger.getLogger(ShapesView.class.getName());

	private ShapeDraftman draftman;

	public ShapesView(Shape model) {
		super(model);
		LOGGER.log(Level.INFO, "ShapesView({0})", model);
	}

	@Override
	public void paintComponent(Graphics component) {
		// Draw the default (blank) JPanel
		super.paintComponent(component);
		// LOGGER.log(Level.INFO, "Drawing component : {0}", component);
		Shape model = (Shape) this.getModel();
		this.draftman = new ShapeDraftman(component);
		model.accept(draftman);
	}

	@Override
	public Controller defaultController(Object model) {
		return new ShapesController((Shape) this.getModel());
	}

}
