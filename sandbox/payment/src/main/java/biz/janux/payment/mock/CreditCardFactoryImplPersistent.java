package biz.janux.payment.mock;

import java.util.Date;

import org.janux.bus.search.SearchCriteria;

import biz.janux.geography.PostalAddress;
import biz.janux.geography.PostalAddressImpl;

import biz.janux.payment.*;

import com.trg.search.Search;

public class CreditCardFactoryImplPersistent implements CreditCardFactory{

	private CreditCardTypeService<SearchCriteria> creditCardTypeService; 
	
	public CreditCard getCreditCardVisa() {
		CreditCard creditCard = new CreditCardImpl();
		
		PostalAddress postalAddress = getPostalAddress();
		
		creditCard.setBillingAddress(postalAddress);
		creditCard.setCardholderName(CreditCardConstants.CARD_HOLDER_NAME);
		creditCard.setExpirationDate(new Date());
		creditCard.setNumber(CreditCardConstants.NUMBER_VISA);
		creditCard.setSwiped(true);
		
		Search searchCriteria = new Search();
		searchCriteria.addFilterEqual("code", CreditCardTypeEnum.VISA.getCode());
		CreditCardType creditCardType = getCreditCardTypeService().findFirstByCriteria(searchCriteria);

		creditCard.setType(creditCardType);
		
		return creditCard;
	}

	/**
	 * @return
	 */
	private PostalAddress getPostalAddress() {
		PostalAddress postalAddress = new PostalAddressImpl();
		postalAddress.setCityAsString(CreditCardConstants.CITY);
		postalAddress.setCountryAsString(CreditCardConstants.COUNTRY_STRING);
		postalAddress.setLine1(CreditCardConstants.ADDRESS);
		postalAddress.setPostalCode(CreditCardConstants.POSTAL_CODE);
		postalAddress.setStateProvinceAsString(CreditCardConstants.STATE_STRING);
		return postalAddress;
	}
	
	public CreditCard getCreditCardAmericanExpress() {
		CreditCard creditCard = new CreditCardImpl();
		
		PostalAddress postalAddress = getPostalAddress();
		
		creditCard.setBillingAddress(postalAddress);
		creditCard.setCardholderName(CreditCardConstants.CARD_HOLDER_NAME);
		creditCard.setExpirationDate(new Date());
		creditCard.setNumber(CreditCardConstants.NUMBER_AMERICAN_EXPRESS);
		creditCard.setSwiped(true);
		
		Search searchCriteria = new Search();
		searchCriteria.addFilterEqual("code", CreditCardTypeEnum.AMERICAN_EXPRESS.getCode());
		CreditCardType creditCardType = getCreditCardTypeService().findFirstByCriteria(searchCriteria);

		creditCard.setType(creditCardType);
		
		return creditCard;
	}

	public void setCreditCardTypeService(CreditCardTypeService<SearchCriteria> creditCardTypeService) {
		this.creditCardTypeService = creditCardTypeService;
	}

	public CreditCardTypeService<SearchCriteria> getCreditCardTypeService() {
		return creditCardTypeService;
	}

	public CreditCard getCreditCard(String arg0, String arg1, Date arg2, String arg3, PostalAddress arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	public CreditCard getCreditCardCompleteVisa() {
		// TODO Auto-generated method stub
		return null;
	}

	public CreditCard getCreditCardSavedVisa() {
		// TODO Auto-generated method stub
		return null;
	}

}

