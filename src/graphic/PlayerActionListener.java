package graphic;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.FileSystems;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

import command.Command;
import command.GameBot;
import command.RuntimeErrorException;

import core.Bot;
import core.UserManager;

public final class PlayerActionListener implements ActionListener{

	private JTextArea main;
	private JTextArea func1;
	private JTextArea func2;
	private JTextArea consol;
	private Bot b;
	private Drawer d;
	private Timer timer;
	private PrintWriter pw;
	private boolean run;
	private int lastPosition[];
	private int lastDir;
	
	public PlayerActionListener(Drawer d, JTextArea main, JTextArea func1, JTextArea func2, JTextArea consol, Bot b){
		this.main = main;
		this.func1 = func1;
		this.func2 = func2;
		this.consol = consol;
		this.b = b;
		this.d = d;
		this.timer = new Timer(20, this);
		lastPosition = new int[2];
		lastPosition[0] = -1;
		lastPosition[1] = -1;
		lastDir = -1;
		timer.setActionCommand("Animate");
	}
	
	private void save(){
		try{
			
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("Users" + FileSystems.getDefault().getSeparator() + UserManager.user.getName() + FileSystems.getDefault().getSeparator() + 
					UserManager.user.getName() + "_Main_" + b.getLevel().levelNumber)));
			main.write(pw);
			pw.close();
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("Users" + FileSystems.getDefault().getSeparator() + UserManager.user.getName() + FileSystems.getDefault().getSeparator() + 
					UserManager.user.getName() + "_Function1_" + b.getLevel().levelNumber)));
			func1.write(pw);
			pw.close();
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("Users" + FileSystems.getDefault().getSeparator() + UserManager.user.getName() + FileSystems.getDefault().getSeparator() + 
					UserManager.user.getName() + "_Function2_" + b.getLevel().levelNumber)));
			func2.write(pw);
			pw.close();
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("Users" + FileSystems.getDefault().getSeparator() + UserManager.user.getName() + FileSystems.getDefault().getSeparator() + 
					UserManager.user.getName() + "_consol")));
			pw.close();
		}
		catch(IOException e){}
	}
	
	public void actionPerformed(ActionEvent ae){
		
		try {
			if(ae.getActionCommand().equals("Syntax")){
				
				save();
				
				Command.SyntaxChecker(b);
				
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Users" + FileSystems.getDefault().getSeparator() + UserManager.user.getName() + FileSystems.getDefault().getSeparator() + 
						UserManager.user.getName() + "_consol")));
				consol.read(br, new File("Users" + FileSystems.getDefault().getSeparator() + UserManager.user.getName() + FileSystems.getDefault().getSeparator() + 
						UserManager.user.getName() + "_consol"));
				br.close();
				
			}
			else if(ae.getActionCommand().equals("Run")){
				
				save();
				
				Command.SyntaxChecker(b);
				
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Users" + FileSystems.getDefault().getSeparator() + UserManager.user.getName() + FileSystems.getDefault().getSeparator() + 
						UserManager.user.getName() + "_consol")));
				consol.read(br, new File("Users" + FileSystems.getDefault().getSeparator() + UserManager.user.getName() + FileSystems.getDefault().getSeparator() + 
						UserManager.user.getName() + "_consol"));
				br.close();
				
				if(!timer.isRunning()){
					
					timer.start();
				}
			}
			else if(ae.getActionCommand().equals("Animate")){
				try{
					run = Command.run(b);
					if(run){
						if(!(b.getPosition()[0] == lastPosition[0] && b.getPosition()[1] == lastPosition[1] && b.getDir() == lastDir)){
							System.arraycopy(b.getPosition(), 0, lastPosition, 0, b.getPosition().length);
							lastDir = b.getDir();
							d.repaint();
						}
					}
					else{
						if(b.getFieldColor().equals(Color.CYAN)){
							timer.stop();
							Object[] options = {"Try next level",
				                    "Stay at this level",
				                    "Go Back to the Menu"};
							int answer = JOptionPane.showOptionDialog(new JFrame(),
								    "Congratulation!\n" +
								    "You have successfully done this level.\n" +
								    "You can stay at this level and try write a better code, or you can try next level.",
								    "Level Completed",
								    JOptionPane.YES_NO_OPTION,
								    JOptionPane.QUESTION_MESSAGE,
								    null,
								    options,
								    options[2]);
							Command.reset();
							b.setDefault();
							if(b.getLevel().levelNumber < GameBot.levelNumber){
									UserManager.user.levelNumber++;
							}
							else{
								if(answer == 0)
									MenuWindow.noMoreLevel = true;
							}
											
							d.repaint();
							if(answer == 0){
								GameWindow.close();
								if(!MenuWindow.noMoreLevel)
									GameBot.openGame(b.getLevel().levelNumber + 1);
								else
									GameBot.openMenu();
							}
							else if(answer == 2){
								GameWindow.close();
								GameBot.openMenu();
							}
							UserManager.write();
						}
						else{
							timer.stop();
							try{Thread.sleep(300);}catch(InterruptedException ex){}
							Command.reset();	
							b.setDefault();
							d.repaint();
						}
					}
				}
				catch(RuntimeErrorException e){
					timer.stop();
					consol.append("Runtime error: " + e.getMessage());
					consol.write(new PrintWriter(new OutputStreamWriter(new FileOutputStream("Users" + FileSystems.getDefault().getSeparator() + UserManager.user.getName() + FileSystems.getDefault().getSeparator() + 
							UserManager.user.getName() + "_consol"))));
					Command.reset();				
					b.setDefault();
					d.repaint();
				}
			}
			else if(ae.getActionCommand().equals("Stop")){
				if(timer.isRunning())
					timer.stop();
				b.setDefault();
				Command.reset();
				d.repaint();
			}
			else if(ae.getActionCommand().equals("Menu")){
				Command.reset();
				GameWindow.close();
				GameBot.openMenu();
			}
			else if(ae.getActionCommand().equals("Help")){
				HelpFrame hf = new HelpFrame();
				hf.setVisible(true);
			}
			else if(ae.getActionCommand().equals("Save")){
				save();
			}
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
}