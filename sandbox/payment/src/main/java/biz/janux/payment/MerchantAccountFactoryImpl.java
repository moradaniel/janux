package biz.janux.payment;


public class MerchantAccountFactoryImpl implements MerchantAccountFactory{

	public MerchantAccount getMerchantAccount() {
		
		MerchantAccount merchantAccount = new MerchantAccountImpl();
		merchantAccount.setAcquiringBankBin(MerchantAccountConstants.ACQUIRING_BANK_BIN);
		merchantAccount.setName(MerchantAccountConstants.NAME);
		merchantAccount.setNumber(MerchantAccountConstants.NUMBER);
		merchantAccount.getMerchantAddress().setCityAsString(MerchantAccountConstants.CITY_NAME);
		merchantAccount.getMerchantAddress().setStateProvinceAsString(MerchantAccountConstants.MERCHANT_STATE);
		merchantAccount.setStoreNum(MerchantAccountConstants.STORE_NUM);
		merchantAccount.setTerminalNum(MerchantAccountConstants.TERMINAL_NUM);
		merchantAccount.getMerchantAddress().setPostalCode(MerchantAccountConstants.CITY_CODE);
		merchantAccount.setAgentBankNum(MerchantAccountConstants.AGENT_BANK_NUM);
		merchantAccount.setAgentChainNum(MerchantAccountConstants.AGENT_CHAIN_NUM);
		merchantAccount.setMerchantPhone("999-9999999");
		merchantAccount.setCardholderServicePhone("999-9999999");
		merchantAccount.setTerminalId("00000001");

		return merchantAccount;
	}
	
	public MerchantAccount getVitalMerchantAccount() {
		
		MerchantAccount merchantAccount = new MerchantAccountImpl();
		merchantAccount.setAcquiringBankBin(MerchantAccountConstants.ACQUIRING_BANK_BIN);
		merchantAccount.setName(MerchantAccountConstants.NAME);
		merchantAccount.setNumber(MerchantAccountConstants.NUMBER);
		merchantAccount.getMerchantAddress().setCityAsString(MerchantAccountConstants.CITY_NAME);
		merchantAccount.getMerchantAddress().setStateProvinceAsString(MerchantAccountConstants.MERCHANT_STATE);
		merchantAccount.setStoreNum(MerchantAccountConstants.STORE_NUM);
		merchantAccount.setTerminalNum(MerchantAccountConstants.TERMINAL_NUM);
		merchantAccount.getMerchantAddress().setPostalCode(MerchantAccountConstants.CITY_CODE);
		merchantAccount.setAgentBankNum(MerchantAccountConstants.AGENT_BANK_NUM);
		merchantAccount.setAgentChainNum(MerchantAccountConstants.AGENT_CHAIN_NUM);
		merchantAccount.setMerchantPhone("999-9999999");
		merchantAccount.setCardholderServicePhone("999-9999999");
		//merchantAccount.setTerminalId("77777777");
		merchantAccount.setTerminalId("00000001");
		
		/**
		 * custom for Vital
		 */
		merchantAccount.setCurrencyCode(MerchantAccountConstants.CURRENCY_CODE_USD);
		merchantAccount.getMerchantAddress().setCountryAsString(MerchantAccountConstants.COUNTRY_CODE_US);
		merchantAccount.setMerchantTimeZoneAsString(MerchantAccountConstants.TIME_ZONE_DIFFERENTIAL_PST);
				
		
		return merchantAccount;
		
	}

}
