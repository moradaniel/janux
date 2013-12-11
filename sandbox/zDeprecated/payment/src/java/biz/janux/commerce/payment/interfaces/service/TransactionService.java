package biz.janux.commerce.payment.interfaces.service;

import biz.janux.commerce.payment.implementation.vendor.vital.transaction.FormatDetails.DetailType;
import biz.janux.commerce.payment.interfaces.response.AddressVerificationResponse;
import biz.janux.commerce.payment.interfaces.response.AuthorizationResponse;
import biz.janux.commerce.payment.interfaces.response.SettlementResponse;
import biz.janux.commerce.payment.interfaces.transaction.AddressVerification;
import biz.janux.commerce.payment.interfaces.transaction.Authorization;
import biz.janux.commerce.payment.interfaces.transaction.Settlement;

/**
 * 
 * @author Nilesh
 * </br>
 * Interface for TransactionService
 *
 */
public interface TransactionService {
	/**
	 * 
	 * @param transaction
	 * @param type
	 * @return Settlement Response
	 */
	public SettlementResponse process(Settlement transaction , DetailType type);
	/**
	 * 
	 * @param transaction
	 * @return AddressVerification Response
	 */
	public AddressVerificationResponse process(AddressVerification transaction);
	/**
	 * 
	 * @param transaction
	 * @return Authorization Response
	 */
	public AuthorizationResponse process(Authorization transaction);
	
}
