package biz.janux.commerce.payment.interfaces.response.codes;

/**
 * 
 * @author Nilesh
 *	</br>
 *	Interface for AddressVerificationResponseCodes
 *	</br>
 *	Extends ResponseCodes
 */
public interface AddressVerificationResponseCodes extends ResponseCodes {
	
	/*
	 * AVS RESULT CODES
	 * Table 4.30 -- Response Codes
	 */ 
	
 	public static final char AVS_EXACT_MATCH_9_CHAR_ZIP = 'X';
 	
	public static final char AVS_EXACT_MATCH_5_CHAR_ZIP = 'Y';
	
	public static final char AVS_ADDRESS_MATCH = 'A';
	
	public static final char AVS_ZIP_MATCH_9_CHAR_ZIP = 'W';
	
	public static final char AVS_ZIP_MATCH_5_CHAR_ZIP = 'Z';
	
	public static final char AVS_NO_MATCH = 'N';
	
	public static final char AVS_VER_UNAVAILABLE_ADDRESS_UNAVAILABLE = 'U';
	
	public static final char AVS_VER_UNAVAILABLE_NON_US_ISSUER_DOES_NOT_PARTICIPATE = 'G';
	
	public static final char AVS_RETRY = 'R';
	
	public static final char AVS_ERROR_INELIGIBLE = 'E';
	
	public static final char AVS_SERV_UNAVAILABLE_SERVICE_NOT_SUPPORTED = 'S';
	
	// See bottom of Table 4.30, "international" AVS response codes
	
	public static final char AVS_ADDRESS_MATCH_INTERNATIONAL = 'B';
	
	public static final char AVS_EXACT_MATCH_INTERNATIONAL_1 = 'D';
	
	public static final char AVS_EXACT_MATCH_INTERNATIONAL_2 = 'M';
	
	public static final char AVS_SERV_UNAVAILABLE_INTERNATIONAL = 'C';
	
	public static final char AVS_VER_UNAVAILABLE_INTERNATIONAL = 'I';
	
	public static final char AVS_ZIP_MATCH_INTERNATIONAL = 'P';
 
	public static final String CALL_REFER_TO_ISSUER = "01";

	public static final String CALL_REFER_TO_ISSUER_SPECIAL_CONDITION = "02";

	public static final String PICK_UP_CARD = "04";

	public static final String PICK_UP_CARD_SPECIAL_CONDITION = "07";

	public static final String PICK_UP_CARD_LOST = "41";

	public static final String PICK_UP_CARD_STOLEN = "43";

	public static final String ALREADY_REVERSED = "79";

	public static final String AMOUNT_ERROR = "13";

	public static final String CARD_NO_ERROR = "14";

	public static final String DECLINE = "05";

	public static final String DECLINE_INSUFFICIENT_FUNDS = "51";

	public static final String DECLINE_EXCEEDS_ISSUER_WITHDRAWAL_LIMIT = "N4";

	public static final String DECLINE_EXCEEDS_WITHDRAWAL_LIMIT = "61";

	public static final String DECLINE_INVALID_SERVICE_CODE = "62";

	public static final String DECLINE_ACTIVITY_LIMIT_EXCEEDED = "65";

	public static final String DECLINE_VIOLATION = "93";

	public static final String EXPIRED_CARD = "54";

	public static final String INVALID_TRANSACTION = "12";

	public static final String NO_ACCOUNT = "78";

	public static final String NO_SUCH_ISSUER = "15";

	public static final String RE_ENTER = "19";

	public static final String SECURITY_VIOLATION = "63";

	public static final String SERV_NOT_ALLOWED_CARD = "57";

	public static final String SERV_NOT_ALLOWED_TERMINAL = "58";

	public static final String SYSTEM_MALFUNCTION = "96";

	public static final String TERM_ID_ERROR_INVALID_MERCHANT_ID = "03";

	public static final String DUPLICATE_TRANSACTION = "94";
	
	public static final String APPROVAL = "00";

	public static final String CARD_OK = "85";
	
	public static final String INVALID_ROUTING = "92";
}
