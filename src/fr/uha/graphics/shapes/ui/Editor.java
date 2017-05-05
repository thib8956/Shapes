package fr.uha.graphics.shapes.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import fr.uha.graphics.shapes.SCircle;
import fr.uha.graphics.shapes.SCollection;
import fr.uha.graphics.shapes.SRectangle;
import fr.uha.graphics.shapes.SSelection;
import fr.uha.graphics.shapes.SText;
import fr.uha.graphics.shapes.STriangle;
import fr.uha.graphics.shapes.attributes.ColorAttributes;
import fr.uha.graphics.shapes.attributes.SelectionAttributes;

public class Editor extends JFrame {
	private static final Logger LOGGER = Logger.getLogger(Editor.class.getName());
	private static FileHandler fh = null;
	private static final Dimension WIN_SIZE = new Dimension(800, 600);

	private ShapesView sview;
	protected static SCollection model;
	private JMenuBar menubar;

	public Editor() {
		super("Shapes Editor");

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent evt) {
				System.exit(0);
			}
		});

		this.buildModel();
		this.buildMenu();

		this.sview = new ShapesView(this.model);
		this.sview.setPreferredSize(WIN_SIZE);
		this.getContentPane().add(this.sview, java.awt.BorderLayout.CENTER);
	}

	private static void initLogger() {
		try {
			fh = new FileHandler("Shapes.log", false);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
		Logger l = Logger.getLogger("");
		fh.setFormatter(new SimpleFormatter());
		l.addHandler(fh);
		l.setLevel(Level.INFO);
	}

	private void buildModel() {
		this.model = new SCollection();
		this.model.addAttributes(new SelectionAttributes());
		
		SSelection sel=new SSelection(new Point (0,0),0,0);
		sel.addAttributes(new ColorAttributes(false, true, Color.BLACK, Color.BLACK));
//		sel.addAttributes(new SelectionAttributes());
		this.model.add(sel);
		
		SRectangle r = new SRectangle(new Point(10, 10), 40, 60);
		r.addAttributes(new ColorAttributes(true, true, Color.BLUE, Color.BLACK));
		r.addAttributes(new SelectionAttributes());
		this.model.add(r);

		SCircle c = new SCircle(new Point(100, 100), 30);
		c.addAttributes(new ColorAttributes(false, true, Color.RED, Color.RED));
		c.addAttributes(new SelectionAttributes());
		this.model.add(c);
//
//		SText t = new SText(new Point(150, 150), "hello");
//		t.addAttributes(new ColorAttributes(true, true, Color.YELLOW, Color.BLUE));
//		t.addAttributes(new FontAttributes());
//		t.addAttributes(new SelectionAttributes());
//		this.model.add(t);
//
//		SCollection sc = new SCollection();
//		sc.addAttributes(new SelectionAttributes());
//		r = new SRectangle(new Point(20, 30), 60, 60);
//		r.addAttributes(new ColorAttributes(true, false, Color.MAGENTA, Color.BLUE));
//		r.addAttributes(new SelectionAttributes());
//		sc.add(r);
//		
//		c = new SCircle(new Point(150, 100), 40);
//		c.addAttributes(new ColorAttributes(false, true, Color.BLUE, Color.DARK_GRAY));
//		c.addAttributes(new SelectionAttributes());
//		sc.add(c);
//		this.model.add(sc);
//
//		STriangle tri = new STriangle(new Point(200, 200), 50);
//		tri.addAttributes(new ColorAttributes(true, true, Color.YELLOW, Color.BLACK));
//		tri.addAttributes(new SelectionAttributes());
//		this.model.add(tri);

	}
	
	private void buildMenu(){
		this.menubar = new JMenuBar();
		JMenu menuAdd = new JMenu("Add");
		JMenuItem addRectItem = new JMenuItem("Add SRectangle");
		JMenuItem addCircleItem = new JMenuItem("Add SCircle");
		JMenuItem addTriItem = new JMenuItem("Add STriangle");
		
		addRectItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SRectangle r = new SRectangle(new Point(WIN_SIZE.height/2, WIN_SIZE.width/2), 50, 50);
				r.addAttributes(new ColorAttributes(true, false, randomColor(), null));
				r.addAttributes(new SelectionAttributes());
				model.add(r);
				sview.repaint();
			}
		});
		
		addCircleItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SCircle c = new SCircle(new Point(WIN_SIZE.height/2, WIN_SIZE.width/2), 50);
				c.addAttributes(new SelectionAttributes());
				c.addAttributes(new ColorAttributes(true, false, randomColor(), null));
				model.add(c);
				sview.repaint();
			}
		});
		
		addTriItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				STriangle t = new STriangle(new Point(WIN_SIZE.height/2, WIN_SIZE.width/2), 50);
				t.addAttributes(new SelectionAttributes());
				t.addAttributes(new ColorAttributes(true, false, randomColor(), null));
				model.add(t);
				sview.repaint();
			}
		});
		menuAdd.add(addRectItem);
		menuAdd.add(addCircleItem);
		menuAdd.add(addTriItem);
		
		JMenuItem backItem = new JMenuItem("Back");
		JMenuItem forwardItem = new JMenuItem("Forward");
		
		menubar.add(menuAdd);
		menubar.add(backItem);
		menubar.add(forwardItem);

		this.setJMenuBar(this.menubar);
	}
	
	private Color randomColor(){
		return new Color((int)(Math.random() * 0x1000000));
	}

	public static void main(String[] args) {
		Editor.initLogger();

		Editor self = new Editor();
		self.pack();
		self.sview.requestFocusInWindow();
		self.setVisible(true);

	}
}
