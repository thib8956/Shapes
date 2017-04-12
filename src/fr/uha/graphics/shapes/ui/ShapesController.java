package fr.uha.graphics.shapes.ui;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

import fr.uha.graphics.shapes.Shape;
import fr.uha.graphics.ui.Controller;

public class ShapesController extends Controller {
    
    private static final Logger LOGGER = Logger.getLogger(ShapesController.class.getName());

    public ShapesController(Shape model) {
	super(model);
	// TODO Auto-generated constructor stub
    }

    @Override
    public Object getModel() {
	// TODO Auto-generated method stub
	return super.getModel();
    }

    @Override
    public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	super.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	super.mouseReleased(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	super.mouseClicked(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	super.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	super.mouseExited(e);
    }

    @Override
    public void mouseMoved(MouseEvent evt) {
	// TODO Auto-generated method stub
	super.mouseMoved(evt);
    }

    @Override
    public void mouseDragged(MouseEvent evt) {
	// TODO Auto-generated method stub
	super.mouseDragged(evt);
    }

    @Override
    public void keyPressed(KeyEvent evt) {
	// TODO Auto-generated method stub
	super.keyPressed(evt);
    }

    @Override
    public void keyReleased(KeyEvent evt) {
	// TODO Auto-generated method stub
	super.keyReleased(evt);
    }
    
    public Shape getTarget(){
	return null;
    }
    
    public void translateSelected(int x, int y){
	//Iterator it = ()(this.getModel()).iterator()
	
    }
    
    public void unselectAll(){
	
    }

}
