package core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptWithMD5 {
	
	/*
	 * This method encrypts the users password with MD5
	 */

	public static String cryptWithMD5(String pass){
		try {
		    	
			MessageDigest md;
			md = MessageDigest.getInstance("MD5");
			byte[] passBytes = pass.getBytes();
			md.reset();
			byte[] digested = md.digest(passBytes);
			StringBuffer sb = new StringBuffer();
		        
			for(int i=0;i<digested.length;i++){
				sb.append(Integer.toHexString(0xff & digested[i]));
			}
		        
			return sb.toString();
		        
		} 
		catch (NoSuchAlgorithmException e) {
			System.out.println(e.getMessage());
		}
		    
		return null;
	    
	}
}
