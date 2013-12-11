package biz.janux.payment;

import static junit.framework.Assert.assertEquals;

import java.text.SimpleDateFormat;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import biz.janux.payment.mock.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:TestContext.xml" })
@TransactionConfiguration(transactionManager = "transcactionManager", defaultRollback = false)
public class CreditCardControllerRestTest {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PaymentGatewayRestClient paymentGatewayRestClient;
	
	@Autowired
	private CreditCardFactory creditCardFactory;
	
	SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
	
	@Test
	public void crudNewCreditCard() throws Exception{
		MDC.put("context", "testing");
		String uuid=null; 
		try {
			uuid = saveNewCreditCard();
			loadCreditCard(uuid);
			deleteCreditCard(uuid);
		} catch (RuntimeException e) {
			throw e;
		}
		finally{
			/**
			 * clean the database
			 */
		}
	}
	
	@Test
	public void updateCreditCard() throws Exception{
		MDC.put("context", "testing");
		String uuid=null; 
		try {
			uuid = saveNewCompleteCreditCard();
			updateCreditCard(uuid);
		} catch (RuntimeException e) {
			throw e;
		}
		finally{
			/**
			 * clean the database
			 */
		}
	}
	
	public String updateCreditCard(String uuid) throws Exception{
		CreditCard creditCard = getPaymentGatewayRestClient().loadCreditCard(uuid);
		creditCard.getBillingAddress().setCityAsString("theCityWasChanged");
		CreditCard creditCardSaved = getPaymentGatewayRestClient().updateCreditCard(uuid,creditCard);

		Assert.hasText(creditCardSaved.getUuid());
		assertEquals(creditCard.getUuid(), creditCardSaved.getUuid());
		assertEquals(creditCard.getNumberMasked(),creditCardSaved.getNumberMasked());
		assertEquals(formatter.format(creditCard.getExpirationDate()),formatter.format(creditCardSaved.getExpirationDate()));
		assertEquals(creditCard.getType().getCode(),creditCardSaved.getType().getCode());
		assertEquals(creditCard.getCardholderName(),creditCardSaved.getCardholderName());
		assertEquals(creditCard.getBillingAddress().getCityAsString(),creditCardSaved.getBillingAddress().getCityAsString());
		assertEquals(creditCard.getBillingAddress().getCountryAsString(),creditCardSaved.getBillingAddress().getCountryAsString());
		assertEquals(creditCard.getBillingAddress().getStateProvinceAsString(),creditCardSaved.getBillingAddress().getStateProvinceAsString());
				
		return uuid;
	}
	
	public String saveNewCreditCard() throws Exception{
		CreditCard creditCard = new CreditCardImpl();
		creditCard.setNumber(CreditCardConstants.NUMBER_VISA);
		creditCard.setExpirationDate(formatter.parse(CreditCardConstants.EXPIRATION));
		CreditCardType creditCardType = new CreditCardTypeImpl();
		creditCardType.setCode(CreditCardConstants.TYPE_VISA_CC);
		creditCard.setType(creditCardType);
		creditCard.setCardholderName(CreditCardConstants.CARD_HOLDER_NAME);
		
		CreditCard creditCardSaved = getPaymentGatewayRestClient().saveCreditCard(creditCard);
		String uuid = creditCardSaved.getUuid();
		Assert.hasText(uuid);
		assertEquals(CreditCardConstants.NUMBER_VISA_MASKED,creditCardSaved.getNumberMasked());
		return uuid;
	}
	
	public String saveNewCompleteCreditCard() throws Exception{
		CreditCard creditCard = getCreditCardFactory().getCreditCardVisa();
		CreditCard creditCardSaved = getPaymentGatewayRestClient().saveCreditCard(creditCard);
		String uuid = creditCardSaved.getUuid();
		Assert.hasText(uuid);
		return uuid;
	}

	public void loadCreditCard(String uuid) throws Exception{
		CreditCard creditCard = getPaymentGatewayRestClient().loadCreditCard(uuid);
		Assert.hasText(creditCard.getUuid());
		assertEquals(uuid,creditCard.getUuid());
		assertEquals(CreditCardConstants.CARD_HOLDER_NAME,creditCard.getCardholderName());
		assertEquals(CreditCardConstants.EXPIRATION,formatter.format(creditCard.getExpirationDate()));
		assertEquals(CreditCardConstants.TYPE_VISA_CC,creditCard.getType().getCode());
		/*assertEquals(CreditCardConstants.CITY,jsonObject.getString("billingAddressCity"));
		assertEquals(CreditCardConstants.COUNTRY,jsonObject.getString("billingAddressCountry"));
		assertEquals(CreditCardConstants.STATE,jsonObject.getString("billingAddressStateProvince"));
		assertEquals(CreditCardConstants.POSTAL_CODE,jsonObject.getString("billingAddressPostalCode"));
		assertEquals(CreditCardConstants.ADDRESS,jsonObject.getString("billingAddressLine1"));*/
		assertEquals(CreditCardConstants.NUMBER_VISA_MASKED,creditCard.getNumberMasked());
	}

	public void deleteCreditCard(String uuid) throws Exception{
		JSONObject jsonObject = getPaymentGatewayRestClient().deleteCreditCard(uuid);
		assertEquals(true,jsonObject.getBoolean("deleteOrDisable"));
	}

	public PaymentGatewayRestClient getPaymentGatewayRestClient() {
		return paymentGatewayRestClient;
	}

	public void setPaymentGatewayRestClient(
			PaymentGatewayRestClient paymentGatewayRestClient) {
		this.paymentGatewayRestClient = paymentGatewayRestClient;
	}

	public void setCreditCardFactory(CreditCardFactory creditCardFactory) {
		this.creditCardFactory = creditCardFactory;
	}

	public CreditCardFactory getCreditCardFactory() {
		return creditCardFactory;
	}

}


