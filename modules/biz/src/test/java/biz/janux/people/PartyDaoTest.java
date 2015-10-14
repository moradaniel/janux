package biz.janux.people;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Map;

import biz.janux.geography.PostalAddress;
import biz.janux.test.TransactionalBizTestAbstract;

/**
 * @author   <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version  $Revision: 1.14 $ - $Date: 2006-11-14 01:36:50 $
 */
public class PartyDaoTest extends TransactionalBizTestAbstract
{
	protected PartyDao partyDao;

	public PartyDaoTest() {
		super();
	}

	/*
	public PartyDaoTest(String name) {
		super(name);
	}*/

	public void testLoadPerson() 
	{
		Party party = partyDao.load(new Integer(1));
    assertNotNull("party", party);

    assertEquals("shortName", "Philippe Paravicini", party.getPartyName().getShort());
		assertEquals("longName", "Mr. Philippe Jean Paravicini, M.F.A., C.F.P.", party.getPartyName().getLong());

		Map emailAddrs = party.getEmailAddresses();
		assertEquals("num email addr.", 2, emailAddrs.size());
		assertEquals("EMAIL1", "philippe.paravicini@janux.org", party.getEmailAddress("EMAIL1").getAddress());
		assertEquals("EMAIL2", "philippe@paravicini.com", party.getEmailAddress("EMAIL2").getAddress() );

		Map urls = party.getUrls();
		assertEquals("num urls", 1, urls.size());
		assertEquals("HOME_PAGE", "www.paravicini.com", party.getUrl("HOME_PAGE").getAddress() );

		Map phones = party.getPhoneNumbers();
		assertEquals("num phones", 2, phones.size());

		PhoneNumber phone = party.getPhoneNumber("OFFICE");
		assertEquals("OFFICE area code", "707", phone.getAreaCode() );
		assertEquals("OFFICE number", "763.8535", phone.getNumber() );

		phone = party.getPhoneNumber("MOBILE");
		assertEquals("MOBILE area code", "707", phone.getAreaCode() );
		assertEquals("MOBILE number", "364.1550", phone.getNumber() );

		Map postalAddrs = party.getPostalAddresses();
		assertEquals("num addrs", 2,postalAddrs.size());

		PostalAddress address = party.getPostalAddress("MAILING");
		assertEquals("MAILING line1",        "751 Paula Lane", address.getLine1() );
		assertNull("MAILING line2",          address.getLine2() );
		assertNull("MAILING line3",          address.getLine3() );
		assertEquals("MAILING zip",          "94952", address.getPostalCode() );
		assertEquals("MAILING city",         "Petaluma", address.getCity().getName() );
		assertEquals("MAILING city code",    "PET", address.getCity().getCode() );
		assertEquals("MAILING state",        "California", address.getStateProvince().getName() );
		assertEquals("MAILING state code",   "CA", address.getStateProvince().getCode() );
		assertEquals("MAILING country",      "United States", address.getCountry().getName() );
		assertEquals("MAILING country code", "US", address.getCountry().getCode() );
	}


	public void testLoadOrganization() 
	{
		Party party = partyDao.load(new Integer(3));
    assertNotNull("party", party);

    assertEquals("shortName", "Janux", party.getPartyName().getShort());
		assertEquals("longName", "Janux, Big Business eBiz at Small Business Cost", party.getPartyName().getLong());

		Map emailAddrs = party.getEmailAddresses();
		assertEquals("num email addr.", 1, emailAddrs.size());
		assertEquals("EMAIL1", "info@janux.org", party.getEmailAddress("EMAIL1").getAddress() );

		Map urls = party.getUrls();
		assertEquals("num urls", 2, urls.size());
		assertEquals("WEBSITE", "www.janux.org", party.getUrl("WEBSITE").getAddress() );
		assertEquals("WIKI", "wiki.janux.org", party.getUrl("WIKI").getAddress() );

		Map phones = party.getPhoneNumbers();
		assertEquals("num phones", 1, phones.size());

		PhoneNumber phone = party.getPhoneNumber("MAIN");
		assertEquals("MAIN area code", "707", phone.getAreaCode() );
		assertEquals("MAIN number", "364-1550", phone.getNumber() );

		Map postalAddrs = party.getPostalAddresses();
		assertEquals("num addrs", 1,postalAddrs.size());

		PostalAddress address = party.getPostalAddress("MAILING");
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
	}
	
    /**
     * Main function for unit tests
     * @param args command line args
     */
    public static void main(String[] args)
    {/*
        // create the suite of tests
        final TestSuite tSuite = new TestSuite();
        tSuite.addTest(new PartyDaoTest("testSavePerson"));
        TestRunner.run(tSuite);*/
    }

} // end class PartyDaoTest
