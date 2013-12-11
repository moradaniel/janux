package org.janux.util;

/**
 ***************************************************************************************************
 * Interface for a class that generates random string codes of varying length; such generators can
 * be used to generate passwords, alpha-numeric identifiers for orders, or mock data; this interface
 * heavily inspired by 
 * <a href="http://ostermiller.org/utils/RandPass.html">Stephen Ostermiller's RandPass utility class</a>
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version $Revision: 1.1 $ - $Date: 2005-12-19 21:12:53 $
 ***************************************************************************************************
 */
public interface RandomReadbleStringGenerator extends RandomStringGenerator
{
	/** returns a random string with the specified length */
	String getString(int length);

	/** get the alphabet (universe of characters) used by this random string generator */
	char[] getAlphabet();

	/** get the alphabet (universe of characters) used by this random password generator */
	void setAlphabet(char[] alphabet);

} // end class
