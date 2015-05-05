package command;

import java.awt.Color;

import core.Bot;

public class Loop {
	
	/*
	 * begin stores where it starts.
	 * type can be for or while.
	 * number and size are for for loops
	 * color and conditionType for while loops.
	 */
	
	public int begin;
	
	private String type;
	private int number;
	private int size;
	private Color color;
	private boolean conditionType;
	
	/*
	 * Initializes a newly created Loop from the 
	 * attributions of the Loop parameter.
	 */
	
	public Loop(Loop l){
		begin = l.begin;
		type = l.type;
		size = l.size;
		conditionType = l.conditionType;
		number = size;
		color = l.color;
		
	}
	
	/*
	 * Initializes a newly created Loop width the specified begin, type, size, conditionType, and color.
	 */
	
	public Loop(int begin, String type, int size, Color c, boolean conditionType){
		this.begin = begin;
		this.type = type;
		this.size = size;
		this.conditionType = conditionType;
		number = size;
		color = c;
	}
	
	/*
	 * If it is a for loop, then returns true if the number is not 0,
	 * and reduces the number.
	 * If it reaches 0, then returns false and set number to size.
	 */
	
	public boolean conditionIsTrue(Bot b) throws RuntimeErrorException{
		
		if(type == "for"){
			number--;
			if(number < 0)
				number = -1;
			if(this.number == 0){
				number = size;
				return false;
			}
			else
				return true;
		}
		else if(type == "while"){
			return conditionType ^ !(b.getFieldColor().equals(color));
		}
		else
			throw new RuntimeErrorException("Cycle not found");
	}

}
