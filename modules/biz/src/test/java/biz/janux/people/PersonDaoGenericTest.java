package biz.janux.people;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import junit.framework.TestSuite;
import junit.textui.TestRunner;
import biz.janux.commerce.CommerceDao;
import biz.janux.commerce.CreditCardFop;
import biz.janux.commerce.FormOfPayment;
import biz.janux.commerce.PaymentMethod;
import biz.janux.geography.City;
import biz.janux.geography.CityDaoGeneric;
import biz.janux.geography.Country;
import biz.janux.geography.CountryDaoGeneric;
import biz.janux.geography.PostalAddress;
import biz.janux.geography.PostalAddressImpl;
import biz.janux.geography.StateProvince;
import biz.janux.geography.StateProvinceDaoGeneric;
import biz.janux.people.net.EmailAddressImpl;
import biz.janux.people.net.HttpAddressImpl;
import biz.janux.people.net.Uri;
import biz.janux.test.TransactionalBizTestAbstractGeneric;

/**
 * @author	 <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version  $Revision: 1.22 $ - $Date: 2007-12-18 20:51:37 $
 */
public class PersonDaoGenericTest extends TransactionalBizTestAbstractGeneric
{
	protected PersonDaoGeneric<Person> personDaoGeneric;
	protected CommerceDao commerceDao;
	protected CityDaoGeneric<City> cityDaoGeneric;
	protected StateProvinceDaoGeneric<StateProvince> stateProvinceDaoGeneric;
	protected CountryDaoGeneric<Country> countryDaoGeneric;

	public PersonDaoGenericTest() {
		super();
	}

	public PersonDaoGenericTest(String name) {
		super(name);
	}

	public void testLoadPerson() 
	{
		Person person = personDaoGeneric.load(new Integer(1));
	assertNotNull("person", person);

		assertEquals("prefix", "Mr.",         person.getName().getHonorificPrefix());
		assertEquals("first",  "Philippe",    person.getName().getFirst());
		assertEquals("middle", "Jean",        person.getName().getMiddle());
		assertEquals("last",   "Paravicini",  person.getName().getLast());
		assertEquals("suffix", "M.F.A., C.F.P.", person.getName().getHonorificSuffix());

		assertEquals("shortName", "Philippe Paravicini", person.getPartyName().getShort());
		assertEquals("longName", "Mr. Philippe Jean Paravicini, M.F.A., C.F.P.", person.getPartyName().getLong());

		Map emailAddrs = person.getEmailAddresses();
		assertEquals("num email addr.", 2, emailAddrs.size());
		assertEquals("EMAIL1", "philippe.paravicini@janux.org", ((EmailAddressImpl)emailAddrs.get("EMAIL1")).getAddress() );
		assertEquals("EMAIL2", "philippe@paravicini.com", ((EmailAddressImpl)emailAddrs.get("EMAIL2")).getAddress() );

		Map urls = person.getUrls();
		assertEquals("num urls", 1, urls.size());
		assertEquals("HOME_PAGE", "www.paravicini.com", ((HttpAddressImpl)urls.get("HOME_PAGE")).getAddress() );

		Map phones = person.getPhoneNumbers();
		assertEquals("num phones", 2, phones.size());

		PhoneNumberImpl phone = (PhoneNumberImpl)phones.get("OFFICE");
		assertEquals("OFFICE area code", "707", phone.getAreaCode() );
		assertEquals("OFFICE number", "763.8535", phone.getNumber() );

		phone = (PhoneNumberImpl)phones.get("MOBILE");
		assertEquals("MOBILE area code", "707", phone.getAreaCode() );
		assertEquals("MOBILE number", "364.1550", phone.getNumber() );

		Map postalAddrs = person.getPostalAddresses();
		assertEquals("num addrs", 2,postalAddrs.size());

		PostalAddressImpl address = (PostalAddressImpl)postalAddrs.get("MAILING");
		assertEquals("MAILING line1",      "751 Paula Lane", address.getLine1() );
		assertNull("MAILING line2",        address.getLine2() );
		assertNull("MAILING line3",        address.getLine3() );
		assertEquals("MAILING zip",        "94952", address.getPostalCode() );
		assertEquals("MAILING city",       "Petaluma", address.getCity().getName() );
		assertEquals("MAILING city code",  "PET", address.getCity().getCode() );
		assertEquals("MAILING state",      "California", address.getStateProvince().getName() );
		assertEquals("MAILING state code", "CA", address.getStateProvince().getCode() );
		assertEquals("MAILING country",    "United States", address.getCountry().getName() );
		assertEquals("MAILING country code", "US", address.getCountry().getCode() );
	}

	public void testSavePerson() 
	{
		final Person person = new PersonImpl();
		
		// person name
		final PersonName name = new PersonNameImpl();
		name.setFirst("Joe");
		name.setLast("Smith");
		person.setName(name);
		
		// set the mailing address
		if (true)
		{
			final String sAddressType = "MAILING";
			final PostalAddress address = new PostalAddressImpl();
			address.setLine1("11 Main Street");
			address.setPostalCode("94952");
			address.setCityAsString("CuPeRtInO");
			address.setCountryAsString("United States of America");
			address.setStateProvinceAsString("CaliFORNIA");
			
			StateProvince stateProvince = stateProvinceDaoGeneric.findByName("US", "California");
			
			City city = cityDaoGeneric.findByName(stateProvince, "Petaluma");
			
			Country country = countryDaoGeneric.findByCode("US");
			
			city.setProvince(stateProvince);
			city.setCountry(country);
			address.setCity(city);
			
			person.setContactMethod(sAddressType, address);
		}
		
		// phone number
		if (true)
		{
			final String sPhoneType = "MAIN";
			final PhoneNumber phone = new PhoneNumberImpl();
			phone.setNumber("555-1212");
			person.setContactMethod(sPhoneType, phone);
		}
		
		// email address
		if (true)
		{
			final String sEmailType = "EMAIL1";
			final Uri email = new EmailAddressImpl();
			email.setAddress("joe@smith.com");
			person.setContactMethod(sEmailType, email);
		}
		
		// form of payment
		if (true)
		{
			final CreditCardFop fop = new CreditCardFop();
			final PaymentMethod visaCard = commerceDao.findPaymentMethodByCode("VI");

			fop.setCardNumber("1234123412341234");
			fop.setCardOwnerName("Joe Smith");
			fop.setCardType(visaCard);
			fop.setSecurityCode("1234");
			fop.setExpiration(new Date());
			fop.setPosition(1);    // TODO does this need to be set?
			fop.setParty(person);
			
			person.getFormsOfPayment().add(fop);
			
			
			final CreditCardFop fop2 = new CreditCardFop();
			final PaymentMethod amexCard = commerceDao.findPaymentMethodByCode("AX");

			fop2.setCardNumber("555444333222");
			fop2.setCardOwnerName("Dave Jones");
			fop2.setCardType(amexCard);
			fop2.setExpiration(new Date());
			fop2.setPosition(2);	// TODO does this need to be set?
			fop2.setParty(person);
			
			person.getFormsOfPayment().add(fop2);
		}
		
		
		personDaoGeneric.saveOrUpdate(person);
		
		Object retObject = personDaoGeneric.load(person.getId());
		
		final Person retPerson = (Person ) retObject;
		assertNotNull("party", retPerson);
		
		final Iterator<FormOfPayment> it = retPerson.getFormsOfPayment().iterator();
		while (it.hasNext())
		{
			final FormOfPayment fop = it.next();
			if (fop instanceof FormOfPayment)
			{
				final Integer id = fop.getId();
				assertNotNull(id);
			}
		}
		
		assertEquals(2, retPerson.getFormsOfPayment().size());
		assertEquals(3, retPerson.getContactMethods().size());
		
		final FormOfPayment retFop = retPerson.getFormsOfPayment().get(0);
		final CreditCardFop ccFop = (CreditCardFop )retFop;
		retFop.hashCode();
	  //  int iPosition = retFop.getPosition();
		
		PostalAddress address = person.getPostalAddress("MAILING");
		
		assertEquals("Petaluma", address.getCityName());
		assertEquals("California", address.getStateProvinceName());
		assertEquals("United States", address.getCountryName());

		personDaoGeneric.delete(person);
	}
	
	public void testSavePersonWithAddressVariation() 
	{
		final Person person = new PersonImpl();
		
		// person name
		final PersonName name = new PersonNameImpl();
		name.setFirst("Joe");
		name.setLast("Smith");
		person.setName(name);
		
		// set the mailing address
		if (true)
		{
			final String sAddressType = "MAILING";
			final PostalAddress address = new PostalAddressImpl();
			address.setLine1("11 Main Street");
			address.setPostalCode("95014-2345");
			address.setCityAsString("CuPeRtInO");
			address.setCountryAsString("United States of America");
			address.setStateProvinceAsString("CaliFORNIA");
			
			person.setContactMethod(sAddressType, address);
		}
		
		// phone number
		if (true)
		{
			final String sPhoneType = "MAIN";
			final PhoneNumber phone = new PhoneNumberImpl();
			phone.setNumber("555-1212");
			person.setContactMethod(sPhoneType, phone);
		}
		
		// email address
		if (true)
		{
			final String sEmailType = "EMAIL1";
			final Uri email = new EmailAddressImpl();
			email.setAddress("joe@smith.com");
			person.setContactMethod(sEmailType, email);
		}
		
		// form of payment
		if (true)
		{
			final CreditCardFop fop = new CreditCardFop();
			final PaymentMethod visaCard = commerceDao.findPaymentMethodByCode("VI");

			fop.setCardNumber("1234123412341234");
			fop.setCardOwnerName("Joe Smith");
			fop.setCardType(visaCard);
			fop.setSecurityCode("1234");
			fop.setExpiration(new Date());
			fop.setPosition(1);    // TODO does this need to be set?
			fop.setParty(person);
			
			person.getFormsOfPayment().add(fop);
			
			
			final CreditCardFop fop2 = new CreditCardFop();
			final PaymentMethod amexCard = commerceDao.findPaymentMethodByCode("AX");

			fop2.setCardNumber("555444333222");
			fop2.setCardOwnerName("Dave Jones");
			fop2.setCardType(amexCard);
			fop2.setExpiration(new Date());
			fop2.setPosition(2);	// TODO does this need to be set?
			fop2.setParty(person);
			
			person.getFormsOfPayment().add(fop2);
			
		}
		
		
		personDaoGeneric.saveOrUpdate(person);
		
		Object retObject = personDaoGeneric.load(person.getId());
		
		final Person retPerson = (Person ) retObject;
		assertNotNull("party", retPerson);
		
		final Iterator<FormOfPayment> it = retPerson.getFormsOfPayment().iterator();
		while (it.hasNext())
		{
			final FormOfPayment fop = it.next();
			if (fop instanceof FormOfPayment)
			{
				final Integer id = fop.getId();
				assertNotNull(id);
			}
		}
		
		assertEquals(2, retPerson.getFormsOfPayment().size());
		assertEquals(3, retPerson.getContactMethods().size());
		
		final FormOfPayment retFop = retPerson.getFormsOfPayment().get(0);
		final CreditCardFop ccFop = (CreditCardFop)retFop;
		retFop.hashCode();
		
		PostalAddress address = person.getPostalAddress("MAILING");
	   
		assertEquals("CuPeRtInO", address.getCityName());
		assertEquals("CaliFORNIA", address.getStateProvinceName());
		assertEquals("United States of America", address.getCountryName());

		personDaoGeneric.delete(person);
	}
	
	/**
	 * Main function for unit tests
	 * @param args command line args
	 */
	public static void main(String[] args)
	{
		// create the suite of tests
		final TestSuite tSuite = new TestSuite();
		tSuite.addTest(new PersonDaoGenericTest("testSavePerson"));
		tSuite.addTest(new PersonDaoGenericTest("testLoadPerson"));
		TestRunner.run(tSuite);
	}
	
} // end class PersonDaoTest
