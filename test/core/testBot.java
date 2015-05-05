package core;


import java.awt.Color;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class testBot {
	
	Bot b;
	
	/*
	 * Creat a level with 10 rows and 10 columns
	 * and put the bot on the (0,0) Field
	 */
	
	@Before
	public void setUp() throws Exception{
		int[] i = {0, 0};
		b = new Bot(new Level(10, 10, 1, "test"), i);
	}
	
	/*
	 * Call the move() method
	 * The dir of the Bot is 0, so it should increment the column number
	 */
	
	@Test
	public void testMove(){
		b.move();
		int[] i  = {0, 1};
		Assert.assertArrayEquals(i, b.getPosition());
	}
	
	/*
	 * The whole level is black so the check() method should return with false
	 */
	
	@Test
	public void testCheck(){
		Assert.assertEquals(false, b.check());
	}
	
	/*
	 * First turn right, so the dir will be 1.
	 * The Bots row number should incrmenet.
	 * Than turn right again, so the dir will be 2.
	 * The column number is 0, and it can't be zero, so it should stay to be zero.
	 * Turn right twice, than move().
	 * The dir is 0, so the column number should increment.
	 */
	
	@Test
	public void testTurn()throws Exception{
		int[] i  = {1, 0};
		b.turn("right");
		b.move();
		Assert.assertArrayEquals(i, b.getPosition());
		Assert.assertEquals(1, b.getDir());
		b.turn("right");
		b.move();
		Assert.assertArrayEquals(i, b.getPosition());
		Assert.assertEquals(2, b.getDir());
		b.turn("right");
		b.turn("right");
		b.move();
		i[1]++;
		Assert.assertArrayEquals(i, b.getPosition());
		Assert.assertEquals(0, b.getDir());
	}
	
	/*
	 * The Bot is on the (0,0) Field.
	 * Set the (0,0) Field red.
	 * The Bot's Field color should be Red.
	 */
	
	@Test
	public void testGetFieldColor(){
		b.getLevel().set(b.getPosition()[0], b.getPosition()[1], Color.RED);
		Assert.assertEquals(Color.RED, b.getFieldColor());
	}
}
