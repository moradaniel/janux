package biz.janux.commerce.payment.Register;
import biz.janux.commerce.payment.client.VitalMerchantAccount;
import biz.janux.commerce.payment.implementation.common.RegistrationServiceImpl;
import biz.janux.commerce.payment.implementation.common.dao.hibernate.HibernateDao;
import biz.janux.commerce.payment.model.Country;
import biz.janux.commerce.payment.model.CreditCard;
import biz.janux.commerce.payment.model.CreditCardType;
import biz.janux.commerce.payment.model.MyhmsCreditcard;
import biz.janux.commerce.payment.model.State;
import biz.janux.commerce.payment.util.CreditCardUtil;
import biz.janux.commerce.payment.util.DesEncrypt;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestCaseRagister extends TestCase {
	
	public void testRagisterInstrument(){
		CreditCardUtil ccu= new CreditCardUtil();
		RegistrationServiceImpl rs= new RegistrationServiceImpl();
		CreditCardType creditCardType = new CreditCardType();
		creditCardType.setCreditCardTypeId(12);
		Country country = new Country();
		country.setCountryId(2);
		CreditCard cc = new CreditCard();
		cc.setCardNumber("5466160153175375");
		cc.setCreditCardType(creditCardType);
		cc.setAddress1(" New Mexico");
		cc.setAddress2("US");
		cc.setCity("Mumbai");
		cc.setCountry(country);
		cc.setExpirationDate("06/04");
		cc.setHoldersName("JOHN L RUIZ");
		cc.setLoggableInfo("my");
		cc.setPhoneNumber("022-2459863");
		cc.setState("New Mexico");
		cc.setTrack1("track1");
		cc.setTrack2("track2");
		cc.setZip("8340029");
		long id = rs.registerInstrument(cc);
		//System.out.println("Registered for :::: "+id);
		String carnum=cc.getCardNumber();
		String carnumdecrypt=null;
		HibernateDao hibernateDao = HibernateDao.getInstance();
		DesEncrypt desEncrypt = new DesEncrypt();
//		CreditCard loadCreditCard = rs.getInstrument(896L);
		String databseccnum=rs.getInstrument(896L).getCardNumber();
		String databseccnumdecrypt=null;
		try {
			carnumdecrypt=cc.decrypt(carnum);
			 databseccnumdecrypt =desEncrypt.decrypt(databseccnum);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
//		System.out.println("Credit_Card number: "+ccu.getMaskedNumber(databseccnumdecrypt));
		assertEquals(carnumdecrypt,databseccnumdecrypt);
	}
	
	public void testRagisterMerchant(){
			RegistrationServiceImpl rs= new RegistrationServiceImpl();
		 	VitalMerchantAccount vma= new VitalMerchantAccount();
		 	Country cntry = new Country();
		 	State st = new State();
		 	st.setStateId(57);
		 	vma.setId(34);
		 	vma.setAcqBankIdentificationNumber("999995");
		 	vma.setAgentBankNumber("000000");
			vma.setCardholderServiceNumber("null");
			vma.setChainNumber("000000");
			vma.setCity("New York");
			vma.setCityCode("99999");
			vma.setCountry(cntry);
			vma.setCurrencyCode("840");
			vma.setLocalPhoneNumber("999-9999999");
			vma.setLocation("Test City");
			vma.setName("Test Hotel");
			vma.setNumber("888000002329");
			vma.setState(st);
			vma.setStoreNumber("5999");
			vma.setTerminalID("77777777");
			vma.setTerminalNumber("1515");
			vma.setTimeZoneDifferential("America/Denver");
			long id = rs.registerMerchantAccount(vma);
			//System.out.println("Registered for ::"+id);
			String termNum=vma.getTerminalNumber();
			HibernateDao hibernateDao = HibernateDao.getInstance();
//			VitalMerchantAccount loadMerchent= (VitalMerchantAccount)hibernateDao.loadfromDB(VitalMerchantAccount.class,34L);
			String databaseTermNum=rs.getMerchantAccount(34L).getTerminalNumber();
//			System.out.println("VitalMerchantAccount : "+rs.getMerchantAccount(34L).getId());	
			assertEquals(termNum,databaseTermNum);	
	}
		
	public static Test suite(){
		return new TestSuite(TestCaseRagister.class);
	}

	public static void main(String[] args){
		junit.textui.TestRunner.run(suite());
	}
		
}


