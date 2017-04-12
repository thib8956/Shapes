package fr.uha.graphics.shapes.ui;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.uha.graphics.shapes.SCollection;
import fr.uha.graphics.shapes.Shape;
import fr.uha.graphics.shapes.attributes.ColorAttributes;
import fr.uha.graphics.shapes.attributes.SelectionAttributes;
import fr.uha.graphics.ui.Controller;

public class ShapesController extends Controller{

	private static final Logger LOGGER = Logger.getLogger(ShapesController.class.getName());
	private boolean shift_down;
	private Shape s;
	private Point locClicked;



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
//		if(s!=null){
//			SelectionAttributes sel1 = (SelectionAttributes)s.getAttributes(SelectionAttributes.ID);
//			if(sel1.isSelected()){
//				s=getTarget();
//			}
//		}
		
		this.locClicked = e.getPoint();
		s = getTarget();
		if(s!=null){
			SelectionAttributes sel2 = (SelectionAttributes)s.getAttributes(SelectionAttributes.ID);
			sel2.toggleSelection();
		}
//		if(!shiftDown()) unselectAll();	
		getView().repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseClicked(e);
		this.locClicked = e.getPoint();
		s = getTarget();
		if(s!=null){
			SelectionAttributes sel2 = (SelectionAttributes)s.getAttributes(SelectionAttributes.ID);
			sel2.toggleSelection();
		}
	//	if(!shiftDown()) unselectAll();
		getView().repaint();

	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		// TODO Auto-generated method stub

		super.mouseDragged(evt);
//		s=getTarget();
//		LOGGER.log(Level.INFO, "Drag started, s={0}",s);
//		if(s!=null){
		translateSelected(evt.getPoint().x-s.getLoc().x,evt.getPoint().y-s.getLoc().y);
		}
		
//	}

	@Override
	public void keyPressed(KeyEvent evt) {
		// TODO Auto-generated method stub
		super.keyPressed(evt);
		if((evt.getKeyCode()==KeyEvent.VK_SHIFT)) shift_down=true;
	}

	@Override
	public void keyReleased(KeyEvent evt) {
		// TODO Auto-generated method stub
		super.keyReleased(evt);
		if((evt.getKeyCode()==KeyEvent.VK_SHIFT)) shift_down=false;
	}

	public Shape getTarget(){
		SCollection model = (SCollection)getModel();
		Iterator<Shape> it = model.getIterator();
		while (it.hasNext()){
			Shape current = it.next();
			LOGGER.log(Level.INFO, "[getTarget] Current bounds : {0}",current.getBounds());
			if (current.getBounds().contains(this.locClicked)){
				LOGGER.log(Level.INFO, "Shape selected : {0}", current);
				return current;
			}
		}
		return null;
	}

	public void translateSelected(int x, int y){
		LOGGER.log(Level.INFO, "Translate started");
		LOGGER.log(Level.INFO, "s={0}", s );
		LOGGER.log(Level.INFO, "x={0}", x );
		LOGGER.log(Level.INFO, "y={0}", y );
		s.translate(x, y);
		getView().repaint();
	}

//	public void unselectAll(){
//		if(s!=null){
//			SelectionAttributes sel3 = (SelectionAttributes)s.getAttributes(SelectionAttributes.ID);
//			sel3.unselect();
//			s=null;
//		}
//	}

	public boolean shiftDown(){  	
		return shift_down;
	}

}
