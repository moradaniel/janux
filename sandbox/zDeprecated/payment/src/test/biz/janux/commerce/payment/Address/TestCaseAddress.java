package biz.janux.commerce.payment.Address;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import biz.janux.commerce.payment.client.VitalMerchantAccount;
import biz.janux.commerce.payment.implementation.common.dao.hibernate.HibernateDao;
import biz.janux.commerce.payment.implementation.vendor.vital.TransactionServiceImpl;
import biz.janux.commerce.payment.implementation.vendor.vital.transaction.VitalAddressVerification;
import biz.janux.commerce.payment.interfaces.response.AddressVerificationResponse;
import biz.janux.commerce.payment.model.AddressVerificationModel;
import biz.janux.commerce.payment.model.CreditCard;
import junit.framework.TestCase;
/**
 * 
 * @author Nilesh
 * 
 *  This is a test case class,
 *  used for  Address verification
 *
 */
public class TestCaseAddress extends TestCase{

	
  public void testAddressVerification(){
	  
 	 AddressVerificationModel addressVerification = new AddressVerificationModel();
	 VitalMerchantAccount merchant = new VitalMerchantAccount();
	 CreditCard creditCard = new CreditCard();
	
	 /**
	  *  use of hibernate and open the  session
	  */
	 HibernateDao hibernateDao = HibernateDao.getInstance();
	 
	 /**
	  * access database using hibernate  
	  */
	 VitalMerchantAccount loadMerchent= (VitalMerchantAccount)hibernateDao.loadfromDB(VitalMerchantAccount.class,33L);
	 CreditCard loadCreditCard = (CreditCard)hibernateDao.loadfromDB(CreditCard.class,895L);
	
	  /**
	   * Set data field using hibernet
	   * 
	   */
	 addressVerification.setInstrumentId(loadCreditCard.getCreditCardId());
	 addressVerification.setMerchantId(loadMerchent.getId());
	 VitalAddressVerification vitalAddVerification = new VitalAddressVerification(addressVerification); 
	 TransactionServiceImpl transactionServiceImpl=new TransactionServiceImpl();
	 AddressVerificationResponse addressVerificationResponse = transactionServiceImpl.process(addressVerification); 
	 
	
	
	      assertNotNull(addressVerificationResponse);
 	      assertFalse(addressVerificationResponse.isNoMatch());
 	      assertTrue(addressVerificationResponse.isExactMatch());
//		  assertTrue(addressVerificationResponse.isAddressMatch());
		}
	}
