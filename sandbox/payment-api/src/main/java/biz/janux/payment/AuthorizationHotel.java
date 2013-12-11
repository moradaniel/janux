package biz.janux.payment;


/**
 * Represents a Credit Card Authorization for Hotels.
 * 
 * @author albertobuffagni@gmail.com 
 */
public interface AuthorizationHotel extends Authorization {

	/**
	 * The value of this sub-field is the anticipated length of the hotel stay or auto rental.
	 * When the market specific data is supplied in an incremental transaction, this sub-field 
	 * represents the number of additional days for the hotel stay or auto rental.
	 * This sub-field must be in the range of "01" to "99" for all original authorization requests. For
	 * incremental authorization requests, the range for this sub-field is "00" to "99". For No Show
	 * Authorizations, this sub-field should be set to "01". For Advanced Lodging Deposits and 
	 * Auto Rental PrePays, this sub-field should reflect the number of days being paid for in the 
	 * advanced payment.
	 */
	public Integer getStayDuration();
	public void setStayDuration(Integer stayDuration);	
}
