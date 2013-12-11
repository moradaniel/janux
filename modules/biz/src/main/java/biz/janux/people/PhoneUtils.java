package biz.janux.people;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 ***************************************************************************************************
 * Convenience class to store formatting or validation methods that are commonly used when handling
 * phone numbers
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.2.09
 ***************************************************************************************************
 */
public class PhoneUtils
{
	Log log = LogFactory.getLog(this.getClass());

	/** returns the pre-defined non-digit character class: "\D" which is equivalent to [^0-9] */
	public final static String REGEXP_MATCH_NON_NUMERIC = "\\D";

	/** 
	 * accepts a phone string with alphanumeric characters, and strips out all spaces and non-numeric
	 * characters
	 */
	public static String makeNumeric(String aPhoneString)
	{
		if (aPhoneString != null) 
		{
			return aPhoneString.replaceAll(REGEXP_MATCH_NON_NUMERIC,"");
		}
		else return null;
	}

} // end class
