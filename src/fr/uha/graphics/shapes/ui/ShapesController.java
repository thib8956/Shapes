package fr.uha.graphics.shapes.ui;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.uha.graphics.shapes.SCollection;
import fr.uha.graphics.shapes.Shape;
import fr.uha.graphics.shapes.attributes.SelectionAttributes;
import fr.uha.graphics.ui.Controller;

public class ShapesController extends Controller {

	private static final Logger LOGGER = Logger.getLogger(ShapesController.class.getName());
	private boolean shiftDown;
	private Shape s;
	private Point locClicked;

	public ShapesController(Shape model) {
		super(model);

		this.locClicked = new Point();
		this.shiftDown = false;
	}

	@Override
	public Object getModel() {
		return super.getModel();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		if (!shiftDown()) unselectAll();
		this.locClicked = e.getPoint();
		s = getTarget();
		if (s != null) {
			SelectionAttributes sel = (SelectionAttributes) s.getAttributes(SelectionAttributes.ID);
			sel.toggleSelection();
		}
		getView().repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// if(!shiftDown()) unselectAll();
		for (Iterator<Shape> it = ((SCollection) this.getModel()).getIterator(); it.hasNext();) {
			Shape current = it.next();
			SelectionAttributes sattrs = (SelectionAttributes) current.getAttributes(SelectionAttributes.ID);
			LOGGER.log(Level.INFO, "Shape {0} isSelected : {1}", new Object[] { current, sattrs.isSelected() });

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		this.locClicked = e.getPoint();
		s = getTarget();
		if (s != null) {
			SelectionAttributes sel = (SelectionAttributes) s.getAttributes(SelectionAttributes.ID);
			sel.toggleSelection();
		} else {
			LOGGER.log(Level.INFO, "Point clicked : x={0} y={1}", new Object[] { locClicked.x, locClicked.y });
			if (!shiftDown()) unselectAll();
		}
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		super.mouseDragged(evt);
		if (this.s == null) return;
		int dx = evt.getPoint().x - s.getLoc().x;
		int dy = evt.getPoint().y - s.getLoc().y;
		translateSelected(dx, dy); // s is defined into mousePressed()
	}

	@Override
	public void keyPressed(KeyEvent evt) {
		super.keyPressed(evt);
		if ((evt.getKeyCode() == KeyEvent.VK_SHIFT)) {
			shiftDown = true;
			LOGGER.info("Shift pressed");
		} else if ((evt.getKeyCode() == KeyEvent.VK_DELETE)){
			LOGGER.info("Delete pressed");
			deleteSelected();
		}
	}

	@Override
	public void keyReleased(KeyEvent evt) {
		super.keyReleased(evt);
		if ((evt.getKeyCode() == KeyEvent.VK_SHIFT)) {
			shiftDown = false;
			LOGGER.info("Shift released");
		} else if ((evt.getKeyCode() == KeyEvent.VK_DELETE)){
			LOGGER.info("Delete released");
		}
	}

	public Shape getTarget() {
		SCollection model = (SCollection) getModel();
		Iterator<Shape> it = model.getIterator();
		while (it.hasNext()) {
			Shape current = it.next();
			if (current.getBounds().contains(this.locClicked)) {
				LOGGER.log(Level.INFO, "Shape selected : {0}", current);
				return current;
			}
		}
		return null;
	}

	public void translateSelected(int dx, int dy) {
		LOGGER.log(Level.FINE, "Translate : x={0}, y={1}", new Object[] { s.getLoc().x, s.getLoc().y });
		this.s.translate(dx, dy);
		getView().repaint();
	}
	
	public void deleteSelected(){
		// TODO : IndexOutOfBoundsException while deleting the last shape
		// is an empy model allowed ?
		if (s == null) return;
		((SCollection)this.getModel()).remove(s);
		unselectAll(); // unselectAll() takes care of the graphical update (repaint).
		LOGGER.log(Level.INFO, "Removing shape {0}", s);
	}

	public void unselectAll() {
		for (Iterator<Shape> it = ((SCollection) this.getModel()).getIterator(); it.hasNext();) {
			Shape current = it.next();
			((SelectionAttributes) current.getAttributes(SelectionAttributes.ID)).unselect();
		}
		this.s = null;
		getView().repaint();
	}

	public boolean shiftDown() {
		return this.shiftDown;
	}

}
