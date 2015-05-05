package graphic;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import command.GameBot;

import core.LoginException;
import core.UserManager;

public class LoginWindow{
	private static JFrame frame;
	
	private static JTextField nameTextField;
	private static JTextField passTextField;
	private static JButton loginButton;
	private static JButton registerButton;
	private static JButton backButton;
	
	private static JPanel namePanel;
	private static JPanel passPanel;
	private static JPanel buttonPanel;
	private static JPanel mainPanel;
	
	public LoginWindow(){}
	
	public static void login(){
		
		frame = new JFrame("GameBot - Login");
		
		nameTextField = new JTextField(null, null, 20);
		passTextField = new JPasswordField(null, null, 20);
		loginButton = new JButton("Sign In");
		registerButton = new JButton("Sign Up");
		backButton = new JButton("Back");
		
		namePanel = new JPanel();
		passPanel = new JPanel();
		buttonPanel = new JPanel();
		mainPanel = new JPanel();
		
		nameTextField.setText("");
		passTextField.setText("");
		
		mainPanel.setLayout(new GridLayout(3, 1));
		
		registerButton.setActionCommand("toregister");
		loginButton.setActionCommand("login");
		registerButton.addActionListener(new ButtonActionListener());
		loginButton.addActionListener(new ButtonActionListener());
		backButton.addActionListener(new ButtonActionListener());
		
		namePanel.add(new JLabel("Username:"), BorderLayout.CENTER);
		namePanel.add(nameTextField, BorderLayout.CENTER);
		passPanel.add(new JLabel("Password:"), BorderLayout.CENTER);
		passPanel.add(passTextField, BorderLayout.CENTER);
		buttonPanel.add(loginButton, BorderLayout.CENTER);
		buttonPanel.add(registerButton, BorderLayout.CENTER);
		
		mainPanel.addKeyListener(new Key());
		nameTextField.addKeyListener(new Key());
		passTextField.addKeyListener(new Key());
		mainPanel.setFocusable(true);
		
		mainPanel.add(namePanel);
		mainPanel.add(passPanel);
		mainPanel.add(buttonPanel);
		
		frame.add(mainPanel, BorderLayout.CENTER);
		
		frame.setResizable(false);
		frame.setSize(400, 150);
		frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 200, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void register(){
		
		frame.setTitle("GameBot - Register");
		registerButton.setActionCommand("register");
		nameTextField.setText("");
		passTextField.setText("");
		backButton.setActionCommand("back");
		buttonPanel.removeAll();
		buttonPanel.add(backButton);
		buttonPanel.add(registerButton);
		buttonPanel.revalidate();
		frame.repaint();
		
	}
	
	public static void back(){
		
		frame.setTitle("GameBot - Login");
		registerButton.setActionCommand("toregister");
		nameTextField.setText("");
		passTextField.setText("");
		buttonPanel.removeAll();
		buttonPanel.add(loginButton);
		buttonPanel.add(registerButton);
		buttonPanel.revalidate();
		frame.repaint();
		
	}
	
	public static void error(String message){
		JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
		        JOptionPane.ERROR_MESSAGE);
		
	}
	
	static class ButtonActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()){
				case "toregister":
					register();
				break;
				case "register":
					try{
						if(nameTextField.getText().equals("") || passTextField.getText().equals(""))
							error("Wrong Username or Password!");
						else{
							UserManager.register(nameTextField.getText(), passTextField.getText());
							frame.dispose();
							GameBot.openMenu();
						}
					}
					catch(LoginException le){
						error(le.getMessage());
					}
				break;
				case "login":
					try{
						UserManager.login(nameTextField.getText(), passTextField.getText());
						frame.dispose();
						GameBot.openMenu();
					}
					catch(LoginException le){
						error(le.getMessage());
					}
				break;
				case "back":
					back();
				break;
				default:
				break;
			}
		}
		
	}
	
	static class Key implements KeyListener{

		public void keyTyped(KeyEvent e){
			
		}

		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER && frame.getTitle().equals("GameBot - Login"))
				loginButton.doClick();
			if(e.getKeyCode() == KeyEvent.VK_ENTER && frame.getTitle().equals("GameBot - Register"))
				registerButton.doClick();
		}

		public void keyReleased(KeyEvent e) {
			
		}
		
	}
	
}
