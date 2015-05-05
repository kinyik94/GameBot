package command;

import graphic.GameWindow;
import graphic.LoginWindow;
import graphic.MenuWindow;

import java.awt.Color;

import core.Bot;
import core.Level;
import core.UserManager;

public class GameBot {
	
	/*
	 * The number of the levels in the game.
	 */
	
	public static final int levelNumber = 5;
	
	/*
	 * Opens a Menu frame.
	 */
	
	public static void openMenu(){
		MenuWindow.open(UserManager.user, levelNumber);
	}
	
	/*
	 * Opens a game frame for the specified level.
	 */
	
	public static void openGame(int levelNumber){
		try{
			Bot b = Bot.read(levelNumber);
	        GameWindow.game(b);
		}
		catch(IndexOutOfBoundsException e){
			System.out.println(e.getMessage());
		}
		catch(ClassNotFoundException e){
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * Create a Bot and write it out.
	 */
	
	public static void createBot(){
		Level l = new Level(15, 15, 1, "Begin");
		int m = 0;
		int n = 0;
		l.set(m, n++, Color.WHITE);
		l.set(m, n++, Color.LIGHT_GRAY);
		l.set(m, n++, Color.LIGHT_GRAY);
		l.set(m, n++, Color.LIGHT_GRAY);
		l.set(m, n, Color.CYAN);
		int[] a = {0, 0};
		Bot b = new Bot(l, a);
		b.write();
	}
	
	/*
	 * Reads in the users and opens a login window.
	 */
	
	public static void main(String[] args) {
		UserManager.read();
		LoginWindow.login();
	}

}
