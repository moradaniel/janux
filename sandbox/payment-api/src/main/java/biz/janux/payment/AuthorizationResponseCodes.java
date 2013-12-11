package biz.janux.payment;


/**
 * 
 * A response code indicates the status of the authorization request.
 *  
 * Table4.31  provides a listing of currently defined 
 * response codes. A response code of "00" represents an approval. A response code of "85" 
 * represents a successful card verification returned  normally by transaction codes 58, 68, and 
 * 88. All other response codes represent non-approved requests. Do NOT interpret all non-
 * approved response codes as "DECLINED".
 * 
 * @author Nilesh
 *
 */

public interface AuthorizationResponseCodes {
	/*
	 * Authorization RESULT CODES
	 * Table 4.31 -- Response Codes
	 */ 
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
