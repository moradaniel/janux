package biz.janux.payment;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.janux.bus.search.SearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trg.search.Filter;
import com.trg.search.Search;

public class CreditCardProcessingServiceImpl implements CreditCardProcessingService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	AuthorizationHotelService<AuthorizationHotel> authorizationService;

	CreditCardStorageService<SearchCriteria> creditCardService;
	
	BusinessUnitService<SearchCriteria> businessUnitService;
	
	CreditCardTypeService<SearchCriteria> creditCardTypeService;
	
	SettlementItemHotelService<SettlementItemHotel> settlementItemService;
	
	SettlementService<Settlement> settlementService;
	
	PaymentProcessorIntegrator paymentProcessorIntegrator;

	
	public AuthorizationHotel authorize(String businessUnitUuid, String creditCardUuid, BigDecimal authAmount,Integer stayDuration,String externalSourceId) {
		/**
		 * the credit card number has to restored because it has to be sent to cc processor
		 */
		CreditCard creditCard = getCreditCardService().decrypt(creditCardUuid);
		BusinessUnit businessUnit = getBusinessUnitService().load(businessUnitUuid);
		/**
		 * create the authorization
		 */
		AuthorizationHotel authorization = new AuthorizationHotelImpl(businessUnit, creditCard, authAmount,stayDuration, externalSourceId);
		authorization.setDate(new Timestamp(System.currentTimeMillis()));

		return authorize(authorization, authAmount,AuthorizationType.AUTHORIZATION);
	}
	
	private AuthorizationHotel authorize(AuthorizationHotel authorization, BigDecimal authAmount, AuthorizationType authorizationType) {
		log.info("Processing Authorization {} {})",new Object[]{authorization,authorizationType});
		
		if (!getAuthorizationService().isAllowAuthorization(authorizationType, authorization.getCreditCard().getType()))
			throw new IllegalStateException("This transactions type "+ authorizationType +" is not currently supported for "+authorization.getCreditCard().getType());
		
		AuthorizationResponse authorizationResponse = getPaymentProcessorIntegrator().processAuthorization(authorization, authAmount, authorizationType);
		
		if (authorizationResponse.isApproved())
			authorization = getAuthorizationService().create(authorization,authAmount,authorizationResponse,authorizationType);
		else
			authorization.setTransactionResponse(authorizationResponse);
		
		log.info("Processed Authorization {} {}",new Object[]{authorization,authorizationType});
		return authorization;
	}

	public SettlementItemHotel addCreditToSettlementBatch(SettlementItemHotel settlementItem) {
		log.info("Adding credit to batch {} {} {}",new Object[]{settlementItem.getBusinessUnit(),settlementItem.getCreditCard(),settlementItem.getAmount()});
		
		settlementItem.setType(SettlementItemType.CREDIT);
		settlementItem.setDate(new Date());
		getSettlementItemService().saveOrUpdate(settlementItem);
		
		log.info("Added credit to batch {}",settlementItem);
		return settlementItem;
	}

	public SettlementItemHotel addPurchaseToSettlementBatch(AuthorizationHotel authorization,SettlementItemHotel  settlementItem) {
		log.info("Adding purchase to batch {} {} ",new Object[]{authorization,settlementItem.getAmount()});
		
		getAuthorizationService().setBatched(authorization);

		settlementItem.setAuthorization(authorization);
		settlementItem.setType(SettlementItemType.PURCHASE);
		settlementItem.setDate(new Date());
		settlementItem.setCreditCard(authorization.getCreditCard());
		settlementItem.setBusinessUnit(authorization.getBusinessUnit());

		getSettlementItemService().saveOrUpdate(settlementItem);

		log.info("Added purchase to batch {}",settlementItem);
		return settlementItem;
	}

	public SettlementItemHotel applyPayment(BigDecimal authAmountPayment, Date date, String creditCardUuid, String businessUnitUuid, String purchaseIdentifier, BigDecimal averageRate, Date checkOutDate, Integer stayDuration, String externalSourceIdAuthorization, String externalSourceId) {

		CreditCard creditCard = getCreditCardService().decrypt(creditCardUuid);
		BusinessUnit businessUnit = getBusinessUnitService().load(businessUnitUuid);
	
		log.info("Applying payment {} {} {}",new Object[]{businessUnit,creditCard,authAmountPayment});

		if (! ((CreditCardImpl)creditCard).getBusinessUnit().equals(businessUnit) )
			throw new RuntimeException("The businessUnit of credit card does not match with the businessUnit set");
		
		SettlementItemHotel settlementItem = new SettlementItemHotelImpl(authAmountPayment,null,date,creditCard,businessUnit,purchaseIdentifier,averageRate,checkOutDate, externalSourceId);
		/**
		 * If this is a "payment", as opposed to a "credit". 
		 * Amount will be > 0 is a payment.
		 */
		if (settlementItem.getAmount().compareTo(new BigDecimal(0.0)) > 0) {

			if (externalSourceIdAuthorization==null || externalSourceIdAuthorization.equals(""))
				throw new RuntimeException("externalSourceIdAuthorization is null or is empty");

			if (settlementItem.getBusinessUnit().getUuid()==null || settlementItem.getBusinessUnit().getUuid().equals(""))
				throw new RuntimeException("settlementItem.getBusinessUnit().getUuid() is null or is empty");
			
			/**
			 * Try to retrieve existing authorization amounts
			 */
			Search search = new Search(AuthorizationImpl.class);
			search.addFilter(Filter.equal("batched", false));
			search.addFilter(Filter.equal("enabled", true));
			search.addFilter(Filter.equal("externalSourceId", externalSourceIdAuthorization));
			search.addFilter(Filter.equal("creditCard.uuid", settlementItem.getCreditCard().getUuid()));
			search.addFilter(Filter.equal("businessUnit.uuid", settlementItem.getBusinessUnit().getUuid()));
			
			AuthorizationHotel originalAuthorization = getAuthorizationService().find(search);

			/**
			 * If authResponse is null, no authorizations are available. Get a NEW authorization
			 */
			if (originalAuthorization == null) {
				originalAuthorization = authorize(settlementItem.getBusinessUnit().getUuid(), settlementItem.getCreditCard().getUuid(), settlementItem.getAmount(),stayDuration, externalSourceIdAuthorization);
				if (!originalAuthorization.getTransactionResponse().isApproved())
					throw new CreditCardProccesingException(originalAuthorization);
			}
			else {
				/**
				 * 	Authorization already existed in system
				 */
				if (originalAuthorization.getAmount().compareTo(settlementItem.getAmount()) < 0) {
					/**
					 * If offline authorization, no incremental is allowed.
					 * Throw error that only X can be settled.
					 */
					if (originalAuthorization.getTransactionResponse().isOffline()) {
						throw new IllegalStateException("The authorization is offline, no incremental is allowed");
					}

					BigDecimal diff = settlementItem.getAmount().subtract(originalAuthorization.getAmount());

					log.debug("attempting to incremen authorization to {}",diff);
					AuthorizationHotel authorization2 = null;
					authorization2 = increment(originalAuthorization.getUuid(),diff);
				}
			}

			originalAuthorization = getAuthorizationService().load(originalAuthorization);
			settlementItem = addPurchaseToSettlementBatch(originalAuthorization, settlementItem);

		} else {

			settlementItem = addCreditToSettlementBatch(settlementItem);
		}

		log.info("Applied payment {}",settlementItem);
		return settlementItem;
	}
	
	public SettlementItemHotel applyPayment(BigDecimal authAmountPayment, Date date, String creditCardUuid, String businessUnitUuid, String purchaseIdentifier, BigDecimal averageRate, Date checkOutDate, Integer stayDuration, String externalSourceIdAuthorization, String externalSourceId, String authorizationUuid) {
		CreditCard creditCard = getCreditCardService().decrypt(creditCardUuid);
		BusinessUnit businessUnit = getBusinessUnitService().load(businessUnitUuid);
		
		log.info("Applying payment {} {} {}",new Object[]{businessUnit,creditCard,authAmountPayment});
		
		if (! ((CreditCardImpl)creditCard).getBusinessUnit().equals(businessUnit) )
			throw new RuntimeException("The businessUnit of credit card does not match with the businessUnit set");
		
		SettlementItemHotel settlementItem = new SettlementItemHotelImpl(authAmountPayment,null,date,creditCard,businessUnit,purchaseIdentifier,averageRate,checkOutDate, externalSourceId);
		
		/**
		 * If this is a "payment", as opposed to a "credit". 
		 * Amount will be > 0 is a payment.
		 */
		if (settlementItem.getAmount().compareTo(new BigDecimal(0.0)) > 0) {

			if (externalSourceIdAuthorization==null || externalSourceIdAuthorization.equals(""))
				throw new RuntimeException("externalSourceIdAuthorization is null or is empty");
			
			if (authorizationUuid==null || authorizationUuid.equals(""))
				throw new RuntimeException("authorizationUuid is null or is empty");
			
			if (settlementItem.getBusinessUnit().getUuid()==null || settlementItem.getBusinessUnit().getUuid().equals(""))
				throw new RuntimeException("settlementItem.getBusinessUnit().getUuid() is null or is empty");
			
			/**
			 * Try to retrieve existing authorization amounts
			 */
			Search search = new Search(AuthorizationImpl.class);
			search.addFilter(Filter.equal("batched", false));
			search.addFilter(Filter.equal("enabled", true));
			search.addFilter(Filter.equal("externalSourceId", externalSourceIdAuthorization));
			search.addFilter(Filter.equal("uuid", authorizationUuid));
			search.addFilter(Filter.equal("businessUnit.uuid", settlementItem.getBusinessUnit().getUuid()));
			
			AuthorizationHotel originalAuthorization = getAuthorizationService().find(search);

			/**
			 * If authResponse is null, no authorizations are available. Get a NEW authorization
			 */
			if (originalAuthorization == null) {
				throw new RuntimeException("the authorization with uuid "+authorizationUuid+" does not exist");
			}

			settlementItem = addPurchaseToSettlementBatch(originalAuthorization, settlementItem);

		} else {

			settlementItem = addCreditToSettlementBatch(settlementItem);
		}

		log.info("Applied payment {}",settlementItem);
		return settlementItem;
	}


	public AuthorizationHotel increment(String originalAuthorizationUuid,BigDecimal amount){
		log.info(" Processing Increment Authorization {} {}",originalAuthorizationUuid,amount);

		AuthorizationHotel originalAuthorization = getAuthorizationService().load(originalAuthorizationUuid);
		/**
		 * the credit card number has to restored because it has to be sent to cc processor
		 */
		CreditCard creditCard = getCreditCardService().decrypt(originalAuthorization.getCreditCard());
		originalAuthorization.setCreditCard(creditCard);

		AuthorizationHotel authorization = authorize(originalAuthorization, amount, AuthorizationType.INCREMENTAL);

		if (!authorization.getTransactionResponse().isApproved())
			throw new CreditCardProccesingException(originalAuthorization);
		
		log.info(" Processed Increment Authorization {}",authorization);
		return authorization;
	}
	
	public AuthorizationHotel reverse(String originalAuthorizationUuid, BigDecimal amount){
		log.info(" Processing Reversal Authorization {} {}",originalAuthorizationUuid,amount);

		AuthorizationHotel originalAuthorization = getAuthorizationService().load(originalAuthorizationUuid);
		/**
		 * the credit card number has to restored because it has to be sent to cc processor
		 */
		CreditCard creditCard = getCreditCardService().decrypt(originalAuthorization.getCreditCard());
		originalAuthorization.setCreditCard(creditCard);

		AuthorizationHotel authorization = authorize(originalAuthorization, amount, AuthorizationType.REVERSAL);
		log.info(" Processed Reversal Authorization {} ",authorization);
		return authorization;
	}

	public synchronized Settlement settlement(String businessUnitUuid) {
		BusinessUnit businessUnit = getBusinessUnitService().load(businessUnitUuid);
		int lastBatchNumber = getSettlementService().findLastBatchNumber(businessUnit.getMerchantAccount());
		lastBatchNumber++;
		int batchNumber = getPaymentProcessorIntegrator().checkLimitBatchNumber(lastBatchNumber);
		return settlement(businessUnitUuid,batchNumber);
	}
	
	public synchronized Settlement settlement(String businessUnitUuid,int batchNumber) {
		log.info("Proccessing settlement {} {}",businessUnitUuid,batchNumber);

		BusinessUnit businessUnit = getBusinessUnitService().load(businessUnitUuid);
		
		if (businessUnit==null)
			throw new RuntimeException("businessUnit is null");

		Settlement settlement = new SettlementImpl(businessUnit);
		settlement.setSettlementItems(new ArrayList<SettlementItem>());
		settlement.setDate(new Date());
		// Set the batch settlement number. This is unique to the hotel
		settlement.setBatchNumber(batchNumber);

		Search search = new Search(SettlementItemImpl.class);
		search.addFilterNull("settlement");
		search.addFilterEqual("businessUnit",businessUnit);

		List<SettlementItemHotel> listSettlementItems = getSettlementItemService().findAll(search);
		
		if (listSettlementItems.isEmpty())
			throw new IllegalStateException ("There are not items for capture");
		
		for (Iterator<SettlementItemHotel> iterator = listSettlementItems.iterator(); iterator.hasNext();) {
			SettlementItemHotel settlementItem = (SettlementItemHotel) iterator.next();
			settlementItem = getSettlementItemService().load(settlementItem);
				settlement.getSettlementItems().add(settlementItem);
				settlementItem.setSettlement(settlement);
		}

		SettlementResponse settlementResponse = getPaymentProcessorIntegrator().processSettlement(settlement, batchNumber);
		settlement.setTransactionResponse(settlementResponse);
		
		if (settlementResponse.isApproved()) {
			settlement = getSettlementService().create(settlement);
			getSettlementService().saveOrUpdateBatchNumber(businessUnit.getMerchantAccount(), batchNumber);
		} 

		log.info("Proccessed settlement {}",settlement);
		return settlement;
	}

	public AuthorizationHotel modify(String originalAuthorizationUuid, BigDecimal amount) {
		log.info("Modifying authorization {} {}",originalAuthorizationUuid,amount);
		
		AuthorizationHotel authorization = null;
		AuthorizationHotel originalAuthorization = getAuthorizationService().load(originalAuthorizationUuid);
		
		BigDecimal authAmount = originalAuthorization.getAmount();
		
		if(amount.compareTo(new BigDecimal(0.00)) == 0)
		{
			/**
			 * TODO:review to do in this case
			 * abuffagni:20110124
			 */
			throw new IllegalStateException("Transaction is not supported");
		}
		else if(amount.compareTo(authAmount) > 0)
		{
			BigDecimal difference = amount.subtract(authAmount);
			authorization = increment(originalAuthorizationUuid,difference);
		}
		else if(amount.compareTo(authAmount) < 0)
		{
			BigDecimal difference = amount.subtract(authAmount);
			authorization = reverse(originalAuthorizationUuid,difference);
		}
		
		
		log.info("Modificated authorization {}",authorization);
		return authorization;
	}
	
	public void voidAuthorization(String originalAuthorizationUuid) {
		AuthorizationHotel originalAuthorization = getAuthorizationService().load(originalAuthorizationUuid);
		getAuthorizationService().voidAuthorization(originalAuthorization);
	}

	public AuthorizationHotel authorizeOffline(String businessUnitUuid, String creditCardUuid, BigDecimal authAmount,Integer stayDuration,String approvalCode, String externalSourceId) {
		log.info("Creating offline authorization {} {} {} approvalCode:{}",new Object[]{businessUnitUuid,creditCardUuid,authAmount,approvalCode});
		
		/**
		 * TODO:check if the date will be get in this way or the timezone of application client will be take into account for it.
		 * abuffagni:20110124
		 */
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());

		CreditCard creditCard = getCreditCardService().decrypt(creditCardUuid);
		BusinessUnit businessUnit = getBusinessUnitService().load(businessUnitUuid);
		
		AuthorizationHotel authorization = new AuthorizationHotelImpl(businessUnit,creditCard,authAmount,stayDuration, externalSourceId);
		authorization.setDate(date);
		authorization.setEnabled(true);
		
		AuthorizationResponse authorizationResponse = getPaymentProcessorIntegrator().processAuthorizationOffline(authorization, authAmount, AuthorizationType.AUTHORIZATION,approvalCode);
		
		authorization = getAuthorizationService().create(authorization, authAmount, authorizationResponse, AuthorizationType.AUTHORIZATION);
		log.info("Created offline authorization {}",authorization);

		return authorization;
	}
	
	public int initializeBatchNumber(String businessUnitUuid, int number) {
		BusinessUnit businessUnit = getBusinessUnitService().load(businessUnitUuid);
		getSettlementService().saveOrUpdateBatchNumber(businessUnit.getMerchantAccount(), number);
		return getSettlementService().findLastBatchNumber(businessUnit.getMerchantAccount());
	}

	
	public CreditCardStorageService<SearchCriteria> getCreditCardService() {
		return creditCardService;
	}

	public void setCreditCardService(CreditCardStorageService<SearchCriteria> creditCardService) {
		this.creditCardService = creditCardService;
	}

	public AuthorizationHotelService<AuthorizationHotel> getAuthorizationService() {
		return authorizationService;
	}

	public void setAuthorizationService(AuthorizationHotelService<AuthorizationHotel> authorizationService) {
		this.authorizationService = authorizationService;
	}

	public SettlementItemHotelService<SettlementItemHotel> getSettlementItemService() {
		return settlementItemService;
	}

	public void setSettlementItemService(SettlementItemHotelService<SettlementItemHotel> settlementItemService) {
		this.settlementItemService = settlementItemService;
	}

	public CreditCardTypeService<SearchCriteria> getCreditCardTypeService() {
		return creditCardTypeService;
	}

	public void setCreditCardTypeService(CreditCardTypeService<SearchCriteria> creditCardTypeService) {
		this.creditCardTypeService = creditCardTypeService;
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

	public PaymentProcessorIntegrator getPaymentProcessorIntegrator() {
		return paymentProcessorIntegrator;
	}

	public void setPaymentProcessorIntegrator(PaymentProcessorIntegrator paymentProcessorIntegrator) {
		this.paymentProcessorIntegrator = paymentProcessorIntegrator;
	}

}
