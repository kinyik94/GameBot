package core;

import java.awt.Color;
import java.io.Serializable;

public class Level implements Serializable{
	
	/*
	 * Generated serialVersion number
	 */
	
	private static final long serialVersionUID = -3889138264515343336L;
	
	/*
	 * The Level object stores a 2D Field Array
	 * its name, its levelNumber
	 * func1 is true if Function1 is editable, otherwise it's false
	 * func2 is true if Function2 is editable, otherwise it's false
	 * This is the the space where the Bot can move
	 */
	
	private Field[][] fields;
	public boolean isFunc1;
	public boolean isFunc2;
	public int levelNumber;
	private String Name;
	
	/*
	 * Initializes a newly created Level object
	 * The parameters of the constructor are the name of the field, the levelNumber and the number of rows and columns
	 */
	
	public Level(int row, int element, int levelNumber, String Name){
		this.Name = Name;
		fields = new Field[row][element];
		this.levelNumber = levelNumber;
		for(int j = 0; j < row; j++)
			for(int i = 0; i < element; i++)
				fields[j][i] = new Field();
	}
	
	/*
	 * Initializes a newly created Level object
	 * The parameters are the color of the field, the levelNumber, 
	 * the Name of the Level and the number of rows and columns
	 */
	
	public Level(int row, int element, Color c, int levelNumber, String Name){
		this.Name = Name;
		fields = new Field[row][element];
		this.levelNumber = levelNumber;
		for(int j = 0; j < row; j++)
			for(int i = 0; i < element; i++)
				fields[j][i] = new Field(c);
	}
	
	/*
	 * Returns the Field object at the specified place
	 * Throws IndexOutOfBoundsException if the given numbers are not appropriate
	 */
	
	public Field get(int row, int element) throws RuntimeException{
		if((row >= fields.length) || (element >= fields[0].length) || (row < 0) || (element < 0))
			throw new RuntimeException("Not suitable index in class Level line: 20");
		return fields[row][element];
	}
	
	/*
	 * Sets the Field object's color in the specified place to the specified color
	 * Throws IndexOutOfBoundsException if the given numbers are not appropriate
	 */
	
	public void set(int row, int element, Color color) throws RuntimeException{
		if((row >= fields.length) || (element >= fields[0].length) || (row < 0) || (element < 0))
			throw new RuntimeException("Not suitable index in class Level line:29");
		fields[row][element].set(color);
	}
	
	/*
	 * Returns the size of the Field array
	 */
	
	public int[] size(){
		return new int[]{fields.length, fields[0].length};
	}
	
	/*
	 * Returns the Name of the level
	 */
	
	public String getName(){
		return Name;
	}
}