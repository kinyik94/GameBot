package core;

import java.awt.Color;
import java.io.Serializable;

public class Field implements Serializable{
	
	/*
	 * Generated serialVersion number
	 */

	private static final long serialVersionUID = -1042205459468817117L;
	
	/*
	 * The Field object stores its color in String
	 */
	
	private Color color;
	
	/*
	 * Initializes a newly created Field object with gray color
	 */
	
	public Field(){
		color = Color.BLACK;
	}
	
	/*
	 * Initializes a newly created Field object with the given color
	 */
	
	public Field(Color color){
		this.color = color;
	}
	
	/*
	 * Returns the Field object's color
	 */
	
	public Color get(){
		return color;
	}
	
	/*
	 * Sets the Field object's color to the given color
	 */
	
	public void set(Color color){
		this.color = color;
	}
	
}
