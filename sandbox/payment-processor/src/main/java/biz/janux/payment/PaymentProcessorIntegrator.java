package biz.janux.payment;

import java.math.BigDecimal;

/**
 * Contains the methods used for communicate with a payment processor. Such as TSYS
 * 
 * @author albertobuffagni@gmail.com
 *
 */
public interface PaymentProcessorIntegrator {

	/**
	 * Send for process the authorization to payment processor.
	 */
	public AuthorizationResponse processAuthorization(Authorization authorization, BigDecimal authAmount, AuthorizationType authorizationType);
	
	/**
	 * Process the offline authorization to payment processor.
	 */
	public AuthorizationResponse processAuthorizationOffline(Authorization authorization, BigDecimal authAmount, AuthorizationType authorizationType,String approvalCode);
	
	/**
	 * Send for process the settlement to payment processor.
	 */
	public SettlementResponse processSettlement(Settlement settlement,int batchNumber);
	

	/**
	 * Return the correct batch number.
	 * Checks of the limit of the Batch Number of the payment processor. 
	 */
	public int checkLimitBatchNumber(int batchNumber);
	
	/**
	 * Return the correct transaction sequence number.
	 * Checks of the limit of the transaction sequence number of the payment processor. 
	 */
	public int checkLimitTransactionSequenceNumber(int transactionSequenceNumber);

	
}
