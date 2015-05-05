package command;

import java.awt.Color;

import core.Bot;

public class Branching {
	
	/*
	 * With branchings the user can write code what will run only 
	 * if the condition is true.
	 * Branching's type is true if in the condition
	 * the field must have the specified color.
	 * If the type is false, the condition is true 
	 * if the field doesn't have the specified color.
	 * If the branching is alwaystrue,
	 * the branchings condition always will be true.
	 * The color stores the specified color what the field must have or
	 * must not have.
	 */
	
	private boolean type;
	private boolean alwaystrue;
	private Color color;
	
	/*
	 * Initializes a newly created branching from the 
	 * attributions of the Branching parameter.
	 */
	
	public Branching(Branching b){
		type = b.type;
		color = b.color;
	}
	
	/*
	 * Initializes a newly created branching from the given parameters.
	 */
	
	public Branching(boolean type, Color c, boolean alwaystrue){
		this.type = type;
		this.alwaystrue = alwaystrue;
		color = c;
	}
	
	/*
	 * Returns true if the branching's condition is true.
	 */
	
	public boolean conditionIsTrue(Bot b){
		return alwaystrue || (type ^ !(b.getFieldColor().equals(color)));
	}
}
