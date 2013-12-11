package biz.janux.commerce.payment.Register;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import biz.janux.commerce.payment.client.VitalMerchantAccount;
import biz.janux.commerce.payment.implementation.common.RegistrationServiceImpl;
import biz.janux.commerce.payment.implementation.common.dao.hibernate.HibernateDao;
import biz.janux.commerce.payment.model.Country;
import biz.janux.commerce.payment.model.CreditCard;
import biz.janux.commerce.payment.model.CreditCardType;
import biz.janux.commerce.payment.model.State;
import biz.janux.commerce.payment.util.CreditCardUtil;

public class TestCaseEdit extends TestCase{
	
	
	public void testEditinstrument(){
		CreditCardUtil ccu= new CreditCardUtil();
		RegistrationServiceImpl rs= new RegistrationServiceImpl();
		CreditCardType creditCardType = new CreditCardType();
		creditCardType.setCreditCardTypeId(12);
		Country country = new Country();
		country.setCountryId(2);
		HibernateDao hibernateDao = HibernateDao.getInstance();
		CreditCard loadCreditCard = (CreditCard) rs.getInstrument(898L);
		loadCreditCard.setCardNumber("5466160153175375");	
		loadCreditCard.setCreditCardType(creditCardType);
		loadCreditCard.setAddress1(" New York");
		loadCreditCard.setAddress2("US");
		loadCreditCard.setCity("Dubai");
		loadCreditCard.setCountry(country);
		loadCreditCard.setExpirationDate("08/12");
		loadCreditCard.setHoldersName("JOHN L RUIZ");
		loadCreditCard.setLoggableInfo("my");
		loadCreditCard.setPhoneNumber("022-2459863");
		loadCreditCard.setState("New Mexico");
		loadCreditCard.setTrack1("track1");
		loadCreditCard.setTrack2("track2");
		loadCreditCard.setZip("8340029");
		long id =rs.editInstrument(loadCreditCard);
//		System.out.println("update row : "+id);
		assertEquals(loadCreditCard.getCreditCardId(),id);
	}
	
	public void testEditmerchent(){
		State st = new State();
		st.setStateId(57);
		Country cntry = new Country();
		RegistrationServiceImpl rs= new RegistrationServiceImpl();
		HibernateDao hibernateDao = HibernateDao.getInstance();
		VitalMerchantAccount loadMerchent= (VitalMerchantAccount) rs.getMerchantAccount(38L);
		loadMerchent.setAcqBankIdentificationNumber("999995");
	 	loadMerchent.setAgentBankNumber("1111111");
		loadMerchent.setCardholderServiceNumber("12345");
		loadMerchent.setChainNumber("000000");
		loadMerchent.setCity("New York");
		loadMerchent.setCityCode("99999");
		loadMerchent.setCountry(cntry);
		loadMerchent.setCurrencyCode("840");
		loadMerchent.setLocalPhoneNumber("999-9999999");
		loadMerchent.setLocation("Test City");
		loadMerchent.setName("Test Hotel");
		loadMerchent.setNumber("888000002329");
		loadMerchent.setState(st);
		loadMerchent.setStoreNumber("5999");
		loadMerchent.setTerminalID("77777777");
		loadMerchent.setTerminalNumber("1515");
		loadMerchent.setTimeZoneDifferential("America/Denver");
		long id =rs.editmerchent(loadMerchent);
//		System.out.println("update row : "+id);
		assertEquals(id,loadMerchent.getId());
	}

		
	public static Test suite(){
		return new TestSuite(TestCaseRagister.class);
	}

	public static void main(String[] args){
		junit.textui.TestRunner.run(suite());
	}
		

}
