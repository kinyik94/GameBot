package command;

public class RuntimeErrorException extends Exception {

	private static final long serialVersionUID = -3620638086492512605L;
	
	/*
	 * Constructor without specified detail massage
	 */
	
	public RuntimeErrorException() {
	    super();
	}
			
	/*
	 * Constructor with specified detail massage
	 */
		
	public RuntimeErrorException(String s) {
	    super(s);
	 }
	
}
