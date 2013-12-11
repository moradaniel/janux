package biz.janux.commerce.payment.implementation.common;

import org.apache.log4j.Logger;
import biz.janux.commerce.payment.util.StringRandomizerUtil;
import biz.janux.commerce.payment.implementation.common.dao.hibernate.HibernateDao;
import biz.janux.commerce.payment.interfaces.model.MerchantAccount;
import biz.janux.commerce.payment.interfaces.model.PaymentInstrument;
import biz.janux.commerce.payment.interfaces.service.RegistrationService;
import biz.janux.commerce.payment.model.CreditCard;
import biz.janux.commerce.payment.model.VitalMerchantAccount;
/**
 * @author Nilesh
 * 
 * <br/>
 * Implementation for Registration Service
 * 
 * */
public class RegistrationServiceImpl implements RegistrationService {

	private static final Logger logger = Logger.getLogger(RegistrationServiceImpl.class);
	/**
	 * @see {@link biz.janux.commerce.payment.interfaces.service.RegistrationService.getInstrument()}	 
	 * */
	public PaymentInstrument getInstrument(String UUID) {
		
		if(logger.isDebugEnabled()){
			logger.debug(" getInstrument { UUID = " + UUID
					+ "}");
		}
		HibernateDao hibernateDao = HibernateDao.getInstance();
		CreditCard creditCard =(CreditCard)hibernateDao.loadfromUUID(CreditCard.class, UUID);
		return creditCard;
	}
	/**
	 * @see {@link biz.janux.commerce.payment.interfaces.service.RegistrationService.getMerchantAccount()}	 
	 * */
	public MerchantAccount getMerchantAccount(String UUID) {
		
		if(logger.isDebugEnabled()){
			logger.debug(" getMerchantAccount { UUID = " + UUID
					+ "}");
		}
		HibernateDao hibernateDao = HibernateDao.getInstance();
		MerchantAccount vitalMerchantAccount =(MerchantAccount)hibernateDao.loadfromUUID(VitalMerchantAccount.class , UUID);	
		return vitalMerchantAccount;
	}
	/**
	 * @see {@link biz.janux.commerce.payment.interfaces.service.RegistrationService.registerInstrument()}	 
	 * */
	public String registerInstrument(PaymentInstrument paymentInstrument) {
		if(logger.isDebugEnabled()){
			logger.debug(" registerInstrument { LoggableInfo = " + paymentInstrument.getLoggableInfo()
					+ " }");
		}
		HibernateDao hibernateDao = HibernateDao.getInstance();
		// Encrypt Credit Card No.
		paymentInstrument.encryptCardNumber();
		// get the random no. for the merchant.
		String UUID = StringRandomizerUtil.getRandomStringFor(paymentInstrument.getCardNumber());
		paymentInstrument.setUUID(UUID);
		hibernateDao.save(paymentInstrument);
		return paymentInstrument.getUUID();
	}
	/**
	 * @see {@link biz.janux.commerce.payment.interfaces.service.RegistrationService.registerMerchantAccount()}	 
	 * */
	public String registerMerchantAccount(MerchantAccount merchantAccount) {	
		if (logger.isDebugEnabled()) {
			logger.debug(" registerMerchantAccount { Name = " + merchantAccount.getName()
					+ " State = " + merchantAccount.getState()
					+ " }");
		}
		HibernateDao hibernateDao = HibernateDao.getInstance();
		// get the random no. for the merchant.
		String UUID = StringRandomizerUtil.getRandomStringFor(merchantAccount.getAcqBankIdentificationNumber());
		merchantAccount.setUUID(UUID);
		hibernateDao.save(merchantAccount);
		return merchantAccount.getUUID();
	}
	/**
	 * @see {@link biz.janux.commerce.payment.interfaces.service.RegistrationService.edit Instrument()}	 
	 * */
	public String editInstrument(PaymentInstrument paymentInstrument){
		if (logger.isDebugEnabled()) {
			logger.debug(" editInstrument { UUID = " + paymentInstrument.getUUID()
					+ " }");
		}
		HibernateDao hibernateDao = HibernateDao.getInstance();
		paymentInstrument.encryptCardNumber();		
		hibernateDao.update(paymentInstrument);
		return paymentInstrument.getUUID();
	}
	/**
	 * @see {@link biz.janux.commerce.payment.interfaces.service.RegistrationService.edit merchant()}	 
	 * */
	public String editmerchent(MerchantAccount merchantAccount){
		if (logger.isDebugEnabled()) {
			logger.debug(" editmerchent { UUID = " + merchantAccount.getUUID()
					+ " }");
		}
		HibernateDao hibernateDao = HibernateDao.getInstance();
		hibernateDao.update(merchantAccount);
		return merchantAccount.getUUID();
	}
}
