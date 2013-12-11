package biz.janux.payment.gateway.controllers.creditcard;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.janux.bus.search.SearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import biz.janux.geography.GeographyService;
import biz.janux.geography.PostalAddress;
import biz.janux.payment.CreditCard;
import biz.janux.payment.CreditCardStorageService;
import biz.janux.payment.CreditCardType;
import biz.janux.payment.CreditCardTypeService;

import com.trg.search.Search;

@Controller
public class CreditCardControllerRest {

	Logger log = LoggerFactory.getLogger(this.getClass());

	private	CreditCardStorageService<SearchCriteria> creditCardService;
	
	private CreditCardTypeService<SearchCriteria> creditCardTypeService;
	
	private GeographyService geographyService;
	
	@Autowired
	@Qualifier("januxObjectMapper")
	private ObjectMapper objectMapper; 
	
	@RequestMapping(params = "load", method = RequestMethod.GET)
	public void loadCreditCard(
			@RequestParam("pay_uuid") String pay_uuid,
			ModelMap modelMap)
	{
		boolean decrypt = false;
		CreditCard creditCard = getCreditCardService().load(pay_uuid, decrypt);
		
		if (creditCard!=null)
		{
			modelMap.addAttribute("creditCard",creditCard);
		}
	}
	
	@RequestMapping(params = "decrypt", method = RequestMethod.GET)
	public void decryptCreditCard(
			@RequestParam("pay_uuid") String pay_uuid,
			ModelMap modelMap)
	{
		boolean decrypt = true;
		CreditCard creditCard = getCreditCardService().load(pay_uuid, decrypt);
		log.debug("in decrypt cc is: {}", creditCard);
		
		if (creditCard!=null)
		{
			modelMap.addAttribute("creditCard",creditCard);
		}
	}
	
	
	@RequestMapping(params = "save", method = {RequestMethod.POST,RequestMethod.GET})
	public void saveCreditCard(
			@RequestParam(value="pay_uuid",              required=false) String     pay_uuid,
			@RequestParam(value="creditCard",            required=true)  CreditCard creditCard,
			@RequestParam(value="businessUnitCode",      required=false) String     businessUnitCode,
			@RequestParam(value="autoCreateBusinessUnit",required=false) boolean    autoCreateBusinessUnit,
			ModelMap modelMap) 
	{
		// set a existing creditCardType to deserialized creditCard
		if (log.isDebugEnabled()) {log.debug("calling saveCreditCard with pay_uuid={}, businessUnitCode={},autoCreateBusinessUnit={}, {}", new Object[] {pay_uuid, businessUnitCode, autoCreateBusinessUnit, creditCard});}
		creditCard = saveCreditCard(pay_uuid, creditCard, businessUnitCode, autoCreateBusinessUnit);
		modelMap.addAttribute("creditCard", creditCard);
	}

	@RequestMapping(params = "saveList", method = {RequestMethod.POST,RequestMethod.GET})
	public void saveCreditCard(
			@RequestParam(value="creditCards",           required=true)  ArrayList<CreditCard> creditCards,
			@RequestParam(value="businessUnitCode",      required=false) String     businessUnitCode,
			@RequestParam(value="autoCreateBusinessUnit",required=false) boolean    autoCreateBusinessUnit,
			ModelMap modelMap) 
	{
		// set a existing creditCardType to deserialized creditCard
		log.debug("calling saveCreditCard with businessUnitCode={},autoCreateBusinessUnit={}, {}", new Object[] {businessUnitCode, autoCreateBusinessUnit, creditCards});
		List<CreditCard> creditCardsSaved = new ArrayList<CreditCard>();
		for (int i = 0; i < creditCards.size(); i++) {
			CreditCard creditCard = creditCards.get(i);
			try {
				CreditCard creditCardSaved = saveCreditCard(null, creditCard, businessUnitCode, autoCreateBusinessUnit);
				creditCardsSaved.add(creditCardSaved);
			} catch (Exception e) {
				/**
				 * TODO abuffagni:20120103:improve the way to return a error message.
				 */
				log.error("Error saving credit card: {}, error: {}",creditCard,e.getMessage());
				log.error("Error saving credit card",e);
				creditCardsSaved.add(creditCard);
			}
		}
		modelMap.addAttribute("creditCards", creditCardsSaved);
	}
	
	@RequestMapping(params = "delete", method = RequestMethod.GET)
	public void deleteCreditCard(
			@RequestParam("pay_uuid") String pay_uuid,
			ModelMap modelMap)
	{
		boolean deleteOrDisable = getCreditCardService().deleteOrDisable(pay_uuid);
		modelMap.addAttribute("deleteOrDisable",deleteOrDisable);
	}

	/**
	 * @param pay_uuid
	 * @param creditCard
	 * @param businessUnitCode
	 * @param autoCreateBusinessUnit
	 * @return
	 */
	private CreditCard saveCreditCard(String pay_uuid, CreditCard creditCard, String businessUnitCode, boolean autoCreateBusinessUnit) {
		CreditCardType creditCardType = getCreditCardTypeService().findByCode(creditCard.getTypeCode());
		creditCard.setType(creditCardType);

		// set the billingAddress of the deserialized creditCard  
		/*
		if (creditCard.getBillingAddress()!=null)
		{
			PostalAddress billingAddress = creditCard.getBillingAddress();
		
			Country country = getGeographyService().findCountryByCode(creditCard.getBillingAddress().getCountryCode());
			StateProvince stateProvince = getGeographyService().findStateByCode(creditCard.getBillingAddress().getCountryCode(),creditCard.getBillingAddress().getCountryCode());
			if (stateProvince!=null)
			{
				stateProvince.setCountry(country);
				billingAddress.setStateProvinceAsString(stateProvince.getName());
				billingAddress.setCountryAsString(country.getName());
			}
		}
		*/
		
		CreditCard creditCardExisting = null;
		if (pay_uuid!=null && !pay_uuid.equals(""))
		{
			Search searchCriteria = new Search();
			searchCriteria.addFilterEqual("uuid",pay_uuid);
			creditCardExisting = getCreditCardService().load(pay_uuid,false);
		}
				
		if (creditCardExisting!=null)
		{
			creditCardExisting.setCardholderName(creditCard.getCardholderName());
			
			// Updates all fields of the existing credit card
			PostalAddress billingAddressExisting = creditCardExisting.getBillingAddress();
			PostalAddress billingAddress = creditCard.getBillingAddress();
			if (billingAddressExisting!=null && billingAddress!=null)
			{
				billingAddressExisting.setCityAsString(billingAddress.getCityAsString());
				
/*				Country country = billingAddress.getCountry();
				
				StateProvince stateProvince = billingAddress.getStateProvince();
				if (stateProvince!=null)
				{
					stateProvince.setCountry(country);
					billingAddressExisting.setStateProvinceAsString(stateProvince.getName());
					billingAddressExisting.setCountryAsString(country.getName());
				}
*/
				
				billingAddressExisting.setStateProvinceAsString(billingAddress.getStateProvinceAsString());
				billingAddressExisting.setCountryAsString(billingAddress.getCountryAsString());
				billingAddressExisting.setLine1(billingAddress.getLine1());
				billingAddressExisting.setLine2(billingAddress.getLine2());
				billingAddressExisting.setLine3(billingAddress.getLine3());
				billingAddressExisting.setPostalCode(billingAddress.getPostalCode());

			}
			else 
				billingAddressExisting = creditCard.getBillingAddress();
			
			creditCardExisting.setBillingAddress(billingAddressExisting);
			
			if (businessUnitCode==null)
				creditCardExisting =  getCreditCardService().saveOrUpdate(creditCardExisting);
			else
				creditCardExisting =  getCreditCardService().saveOrUpdate(creditCardExisting,businessUnitCode,autoCreateBusinessUnit,null);
			
			creditCardExisting.setSwiped(creditCard.isSwiped());
			
			creditCard = creditCardExisting;
		}
		else
		{
			if (businessUnitCode==null)
				creditCard = getCreditCardService().saveOrUpdate(creditCard);
			else
				creditCard = getCreditCardService().saveOrUpdate(creditCard,businessUnitCode,autoCreateBusinessUnit,null);
		}
		return creditCard;
	}

	/** Simply returns the view 'ping' to verify that the creditCard app is accessible */
	@RequestMapping(params = "ping", method = RequestMethod.GET)
	public String ping()
	{
		return "ping";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		binder.registerCustomEditor(ArrayList.class, new JsonPropertyEditor(new TypeReference<ArrayList<CreditCard>>(){},getObjectMapper()));
	    binder.registerCustomEditor(CreditCard.class, new JsonPropertyEditor(new TypeReference<CreditCard>(){},getObjectMapper()));
	}

	public CreditCardStorageService<SearchCriteria> getCreditCardService() {
		return creditCardService;
	}

	public void setCreditCardService(
			CreditCardStorageService<SearchCriteria> creditCardService) {
		this.creditCardService = creditCardService;
	}

	public CreditCardTypeService<SearchCriteria> getCreditCardTypeService() {
		return creditCardTypeService;
	}

	public void setCreditCardTypeService(
			CreditCardTypeService<SearchCriteria> creditCardTypeService) {
		this.creditCardTypeService = creditCardTypeService;
	}

	public GeographyService getGeographyService() {
		return geographyService;
	}

	public void setGeographyService(GeographyService geographyService) {
		this.geographyService = geographyService;
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}
}
