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

import com.trg.search.Search;

import biz.janux.payment.mock.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ApplicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class CreditCardServiceTest {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("CreditCardStorageService")
	CreditCardStorageService<SearchCriteria> creditCardService;
	
	@Autowired
	@Qualifier("CreditCardTypeService")
	CreditCardTypeService<SearchCriteria> cardTypeService;
	
	@Autowired
	CreditCardFactory creditCardFactory;
	
	@Autowired
	@Qualifier("BusinessUnitService")
	private BusinessUnitService<SearchCriteria> businessUnitService;
	
	
	@Test
	public void crudCreditCard() throws Exception{
		MDC.put("context", "testing");
		String uuid=null; 
		try {
			uuid = createCreditCard();
			loadCreditCard(uuid);
			findCreditCard(uuid);
			deleteCreditCard(uuid);
			findDeletedCreditCard(uuid);
		} catch (RuntimeException e) {
			throw e;
		}
		finally{
			/**
			 * clean the database
			 */
			try {
				getCreditCardService().deleteOrDisable(uuid);
			} catch (RuntimeException e) {
				
			}
		}
	}
	
	private void loadCreditCard(String uuid) {
		CreditCard creditCard = getCreditCardService().load(uuid,false);
		
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
		
		creditCard = getCreditCardService().load(uuid,true);
		
		Assert.assertNotNull(creditCard);
		Assert.assertEquals(CreditCardConstants.NUMBER_AMERICAN_EXPRESS,creditCard.getNumber());
		
	}

	public String createCreditCard() throws Exception{
		CreditCard creditCard = getCreditCardFactory().getCreditCardAmericanExpress();
		String uuid = getCreditCardService().saveOrUpdate(creditCard).getUuid();
		return uuid;
	}

	public void findCreditCard(String uuid) throws Exception{
		Search searchCriteria = new Search();
		searchCriteria.addFilterEqual("uuid",uuid);
		
		CreditCard creditCard = getCreditCardService().findFirstByCriteria(searchCriteria);
		
		Assert.assertNotNull(creditCard);
		assertEquals(CreditCardConstants.CREDIT_CARD_AMERICAN_EXPRESS_TOKEN,creditCard.getToken());
		assertEquals(CreditCardConstants.CARD_HOLDER_NAME,creditCard.getCardholderName());
		assertEquals(CreditCardConstants.CITY,creditCard.getBillingAddress().getCityAsString());
		assertEquals(CreditCardConstants.COUNTRY_STRING,creditCard.getBillingAddress().getCountryAsString());
		assertEquals(CreditCardConstants.STATE_STRING,creditCard.getBillingAddress().getStateProvinceAsString());
		assertEquals(CreditCardConstants.POSTAL_CODE,creditCard.getBillingAddress().getPostalCode());
		assertEquals(CreditCardConstants.ADDRESS,creditCard.getBillingAddress().getLine1());
		assertEquals(true,creditCard.isSwiped());
		assertEquals(getBusinessUnitService().BUSINESS_UNIT_CODE_BY_DEFAULT,((CreditCardImpl)creditCard).getBusinessUnit().getCode());
		assertEquals(true,creditCard.isEnabled());
		Assert.assertNull(creditCard.getNumber());
		
		creditCard = getCreditCardService().findFirstByCriteria(searchCriteria,true);
		
		Assert.assertNotNull(creditCard);
		assertEquals(CreditCardConstants.CREDIT_CARD_AMERICAN_EXPRESS_TOKEN,creditCard.getToken());
		assertEquals(CreditCardConstants.CARD_HOLDER_NAME,creditCard.getCardholderName());
		assertEquals(CreditCardConstants.CITY,creditCard.getBillingAddress().getCityAsString());
		assertEquals(CreditCardConstants.COUNTRY_STRING,creditCard.getBillingAddress().getCountryAsString());
		assertEquals(CreditCardConstants.STATE_STRING,creditCard.getBillingAddress().getStateProvinceAsString());
		assertEquals(CreditCardConstants.POSTAL_CODE,creditCard.getBillingAddress().getPostalCode());
		assertEquals(CreditCardConstants.ADDRESS,creditCard.getBillingAddress().getLine1());
		assertEquals(getBusinessUnitService().BUSINESS_UNIT_CODE_BY_DEFAULT,((CreditCardImpl)creditCard).getBusinessUnit().getCode());
		assertEquals(true,creditCard.isEnabled());
		assertEquals(true,creditCard.isSwiped());
		Assert.assertEquals(CreditCardConstants.NUMBER_AMERICAN_EXPRESS,creditCard.getNumber());
	}

	public void deleteCreditCard(String uuid) throws Exception{
		getCreditCardService().deleteOrDisable(uuid);
	}
	

	public void findDeletedCreditCard(String uuid) throws Exception{
		Search searchCriteria = new Search();
		searchCriteria.addFilterEqual("uuid",uuid);
		
		CreditCard creditCard = getCreditCardService().findFirstByCriteria(searchCriteria);
		Assert.assertNull(creditCard);	
	}

	@Test
	public void updateExistingCreditCardUsingBusinessUnitAndNumber() throws Exception{
		CreditCard creditCard = getCreditCardFactory().getCreditCardVisa();
		String uuid = getCreditCardService().saveOrUpdate(creditCard).getUuid();
		CreditCard creditCard1 = getCreditCardService().load(uuid, true);
		
		String newCity = "TheCityWasChanged";
		creditCard.setSwiped(false);
		creditCard.getBillingAddress().setCityAsString(newCity);
		uuid = getCreditCardService().saveOrUpdate(creditCard).getUuid();
		CreditCard creditCard2 = getCreditCardService().load(uuid, true);
		
		assertEquals(creditCard1.getCardholderName(), creditCard2.getCardholderName());
		assertEquals(creditCard1.getExpirationDate(), creditCard2.getExpirationDate());
		assertEquals(creditCard1.getNumber(), creditCard2.getNumber());
		assertEquals(creditCard1.getNumberMasked(), creditCard2.getNumberMasked());
		assertEquals(creditCard1.getToken(), creditCard2.getToken());
		assertEquals(creditCard1.getType(), creditCard2.getType());
		assertEquals(((CreditCardImpl)creditCard1).getCardNumberHash(), ((CreditCardImpl)creditCard2).getCardNumberHash());
		assertEquals(((CreditCardImpl)creditCard1).getBusinessUnit(), ((CreditCardImpl)creditCard2).getBusinessUnit());
		assertEquals(creditCard1.getBillingAddress().getCountryAsString(), creditCard2.getBillingAddress().getCountryAsString());
		assertEquals(creditCard1.getBillingAddress().getStateProvinceAsString(), creditCard2.getBillingAddress().getStateProvinceAsString());
		assertEquals(creditCard1.getBillingAddress().getLine1(), creditCard2.getBillingAddress().getLine1());
		assertEquals(creditCard1.getBillingAddress().getPostalCode(), creditCard2.getBillingAddress().getPostalCode());
		assertEquals(creditCard1.isEnabled(), creditCard2.isEnabled());
		assertEquals(!creditCard1.isSwiped(), creditCard2.isSwiped());
		assertEquals(newCity,creditCard2.getBillingAddress().getCityAsString());
	}
	
	@Test
	public void updateExistingCreditCard() throws Exception{
		CreditCard creditCard = getCreditCardFactory().getCreditCardVisa();
		String uuid = getCreditCardService().saveOrUpdate(creditCard).getUuid();
		CreditCard creditCard1 = getCreditCardService().load(uuid, true);
		
		String newCity = "TheCityWasChanged";
		creditCard1.getBillingAddress().setCityAsString(newCity);
		creditCard1.setSwiped(false);
		uuid = getCreditCardService().saveOrUpdate(creditCard1).getUuid();
		CreditCard creditCard2 = getCreditCardService().load(uuid, true);
		
		assertEquals(creditCard1.getCardholderName(), creditCard2.getCardholderName());
		assertEquals(creditCard1.getExpirationDate(), creditCard2.getExpirationDate());
		assertEquals(creditCard1.getNumberMasked(), creditCard2.getNumberMasked());
		assertEquals(creditCard1.getToken(), creditCard2.getToken());
		assertEquals(creditCard1.getType(), creditCard2.getType());
		assertEquals(((CreditCardImpl)creditCard1).getCardNumberHash(), ((CreditCardImpl)creditCard2).getCardNumberHash());
		assertEquals(((CreditCardImpl)creditCard1).getBusinessUnit(), ((CreditCardImpl)creditCard2).getBusinessUnit());
		assertEquals(creditCard1.getBillingAddress().getCountryAsString(), creditCard2.getBillingAddress().getCountryAsString());
		assertEquals(creditCard1.getBillingAddress().getStateProvinceAsString(), creditCard2.getBillingAddress().getStateProvinceAsString());
		assertEquals(creditCard1.getBillingAddress().getLine1(), creditCard2.getBillingAddress().getLine1());
		assertEquals(creditCard1.getBillingAddress().getPostalCode(), creditCard2.getBillingAddress().getPostalCode());
		assertEquals(creditCard1.isEnabled(), creditCard2.isEnabled());
		assertEquals(creditCard1.isSwiped(), creditCard2.isSwiped());
		assertEquals(newCity,creditCard2.getBillingAddress().getCityAsString());
	}

	public CreditCardStorageService<SearchCriteria> getCreditCardService() {
		return creditCardService;
	}

	public void setCreditCardService(CreditCardStorageService<SearchCriteria> creditCardService) {
		this.creditCardService = creditCardService;
	}

	public CreditCardTypeService<SearchCriteria> getCardTypeService() {
		return cardTypeService;
	}

	public void setCardTypeService(
			CreditCardTypeService<SearchCriteria> cardTypeService) {
		this.cardTypeService = cardTypeService;
	}

	public CreditCardFactory getCreditCardFactory() {
		return creditCardFactory;
	}

	public void setCreditCardFactory(CreditCardFactory creditCardFactory) {
		this.creditCardFactory = creditCardFactory;
	}

	public void setBusinessUnitService(BusinessUnitService<SearchCriteria> businessUnitService) {
		this.businessUnitService = businessUnitService;
	}

	public BusinessUnitService<SearchCriteria> getBusinessUnitService() {
		return businessUnitService;
	}

}


