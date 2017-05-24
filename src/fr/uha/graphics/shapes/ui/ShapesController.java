package fr.uha.graphics.shapes.ui;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.uha.graphics.shapes.SCollection;
import fr.uha.graphics.shapes.SSelection;
import fr.uha.graphics.shapes.Shape;
import fr.uha.graphics.shapes.attributes.SelectionAttributes;
import fr.uha.graphics.ui.Controller;

public class ShapesController extends Controller {

	private static final Logger LOGGER = Logger.getLogger(ShapesController.class.getName());
	private boolean shiftDown;
	private Point locClicked;
	private SSelection sel;
	private List<Shape> copyMem = new ArrayList<Shape>();
	private List<Shape> delMem = new ArrayList<Shape>();


	public ShapesController(Shape model) {
		super(model);

		this.locClicked = new Point();
		this.shiftDown = false;
		this.sel = new SSelection();
	}

	@Override
	public Object getModel() {
		return super.getModel();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		this.locClicked = e.getPoint();
		Shape target = getTarget();
		
		if (!shiftDown()){
			unselectAll();
		} 

		if (target != null){
			SelectionAttributes selAttrs = (SelectionAttributes) target.getAttributes(SelectionAttributes.ID);
			if (selAttrs != null){
				selAttrs.toggleSelection();
			}
		}
		getView().repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		SSelection currentSSelection = this.sel;
		for (Iterator<Shape> it = ((SCollection) this.getModel()).getIterator(); it.hasNext();) {
			Shape current = it.next();

			if (this.sel.getBounds().contains(current.getBounds())) {
				select(current);
				getView().repaint();
			}
			if (current instanceof SSelection) current = currentSSelection;
		}
		currentSSelection.setLoc(new Point(0, 0));
		currentSSelection.resize(0, 0);
		this.sel = currentSSelection;
		getView().repaint();
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		super.mouseDragged(evt);
		boolean anyShapeSelected = isAnyShapeSelected();
		
		for (Iterator<Shape> it = ((SCollection) this.getModel()).getIterator(); it.hasNext();) {
			Shape current = it.next();

			if ((!anyShapeSelected) && (current instanceof SSelection)) {
				this.sel = (SSelection) current;
				this.sel.setLoc(this.locClicked);
				this.sel.resize(evt.getX()-this.locClicked.x, evt.getY()-this.locClicked.y);
				getView().repaint();
			}
			// Translate all selected shapes.
			if(isSelected(current)){	
				int dx = evt.getPoint().x - current.getLoc().x;
				int dy = evt.getPoint().y - current.getLoc().y;
				translateSelected(dx, dy);
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent evt) {
		super.keyPressed(evt);
		switch (evt.getKeyCode()) {
		case KeyEvent.VK_SHIFT:
			shiftDown = true;
			break;
		case KeyEvent.VK_DELETE:
			deleteSelected();
			break;
		case KeyEvent.VK_Z:
			undo();
			break;
		case KeyEvent.VK_C:
			copy();
			break;
		case KeyEvent.VK_V:
			paste();
			break;
		default:
			break;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent evt) {
		super.keyReleased(evt);
		switch (evt.getKeyCode()) {
		case KeyEvent.VK_DELETE:
			deleteSelected();
			break;
		case KeyEvent.VK_SHIFT:
			shiftDown = false;
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
		for (Iterator<Shape> it = ((SCollection) this.getModel()).getIterator(); it.hasNext();) {
			Shape current = it.next();
			if (isSelected(current)) current.translate(dx, dy);
		}
		getView().repaint();
	}

	public void deleteSelected(){
		ArrayList<Shape> toDelete = new ArrayList<Shape>();
		for (Iterator<Shape> it = ((SCollection) this.getModel()).getIterator(); it.hasNext();) {
			Shape current = it.next();
			if(isSelected(current)){	
				this.delMem.add(current);
				toDelete.add(current);
			}
		}
		for (Shape current: toDelete){
			LOGGER.log(Level.INFO, "Removing shape {0}", current);
			((SCollection)this.getModel()).remove(current);
		}
		unselectAll(); // unselectAll() takes care of the graphical update (repaint).
	}

	public void unselectAll() {
		for (Iterator<Shape> it = ((SCollection) this.getModel()).getIterator(); it.hasNext();) {
			Shape current = it.next();
			unselect(current);
		}
		getView().repaint();
	}

	public boolean shiftDown() {
		return this.shiftDown;
	}

	private boolean isAnyShapeSelected(){
		for (Iterator<Shape> it = ((SCollection) this.getModel()).getIterator(); it.hasNext();) {
			Shape current = it.next();
			if (isSelected(current)) return true;
		}
		return false;
	}

	private boolean isSelected(Shape s) {
		SelectionAttributes attrs = (SelectionAttributes) s.getAttributes(SelectionAttributes.ID);
		if (attrs == null) return false;
		return attrs.isSelected();
	}

	private int select(Shape s) {
		LOGGER.log(Level.INFO, "Selecting {0}", s);
		SelectionAttributes attrs = (SelectionAttributes) s.getAttributes(SelectionAttributes.ID);
		// If this shape has no selection attributes
		if (attrs == null) return -1; 
		attrs.select();
		return 0;
	}

	private int unselect(Shape s) {
		SelectionAttributes attrs = (SelectionAttributes) s.getAttributes(SelectionAttributes.ID);
		// If this shape has no selection attributes
		if (attrs == null) return -1; 
		attrs.unselect();
		return 0;
	}
	
	private void undo(){
		ListIterator<Shape> it = delMem.listIterator();
		while(it.hasNext()){
			Shape str = it.next();
			((SCollection) getModel()).add(str);
		}
		delMem.clear();
		getView().repaint();
	}
	
	private void copy(){
		for (Iterator<Shape> it = ((SCollection) this.getModel()).getIterator(); it.hasNext();) {
			Shape current = it.next();
			if(isSelected(current)){	
				copyMem.add(current);
			}
		}
	}
	
	private void paste(){
		ListIterator<Shape> it = copyMem.listIterator();
		while(it.hasNext()){
			Shape str = it.next();
			((SCollection) getModel()).add(str);
		}
		copyMem.clear();
		getView().repaint();
	}
}
