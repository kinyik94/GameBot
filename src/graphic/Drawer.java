package graphic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import core.Bot;


public class Drawer extends JComponent{
	
	/*
	 * Generated serialVersion number
	 */
	
	private static final long serialVersionUID = 5474637369704622503L;
	
	/*
	 * Stores the bot what is on the screen.
	 */
	
	private Bot b;
	
	/*
	 * Creates a new drawer for the specified Bot.
	 */
	
	public Drawer(Bot b){
		super();
		this.b = b;
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 * Draws the level and the bot.
	 * The level consists of squares.
	 * Load a picture for the Bot from a file.
	 */
	
	public void paint(Graphics g){
		try{
		
			super.paintComponent(g);
			
			Graphics2D graph2 = (Graphics2D)g;
			
			graph2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			
			graph2.setPaint(Color.BLACK);
			
			int width = 50;
			int height = 50;
			
			for(int j = 0; j < b.getLevel().size()[0]; j++)
				for(int i = 0; i < b.getLevel().size()[1]; i++){
					Shape drawRect = new Rectangle2D.Double(i * width, j * height, (i+1) * width, (j+1) * height);
					graph2.setPaint(b.getLevel().get(j, i).get());
					graph2.fill(drawRect);
					graph2.setPaint(Color.WHITE);
					graph2.draw(drawRect);
					if(b.getLevel().get(j, i).get().equals(Color.CYAN)){
						Shape line = new Line2D.Double(i * width, j * height, (i+1) * width, (j+1) * height);
						graph2.setPaint(Color.BLACK);
						graph2.draw(line);
						line = new Line2D.Double((i+1) * width, j * height, i * width, (j+1) * height);
						graph2.setPaint(Color.BLACK);
						graph2.draw(line);
					}
						
				}
			
			BufferedImage img = ImageIO.read(new File("bot" + FileSystems.getDefault().getSeparator() + "bot" + b.getDir() +".png"));
			g.drawImage(img, height * b.getPosition()[1] - 15, width * b.getPosition()[0] + 1, null);

		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
		
	}
}