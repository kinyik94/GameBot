package graphic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HelpFrame extends JFrame {
	
	/*
	 * Generated serialVersion number
	 */
	
	private static final long serialVersionUID = 7009746366514904469L;
	
	/*
	 * BufferedReader for read in the help.txt file.
	 */
	
	private BufferedReader br;
	
	/*
	 * Create a new window width a JTextArea in it with the content if the help.txt file.
	 */
	
	public HelpFrame(){
		
		super();
		try{
			
			br = new BufferedReader(new InputStreamReader(new FileInputStream("help.txt")));
			
			try{
				
				this.setPreferredSize(new Dimension(640, 360));
				JPanel panel = new JPanel();
				panel.setLayout(new GridBagLayout());
				GridBagConstraints constraints = new GridBagConstraints();
				constraints.fill = GridBagConstraints.BOTH;
				constraints.weightx = 1;
				constraints.weighty = 1;
				JTextArea lines = new JTextArea();
				lines.read(br, new File("help.txt"));
				lines.setEditable(false);
				
				JScrollPane scrollPane = new JScrollPane(lines);
				panel.add(scrollPane, constraints);
				
				this.add(panel, BorderLayout.CENTER);
				this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				this.pack();
				this.setSize(this.getPreferredSize());
			}
			finally{
				br.close();
			}
		}
		catch(IOException e){}
		
	}
	
}
