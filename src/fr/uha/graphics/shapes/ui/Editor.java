package fr.uha.graphics.shapes.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import fr.uha.graphics.shapes.SCircle;
import fr.uha.graphics.shapes.SCollection;
import fr.uha.graphics.shapes.SRectangle;
import fr.uha.graphics.shapes.SSelection;
import fr.uha.graphics.shapes.SText;
import fr.uha.graphics.shapes.STriangle;
import fr.uha.graphics.shapes.attributes.ColorAttributes;
import fr.uha.graphics.shapes.attributes.FontAttributes;
import fr.uha.graphics.shapes.attributes.SelectionAttributes;
import fr.uha.graphics.shapes.ui.menu.MenuAddListener;
import fr.uha.graphics.shapes.ui.menu.MenuEditListener;

public class Editor extends JFrame {
	private static final Logger LOGGER = Logger.getLogger(Editor.class.getName());
	private static FileHandler fh = null;
	public static final Dimension WIN_SIZE = new Dimension(800, 600);

	private ShapesView sview;
	private SCollection model;
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

		this.sview = new ShapesView(this.model);
		this.sview.setPreferredSize(WIN_SIZE);
		this.getContentPane().add(this.sview, java.awt.BorderLayout.CENTER);
		this.buildMenu();
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
		this.model.add(sel);

		SRectangle r = new SRectangle(new Point(10, 10), 40, 60);
		r.addAttributes(new ColorAttributes(true, true, Color.BLUE, Color.BLACK));
		r.addAttributes(new SelectionAttributes());
		this.model.add(r);

		SCircle c = new SCircle(new Point(100, 100), 30);
		c.addAttributes(new ColorAttributes(false, true, Color.RED, Color.RED));
		c.addAttributes(new SelectionAttributes());
		this.model.add(c);

		SText t = new SText(new Point(150, 150), "hello");
		t.addAttributes(new ColorAttributes(true, false, Color.YELLOW, Color.BLUE));
		t.addAttributes(new FontAttributes(new Font("Arial", Font.BOLD, 30), Color.BLUE));
		t.addAttributes(new SelectionAttributes());
		this.model.add(t);

		SCollection sc = new SCollection();
		sc.addAttributes(new SelectionAttributes());
		r = new SRectangle(new Point(20, 30), 60, 60);
		r.addAttributes(new ColorAttributes(true, false, Color.MAGENTA, Color.BLUE));
		r.addAttributes(new SelectionAttributes());
		sc.add(r);

		c = new SCircle(new Point(150, 100), 40);
		c.addAttributes(new ColorAttributes(false, true, Color.BLUE, Color.DARK_GRAY));
		c.addAttributes(new SelectionAttributes());
		sc.add(c);
		this.model.add(sc);

		STriangle tri = new STriangle(new Point(200, 200), 50);
		tri.addAttributes(new ColorAttributes(true, true, Color.YELLOW, Color.BLACK));
		tri.addAttributes(new SelectionAttributes());
		this.model.add(tri);
	}

	private void buildMenu(){
		this.menubar = new JMenuBar();

		// Add menu
		JMenu menuFile = new JMenu("File");
		JMenuItem addRectItem = new JMenuItem("Add SRectangle");
		JMenuItem addCircleItem = new JMenuItem("Add SCircle");
		JMenuItem addTriItem = new JMenuItem("Add STriangle");
		JMenuItem addTextItem = new JMenuItem("Add SText");
		JMenuItem htmlExportItem = new JMenuItem("Export to HTML");
		JMenuItem exitItem = new JMenuItem("Exit");
		addRectItem.addActionListener(new MenuAddListener("SRectangle", model, sview));
		addCircleItem.addActionListener(new MenuAddListener("SCircle", model, sview));
		addTriItem.addActionListener(new MenuAddListener("STriangle", model, sview));
		addTextItem.addActionListener(new MenuAddListener("SText", model, sview));
		htmlExportItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					((ShapesController) sview.getController()).generateHtml();
					JOptionPane.showMessageDialog(null, "HTML/CSS generated successfully !", "Success",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e) {
					LOGGER.log(Level.SEVERE, e.getMessage());
					JOptionPane.showMessageDialog(null, "Error while generating the HTML/CSS. See log for more details",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		menuFile.add(addRectItem);
		menuFile.add(addCircleItem);
		menuFile.add(addTriItem);
		menuFile.add(addTextItem);
		menuFile.addSeparator();
		menuFile.add(htmlExportItem);
		menuFile.add(exitItem);

		// Edit menu
		MenuEditListener editListener = new MenuEditListener(model, sview, sview.getController());
		JMenu menuEdit = new JMenu("Edit");
		JMenuItem editColor = new JMenuItem("Change color");
		JMenuItem deleteItem = new JMenuItem("Delete");
		JMenuItem undoItem = new JMenuItem("Undo");
		JCheckBoxMenuItem editFill = new JCheckBoxMenuItem("Fill Shape");
		JCheckBoxMenuItem editBorder = new JCheckBoxMenuItem("Draw border");
		editColor.addActionListener(editListener);
		deleteItem.addActionListener(editListener);
		undoItem.addActionListener(editListener);
		editFill.addActionListener(editListener);
		editBorder.addActionListener(editListener);

		menuEdit.add(editColor);
		menuEdit.add(deleteItem);
		menuEdit.add(undoItem);
		menuEdit.addSeparator();
		menuEdit.add(editBorder);
		menuEdit.add(editFill);

		// About
		JMenu helpMenu = new JMenu("Help");
		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//TODO: about message
				JOptionPane.showMessageDialog(null, "Lorem ipsum dolor sit amet", "About this project",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		helpMenu.add(aboutItem);

		menubar.add(menuFile);
		menubar.add(menuEdit);
		menubar.add(helpMenu);
		this.setJMenuBar(this.menubar);
	}

	public static void main(String[] args) {
		Editor.initLogger();
		Editor self = new Editor();
		self.pack();
		self.sview.requestFocusInWindow();
		self.setVisible(true);

	}
}
