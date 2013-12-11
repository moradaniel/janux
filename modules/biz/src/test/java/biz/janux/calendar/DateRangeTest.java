package biz.janux.calendar;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.NoSuchElementException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 ***************************************************************************************************
 *
 *
 * @author  <a href="mailto:philippe.paravicini@eCommerceStudio.com">Philippe Paravicini</a>
 * @version $Revision: 1.6 $ - $Date: 2008-01-11 09:02:00 $
 *
 * @see
 ***************************************************************************************************
 */
public class DateRangeTest extends TestCase
{
	/** define the tests to be run in this class */
	public static Test suite() throws Exception
	{
		final TestSuite suite = new TestSuite();

		// run all tests
		suite.addTestSuite(DateRangeTest.class);

		// or a subset thereoff
	//	suite.addTest(new DateRangeTest("testNumDaysInRange"));
	//	suite.addTest(new DateRangeTest("testIterator"));

		return suite;
	}
	
	public DateRangeTest(String aTestName)
	{
		super(aTestName);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
        TestRunner.run(suite());
	}

	
	public void testNumDaysInRange()
	{
		DateRange range;
		
		Date dtStart = new Date();
		dtStart = DateUtils.setTimeComponent(dtStart, 0, 0, 0);
		
		final Calendar cal = new GregorianCalendar();
		for (int iNumDays= -1000; iNumDays <= 1000 ; iNumDays++)
		{
			cal.setTime(dtStart);
			cal.add(Calendar.DATE, iNumDays);
			final Date dtStop = cal.getTime();
			
			if (dtStart.before(dtStop))
			{
			    range = new DateRange(dtStart, dtStop);
			}
			else
			{
				range = new DateRange(dtStop, dtStart);
			}
			
			
			assertEquals(iNumDays + " day(s) in range", Math.abs(iNumDays), range.numDaysInRange());
		}

		range = new DateRange(dtStart, (long)(DateRange.MILLIS_PER_DAY * 0.25));
		assertEquals("0.25 day in range", 0, range.numDaysInRange());

		range = new DateRange(dtStart, (long)(DateRange.MILLIS_PER_DAY * 0.5));
		assertEquals("0.5 day in range", 0, range.numDaysInRange());

		range = new DateRange(dtStart, (long)(DateRange.MILLIS_PER_DAY * 0.75));
		assertEquals("0.75 day in range", 0, range.numDaysInRange());


		range = new DateRange(dtStart, (long)(DateRange.MILLIS_PER_DAY * 1.25));
		assertEquals("1.25 day in range", 1, range.numDaysInRange());

		range = new DateRange(dtStart, (long)(DateRange.MILLIS_PER_DAY * 1.5));
		assertEquals("1.5 day in range", 1, range.numDaysInRange());

		range = new DateRange(dtStart, (long)(DateRange.MILLIS_PER_DAY * 1.75));
		assertEquals("1.75 day in range", 1, range.numDaysInRange());

		// daylight savings check
		range = new DateRange(DateUtils.getDate(2007, 3, 9), DateUtils.getDate(2007, 3, 13));
		assertEquals("4 day in range", 4, range.numDaysInRange());

		// null check
		range = new DateRange(new Date(), null);
		assertEquals("null 'To'", -1, range.numDaysInRange());

		range = new DateRange(null, new Date());
		assertEquals("null 'From'", -1, range.numDaysInRange());
	}

	
	public void testIterator()
	{
		final DateRange drRange = new DateRange(DateUtils.getDate(2000, 2, 1), DateUtils.getDate(2000, 2, 5));
		assertNotNull(drRange);
		assertEquals(4, drRange.numDaysInRange());
		
		// get a date iterator that doesn't include the end date
		Iterator<Date> itDates = drRange.iterator(Calendar.DATE, false);
		assertNotNull(itDates);
		
		assertTrue(itDates.hasNext());
		assertEquals(DateUtils.getDate(2000, 2, 1), itDates.next());
		assertTrue(itDates.hasNext());
		assertEquals(DateUtils.getDate(2000, 2, 2), itDates.next());
		assertTrue(itDates.hasNext());
		assertEquals(DateUtils.getDate(2000, 2, 3), itDates.next());
		assertTrue(itDates.hasNext());
		assertEquals(DateUtils.getDate(2000, 2, 4), itDates.next());
		
		assertFalse(itDates.hasNext());
		try
		{
			assertEquals(DateUtils.getDate(2000, 2, 1), itDates.next());
			fail("Date Iterator should have thrown an exception");
		}
		catch (NoSuchElementException e)
		{
			// this is what we want
		}
		
		// get a date iterator that includes the end date
		itDates = drRange.iterator(Calendar.DATE, true);
		assertNotNull(itDates);
		
		assertTrue(itDates.hasNext());
		assertEquals(DateUtils.getDate(2000, 2, 1), itDates.next());
		assertTrue(itDates.hasNext());
		assertEquals(DateUtils.getDate(2000, 2, 2), itDates.next());
		assertTrue(itDates.hasNext());
		assertEquals(DateUtils.getDate(2000, 2, 3), itDates.next());
		assertTrue(itDates.hasNext());
		assertEquals(DateUtils.getDate(2000, 2, 4), itDates.next());
		assertTrue(itDates.hasNext());
		assertEquals(DateUtils.getDate(2000, 2, 5), itDates.next());
		
		assertFalse(itDates.hasNext());
		try
		{
			assertEquals(DateUtils.getDate(2000, 2, 1), itDates.next());
			fail("Date Iterator should have thrown an exception");
		}
		catch (NoSuchElementException e)
		{
			// this is what we want
		}
		
		
		// test that these static variables are set
		assertTrue(new Date().after(DateRange.EARLIEST_DATE));
		assertTrue(new Date().before(DateRange.LATEST_DATE));
		
		
		
		// test iteration by hour
		final DateRange drTimeRange = new DateRange(DateUtils.getDate(2000, 2, 1, 12, 30), DateUtils.getDate(2000, 2, 1, 16, 30));
		final Iterator<Date> itTimes = drTimeRange.iterator(Calendar.HOUR, false);
		
		assertNotNull(itTimes);
		
		assertTrue(itTimes.hasNext());
		assertEquals(DateUtils.getDate(2000, 2, 1, 12, 30), itTimes.next());
		assertTrue(itTimes.hasNext());
		assertEquals(DateUtils.getDate(2000, 2, 1, 13, 30), itTimes.next());
		assertTrue(itTimes.hasNext());
		assertEquals(DateUtils.getDate(2000, 2, 1, 14, 30), itTimes.next());
		assertTrue(itTimes.hasNext());
		assertEquals(DateUtils.getDate(2000, 2, 1, 15, 30), itTimes.next());
		
		assertFalse(itTimes.hasNext());
		try
		{
			assertEquals(DateUtils.getDate(2000, 2, 1, 16, 30), itDates.next());
			fail("Time Iterator should have thrown an exception");
		}
		catch (NoSuchElementException e)
		{
			// this is what we want
		}
		
	}
	
	
	public void testConstructor() 
	{
		Date date = new Date();

		final Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);

		Date laterDate = cal.getTime();

		DateRange dateRange1 = new DateRange(date, laterDate);
		DateRange dateRange2 = new DateRange(null, date);
		DateRange dateRange3 = new DateRange(date, null);
		DateRange dateRange4 = new DateRange(date, date);

		try
		{ 
			DateRange dateRange5 = new DateRange(laterDate, date);
			fail("The first date in a DateRange should occur prior to the second one");
		}
		catch (IllegalArgumentException e)
		{
			// we expect an exception
		}


	}

} // end class DateRangeTest
