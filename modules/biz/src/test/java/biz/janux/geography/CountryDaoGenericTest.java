package biz.janux.geography;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.Map;

import biz.janux.test.TransactionalBizTestAbstractGeneric;

/**
 * @author   <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version  $Revision: 1.3 $ - $Date: 2006-07-17 05:21:55 $
 */
public class CountryDaoGenericTest extends TransactionalBizTestAbstractGeneric
{
	protected CountryDaoGeneric<CountryImpl> countryDaoGeneric;

	private static final int NUM_COUNTRIES  = 240;
	private static final int ID_US          = 227;
	//	private static final int ID_CALIFORNIA  = 246;
	//	private static final int ID_ALBUQUERQUE = 3;

	// country codes
	private static final String CODE_US     = "US";
	private static final String CODE_CAN    = "CA";
	private static final String CODE_ZW     = "ZW";

	// state codes
	//	private static final String CODE_CA     = "CA";
	//	private static final String CODE_NM     = "NM";
	//	private static final String CODE_AZ     = "AZ";
	//	private static final String CODE_TX     = "TX";

	// city codes
	//	private static final String CODE_ABQ    = "ABQ";
	//	private static final String CODE_PET    = "PET";
	//	private static final String CODE_UNK    = "UNKNOWN";

	public CountryDaoGenericTest()  { 
		super();
	}

	/*
	public CountryDaoGenericTest(String name)  {
		super(name);
	}*/

	public void testLoad() 
	{
		Country c = countryDaoGeneric.load(new Integer(ID_US));
		assertNotNull("US country", c);
		assertEquals("code", CODE_US, c.getCode());
		assertEquals("name", "United States", c.getName());
		assertEquals("sortOrder", new Integer(0), c.getSortOrder());
	}

	public void testFindByCode() 
	{
		Country c = countryDaoGeneric.findByCode(CODE_US);
		assertNotNull("US country", c);
		assertEquals("code", CODE_US, c.getCode());
		assertEquals("name", "United States", c.getName());
		assertEquals("sortOrder", new Integer(0), c.getSortOrder());
	}


	public void testFindByName() 
	{
		Country c = countryDaoGeneric.findByName("United States");
		assertNotNull("US country", c);
		assertEquals("code", CODE_US, c.getCode());
		assertEquals("name", "United States", c.getName());
		assertEquals("sortOrder", new Integer(0), c.getSortOrder());
	}


	public void testLoadAll()
	{
		Map countries = countryDaoGeneric.loadAll();

		assertEquals("num Countries", NUM_COUNTRIES, countries.keySet().size());
		assertTrue("contains US", countries.containsKey(CODE_US));
		assertTrue("contains Canada", countries.containsKey(CODE_CAN));
		assertTrue("contains Zimbabwe", countries.containsKey(CODE_ZW));

		int i=1;
		for (Iterator it = countries.keySet().iterator(); it.hasNext(); i++)
		{
			Country c = (Country)countries.get((String)it.next());

			if (i == 1)
				assertEquals("first key US", CODE_US, c.getCode());
			else if (i == 2)
				assertEquals("second key CAN", CODE_CAN, c.getCode());
			else if (i == NUM_COUNTRIES)
				assertEquals("last key Zimbabwe ", CODE_ZW, c.getCode());
		} // end for

	}
    public static void main(String[] args)
    {
        // create the suite of tests
        /*final TestSuite tSuite = new TestSuite();
        tSuite.addTest(new CountryDaoGenericTest("testLoad"));
        TestRunner.run(tSuite);*/
    }

}
