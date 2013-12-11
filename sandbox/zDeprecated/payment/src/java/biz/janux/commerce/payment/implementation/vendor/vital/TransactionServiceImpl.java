package biz.janux.commerce.payment.implementation.vendor.vital;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;


import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import biz.janux.commerce.payment.implementation.common.dao.hibernate.HibernateDao;
import biz.janux.commerce.payment.implementation.vendor.vital.helper.VitalHelper;
import biz.janux.commerce.payment.implementation.vendor.vital.response.VitalAddressVerificationResponse;
import biz.janux.commerce.payment.implementation.vendor.vital.response.VitalAuthorizationResponse;
import biz.janux.commerce.payment.implementation.vendor.vital.response.VitalSettlementResponse;
import biz.janux.commerce.payment.implementation.vendor.vital.transaction.VitalAddressVerification;
import biz.janux.commerce.payment.implementation.vendor.vital.transaction.VitalAuthorization;
import biz.janux.commerce.payment.implementation.vendor.vital.transaction.VitalSettlement;
import biz.janux.commerce.payment.implementation.vendor.vital.transaction.FormatDetails.DetailType;
import biz.janux.commerce.payment.interfaces.response.AddressVerificationResponse;
import biz.janux.commerce.payment.interfaces.response.AuthorizationResponse;
import biz.janux.commerce.payment.interfaces.response.SettlementResponse;
import biz.janux.commerce.payment.interfaces.service.TransactionService;
import biz.janux.commerce.payment.interfaces.transaction.AddressVerification;
import biz.janux.commerce.payment.interfaces.transaction.Authorization;
import biz.janux.commerce.payment.interfaces.transaction.Settlement;
import biz.janux.commerce.payment.model.VitalAuthorizationResponseModel;
import biz.janux.commerce.payment.model.VitalSettlementResponsemodel;

/**
 * @author Nilesh
 * <br/>
 * Implementation for Service class for the Transactions
 * 
 * */
public class TransactionServiceImpl implements TransactionService {
	
	private VitalHelper helper = new VitalHelper();
	
	private static final Logger logger = Logger.getLogger(TransactionServiceImpl.class);
	
	/**
	 * @param settlement {@link biz.janux.commerce.payment.interfaces.transaction.AddressVerification}
	 * 
	 * <br/>
	 * Implementation of Address Verification Service 
	 * 
	 * */
	public AddressVerificationResponse process(AddressVerification transaction) {
			if(logger.isDebugEnabled()){
					logger.debug(" Processing Address Verification { Merchant = " + transaction.getMerchantId()
					+ " Instrument = " + transaction.getInstrumentId()
					+ " }");
			}
			VitalAddressVerification vav = new VitalAddressVerification(transaction);
			byte[] response= helper.sendTransaction(vav.getVitalMessage());
//			HibernateDao hibernateDao = HibernateDao.getInstance();
//			VitalAuthorizationResponseModel viAuthRespModel = new VitalAuthorizationResponseModel(response);
//			viAuthRespModel.setRequestedACI("P");
//			viAuthRespModel.setMerchantId(transaction.getMerchantId());
//			viAuthRespModel.setInstrumentId(transaction.getInstrumentId());
//			viAuthRespModel.setAuthorizationAmount(new BigDecimal(0.0));
//			viAuthRespModel.setLocalTransactionDateTime(new Timestamp(System.currentTimeMillis()));
//			viAuthRespModel.setOriginalAuthorizationAmount(new BigDecimal(0.0));	
//			viAuthRespModel.setSystemDate(new Date(System.currentTimeMillis()));
//			if(viAuthRespModel.isApproved())
//				hibernateDao.save(viAuthRespModel);
			return new VitalAddressVerificationResponse(response);
			
	}
	/**
	 * @param settlement {@link biz.janux.commerce.payment.interfaces.transaction.Authorization}
	 * 
	 * <br/>
	 * Implementation of Authorization Service 
	 * 
	 * */
	public AuthorizationResponse process(Authorization authorization) {
		if(logger.isDebugEnabled()){
			logger.debug(" Processing Address Verification { Merchant = " + authorization.getMerchantId()
					+ " Instrument = " + authorization.getInstrumentId()
					+ " Amount = " + authorization.getAuthAmount()
					+ " }");
		}
		
		VitalAuthorization vav = new VitalAuthorization(authorization);
		byte[] response= helper.sendTransaction(vav.getVitalMessage());
		HibernateDao hibernateDao = HibernateDao.getInstance();
		VitalAuthorizationResponseModel viAuthRespModel = new VitalAuthorizationResponseModel(response);
		viAuthRespModel.setRequestedACI("Y");
		viAuthRespModel.setMerchantId(authorization.getMerchantId());
		viAuthRespModel.setInstrumentId(authorization.getInstrumentId());
		viAuthRespModel.setAuthorizationAmount(authorization.getAuthAmount());
		viAuthRespModel.setLocalTransactionDateTime(new Timestamp(System.currentTimeMillis()));
		viAuthRespModel.setOriginalAuthorizationAmount(authorization.getAuthAmount());	
		viAuthRespModel.setSystemDate(new Date(System.currentTimeMillis()));
		if(viAuthRespModel.isApproved())
			hibernateDao.save(viAuthRespModel);
		return new VitalAuthorizationResponse(response);
		
	}
	/**
	 * @param settlement {@link biz.janux.commerce.payment.interfaces.transaction.Settlement}
	 * 
	 * <br/>
	 * Implementation of Settlement Service 
	 * 
	 * */
	public SettlementResponse process(Settlement transaction , DetailType type)  {
		
		if(logger.isDebugEnabled()){
			logger.debug(" Processing Address Verification { Merchant = " + transaction.getAuthId()
					+ " Instrument = " + transaction.getInstrumentId()
					+ " Amount = " + transaction.getSettlementAmount()
					+ " }");
		}
		VitalSettlement vav = new VitalSettlement(transaction , type);
		byte[] response= helper.sendTransactionSettlement(vav.getVitalMessage());
		HibernateDao hibernateDao = HibernateDao.getInstance();
		VitalSettlementResponsemodel vitalsetlmnt= new VitalSettlementResponsemodel(response);
		vitalsetlmnt.setInstrumentId(transaction.getInstrumentId());
		vitalsetlmnt.setMerchantId(transaction.getMerchantId());
		vitalsetlmnt.setSettlementAmount(transaction.getSettlementAmount());
		vitalsetlmnt.setSettled(0);
		vitalsetlmnt.setAuthId(transaction.getAuthId());
		vitalsetlmnt.setSystemDate(new Date(System.currentTimeMillis()));
		if(vitalsetlmnt.isApproved())
			hibernateDao.save(vitalsetlmnt);
		return new VitalSettlementResponse(response);
	}
}
