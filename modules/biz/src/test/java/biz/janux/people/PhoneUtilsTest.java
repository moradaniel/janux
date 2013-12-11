package biz.janux.people;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.extensions.TestSetup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 ***************************************************************************************************
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.2.09 - November 2008
 ***************************************************************************************************
 */
public class PhoneUtilsTest extends TestCase
{
	Log log = LogFactory.getLog(this.getClass());

	public void testMakeNumeric()
	{
		assertEquals("12345678901", PhoneUtils.makeNumeric("+1 (234) 567-8901"));
		assertEquals("12345678901", PhoneUtils.makeNumeric("1-234-567-8901"));
		assertEquals("12345678901", PhoneUtils.makeNumeric("  !@#$%^&*()1   234 5678901  "));
		assertEquals("12345678901", PhoneUtils.makeNumeric("12345678901"));
	}

} // end class PhoneUtilsTest
