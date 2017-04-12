package fr.uha.graphics.shapes.attributes;

import java.awt.Color;

public class ColorAttributes extends Attributes {
    
    public static final String ID = "colors";
    
    public boolean filled = false;
    public boolean stroked = true;
    public Color strokedColor = Color.BLACK;
    public Color filledColor = Color.BLACK;
    
    public ColorAttributes(boolean filled, boolean stroked, Color strokedColor, Color filledColor) {
	this.filled = filled;
	this.stroked = stroked;
	this.strokedColor = strokedColor;
	this.filled = filled;
    }

    public ColorAttributes() {
    }

    @Override
    public String getId() {
	return ID;
    }
}
