package biz.janux.commerce;

/**
 * Utility class used to mask all but the N first digits (front digits) and M
 * last digits (back digits) of a number with the specific masking character.
 *
 * So for example, if numFrontDigits = 1, numBackDigits = 4, and maskingChar = * '*', 
 * the credit card number '1234567890654321' will be masked as * '1***********4321'
 */
public class CreditCardMask 
{
	private int  first = 1;
	private int  last  = 4;
	private char mask  = '*';


	/** The number of digits that should appear unmasked at the beginning of the credit card number */
  public int getNumFrontDigits() { return this.first;}
  public void setNumFrontDigits(int n) 
	{ 
		if (n < 0) throw new IllegalArgumentException("'numFrontDigits' must be a non-positive integer: '" + n + "' was entered");
		this.first = n; 
	}

	/** The number of digits that should appear unmasked at the beginning of the credit card number */
  public int getNumBackDigits() { return this.last;}
  public void setNumBackDigits(int n) 
	{ 
		if (n < 0) throw new IllegalArgumentException("'numBackDigits' must be a non-positive integer: '" + n + "' was entered");
		this.last = n; 
	}

	/** The character used to mask all masked digits */
  public char getMaskingChar() { return this.mask;}
  public void setMaskingChar(char n) { this.mask = n; }

	public CreditCardMask() {}

	public CreditCardMask(int numFrontDigits, int numBackDigits, char maskingChar)
	{
		this.setNumFrontDigits(numFrontDigits);
		this.setNumBackDigits(numBackDigits);
		this.setMaskingChar(maskingChar);
	}
	
	public String maskNumber(String num)
	{
		if (num == null)
			return num;
		
		// sanity check
		if (num.length() < (this.last + 1)) {
			return num;
		}

		// hash out number
		StringBuffer buf = new StringBuffer();

		buf.append(num.substring(0, this.first));

		for (int i = first; i < num.length() - (this.last); i++) {
			buf.append(this.mask);
		}

		buf.append(num.substring(num.length() - (this.last)));

		return buf.toString();
	}
}
