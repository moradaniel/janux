package biz.janux.payment.jackson;

import biz.janux.geography.PostalAddress;
import biz.janux.payment.CreditCard;
import biz.janux.payment.CreditCardImpl;
import biz.janux.payment.mock.*;

import org.janux.bus.processor.jackson.JanuxObjectMapper;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
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
public class CreditCardSerializationTest 
{
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JanuxObjectMapper objectMapper;

	@Autowired
	private CreditCardFactory creditCardFactory;

	private CreditCard cc;


	@Before
	public void setUp() {
		this.cc = this.creditCardFactory.getCreditCardVisa();
		// log.debug("Instantiated cc: {}", this.cc);
	}

	/*
	@After
	public void tearDown() {
		emptyList = null;
	}
	*/
	
	@Test
	public void testCreditCardOut() throws java.io.IOException
	{
		String cc_string = this.objectMapper.writeValueAsString(this.cc);
		log.debug(cc_string);
	}

	@Test
	public void testJsonParsing() throws java.io.IOException
	{
		String cc_json = this.objectMapper.writeValueAsString(this.cc);
		CreditCardImpl creditCard = (CreditCardImpl)this.objectMapper.readValue(cc_json, CreditCard.class);
		log.debug("CreditCard parsed: {}", creditCard);


		assertEquals(this.cc.getNumber(),         creditCard.getNumber());
		assertEquals(this.cc.getExpirationDate(), creditCard.getExpirationDate());
		assertEquals(this.cc.getTypeCode(),       creditCard.getTypeCode());
		assertEquals(this.cc.getCardholderName(), creditCard.getCardholderName());

		PostalAddress address = creditCard.getBillingAddress();

		assertEquals(this.cc.getBillingAddress().getLine1(), address.getLine1());
		assertEquals(this.cc.getBillingAddress().getLine2(), address.getLine2());
		assertEquals(this.cc.getBillingAddress().getLine3(), address.getLine3());
		assertEquals(this.cc.getBillingAddress().getPostalCode(),            address.getPostalCode());
		assertEquals(this.cc.getBillingAddress().getCityAsString(),          address.getCityAsString());
		assertEquals(this.cc.getBillingAddress().getStateProvinceAsString(), address.getStateProvinceAsString());
		assertEquals(this.cc.getBillingAddress().getCountryAsString(),       address.getCountryAsString());
	}
}
