package fr.uha.graphics.shapes;

public interface ShapeVisitor {
	public void visitRectangle(SRectangle rect);

	public void visitCircle(SCircle circle);

	public void visitText(SText text);

	public void visitCollection(SCollection col);

	public void visitTriangle(STriangle sTriangle);

	public void visitSelection(SSelection sel);

}
