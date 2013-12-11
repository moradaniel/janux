package biz.janux.commerce.payment.interfaces.service;

import java.math.BigDecimal;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import biz.janux.commerce.payment.implementation.common.dao.hibernate.HibernateDao;
import biz.janux.commerce.payment.implementation.vendor.vital.TransactionServiceImpl;
import biz.janux.commerce.payment.implementation.vendor.vital.transaction.VitalAddressVerification;
import biz.janux.commerce.payment.implementation.vendor.vital.transaction.VitalAuthorization;
import biz.janux.commerce.payment.implementation.vendor.vital.transaction.FormatDetails.DetailType;
import biz.janux.commerce.payment.model.AddressVerificationModel;
import biz.janux.commerce.payment.model.Authorizationmodel;
import biz.janux.commerce.payment.model.CreditCard;
import biz.janux.commerce.payment.model.SettlementModel;
import biz.janux.commerce.payment.model.VitalAuthorizationResponseModel;
import biz.janux.commerce.payment.model.VitalMerchantAccount;

public class MainServiceAppContext {

	public static void printAuthorizationInfo(final TransactionService transactionService)   {

		Authorizationmodel authorization = new Authorizationmodel();
		authorization.setAuthAmount(new BigDecimal(0.5));
		authorization.setInstrumentId(896);
		authorization.setMerchantId(34);
		TransactionServiceImpl tsi=new TransactionServiceImpl();
		transactionService.process(authorization);
	}
	public static void printSettlementInfo(final TransactionService transactionService)   {
		HibernateDao hibernateDao = HibernateDao.getInstance();
		VitalAuthorizationResponseModel vitalAuthorizationResponseModel = (VitalAuthorizationResponseModel)hibernateDao.loadfromDB(VitalAuthorizationResponseModel.class , 1L);	
		SettlementModel settlement = new SettlementModel(vitalAuthorizationResponseModel , new BigDecimal(0.5));
		TransactionServiceImpl tsi=new TransactionServiceImpl();
		 transactionService.process(settlement , DetailType.PURCHASE);
	}
	public static void printAddressInfo(final TransactionService transactionService)   {
		
		AddressVerificationModel addressVerification = new AddressVerificationModel();
		 VitalMerchantAccount merchant = new VitalMerchantAccount();
		 CreditCard creditCard = new CreditCard();
		
		 /**
		  * @author Nilesh
		  *  use of hibernate and open the  session
		  */
		 HibernateDao hibernateDao = HibernateDao.getInstance();
		 
		 /**
		  * access database using hibernate  
		  */
		 VitalMerchantAccount loadMerchent= (VitalMerchantAccount)hibernateDao.loadfromDB(VitalMerchantAccount.class , 33L);
		 CreditCard loadCreditCard = (CreditCard)hibernateDao.loadfromDB(CreditCard.class , 895L);
		
		  /**
		   * Set data field using hibernet
		   * 
		   */
		 addressVerification.setInstrumentId(loadCreditCard.getCreditCardId());
		 addressVerification.setMerchantId(loadMerchent.getId());
		 VitalAddressVerification vitalAddVerification = new VitalAddressVerification(addressVerification); 
		 transactionService.process(addressVerification);
	}
	/**    * @param args the command line arguments    */   
	public static void main(String[] args)   {
		final ApplicationContext context =  new ClassPathXmlApplicationContext("payment-service-http-config.xml");
		TransactionService transactionService = (TransactionService) context.getBean("transaction");
		printAuthorizationInfo(transactionService);
		printSettlementInfo(transactionService);
		printAddressInfo(transactionService);
	}
}
