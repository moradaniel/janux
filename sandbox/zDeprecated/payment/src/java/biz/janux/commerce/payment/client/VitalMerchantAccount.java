package biz.janux.commerce.payment.client;

import java.util.TimeZone;
import biz.janux.commerce.payment.implementation.vendor.vital.helper.VitalHelper;
import biz.janux.commerce.payment.model.Country;
import biz.janux.commerce.payment.model.State;
import biz.janux.commerce.payment.util.DataFormattingUtil;
 

/**
 * 
 * @author Nilesh
 * @extends MerchantAccount
 * 
 * DB Model for Merchant Acccount based on the Vital implementation
 * 
 * */
public class VitalMerchantAccount extends MerchantAccount {

	private long id; 
	
	private String acqBankIdentificationNumber;
	
	private String agentBankNumber;
	
	private String cardholderServiceNumber;
	
	private String chainNumber;
		
	private String city;
	
	private String cityCode;
	
	private Country country ;	
	
	private String currencyCode;
	
	private String localPhoneNumber;
	
	private String location;
	
	private String name;
	
	private String number;
	
	private State state;
	
	private String storeNumber;
	
	private String terminalID;
	
	private String terminalNumber;
	
	private String timeZoneDifferential;
	
	private String merchenttimezone;
	
	private String UUID;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
	/**
	 * @return String 
	 * Bank Identification Number
	 * */ 
	public String getAcqBankIdentificationNumber() {
		return acqBankIdentificationNumber;
	}

	/**
	 * @param acqBankIdentificationNumber 
	 * Sets the Bank Identification Number
	 */
	public void setAcqBankIdentificationNumber(String acqBankIdentificationNumber) {
		this.acqBankIdentificationNumber = acqBankIdentificationNumber;
	}

	/**
	 * @return String 
	 * Agent Account Number
	 * */ 
	public String getAgentBankNumber() { 
		 return DataFormattingUtil.rightJustifyZero(agentBankNumber, 6);
	}

	/**
	 * @param agentBankNumber 
	 * Sets the Agent Account Number
	 */
	public void setAgentBankNumber(String agentBankNumber) {
		this.agentBankNumber = agentBankNumber;
	}

	/**
	 * @return String 
	 * Card Holder Service Number
	 * */ 
	public String getCardholderServiceNumber() {
		
		return DataFormattingUtil.convertToVitalPhoneFormat(cardholderServiceNumber);
		 
	}

	/**
	 * @param cardholderServiceNumber
	 *  Sets the Card Holder Service Number
	 */
	public void setCardholderServiceNumber(String cardholderServiceNumber) {
		this.cardholderServiceNumber = cardholderServiceNumber;
	}

	/**
	 * @return String 
	 * Chain Number
	 * */ 
	public String getChainNumber() {
		return DataFormattingUtil.rightJustifyZero(chainNumber, 6);
	}

	/**
	 * @param chainNumber Sets the Chain Number
	 */
	public void setChainNumber(String chainNumber) {
		this.chainNumber = chainNumber;
	}

	/**
	 * @return String 
	 * City
	 * */ 
	public String getCity() {
		 return DataFormattingUtil.leftJustifySpace(city, 13);
	}

	/**
	 * @param string Sets the City
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return String 
	 *  City Code
	 * */ 
	public String getCityCode() {
		 return DataFormattingUtil.leftJustifySpace(cityCode, 9);
	}

	/**
	 * @param string Sets the City Code
	 * 
	 **/
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

//	/**
//	 * @return String 
//	 * Country Code
//	 * */ 
//	public String getCountryCode() {
//		return countryCode;
//	}
//
//	/**
//	 * @param countryCode 
//	 * Sets the Country Code
//	 */
//	public void setCountryCode(String countryCode) {
//		this.countryCode = countryCode;
//	}

	/**
	 * @return String 
	 *  Currency Code
	 * */ 
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode 
	 * Sets the Currency Code
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return String 
	 *  Local Phone Number
	 * */ 
	public String getLocalPhoneNumber() {
		   return DataFormattingUtil.convertToVitalPhoneFormat(localPhoneNumber);
	}

	/**
	 * @param localPhoneNumber 
	 * Sets the Local Phone Number
	 */
	public void setLocalPhoneNumber(String localPhoneNumber) {
		this.localPhoneNumber = localPhoneNumber;
	}

	/**
	 * @return String 
	 *  Location
	 * */ 
	public String getLocation() {
 		 return DataFormattingUtil.leftJustifySpace(location, 13);
 		 
	}

	/**
	 * @param location 
	 * Sets the Location
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	 
	/**
	 * @return String 
	 * Name
	 * */ 
	public String getName() {
		return DataFormattingUtil.leftJustifySpace(name, 25);
	}

	/**
	 * @param name
	 *  Sets the Name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return String 
	 *  Number
	 * */ 
	public String getNumber() {
		 return DataFormattingUtil.rightJustifyZero(number, 12);
		 
	}

	/**
	 * @param number Sets the Number
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	
	/**
	 * @return String 
	 * Store Number
	 * */ 
	public String getStoreNumber() {
		return DataFormattingUtil.rightJustifyZero(storeNumber, 4);
	}

	/**
	 * @param storeNumber Sets the Store Number
	 */
	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

	/**
	 * @return String 
	 * Terminal Id
	 * */ 
	public String getTerminalID() {
		return DataFormattingUtil.rightJustifyZero(terminalID, 8);
	}

	/**
	 * @param terminalID Sets the Terminal Id
	 */
	public void setTerminalID(String terminalID) {
		this.terminalID = terminalID;
	}

	/**
	 * @return String 
	 * Terminal Number
	 * */ 
	public String getTerminalNumber() {
		 return DataFormattingUtil.rightJustifyZero(terminalNumber, 4); 
	}

	/**
	 * @param terminalNumber Sets the Terminal Number
	 */
	public void setTerminalNumber(String terminalNumber) {
		this.terminalNumber = terminalNumber;
	}
	

	/**
	 * @return String 
	 * Time Zone differential
	 * */ 
	public String getTimeZoneDifferential() {
		return timeZoneDifferential;
	}

	/**
	 * @param timeZoneDifferential Sets the Time Zone differential
	 */
	
	TimeZone timezone;

	public TimeZone getTimezone() {
		return timezone;
	}

	public void setTimezone(TimeZone timezone) {
		this.timezone = timezone;
	}
	public void setTimeZoneDifferential(String timeZoneDifferential) {
		setTimezone(TimeZone.getTimeZone(timeZoneDifferential));
		this.timeZoneDifferential = VitalHelper.timeZoneToVitalTZStr(getTimezone());
	}

	public String getMerchenttimezone() {
		return merchenttimezone;
	}

	public void setMerchenttimezone(String merchenttimezone) {
		this.merchenttimezone = merchenttimezone;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	
	/*
	 * This is used to give the unicode character for a currency in 3 character
	 * (ISO?) format. i.e. USD = United States Dollars, GBP = Great Britain
	 * Pounds, etc. See www.oanda.com for other codes! See ISO 4217
	 * (http://www.xe.com/iso4217.htm)
	 * 
	 * Please Note: ISO 4217: MXN (prior to 1993: MXP) for Mexican Peso
	 */
	public static char getCurrencySymbol(String currencyCode) {
//		if (logger.isDebugEnabled()) {
//			logger
//					.debug("getCurrencySymbol(currencyCode=" + currencyCode
//							+ ")");
//		}

		// Default, USD, $
		// NOTE: Mexican Peso (MXN/(MXP?)) uses $
		char retVal = '\u0024';

		// Great Britain - Pound
		if (currencyCode.equals("GBP")) {
			retVal = '\u00A3';
		}
		// Japan - Yen
		else if (currencyCode.equals("JPY")) {
			retVal = '\u00A5';
		}
		// European Nation - Euro
		else if (currencyCode.equals("EUR")) {
			retVal = '\u20AC';
		}
		// China - Yuan
		else if (currencyCode.equals("CNY")) {
			// Same as YEN
			retVal = '\u00A5';
		}
		// India - INR
		else if (currencyCode.equals("INR"))
			retVal = '\u20A8';

		return retVal;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String UUID) {
		this.UUID=UUID;
	}
	
 }
