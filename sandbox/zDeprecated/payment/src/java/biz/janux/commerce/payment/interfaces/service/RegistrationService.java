package biz.janux.commerce.payment.interfaces.service;

import biz.janux.commerce.payment.interfaces.model.MerchantAccount;
import biz.janux.commerce.payment.interfaces.model.PaymentInstrument;

/**
 * 
 * @author Nilesh
 * </br>
 * Interface for RegistrationService
 *
 */
public interface RegistrationService {
	/**
	 * 
	 * @param merchantAccount
	 * @return String
	 */
	public String registerMerchantAccount(MerchantAccount merchantAccount);
	/**
	 * 
	 * @param paymentInstrument
	 * @return String
	 */
	public String registerInstrument(PaymentInstrument paymentInstrument);
	/**
	 * 
	 * @param UUID
	 * @return String
	 */
	public PaymentInstrument getInstrument(String UUID);
	/**
	 * 
	 * @param UUID
	 * @return Merchant Account
	 */
	public MerchantAccount getMerchantAccount(String UUID);
	/**
	 * 
	 * @param paymentInstrument
	 * @return String
	 */
	public String editInstrument(PaymentInstrument paymentInstrument);
	/**
	 * 
	 * @param merchantAccount
	 * @return String
	 */
	public String editmerchent(MerchantAccount merchantAccount);
}
