package biz.janux.people;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Map;

import biz.janux.geography.PostalAddress;
import biz.janux.test.TransactionalBizTestAbstract;

/**
 * @author   <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version  $Revision: 1.12 $ - $Date: 2006-09-07 17:52:36 $
 */
public class OrganizationDaoTest extends TransactionalBizTestAbstract
{
	protected OrganizationDao orgDao;

	private final static String ORG_CODE = "JANUX";

	public OrganizationDaoTest() {
		super();
	}

	/*
	public OrganizationDaoTest(String name) {
		super(name);
	}*/

	public void testFindByCode() 
	{
		Organization org = orgDao.findByCode(ORG_CODE);
		assertOrganization(org);
	}

	private void assertOrganization(Organization org)
	{
    assertNotNull("org", org);

    assertEquals("shortName", "Janux", org.getPartyName().getShort());
		assertEquals("longName", "Janux, Big Business eBiz at Small Business Cost", org.getPartyName().getLong());
		assertEquals("legalName", "Janux, the Open Source eBiz Integration Infrastructure", org.getName().getLegal());

		Map emailAddrs = org.getEmailAddresses();
		assertEquals("num email addr.", 1, emailAddrs.size());
		assertEquals("EMAIL1", "info@janux.org", org.getEmailAddress("EMAIL1").getAddress() );

		Map urls = org.getUrls();
		assertEquals("num urls", 2, urls.size());
		assertEquals("WEBSITE", "www.janux.org", org.getUrl("WEBSITE").getAddress() );
		assertEquals("WIKI", "wiki.janux.org", org.getUrl("WIKI").getAddress() );

		Map phones = org.getPhoneNumbers();
		assertEquals("num phones", 1, phones.size());

		PhoneNumber phone = org.getPhoneNumber("MAIN");
		assertEquals("MAIN area code", "707", phone.getAreaCode() );
		assertEquals("MAIN number", "364-1550", phone.getNumber() );

		Map postalAddrs = org.getPostalAddresses();
		assertEquals("num addrs", 1,postalAddrs.size());

		PostalAddress address = org.getPostalAddress("MAILING");
		assertEquals("MAILING line1",        "751 Paula Lane", address.getLine1() );
		assertEquals("MAILING line2",        "World Headquarters", address.getLine2() );
		assertNull("MAILING line3",          address.getLine3() );
		assertEquals("MAILING zip",          "94952", address.getPostalCode() );
		assertEquals("MAILING city",         "Petaluma", address.getCity().getName() );
		assertEquals("MAILING city code",    "PET", address.getCity().getCode() );
		assertEquals("MAILING state",        "California", address.getStateProvince().getName() );
		assertEquals("MAILING state code",   "CA", address.getStateProvince().getCode() );
		assertEquals("MAILING country",      "United States", address.getCountry().getName() );
		assertEquals("MAILING country code", "US", address.getCountry().getCode() );

		/*
		Map props = org.getProperties();
		assertEquals("num props", 5, props.size());

		assertEquals("numEmployees", new Integer(3),   org.getProperty("numEmployees"));
		assertEquals("isForProfit", Boolean.FALSE, org.getProperty("isForProfit"));
		assertEquals("memo1", "memo1",  org.getProperty("memo1"));
		assertEquals("memo2", "memo2",  org.getProperty("memo2"));
		*/
	}

} // end class OrganizationDaoTest
