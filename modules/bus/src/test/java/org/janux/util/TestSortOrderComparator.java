package org.janux.util;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 ***************************************************************************************************
 * Tests the SortOrderComparator
 *
 * @author  <a href="mailto:philippe.paravicini@eCommerceStudio.com">Philippe Paravicini</a>
 * @version $Revision: 1.2 $ - $Date: 2006-08-29 21:11:31 $
 *
 * @see SortOrderComparator
 * @see Sorteable
 ***************************************************************************************************
 */
public class TestSortOrderComparator extends TestCase
{
	Log log = LogFactory.getLog(this.getClass());

	/** executed prior to each test */
	protected void setUp() { }

	/** executed after each test */
	protected void tearDown() { }

	public void testSortOrderComparator()
	{
		SorteableTester s1 = new SorteableTester(new Integer(7));
		SorteableTester s2 = new SorteableTester(new Integer(-5));
		SorteableTester s3 = new SorteableTester(null);
		SorteableTester s4 = new SorteableTester(null);

		SortOrderComparator c = new SortOrderComparator();

		log.debug("s1:s3 " + c.compare(s1,s3));
		log.debug("s1: " + s1.getSortOrder());
		log.debug("s3: " + s3.getSortOrder());
		log.debug("min: " + Integer.MIN_VALUE);
		log.debug("s1-min " + (s1.getSortOrder() - (int)(Integer.MIN_VALUE)));

		assertTrue("s1:s1", c.compare(s1,s1) == 0);
		assertTrue("s2:s2", c.compare(s2,s2) == 0);
		assertTrue("s3:s3", c.compare(s3,s3) == 0);

		assertTrue("s1:s2", c.compare(s1,s2) > 0);
		assertTrue("s2:s1", c.compare(s2,s1) < 0);

		assertTrue("s1:s3", c.compare(s1,s3) > 0);
		assertTrue("s3:s1", c.compare(s3,s1) < 0);

		assertTrue("s2:s3", c.compare(s2,s3) > 0);
		assertTrue("s3:s2", c.compare(s3,s2) < 0);

		assertTrue("s3:s4", c.compare(s3,s4) == 0);
		assertTrue("s4:s3", c.compare(s4,s3) == 0);
	}

} // end class SortOrderComparator

