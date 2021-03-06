package fr.uha.graphics.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Controller implements MouseListener, MouseMotionListener, KeyListener {
	protected Object model;
	private View view;

	public Controller(Object newModel) {
		model = newModel;
	}

	public void setView(View view) {
		this.view = view;
	}

	final public View getView() {
		return this.view;
	}

	public void setModel(Object model) {
		this.model = model;
	}

	public Object getModel() {
		return this.model;
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent evt) {
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
	}

	@Override
	public void keyTyped(KeyEvent evt) {
	}

	@Override
	public void keyPressed(KeyEvent evt) {
	}

	@Override
	public void keyReleased(KeyEvent evt) {
	}
	
}
