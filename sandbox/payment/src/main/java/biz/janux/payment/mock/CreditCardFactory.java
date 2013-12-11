package biz.janux.payment.mock;

import java.util.Date;

import biz.janux.geography.PostalAddress;
import biz.janux.payment.CreditCard;

/**
 ***************************************************************************************************
 * Convenience factory interface for creating mock credit card data
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since
 ***************************************************************************************************
 */
public interface CreditCardFactory {

	public CreditCard getCreditCardVisa();
	
	public CreditCard getCreditCardSavedVisa();
	
	public CreditCard getCreditCardAmericanExpress();
	
	public CreditCard getCreditCardCompleteVisa();

	/**
	 * 
	 * @param holderName
	 * @param number
	 * @param expirationDate
	 * @param typeCode
	 * @param postalAddress
	 * @return
	 */
	public CreditCard getCreditCard(String holderName, String number, Date expirationDate, String typeCode, PostalAddress postalAddress);
	
}
