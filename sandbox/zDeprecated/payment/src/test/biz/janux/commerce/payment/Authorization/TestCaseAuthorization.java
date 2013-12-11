package biz.janux.commerce.payment.Authorization;

import java.math.BigDecimal;
import junit.framework.TestCase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.hibernate.encryptor.*;
import biz.janux.commerce.payment.client.VitalMerchantAccount;
import biz.janux.commerce.payment.implementation.common.dao.hibernate.HibernateDao;
import biz.janux.commerce.payment.implementation.vendor.vital.TransactionServiceImpl;
import biz.janux.commerce.payment.implementation.vendor.vital.transaction.VitalAuthorization;
import biz.janux.commerce.payment.interfaces.response.AuthorizationResponse;
import biz.janux.commerce.payment.model.Authorizationmodel;
import biz.janux.commerce.payment.model.CreditCard;

public class TestCaseAuthorization extends TestCase{

	public void testVitalAuthorization(){
		
        
		Authorizationmodel authorization = new Authorizationmodel();
		HibernateDao hibernateDao = HibernateDao.getInstance();
		
		VitalMerchantAccount loadMerchent= (VitalMerchantAccount)hibernateDao.loadfromDB(VitalMerchantAccount.class,34L);
		//VitalMerchantAccount loadMerchent= (VitalMerchantAccount)session.load(VitalMerchantAccount.class,104L);
		CreditCard loadCreditCard = (CreditCard)hibernateDao.loadfromDB(CreditCard.class,896L);
		//CreditCard loadCreditCard = (CreditCard)session.load(CreditCard.class,506114L);
		
		//loadCreditCard.encryptCardNumber();
//		System.out.println("cc number ::"+loadCreditCard.getCardNumber());
		
		authorization.setInstrumentId(loadCreditCard.getCreditCardId());
		authorization.setMerchantId(loadMerchent.getId());
		
		authorization.setAuthAmount(new BigDecimal(125.0));
//		System.out.println("curr ...."+loadMerchent.getCurrencySymbol("GBP"));
//		VitalAuthorization va = new VitalAuthorization(authorization);		
		TransactionServiceImpl tsi=new TransactionServiceImpl();
		AuthorizationResponse authorizationResponse = tsi.process(authorization); 		
	
		assertNotNull(authorizationResponse);
		assertTrue(authorizationResponse.isApproved());
		assertFalse(authorizationResponse.isDeclined());
	}
}
