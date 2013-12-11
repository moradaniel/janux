package biz.janux.payment.mock;

import org.springframework.beans.factory.annotation.Autowired;

import biz.janux.payment.BusinessUnit;
import biz.janux.payment.BusinessUnitImpl;
import biz.janux.payment.IndustryType;
import biz.janux.payment.MerchantAccount;
import biz.janux.payment.MerchantAccountFactory;

public class BusinessUnitFactoryImpl implements BusinessUnitFactory{

	@Autowired
	MerchantAccountFactory merchantAccountFactory;
	
	public BusinessUnit getBusinessUnit() {
		
		BusinessUnit businessUnit = new BusinessUnitImpl();
		businessUnit.setIndustryType(BusinessUnitConstants.INDUSTRY_TYPE);
		businessUnit.setName(BusinessUnitConstants.NAME);
		businessUnit.setCode(BusinessUnitConstants.CODE);
		
		MerchantAccount merchantAccount = getMerchantAccountFactory().getMerchantAccount();

		businessUnit.setEnabled(true);
		businessUnit.setMerchantAccount(merchantAccount);

		return businessUnit;
		
	}

	public BusinessUnit getVitalBusinessUnit() {
		BusinessUnit businessUnit = new BusinessUnitImpl();
		businessUnit.setIndustryType(BusinessUnitConstants.INDUSTRY_TYPE);
		businessUnit.setName(BusinessUnitConstants.NAME);
		businessUnit.setCode(BusinessUnitConstants.CODE);
		
		MerchantAccount merchantAccount = getMerchantAccountFactory().getVitalMerchantAccount();
		businessUnit.setEnabled(true);
		businessUnit.setMerchantAccount(merchantAccount);
		
		return businessUnit;
	}

	public MerchantAccountFactory getMerchantAccountFactory() {
		return merchantAccountFactory;
	}

	public void setMerchantAccountFactory(MerchantAccountFactory merchantAccountFactory) {
		this.merchantAccountFactory = merchantAccountFactory;
	}

}
