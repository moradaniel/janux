package biz.janux.payment.mock;

import java.util.Date;

import org.janux.bus.persistence.Identifiable;

import biz.janux.geography.Country;
import biz.janux.geography.CountryImpl;
import biz.janux.geography.PostalAddress;
import biz.janux.geography.PostalAddressImpl;
import biz.janux.geography.StateProvince;
import biz.janux.geography.StateProvinceImpl;
import biz.janux.payment.*;

import java.text.SimpleDateFormat;

public class CreditCardFactoryImpl implements CreditCardFactory{

	public CreditCard getCreditCardVisa() {		
		PostalAddress postalAddress = getPostalAddress();
		return getCreditCard(CreditCardConstants.CARD_HOLDER_NAME,CreditCardConstants.NUMBER_VISA, CreditCardConstants.EXPIRATION_DATE, CreditCardTypeEnum.VISA.getCode(),postalAddress);
	}
	
	public CreditCard getCreditCard(String holderName,String number,Date expirationDate,String typeCode,PostalAddress postalAddress) {
		CreditCard creditCard = new CreditCardImpl();
		
		creditCard.setBillingAddress(postalAddress);
		creditCard.setCardholderName(holderName);
		creditCard.setExpirationDate(expirationDate);
		creditCard.setNumber(number);
		
		CreditCardType creditCardType;
		creditCardType = new CreditCardTypeImpl();
		creditCardType.setCode(typeCode);
		
		creditCard.setType(creditCardType);
		
		creditCard.setSwiped(true);
		
		return creditCard;
	}

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
		PostalAddress postalAddress = getPostalAddress();
		return getCreditCard(CreditCardConstants.CARD_HOLDER_NAME,CreditCardConstants.NUMBER_AMERICAN_EXPRESS,new Date(),CreditCardTypeEnum.AMERICAN_EXPRESS.getCode(),postalAddress);
	}

	public CreditCard getCreditCardSavedVisa() {
		CreditCard creditCard = getCreditCardVisa();
		creditCard.setNumber(null);
		((Identifiable)creditCard).setUuid(CreditCardConstants.UUID);
		creditCard.setNumberMasked(CreditCardConstants.NUMBER_VISA_MASKED);
		creditCard.setToken(CreditCardConstants.CREDIT_CARD_VISA_TOKEN);
		creditCard.setEnabled(true);
		creditCard.setSwiped(true);
		return creditCard;
	}
	
	public CreditCard getCreditCardCompleteVisa() {
		CreditCard creditCard = getCreditCardVisa();
		((Identifiable)creditCard).setUuid(CreditCardConstants.UUID);
		creditCard.setNumberMasked(CreditCardConstants.NUMBER_VISA_MASKED);
		return creditCard;
	}

}
