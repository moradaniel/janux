package biz.janux.people;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import biz.janux.commerce.CashFormOfPayment;
import biz.janux.commerce.CreditCardFop;
import biz.janux.commerce.PaymentMethod;
import biz.janux.commerce.PaymentMethodImpl;
import biz.janux.geography.CityImpl;
import biz.janux.geography.CountryImpl;
import biz.janux.geography.PostalAddress;
import biz.janux.geography.PostalAddressImpl;
import biz.janux.geography.StateProvinceImpl;
import biz.janux.people.net.EmailAddressImpl;
import biz.janux.people.net.HttpAddressImpl;
import biz.janux.people.net.Uri;
import biz.janux.people.net.Url;

public class ContactMethodTest extends TestCase
{
	public ContactMethodTest() 
	{
		super();
	}

	public ContactMethodTest(String name) 
	{
		super(name);
	}

	/** define the tests to be run in this class */
	public static Test suite() throws Exception
	{
		final TestSuite suite = new TestSuite();

		// run all tests
		suite.addTestSuite(ContactMethodTest.class);

		// or a subset thereoff
	//	suite.addTest(new ContactMethodTest("testClone"));

		return suite;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		TestRunner.run(suite());
	}

	
	public void testEmailClone()
	{
		final Uri source = getTestEmailAddress();
		final EmailAddressImpl clone = (EmailAddressImpl )source.clone();
		checkEmailClone(source, clone);
	}
	
	private Uri getTestEmailAddress()
	{
		// create the source object to clone
		final Party person1 = new PersonImpl();
		person1.setCode("PERSON1");
		final Party person2 = new PersonImpl();
		person2.setCode("PERSON2");
		final Party person3 = new PersonImpl();
		person3.setCode("PERSON2");
		
		final EmailAddressImpl source = new EmailAddressImpl();
		source.setId(100);
		source.setAddress("mailto:test@janux.com");
		//source.getParties().put("FIRST", person1);
		//source.getParties().put("SECOND", person2);
		//source.getParties().put("THIRD", person3);
		
		return (source);
	}
	
	private void checkEmailClone(final Uri source, final Uri clone)
	{
		assertNotNull(clone);
		assertNotSame(source, clone);
		assertEquals(new Integer(-1), clone.getId());
		assertEquals(source.getAddress(), clone.getAddress());
		/*
		assertNotNull(clone.getParties());
		assertNotSame(source.getParties(), clone.getParties());
		assertEquals(source.getParties().size(), clone.getParties().size());
		
		assertTrue(clone.getParties().keySet().contains("FIRST"));
		assertTrue(clone.getParties().keySet().contains("SECOND"));
		assertTrue(clone.getParties().keySet().contains("THIRD"));
		assertSame(source.getParties().get("FIRST"), clone.getParties().get("FIRST"));
		assertSame(source.getParties().get("SECOND"), clone.getParties().get("SECOND"));
		assertSame(source.getParties().get("THIRD"), clone.getParties().get("THIRD"));
		*/
	}

	
	public void testHttpAddressClone()
	{
		final Url source = getTestHttpAddress();
		final HttpAddressImpl clone = (HttpAddressImpl )source.clone();
		checkHttpClone(source, clone);
	}
	
	private Url getTestHttpAddress()
	{
		// create the source object to clone
		final Party person1 = new PersonImpl();
		person1.setCode("PERSON1");
		final Party person2 = new PersonImpl();
		person2.setCode("PERSON2");
		final Party person3 = new PersonImpl();
		person3.setCode("PERSON2");
		
		final HttpAddressImpl source = new HttpAddressImpl();
		source.setId(100);
		source.setAddress("mailto:test@janux.com");
		//source.getParties().put("FIRST", person1);
		//source.getParties().put("SECOND", person2);
		//source.getParties().put("THIRD", person3);
		
		return (source);
	}
	
	private void checkHttpClone(final Url source, final Url clone)
	{
		assertNotNull(clone);
		assertNotSame(source, clone);
		assertEquals(new Integer(-1), clone.getId());
		assertEquals(source.getAddress(), clone.getAddress());
		/*
		assertNotNull(clone.getParties());
		assertNotSame(source.getParties(), clone.getParties());
		assertEquals(source.getParties().size(), clone.getParties().size());
		
		assertTrue(clone.getParties().keySet().contains("FIRST"));
		assertTrue(clone.getParties().keySet().contains("SECOND"));
		assertTrue(clone.getParties().keySet().contains("THIRD"));
		assertSame(source.getParties().get("FIRST"), clone.getParties().get("FIRST"));
		assertSame(source.getParties().get("SECOND"), clone.getParties().get("SECOND"));
		assertSame(source.getParties().get("THIRD"), clone.getParties().get("THIRD"));
		*/
	}

	
	public void testPhoneNumberClone()
	{
		final PhoneNumber source = getTestPhoneNumber();
		final PhoneNumberImpl clone = (PhoneNumberImpl )source.clone();
		checkPhoneNumberClone(source, clone);
	}
	
	private PhoneNumber getTestPhoneNumber()
	{
		// create the source object to clone
		final Party person1 = new PersonImpl();
		person1.setCode("PERSON1");
		final Party person2 = new PersonImpl();
		person2.setCode("PERSON2");
		final Party person3 = new PersonImpl();
		person3.setCode("PERSON2");
		
		final PhoneNumberImpl source = new PhoneNumberImpl();
		source.setId(100);
		source.setCountryCode("123");
		source.setAreaCode("456");
		source.setNumber("555-1212");
		source.setExtension("789");
		//source.getParties().put("FIRST", person1);
		//source.getParties().put("SECOND", person2);
		//source.getParties().put("THIRD", person3);
	
		return (source);
	}
	
	private void checkPhoneNumberClone(final PhoneNumber source, final PhoneNumber clone)
	{
		assertNotNull(clone);
		assertNotSame(source, clone);
		assertEquals(new Integer(-1), clone.getId());
		assertEquals(source.getCountryCode(), clone.getCountryCode());
		assertEquals(source.getAreaCode(), clone.getAreaCode());
		assertEquals(source.getNumber(), clone.getNumber());
		assertEquals(source.getExtension(), clone.getExtension());
				/*
		assertNotNull(clone.getParties());
		assertNotSame(source.getParties(), clone.getParties());
		assertEquals(source.getParties().size(), clone.getParties().size());
		
		assertTrue(clone.getParties().keySet().contains("FIRST"));
		assertTrue(clone.getParties().keySet().contains("SECOND"));
		assertTrue(clone.getParties().keySet().contains("THIRD"));
		assertSame(source.getParties().get("FIRST"), clone.getParties().get("FIRST"));
		assertSame(source.getParties().get("SECOND"), clone.getParties().get("SECOND"));
		assertSame(source.getParties().get("THIRD"), clone.getParties().get("THIRD"));
				*/
	}
	
	
	public void testPostalAddressClone()
	{
		// address
		final PostalAddress source = getTestPostalAddress();
		final PostalAddressImpl clone = (PostalAddressImpl )source.clone();
		checkPostalAddressClone(source, clone);
	}
	
	private PostalAddress getTestPostalAddress()
	{
		// create the source object to clone
		final Party person1 = new PersonImpl();
		person1.setCode("PERSON1");
		final Party person2 = new PersonImpl();
		person2.setCode("PERSON2");
		final Party person3 = new PersonImpl();
		person3.setCode("PERSON2");
		
		// country
		final CountryImpl country = new CountryImpl();
		country.setId(100);
		country.setCode("US");
		country.setName("United States");
		country.setPhoneCode(123);
		country.setSortOrder(3);
		country.setVisible(true);

		// state
		final StateProvinceImpl state = new StateProvinceImpl();
		state.setId(101);
		state.setCode("CA");
		state.setName("California");
		state.setSortOrder(4);
		state.setVisible(true);
		state.setCountry(country);
		
		// city
		final CityImpl city = new CityImpl();
		city.setId(102);
		city.setCode("SFO");
		city.setName("San Francisco");
		city.setState(state);

		// address
		final PostalAddressImpl source = new PostalAddressImpl();
		source.setId(100);
		source.setLine1("Address Line 1");
		source.setLine2("Address Line 2");
		source.setLine3("Address Line 3");
		source.setPostalCode("12345");
		source.setCityAsString("San Francisco");
		source.setStateProvinceAsString("California");
		source.setCountryAsString("United States");
		source.setCountry(country);
		source.setStateProvince(state);
		source.setCity(city);
		
		
		//source.getParties().put("FIRST", person1);
		//source.getParties().put("SECOND", person2);
		//source.getParties().put("THIRD", person3);
	
		return (source);
	}
	
	private void checkPostalAddressClone(final PostalAddress source, final PostalAddress clone)
	{
		assertNotNull(clone);
		assertNotSame(source, clone);
		assertEquals(new Integer(-1), clone.getId());
		
		assertEquals(source.getLine1(), clone.getLine1());
		assertEquals(source.getLine2(), clone.getLine2());
		assertEquals(source.getLine3(), clone.getLine3());
		assertEquals(source.getPostalCode(), clone.getPostalCode());
		assertEquals(source.getCityName(), clone.getCityName());
		assertEquals(source.getStateProvinceName(), clone.getStateProvinceName());
		assertEquals(source.getCountryName(), clone.getCountryName());
		
		// check country
		assertNotNull(clone.getCountry());
		assertSame(source.getCountry(), clone.getCountry());
		/*
		assertEquals(new Integer(-1), clone.getCountry().getId());
		assertEquals(source.getCountry().getCode(), clone.getCountry().getCode());
		assertEquals(source.getCountry().getName(), clone.getCountry().getName());
		assertEquals(source.getCountry().getSortOrder(), clone.getCountry().getSortOrder());
		assertEquals(source.getCountry().getPhoneCode(), clone.getCountry().getPhoneCode());
		*/
		
		// check state
		assertNotNull(clone.getStateProvince());
		assertSame(source.getStateProvince(), clone.getStateProvince());
		/*
		assertEquals(new Integer(-1), clone.getStateProvince().getId());
		assertEquals(source.getStateProvince().getCode(), clone.getStateProvince().getCode());
		assertEquals(source.getStateProvince().getName(), clone.getStateProvince().getName());
		assertEquals(source.getStateProvince().getSortOrder(), clone.getStateProvince().getSortOrder());
		*/
		
		// check city
		assertNotNull(clone.getCity());
		assertSame(source.getCity(), clone.getCity());
		/*
		assertEquals(new Integer(-1), clone.getCity().getId());
		assertEquals(source.getCity().getCode(), clone.getCity().getCode());
		assertEquals(source.getCity().getName(), clone.getCity().getName());
		*/
		
		// check parties
		//assertNotNull(clone.getParties());
		//assertNotSame(source.getParties(), clone.getParties());
		//assertEquals(source.getParties().size(), clone.getParties().size());
		//assertTrue(clone.getParties().keySet().contains("FIRST"));
		//assertTrue(clone.getParties().keySet().contains("SECOND"));
		//assertTrue(clone.getParties().keySet().contains("THIRD"));
		//assertSame(source.getParties().get("FIRST"), clone.getParties().get("FIRST"));
		//assertSame(source.getParties().get("SECOND"), clone.getParties().get("SECOND"));
		//assertSame(source.getParties().get("THIRD"), clone.getParties().get("THIRD"));
	}
	
	
	public void testContactMethodManager()
	{
		final Uri email = getTestEmailAddress();
		final Url http	= getTestHttpAddress();
		final PhoneNumber phone = getTestPhoneNumber();
		final PostalAddress address = getTestPostalAddress();
		
		
		final ContactMethodManager source = new ContactMethodManager();
		source.setContactMethod("EMAIL", email);
		source.setContactMethod("HTTP", http);
		source.setContactMethod("PHONE", phone);
		source.setContactMethod("ADDRESS", address);
		
		final ContactMethodManager clone = (ContactMethodManager )source.clone();
		
		assertNotNull(clone);
		assertNotSame(source, clone);
		assertNotNull(clone.getContactMethods());
		assertNotSame(source.getContactMethods(), clone.getContactMethods());
		assertEquals(source.getContactMethods().size(), clone.getContactMethods().size());
		
		// check email
		final Uri sourceEmail = source.getEmailAddress("EMAIL");
		final Uri cloneEmail  = clone.getEmailAddress("EMAIL");
		checkEmailClone(sourceEmail, cloneEmail);
		
		// check http
		final Url sourceHttp = source.getUrl("HTTP");
		final Url cloneHttp  = clone.getUrl("HTTP");
		checkHttpClone(sourceHttp, cloneHttp);
		
		// check phone
		final PhoneNumber sourcePhone = source.getPhoneNumber("PHONE");
		final PhoneNumber clonePhone  = clone.getPhoneNumber("PHONE");
		checkPhoneNumberClone(sourcePhone, clonePhone);
		
		// check address
		final PostalAddress sourceAddress = source.getPostalAddress("ADDRESS");
		final PostalAddress cloneAddress  = clone.getPostalAddress("ADDRESS");
		checkPostalAddressClone(sourceAddress, cloneAddress);
	}
	
	public void testPersonClone()
	{
		final Uri email = getTestEmailAddress();
		final Url http	= getTestHttpAddress();
		final PhoneNumber phone = getTestPhoneNumber();
		final PostalAddress address = getTestPostalAddress();
		
		final CashFormOfPayment fopCash = new CashFormOfPayment();
		fopCash.setCurrencyCode("USD");
		fopCash.setPosition(5);
		
		final CreditCardFop fopCard = new CreditCardFop();
		final PaymentMethod cardType = new PaymentMethodImpl();
		cardType.setCode("VISA");
		fopCard.setCardNumber("123456789");
		fopCard.setCardOwnerName("Ed Jones");
		fopCard.setCardType(cardType);
		fopCard.setExpiration(2009, 12);
		fopCard.setSecurityCode("1234");
		fopCard.setPosition(5);
		
		final PersonNameImpl name = new PersonNameImpl();
		name.setFirst("Ted");
		name.setLast("Smith");
		
		final PersonImpl source = new PersonImpl();
		source.setCode("TEST");
		source.setName(name);
		source.setContactMethod("EMAIL", email);
		source.setContactMethod("HTTP", http);
		source.setContactMethod("PHONE", phone);
		source.setContactMethod("ADDRESS", address);
		source.getFormsOfPayment().add(fopCash);
		source.getFormsOfPayment().add(fopCard);
		
		
		// clone the source
		final PersonImpl clone = (PersonImpl )source.clone();
		
		// check the results
		assertNotNull(clone);
		assertNotSame(source, clone);
		assertEquals(new Integer(-1), clone.getId());
		assertEquals(source.getCode(), clone.getCode());
		
		// check name
		assertNotSame(source.getName(), clone.getName());
		assertEquals(source.getName().getFirst(), clone.getName().getFirst());
		assertEquals(source.getName().getLast(), clone.getName().getLast());
		
		// check contact methods
		assertNotNull(clone.getContactMethods());
		assertNotSame(source.getContactMethods(), clone.getContactMethods());
		assertEquals(source.getContactMethods().size(), clone.getContactMethods().size());
		
		// check email
		final Uri sourceEmail = source.getEmailAddress("EMAIL");
		final Uri cloneEmail  = clone.getEmailAddress("EMAIL");
		checkEmailClone(sourceEmail, cloneEmail);
		
		// check http
		final Url sourceHttp = source.getUrl("HTTP");
		final Url cloneHttp  = clone.getUrl("HTTP");
		checkHttpClone(sourceHttp, cloneHttp);
		
		// check phone
		final PhoneNumber sourcePhone = source.getPhoneNumber("PHONE");
		final PhoneNumber clonePhone  = clone.getPhoneNumber("PHONE");
		checkPhoneNumberClone(sourcePhone, clonePhone);
		
		// check address
		final PostalAddress sourceAddress = source.getPostalAddress("ADDRESS");
		final PostalAddress cloneAddress  = clone.getPostalAddress("ADDRESS");
		checkPostalAddressClone(sourceAddress, cloneAddress);
		
		// check forms of payment
		assertNotNull(clone.getFormsOfPayment());
		assertNotSame(source.getFormsOfPayment(), clone.getFormsOfPayment());
		assertEquals(source.getFormsOfPayment().size(), clone.getFormsOfPayment().size());
		
		
	}
	
	
	public void testOrganizationClone()
	{
		final Uri email = getTestEmailAddress();
		final Url http	= getTestHttpAddress();
		final PhoneNumber phone = getTestPhoneNumber();
		final PostalAddress address = getTestPostalAddress();
		
		final CashFormOfPayment fopCash = new CashFormOfPayment();
		fopCash.setCurrencyCode("USD");
		fopCash.setPosition(5);
		
		final CreditCardFop fopCard = new CreditCardFop();
		final PaymentMethod cardType = new PaymentMethodImpl();
		cardType.setCode("VISA");
		fopCard.setCardNumber("123456789");
		fopCard.setCardOwnerName("Ed Jones");
		fopCard.setCardType(cardType);
		fopCard.setExpiration(2009, 12);
		fopCard.setSecurityCode("1234");
		fopCard.setPosition(5);
		
		final OrganizationNameImpl name = new OrganizationNameImpl();
		name.setShort("Janux Corporation");
		
		final OrganizationImpl source = new OrganizationImpl();
		source.setCode("TEST");
		source.setName(name);
		source.setContactMethod("EMAIL", email);
		source.setContactMethod("HTTP", http);
		source.setContactMethod("PHONE", phone);
		source.setContactMethod("ADDRESS", address);
		source.getFormsOfPayment().add(fopCash);
		source.getFormsOfPayment().add(fopCard);
		
		
		// clone the source
		final OrganizationImpl clone = (OrganizationImpl )source.clone();
		
		// check the results
		assertNotNull(clone);
		assertNotSame(source, clone);
		assertEquals(new Integer(-1), clone.getId());
		assertEquals(source.getCode(), clone.getCode());
		
		// check name
		assertNotSame(source.getName(), clone.getName());
		assertEquals(source.getName().getShort(), clone.getName().getShort());
		
		// check contact methods
		assertNotNull(clone.getContactMethods());
		assertNotSame(source.getContactMethods(), clone.getContactMethods());
		assertEquals(source.getContactMethods().size(), clone.getContactMethods().size());
		
		// check email
		final Uri sourceEmail = source.getEmailAddress("EMAIL");
		final Uri cloneEmail  = clone.getEmailAddress("EMAIL");
		checkEmailClone(sourceEmail, cloneEmail);
		
		// check http
		final Url sourceHttp = source.getUrl("HTTP");
		final Url cloneHttp  = clone.getUrl("HTTP");
		checkHttpClone(sourceHttp, cloneHttp);
		
		// check phone
		final PhoneNumber sourcePhone = source.getPhoneNumber("PHONE");
		final PhoneNumber clonePhone  = clone.getPhoneNumber("PHONE");
		checkPhoneNumberClone(sourcePhone, clonePhone);
		
		// check address
		final PostalAddress sourceAddress = source.getPostalAddress("ADDRESS");
		final PostalAddress cloneAddress  = clone.getPostalAddress("ADDRESS");
		checkPostalAddressClone(sourceAddress, cloneAddress);
		
		// check forms of payment
		assertNotNull(clone.getFormsOfPayment());
		assertNotSame(source.getFormsOfPayment(), clone.getFormsOfPayment());
		assertEquals(source.getFormsOfPayment().size(), clone.getFormsOfPayment().size());
		
		
	}
	
}
