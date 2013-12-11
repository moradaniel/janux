package biz.janux.payment;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;
import javax.management.RuntimeOperationsException;

import org.janux.bus.search.SearchCriteria;
import org.jasypt.digest.StringDigester;
import org.jasypt.util.digest.Digester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biz.janux.commerce.CreditCardMask;

import com.trg.search.Filter;
import com.trg.search.Search;

/**
 * 
 * @author albertobuffagni@gmail.com
 * 
 */
public class CreditCardStorageServiceImpl implements CreditCardStorageService<SearchCriteria> {

	private CreditCardMask creditCardMask;
	private CreditCardDao<CreditCard, Integer, SearchCriteria> creditCardDao;
	private EncryptedStorageService<String, String> encryptedStorageService;
	private BusinessUnitService<SearchCriteria> businessUnitService;
	private StringDigester encryptorOneWay;
	private CreditCardValidator creditCardValidator;
	private AuthorizationHotelService authorizationService;

	public Logger log = LoggerFactory.getLogger(this.getClass());

	public CreditCard decrypt(CreditCard creditCard) {
		log.debug("Attempting to decrypt credit card number for '{}' ...", creditCard.getUuid());

		// the credit card number has to restored.
		String token = creditCard.getToken();
		String number = getEncryptedStorageService().decryption(token);
		creditCard.setNumber(number);
		creditCard.setNumberMasked(maskNumber(number));

		log.info("Successfully decrypted credit card number for '{}': '{}'", creditCard.getUuid(), creditCard.getNumberMasked());
		return creditCard;
	}

	public boolean deleteOrDisable(String creditCardUuid) {
		log.debug("Attempting to delete credit card with uuid: '{}' ...", creditCardUuid);

		CreditCard creditCard = load(creditCardUuid, false);
		return deleteOrDisable(creditCard);
	}

	public boolean deleteOrDisable(CreditCard creditCard) 
	{
		log.debug("Attempting to delete credit card: {} ...", creditCard);
		if (creditCard != null) {
			String uuid = creditCard.getUuid();
			if (canBeDeleted(uuid)) {
				getCreditCardDao().delete(creditCard);
				log.info("Successfully deleted credit card: {}", creditCard);
				return true;
			} else {
				creditCard.setEnabled(false);
				this.saveOrUpdate(creditCard);
				log.info("Unable to delete credit card, marking it disabled: {}", creditCard);
				return false;
			}
		}
		throw new RuntimeException("EntityNotFound");
	}

	private boolean canBeDeleted(String uuid) 
	{
		log.trace("checking if credit card can be purged - uuid={} ...", uuid);

		if (getAuthorizationService()!=null)
		{
			if (uuid==null || uuid.equalsIgnoreCase(""))
				throw new RuntimeException("credit card with uuid null or empty");
			
			Search searchCriteria = new Search();
			searchCriteria.addFilterEqual("creditCard.uuid", uuid);
			searchCriteria.setMaxResults(1);
			List<AuthorizationHotel> list = getAuthorizationService().findAll(searchCriteria);
			 
			if (!list.isEmpty())
			{
				log.debug("The credit card has authorizations, can not be deleted");
				return false;
			}
		}
		

		log.debug("The credit card with uuid '{}' has no authorizations, and can be deleted", uuid);
		return true;
	}

	public CreditCard encrypt(CreditCard creditCard) {
		return saveOrUpdate(creditCard);
	}

	public List<CreditCard> findByCriteria(SearchCriteria searchCriteria) {
		return findByCriteria(searchCriteria, false);
	}

	public List<CreditCard> findByCriteria(SearchCriteria searchCriteria, boolean decrypt) {

		String propertyName = "enabled";
		boolean containsFilter = containsFilter(searchCriteria, propertyName);
		if (!containsFilter)
			((Search) searchCriteria).addFilterEqual("enabled", true);

		((Search) searchCriteria).setMaxResults(1);

		log.debug("Finding by:{}", searchCriteria);
		List<CreditCard> list = getCreditCardDao().findByCriteria(searchCriteria);

		if (decrypt) {
			for (Iterator<CreditCard> iterator = list.iterator(); iterator.hasNext();) {
				CreditCard creditCard = (CreditCard) iterator.next();
				decrypt(creditCard);
			}
		}

		log.trace("Found {} Credit Card records", list.size());
		return list;

	}

	/**
	 * It is True if the propertyName was included in the searchCriteria
	 * 
	 * @param searchCriteria
	 * @param propertyName
	 * @return
	 */
	private boolean containsFilter(SearchCriteria searchCriteria, String propertyName) {
		Search search = (Search) searchCriteria;
		List<Filter> filters = search.getFilters();
		boolean containsFilter = false;
		for (Iterator<Filter> iterator = filters.iterator(); iterator.hasNext();) {
			Filter filter = iterator.next();
			if (filter.getProperty().equalsIgnoreCase(propertyName)) {
				containsFilter = true;
				break;
			}
		}
		return containsFilter;
	}

	public CreditCard findFirstByCriteria(SearchCriteria searchCriteria) {
		return findFirstByCriteria(searchCriteria, false);
	}

	public CreditCard findFirstByCriteria(SearchCriteria searchCriteria, boolean decrypt) {
		((Search) searchCriteria).setMaxResults(1);

		List<CreditCard> list = findByCriteria(searchCriteria, decrypt);

		if (!list.isEmpty()) {
			CreditCard creditCard = list.get(0);
			return creditCard;
		}

		return null;
	}

	public CreditCard load(String creditCardUuid, boolean decrypt) {

		String action = decrypt ? "decrypt" : "load";
		log.debug("Attempting to {} cc with uuid: '{}'", action, creditCardUuid);

		if (creditCardUuid==null || creditCardUuid.equalsIgnoreCase(""))
			throw new RuntimeException("credit card with uuid null or empty");
		
		Search searchCriteria = new Search(CreditCardImpl.class);
		searchCriteria.addFilter(Filter.equal("uuid", creditCardUuid));
		CreditCard creditCard = findFirstByCriteria(searchCriteria, decrypt);

		if (creditCard != null && decrypt) {
			decrypt(creditCard);
		}

		log.info("Successfully {}ed cc: {}", new Object[] { action, creditCardUuid, creditCard });
		return creditCard;
	}

	public boolean purge(CreditCard creditCard) {
		throw new UnsupportedOperationException();
	}

	public boolean purge(String uuid) {
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
	

	public CreditCard saveOrUpdate(CreditCard creditCard, String businessUnitCode, boolean autoCreateBusinessUnit,IndustryType industryType) 
	{
		log.debug("Attempting to save credit card...: {}", creditCard);
		
		// validate the credit card to be stored
		if (this.getCreditCardValidator() instanceof CreditCardValidator)
		{
			final Map<String, String> errors = this.getCreditCardValidator().validate(creditCard);
			if (errors.size() > 0)
			{
				final CreditCardValidationException ex = new CreditCardValidationException();
				ex.setErrors(errors);
				throw ex;
			}
		}
		
		String token = null;
		String creditCardNumber = null;

		BusinessUnit businessUnit = getBusinessUnitService().resolveBusinessUnit(businessUnitCode,autoCreateBusinessUnit,industryType);
		
		// if the credit card has not been persisted yet
		if (((CreditCardImpl) creditCard).getId() == ((CreditCardImpl) creditCard).UNSAVED_ID) 
		{

			String cardNumberHash = hashNumber(creditCard.getNumber());

			if (businessUnit==null)
				throw new RuntimeException("businessUnit is null");
			
			if (cardNumberHash==null || cardNumberHash.equals(""))
				throw new RuntimeException("cardNumberHash is null or empty");
			
			Search searchCriteria = new Search();
			searchCriteria.addFilterEqual("businessUnit",   businessUnit);
			searchCriteria.addFilterEqual("cardNumberHash", cardNumberHash);
			CreditCard creditCardExisting = findFirstByCriteria(searchCriteria,true);
			
			if (creditCardExisting!=null)
			{
				creditCardExisting.setBillingAddress(creditCard.getBillingAddress());
				creditCardExisting.setCardholderName(creditCard.getCardholderName());
				creditCardExisting.setExpirationDate(creditCard.getExpirationDate());
				creditCardExisting.setSecurityCode(creditCard.getSecurityCode());
				creditCardExisting.setType(creditCard.getType());
				creditCardExisting.setSwiped(creditCard.isSwiped());
				creditCard = creditCardExisting;
			}
			else
			{
				token = getEncryptedStorageService().encryption(creditCard.getNumber());
				creditCard.setToken(token);
				log.trace("Token created: {}", token);
	
				creditCard.setNumberMasked(maskNumber(creditCard.getNumber()));
				((CreditCardImpl)creditCard).setCardNumberHash(cardNumberHash);
				creditCard.setNumber(null);
				creditCard.setEnabled(true);
				log.trace("CC masked: {}", creditCard.getNumberMasked());
			}
		}

		// if the credit card was persisted and its number is not null
		// It is possible that the number was changed
		if (((CreditCardImpl) creditCard).getId() != ((CreditCardImpl) creditCard).UNSAVED_ID && creditCard.getNumber() != null) 
		{
			log.warn("Attempting to update previously stored Credit Card number");

			token = getEncryptedStorageService().encryption(creditCard.getNumber());
			creditCard.setToken(token);
			log.trace("Token created: {}", token);

			creditCard.setNumberMasked(maskNumber(creditCard.getNumber()));
			((CreditCardImpl)creditCard).setCardNumberHash(hashNumber(creditCard.getNumber()));
			creditCard.setNumber(null);
			log.trace("CC masked: {}", creditCard.getNumberMasked());
		}
		
		((CreditCardImpl)creditCard).setBusinessUnit(businessUnit);
		
		creditCard = (CreditCard) getCreditCardDao().saveOrUpdate((CreditCardImpl) creditCard);
		log.info("Successfully saved credit card '{}'", creditCard);
		return creditCard;

	}

	private String hashNumber(String string) {
		return getEncryptorOneWay().digest(string).toString();
	}

	public String maskNumber(String num) {
		if (this.getCreditCardMask() == null) {
			throw new IllegalStateException("CreditCardMask has not been instantiated");
		}
		return this.getCreditCardMask().maskNumber(num);
	}

	public CreditCard decrypt(String creditCardUuid) {
		CreditCard creditCard = load(creditCardUuid, true);
		return creditCard;
	}

	public CreditCardMask getCreditCardMask() {
		return creditCardMask;
	}

	public void setCreditCardMask(CreditCardMask creditCardMask) {
		this.creditCardMask = creditCardMask;
	}

	public void setCreditCardDao(CreditCardDao creditCardDao) {
		this.creditCardDao = creditCardDao;
	}

	public CreditCardDao getCreditCardDao() {
		return creditCardDao;
	}

	public void setEncryptedStorageService(EncryptedStorageService<String, String> encryptedStorageService) {
		this.encryptedStorageService = encryptedStorageService;
	}

	public EncryptedStorageService<String, String> getEncryptedStorageService() {
		return encryptedStorageService;
	}

	public void setBusinessUnitService(BusinessUnitService<SearchCriteria> businessUnitService) {
		this.businessUnitService = businessUnitService;
	}

	public BusinessUnitService<SearchCriteria> getBusinessUnitService() {
		return businessUnitService;
	}

	public void setEncryptorOneWay(StringDigester encryptorOneWay) {
		this.encryptorOneWay = encryptorOneWay;
	}

	public StringDigester getEncryptorOneWay() {
		return encryptorOneWay;
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
		return this.getCreditCardValidator().isValid(aCreditCard);
	}

	public Map<String, String> validate(CreditCard aCreditCard)
	{
		return this.getCreditCardValidator().validate(aCreditCard);
	}
	
	public void setAuthorizationService(AuthorizationHotelService authorizationHotelService) {
		this.authorizationService = authorizationHotelService;
	}

	public AuthorizationHotelService getAuthorizationService() {
		return authorizationService;
	}

}
