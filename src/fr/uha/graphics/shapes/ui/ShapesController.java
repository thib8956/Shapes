package fr.uha.graphics.shapes.ui;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
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
		this.locClicked = e.getPoint();
		Shape s = getTarget();
		
		if (!shiftDown()){
			unselectAll();
		} 
		if (getTarget() != null){
			SelectionAttributes selAttrs = (SelectionAttributes) s.getAttributes(SelectionAttributes.ID);
			if (selAttrs != null){
				selAttrs.toggleSelection();
			}
		}
		getView().repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
//		super.mousePressed(e);
//		this.locClicked = e.getPoint();
//		Shape s = getTarget();
//		
//		if ((s == null) && (!shiftDown())){
//			unselectAll();
//		} else {
//			SelectionAttributes selAttrs = (SelectionAttributes) s.getAttributes(SelectionAttributes.ID);
//			if (selAttrs != null){
//				selAttrs.toggleSelection();
//			}
//		}
//		getView().repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
//		// if(!shiftDown()) unselectAll();
//		for (Iterator<Shape> it = ((SCollection) this.getModel()).getIterator(); it.hasNext();) {
//			Shape current = it.next();
//			SelectionAttributes attrs = (SelectionAttributes) current.getAttributes(SelectionAttributes.ID);
////			LOGGER.log(Level.INFO, "Selector size: {0}", new Object[] {sel.getBounds()});
//			
//			if (sel.getBounds().contains(current.getLoc())) {
//				if (attrs != null) attrs.select();
//				getView().repaint();
//			}			
//		}
//		
		for (Iterator<Shape> it = ((SCollection) this.getModel()).getIterator(); it.hasNext();) {
			Shape current = it.next();
			if(current instanceof SSelection){
				((SSelection)current).setLoc(new Point(0,0));
				((SSelection)current).resize(0, 0);
				getView().repaint();
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		super.mouseDragged(evt);
		//SSelection sel = new SSelection(new Point (0,0),0,0);
//		if (!isShapeSel()){

		for (Iterator<Shape> it = ((SCollection) this.getModel()).getIterator(); it.hasNext();) {
			Shape current = it.next();
//			SelectionAttributes attrs = (SelectionAttributes) current.getAttributes(SelectionAttributes.ID);

//			if (current instanceof SSelection){
//				this.sel = (SSelection) current;
//				this.sel.setLoc(this.locClicked);
//				this.sel.resize(evt.getX()-this.locClicked.x, evt.getY()-this.locClicked.y);
//				getView().repaint();
//			} else if (sel.getBounds().contains(current.getLoc())) {
//				select(current);
//				getView().repaint();
//			}
			if(isSelected(current)){	
				int dx = evt.getPoint().x - current.getLoc().x;
				int dy = evt.getPoint().y - current.getLoc().y;
				translateSelected(dx, dy);
			}
		}
	}

	@Deprecated
	private boolean isShapeSel(){
		for (Iterator<Shape> it = ((SCollection) this.getModel()).getIterator(); it.hasNext();) {
			Shape current = it.next();
			SelectionAttributes attrs = (SelectionAttributes) current.getAttributes(SelectionAttributes.ID);
			if((attrs != null)&&(attrs.isSelected())){	
				return true;
			}
		}
		return false;
	}
	
	private boolean isSelected(Shape s){
		SelectionAttributes attrs = (SelectionAttributes) s.getAttributes(SelectionAttributes.ID);
		if (attrs == null) return false;
		return attrs.isSelected();
	}
	
	private int select(Shape s){
		SelectionAttributes attrs = (SelectionAttributes) s.getAttributes(SelectionAttributes.ID);
		// If this shape has no selection attributes
		if (attrs == null) return -1; 
		attrs.select();
		return 0;
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
		else if (evt.getKeyCode() == KeyEvent.VK_Z){
				 LOGGER.info("Z pressed: Back");
		}
		else if (evt.getKeyCode() == KeyEvent.VK_C){
			LOGGER.info("C pressed: Copy");
		}
		else if (evt.getKeyCode() == KeyEvent.VK_V){
			LOGGER.info("V pressed: Paste");
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
			deleteSelected();
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
		//LOGGER.log(Level.FINE, "Translate : x={0}, y={1}", new Object[] { s.getLoc().x, s.getLoc().y });
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
			SelectionAttributes attrs = (SelectionAttributes) current.getAttributes(SelectionAttributes.ID);
			if (attrs != null) attrs.unselect();
		}
		getView().repaint();
	}

	public boolean shiftDown() {
		return this.shiftDown;
	}

}
