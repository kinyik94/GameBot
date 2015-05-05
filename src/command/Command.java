package command;

import graphic.GameWindow;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

import core.Bot;
import core.UserManager;


public class Command {
	
	/*
	 * This class handles the commands.
	 * It checks the syntax of the code and runs the code
	 * The SyntaxChecker() reads the commands from the actual logged in user's code files,
	 * and check the commands syntax. If the syntax good, then the method put it in the appropriate cmd List.
	 * The run method read the commands from the cmd Lists and executes them.
	 */
	
	
	/*
	 * The par string array stores the commands parameters.
	 * The pars string is the string of the parameters, with split we get the par array.
	 * Line is the input line, it contains the command and the parameters.
	 * ln is the number of the line.
	 * isError is a boolean value and it is true when the program have found error.
	 */
	private static String[] par = null;
	private static String pars;
	private static String line = "";
	public static int ln = 0;
	private static boolean isError = false;
	
	/*
	 * The ArrayList cmdmain stores the commands of the main method
	 * The ArrayList cmdfunc1 stores the commands of the Function1 method
	 * The ArrayList cmdfunc2 stores the commands of the Function2 method
	 * HashMap loops stores the loops.
	 * The key of a loop is the line where the loop starts.
	 * The HashMap branchings stores the branchings.
	 * The key of a branching is the line where the branching's condition is.
	 * The ArrayDeque accespoints is for the function calls.
	 * If there is a function call it stores the accesspoint where the thread can continue
	 * after the function is over.
	 * cmdType stores the the type of the cmd list, it can be Main, Function1 or Function2.
	 */
	
	private static ArrayList<String> cmdmain = new ArrayList<String>();
	private static ArrayList<String> cmdfunc1 = new ArrayList<String>();
	private static ArrayList<String> cmdfunc2 = new ArrayList<String>();
	private static HashMap<Integer, Loop> loops = new HashMap<Integer, Loop>();
	private static HashMap<Integer, Branching> branchings = new HashMap<Integer, Branching>();
	private static ArrayDeque<String> accesspoints = new ArrayDeque<String>();
	private static ArrayList<String> cmd = null;
	private static String cmdType = null;
	
	/*
	 * Run method
	 * Get command from the cmdmain first.
	 * If there is a function call, the cmd will be a reference for appropriate cmd ist.
	 */
	
	public static boolean run(Bot b) throws RuntimeErrorException{
		
		/*
		 * Get the command from cmd.
		 */
		
		if(cmd.size() > ln){
			line = cmd.get(ln);
			ln++;
		}
		
		/*
		 * If there are no more commands in this function, 
		 * and the accespoints is Empty, so the main method is over, 
		 * then clear lists, set ln to 0 and return false.
		 * If the accespoints isn't empty, then we pop() the accespoint
		 * and we set the appropriate cmd, cmdType and ln.
		 */
		
		else{
			if(accesspoints.isEmpty()){
				ln = 0;
				cmd.clear();
				cmdmain.clear();
				cmdfunc1.clear();
				cmdfunc2.clear();
				return false;
			}
			else{
				String access = accesspoints.pop();
				cmdType = access.substring(0, 5);
				switch(cmdType){
				case "mainf":
					cmd = cmdmain;
				break;
				case "func1":
					cmd = cmdfunc1;
				break;
				case "func2":
					cmd = cmdfunc2;
				break;
				default:
					reset();
					throw new RuntimeException("No function like this in run() method");
				}
				ln = Integer.valueOf(access.substring(5, access.length()));	
				return true;
			}
		}
		
		/*
		 * If the command is move we call the Bot object's move() method.
		 */
		
		if(line.matches("move\\(.*\\)")){
			b.move();
		}
		
		/*
		 * If the command is turn, then we call the Bot object's turn() method
		 * with the specified parameter.
		 * The parameter is between the brackets, so we get a substring of the string
		 * between the brackets.
		 */
		
		else if(line.matches("turn\\(.*\\)")){
			pars = line.substring(5, line.length() - 1);
			if(pars.length() > 0){
				par = pars.split(" ");
				b.turn(par[0]);
			}
		}
		
		/*
		 * If the command is loop, then we look on the loop's condition.
		 * If the condition is true, we continue the program with the code in the loop,
		 * otherwise we jump to the end of the loop.
		 */
		
		else if(line.matches("loop.*")){
			par = line.split(" ");
			if(par.length != 2)
				throw new RuntimeErrorException("Too much parameters for loop at line: " + ln);
			else{
				int get = Integer.parseInt(par[1]);
				if(!loops.get(get - 1).conditionIsTrue(b))
					ln = get;
			}
		}
		
		/*
		 * If the command is endloop, then we jump to the line where the loop starts.
		 */
		
		else if(line.matches("endloop")){
			ln = loops.get(ln - 1).begin;
		}
		
		else if(line.matches("fi")){}
		
		/*
		 * If the command is if, then we look on the condition.
		 * If the condition is true, we continue the program with the code in the branching,
		 * otherwise we jump to the end of the branching.
		 */
		
		else if(line.matches("if.*")){
			par = line.split(" ");
			if(par.length != 2)
				throw new RuntimeErrorException("Too much parameters for if at line: " + ln);
			else{
				int get = Integer.parseInt(par[1]) - 1;
				if(!branchings.get(ln).conditionIsTrue(b))
					ln = get;
			}
		}
		
		/*
		 * If the command is function1, then we push the actual ln and cmdType to the accespoints deque,
		 * then set the cmd, cmdType and ln.
		 */
		
		else if(line.equals("function1")){
			accesspoints.add(cmdType + ln);
			cmd = cmdfunc1;
			cmdType = "func1";
			ln = 0;
		}
		
		/*
		 * If the command is function2, then we push the actual ln and cmdType to the accespoints deque,
		 * then set the cmd, cmdType and ln.
		 */
		
		else if(line.equals("function2")){
			accesspoints.add(cmdType + ln);
			cmd = cmdfunc2;
			cmdType = "func2";
			ln = 0;
		}
		
		/*
		 * If there is no command like the actual,
		 * then throw RunTomeException.
		 */
		
		else{
			cmdmain.clear();
			cmdfunc1.clear();
			cmdfunc2.clear();
			ln = 0;
			throw new RuntimeErrorException("Command not found at line: ");
		}
		
		/*
		 * If the Bot stepped on a black field, then
		 * the program is over, so we clear the cmd lists.
		 */
		
		if(b.getFieldColor().equals(Color.BLACK)){
			cmdmain.clear();
			cmdfunc1.clear();
			cmdfunc2.clear();
			ln = 0;
		}
		
		return true;
	}
	
	public static void SyntaxChecker(Bot b){
		
		/*
		 * PrintWriter to write out the errors to the consol JTextArea.
		 */
		
		PrintWriter pw = null;
		
		try{
			
			/*
			 * Open the user's consol file for append.
			 */
			
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("Users" + FileSystems.getDefault().getSeparator() + UserManager.user.getName() + FileSystems.getDefault().getSeparator() + 
					UserManager.user.getName() + "_consol", true)));
			pw.append(GameWindow.defText);
			
			/*
			 * Open the code files after each other and read in the code,
			 * then chack its syntax.
			 */
			
			for(int i = 1; i <= 3; i++){
				switch (i){
					case 1:
						cmd = cmdmain;
						cmdType = "Main";
					break;
					case 2:
						cmd = cmdfunc1;
						cmdType = "Function1";
					break;
					case 3:
						cmd = cmdfunc2;
						cmdType = "Function2";
					break;
					default:
						throw new IndexOutOfBoundsException("Unreachable code in SyntaxChacker()");
				}
			
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Users" + FileSystems.getDefault().getSeparator() + UserManager.user.getName() + FileSystems.getDefault().getSeparator() + 
						UserManager.user.getName() + "_" + cmdType + "_" + b.getLevel().levelNumber)));
				
				/*
				 * loopindex stores the lines where the loops starts.
				 * ifindex stores the lines where the branchigs starts.
				 * colorMap stores the colors what the user can use for define conditions
				 */
				
				ArrayDeque<Integer> loopindex = new ArrayDeque<Integer>();
				ArrayDeque<Integer> ifindex = new ArrayDeque<Integer>();
				HashMap<String, Color> colorMap = new HashMap<String, Color>();
				
				/*
				 * Clear the actual cmd list before we put the commands in it.
				 */
				
				cmd.clear();
				
				/*
				 * Put the usable colors in the colorMap
				 */
				
				colorMap.put("Green", Color.GREEN);
				colorMap.put("Yellow", Color.YELLOW);
				colorMap.put("Red", Color.RED);
				colorMap.put("Blue", Color.BLUE);
				colorMap.put("Gray", Color.LIGHT_GRAY);
				colorMap.put("Cyan", Color.CYAN);
				
				try{
					
					/*
					 * While there is line to read, we read it in and chack the syntax
					 */
					
					while(reader(br)){
						try{
							
							/*
							 * Get down the white space charatres from the line.
							 */
							
							String replacedLine = line.replaceAll("\\s+", "");
							
							/*
							 * If the replaced string is move() then we check the parameters
							 * if there are any parameter, then throw SyntaxErrorException,
							 * otherwise add it to the cmd.
							 */
							
							if(replacedLine.matches("move\\(.*\\)")){
								pars = replacedLine.substring(5, replacedLine.length() - 1);
								if(pars.length() > 0)
									throw new SyntaxErrorException("Too mutch parameter for move command in " + cmdType + " at line: " + ln);
								cmd.add(replacedLine);
							}
							
							/*
							 * If the replaced string is turnmove() then we check the parameters
							 * if there are more than 1 parameter, then throw SyntaxErrorException.
							 * If the given parameter is not appropriate, then throw SyntaxErrorException.
							 * If there wasn't any problem, add it to the cmd.
							 */
							
							else if(replacedLine.matches("turn\\(.*\\)")){
								pars = replacedLine.substring(5, replacedLine.length() - 1);
								if(pars.length() > 0){
									par = pars.split(" ");
									if(par.length != 1)
										throw new SyntaxErrorException("Too mutch parameter for turn command in " + cmdType + " at line: " + ln);
									if(!(par[0].equals("right")) && !(par[0].equals("left")))
										throw new SyntaxErrorException(par[0] + " is not acceptable parameter for turn command in " + cmdType + " at line: " + ln);
									cmd.add(replacedLine);
								}
							}
							
							/*
							 * If the command is for(), then we look on the condition.
							 * If the condition is empty, then it is an endless loop, so we create a loop and 
							 * put it in the loops map.
							 * If there is more paramters, than 1 or the parameter is not a number, 
							 * then throw SyntaxErrorException.
							 * If there wasn't any problem, we create a loop and 
							 * put it in the loops map, push ln in the loopindex deque and
							 * add "loop" to cmd.
							 */
							
							else if(replacedLine.matches("for\\(.*\\)")){
								pars = replacedLine.substring(4, replacedLine.length() - 1);
								if(pars.length() == 0){
									loops.put(ln, new Loop(ln - 1, "for", 0, Color.BLACK, true));
								}
								else if(pars.length() > 0){
									par = pars.split(" ");
									if(par.length > 1)
										throw new SyntaxErrorException("Too mutch parameter for loop command in " + cmdType + " at line: " + ln);
									try{
										loops.put(ln, new Loop(ln - 1, "for", Integer.parseInt(par[0]) + 1, Color.BLACK, true));
									}
									catch(NumberFormatException nfe){
										throw new SyntaxErrorException("Not appropriate parameter for loop command in " + cmdType + " at line: " + ln);
									}
								}
								loopindex.addFirst(ln);
								cmd.add("loop");
							}
							
							/*
							 * If the command is while(), then we look on the condition.
							 * If condition is empty, then throw SyntaxErrorException.
							 * If there are more then one one parameter, then throw SyntaxErrorException.
							 * If the parameter is true, we create an endless for loop.
							 * If there wasn't any problem, we create a loop and 
							 * put it in the loops map, push ln in the loopindex deque and
							 * add "loop" to cmd.
							 */
							
							else if(replacedLine.matches("while\\(.*\\)")){
								pars = replacedLine.substring(6, replacedLine.length() - 1);
								if(pars.length() == 0){
									throw new SyntaxErrorException("No parameter for while in " + cmdType + " at line: " + ln);
								}
								else if(pars.length() > 0){
									par = pars.split(" ");
									if(par.length != 1)
										throw new SyntaxErrorException("Too mutch parameter for loop command in " + cmdType + " at line: " + ln);
									if(par[0].equals("true"))
										loops.put(ln, new Loop(ln - 1, "for", 0, Color.BLACK, true));
									else{
										if(par[0].matches("FieldIsNot.*"))
											loops.put(ln, new Loop(ln - 1, "while", 0, colorMap.get(par[0].substring(10, par[0].length())), false));
										else if(par[0].matches("FieldIs.*"))
											loops.put(ln, new Loop(ln - 1, "while", 0, colorMap.get(par[0].substring(10, par[0].length())), true));
										else
											throw new SyntaxErrorException("Not suitable parameter for while in " + cmdType + " at line: " + ln);
									}
								}
								loopindex.addFirst(ln);
								cmd.add("loop");
							}
							
							/*
							 * If the command is endloop, then we pop the last index from the loopindex.
							 * If the loopindex is empty, then throw SyntaxErrorException.
							 * With the index, we get the loop from loops and put back to with the key ln - 1.
							 * Add the actual line number to the loop in the cmd, then add endloop. 
							 */
							
							else if(replacedLine.equals("endloop")){
								if(loopindex.size() == 0)
									throw new SyntaxErrorException("More endloop, than loop starts in " + cmdType + " !");
								int li = loopindex.pop() - 1;
								Loop tmploop = loops.get(li + 1);
								loops.remove(li + 1);
								loops.put(ln - 1, new Loop(tmploop));
								cmd.set(li, "loop " + ln);
								cmd.add("endloop");
							}
							
							/*
							 * If the command is if, then look on the condition,
							 * if there is no condititon or there are more than 1, throw SyntaxErrorException.
							 * If condition is "true", then it is an always true branching.
							 * If there wasn't any problem ,we create a branching, put it in branchings, push ln in ifindex
							 * and add if to cmd.
							 */
							
							else if(replacedLine.matches("if\\(.*\\)")){
								pars = replacedLine.substring(3, replacedLine.length() - 1);
								if(pars.length() == 0){
									throw new SyntaxErrorException("No parameter for if in " + cmdType + " line: " + ln);
								}
								else if(pars.length() > 0){
									par = pars.split(" ");
									if(par.length != 1)
										throw new SyntaxErrorException("Too mutch parameter for if in " + cmdType + " at line: " + ln);
									if(par[0].equals("true")){
										branchings.put(ln, new Branching(true, Color.BLACK, true));
									}
									else{
										if(par[0].matches("FieldIsNot.*"))
											branchings.put(ln, new Branching(false, colorMap.get(par[0].substring(10, par[0].length())), false));
										else if(par[0].matches("FieldIs.*"))
											branchings.put(ln, new Branching(true, colorMap.get(par[0].substring(7, par[0].length())), false));
										else
											throw new SyntaxErrorException("Not suitable parameter for if in " + cmdType + " at line: " + ln);
									}
								}
								ifindex.addFirst(ln);
								cmd.add("if");
							}
							
							/*
							 * If the command is fi, then we pop the last index from the ifindex.
							 * If the ifindex is empty, then throw SyntaxErrorException.
							 * Add the actual line number to the if in the cmd, then add fi. 
							 */
							
							else if(replacedLine.equals("fi")){
								if(ifindex.size() == 0)
									throw new SyntaxErrorException("More fi, than if statements in " + cmdType + " !");
								int li = ifindex.pop() - 1;
								cmd.set(li, "if " + ln);
								cmd.add("fi");
							}
							
							/*
							 * If the command function1(),
							 * if there is any parameter, then throw SyntaxErrorException,
							 * otherwise add function1 to cmd.
							 */
							
							else if(replacedLine.matches("function1\\(.*\\)")){
								
								pars = replacedLine.substring(10, replacedLine.length() - 1);
								if(pars.length() > 0)
									throw new SyntaxErrorException("Too mutch parameter for if in " + cmdType + " at line: " + ln);
								cmd.add("function1");
							}
							
							/*
							 * If the command function2(),
							 * if there is any parameter, then throw SyntaxErrorException,
							 * otherwise add function2 to cmd.
							 */
							
							else if(replacedLine.matches("function2\\(.*\\)")){
								pars = replacedLine.substring(10, replacedLine.length() - 1);
								if(pars.length() > 0)
									throw new SyntaxErrorException("Too mutch parameter for if in " + cmdType + " at line: " + ln);
								cmd.add("function2");
							}
							
							else if(line.trim().isEmpty());
							
							/*
							 * If there is no command like this, then throw SyntaxErrorException.
							 */
							
							else
								throw new SyntaxErrorException("Command not found in " + cmdType + " at line: " + ln);
						}
						
						/*
						 * If there was syntax error, then write out to the consol file
						 * and set isError true.
						 */
						
						catch(SyntaxErrorException er){
							pw.append("SyntaxError: " + er.getMessage() + System.lineSeparator());
							cmd.add(line);
							isError = true;
						}
					}
					
					/*
					 * After checked the syntax, set ln to 0, par to null.
					 * If there was any error, then clear cmds. 
					 */
					
					ln = 0;
					par = null;
					
					if(isError){
						cmdmain.clear();
						cmdfunc1.clear();
						cmdfunc2.clear();
					}
					
					if(loopindex.size() != 0)
						throw new SyntaxErrorException("More loops starts, than endloop in  " + cmdType + " !");
					
					if(ifindex.size() != 0)
						throw new SyntaxErrorException("More if statements, than fi in  " + cmdType + " !");
					
				}
				
				/*
				 * If there was syntax error, then write out to the consol file.
				 */
				
				catch(SyntaxErrorException er){
					pw.append("SyntaxError: " + er.getMessage() + System.lineSeparator());
					if(loopindex.size() != 0)
						loopindex.clear();
					if(ifindex.size() != 0)
						ifindex.clear();
				}
				finally{
					br.close();	
				}
			}
			
			pw.close();
			
			/*
			 * After chech all files, set cmd and cmdType to main.
			 */
			
			cmd = cmdmain;
			cmdType = "mainf";
				
		}
		catch(IOException er){
			System.out.println(er.getCause().toString());
		}
		finally{
			if(pw != null)
				pw.close();
		}
		
	}
	
	/*
	 * Read in one line with the br reader.
	 */
	
	private static boolean reader(BufferedReader br){
		try{
			
			line = br.readLine();

			if (line == null){
				return false;
			}
			else{
				ln++;
			}
			
			return true;
		}
		catch(IOException e){
				System.out.println(e.getMessage());
				return false;
		}
	}
	
	/*
	 * Reset the Attributons.
	 * par, line, ln to null, clear cmds, set cmd to null, clear loops, branchings, and accespoints.
	 */
	
	public static void reset(){
		par = null;
		line = "";
		ln = 0;
		isError = false;
		cmdmain.clear();
		cmdfunc1.clear();
		cmdfunc2.clear();
		cmd = null;
		loops.clear();
		branchings.clear();
		accesspoints.clear();
	}
	
}
