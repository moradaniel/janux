package biz.janux.payment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.janux.bus.search.SearchCriteria;
import org.janux.util.RandomBasedGenerator;
import org.janux.util.RandomStringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biz.janux.commerce.CreditCardMask;
import biz.janux.payment.mock.CreditCardFactory;

import com.trg.search.Search;


public class CreditCardStorageServiceMockImpl implements CreditCardStorageService<SearchCriteria>{

	private RandomStringGenerator uuidGenerator=new RandomBasedGenerator();
	private CreditCardMask creditCardMask;
	private CreditCardFactory creditCardFactory;
	private Map<String, CreditCard> savedCreditCard = new HashMap<String, CreditCard>();
	private EncryptedStorageService<String, String> encryptedStorageService;
	private BusinessUnitService<SearchCriteria> businessUnitService;
	private CreditCardValidator creditCardValidator;
	
	public Logger log = LoggerFactory.getLogger(this.getClass());

	
	public CreditCard decrypt(String uuid) {
		return getCreditCardFactory().getCreditCardCompleteVisa();
	}

	
	public CreditCard decrypt(CreditCard creditCard) {
		log.debug("Attempting to decrypt credit card number for '{}' ...",creditCard.getUuid());

		// the credit card number has to restored.
		String token = creditCard.getToken();
		String number = getEncryptedStorageService().decryption(token);
		creditCard.setNumber(number);
		creditCard.setNumberMasked(maskNumber(number));
		
		log.info("Successfully decrypted credit card number for '{}': '{}'",creditCard.getUuid(), creditCard.getNumberMasked());
		return creditCard;
	}

	public boolean deleteOrDisable(String creditCardUuid) {
		log.debug("Attempting to delete credit card with uuid: '{}' ...", creditCardUuid);

		CreditCard creditCard = load(creditCardUuid,false);
		return deleteOrDisable(creditCard);
	}
	
	public boolean deleteOrDisable(CreditCard creditCard) {
		log.debug("Attempting to delete credit card: {} ...", creditCard);
		if (creditCard!=null)
		{
			String uuid = creditCard.getUuid();
			if (canBeDeleted(uuid))
			{
				getSavedCreditCard().remove(creditCard.getUuid());
				log.info("Successfully deleted credit card: {}", creditCard);
				return true;
			}
			else
			{
				creditCard.setEnabled(false);
				this.saveOrUpdate(creditCard);
				log.info("Unable to delete credit card, marking it disabled: {}", creditCard);
				return false;
			}
		}
		throw new RuntimeException("EntityNotFound");
	}
	
	private boolean canBeDeleted(String uuid) {
		log.debug("Asking if the credit card can be deleted with uuid: '{}'",uuid);
		
		log.debug("The credit card with uuid '{}' has not authorizations, can be deleted", uuid);
		return true;
	}

	
	public CreditCard encrypt(CreditCard creditCard) {
		return saveOrUpdate(creditCard);
	}
	
	
	public List<CreditCard> findByCriteria(SearchCriteria arg0) {
		throw new UnsupportedOperationException();
	}

	
	public List<CreditCard> findByCriteria(SearchCriteria arg0, boolean arg1) {
		throw new UnsupportedOperationException();
	}

	
	public CreditCard findFirstByCriteria(SearchCriteria arg0) {
		throw new UnsupportedOperationException();
	}

	
	public CreditCard findFirstByCriteria(SearchCriteria arg0, boolean arg1) {
		throw new UnsupportedOperationException();
	}

	
	public CreditCard load(String creditCardUuid, boolean decrypt) 
	{
		String action = decrypt ? "decrypt" : "load";
		log.debug("Attempting to {} cc with uuid: '{}'", action, creditCardUuid);
		CreditCard creditCard = getSavedCreditCard().get(creditCardUuid);
		if (creditCard!=null && decrypt){
			decrypt(creditCard);
		}
		log.info("Successfully {}ed cc: {}", new Object[] {action, creditCardUuid, creditCard});
		return creditCard;
	}

	
	public boolean purge(CreditCard arg0) {
		throw new UnsupportedOperationException();
	}

	
	public boolean purge(String arg0) {
		throw new UnsupportedOperationException();
	}

	public CreditCard saveOrUpdate(CreditCard creditCard) {
		if (((CreditCardImpl)creditCard).getBusinessUnit()!=null)
			return saveOrUpdate(creditCard,((CreditCardImpl)creditCard).getBusinessUnit());
		else
			return saveOrUpdate(creditCard,getBusinessUnitService().BUSINESS_UNIT_CODE_BY_DEFAULT,true,null);
	}
	
	public CreditCard saveOrUpdate(CreditCard creditCard, BusinessUnit businessUnit) {
		return saveOrUpdate(creditCard,businessUnit.getCode(),false,null);
	}


	public CreditCard saveOrUpdate(CreditCard creditCard, BusinessUnit businessUnit, boolean autoCreateBusinessUnit) {
		return saveOrUpdate(creditCard,businessUnit.getCode(),autoCreateBusinessUnit,null);
	}
	
	public CreditCard saveOrUpdate(CreditCard creditCard, String businessUnitCode) {
		return saveOrUpdate(creditCard,businessUnitCode,false,null);
	}
	

	public CreditCard saveOrUpdate(CreditCard creditCard, String businessUnitCode, boolean autoCreateBusinessUnit,IndustryType industryType) {
		log.debug("Attempting to save credit card: {} ...",creditCard);
		String token=null; 
		
		BusinessUnit businessUnit = getBusinessUnitService().resolveBusinessUnit(businessUnitCode,autoCreateBusinessUnit,industryType);
		
		// if the credit card has not been persisted yet
		if (((CreditCardImpl)creditCard).getId()==((CreditCardImpl)creditCard).UNSAVED_ID)
		{
			token = getEncryptedStorageService().encryption(creditCard.getNumber());
			creditCard.setToken(token);
			log.debug("Token created: '{}'",token);
			
			creditCard.setNumberMasked(maskNumber(creditCard.getNumber()));
			creditCard.setNumber(null);
			creditCard.setEnabled(true);
			log.debug("CC masked: '{}'",creditCard.getNumberMasked());
		}
		
		 // if the credit card was persisted and its number is not null
		 // It is possible that the number was changed
		if (((CreditCardImpl)creditCard).getId() != ((CreditCardImpl)creditCard).UNSAVED_ID 
				&& creditCard.getNumber()!=null)
		{
			token = getEncryptedStorageService().encryption(creditCard.getNumber());
			creditCard.setToken(token);
			log.debug("Token created: '{}'",token);
			
			creditCard.setNumberMasked(maskNumber(creditCard.getNumber()));
			creditCard.setNumber(null);
			log.debug("CC masked: '{}'",creditCard.getNumberMasked());
		}

		((CreditCardImpl)creditCard).setBusinessUnit(businessUnit);
		
		if (creditCard.getUuid()==null)
		{
			String uuid = getUuidGenerator().getString();
			((CreditCardImpl)creditCard).setUuid(uuid);
		}
		log.info("Successfully saved credit card '{}'",creditCard);
		
		/**
		 * for mock object. Set the id != -1 to indicate that the creditCard is persistent
		 */
		((CreditCardImpl)creditCard).setId(1);
		getSavedCreditCard().put(creditCard.getUuid(), creditCard);
		return creditCard; 
	}


	public String maskNumber(String num)
	{
		if (this.getCreditCardMask() == null) {
			throw new IllegalStateException ("CreditCardMask has not been instantiated");
		}
		return this.getCreditCardMask().maskNumber(num);
	}

	public CreditCard find(Search search) {
		return getCreditCardFactory().getCreditCardSavedVisa();
	}

	public void setUuidGenerator(RandomStringGenerator uuidGenerator) {
		this.uuidGenerator = uuidGenerator;
	}

	public RandomStringGenerator getUuidGenerator() {
		return uuidGenerator;
	}

	public void setCreditCardMask(CreditCardMask creditCardMask) {
		this.creditCardMask = creditCardMask;
	}

	public CreditCardMask getCreditCardMask() {
		return creditCardMask;
	}

	public CreditCardFactory getCreditCardFactory() {
		return creditCardFactory;
	}

	public void setCreditCardFactory(CreditCardFactory creditCardFactory) {
		this.creditCardFactory = creditCardFactory;
	}

	private void setSavedCreditCard(Map<String, CreditCard> savedCreditCard) {
		this.savedCreditCard = savedCreditCard;
	}

	private Map<String, CreditCard> getSavedCreditCard() {
		return savedCreditCard;
	}


	public void setEncryptedStorageService(EncryptedStorageService<String, String> encryptedStorageService) {
		this.encryptedStorageService = encryptedStorageService;
	}


	public EncryptedStorageService<String, String> getEncryptedStorageService() {
		return encryptedStorageService;
	}


	public BusinessUnitService<SearchCriteria> getBusinessUnitService() {
		return businessUnitService;
	}


	public void setBusinessUnitService(BusinessUnitService<SearchCriteria> businessUnitService) {
		this.businessUnitService = businessUnitService;
	}


	public CreditCardValidator getCreditCardValidator()
	{
		return creditCardValidator;
	}


	public void setCreditCardValidator(CreditCardValidator creditCardValidator)
	{
		this.creditCardValidator = creditCardValidator;
	}


	public boolean isValid(CreditCard aCreditCard)
	{
		return this.creditCardValidator.isValid(aCreditCard);
	}


	public Map<String, String> validate(CreditCard aCreditCard)
	{
		return this.validate(aCreditCard);
	}

}
