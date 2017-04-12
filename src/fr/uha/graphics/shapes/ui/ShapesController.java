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

public class ShapesController extends Controller{

    private static final Logger LOGGER = Logger.getLogger(ShapesController.class.getName());
    private boolean shiftDown;
    private Shape selected ;
    private Point locClicked;



    public ShapesController(Shape model) {
	super(model);
    }

    @Override
    public Object getModel() {
	return super.getModel();
    }

    @Override
    public void mousePressed(MouseEvent e) {
	super.mousePressed(e);
	this.locClicked = e.getPoint();
	selected  = getTarget();
	if(selected !=null){
	    SelectionAttributes sel = (SelectionAttributes)selected .getAttributes(SelectionAttributes.ID);
	    sel.toggleSelection();
	}
	getView().repaint();
	
	for (Iterator<Shape> it = ((SCollection)this.getModel()).getIterator(); it.hasNext();) {
	    Shape s = it.next();
	    SelectionAttributes sattrs = (SelectionAttributes)s.getAttributes(SelectionAttributes.ID);
	    LOGGER.log(Level.INFO, "Shape {0} isSelected : {1}", new Object[]{s, sattrs.isSelected()});
	}
    }

    @Override
    public void mouseReleased(MouseEvent e) {
	if(!shiftDown()) unselectAll();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
	super.mouseClicked(e);
	this.locClicked = e.getPoint();
	selected  = getTarget();
	if(selected !=null){
	    SelectionAttributes sel = (SelectionAttributes)selected .getAttributes(SelectionAttributes.ID);
	    sel.toggleSelection();
	}
//	if(!shiftDown()) unselectAll();
    }

    @Override
    public void mouseDragged(MouseEvent evt) {
	//LOGGER.log(Level.INFO, "s={0}", s );
	super.mouseDragged(evt);
	int dx = evt.getPoint().x-selected .getLoc().x;
	int dy = evt.getPoint().y-selected .getLoc().y;
	if(selected !=null) translateSelected(dx, dy); // s define into mousePressed
    }

    @Override
    public void keyPressed(KeyEvent evt) {
	super.keyPressed(evt);
	LOGGER.log(Level.INFO, "Key used");
	if((evt.getKeyCode()==KeyEvent.VK_SHIFT)){
	    shiftDown=true;
	    LOGGER.log(Level.INFO, "Shift On" );
	}
    }

    @Override
    public void keyReleased(KeyEvent evt) {
	super.keyReleased(evt);
	if((evt.getKeyCode()==KeyEvent.VK_SHIFT)){
	    shiftDown=false;
	    LOGGER.log(Level.INFO, "Shift Off" );
	}
    }

    public Shape getTarget(){
	SCollection model = (SCollection)getModel();
	Iterator<Shape> it = model.getIterator();
	while (it.hasNext()){
	    Shape current = it.next();
//	    LOGGER.log(Level.INFO, "[getTarget] Current bounds : {0}",current.getBounds());
	    if (current.getBounds().contains(this.locClicked)){
//		LOGGER.log(Level.INFO, "Shape selected : {0}", current);
		return current;
	    }
	}
	return null;
    }

    public void translateSelected(int x, int y){
	LOGGER.log(Level.INFO, "Translate started");
	LOGGER.log(Level.INFO, "s={0}", selected  );
	LOGGER.log(Level.INFO, "x={0}", x );
	LOGGER.log(Level.INFO, "y={0}", y );
	selected .translate(x, y);
	getView().repaint();
    }

    public void unselectAll(){
	if(selected != null){
	    SelectionAttributes sel = (SelectionAttributes)selected .getAttributes(SelectionAttributes.ID);
	    sel.unselect();
	    selected =null;
	}
    }

    public boolean shiftDown(){  	
	return this.shiftDown;
    }

}
