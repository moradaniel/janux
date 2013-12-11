package biz.janux.payment;



/**
 * 
 * @author Nilesh
 *	</br>
 *	Interface for  Settlement Response Codes
 *	</br>
 *
 */
public interface SettlementResponseCodes {
	
	/**
	 * Settlement RESULT CODES
	 * Table 4.4 -- Batch Response Codes
	 */
	public static final String GOODBATCH = "GB";

	public static final String REJECTEDBATCH = "RB";

	public static final String DUPLICATEBATCH = "QD";
	
	/**
	 * Error Response Codes 
	 */
	public static final String BLOCKEDTERMINAL = "B";

	public static final String CARDTYPEERROR = "C";

	public static final String DEVICEERROR = "D";

	public static final String BATCHERROR = "E";

	public static final String SEQUENCEERROR = "S";

	public static final String TRANSMISSIONERROR = "T";

	public static final String UNKNOWNERROR = "U";

	public static final String ROUTNINGERROR = "V";

	public static final String HEADERERROR = "H";

	public static final String PARAMETERERROR = "P";

	public static final String DETAILERROR = "D";

	public static final String LINEITEMERROR = "L";

	public static final String TRAILERERROR = "T";

	public String STATUS_OK = "OK";
	
	public String STATUS_ERROR = "ERROR";

}

