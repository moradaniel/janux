package biz.janux.commerce;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * An initial implementation of a CreditCard object; this class is likely to
 * undergo revisions when we build a standalone Credit Card Vault
 *
 * @author David Fairchild
 * @author Alberto Buffagni
 * @author Philippe Paravicini
 */
public class CreditCardFop extends AbstractFormOfPayment
{
	private static final long serialVersionUID = -2556962160569217392L;
	/** the type of card (VISA, Mastercard, american express) */
	private PaymentMethod cardType;

	/** the card number */
	private String cardNumber;

	/** the name on the card */
	private String cardOwnerName;

	/** the card expiration date */
	private Date expiration;

	/** security code */
	private String securityCode;

	/** indicate if the card number is masked */
	private boolean masked;

	/** indicate if the card number is encrypted */
	private boolean encrypted;
	
	/**
	* used to set the number to show to user. This number can be encrypted, decrypted or masked.
	* It is not persistent.
	*/
	private String cardNumberToShow;
	
	
	/**
	 * Constructor
	 */
	public CreditCardFop()
	{
	}

	/**
	 * @return Returns the cardNumber.
	 */
	public String getCardNumber()
	{
		return cardNumber;
	}
	/**
	 * @return Returns the cardOwnerName.
	 */
	public String getCardOwnerName()
	{
		return cardOwnerName;
	}
	/**
	 * @return Returns the cardType.
	 */
	public PaymentMethod getCardType()
	{
		return cardType;
	}
	/**
	 * @return Returns the expiration.
	 */
	public Date getExpiration()
	{
		return expiration;
	}
	/**
	 * @param cardNumber The cardNumber to set.
	 */
	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}
	/**
	 * @param cardOwnerName The cardOwnerName to set.
	 */
	public void setCardOwnerName(String cardOwnerName)
	{
		this.cardOwnerName = cardOwnerName;
	}
	/**
	 * @param cardType The cardType to set.
	 */
	public void setCardType(PaymentMethod cardType)
	{
		this.cardType = cardType;
	}
	/**
	 * @param expiration The expiration to set.
	 */
	public void setExpiration(Date expiration)
	{
		this.expiration = expiration;
	}
	
	/**
	 * @param aMonth the expiration month specified from 1 to 12
	 * @param aYear the full 4 digit year
	 */
	public void setExpiration(final int aYear, final int aMonth)
	{
		final GregorianCalendar cal = new GregorianCalendar();

		cal.set(Calendar.YEAR, aYear);
		cal.set(Calendar.MONTH, aMonth - 1); // calendar object wants zero-based months, January = 0
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 0);
		
		this.expiration = cal.getTime();
	}
	
	/**
	 * @return the expiration month (from 1 to 12)
	 */
	public int getExpirationMonth()
	{
		final Calendar cal = new GregorianCalendar();
		cal.setTime(this.expiration);
		final int iMonth = cal.get(Calendar.MONTH) + 1;
		return (iMonth);
	}
	
	/**
	 * @return the expiration year
	 */
	public int getExpirationYear()
	{
		final Calendar cal = new GregorianCalendar();
		cal.setTime(this.expiration);
		final int iYear = cal.get(Calendar.YEAR);
		return (iYear);
	}

	
	public boolean isExpired()
	{
		final Date dtCurrent = new Date();
		if (dtCurrent.after(this.expiration))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	/**
	 * Generally known as the CVV or CVV2 code, this is a three-letter code at the
	 * back of the card that identifies the card and is used for card-not-present
	 * transactions as a security precaution; this code may be transmitted to
	 * a processor for purposes of Authorization, but should never be stored;
	 * storing this code is considered an egregious violation of PCI (Payment Card
	 * Industry) Compliance standards.  As such, we provide a field in this object
	 * to be able to capture and transmit this code, but this field is not
	 * persisted to the database, and should not be logged or stored permanently
	 * in any other fashion.
	 */
	public String getSecurityCode()
	{
		return securityCode;
	}

	public void setSecurityCode(String securityCode)
	{
		this.securityCode = securityCode;
	}
	
	public Object clone() 
	{
		Object result = super.clone();
		return result;
	}

	public void setMasked(boolean masked) {
		this.masked = masked;
	}

	public boolean isMasked() {
		return masked;
	}

	public void setEncrypted(boolean encrypted) {
		this.encrypted = encrypted;
	}

	public boolean isEncrypted() {
		return encrypted;
	}

	public void setCardNumberToShow(String cardNumberToShow) {
		this.cardNumberToShow = cardNumberToShow;
	}

	public String getCardNumberToShow() {
		return cardNumberToShow;
	}
	
}
