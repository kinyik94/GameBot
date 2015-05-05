package graphic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import command.GameBot;

import core.User;
import core.UserManager;

public class MenuWindow {
	
	private static JFrame frame;
	private static ArrayList<JButton> buttons;
	public static boolean noMoreLevel = false;
	
	public static void open(User user, int levelNumber){
		
		frame = new JFrame("GameBot - Menu");
		
		buttons = new ArrayList<JButton>();
		
		JMenuBar menuBar = new JMenuBar();
		JMenu userMenu = new JMenu("User");
	
		JMenuItem deleteMenu = new JMenuItem("Delete Account");
		JMenuItem logoutMenu = new JMenuItem("Log out");
		JMenuItem exitMenu = new JMenuItem("Exit");
		
		
		
		userMenu.add(deleteMenu);
		userMenu.add(logoutMenu);
		userMenu.add(exitMenu);
		
		menuBar.add(userMenu);
		
		frame.setJMenuBar(menuBar);
		
		JPanel panel = new JPanel();
		
		for(int i = 0; i < levelNumber; i++){
			buttons.add(new JButton("Level " + (i+1)));
			if(i + 1 > user.levelNumber){
				buttons.get(i).setEnabled(false);
				buttons.get(i).setBackground(Color.LIGHT_GRAY);
			}
				
		}
		
		MenuActionListener mal = new MenuActionListener();
		
		deleteMenu.setActionCommand("delete");
		deleteMenu.addActionListener(mal);
		logoutMenu.setActionCommand("logout");
		logoutMenu.addActionListener(mal);
		exitMenu.setActionCommand("exit");
		exitMenu.addActionListener(mal);
		
		for(JButton b : buttons){
			b.setActionCommand(b.getText().substring(6, b.getText().length()));
			b.addActionListener(mal);
			panel.add(b, BorderLayout.CENTER);
		}
		
		frame.add(panel);
		frame.setSize(300, 170);
		frame.setResizable(false);
		frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 150, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 170);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		if(noMoreLevel)
			JOptionPane.showMessageDialog(new JFrame(), "You have successfully done all levels!\n" + 
														"Please choose a level and try write a better code!");
		
	}
	
	static class MenuActionListener implements ActionListener{

		
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
			if(e.getActionCommand().equals("delete")){
				UserManager.remove();
				noMoreLevel = false;
				LoginWindow.login();
			}
			else if(e.getActionCommand().equals("logout")){
				UserManager.user = null;
				noMoreLevel =false;
				LoginWindow.login();
			}
			else if(e.getActionCommand().equals("exit")){
				System.exit(0);
			}
			else{
				GameBot.openGame(Integer.valueOf(e.getActionCommand()));
			}
		}
		
	} 
	
}
