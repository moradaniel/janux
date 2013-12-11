package biz.janux.payment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class CreditCardValidatorDefaultImpl implements CreditCardValidator
{
	/**
	 * Loads the card type patterns as a configurable property
	 */
	private Map<String, String> cardTypePatterns = DEFAULT_CARD_TYPE_PATTERNS;

	
	/**
	 * This map contains a default list of various card validation patterns; this default list can be
	 * changed via @{link #setCardTypePatterns(Map)}
	 */
	private final static Map<String, String> DEFAULT_CARD_TYPE_PATTERNS = new HashMap<String, String>();
	 
	static
	{
		DEFAULT_CARD_TYPE_PATTERNS.put("VI", "4(\\d{12}|\\d{15})");    // VISA - starts with 4 followed by 12 or 15 digits
		DEFAULT_CARD_TYPE_PATTERNS.put("MC", "5[12345]\\d{14}");       // Mastercard - starts with 51 - 55 followed by 14 digits
		DEFAULT_CARD_TYPE_PATTERNS.put("CA", "5[12345]\\d{14}");       // Mastercard - starts with 51 - 55 followed by 14 digits
		DEFAULT_CARD_TYPE_PATTERNS.put("EU", "5[12345]\\d{14}");       // Mastercard - starts with 51 - 55 followed by 14 digits
		DEFAULT_CARD_TYPE_PATTERNS.put("AX", "3[47]\\d{13}");          // Amex - starts with 34 or 37 followed by 13 digits
		DEFAULT_CARD_TYPE_PATTERNS.put("DS", "6011\\d{12}");           // Discover - starts with 6011 followed by 12 digits
		DEFAULT_CARD_TYPE_PATTERNS.put("DI", "3[068]\\d{12}");         // Diners Club - starts with 30 followed by 12 digits
		DEFAULT_CARD_TYPE_PATTERNS.put("CB", "3[068]\\d{12}");         // Carte Blanche - same as Diners Club
		DEFAULT_CARD_TYPE_PATTERNS.put("JC", "(3088|3096|3112|3158|3337|3528)\\d{12}"); // JC - has one of the prefixes followed by 12 digits
	}
	
	
	/**
	 * Validate the given credit card and return a simple boolean result
	 */
	public boolean isValid(final CreditCard aCreditCard)
	{
		final Map<String, String> errors = this.validate(aCreditCard);
		return errors.size() == 0;
	}

	
	/**
	 * Validate the given credit card and return a map containing any errors
	 */
	public Map<String, String> validate(final CreditCard aCreditCard)
	{
		final Map<String, String> errors = new HashMap<String, String>();
		
		// check expiration date
		if ( isValidExpiration(aCreditCard.getExpirationDate()) == false )
		{
			errors.put(CC_ERR_EXPIRATION, "Card expired");
		}

		// check card digit checksums
		if ( isValidCheckDigit(aCreditCard.getNumber()) == false )
		{
			errors.put(CC_ERR_CHECKSUM, "Card number has invalid checksum");
		}
		
		
		// check card type
		if ( isValidCardType(aCreditCard.getTypeCode(), aCreditCard.getNumber()) == false )
		{
			errors.put(CC_ERR_CARDTYPE, "Card number is invalid for the type code '" + aCreditCard.getTypeCode() + "'");
		}
		
		return errors;
	}

	
	/**
	 * Method for validating a given card number against a card type code.  
	 * Card number must match the expected pattern for the given code
	 * @param aCardTypeCode
	 * @param aCardNumber
	 * @return
	 */
	public boolean isValidCardType(final String aCardTypeCode, final String aCardNumber)
	{
		if ( aCardTypeCode == null )
		{
			return true;  // if there's no given card type - just return true
		}
		if ( aCardNumber == null )
		{
			return true;  // if there is a card type, but no number, return true
		}
		
		final String sPattern = this.getCardTypePatterns().get(aCardTypeCode.trim().toUpperCase());
		
		// we don't have a pattern for the given cardtype code
		if (sPattern == null)
		{
			return false;
		}
			
		final String sCardNumber = getDigitsOnly(aCardNumber);
		
		// return true if the given card number matches the expected pattern
		return sCardNumber.matches(sPattern);
	}
	
	
	/**
	 * Method for validating the card number checksum using the 
	 * <a href="http://en.wikipedia.org/wiki/Luhn_algorithm">Luhn algorithm</a>
	 */
	public static boolean isValidCheckDigit(final String aCardNumber) 
	{
		if (aCardNumber == null)
		{
			return true;   // return true if there's nothing to check
		}
		
		final String sCardNumber = getDigitsOnly(aCardNumber);

		// loop backwards through the digits of the card number, added each to the sum
		int sum = 0;
		boolean timesTwo = false;
		for (int i = sCardNumber.length () - 1; i >= 0; i--) 
		{
			final int digit = Integer.parseInt (sCardNumber.substring (i, i + 1));
			
			// calculate the value to add to the sum
			int addend = digit;
		    if (timesTwo) 
		    {
		    	addend = digit * 2;
		        if (addend > 9) 
		        {
		        	addend -= 9;
		        }
		    }
		      
		    sum += addend;
		    timesTwo = !timesTwo;
		}

		    
		// final sum should be divisable by 10
		final int modulus = sum % 10;
		return modulus == 0;
	}	
	
	
	/**
	 * Strip any non-digit characters from the input string
	 * @param aInputString
	 * @return
	 */
	private static String getDigitsOnly (final String aInputString) 
	{
		if (aInputString == null)
		{
			return null;
		}
		
		final StringBuilder sDigitsOnly = new StringBuilder();
		
		for (int i = 0; i < aInputString.length (); i++) 
		{
			final char c = aInputString.charAt(i);
		    if (Character.isDigit (c)) 
		    {
		    	sDigitsOnly.append (c);
		    }
		}
		
		return sDigitsOnly.toString ();
	}	
	
	
	/**
	 * Checks that the current date is before the given expiration date
	 * @param aCardExpiration
	 * @return
	 */
	public static boolean isValidExpiration(final Date aCardExpiration)
	{
		if (aCardExpiration == null)
		{
			return false;
		}
		
		final Date dtCurrent = new Date();
		return isValidExpiration(aCardExpiration, dtCurrent) ;
	}
	
	
	/**
	 * Checks that the given comparison date is before the expiration date.
	 * Only the year and month of the expiration date are considered.  If
	 * the card expires in Feb of 2016, then all dates in February 2016 are considered
	 * valid.  March 1st would be the first invalid date
	 * @param aCardExpiration
	 * @param aCompareDate
	 * @return
	 */
	public static boolean isValidExpiration(final Date aCardExpiration, final Date aCompareDate)
	{
		// get the timestamp of the card expiration - if the card expires on 2/2016 
		// make sure the date variable is set for 2/29/2016 23:59:59
		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(aCardExpiration);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		
		final Date dtExpiration = cal.getTime();
		
		return ( dtExpiration.after(aCompareDate) );
	}


	public Map<String, String> getCardTypePatterns()
	{
		return cardTypePatterns;
	}


	public void setCardTypePatterns(Map<String, String> cardTypePatterns)
	{
		this.cardTypePatterns = cardTypePatterns;
	}
	
}
