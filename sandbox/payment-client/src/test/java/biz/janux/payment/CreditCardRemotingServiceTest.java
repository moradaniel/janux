package biz.janux.payment;

import static junit.framework.Assert.assertEquals;
import junit.framework.Assert;

import org.janux.bus.search.SearchCriteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import biz.janux.payment.CreditCard;
import biz.janux.payment.CreditCardStorageService;
import biz.janux.payment.mock.CreditCardConstants;
import biz.janux.payment.mock.CreditCardFactory;

import com.trg.search.Search;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:TestContext.xml"})
@TransactionConfiguration(transactionManager = "transcactionManager", defaultRollback = false)
public class CreditCardRemotingServiceTest {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("CreditCardRemotingService")
	CreditCardStorageService<SearchCriteria> creditCardRemotingServiceClient;
	
	@Autowired
	CreditCardFactory creditCardFactory;
	
	
	@Test
	public void crudCreditCard() throws Exception{
		MDC.put("context", "testing");
		String uuid=null; 
		try {
			uuid = createCreditCard();
			loadCreditCard(uuid);
			//findCreditCard(uuid);
			deleteCreditCard(uuid);
			//findDeletedCreditCard(uuid);
		} catch (RuntimeException e) {
			throw e;
		}
		finally{
			/**
			 * clean the database
			 */
			try {
				getCreditCardRemotingServiceClient().deleteOrDisable(uuid);
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void loadCreditCard(String uuid) {
		CreditCard creditCard = getCreditCardRemotingServiceClient().load(uuid,false);
		
		Assert.assertNotNull(creditCard);
		assertEquals(CreditCardConstants.CREDIT_CARD_AMERICAN_EXPRESS_TOKEN,creditCard.getToken());
		assertEquals(CreditCardConstants.CARD_HOLDER_NAME,creditCard.getCardholderName());
		assertEquals(CreditCardConstants.CITY,creditCard.getBillingAddress().getCityAsString());
		assertEquals(CreditCardConstants.COUNTRY_STRING,creditCard.getBillingAddress().getCountryAsString());
		assertEquals(CreditCardConstants.STATE_STRING,creditCard.getBillingAddress().getStateProvinceAsString());
		assertEquals(CreditCardConstants.POSTAL_CODE,creditCard.getBillingAddress().getPostalCode());
		assertEquals(CreditCardConstants.ADDRESS,creditCard.getBillingAddress().getLine1());
		assertEquals(true,creditCard.isEnabled());
		assertEquals(true,creditCard.isSwiped());
		Assert.assertNull(creditCard.getNumber());
		
		creditCard = getCreditCardRemotingServiceClient().load(uuid,true);
		
		Assert.assertNotNull(creditCard);
		Assert.assertEquals(CreditCardConstants.NUMBER_AMERICAN_EXPRESS,creditCard.getNumber());
		
	}

	public String createCreditCard() throws Exception{
		CreditCard creditCard = getCreditCardFactory().getCreditCardAmericanExpress();
		String uuid = getCreditCardRemotingServiceClient().saveOrUpdate(creditCard).getUuid();
		return uuid;
	}

	public void findCreditCard(String uuid) throws Exception{
		Search searchCriteria = new Search();
		searchCriteria.addFilterEqual("uuid",uuid);
		
		CreditCard creditCard = getCreditCardRemotingServiceClient().findFirstByCriteria(searchCriteria);
		
		Assert.assertNotNull(creditCard);
		assertEquals(CreditCardConstants.CREDIT_CARD_AMERICAN_EXPRESS_TOKEN,creditCard.getToken());
		assertEquals(CreditCardConstants.CARD_HOLDER_NAME,creditCard.getCardholderName());
		assertEquals(CreditCardConstants.CITY,creditCard.getBillingAddress().getCityAsString());
		assertEquals(CreditCardConstants.COUNTRY_STRING,creditCard.getBillingAddress().getCountryAsString());
		assertEquals(CreditCardConstants.STATE_STRING,creditCard.getBillingAddress().getStateProvinceAsString());
		assertEquals(CreditCardConstants.POSTAL_CODE,creditCard.getBillingAddress().getPostalCode());
		assertEquals(CreditCardConstants.ADDRESS,creditCard.getBillingAddress().getLine1());
		assertEquals(true,creditCard.isEnabled());
		Assert.assertNull(creditCard.getNumber());
		
		creditCard = getCreditCardRemotingServiceClient().findFirstByCriteria(searchCriteria,true);
		
		Assert.assertNotNull(creditCard);
		assertEquals(CreditCardConstants.CREDIT_CARD_AMERICAN_EXPRESS_TOKEN,creditCard.getToken());
		assertEquals(CreditCardConstants.CARD_HOLDER_NAME,creditCard.getCardholderName());
		assertEquals(CreditCardConstants.CITY,creditCard.getBillingAddress().getCityAsString());
		assertEquals(CreditCardConstants.COUNTRY_STRING,creditCard.getBillingAddress().getCountryAsString());
		assertEquals(CreditCardConstants.STATE_STRING,creditCard.getBillingAddress().getStateProvinceAsString());
		assertEquals(CreditCardConstants.POSTAL_CODE,creditCard.getBillingAddress().getPostalCode());
		assertEquals(CreditCardConstants.ADDRESS,creditCard.getBillingAddress().getLine1());
		assertEquals(true,creditCard.isEnabled());
		Assert.assertEquals(CreditCardConstants.NUMBER_AMERICAN_EXPRESS,creditCard.getNumber());
	}

	public void deleteCreditCard(String uuid) throws Exception{
		getCreditCardRemotingServiceClient().deleteOrDisable(uuid);
	}
	

	public void findDeletedCreditCard(String uuid) throws Exception{
		Search searchCriteria = new Search();
		searchCriteria.addFilterEqual("uuid",uuid);
		
		CreditCard creditCard = getCreditCardRemotingServiceClient().findFirstByCriteria(searchCriteria);
		Assert.assertNull(creditCard);	
	}

	public CreditCardFactory getCreditCardFactory() {
		return creditCardFactory;
	}

	public void setCreditCardFactory(CreditCardFactory creditCardFactory) {
		this.creditCardFactory = creditCardFactory;
	}

	public CreditCardStorageService<SearchCriteria> getCreditCardRemotingServiceClient() {
		return creditCardRemotingServiceClient;
	}

	public void setCreditCardRemotingServiceClient(
			CreditCardStorageService<SearchCriteria> creditCardRemotingService) {
		this.creditCardRemotingServiceClient = creditCardRemotingService;
	}

}


