package fr.uha.graphics.shapes;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.Rectangle;

import fr.uha.graphics.shapes.attributes.ColorAttributes;
import fr.uha.graphics.shapes.attributes.FontAttributes;

public class SText extends Shape {

	private String text;
	private Point loc;

	public SText(Point loc, String string) {
		this.text = string;
		this.loc = loc;
	}

	public String getText() {
		return this.text;
	}

	@Override
	public Point getLoc() {
		return this.loc;
	}

	@Override
	public void setLoc(Point loc) {
		this.loc = loc;
	}

	@Override
	public void translate(int dx, int dy) {
		this.loc.translate(dx, dy);
	}

	@Override
	public Rectangle getBounds() {
		FontAttributes attrs = (FontAttributes) this.getAttributes(FontAttributes.ID);
		Canvas can = new Canvas();
		FontMetrics fontMetrics = can.getFontMetrics(attrs.font);

		int w = fontMetrics.stringWidth(this.text);
		int h = attrs.font.getSize();
		return new Rectangle(loc.x, loc.y - h, w, h);
	}

	@Override
	public void accept(ShapeVisitor sv) {
		sv.visitText(this);
	}

	@Override
	public String htmlShape() {
		return "<div class=\"text"+this.hashCode()+"\">"+getText()+"</div>";
	}

	@Override
	public String cssShape() {
		Rectangle bounds = this.getBounds();
		FontAttributes fontAttrs = (FontAttributes) this.getAttributes(FontAttributes.ID);
		String hexFontColor = String.format("#%02x%02x%02x", 
											fontAttrs.fontColor.getRed(), 
											fontAttrs.fontColor.getGreen(), 
											fontAttrs.fontColor.getBlue());
		Font font = fontAttrs.font;
		
		StringBuilder strBuilder = new StringBuilder(".text" + this.hashCode() + "{ ");
		strBuilder.append("position: absolute;");
		strBuilder.append("top: " + this.loc.y + "px;");
		strBuilder.append("left: " + this.loc.x + "px;");
		strBuilder.append("width: " + bounds.width + "px;");
		strBuilder.append("height: " + bounds.height + "px;");
		strBuilder.append("font-family: \"" + font.getName() + "\";");
		strBuilder.append("font-size: " + font.getSize() + "px;");
		strBuilder.append("color: " + hexFontColor +";");
		if (font.isBold()) strBuilder.append("font-weight: bold;");
		if (font.isItalic()) strBuilder.append("font-style: italic;");
		strBuilder.append(this.attributesCss());
		strBuilder.append(" }");
		return strBuilder.toString();
	}

}
