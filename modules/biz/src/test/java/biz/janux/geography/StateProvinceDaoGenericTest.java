package biz.janux.geography;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.Map;

import biz.janux.test.TransactionalBizTestAbstractGeneric;

/**
 * @author   <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version  $Revision: 1.2 $ - $Date: 2006-07-17 05:21:55 $
 * @since 2005-02-21
 */
public class StateProvinceDaoGenericTest extends TransactionalBizTestAbstractGeneric
{
	protected CountryDaoGeneric<Country> countryDaoGeneric;
	protected StateProvinceDaoGeneric<StateProvince> stateProvinceDaoGeneric;

	private static final int ID_CALIFORNIA  = 246;

	// country codes
	private static final String CODE_US     = "US";
	private static final int NUM_US_STATES  = 62;

	// state codes
	private static final String CODE_AL     = "AL";
	private static final String CODE_AK     = "AK";
	private static final String CODE_AZ     = "AZ";
	private static final String CODE_AP     = "AP";
	private static final String CODE_CA     = "CA";
	private static final String CODE_NM     = "NM";
	private static final String CODE_TX     = "TX";

	public StateProvinceDaoGenericTest()  { 
		super();
	}

	/*
	public StateProvinceDaoGenericTest(String name) {
		super(name);
	}*/


	public void testLoad() 
	{
		StateProvince o = stateProvinceDaoGeneric.load(new Integer(ID_CALIFORNIA));
    assertNotNull("California state", o);
    assertEquals("code", CODE_CA, o.getCode());
    assertEquals("name", "California", o.getName());
    assertEquals("order", new Integer(6), o.getSortOrder());
	}

	public void testFindByCode() 
	{
		StateProvince o = stateProvinceDaoGeneric.findByCode(CODE_US, CODE_TX);
    assertNotNull(CODE_TX, o);
    assertEquals("TX code", CODE_TX, o.getCode());
    assertEquals("TX name", "Texas", o.getName());
    assertEquals("TX order", new Integer(51), o.getSortOrder());

		o = stateProvinceDaoGeneric.findByCode(CODE_US, CODE_CA);
    assertNotNull(CODE_CA, o);
    assertEquals("CA code", CODE_CA, o.getCode());
    assertEquals("CA name", "California", o.getName());
    assertEquals("CA order", new Integer(6), o.getSortOrder());
	}

	public void testFindByName() 
	{
		StateProvince o = stateProvinceDaoGeneric.findByName(CODE_US, "New Mexico");
    assertNotNull("New Mexico", o);
    assertEquals("NM code", CODE_NM, o.getCode());
    assertEquals("NM name", "New Mexico", o.getName());
    assertEquals("NM order", new Integer(36), o.getSortOrder());

		o = stateProvinceDaoGeneric.findByName(CODE_US, "Arizona");
    assertNotNull(CODE_AZ, o);
    assertEquals("AZ code", CODE_AZ, o.getCode());
    assertEquals("AZ name", "Arizona", o.getName());
    assertEquals("AZ order", new Integer(4), o.getSortOrder());
	}


	public void testFindByCountry()
	{
		Map states = stateProvinceDaoGeneric.findByCountry(CODE_US);

    assertEquals("num US states", NUM_US_STATES, states.keySet().size());
		assertTrue("contains CA", states.containsKey(CODE_CA));
		assertTrue("contains TX", states.containsKey(CODE_TX));
		assertTrue("contains Armed Forces Pacific", states.containsKey(CODE_AP));

		int i=1;
		for (Iterator it = states.keySet().iterator(); it.hasNext(); i++)
		{
			StateProvince s = (StateProvince)states.get((String)it.next());

			if (i == 1)
				assertEquals("first state AL ", CODE_AL, s.getCode());
			else if (i == 2)
				assertEquals("second state AK", CODE_AK, s.getCode());
			else if (i == NUM_US_STATES)
				assertEquals("last state Armed Force Pacific ", CODE_AP, s.getCode());
		} // end for

	}

	/*
	public void testLoadUnknownState() 
	{
		Map countries = countryDao.loadAll();

		for (Iterator i = countries.keySet().iterator(); i.hasNext();)
		{
			String countryCode = (String)i.next();
			StateProvince o = stateProvinceDao.loadUnknownState(countryCode);
    	assertNotNull("Unknown state for country " + countryCode, o);
    	assertEquals("Unknown state code for country " + countryCode, CODE_UNK, o.getCode());
    	assertEquals("country of Unknown state in " + countryCode, countries.get(countryCode), o.getCountry());
		} // end for
	}
	*/

}
