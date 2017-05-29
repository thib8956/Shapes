package fr.uha.graphics.shapes.ui;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.uha.graphics.shapes.SCircle;
import fr.uha.graphics.shapes.SCollection;
import fr.uha.graphics.shapes.SRectangle;
import fr.uha.graphics.shapes.SSelection;
import fr.uha.graphics.shapes.SText;
import fr.uha.graphics.shapes.STriangle;
import fr.uha.graphics.shapes.Shape;
import fr.uha.graphics.shapes.attributes.ColorAttributes;
import fr.uha.graphics.shapes.attributes.FontAttributes;
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
		case KeyEvent.VK_H:
			LOGGER.info("HTML/CSS generated");
			try {
				generateHtml();
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, e.getMessage());
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent evt) {
		super.keyReleased(evt);
		switch (evt.getKeyCode()) {
		case KeyEvent.VK_SHIFT:
			shiftDown = false;
			break;
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

	public void generateHtml() throws IOException {
		PrintWriter index = new PrintWriter("index.html", "UTF-8");
		PrintWriter style = new PrintWriter("style.css", "UTF-8");

		index.println("<!DOCTYPE htm>");
		index.println("<html lang=\"fr\">");
		index.println("<head>");
		index.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
		index.println("<link rel=\"stylesheet\" href=\"style.css\" />");
		index.println("<title>TP Shape COLICCHIO et GASSER</title>");
		index.println("</head>");
		index.println("<body>");

		for (Iterator<Shape> it = ((SCollection) this.getModel()).getIterator(); it.hasNext();) {
			Shape current = it.next();
			index.println(current.htmlShape());
		}
		index.flush();
		index.println("<footer>");
		index.println("<p> 2017 Crée par Alexandre COLICCHIO et Thibaud GASSER - Site généré automatiquement dans le cadre de notre TP Shape </p>");
		index.println("</footer>");
		index.println("</body>");
		index.println("</html>");
		index.close();

		style.println("footer{text-align:center;margin:auto;height:50px;position:fixed;bottom:0;font-weight:bold;}");

		for (Iterator<Shape> it = ((SCollection) this.getModel()).getIterator(); it.hasNext();) {
			Shape current = it.next();
			style.println(current.cssShape());
		}
		style.close();
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

	public void undo(){
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
		SCollection model = (SCollection) this.getModel();
		for (Iterator<Shape> it = copyMem.iterator(); it.hasNext();) {
			Shape current = (Shape) it.next();
			Shape s = cloneShape(current);
			model.add(s);
		}
		copyMem.clear();
		getView().repaint();
	}
	
	private Shape cloneShape(Shape s){
		Rectangle bounds = s.getBounds();
		Shape newShape = null;
		
		if (s instanceof STriangle) {
			newShape = new STriangle(new Point(), bounds.width);
		} else if (s instanceof SRectangle) {
			newShape = new SRectangle(new Point(), bounds.width, bounds.height);
		} else if (s instanceof SCircle) {
			newShape = new SCircle(new Point(), bounds.width);
		} else if (s instanceof SText) {
			System.out.println("Texte : " + ((SText) s).getText());
			newShape = new SText(new Point(0, bounds.height), ((SText) s).getText());
			newShape.addAttributes((FontAttributes) s.getAttributes(FontAttributes.ID));
		}
		newShape.addAttributes(new SelectionAttributes());
		newShape.addAttributes(s.getAttributes(ColorAttributes.ID));
		return newShape;
	}
}
