package biz.janux.payment;

import java.util.Map;


/**
 ***************************************************************************************************
 * Interface class that can be used to create pluggable Credit Card validators that perform standard
 * validation on CreditCards, such as:
 * <ul>
 * 	<li>Checking that the expiration date is in the future</li>
 * 	<li>Checking that the CreditCardType matches the one encoded in the CreditCard Number</li>
 * 	<li>Performing <a href="http://en.wikipedia.org/wiki/Luhn_algorithm">Luhn validation</a></li>
 * </ul>
 *
 * Or any other validation that may be appropriate. The CreditCardStorageService implements this
 * interface, and we recommend that implementations of the service delegate to validator classes
 * that implement this interface, so that the validation can be injected and modified as necessary
 * to suit individual projects.
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.4.0 - 2012-01-04
 * @see <a href="http://www.merriampark.com/anatomycc.htm>Anatomy of CreditCard Numbers</a>
 *
 ***************************************************************************************************
 */
public interface CreditCardValidator
{
	public static final String CC_ERR_EXPIRATION = "CC_EXP";
	public static final String CC_ERR_CHECKSUM = "CC_CHECKSUM";
	public static final String CC_ERR_CARDTYPE = "CC_CARDTYPE";
	
	/** 
	 * Given a CreditCard object, this method will run several validations on the data and return a
	 * Map of error messages, keyed by an error code; implementations of this method should return an
	 * empty map if the CreditCard passes validation and there are no errors to report.  
	 */
	public Map<String, String> validate(CreditCard creditCard);

	/** 
	 * Convenience method that calls {@link #validate(CreditCard)} and returns true if the CreditCard
	 * passes validation; 
	 */
	public boolean isValid(CreditCard creditCard);
}
