package biz.janux.commerce.payment.interfaces.model;

public interface Merchant {
	public enum VENDORTYPE {VITAL, TSYS};
		
	public String getAcqBankIdentificationNumber() ;

	public void setAcqBankIdentificationNumber(String acqBankIdentificationNumber) ;

	public String getAgentBankNumber() ;	

	public void setAgentBankNumber(String agentBankNumber) ;

	public String getCardholderServiceNumber() ;

	public void setCardholderServiceNumber(String cardholderServiceNumber) ;

	public String getChainNumber() ;

	public void setChainNumber(String chainNumber) ;

	public String getCityCode();

	public void setCityCode(String cityCode) ;

	public String getCountryCode() ;

	public void setCountryCode(String countryCode);

	public String getCurrencyCode() ;

	public void setCurrencyCode(String currencyCode);

	public String getCity() ;

	public void setCity(String city);

	public String getLocalPhoneNumber() ;

	public void setLocalPhoneNumber(String localPhoneNumber) ;

	public String getLocation();

	public void setLocation(String location);

	public String getName() ;

	public void setName(String name);

	public String getNumber() ;

	public void setNumber(String number);

	public String getState() ;

	public void setState(String state);

	public String getStoreNumber() ;

	public void setStoreNumber(String storeNumber);

	public String getTerminalID() ;

	public void setTerminalID(String terminalID);

	public String getTerminalNumber() ;

	public void setTerminalNumber(String terminalNumber) ;

	public String getTimeZoneDifferential() ;

	public void setTimeZoneDifferential(String timeZoneDifferential);
	
	public VENDORTYPE getVendorType();
	
	public void setVendorType(VENDORTYPE vendorType); 
	
}
