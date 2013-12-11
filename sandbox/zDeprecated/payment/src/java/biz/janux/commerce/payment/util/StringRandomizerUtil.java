package biz.janux.commerce.payment.util;

import com.Ostermiller.util.RandPass;

/**
 * @author Nilesh
 * <br/>
 * <br/>
 * <b>Description :</b>
 * Class to handle String randomizer functionality.<br/>
 * This functionality will be used mainly for generating UUID 
 * for credit card and the merchant account
 * */
public class StringRandomizerUtil {
	
	private static final int MAX_LENGTH = 32;
	private static final String SALT = "1G*8+i";
	
	public static String getRandomStringFor(String string){
		string += SALT; 
		char[] chars = string.toCharArray();		
		return new RandPass(chars).getPass(MAX_LENGTH);
	}
}
