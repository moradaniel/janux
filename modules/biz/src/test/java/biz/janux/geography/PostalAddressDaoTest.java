package biz.janux.geography;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import junit.framework.TestSuite;
import junit.textui.TestRunner;
import biz.janux.test.TransactionalBizTestAbstract;

/**
 * @author   <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version  $Revision: 1.1 $ - $Date: 2008-03-31 20:41:47 $
 */
public class PostalAddressDaoTest extends TransactionalBizTestAbstract
{
	protected PostalAddressDao postalAddressDao;

	private static final int ID_ADDR1       = 15;

	public PostalAddressDaoTest()  { 
		super();
	}

	/*
	public PostalAddressDaoTest(String name)  {
		super(name);
	}*/

	/** 
	 * Address which references a City as an entity (with a foreign-key) and
	 * thus implicitly references a StateProvince and Country
	 */
	public void testLoadAddressWithCityEntity() 
	{
		PostalAddress address = postalAddressDao.load(new Integer(15));
    assertNotNull(address);
    assertNotNull(address.getCity());
    assertNotNull(address.getStateProvince());
    assertNotNull(address.getCountry());

		assertEquals("Petaluma",      address.getCity().getName());
		assertEquals("California",    address.getStateProvince().getName());
		assertEquals("United States", address.getCountry().getName());

		assertEquals(address.getCity().getName(),          address.getCityName());
		assertEquals(address.getStateProvince().getName(), address.getStateProvinceName());
		assertEquals(address.getCountry().getName(),       address.getCountryName());

		assertEquals("751 Paula Lane", address.getLine1());
		assertEquals("World Headquarters", address.getLine2());

		assertNull(address.getCityAsString());
		assertNull(address.getStateProvinceAsString());
		assertNull(address.getCountryAsString());
	}

	/** 
	 * Address which references a Country as an entity (with a foreign-key) and
	 * stores City/StateProvince as Strings
	 */
	public void testLoadAddressWithCountryEntity() 
	{
		PostalAddress address = postalAddressDao.load(new Integer(21));
    assertNotNull(address);
    assertNull(address.getCity());
    assertNull(address.getStateProvince());
    assertNotNull(address.getCountry());

		assertEquals("France", address.getCountry().getName());
		assertEquals(address.getCountry().getName(), address.getCountryName());
		
		assertEquals("Bordeaux", address.getCityAsString());
		assertEquals("Bordeaux", address.getCityName());

		assertEquals("13 RUE THALES", address.getLine1());

		assertNull(address.getStateProvinceAsString());
		assertNull(address.getCountryAsString());
	}

	public void testLoadAddressWithCityStateCountryAsStrings() 
	{
		PostalAddress address = postalAddressDao.load(new Integer(20));

    assertNotNull(address);

    assertNull(address.getCity());
    assertNull(address.getStateProvince());
    assertNull(address.getCountry());

		assertEquals("Suisse", address.getCountryAsString());
		assertEquals("Suisse", address.getCountryName());

		assertEquals("GE", address.getStateProvinceAsString());
		assertEquals("GE", address.getStateProvinceName());

		assertEquals("Geneva", address.getCityAsString());
		assertEquals("Geneva", address.getCityName());

		assertEquals("2-4 Place des Alpes", address.getLine1());
	}


	public void testCountByCountryAsString()
	{
		assertEquals(4, postalAddressDao.countByCountryAsString());
		assertEquals(2, postalAddressDao.countByCountryAsString("Suisse"));
	}


	public void testFindAllCountryAsString()
	{
		List<PostalAddress> l;
		PostalAddress address;

		// retrieve all
		l = this.postalAddressDao.findByCountryAsString();
		assertEquals(4, l.size());

		address = l.get(0);
		assertEquals("ch. de la Jaque 20", address.getLine1());	

		address = l.get(1);
		assertEquals("2-4 Place des Alpes", address.getLine1());	

		address = l.get(2);
		assertEquals("37 avenue de Saint Mande", address.getLine1());	

		address = l.get(3);
		assertEquals("69 avenue Mozart", address.getLine1());	


		// retrieve first 2-record page
		int PAGE_SIZE = 2;
		l = this.postalAddressDao.findByCountryAsString(PAGE_SIZE, 0);
		assertEquals(2, l.size());

		address = l.get(0);
		assertEquals("ch. de la Jaque 20", address.getLine1());	

		address = l.get(1);
		assertEquals("2-4 Place des Alpes", address.getLine1());	

		// retrieve second 2-record page
		l = this.postalAddressDao.findByCountryAsString(PAGE_SIZE, 1 * PAGE_SIZE);
		assertEquals(2, l.size());

		address = l.get(0);
		assertEquals("37 avenue de Saint Mande", address.getLine1());	

		address = l.get(1);
		assertEquals("69 avenue Mozart", address.getLine1());	
	}


	public void testFindByCountryAsString()
	{
		List<PostalAddress> l;
		PostalAddress address;

		// retrieve all for "Suisse"
		l = this.postalAddressDao.findByCountryAsString("Suisse");
		assertEquals(2, l.size());

		address = l.get(0);
		assertEquals("ch. de la Jaque 20", address.getLine1());	

		address = l.get(1);
		assertEquals("2-4 Place des Alpes", address.getLine1());	


		// retrieve first page (of two records)
		int PAGE_SIZE = 1;
		l = this.postalAddressDao.findByCountryAsString("Suisse", PAGE_SIZE, 0);
		assertEquals(1, l.size());

		address = l.get(0);
		assertEquals("ch. de la Jaque 20", address.getLine1());	

		// retrieve second page (of one record)
		l = this.postalAddressDao.findByCountryAsString("Suisse", PAGE_SIZE, 1 * PAGE_SIZE);
		assertEquals(1, l.size());

		address = l.get(0);
		assertEquals("2-4 Place des Alpes", address.getLine1());	
	}

    public static void main(String[] args)
    {
        // create the suite of tests
        /*final TestSuite tSuite = new TestSuite();
        tSuite.addTest(new PostalAddressDaoTest("testLoadAddressWithCityEntity"));*/
    	final TestSuite tSuite = new TestSuite(PostalAddressDaoTest.class);
        TestRunner.run(tSuite);
    }

}
