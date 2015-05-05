package core;

public class LoginException extends Exception{
	
	/*
	 * Exception for login errors
	 * for example when the name or the password is incorrect
	 */
	
	/*
	 * Generated serialVersion number
	 */

	private static final long serialVersionUID = 5553456752257278570L;
	
	/*
	 * Constructor without specified detail massage
	 */
	
	public LoginException() {
	    super();
	}
		
	/*
	 * Constructor with specified detail massage
	 */
		
	public LoginException(String s) {
	    super(s);
	 }

}