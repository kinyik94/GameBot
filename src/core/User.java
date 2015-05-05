package core;

import java.io.PrintWriter;

public class User{
	
	/*
	 * A user have a name, a password.
	 * The levelNumber stores the number of the last level 
	 * what the user can do.
	 */
	
	private String UserName;
	private String PassWord;
	public int levelNumber;
	
	/*
	 * Initializes a newly created User object with the specified name, password 
	 * and levelNumber.
	 */
	
	public User(String Name, String Pass, int levelNumber){
		UserName = Name;
		PassWord = Pass;
		this.levelNumber = levelNumber;
	}
	
	/*
	 * Create a new User with encrypted password
	 */
	
	public static User create(String Name, String Pass, int levelNumber){
		return new User(Name, CryptWithMD5.cryptWithMD5(Pass), levelNumber);
	}
	
	/*
	 * Returns true if the given password is correct 
	 */
	
	public boolean Login(String Pass){
		return PassWord.equals(CryptWithMD5.cryptWithMD5(Pass));
	}
	
	/*
	 * Returns the name of the User
	 */
	
	public String getName(){
		return UserName;
	}
	
	/*
	 * Writes out the user's parameters
	 */
	
	public void write(PrintWriter pw){
		pw.println(UserName + " " + PassWord + " " + levelNumber);
	}
	
}
