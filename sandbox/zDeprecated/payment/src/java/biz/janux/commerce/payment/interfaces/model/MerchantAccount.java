package biz.janux.commerce.payment.interfaces.model;

import java.util.TimeZone;

import biz.janux.commerce.payment.model.Country;
import biz.janux.commerce.payment.model.State;

/**
 * @author Nilesh
 * Interface for MerchantAccount
 *
 */
public interface MerchantAccount {
	public enum VENDORTYPE {VITAL, TSYS};
	/**
	 * 
	 * @return long
	 */
	public long getId();
	/**
	 * 
	 * @param long MerchantId
	 */
	public void setId(long id);
	/**
	 * 
	 * @return String
	 */	
	public String getAcqBankIdentificationNumber() ;
	/*
	 * @param String
	 * 
	 */
	public void setAcqBankIdentificationNumber(String acqBankIdentificationNumber) ;
	/**
	 * 
	 * @return String
	 */
	public String getAgentBankNumber() ;	

	/*
	 * @Param String
	 * Set Agent Account number of the bank
	 */
	public void setAgentBankNumber(String agentBankNumber) ;
	/**
	 * 
	 * @return String
	 */
	public String getCardholderServiceNumber() ;
	/**
	 * 
	 * @param String cardholderServiceNumber
	 */
	public void setCardholderServiceNumber(String cardholderServiceNumber) ;
	/**
	 * 
	 * @return String
	 */	
	public String getChainNumber() ;
	/**
	 * 
	 * @param String chainNumber
	 */
	public void setChainNumber(String chainNumber) ;
	/**
	 * 
	 * @return String
	 */
	public String getCityCode();
	/**
	 * 
	 * @param String cityCode
	 */
	public void setCityCode(String cityCode) ;
	/**
	 * 
	 * @return object of Country
	 */
	public Country getCountry() ;
	/**
	 * 
	 * @param country
	 */
	public void setCountry(Country country);
	/**
	 * 
	 * @return String
	 */
	public String getCurrencyCode() ;
	/**
	 * 
	 * @param String currencyCode
	 */
	public void setCurrencyCode(String currencyCode);
	/**
	 * 
	 * @return String
	 */
	public String getCity() ;
	/**
	 * 
	 * @param String city
	 */
	public void setCity(String city);
	/**
	 * 
	 * @return String
	 */
	public String getLocalPhoneNumber() ;
	/**
	 * 
	 * @param String localPhoneNumber
	 */
	public void setLocalPhoneNumber(String localPhoneNumber) ;
	/**
	 * 
	 * @return string
	 */
	public String getLocation();
	/**
	 * 
	 * @param String location
	 */
	public void setLocation(String location);
	/**
	 * 
	 * @return String
	 */
	public String getName() ;
	/**
	 * 
	 * @param String name
	 */
	public void setName(String name);
	/*
	 * @return String
	 */
	public String getNumber() ;
	/**
	 * 
	 * @param String number
	 */
	public void setNumber(String number);
	/**
	 * 
	 * @return object of State
	 */
	public State getState() ;
	/**
	 * 
	 * @param state
	 */
	public void setState(State state);
	/**
	 * 
	 * @return String
	 */
	public String getStoreNumber() ;
	/**
	 * 
	 * @param String storeNumber
	 */
	public void setStoreNumber(String storeNumber);
	/**
	 * 
	 * @return String
	 */
	public String getTerminalID() ;
	/**
	 * 
	 * @param String terminalID
	 */
	public void setTerminalID(String terminalID);
	/**
	 * 
	 * @return String
	 */
	public String getTerminalNumber() ;
	/**
	 * 
	 * @param String terminalNumber
	 */
	public void setTerminalNumber(String terminalNumber) ;
	/**
	 * 
	 * @return String
	 */
	public String getTimeZoneDifferential() ;
	/**
	 * 
	 * @param timeZoneDifferential
	 */
	public void setTimeZoneDifferential(String timeZoneDifferential);
	/**
	 * 
	 * @return object of VENDORTYPE
	 */
	public VENDORTYPE getVendorType();
	/**
	 * 
	 * @param vendorType
	 */
	
	public void setVendorType(VENDORTYPE vendorType); 
	/**
	 * 
	 * @return TimeZone
	 */
	public TimeZone getTimezone();
	/**
	 * 
	 * @param timezone
	 */
	public void setTimezone(TimeZone timezone);
	/**
	 * 
	 * @return String
	 */
	public String getUUID();
	/**
	 * 
	 * @param UUID
	 */
	public void setUUID(String UUID);
}
