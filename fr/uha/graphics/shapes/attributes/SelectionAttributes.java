package fr.uha.graphics.shapes.attributes;

public class SelectionAttributes extends Attributes {
    public static final String ID = "attributes";
    
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
    
    @Override
    public String getId(){
	return ID;
    }

}