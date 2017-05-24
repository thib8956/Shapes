package fr.uha.graphics.shapes.attributes;

import java.awt.Color;

public class ColorAttributes extends Attributes {

	public static final String ID = "colors";

	public boolean filled;
	public boolean stroked;
	public Color strokedColor;
	public Color filledColor;

	public ColorAttributes(boolean filled, boolean stroked, Color filledColor, Color strokedColor) {
		this.filled = filled;
		this.stroked = stroked;
		this.strokedColor = strokedColor;
		this.filledColor = filledColor;
	}
	
	public ColorAttributes(ColorAttributes colAttrs){
		// Copy constructor.
		this.filled = colAttrs.filled;
		this.stroked = colAttrs.stroked;
		this.strokedColor = colAttrs.strokedColor;
		this.filledColor = colAttrs.filledColor;
	}

	public ColorAttributes() {
		// Constructor for color attributes with default values
		this.filled = false;
		this.stroked = true;
		this.strokedColor = Color.BLACK;
		this.filledColor = Color.BLACK;
	}

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public String toString() {
		return "ColorAttributes [filled=" + filled + ", stroked=" + stroked + ", strokedColor=" + strokedColor
				+ ", filledColor=" + filledColor + "]";
	}

}
