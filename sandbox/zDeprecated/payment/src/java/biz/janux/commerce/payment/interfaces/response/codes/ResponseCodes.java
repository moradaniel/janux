package biz.janux.commerce.payment.interfaces.response.codes;
/**
 * 
 * @author Nilesh
 * </br>
 * Interface for Error response codes
 *
 */
public interface ResponseCodes {
	/**
	 * Error Response Codes
	 */
	public String ERROR_BLOCKEDTERMINAL = "B";

	public String ERROR_CARDTYPE = "C";

	public String ERROR_DEVICE = "D";

	public String ERROR_TRANSMISSION = "T";

	public String ERROR_UNKNOWN = "U";

	public String ERROR_ROUTNING = "V";
	
	public String STATUS_OK = "OK";
	
	public String STATUS_ERROR = "ERROR";

}
