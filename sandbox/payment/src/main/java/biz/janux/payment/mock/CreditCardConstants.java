package biz.janux.payment.mock;

import java.util.Date;
import biz.janux.payment.CreditCardImpl;

public class CreditCardConstants {

	public static final String CARD_HOLDER_NAME = "John Lennon";

	public static final String STATE = "CA";
	
	public static final String STATE_STRING = "California";

	public static final String POSTAL_CODE = "94043";

	public static final String ADDRESS = "1234 Amphitheatre Parkway";

	public static final String COUNTRY = "US";
	
	public static final String COUNTRY_STRING = "United States";

	public static final String CITY = "Mountain View";

	public static final String NUMBER_VISA = "4111111111111111";
	
	public static final String NUMBER_AMERICAN_EXPRESS = "371449635398431";
	
	public static final String CREDIT_CARD_AMERICAN_EXPRESS_TOKEN = "token371449635398431";
	
	public static final String CREDIT_CARD_VISA_TOKEN = "token4111111111111111";

	public static final String TYPE_VISA_CC = "VI";
	
	public static final String TYPE_AMERICAN_EXPRESS_CC = "AX";
	
	public static final String EXPIRATION = "12/2020";

	private static Date expDate;

	static {
		try { 
			expDate = CreditCardImpl.DEFAULT_DATE_FORMAT.parse(EXPIRATION);
		} catch (Exception e) { throw new RuntimeException("Unable to instantiate CreditCardConstaints.EXPIRATION_DATE",e); }
	}
	
	public static final Date   EXPIRATION_DATE = expDate;

	public static final String UUID = "010be75d-540f-4510-8be7-5d540f6510a4";
	
	public static final String NUMBER_VISA_MASKED = "4***********1111";
	
}
