package biz.janux.commerce;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.extensions.TestSetup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 ***************************************************************************************************
 *
 *
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since
 ***************************************************************************************************
 */
public class CreditCardMaskTest extends TestCase
{

	public static Test suite()
	{
		TestSuite suite = new TestSuite();
		return new TestSuite(CreditCardMaskTest.class);
	}

	public void testMaskNumber()
	{
		CreditCardMask mask = new CreditCardMask();
		System.out.println("maskNumber(1234567890654321) is: " + mask.maskNumber("1234567890654321"));

		assertEquals("1***********4321", mask.maskNumber("1234567890654321"));
		assertEquals("1234", mask.maskNumber("1234") );

		mask.setNumFrontDigits(2);
		mask.setNumBackDigits(3);
		mask.setMaskingChar('x');
		assertEquals("12xxxxxxxxxx321", mask.maskNumber("123456789054321"));

		try
		{ 
			mask.setNumFrontDigits(-1);
			fail("setNumFrontDigits(-1) is illegal");
		}
		catch (Exception e)
		{
			// expected
		}

		try
		{ 
			mask.setNumBackDigits(-1);
			fail("setNumBackDigits(-1) is illegal");
		}
		catch (Exception e)
		{
			// expected
		}
	}

} // end class CreditCardMaskTest

