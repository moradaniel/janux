package biz.janux.commerce.payment.util;
/**
 * 
 * @author Nilesh
 * </br>
 * Interface for Constants
 *
 */
public interface Constants {
	
	public static String PAYMENT_PROCESSING_ERROR_TO = "myhms-monitor@mycrs.innpoints.com";
	
	public static String PAYMENT_PROCESSING_ERROR_FROM = "myhms-monitor@mycrs.innpoints.com";
	
	public static final char FIELD_SEPARATOR = '\u001c';
	
	// START OF TEXT
	public static final char STX = '\u0002';
	
	// END OF TRANSMISSION BLOCK
	public static final char ETB = '\u0017';
	
	// END OF TEST
	public static final char ETX = '\u0003';
	
	public static final char MESSAGE_DELIMITER = '.';
	
	public static final char RECORD_FORMAT_AUTHORIZATION = 'D';
	public static final char RECORD_FORMAT_SETTLEMENT = 'K';
	
	public static final char APPLICATION_TYPE_SINGLE = '0';
	public static final char APPLICATION_TYPE_SETTLEMENT = '1';
	public static final char APPLICATION_TYPE_MULTIPLE = '2';	
	public static final char APPLICATION_TYPE_INTERLEAVED = '4';	
	
	// 'Q' Third Party Developer
	public static final char DEVICE_CODE = 'Q';
	
	// 'H' Hotel
	public static final char INDUSTRY_CODE = 'H';
	
	// 840 - USD
	public static final String CURRENCY_CODE_USD = "840";
	
	// 840 - USA
	public static final String COUNTRY_CODE_US = "840";
	
	// 00 - English
	public static final String LANGUAGE_INDICATOR_ENGLISH = "00";
	
	// 708 - PST
	public static final String TIME_ZONE_DIFFERENTIAL_PST = "708";
	
	// 7011 - HOTEL MERCHANT CATEGORY CODE
	public static final String MERCHANT_CATEGORY_CODE_HOTEL = "7011";
	
	// 'N' - Not supported
	public static final char REQUESTED_ACI_CPS_CAPABLE = 'Y';
	public static final char REQUESTED_ACI_NOT_SUPPORTED = 'N';
	public static final char REQUESTED_ACI_INCREMENTAL_AUTHORIZATION_REQUEST = 'I';
	// TODO: Find a better name for this variable?
	// This is used for non-swiped transactions. See Vital documentation.
	public static final char REQUESTED_ACI_CPS_CAPABLE_PREFERRED_CUSTOMER_AUTHORIZATION = 'P';
	
	// "54" -- Card Present
	// "56" -- Card Not Present
	// "58" -- Card Authentication (Check if lost/stolen, also AVS)
	public static final String TRANSACTION_CODE_CARD_PRESENT = "54";
	public static final String TRANSACTION_CODE_CARD_NOT_PRESENT = "56";
	public static final String TRANSACTION_CODE_CARD_AUTHENTICATION = "58";
	public static final String TRANSACTION_CODE_REVERSAL_PRE_SETTLEMENT = "59";
	public static final String TRANSACTION_CODE_REVERSAL_POST_SETTLEMENT = "5A";
	
	public static final String TRANSACTION_CODE_CREDIT_RETURN = "CR";
	
	public static final char CARD_HOLDER_ID_SIGNATURE = '@';
	public static final char CARD_HOLDER_ID_AVS = 'N';
	public static final char CARD_HOLDER_ID_CARD_PRESENT_AVS = 'M';
	
	public static final char ACCOUNT_DATA_SOURCE_NO_CARDREADER = '@';
	public static final char ACCOUNT_DATA_SOURCE_TRACK_2_READ  = 'D';
	public static final char ACCOUNT_DATA_SOURCE_TRACK_1_READ  = 'H';
	public static final char ACCOUNT_DATA_SOURCE_KEYED_TRACK_2_CAPABLE = 'T';
	public static final char ACCOUNT_DATA_SOURCE_KEYED_TRACK_1_CAPABLE = 'X';
	
	
	// ' ' -- Not Participating
	public static final char PRESTIGIOUS_PROPERTY_INDICATOR_NOT_PARTICIPATING = ' ';
	
	// 'H' -- Hotel
	public static final char MARKET_SPECIFIC_DATA_ID_HOTEL = 'H';
	
	public static final String DEVELOPER_ID = "000572";
	public static final String VERSION_ID = "B020";
	
	public static final char X25_ROUTING_ID = 'Z';
	
	// Section 5.3
	public static final String RECORD_TYPE_HEADER = "H@@@B";
	public static final String RECORD_TYPE_DETAIL = "D@@@P";
	public static final String RECORD_TYPE_AMEX_DETAIL = "D@@AP";
	public static final String RECORD_TYPE_PARAMETER = "P@@@@";
	public static final String RECORD_TYPE_TRAILER = "T@@@@";
	
	public static final char BLOCKING_INDICATOR_NON_BLOCKING = '0';
	public static final char BLOCKING_INDICATOR_BLOCKING = '2';
	
	// Section 4.120
	// SETTLEMENT ONLY	
	public static final String MERCHANT_LOCATION_NUMBER = "00001";
	
	// Section 4.38
	// SETTLEMENT ONLY
	// Note: NOT USED WITH HOTEL
	public static final String CASH_BACK_TOTAL = "0000000000000000";
		
	public static final char AUTHORIZATION_SOURCE_CODE_DEVICE_GENERATED = '6';
	public static final char AUTHORIZATION_SOURCE_CODE_NOT_AUTHORIZED = '9';
	
	public static final char VOID_INDICATOR_NOT_VOIDED = ' ';
	public static final String TRANSACTION_STATUS_CODE_NO_REVERSAL = "00";
	public static final String TRANSACTION_STATUS_CODE_REVERSAL = "10";
	public static final char PURCHASE_IDENTIFIER_FORMAT_CODE_HOTEL = '4';
	
	// Section 4.39
	// SETTLEMENT ONLY
	// AMERICAN EXPRESS ONLY
	public static final char CHARGE_TYPE_HOTEL = '1';
	public static final char CHARGE_TYPE_RESTAURANT = '2';
	public static final char CHARGE_TYPE_GIFT_SHOP = '3';
	
	// Section 4.137
	// NO SHOW INDICATOR
	// FOR NON-AMEX CARDS
	// SETTLEMENT ONLY
	public static final char NO_SHOW_INDICATOR_NOT_APPLICABLE = '0';
	public static final char NO_SHOW_INDICATOR_NOSHOW = '1';
	
	// AMEX - Special Program Indicator
	// Section 4.186
	// SETTLEMENT ONLY
	// AMERICAN EXPRESS ONLY
	// This is for Purchases with field 4.39 = 2 || 3 (non-hotel specific)
	public static final char SPECIAL_PROGRAM_INDICATOR_PURCHASE = '1';
	public static final char SPECIAL_PROGRAM_INDICATOR_NOSHOW = '2';
	public static final char SPECIAL_PROGRAM_INDICATOR_CARD_REPOSIT = '3';
	public static final char SPECIAL_PROGRAM_INDICATOR_DELAYED_CHARGE = '4';
	public static final char SPECIAL_PROGRAM_INDICATOR_EXPRESS_SERVICE = '5';
	public static final char SPECIAL_PROGRAM_INDICATOR_ASSURED_RESERVATION = '6';
	// This is what we should use?
	public static final char SPECIAL_PROGRAM_INDICATOR_OTHER = ' ';
	
	// AVS RESULT CODES
	// Table 4.30 -- Response Codes
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
	
	// Reimbursement Attribute
	// Section 4.160 EIS 1081
	public static final char REIMBURSEMENT_ATTRIBUTE = '0';
	
	public static final String GROUP_III_VERSION_NUMBER_DEVELOPER_INFORMATION = "020";
	
	public static final String NORMAL="NORMAL";
	public static final String REVERSAL="REVERSAL";
	public static final String INCREMENT="INCREMENT";
	public static final String CreditDetail = "CreditDetail";
	
	
	
}
