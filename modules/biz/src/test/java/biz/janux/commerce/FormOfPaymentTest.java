package biz.janux.commerce;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import biz.janux.people.Party;
import biz.janux.people.PersonImpl;

public class FormOfPaymentTest extends TestCase
{
	public FormOfPaymentTest() {
		super();
	}

	public FormOfPaymentTest(String name) {
		super(name);
	}

	/** define the tests to be run in this class */
	public static Test suite() throws Exception
	{
		final TestSuite suite = new TestSuite();

		// run all tests
		suite.addTestSuite(FormOfPaymentTest.class);

		// or a subset thereoff
	//	suite.addTest(new FormOfPaymentTest("testClone"));

		return suite;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
        TestRunner.run(suite());
	}

	
	public void testCashFormOfPaymentClone()
	{
		final Party person = new PersonImpl();
		person.setCode("TEST");
		
		final CashFormOfPayment source = new CashFormOfPayment();
		source.setCurrencyCode("USD");
		source.setId(100);
		source.setParty(person);
		source.setPosition(5);
		
        final CashFormOfPayment clone = (CashFormOfPayment )source.clone();
        assertNotNull(clone);
        assertNotSame(source, clone);
        assertEquals(source.getCurrencyCode(), clone.getCurrencyCode());
        assertEquals(new Integer(-1), clone.getId());
        assertEquals(source.getPosition(), clone.getPosition());
        assertSame(source.getParty(), clone.getParty());
	}

	
	public void testCheckFormOfPaymentClone()
	{
		final Party person = new PersonImpl();
		person.setCode("TEST");
		
		final CheckFormOfPayment source = new CheckFormOfPayment();
		source.setId(100);
		source.setAccountName("Ed Smith");
		source.setAccountNumber("123456789");
		source.setAccountType("SAVINGS");
		source.setBankName("USA BANK");
		source.setRoutingNumber("987654321");
		source.setParty(person);
		source.setPosition(5);
		
        final CheckFormOfPayment clone = (CheckFormOfPayment )source.clone();
        assertNotNull(clone);
        assertNotSame(source, clone);
        assertEquals(source.getAccountName(), clone.getAccountName());
        assertEquals(source.getAccountNumber(), clone.getAccountNumber());
        assertEquals(source.getAccountType(), clone.getAccountType());
        assertEquals(source.getBankName(), clone.getBankName());
        assertEquals(source.getRoutingNumber(), clone.getRoutingNumber());
        assertEquals(new Integer(-1), clone.getId());
        assertEquals(source.getPosition(), clone.getPosition());
        assertSame(source.getParty(), clone.getParty());
	}
	
	
	public void testCreditCardFopClone()
	{
		final Party person = new PersonImpl();
		person.setCode("TEST");
		final PaymentMethod cardType = new PaymentMethodImpl();
		cardType.setCode("VISA");
		
		final CreditCardFop source = new CreditCardFop();
		source.setId(100);
		source.setCardNumber("123456789");
		source.setCardOwnerName("Ed Jones");
		source.setCardType(cardType);
		source.setExpiration(2009, 12);
		source.setSecurityCode("1234");
		source.setId(100);
		source.setParty(person);
		source.setPosition(5);
		
        final CreditCardFop clone = (CreditCardFop )source.clone();
        assertNotNull(clone);
        assertNotSame(source, clone);
        assertEquals(source.getCardNumber(), clone.getCardNumber());
        assertEquals(source.getCardOwnerName(), clone.getCardOwnerName());
        assertEquals(source.getCardType(), clone.getCardType());
        assertEquals(source.getExpiration(), clone.getExpiration());
        assertEquals(source.getSecurityCode(), clone.getSecurityCode());
        assertEquals(new Integer(-1), clone.getId());
        assertEquals(source.getPosition(), clone.getPosition());
        assertSame(source.getParty(), clone.getParty());
	}
	
	
	public void testCreditCardExpiration()
	{
		final Party person = new PersonImpl();
		person.setCode("TEST");
		
		final PaymentMethod cardType = new PaymentMethodImpl();
		cardType.setCode("VISA");
		final CreditCardFop card = new CreditCardFop();
		card.setId(100);
		card.setCardNumber("123456789");
		card.setCardOwnerName("Ed Jones");
		card.setCardType(cardType);
		card.setExpiration(2050, 12);
		card.setSecurityCode("1234");
		card.setId(100);
		card.setParty(person);
		card.setPosition(5);
		assertFalse(card.isExpired());
		
		card.setExpiration(2006, 2);
		assertTrue(card.isExpired());
	}
	
	
	public void testDepositMethodClone()
	{
		final DepositMethodImpl source = new DepositMethodImpl();
		source.setCode("DEP");
		source.setDescription("Deposit");
		source.setId(100);
		
        final DepositMethodImpl clone = (DepositMethodImpl )source.clone();
        assertNotNull(clone);
        assertNotSame(source, clone);
        assertEquals(source.getCode(), clone.getCode());
        assertEquals(source.getDescription(), clone.getDescription());
        assertEquals(new Integer(-1), clone.getId());
	}

	public void testGuaranteeMethodClone()
	{
		final GuaranteeMethodImpl source = new GuaranteeMethodImpl();
		source.setCode("DEP");
		source.setDescription("Deposit");
		source.setId(100);
		
        final GuaranteeMethodImpl clone = (GuaranteeMethodImpl )source.clone();
        assertNotNull(clone);
        assertNotSame(source, clone);
        assertEquals(source.getCode(), clone.getCode());
        assertEquals(source.getDescription(), clone.getDescription());
        assertEquals(new Integer(-1), clone.getId());
	}

	
	
}
