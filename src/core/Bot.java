package core;


import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import command.RuntimeErrorException;

public class Bot implements Serializable{
	
	private static final long serialVersionUID = 2710964454537754979L;
	/*
	 * The Bot stores the Level object where the bot is,
	 * the position, the default position, the direction and the default direction.
	 */
	
	/* This table shows which number which direction */
	/* *************  EAST   =  0;  **************** */
	/* *************  SOUTH  =  1;  **************** */
	/* *************  WEST   =  2;  **************** */
	/* *************  NORTH  =  3;  **************** */
	/* ********************************************* */
	
	private Level lev;
	private int[] position;
	private int[] defaultPosition = new int[2];
	private int dir;
	private int defDirection;
	
	/*
	 * Initializes a newly created Bot object
	 * with the given Level object and pos int array
	 */
	
	public Bot(Level lev, int[] pos) throws IllegalArgumentException{
		this.lev = lev;
		if(pos[0] < 0 || pos[1] < 0 || pos[0] > lev.size()[0] || pos[1] > lev.size()[1]){
			throw new IllegalArgumentException("The givan position is not on the level!");
		}
		System.arraycopy(pos, 0, defaultPosition, 0, 2);
		position = pos;
		dir = 0;
		defDirection = 0;
	}
	
	/*
	 * Initializes a newly created Bot object
	 * with the given Level object, pos int array
	 * and the given dir direction
	 * throws IndexOutOfBoundsException if the given dir not appropriate
	 */
	
	public Bot(Level lev, int[] pos, int dir) throws IndexOutOfBoundsException{
		this.lev = lev;
		if(dir < 0 || dir >= 4)
			throw new IndexOutOfBoundsException("Not appropriate index in class Bot line:33");
		this.dir = dir;
		position = pos;
		System.arraycopy(pos, 0, defaultPosition, 0, 2);
		defDirection = dir;
	}
	
	/*
	 * Move one Field forward in the stored direction
	 */
	
	public void move(){
		if((dir % 2) == 0)
			position[1] -= dir-1;
		else
			position[0] -= dir-2;
		
		if(position[0] < 0)
			position[0] = 0;
		if(position[0] >= lev.size()[0])
			position[0] = lev.size()[0];
		
		if(position[1] < 0)
			position[1] = 0;
		if(position[1] >= lev.size()[1])
			position[1] = lev.size()[1] - 1;
		
	}
	
	/*
	 * Rotate in the given way
	 * The given way could be left or right
	 * if they are not one of these, the method throws NotSuitableWayException
	 */
	
	public void turn(String way) throws RuntimeErrorException{
		
		int i = 0;
		
		switch (way){
		case "left":
			i = -1;
			break;
		case "right":
			i = 1;
			break;
		default:
			throw new RuntimeErrorException("The given way isn't suitable in Bot class line:64");
		}
		
		switch (dir+i){
		case -1:
			dir = 3;
			break;
		case 4:
			dir = 0;
			break;
		default:
			dir = dir + i;
			break;
		}
		
	}
	
	/*
	 * Returns the Field object's color where the Bot is
	 */
	
	public Color getFieldColor(){
		return lev.get(position[0], position[1]).get();
	}
	
	/*
	 * Returns an int array with the Bot's position
	 */
	
	public int[] getPosition(){
		return position;
	}
	
	/*
	 * Returns the level where the Bot is
	 */
	
	public Level getLevel(){
		return lev;
	}
	
	/*
	 * Returns the direction of the bot
	 */
	
	public int getDir(){
		return dir;
	}
	
	/*
	 * Returns true if the Bot is not on a black field
	 */
	
	public boolean check(){
		return lev.get(position[0], position[1]).get() != Color.BLACK;
	}
	
	/*
	 * Put back the Bot to its default position and direction
	 */
	
	public void setDefault(){
		System.arraycopy(defaultPosition, 0, position, 0, 2);
		dir = defDirection;
	}
	
	/*
	 * Reads a Bot object from the specified file
	 */
	
	public static Bot read(int levelNumber) throws ClassNotFoundException{
		Bot b = null;
		try{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(levelNumber + ".lev"));
			b = (Bot) in.readObject();
			in.close();
		}
		catch(IOException ex){}
		return b;
	}
	
	/*
	 * Writes the object to the appropriate file.
	 */
	
	public void write(){
		try{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.getLevel().levelNumber + ".lev"));
			out.writeObject(this);
			out.close();
		}
		catch(IOException ex){
			System.out.println(ex.getMessage());
		}
	}
	
}