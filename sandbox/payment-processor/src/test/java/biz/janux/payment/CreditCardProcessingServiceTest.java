package biz.janux.payment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.janux.bus.search.SearchCriteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.trg.search.Search;

import biz.janux.payment.mock.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ApplicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class CreditCardProcessingServiceTest {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CreditCardProcessingService creditCardProcessingService;

	@Autowired
	BusinessUnitFactory merchantAccountFactory;

	@Autowired
	CreditCardFactory creditCardFactory;

	@Autowired
	@Qualifier("CreditCardStorageService")
	CreditCardStorageService<SearchCriteria> creditCardService;

	@Autowired
	MerchantAccountService<SearchCriteria> merchantAccountService;
	
	@Autowired
	@Qualifier("BusinessUnitService")
	BusinessUnitService<SearchCriteria> businessUnitService;

	@Autowired
	AuthorizationHotelService<AuthorizationHotel> authorizationService;
	
	@Autowired
	SettlementItemHotelService<SettlementItemHotel> settlementItemService;
	
	@Autowired
	SettlementService<Settlement> settlementService;

	@Autowired
	private AuthorizationHotelDao<AuthorizationHotel, Serializable, SearchCriteria, AuthorizationFacet> authorizationDao;
	
	@Autowired
	private SettlementItemHotelDao<SettlementItemHotel, Serializable, SearchCriteria, SettlementItemFacet> settlementItemDao;
	
	@Autowired
	private SettlementDao<Settlement, Serializable, SearchCriteria, SettlementFacet> settlementDao;
	
	@Autowired
	private BatchNumberDao<BatchNumber, Serializable, SearchCriteria> batchNumberDao;

	@Test
	public void authorizeApprovedAuth() throws Exception {
		log.debug("testing do a cc approved authorization");

		BusinessUnit businessUnit = null;
		MerchantAccount merchantAccount = null;
		CreditCard creditCard = null;
		AuthorizationHotel authorization = null;

		try {

			businessUnit =  getMerchantAccountFactory().getVitalBusinessUnit();
			merchantAccount = businessUnit.getMerchantAccount();

			merchantAccount = getMerchantAccountService().save(merchantAccount);
			businessUnit = getBusinessUnitService().save(businessUnit);
			
			creditCard = getCreditCardFactory().getCreditCardVisa();
			creditCard = getCreditCardService().saveOrUpdate(creditCard,businessUnit.getCode());

			String externalSourceId = "pms_folio_1";
			
			BigDecimal authAmount = new BigDecimal("19.20");
			Integer stayDuration=1;
			authorization = getCreditCardProcessingService().authorize(businessUnit.getUuid(), creditCard.getUuid(), authAmount, stayDuration, externalSourceId);

			String uuid = authorization.getUuid();
			Search searchCriteria = new Search();
			searchCriteria.addFilterEqual("uuid", uuid);

			authorization = getAuthorizationService().find(searchCriteria);
			authorization = getAuthorizationService().load(authorization);

			Assert.assertEquals(true, authorization.getTransactionResponse().isApproved());
			Assert.assertEquals(true, authorization.isEnabled());
			Assert.assertEquals(authAmount, authorization.getAmount());
			Assert.assertEquals(creditCard.getUuid(), authorization.getCreditCard().getUuid());
			Assert.assertEquals(businessUnit.getUuid(), authorization.getBusinessUnit().getUuid());
			Assert.assertEquals(merchantAccount.getUuid(), authorization.getBusinessUnit().getMerchantAccount().getUuid());
			Assert.assertEquals(1,authorization.getModifications().size());
			Assert.assertEquals("19.20",authorization.getModifications().get(0).getAmount().toString());

		} catch (RuntimeException e) {
			throw e;
		} finally {
			/**
			 * Clean the database
			 */
			if (authorization != null)
				getAuthorizationDao().delete(authorization);

			if (creditCard != null)
				getCreditCardService().deleteOrDisable(creditCard);

			if (businessUnit != null)
				getBusinessUnitService().delete(businessUnit);

			if (merchantAccount != null)
				getMerchantAccountService().delete(merchantAccount);

		}
	}

	//@Test
	public void authorizeDeclinedAuthErrorContentTypeResponse() throws Exception {
		log.debug("testing do a cc declined authorization");

		BusinessUnit businessUnit = null;
		MerchantAccount merchantAccount = null;
		CreditCard creditCard = null;
		Authorization authorization = null;

		try {
			businessUnit =  getMerchantAccountFactory().getVitalBusinessUnit();
			merchantAccount = businessUnit.getMerchantAccount();
			merchantAccount.setAcquiringBankBin("1");

			merchantAccount = getMerchantAccountService().save(merchantAccount);
			businessUnit = getBusinessUnitService().save(businessUnit);

			creditCard = getCreditCardFactory().getCreditCardVisa();
			creditCard.setNumber("1234565891011121");
			creditCard = getCreditCardService().saveOrUpdate(creditCard,businessUnit.getCode());

			BigDecimal authAmount = new BigDecimal("19.20");
			Integer stayDuration=1;
			String externalSourceId = "pms_folio_1";
			authorization = getCreditCardProcessingService().authorize(businessUnit.getUuid(), creditCard.getUuid(), authAmount,stayDuration, externalSourceId);

			Assert.assertEquals(false, authorization.getTransactionResponse().isApproved());
		} catch (RuntimeException e) {
			log.error("",e);
			throw e;
		} finally {
			/**
			 * Clean the database
			 */
			if (businessUnit != null)
				getBusinessUnitService().delete(businessUnit);

			if (merchantAccount != null)
				getMerchantAccountService().delete(merchantAccount);

			if (creditCard != null)
				getCreditCardService().deleteOrDisable(creditCard);
		}

	}

	//@Test
	public void authorizeDeclinedAuth() throws Exception {
		log.debug("testing do a cc declined authorization");

		BusinessUnit businessUnit = null;
		MerchantAccount merchantAccount = null;
		CreditCard creditCard = null;
		Authorization authorization = null;

		try {

			businessUnit =  getMerchantAccountFactory().getVitalBusinessUnit();
			merchantAccount = businessUnit.getMerchantAccount();

			merchantAccount = getMerchantAccountService().save(merchantAccount);
			businessUnit = getBusinessUnitService().save(businessUnit);
			
			creditCard = getCreditCardFactory().getCreditCardVisa();
			creditCard = getCreditCardService().saveOrUpdate(creditCard,businessUnit.getCode());

			BigDecimal authAmount = new BigDecimal("0");
			Integer stayDuration=1;
			String externalSourceId = "pms_folio_1";
			authorization = getCreditCardProcessingService().authorize(businessUnit.getUuid(), creditCard.getUuid(), authAmount,stayDuration, externalSourceId);
			Assert.assertEquals(false, authorization.getTransactionResponse().isApproved());

		} catch (RuntimeException e) {
			log.error("",e);
			throw e;
		} finally {
			/**
			 * Clean the database
			 */
			if (businessUnit != null)
				getBusinessUnitService().delete(businessUnit);

			if (merchantAccount != null)
				getMerchantAccountService().delete(merchantAccount);

			if (creditCard != null)
				getCreditCardService().deleteOrDisable(creditCard);
		}
	}

	@Test
	public void authorizeApprovedIncrementalAuth() throws Exception {
		log.debug("testing do a cc approved incremental authorization");

		BusinessUnit businessUnit = null;
		MerchantAccount merchantAccount = null;
		CreditCard creditCard = null;
		AuthorizationHotel authorization = null;

		try {

			businessUnit =  getMerchantAccountFactory().getVitalBusinessUnit();
			merchantAccount = businessUnit.getMerchantAccount();

			merchantAccount = getMerchantAccountService().save(merchantAccount);
			businessUnit = getBusinessUnitService().save(businessUnit);
			
			creditCard = getCreditCardFactory().getCreditCardVisa();
			creditCard = getCreditCardService().saveOrUpdate(creditCard,businessUnit.getCode());

			BigDecimal authAmount = new BigDecimal("19.20");
			
			Integer stayDuration=1;
			String externalSourceId = "pms_folioLine_1";
			authorization = getCreditCardProcessingService().authorize(businessUnit.getUuid(), creditCard.getUuid(), authAmount,stayDuration, externalSourceId);

			String uuid = authorization.getUuid();
			Search searchCriteria = new Search();
			searchCriteria.addFilterEqual("uuid", uuid);

			authorization = getAuthorizationService().find(searchCriteria);
			authorization = getAuthorizationService().load(authorization);

			BigDecimal authIncrementAmount = new BigDecimal("7.40");
			AuthorizationHotel incrementalAuthorization = getCreditCardProcessingService().increment(authorization.getUuid(), authIncrementAmount);

			incrementalAuthorization = getAuthorizationService().find(searchCriteria);
			incrementalAuthorization = getAuthorizationService().load(incrementalAuthorization);
			
			authorization = getAuthorizationService().load(authorization);
			
			Assert.assertEquals(true, incrementalAuthorization.getTransactionResponse().isApproved());
			Assert.assertEquals(true, incrementalAuthorization.isEnabled());
			Assert.assertEquals((authAmount.add(authIncrementAmount)), incrementalAuthorization.getAmount());
			Assert.assertEquals(creditCard.getUuid(), authorization.getCreditCard().getUuid());
			Assert.assertEquals(businessUnit.getUuid(), authorization.getBusinessUnit().getUuid());
			Assert.assertEquals(merchantAccount.getUuid(), authorization.getBusinessUnit().getMerchantAccount().getUuid());
			Assert.assertEquals(2,incrementalAuthorization.getModifications().size());
			Assert.assertEquals(authAmount,incrementalAuthorization.getModifications().get(0).getAmount());
			Assert.assertEquals(authIncrementAmount,incrementalAuthorization.getModifications().get(1).getAmount());

		} catch (RuntimeException e) {
			log.error("",e);
			throw e;
		} finally {
			/**
			 * Clean the database
			 */
			if (authorization != null)
				authorization = getAuthorizationService().load(authorization);
				getAuthorizationDao().delete(authorization);

			if (creditCard != null)
				getCreditCardService().deleteOrDisable(creditCard);

			if (businessUnit != null)
				getBusinessUnitService().delete(businessUnit);

			if (merchantAccount != null)
				getMerchantAccountService().delete(merchantAccount);

		}
	}
	
	@Test
	public void authorizeApprovedReversalAuth() throws Exception {
		log.debug("testing do a cc approved reversal authorization");

		BusinessUnit businessUnit = null;
		MerchantAccount merchantAccount = null;
		CreditCard creditCard = null;
		AuthorizationHotel authorization = null;

		try {

			businessUnit =  getMerchantAccountFactory().getVitalBusinessUnit();
			merchantAccount = businessUnit.getMerchantAccount();

			merchantAccount = getMerchantAccountService().save(merchantAccount);
			businessUnit = getBusinessUnitService().save(businessUnit);
			
			creditCard = getCreditCardFactory().getCreditCardVisa();
			creditCard = getCreditCardService().saveOrUpdate(creditCard,businessUnit.getCode());

			BigDecimal authAmount = new BigDecimal("19.20");

			Integer stayDuration=1;
			String externalSourceId = "pms_folio_1";
			authorization = getCreditCardProcessingService().authorize(businessUnit.getUuid(), creditCard.getUuid(), authAmount,stayDuration, externalSourceId);

			String uuid = authorization.getUuid();
			Search searchCriteria = new Search();
			searchCriteria.addFilterEqual("uuid", uuid);

			authorization = getAuthorizationService().find(searchCriteria);
			authorization = getAuthorizationService().load(authorization);

			BigDecimal authReverseAmount = new BigDecimal("-7.40");
			AuthorizationHotel reversalAuthorization = getCreditCardProcessingService().reverse(authorization.getUuid(), authReverseAmount);

			reversalAuthorization = getAuthorizationService().find(searchCriteria);
			reversalAuthorization = getAuthorizationService().load(reversalAuthorization);
			
			authorization = getAuthorizationService().load(authorization);
			
			Assert.assertEquals(true, reversalAuthorization.getTransactionResponse().isApproved());
			Assert.assertEquals(true, reversalAuthorization.isEnabled());
			Assert.assertEquals((authAmount.add(authReverseAmount)), reversalAuthorization.getAmount());
			Assert.assertEquals(creditCard.getUuid(), authorization.getCreditCard().getUuid());
			Assert.assertEquals(businessUnit.getUuid(), authorization.getBusinessUnit().getUuid());
			Assert.assertEquals(merchantAccount.getUuid(), authorization.getBusinessUnit().getMerchantAccount().getUuid());
			Assert.assertEquals(2,reversalAuthorization.getModifications().size());
			Assert.assertEquals(authAmount,reversalAuthorization.getModifications().get(0).getAmount());
			Assert.assertEquals(authReverseAmount,reversalAuthorization.getModifications().get(1).getAmount());

		} catch (RuntimeException e) {
			log.error("",e);
			throw e;
		} finally {
			/**
			 * Clean the database
			 */
			if (authorization != null)
			{
				authorization = getAuthorizationService().load(authorization);
				getAuthorizationDao().delete(authorization);
			}

			if (creditCard != null)
				getCreditCardService().deleteOrDisable(creditCard);

			if (businessUnit != null)
				getBusinessUnitService().delete(businessUnit);

			if (merchantAccount != null)
				getMerchantAccountService().delete(merchantAccount);

		}
	}
	
	//@Test
	public void authorizeDeclinedReversalAuth() throws Exception {
		log.debug("testing do a cc declined reversal authorization");

		BusinessUnit businessUnit = null;
		MerchantAccount merchantAccount = null;
		CreditCard creditCard = null;
		AuthorizationHotel authorization = null;

		try {

			businessUnit =  getMerchantAccountFactory().getVitalBusinessUnit();
			merchantAccount = businessUnit.getMerchantAccount();

			merchantAccount = getMerchantAccountService().save(merchantAccount);
			businessUnit = getBusinessUnitService().save(businessUnit);
			
			creditCard = getCreditCardFactory().getCreditCardAmericanExpress();
			creditCard = getCreditCardService().saveOrUpdate(creditCard,businessUnit.getCode());

			BigDecimal authAmount = new BigDecimal("19.20");
			Integer stayDuration=1;
			String externalSourceId = "pms_folioLine_1";
			authorization = getCreditCardProcessingService().authorize(businessUnit.getUuid(), creditCard.getUuid(), authAmount,stayDuration, externalSourceId);
			Assert.assertEquals(false, authorization.getTransactionResponse().isApproved());
			
		} catch (RuntimeException e) {
			log.error("",e);
			throw e;
		} finally {
			/**
			 * Clean the database
			 */

			if (creditCard != null)
				getCreditCardService().deleteOrDisable(creditCard);

			if (businessUnit != null)
				getBusinessUnitService().delete(businessUnit);

			if (merchantAccount != null)
				getMerchantAccountService().delete(merchantAccount);

		}
	}
	
	@Test
	public void voidAuthorization() throws Exception {
		log.debug("testing do a cc void authorization");

		BusinessUnit businessUnit = null;
		MerchantAccount merchantAccount = null;
		CreditCard creditCard = null;
		AuthorizationHotel authorization = null;

		try {

			businessUnit =  getMerchantAccountFactory().getVitalBusinessUnit();
			merchantAccount = businessUnit.getMerchantAccount();

			merchantAccount = getMerchantAccountService().save(merchantAccount);
			businessUnit = getBusinessUnitService().save(businessUnit);
			
			creditCard = getCreditCardFactory().getCreditCardVisa();
			creditCard = getCreditCardService().saveOrUpdate(creditCard,businessUnit.getCode());

			BigDecimal authAmount = new BigDecimal("19.20");

			Integer stayDuration=1;
			String externalSourceId = "pms_folio_1";
			authorization = getCreditCardProcessingService().authorize(businessUnit.getUuid(), creditCard.getUuid(), authAmount,stayDuration, externalSourceId);

			String uuid = authorization.getUuid();
			Search searchCriteria = new Search();
			searchCriteria.addFilterEqual("uuid", uuid);

			authorization = getAuthorizationService().find(searchCriteria);
			authorization = getAuthorizationService().load(authorization);

			Assert.assertEquals(true, authorization.getTransactionResponse().isApproved());
			Assert.assertEquals(true, authorization.isEnabled());
			Assert.assertEquals(authAmount, authorization.getAmount());
			Assert.assertEquals(creditCard.getUuid(), authorization.getCreditCard().getUuid());
			Assert.assertEquals(businessUnit.getUuid(), authorization.getBusinessUnit().getUuid());
			Assert.assertEquals(merchantAccount.getUuid(), authorization.getBusinessUnit().getMerchantAccount().getUuid());
			Assert.assertEquals(1,authorization.getModifications().size());
			Assert.assertEquals("19.20",authorization.getModifications().get(0).getAmount().toString());

			getCreditCardProcessingService().voidAuthorization(authorization.getUuid());

			authorization = getAuthorizationService().find(searchCriteria);

			Assert.assertEquals(false, authorization.isEnabled());

			
		} catch (RuntimeException e) {
			log.error("",e);
			throw e;
		} finally {
			/**
			 * Clean the database
			 */
			if (authorization != null)
			{
				authorization = getAuthorizationService().load(authorization);
				getAuthorizationDao().delete(authorization);
			}

			if (creditCard != null)
				getCreditCardService().deleteOrDisable(creditCard);

			if (businessUnit != null)
				getBusinessUnitService().delete(businessUnit);

			if (merchantAccount != null)
				getMerchantAccountService().delete(merchantAccount);

		}
	}
	
	@Test
	public void authorizeOffline() throws Exception {
		log.debug("testing do a cc offline authorization");

		BusinessUnit businessUnit = null;
		MerchantAccount merchantAccount = null;
		CreditCard creditCard = null;
		AuthorizationHotel authorization = null;

		try {

			businessUnit =  getMerchantAccountFactory().getVitalBusinessUnit();
			merchantAccount = businessUnit.getMerchantAccount();

			merchantAccount = getMerchantAccountService().save(merchantAccount);
			businessUnit = getBusinessUnitService().save(businessUnit);
			
			creditCard = getCreditCardFactory().getCreditCardVisa();
			creditCard = getCreditCardService().saveOrUpdate(creditCard,businessUnit.getCode());

			BigDecimal authAmount = new BigDecimal("19.20");
			String approvalCode = "09282C";
			Integer stayDuration = 1;
			String externalSourceId = "pms_folio_1";
			authorization = getCreditCardProcessingService().authorizeOffline(businessUnit.getUuid(), creditCard.getUuid(), authAmount,stayDuration,approvalCode, externalSourceId);

			String uuid = authorization.getUuid();
			Search searchCriteria = new Search();
			searchCriteria.addFilterEqual("uuid", uuid);

			authorization = getAuthorizationService().find(searchCriteria);
			authorization = getAuthorizationService().load(authorization);

			Assert.assertEquals(true, authorization.getTransactionResponse().isApproved());
			Assert.assertEquals(true, authorization.getTransactionResponse().isOffline());
			Assert.assertEquals(true, authorization.isEnabled());
			Assert.assertEquals(authAmount, authorization.getAmount());
			Assert.assertEquals(creditCard.getUuid(), authorization.getCreditCard().getUuid());
			Assert.assertEquals(businessUnit.getUuid(), authorization.getBusinessUnit().getUuid());
			Assert.assertEquals(merchantAccount.getUuid(), authorization.getBusinessUnit().getMerchantAccount().getUuid());
			Assert.assertEquals(1,authorization.getModifications().size());
			Assert.assertEquals("19.20",authorization.getModifications().get(0).getAmount().toString());

		} catch (RuntimeException e) {
			log.error("",e);
			throw e;
		} finally {
			/**
			 * Clean the database
			 */
			if (authorization != null)
			{
				authorization = getAuthorizationService().load(authorization);
				getAuthorizationDao().delete(authorization);
			}

			if (creditCard != null)
				getCreditCardService().deleteOrDisable(creditCard);

			if (businessUnit != null)
				getBusinessUnitService().delete(businessUnit);

			if (merchantAccount != null)
				getMerchantAccountService().delete(merchantAccount);

		}
	}

	@Test
	public void applyPaymentPurchasePreviousAuthorization() throws Exception {
		log.debug("testing do a applay payment purchase");

		BusinessUnit businessUnit = null;
		MerchantAccount merchantAccount = null;
		CreditCard creditCard = null;
		AuthorizationHotel authorization = null;
		SettlementItemHotel settlementItem = null;

		try {

			businessUnit =  getMerchantAccountFactory().getVitalBusinessUnit();
			merchantAccount = businessUnit.getMerchantAccount();

			merchantAccount = getMerchantAccountService().save(merchantAccount);
			businessUnit = getBusinessUnitService().save(businessUnit);

			creditCard = getCreditCardFactory().getCreditCardVisa();
			creditCard = getCreditCardService().saveOrUpdate(creditCard,businessUnit.getCode());

			BigDecimal authAmount = new BigDecimal("19.20");
			
			Integer stayDuration=1;
			String externalSourceId = "pms_folio_1";
			authorization = getCreditCardProcessingService().authorize(businessUnit.getUuid(), creditCard.getUuid(), authAmount,stayDuration, externalSourceId);

			BigDecimal authAmountPayment = new BigDecimal("15.30");

			settlementItem = getCreditCardProcessingService().applyPayment(authAmountPayment,new Date(),creditCard.getUuid(),businessUnit.getUuid(),"folioNumber",new BigDecimal("1"),new Date(),stayDuration, externalSourceId, externalSourceId);
			settlementItem = getSettlementItemService().load(settlementItem);

			Assert.assertEquals(authAmountPayment, settlementItem.getAmount());
			Assert.assertEquals(authorization.getUuid(), settlementItem.getAuthorization().getUuid());
			Assert.assertEquals(authorization.getExternalSourceId(), settlementItem.getAuthorization().getExternalSourceId());
			Assert.assertNotNull(settlementItem.getBusinessUnit());
			Assert.assertNotNull(settlementItem.getCreditCard());
			Assert.assertEquals(true,settlementItem.getAuthorization().isBatched());
			Assert.assertEquals(SettlementItemType.PURCHASE,settlementItem.getType());
		} catch (RuntimeException e) {
			log.error("",e);
			throw e;
		} finally {
			/**
			 * Clean the database
			 */
			if (settlementItem != null)
				getSettlementItemDao().delete(settlementItem);
			
			authorization = getAuthorizationService().load(authorization);
			
			if (authorization != null)
			{
				authorization = getAuthorizationService().load(authorization);
				getAuthorizationDao().delete(authorization);
			}

			if (creditCard != null)
				getCreditCardService().deleteOrDisable(creditCard);

			if (businessUnit != null)
				getBusinessUnitService().delete(businessUnit);

			if (merchantAccount != null)
				getMerchantAccountService().delete(merchantAccount);

		}
	}
	
	@Test
	public void applyPaymentPurchaseWithOutAuthorization() throws Exception {
		log.debug("testing do a applay payment purchase");
		
		BusinessUnit businessUnit = null;
		MerchantAccount merchantAccount = null;
		CreditCard creditCard = null;
		SettlementItemHotel settlementItem = null;

		try {

			businessUnit =  getMerchantAccountFactory().getVitalBusinessUnit();
			merchantAccount = businessUnit.getMerchantAccount();

			merchantAccount = getMerchantAccountService().save(merchantAccount);
			businessUnit = getBusinessUnitService().save(businessUnit);

			creditCard = getCreditCardFactory().getCreditCardVisa();
			creditCard = getCreditCardService().saveOrUpdate(creditCard,businessUnit.getCode());

			BigDecimal authAmountPayment = new BigDecimal("15.30");
			Integer stayDuration = 1;
			String externalSourceId = "pms_folioLine_1";
			String externalSourceIdAuthorization = "pms_folio_1";
			settlementItem = getCreditCardProcessingService().applyPayment(authAmountPayment,new Date(),creditCard.getUuid(),businessUnit.getUuid(),"folioNumber",new BigDecimal("1"),new Date(),stayDuration, externalSourceIdAuthorization, externalSourceId);
			settlementItem = getSettlementItemService().load(settlementItem);

			Assert.assertEquals(authAmountPayment, settlementItem.getAmount());
			Assert.assertNotNull(settlementItem.getAuthorization());
			Assert.assertEquals(authAmountPayment, settlementItem.getAuthorization().getAmount());
			Assert.assertNotNull(settlementItem.getBusinessUnit());
			Assert.assertNotNull(settlementItem.getCreditCard());
			Assert.assertEquals(true,settlementItem.getAuthorization().isBatched());
			Assert.assertEquals(SettlementItemType.PURCHASE,settlementItem.getType());
		} catch (RuntimeException e) {
			log.error("",e);
			throw e;
		} finally {
			/**
			 * Clean the database
			 */
			AuthorizationHotel authorization = (AuthorizationHotel) settlementItem.getAuthorization();

			if (settlementItem != null)
				getSettlementItemDao().delete(settlementItem);
			
			if (authorization != null)
			{
				authorization = getAuthorizationService().load(authorization);
				getAuthorizationDao().delete(authorization);
			}

			if (creditCard != null)
				getCreditCardService().deleteOrDisable(creditCard);

			if (businessUnit != null)
				getBusinessUnitService().delete(businessUnit);

			if (merchantAccount != null)
				getMerchantAccountService().delete(merchantAccount);

		}
	}
	
	@Test
	public void applyPaymentCredit() throws Exception {
		log.debug("testing do a applay payment credit");

		BusinessUnit businessUnit = null;
		MerchantAccount merchantAccount = null;
		CreditCard creditCard = null;
		SettlementItemHotel settlementItem = null;

		try {

			businessUnit =  getMerchantAccountFactory().getVitalBusinessUnit();
			merchantAccount = businessUnit.getMerchantAccount();

			merchantAccount = getMerchantAccountService().save(merchantAccount);
			businessUnit = getBusinessUnitService().save(businessUnit);

			creditCard = getCreditCardFactory().getCreditCardVisa();
			creditCard = getCreditCardService().saveOrUpdate(creditCard,businessUnit.getCode());

			BigDecimal authAmountPayment = new BigDecimal("-20.30");

			Integer stayDuration = 1;
			String externalSourceId = "pms_folioLine_1";
			String externalSourceIdAuthorization = "pms_folio_1";
			
			settlementItem = getCreditCardProcessingService().applyPayment(authAmountPayment,new Date(),creditCard.getUuid(),businessUnit.getUuid(),"folioNumber",new BigDecimal("1"),new Date(),stayDuration, externalSourceIdAuthorization, externalSourceId);
			settlementItem = getSettlementItemService().load(settlementItem);

			Assert.assertEquals(authAmountPayment, settlementItem.getAmount());
			Assert.assertNull(settlementItem.getAuthorization());
			Assert.assertNotNull(settlementItem.getBusinessUnit());
			Assert.assertNotNull(settlementItem.getCreditCard());
			Assert.assertEquals(SettlementItemType.CREDIT,settlementItem.getType());
		} catch (RuntimeException e) {
			log.error("",e);
			throw e;
		} finally {
			/**
			 * Clean the database
			 */
			AuthorizationHotel authorization = (AuthorizationHotel) settlementItem.getAuthorization();

			if (settlementItem != null)
				getSettlementItemDao().delete(settlementItem);
			
			if (authorization != null)
			{
				authorization = getAuthorizationService().load(authorization);
				getAuthorizationDao().delete(authorization);
			}

			if (creditCard != null)
				getCreditCardService().deleteOrDisable(creditCard);

			if (businessUnit != null)
				getBusinessUnitService().delete(businessUnit);

			if (merchantAccount != null)
				getMerchantAccountService().delete(merchantAccount);

		}
	}
	
	@Test
	public void settlementApproved() throws InterruptedException{
		log.debug("testing do a approved settlement");

		BusinessUnit businessUnit = null;
		MerchantAccount merchantAccount = null;
		CreditCard creditCard1 = null;
		CreditCard creditCard2 = null;
		
		AuthorizationHotel authorization1 = null;
		AuthorizationHotel authorization2 = null;
		
		SettlementItemHotel settlementItemPurchase1 = null;
		SettlementItemHotel settlementItemPurchase2 = null;
		SettlementItemHotel settlementItemCredit3 = null;
		
		Settlement settlement=null;

		try {

			businessUnit =  getMerchantAccountFactory().getVitalBusinessUnit();
			merchantAccount = businessUnit.getMerchantAccount();

			merchantAccount = getMerchantAccountService().save(merchantAccount);
			businessUnit = getBusinessUnitService().save(businessUnit);

			creditCard1 = getCreditCardFactory().getCreditCardVisa();
			creditCard1 = getCreditCardService().saveOrUpdate(creditCard1,businessUnit);
			
			creditCard2 = getCreditCardFactory().getCreditCardVisa();
			creditCard2 = getCreditCardService().saveOrUpdate(creditCard2,businessUnit);

			Integer stayDuration=1;

			BigDecimal authAmount1 = new BigDecimal("19.20");
			String externalSourceIdAuthorization1 = "pms_folio_1";
			authorization1 = getCreditCardProcessingService().authorize(businessUnit.getUuid(), creditCard1.getUuid(), authAmount1,stayDuration, externalSourceIdAuthorization1);
			
			BigDecimal authAmount2 = new BigDecimal("14.20");
			String externalSourceIdAuthorization2 = "pms_folio_2";
			authorization2 = getCreditCardProcessingService().authorize(businessUnit.getUuid(), creditCard2.getUuid(), authAmount2,stayDuration, externalSourceIdAuthorization2);

			BigDecimal authAmountPayment1 = new BigDecimal("15.30");
			String externalSourceId = "pms_folioLine_1";
			settlementItemPurchase1 = getCreditCardProcessingService().applyPayment(authAmountPayment1,new Date(),creditCard1.getUuid(),businessUnit.getUuid(),"folioNumber",new BigDecimal("1"),new Date(),stayDuration, externalSourceIdAuthorization1, externalSourceId);
			settlementItemPurchase1 = getSettlementItemService().load(settlementItemPurchase1);
			
			/**
			 * With test data the settlement with american express is given error.
			 * I think that it could be a test data problem.
			 * abuffagni:01312011
			 */
			BigDecimal authAmountPayment2 = new BigDecimal("14.20");

			//settlementItemPurchase2 = new SettlementItemHotelImpl<AuthorizationHotel>(authAmountPayment2,null,new Date(),creditCard2,merchantAccount,"folioNumber",new BigDecimal("1"),new Date());

			//settlementItemPurchase2 = getCreditCardProcessingService().applyPayment(merchantAccount, creditCard2, authAmountPayment2);
			//settlementItemPurchase2 = getSettlementItemService().load(settlementItemPurchase2);
			
			BigDecimal authAmountPayment3 = new BigDecimal("-20.20");

			settlementItemCredit3 = getCreditCardProcessingService().applyPayment(authAmountPayment3,new Date(),creditCard1.getUuid(),businessUnit.getUuid(),"folioNumber",new BigDecimal("1"),new Date(),stayDuration, externalSourceIdAuthorization1, externalSourceId);

			settlement = settlement(businessUnit,20);
			
			settlement = getSettlementService().load(settlement);
			
			Assert.assertEquals(true, settlement.getTransactionResponse().isApproved());
			Assert.assertEquals(true, settlement.isEnabled());
			Assert.assertEquals(businessUnit.getUuid(), settlement.getBusinessUnit().getUuid());
			Assert.assertEquals(merchantAccount.getUuid(), settlement.getBusinessUnit().getMerchantAccount().getUuid());
			Assert.assertEquals(2,settlement.getSettlementItems().size());
			Assert.assertEquals(authAmountPayment1,settlement.getSettlementItems().get(0).getAmount());
			//Assert.assertEquals(authAmountPayment2,settlement.getSettlementItems().get(1).getAmount());
			Assert.assertEquals(authAmountPayment3,settlement.getSettlementItems().get(1).getAmount());
			Assert.assertEquals(externalSourceIdAuthorization1,settlement.getSettlementItems().get(0).getAuthorization().getExternalSourceId());

		} catch (RuntimeException e) {
			log.error("",e);
			throw e;
		} finally {
			/**
			 * Clean the database
			 */
			if (settlementItemPurchase1 != null)
				settlementItemPurchase1 = getSettlementItemService().load(settlementItemPurchase1);
			if (settlementItemPurchase1 != null)
				getSettlementItemDao().delete(settlementItemPurchase1);

			if (settlementItemPurchase2 != null)
				settlementItemPurchase2 = getSettlementItemService().load(settlementItemPurchase2);
			
			if (settlementItemPurchase2 != null)
				getSettlementItemDao().delete(settlementItemPurchase2);
			
			if (settlementItemCredit3 != null)
				settlementItemCredit3 = getSettlementItemService().load(settlementItemCredit3);
			if (settlementItemCredit3 != null)
				getSettlementItemDao().delete(settlementItemCredit3);

			if (settlement != null)
				settlement = getSettlementService().load(settlement);
			
			if (settlement != null)
				getSettlementDao().delete(settlement);
			
			if (authorization1 != null)
				authorization1 = getAuthorizationService().load(authorization1);
			if (authorization1 != null)
				getAuthorizationDao().delete(authorization1);
			
			if (authorization2 != null)
				authorization2 = getAuthorizationService().load(authorization2);
			if (authorization2 != null)
				getAuthorizationDao().delete(authorization2);

			/*if (creditCard1 != null)
				getCreditCardService().deleteOrDisable(creditCard1);
			
			if (creditCard2 != null)
				getCreditCardService().deleteOrDisable(creditCard2);

			if (businessUnit != null)
				getBusinessUnitService().delete(businessUnit);*/

			Search searchCriteria =  new Search(BatchNumber.class);
			searchCriteria.addFilterEqual("merchantAccount", merchantAccount);
			searchCriteria.setMaxResults(1);

			List<BatchNumber> list = getBatchNumberDao().findByCriteria(searchCriteria);
			if (list!=null && !list.isEmpty())
			{
				BatchNumber batchNumber = list.get(0);
				getBatchNumberDao().delete(batchNumber);
			}
			
			if (merchantAccount != null)
				getMerchantAccountService().delete(merchantAccount);

		}
	}

	/**
	 * This method is used for avoid repeat the batch number
	 * @param merchantAccount
	 * @return
	 */
	private Settlement settlement(BusinessUnit businessUnit,int bathNumber) {
		Settlement settlement;
		settlement = getCreditCardProcessingService().settlement(businessUnit.getUuid(),bathNumber);

		if (!settlement.getTransactionResponse().isApproved() && settlement.getTransactionResponse().getBatchResponseCode().contains("QD"))
		{
			bathNumber++;
			settlement = settlement(businessUnit,bathNumber);
		}

		return settlement;
	}
	
	public CreditCardProcessingService getCreditCardProcessingService() {
		return creditCardProcessingService;
	}

	public void setCreditCardProcessingService(CreditCardProcessingService creditCardProcessingService) {
		this.creditCardProcessingService = creditCardProcessingService;
	}

	public BusinessUnitFactory getMerchantAccountFactory() {
		return merchantAccountFactory;
	}

	public void setMerchantAccountFactory(BusinessUnitFactory merchantAccountFactory) {
		this.merchantAccountFactory = merchantAccountFactory;
	}

	public CreditCardFactory getCreditCardFactory() {
		return creditCardFactory;
	}

	public void setCreditCardFactory(CreditCardFactory creditCardFactory) {
		this.creditCardFactory = creditCardFactory;
	}

	public AuthorizationHotelService<AuthorizationHotel> getAuthorizationService() {
		return authorizationService;
	}

	public void setAuthorizationService(AuthorizationHotelService<AuthorizationHotel> authorizationService) {
		this.authorizationService = authorizationService;
	}

	public CreditCardStorageService<SearchCriteria> getCreditCardService() {
		return creditCardService;
	}

	public void setCreditCardService(CreditCardStorageService<SearchCriteria> creditCardService) {
		this.creditCardService = creditCardService;
	}

	public MerchantAccountService<SearchCriteria> getMerchantAccountService() {
		return merchantAccountService;
	}

	public void setMerchantAccountService(MerchantAccountService<SearchCriteria> merchantAccountService) {
		this.merchantAccountService = merchantAccountService;
	}

	public void setAuthorizationDao(AuthorizationHotelDao<AuthorizationHotel, Serializable, SearchCriteria, AuthorizationFacet> authorizationDao) {
		this.authorizationDao = authorizationDao;
	}

	public AuthorizationHotelDao<AuthorizationHotel, Serializable, SearchCriteria, AuthorizationFacet> getAuthorizationDao() {
		return authorizationDao;
	}

	public SettlementItemHotelService<SettlementItemHotel> getSettlementItemService() {
		return settlementItemService;
	}

	public void setSettlementItemService(SettlementItemHotelService<SettlementItemHotel> settlementItemService) {
		this.settlementItemService = settlementItemService;
	}

	public SettlementItemHotelDao<SettlementItemHotel, Serializable, SearchCriteria, SettlementItemFacet> getSettlementItemDao() {
		return settlementItemDao;
	}

	public void setSettlementItemDao(SettlementItemHotelDao<SettlementItemHotel, Serializable, SearchCriteria, SettlementItemFacet> settlementItemDao) {
		this.settlementItemDao = settlementItemDao;
	}

	public SettlementDao<Settlement, Serializable, SearchCriteria, SettlementFacet> getSettlementDao() {
		return settlementDao;
	}

	public void setSettlementDao(SettlementDao<Settlement, Serializable, SearchCriteria, SettlementFacet> settlementDao) {
		this.settlementDao = settlementDao;
	}

	public SettlementService<Settlement> getSettlementService() {
		return settlementService;
	}

	public void setSettlementService(SettlementService<Settlement> settlementService) {
		this.settlementService = settlementService;
	}

	public BusinessUnitService<SearchCriteria> getBusinessUnitService() {
		return businessUnitService;
	}

	public void setBusinessUnitService(BusinessUnitService<SearchCriteria> businessUnitService) {
		this.businessUnitService = businessUnitService;
	}

	public BatchNumberDao<BatchNumber, Serializable, SearchCriteria> getBatchNumberDao() {
		return batchNumberDao;
	}

	public void setBatchNumberDao(BatchNumberDao<BatchNumber, Serializable, SearchCriteria> batchNumberDao) {
		this.batchNumberDao = batchNumberDao;
	}
	
}
