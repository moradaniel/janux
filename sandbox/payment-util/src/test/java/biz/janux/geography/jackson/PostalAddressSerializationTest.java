package biz.janux.geography.jackson;

import biz.janux.geography.*;

import org.janux.bus.processor.jackson.JanuxObjectMapper;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 ***************************************************************************************************
 * Tests the Jackson Serialization and Deserialization mappers
 *
 * @author <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.4.0 - 20111121
 ***************************************************************************************************
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ApplicationContext.xml" })
public class PostalAddressSerializationTest 
{
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JanuxObjectMapper objectMapper;

	private PostalAddress postalAddress;

	//static Logger log = LoggerFactory.getLogger(SerializationSetup.class);

	/** define the tests to be run in this class */
	/*
	public static Test suite()
	{
		TestSuite suite = new TestSuite();

		// run all tests
		suite = new TestSuite(PostalAddressSerializationTest.class);

		// or a subset thereoff
		// suite.addTest(new PostalAddressSerializationTest("testSerialization1"));

		return suite;

		// or
		// TestSetup wrapper= new SomeWrapper(suite);
		// return wrapper;
	}
	*/

	/*

	@After
	public void tearDown() {
		emptyList = null;
	}
	*/

	@Before
	public void setUp() 
	{
		this.postalAddress = new PostalAddressImpl();
		this.postalAddress.setLine1("123 Main Street");
		this.postalAddress.setLine2("Annex C");
		this.postalAddress.setLine3("Suite 104");
		this.postalAddress.setPostalCode("94952");
		this.postalAddress.setCityAsString("Albuquerque");
		this.postalAddress.setStateProvinceAsString("CA");
		this.postalAddress.setCountryAsString("US");
	}
	
	@Test
	public void testPostalAddressToJson() throws java.io.IOException
	{
		String address_json = this.objectMapper.writeValueAsString(this.postalAddress);
		log.debug("PostalAddress in json format: {}", address_json);
	}

	@Test
	public void testJsonParsing() throws java.io.IOException
	{
		String address_json = this.objectMapper.writeValueAsString(this.postalAddress);
		PostalAddressImpl address = (PostalAddressImpl)(this.objectMapper.readValue(address_json, PostalAddress.class));
		log.debug("PostalAddress parsed: {}", address);
		assertEquals(this.postalAddress.getLine1(), address.getLine1());
		assertEquals(this.postalAddress.getLine2(), address.getLine2());
		assertEquals(this.postalAddress.getLine3(), address.getLine3());
		assertEquals(this.postalAddress.getPostalCode(),            address.getPostalCode());
		assertEquals(this.postalAddress.getCityAsString(),          address.getCityAsString());
		assertEquals(this.postalAddress.getStateProvinceAsString(), address.getStateProvinceAsString());
		assertEquals(this.postalAddress.getCountryAsString(),       address.getCountryAsString());
	}
}
