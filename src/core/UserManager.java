package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class UserManager {
	
	/*
	 * Manages the users
	 * Stores the actual logged in user and all of the users in a map
	 * The values of the map are users and the keys are the users names
	 */
	
	public static User user = null;
	private static HashMap<String, User> users;
	
	/*
	 * Register a user to the game
	 * It creates a new user with the specified name and password
	 * Put it in the users map and write out users
	 * Creates the folder of the new user, 
	 * where the new user's codes will be stored
	 * throw LoginException if already exists a user width the specified Name
	 */
	
	public static void register(String Name, String Pass) throws LoginException{
		if(!users.containsKey(Name)){
			users.put(Name, User.create(Name, Pass, 1));
			write();
			File dir = new File("Users");
			if(!dir.isDirectory())
				dir.mkdir();
			dir = new File("Users" + FileSystems.getDefault().getSeparator() + Name);
			dir.mkdir();
			user = users.get(Name);
		}
		else
			throw new LoginException("Username already exists!");
	}
	
	/*
	 * Recursively remove a directory from the filesystem
	 */
	
	public static void rmdir(File f) {
		File[] list = f.listFiles();
		
		/*
		 * If there are files in the directory, than we remove these too.
		 */
		
			for (int i = 0; i < list.length; i++) {
				if (list[i].isDirectory()) {
					rmdir(list[i]);
				}
				else{
					list[i].delete();
				}
			}
			
		f.delete();
	}
	
	/*
	 * Removes the actual logged in user's account.
	 * Remove the user's directory from the filesystem,
     * remove the user from the map and set the actual user null.
	 */
	
	public static void remove(){
		File dir = new File("Users" + FileSystems.getDefault().getSeparator() + user.getName());
		if(dir.isDirectory())
			rmdir(dir);
		users.remove(user.getName());
		user = null;
		write();
	}
	
	/*
	 * If the given Name and Password are corrects
	 * it sets the actual logged in user to the user with the specified Name.
	 * throw LoginException if the name or the password incorrect.
	 */
	
	public static void login(String Name, String Pass) throws LoginException{
		if(!users.containsKey(Name))
			throw new LoginException("Wrong Username or Password!");
		else{
			if(users.get(Name).Login(Pass))
				user = users.get(Name);
			else
				throw new LoginException("Wrong Username or Password!");
		}
	}
	
	/*
	 * Reads the users list from a file
	 * and stores it in the users map.
	 */
	
	public static void read(){
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream("user_list"))));
			try{
				users = new HashMap<String, User>();
				String line = br.readLine();
				while (line != null){
					String[] map = new String[3];
					map = line.split(" ");
					users.put(map[0], new User(map[0], map[1], Integer.valueOf(map[2])));
					line = br.readLine();
				}
			}
			finally{
				br.close();
			}
		}
		catch(IOException e){
			users = new HashMap<String, User>();
		}
	}
	
	/*
	 * Writes out the list of the users to the user_list file
	 */
	
	public static void write(){
		try{
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream("user_list"))));
			try{
				for(Map.Entry<String, User> i : users.entrySet()){
					i.getValue().write(pw);
				}
			}
			finally{
				pw.close();
			}
		}
		catch(IOException e){}
	}
	
	/*
	 * Returns true if exists an account with the given Name
	 */
	
	public static boolean consist(String Name){
		return users.containsKey(Name);
	}
	
}
