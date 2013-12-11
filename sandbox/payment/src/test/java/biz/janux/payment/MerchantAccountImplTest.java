package biz.janux.payment;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.extensions.TestSetup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Calendar;
import java.util.TimeZone;

/**
 ***************************************************************************************************
 *
 *
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since
 ***************************************************************************************************
 */
public class MerchantAccountImplTest extends TestCase
{
	Log log = LogFactory.getLog(this.getClass());
	//static Log log = LogFactory.getLog(FooSetup.class);
	
	public static final String   TZ_ID_PACIFIC = "America/Los_Angeles";
	public static final TimeZone TZ_PACIFIC    = TimeZone.getTimeZone(TZ_ID_PACIFIC);

	public static final String   TZ_ID_CUSTOM = "GMT-08:00";
	public static final TimeZone TZ_CUSTOM    = TimeZone.getTimeZone(TZ_ID_CUSTOM);

	private MerchantAccountImpl acct;

	/** define the tests to be run in this class */
	public static Test suite()
	{
		TestSuite suite = new TestSuite();

		// run all tests
		suite = new TestSuite(MerchantAccountImplTest.class);

		// or a subset thereoff
		// suite.addTest(new MerchantAccountImplTest("testFoo1"));

		return suite;

		// or
		// TestSetup wrapper= new SomeWrapper(suite);
		// return wrapper;
	}

	/** executed prior to each test */
	protected void setUp() { 
		this.acct = new MerchantAccountImpl();

		assertNull(this.acct.getMerchantTimeZone());
		assertNull(this.acct.getMerchantTimeZoneAsString());
	}


	/** executed after each test */
	protected void tearDown() { }

	/** tests setting/getting a standard named TimeZone */
	public void testSetPacificTimeZone()
	{
		// Set the timezone using a String ID
		this.acct.setMerchantTimeZoneAsString(TZ_ID_PACIFIC);
		assertEquals(TZ_PACIFIC, this.acct.getMerchantTimeZone());

		acct.setMerchantTimeZoneAsString(null);
		assertNull(this.acct.getMerchantTimeZone());
		assertNull(this.acct.getMerchantTimeZoneAsString());


		// Set the timezone using a TimeZone instance
		this.acct.setMerchantTimeZone(TZ_PACIFIC);
		assertEquals(TZ_ID_PACIFIC, this.acct.getMerchantTimeZoneAsString());

		this.acct.setMerchantTimeZone(null);
		assertNull(this.acct.getMerchantTimeZone());
		assertNull(this.acct.getMerchantTimeZoneAsString());

		/*
		Calendar now = Calendar.getInstance();
		now.setTimeZone(TZ_PACIFIC);
		log.debug("TZ_PACIFIC offset is: " + now.get(Calendar.ZONE_OFFSET) / (60 * 60 * 1000));
		*/
	}

	/** tests setting/getting a custom TimeZone using a NormalizedCustomId (see TimeZone javadocs) */
	public void testSetCustomTimeZone()
	{
		// Set the timezone using a String ID
		this.acct.setMerchantTimeZoneAsString(TZ_ID_CUSTOM);
		assertEquals(TZ_CUSTOM, this.acct.getMerchantTimeZone());

		acct.setMerchantTimeZoneAsString(null);
		assertNull(this.acct.getMerchantTimeZone());
		assertNull(this.acct.getMerchantTimeZoneAsString());


		// Set the timezone using a TimeZone instance
		this.acct.setMerchantTimeZone(TZ_CUSTOM);
		assertEquals(TZ_ID_CUSTOM, this.acct.getMerchantTimeZoneAsString());

		this.acct.setMerchantTimeZone(null);
		assertNull(this.acct.getMerchantTimeZone());
		assertNull(this.acct.getMerchantTimeZoneAsString());
	}

} // end class MerchantAccountImplTest

