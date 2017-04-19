package fr.uha.graphics.shapes.attributes;

public class SelectionAttributes extends Attributes {
    public static final String ID = "selection";
    
    private boolean selected;
    
    public SelectionAttributes() {
	this.selected = false;
    }

    public void select(){
	selected = true;
    }

    public void unselect(){
	selected = false;
    }

    public void toggleSelection(){
	selected = !selected;
    }
    
    public boolean isSelected(){
    	return this.selected;
    }
    
    @Override
    public String getId(){
	return ID;
    }

}
