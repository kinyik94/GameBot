package command;

public class SyntaxErrorException extends Exception{

	private static final long serialVersionUID = -1784958097133725696L;
	
	/*
	 * Constructor without specified detail massage
	 */
	
	public SyntaxErrorException() {
	    super();
	}
		
	/*
	 * Constructor with specified detail massage
	 */
		
	public SyntaxErrorException(String s) {
	    super(s);
	 }
	
}
