package fr.uha.graphics.shapes.attributes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

public class FontAttributes extends Attributes {
	public static final String ID = "font";

	public Font font;
	public Color fontColor;

	public FontAttributes(Font font, Color fontColor) {
		this.font = font;
		this.fontColor = fontColor;
	}

	public FontAttributes() {
		// Constructor with default font attributes values.
		this.font = new Font("TimesRoman", Font.PLAIN, 18);
		this.fontColor = Color.BLACK;
	}

	@Override
	public String toString() {
		return "FontAttributes [font=" + font + ", fontColor=" + fontColor + "]";
	}

	@Override
	public String getId() {
		return ID;
	}

}
