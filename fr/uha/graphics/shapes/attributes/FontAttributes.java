package fr.uha.graphics.shapes.attributes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

public class FontAttributes extends Attributes {
    public static final String ID = "font";
    
    public Font font;
    public Color fontColor;
    
    public FontAttributes(Font font, Color fontColor){
	this.font = font;
	this.fontColor = fontColor;
    }
    
    public FontAttributes() {
	// TODO Auto-generated constructor stub
    }

    @Override
    public String getId() {
	// TODO Auto-generated method stub
	return null;
    }
    
    public Rectangle getBounds(String bounds){
	return null;
    }

}
