package org.janux.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.Ostermiller.util.RandPass;

/**
 ***************************************************************************************************
 * Implementation of RandomStringGenerator used to generate random codes for identifying orders,
 * reservations, etc...; wraps 
 * <a href="http://ostermiller.org/utils/RandPass.html">Stephen Ostermiller's RandPass utility class</a>
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version $Revision: 1.4 $ - $Date: 2008-03-31 20:44:36 $
 ***************************************************************************************************
 */
public class RandomCodeGenerator implements RandomReadbleStringGenerator
{
	transient Log log = LogFactory.getLog(this.getClass());

	/** @see RandPass#NUMBERS_AND_LETTERS_ALPHABET */
	public final static char[] ALPHABET_ALPHANUMERIC = RandPass.NUMBERS_AND_LETTERS_ALPHABET;

	/** @see RandPass#LOWERCASE_LETTERS_AND_NUMBERS_ALPHABET */
	public final static char[] ALPHABET_ALPHANUMERIC_LOWERCASE = RandPass.LOWERCASE_LETTERS_AND_NUMBERS_ALPHABET;

	/** @see RandPass#LETTERS_ALPHABET */
	public final static char[] ALPHABET_LETTERS = RandPass.LETTERS_ALPHABET;

	/** @see RandPass#LOWERCASE_LETTERS_ALPHABET */
	public final static char[] ALPHABET_LETTERS_LOWERCASE = RandPass.LOWERCASE_LETTERS_ALPHABET;

	/** @see RandPass#UPPERCASE_LETTERS_ALPHABET */
	public final static char[] ALPHABET_LETTERS_UPPERCASE = RandPass.UPPERCASE_LETTERS_ALPHABET;

	/** @see RandPass#NONCONFUSING_ALPHABET */
	public final static char[] ALPHABET_NON_CONFUSING = RandPass.NONCONFUSING_ALPHABET;

	public final static char[] ALPHABET_NON_CONFUSING_UPPER_CASE = {
		'A','B','C','D','E','F','G','H',
		'J','K','M','N','P','Q','R','S',
		'T','W','X','Y','Z','2','3','4',
		'5','6','7','8','9',
	};

	/** @see RandPass#PRINTABLE_ALPHABET */
	public final static char[] ALPHABET_PRINTABLE = RandPass.PRINTABLE_ALPHABET;

	/** @see RandPass#SYMBOLS_ALPHABET */
	public final static char[] ALPHABET_SYMBOLS = RandPass.SYMBOLS_ALPHABET;

	private char[]   alphabet;
	private int      defaultLength;
	private RandPass randPass;

	public RandomCodeGenerator(char[] alphabet, int defaultLength) 
	{
		this.alphabet      = alphabet;
		this.defaultLength = defaultLength;
		this.randPass      = new RandPass(alphabet);
	}

	public String getString() {
		return this.getString(defaultLength);
	}


	public String getString(int length) 
	{
		String code = randPass.getPass(length);

		if (log.isDebugEnabled())
			log.debug("Generated new code: '" + code + "'");

		return code;
	}


	public char[] getAlphabet() {
		return this.alphabet;
	}


	public void setAlphabet(char[] alphabet) {
		this.alphabet = alphabet;
		randPass.setAlphabet(alphabet);
	}

}


