package graphic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import core.Bot;
import core.UserManager;

public class GameWindow {
	
	private static JFrame f;
	
	public final static String defText = 
			"/*\n" +
			" *In this game you have to get along the bot\n" +
			" *to the cyan field.\n" + 
			" *You can use commands, what you can find,\n" + 
			" *if you click on the Help button.\n" + 
			" *You can use branchings and loops to complete the level.\n" +
			" *For conditions you can use the color of the field.\n" + 
			" *You can't step on a black field, otherwise the animation stops.\n" +
			" */\n\n";
	
	public static void game(Bot b) throws IndexOutOfBoundsException{
		
		if((b.getPosition()[0] >= b.getLevel().size()[0]) || b.getPosition()[1] >= b.getLevel().size()[1]){
			throw new IndexOutOfBoundsException("The bot isn't on the level");
		}
		
		f = new JFrame("GameBot - " + b.getLevel().getName());
		
		f.setMinimumSize(new Dimension(640, 360));
        f.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2));
		
		JPanel MainPanel = new JPanel();
		JPanel GamePanel = new JPanel();
		JPanel ShowPanel = new JPanel();
		JPanel TextPanel = new JPanel();
		JPanel MenuPanel = new JPanel();
		JPanel ButtonPanel = new JPanel();
		JPanel CaretPanel = new JPanel();
		
		JButton RunButton = new JButton("Run");
		JButton MenuButton = new JButton("Menu");
		JButton HelpButton = new JButton("Help");
		JButton SyntaxButton = new JButton("Compile");
		JButton StopButton = new JButton("Stop");
		JButton SaveButton = new JButton("Save");
		JLabel CaretPosition = new JLabel("");
		JLabel FunctionPosition = new JLabel("");
		
		JLabel MainLabel = new JLabel("Main Function:");
		JTextArea MainTextArea = new JTextArea();
		try{
			MainTextArea.read(new BufferedReader(new InputStreamReader(new FileInputStream("Users" + FileSystems.getDefault().getSeparator() + UserManager.user.getName() + FileSystems.getDefault().getSeparator() + 
													UserManager.user.getName() + "_Main_" + b.getLevel().levelNumber))), 
											   new File("Users" + FileSystems.getDefault().getSeparator() + UserManager.user.getName() + FileSystems.getDefault().getSeparator() + 
													   UserManager.user.getName() + "_Main_" + b.getLevel().levelNumber));
		}
		catch(IOException e){}
		MainTextArea.addCaretListener(new MyCaretListener(CaretPosition, FunctionPosition, "Main"));
		JScrollPane Main = new JScrollPane(MainTextArea);
		Main.setPreferredSize(new Dimension(20, 20));
		
		JLabel Func1Label = new JLabel("Function1:");	
		JTextArea Func1TextArea = new JTextArea();
		try{
			Func1TextArea.read(new BufferedReader(new InputStreamReader(new FileInputStream("lev_" + b.getLevel().levelNumber + ".func1"))), 
											   new File("lev_" + b.getLevel().levelNumber + ".func1"));
		}
		catch(IOException e){}
		try{
			Func1TextArea.read(new BufferedReader(new InputStreamReader(new FileInputStream("Users" + FileSystems.getDefault().getSeparator() + UserManager.user.getName() + FileSystems.getDefault().getSeparator() + 
								UserManager.user.getName() + "_Function1_" + b.getLevel().levelNumber))), 
											   new File("Users" + FileSystems.getDefault().getSeparator() + UserManager.user.getName() + FileSystems.getDefault().getSeparator() + 
													   UserManager.user.getName() + "_Function1_" + b.getLevel().levelNumber));
		}
		catch(IOException e){}
		Func1TextArea.setEditable(b.getLevel().isFunc1);
		Func1TextArea.addCaretListener(new MyCaretListener(CaretPosition, FunctionPosition, "Function1"));
		if(!b.getLevel().isFunc1)
			Func1TextArea.setBackground(Color.LIGHT_GRAY);
		JScrollPane Func1 = new JScrollPane(Func1TextArea);
		Func1.setPreferredSize(new Dimension(20, 20));
		
		JLabel Func2Label = new JLabel("Function2:");
		JTextArea Func2TextArea = new JTextArea();
		try{
			Func2TextArea.read(new BufferedReader(new InputStreamReader(new FileInputStream("lev_" + b.getLevel().levelNumber + ".func2"))), 
											   new File("lev_" + b.getLevel().levelNumber + ".func2"));
		}
		catch(IOException e){}
		try{
			Func2TextArea.read(new BufferedReader(new InputStreamReader(new FileInputStream("Users" + FileSystems.getDefault().getSeparator() + UserManager.user.getName() + FileSystems.getDefault().getSeparator() + 
													UserManager.user.getName() + "_Function2_" + b.getLevel().levelNumber))), 
											   new File("Users" + FileSystems.getDefault().getSeparator() + UserManager.user.getName() + FileSystems.getDefault().getSeparator() + 
													   UserManager.user.getName() + "_Function2_" + b.getLevel().levelNumber));
		}
		catch(IOException e){}
		Func2TextArea.setEditable(b.getLevel().isFunc2);
		Func2TextArea.addCaretListener(new MyCaretListener(CaretPosition, FunctionPosition, "Function2"));
		if(!b.getLevel().isFunc2)
			Func2TextArea.setBackground(Color.LIGHT_GRAY);		
		JScrollPane Func2 = new JScrollPane(Func2TextArea);
		Func2.setPreferredSize(new Dimension(20, 20));
		
		JLabel LevelLabel = new JLabel("Level " + b.getLevel().levelNumber);
		Drawer drawer = new Drawer(b);
		drawer.setPreferredSize(new Dimension(50 * b.getLevel().size()[1], 50 * b.getLevel().size()[0]));
		JScrollPane drawerscroll = new JScrollPane(drawer);
		drawerscroll.setPreferredSize(new Dimension(20, 20));
		
		JLabel ConsolLabel = new JLabel("Consol");
		JTextArea ConsolTextArea = new JTextArea();
		JScrollPane Consol = new JScrollPane(ConsolTextArea);
		ConsolTextArea.setEditable(false);
		Consol.setPreferredSize(new Dimension(20, 20));
		
		ActionListener Pal = new PlayerActionListener(drawer, MainTextArea, Func1TextArea, Func2TextArea, ConsolTextArea, b);
		
		RunButton.setActionCommand("Run");
		RunButton.addActionListener(Pal);
		
		StopButton.setActionCommand("Stop");
		StopButton.addActionListener(Pal);
		
		SyntaxButton.setActionCommand("Syntax");
		SyntaxButton.addActionListener(Pal);
		
		SaveButton.setActionCommand("Save");
		SaveButton.addActionListener(Pal);
		
		MenuButton.setActionCommand("Menu");
		MenuButton.addActionListener(Pal);
		
		HelpButton.setActionCommand("Help");
		HelpButton.addActionListener(Pal);
		
		GridBagLayout GBL = new GridBagLayout();
		GridBagConstraints GBC = new GridBagConstraints();
		
		TextPanel.setLayout(GBL);
		
		GBC.fill = GridBagConstraints.BOTH;
		
		GBC.gridx = 0;
		GBC.gridy = 0;
		GBC.weightx = 1;
		GBC.weighty = 0.02;
		TextPanel.add(MainLabel, GBC);
		
		GBC.weighty = 0.4;
		GBC.gridy = 1;
		TextPanel.add(Main, GBC);
		
		GBC.weighty = 0.02;
		GBC.gridy = 2;
		TextPanel.add(Func1Label, GBC);
		
		GBC.weighty = 0.27;
		GBC.gridy = 3;
		TextPanel.add(Func1, GBC);
		
		GBC.weighty = 0.02;
		GBC.gridy = 4;
		TextPanel.add(Func2Label, GBC);
		
		GBC.weighty = 0.27;
		GBC.gridy = 5;
		TextPanel.add(Func2, GBC);
		
		ShowPanel.setLayout(GBL);
		
		GBC.gridy = 0;
		GBC.weighty = 0.02;
        ShowPanel.add(LevelLabel, GBC);
        
        GBC.gridy = 1;
        GBC.weighty = 0.69;
        GBC.ipady = 50;
        ShowPanel.add(drawerscroll, GBC);
        
        GBC.ipady = 0;
        GBC.gridy = 2;
        GBC.weighty = 0.02;
		ShowPanel.add(ConsolLabel, GBC);
		
		GBC.gridy = 3;
        GBC.weighty = 0.27;
        ShowPanel.add(Consol, GBC);
        
        GamePanel.setLayout(GBL);
        
        GBC.gridy = 0;
        GBC.weighty = 1;
        GBC.weightx = 0.3375;
        GamePanel.add(ShowPanel, GBC);
        
        GBC.insets = new Insets(0, 10, 0, 0);
        GBC.gridx = 1;
        GBC.weightx = 0.6625;
        GamePanel.add(TextPanel, GBC);
        
        ButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        ButtonPanel.add(SaveButton);
        ButtonPanel.add(SyntaxButton);
        ButtonPanel.add(RunButton);
        ButtonPanel.add(StopButton);
        ButtonPanel.add(MenuButton);
        ButtonPanel.add(HelpButton);
        
        CaretPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        CaretPanel.add(FunctionPosition);
        CaretPanel.add(CaretPosition);
        
        MenuPanel.setLayout(GBL);
        
        GBC.insets = new Insets(0, 0, 0, 0);
        GBC.gridx = 0;
        GBC.weightx = 0.5;
        MenuPanel.add(ButtonPanel, GBC);
        GBC.gridx = 1;
        GBC.weightx = 0.5;
        MenuPanel.add(CaretPanel, GBC);
        
        MainPanel.setLayout(GBL);
        
        GBC.insets = new Insets(0, 10, 0, 10);
        GBC.gridx = 0;
        GBC.weightx = 1;
        GBC.weighty = 0.99;
        MainPanel.add(GamePanel, GBC);
        
        GBC.gridy = 1;
        GBC.weighty = 0.01;
        MainPanel.add(MenuPanel, GBC);
        
        try{
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("Users" + FileSystems.getDefault().getSeparator() + UserManager.user.getName() + FileSystems.getDefault().getSeparator() + 
					UserManager.user.getName() + "_consol")));
			try{
				pw.print(defText);
			}
			finally{
				pw.close();
			}
		}catch(IOException e){}
        
        ConsolTextArea.setText(defText);
        
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        f.add(MainPanel, BorderLayout.CENTER);
        f.pack();
        f.setSize(f.getPreferredSize());
        f.setVisible(true);
		
	}
	
	public static void close(){
		f.dispose();
	}
}
