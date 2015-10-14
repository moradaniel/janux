package biz.janux.geography;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import biz.janux.geography.aop.RuntimeExceptionAfterReturningAdvice;
import biz.janux.test.TransactionalBizTestAbstractGeneric;

/**
 * @author   <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version  $Revision: 1.7 $ - $Date: 2008-03-27 00:51:37 $
 */
public class GeographyServiceGenericTest extends TransactionalBizTestAbstractGeneric
{
	protected GeographyService geoServiceGeneric;

	// private static final int NUM_COUNTRIES  = 240;
	// private static final int ID_US          = 227;
	// private static final int ID_CALIFORNIA  = 246;
	// private static final int ID_ALBUQUERQUE = 3;

	// country codes
	private static final String CODE_US     = "US";
	// private static final String CODE_CAN    = "CA";
  // private static final String CODE_ZW     = "ZW";

	// country names
	private static final String NAME_USA    = "United States";
	private static final String NAME_FRANCE = "France";

	// state codes
	private static final String CODE_CA     = "CA";
	private static final String CODE_NM     = "NM";
	private static final String CODE_AZ     = "AZ";
	private static final String CODE_TX     = "TX";
	private static final String CODE_FR     = "FR";

	// state names
	private static final String NAME_CALIFORNIA = "California";
	private static final String NAME_NEW_MEXICO = "New Mexico";
	private static final String NAME_ALSACE     = "Alsace";
	private static final String NAME_MOSELLE    = "Moselle";

	// city codes
	private static final String CODE_ABQ    = "ABQ";
	private static final String CODE_PET    = "PET";
	private static final String CODE_UNK    = "UNKNOWN";

	// city names
	private static final String NAME_PETALUMA    = "Petaluma";
	private static final String NAME_ALBUQUERQUE = "Albuquerque";
	private static final String NAME_STRASBOURG  = "Strasbourg";

	public GeographyServiceGenericTest()  { 
		super();
	}

	/*
	public GeographyServiceGenericTest(String name)  {
		super(name);
	}*/

	public void testFindCountryByCode() 
	{
		Country c = geoServiceGeneric.findCountryByCode(CODE_US);
    assertNotNull(CODE_US, c);
    assertEquals("code", CODE_US, c.getCode());
	}

	public void testFindStateByCode() 
	{
		Country c = geoServiceGeneric.findCountryByCode(CODE_US);
    assertNotNull(CODE_US, c);

		StateProvince o = geoServiceGeneric.findStateByCode(c.getCode(), CODE_TX);
    assertNotNull(CODE_TX, o);
    assertEquals("TX name", "Texas", o.getName());
    assertEquals("TX code", CODE_TX, o.getCode());

		o = geoServiceGeneric.findStateByCode(CODE_US, CODE_CA);
    assertNotNull(CODE_CA, o);
    assertEquals("CA name", NAME_CALIFORNIA, o.getName());
    assertEquals("CA code", CODE_CA, o.getCode());
	}

	public void testLoadUnknownState() 
	{
		Map countries = geoServiceGeneric.loadAllCountries();

		for (Iterator i = countries.keySet().iterator(); i.hasNext();)
		{
			String countryCode = (String)i.next();
			StateProvince o = geoServiceGeneric.loadUnknownState(countryCode);
    	assertNotNull("Unknown state for country " + countryCode, o);
    	assertEquals("Unknown state code for country " + countryCode, CODE_UNK, o.getCode());
    	assertEquals("country of Unknown state in " + countryCode, countries.get(countryCode), o.getCountry());
		} // end for
	}

	public void testFindStateByName() 
	{
		Country c = geoServiceGeneric.findCountryByCode(CODE_US);
    assertNotNull(CODE_US, c);

		StateProvince o = geoServiceGeneric.findStateByName(c.getCode(), NAME_NEW_MEXICO);
    assertNotNull(NAME_NEW_MEXICO, o);
    assertEquals("NM name", NAME_NEW_MEXICO, o.getName());
    assertEquals("NM code", CODE_NM, o.getCode());

		o = geoServiceGeneric.findStateByName(CODE_US, "Arizona");
    assertNotNull(CODE_AZ, o);
    assertEquals("AZ name", "Arizona", o.getName());
    assertEquals("AZ code", CODE_AZ, o.getCode());
	}


	/*
	public void testLoadCity() 
	{
		City o = geoService.loadCity(new Integer(ID_ALBUQUERQUE));
    assertNotNull(NAME_ALBUQUERQUE, o);
    assertEquals("state",   CODE_NM, o.getState().getCode());
    assertEquals("country", CODE_US, o.getCountry().getCode());
	}
	*/

	public void testFindCityByName() 
	{
		StateProvince state = geoServiceGeneric.findStateByCode(CODE_US,CODE_NM);
    assertNotNull(CODE_NM, state);

		City o = geoServiceGeneric.findCityByName(state, NAME_ALBUQUERQUE);
    assertNotNull(NAME_ALBUQUERQUE, o);
    assertEquals("ABQ name", NAME_ALBUQUERQUE, o.getName());
    assertEquals("ABQ code", CODE_ABQ, o.getCode());
    assertEquals("ABQ state", state, o.getState());

		o = geoServiceGeneric.findCityByName(CODE_US,CODE_CA,NAME_PETALUMA);
    assertNotNull(NAME_PETALUMA, o);
    assertEquals("PET name", NAME_PETALUMA, o.getName());
    assertEquals("PET code", CODE_PET, o.getCode());
    assertEquals("PET state code", CODE_CA, o.getState().getCode());
    assertEquals("PET country code", CODE_US, o.getCountry().getCode());
	}

	/** 
	 * test that for an address for which we can match the StateProvince, 
	 * the method returns a new City entity
	 */
	public void testSetCityStateCountryForKnownState() 
	{
		PostalAddress address = new PostalAddressImpl();
		
		//// check that we can do a lookup by name
		address.setCityAsString(NAME_PETALUMA);
		address.setStateProvinceAsString(NAME_CALIFORNIA.toLowerCase());
		address.setCountryAsString(NAME_USA.toUpperCase());
		
		geoServiceGeneric.setCityStateCountry(address);
		
		// check the instances look ups
		this.assertKnownStateProvince(address, NAME_PETALUMA, NAME_CALIFORNIA, NAME_USA);

		assertEquals(CODE_US, address.getCountry().getCode());
		assertEquals(CODE_CA, address.getStateProvince().getCode());
		
		//// check that we can do a lookup by code
		address = new PostalAddressImpl();
		
		address.setCityAsString("GreenBrae");
		address.setStateProvinceAsString(CODE_CA);
		address.setCountryAsString(CODE_US);
		
		geoServiceGeneric.setCityStateCountry(address);
		
		// check the instances look ups
		this.assertKnownStateProvince(address, "GreenBrae", NAME_CALIFORNIA, NAME_USA);

		assertEquals(CODE_US, address.getCountry().getCode());
		assertEquals(CODE_CA, address.getStateProvince().getCode());
	}


	public void testSetCityStateCountryForUnknownState()
	{
		PostalAddress address = new PostalAddressImpl();

		String CITY    = NAME_STRASBOURG;
		String STATE   = NAME_ALSACE;
		String COUNTRY = NAME_FRANCE;
		
		//// check that we can do a lookup by name
		address.setCityAsString(CITY);
		address.setStateProvinceAsString(STATE);
		address.setCountryAsString(COUNTRY);
		
		geoServiceGeneric.setCityStateCountry(address);

		this.assertUnknownStateProvince(address, CITY, STATE, COUNTRY);
		
		//// check that we can do a lookup by Code
		address = new PostalAddressImpl();
		COUNTRY = CODE_FR;
		
		address.setCityAsString(CITY);
		address.setStateProvinceAsString(STATE);
		address.setCountryAsString(COUNTRY);
		
		geoServiceGeneric.setCityStateCountry(address);

		this.assertUnknownStateProvince(address, CITY, STATE, NAME_FRANCE);
	}


	public void testSetCityStateCountryForUnknownCountry()
	{
		PostalAddress address = new PostalAddressImpl();
		String CITY    = NAME_STRASBOURG;
		String STATE   = NAME_ALSACE;
		String COUNTRY = "Fronce";
		
		//// check that we can do a lookup by name
		address.setCityAsString(CITY);
		address.setStateProvinceAsString(STATE);
		address.setCountryAsString(COUNTRY);
		
		geoServiceGeneric.setCityStateCountry(address);

		this.assertUnknownCountry(address, CITY, STATE, COUNTRY);
		
	}


	public void testChangeCityStateCountryForKnownState()
	{
		PostalAddress address = new PostalAddressImpl();
		String CITY    = NAME_PETALUMA;
		String STATE   = NAME_CALIFORNIA;
		String COUNTRY = NAME_USA;
		
		//// check that we can do a lookup by name
		address.setCityAsString(CITY);
		address.setStateProvinceAsString(STATE);
		address.setCountryAsString(COUNTRY);
		
		geoServiceGeneric.setCityStateCountry(address);
		
		// check the instances look ups
		this.assertKnownStateProvince(address, CITY, STATE, COUNTRY);

		//// change the City to Albuquerque
		CITY    = NAME_ALBUQUERQUE;
		STATE   = NAME_NEW_MEXICO;
		COUNTRY = NAME_USA;
		address.setCityAsString(CITY);
		address.setStateProvinceAsString(STATE);
		address.setCountryAsString(COUNTRY);

		geoServiceGeneric.setCityStateCountry(address);

		this.assertKnownStateProvince(address, CITY, STATE, COUNTRY);

		//// change the City to Petaluma
		// Creates a City of Petaluma in New Mexico
		CITY = NAME_PETALUMA;
		address.setCityAsString(CITY);

		geoServiceGeneric.setCityStateCountry(address);

		this.assertKnownStateProvince(address, CITY, STATE, COUNTRY);

		//// change to Unknown Country
		COUNTRY = "UOS";
		address.setCountryAsString(COUNTRY);
		geoServiceGeneric.setCityStateCountry(address);

		this.assertUnknownCountry(address, null, null, COUNTRY);
	}


	public void testChangeCityStateCountryForUnknownState()
	{
		PostalAddress address = new PostalAddressImpl();
		String CITY    = NAME_STRASBOURG;
		String STATE   = NAME_ALSACE;
		String COUNTRY = NAME_FRANCE;
		
		//// check that we can do a lookup by name
		address.setCityAsString(CITY);
		address.setStateProvinceAsString(STATE);
		address.setCountryAsString(COUNTRY);
		
		geoServiceGeneric.setCityStateCountry(address);

		this.assertUnknownStateProvince(address, CITY, STATE, COUNTRY);
		
		//// change the State/Province
		STATE = NAME_MOSELLE;
		address.setStateProvinceAsString(STATE);
		geoServiceGeneric.setCityStateCountry(address);
		
		this.assertUnknownStateProvince(address, CITY, STATE, COUNTRY);

		//// change the City/State
		STATE = NAME_ALSACE;
		CITY  = "Saverne";
		address.setStateProvinceAsString(STATE);
		address.setCityAsString(CITY);

		geoServiceGeneric.setCityStateCountry(address);

		this.assertUnknownStateProvince(address, CITY, STATE, COUNTRY);
		
		//// change the City/State/Country
		STATE   = NAME_CALIFORNIA;
		CITY    = NAME_PETALUMA;
		COUNTRY = CODE_US;

		address.setCountryAsString(COUNTRY);
		address.setStateProvinceAsString(STATE);
		address.setCityAsString(CITY);

		geoServiceGeneric.setCityStateCountry(address);

		this.assertKnownStateProvince(address, CITY, STATE, NAME_USA);
	}
	
	/**
	 * Here we test transactions for {@link GeographyServiceImplGeneric#setCityStateCountry}.
	 * For this we need that the method throws a RuntimeExcetption so we can test that the data was not saved.
	 * We use Spring AOP here, adding an advice at the beginning and removing it at the end.
	 */
	public void testTransactionSetCityStateCountry(){
		// create pointcut, advice, and advisor
		Pointcut pc = new GeographyServiceSetCityStateCountryStaticPointcut();
		Advice advice = new RuntimeExceptionAfterReturningAdvice();
		Advisor advisor = new DefaultPointcutAdvisor(pc, advice);
		((Advised) geoServiceGeneric).addAdvisor(advisor);

		String STATE   = NAME_CALIFORNIA;
		String CITY    = "TestCity";
		String COUNTRY = NAME_USA;

		PostalAddress address = new PostalAddressImpl();
		address.setCountryAsString(COUNTRY);
		address.setStateProvinceAsString(STATE);
		address.setCityAsString(CITY);
		try{
			geoServiceGeneric.setCityStateCountry(address);
		}
		catch(RuntimeException e){
			City city = geoServiceGeneric.findCityByName(COUNTRY,STATE, CITY);
			assertNull("Transaction was rolled back so city must be null,  ", city);
		}
		finally{
			//we must remove the advice for not affecting other tests
			((Advised) geoServiceGeneric).removeAdvisor(advisor);
		}
	}

	private void assertKnownStateProvince(PostalAddress address, String city, String state, String country)
	{
		assertNotNull("Country instance is set", address.getCountry());
		assertNotNull("State instance is set", address.getStateProvince());
		assertNotNull("City instance is set", address.getCity());
		
		assertEquals(country, address.getCountryName());
		assertEquals(state,   address.getStateProvinceName());
		assertEquals(city,    address.getCityName());
		assertEquals(city,    address.getCity().getName());

		assertNull("Country string is null", address.getCountryAsString());
		assertNull("State string is null", address.getStateProvinceAsString());
		assertNull("City string is null", address.getCityAsString());
	}
		

	private void assertUnknownStateProvince(PostalAddress address, String city, String state, String country)
	{
		assertNotNull("Country instance is set", address.getCountry());
		assertNull("State instance is null", address.getStateProvince());
		assertNull("City instance is null",  address.getCity());
		
		assertNull("CountryAsString is null", address.getCountryAsString());
		assertEquals(state,   address.getStateProvinceAsString());
		assertEquals(city,    address.getCityAsString());

		assertEquals(country, address.getCountryName());
		assertEquals(state,   address.getStateProvinceName());
		assertEquals(city,    address.getCityName());
	}


	private void assertUnknownCountry(PostalAddress address, String city, String state, String country) 
	{
		assertNull("Country instance is null", address.getCountry());
		assertNull("State instance is null",   address.getStateProvince());
		assertNull("City instance is null",    address.getCity());
		
		assertEquals(country, address.getCountryAsString());
		assertEquals(state, address.getStateProvinceAsString());
		assertEquals(city,  address.getCityAsString());

		assertEquals(country, address.getCountryName());
		assertEquals(state,   address.getStateProvinceName());
		assertEquals(city,    address.getCityName());
	}

	
	/**
	 * Main function for unit tests
	 * @param args command line args
	 */
	public static void main(String[] args)
	{
		// create the suite of tests
		/*final TestSuite tSuite = new TestSuite();
		try
		{
			tSuite.addTest(new GeographyServiceGenericTest("testTransactionSetCityStateCountry"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		TestRunner.run(tSuite);*/
	}
	
	/**
	 * Pointcut for the method: {@link GeographyServiceImplGeneric#setCityStateCountry}
	 * @author <a href="mailto:moradaniel@janux.org">Daniel Mora</a>
	 *
	 */
	private class GeographyServiceSetCityStateCountryStaticPointcut extends StaticMethodMatcherPointcut {
		public boolean matches(Method method, Class cls) {
			return ("setCityStateCountry".equals(method.getName()));
		}
		public ClassFilter getClassFilter() {
			return new ClassFilter() {
				public boolean matches(Class cls) {
					return (cls == GeographyServiceImplGeneric.class);
				}
			};
		}
	}
}


