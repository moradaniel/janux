package biz.janux.payment;

import java.io.Serializable;
import java.util.Date;

import biz.janux.geography.PostalAddress;


/**
 ***************************************************************************************************
 * Represents a Credit Card Account
 *
 * @author <a href="mailto:philippe.paravicini@janux.org">philippe.paravicini@janux.org</a>
 * @since 0.4
 ***************************************************************************************************
 */
public interface CreditCard extends Serializable
 {
	/**       
	 * A random alphanumeric string that uniquely identifies this Credit Card in the Janux Payment
	 * Service. This is a unique code that external clients can use to reference the Credit Card when
	 * calling the Janux Payment Service.
	 */
	public String getUuid();

	/**       
	 * The type of Credit Card, such as Visa, Mastercard, American Express, Diner, Discover, etc...
	 */
	public CreditCardType getType();
	public void setType(CreditCardType type);
	
	/**       
	 * A two-character representation of the CreditCardType edit card, as indicated in {@link
	 * CreditCardTypeEnum}; this field is provided as a convenience to gather the type of the Credit
	 * Card when it is gathered as a string, or to be used when displaying the Credit Card or
	 * displaying it to a String; This field should return a value congruent with the one returned by
	 * the field {@link #getType()}.
	 */
	public String getTypeCode();
	public void setTypeCode(String code);

	/**       
	 * The Primary Account Number (PAN) of the card, commonly known as the Credit Card Number; 
	 * this field should be null unless the system or user handling this object has privileges to view
	 * credit cards in clear text (unencrypted)
	 */
	public String getNumber();
	public void setNumber(String number);

	/**       
	 * A masked representation of the Credit Card PAN which can be stored, transmitted, or displayed
	 * in a system without causing that system to come under the scope of Payment Card Industry (PCI)
	 * compliance. Applications can use this field to display cached masked numbers without having to
	 * decrypt the credit card or retrieve it from a restricted pci-compliant storage.
	 */
	public String getNumberMasked();
	public void setNumberMasked(String number);

	/**       
	 * The expiration month and year of the Credit Card
	 */
	public Date getExpirationDate();
	public void setExpirationDate(Date expirationDate);

	/**       
	 * The 3 letter security code in the back of the card, also known as the Card Verification Code
	 * (CVC), or Card Verification Value (CVV);  Note that PCI Compliance states very explicitly that
	 * this code should not be stored, though some businesses may require storing this code for short
	 * time periods.
	 */
	public String getSecurityCode();
	public void setSecurityCode(String securityCode);

	/** 
	 * The card holder name is stored as a String, and external calling systems are responsible for
	 * tying a given credit card to a customer or user; or, in the case of applications using the
	 * biz.janux.people model, to a Person or Party entity.
	 */
	public String getCardholderName();
	public void setCardholderName(String cardHolder);

	/** The billing address of the Cardholder of this credit card */
	public PostalAddress getBillingAddress();
	public void setBillingAddress(PostalAddress billingAddress);

	/** Provides a way to disable a credit card without deleting it from the system */
	public boolean isEnabled();
	public void setEnabled(boolean b);
	
	/** 
	 * In certain cases it may be desireable to return a 15 or 16 length numeric value that mimics
	 * a credit card so that it may be stored in an external system that used to store or handle
	 * credit cards in the clear; this field can be used to generate such a number; Ideally, this
	 * number should be unique across credit cards, or at least unique in the context of a
	 * BusinessUnit.
	 */
	public String getToken();
	public void setToken(String token);
	
	public String getBusinessUnitCode();
	public void setBusinessUnitCode(String code);
	
	/**
	 * Indicates if the cc was swiped
	 */
	public boolean isSwiped();
	public void setSwiped(boolean swiped);

} // end interface CreditCard
