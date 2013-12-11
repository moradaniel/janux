package biz.janux.commerce.payment.interfaces.model;

import biz.janux.commerce.payment.interfaces.transaction.Transaction;
/**
 * 
 * @author Nilesh
 * This interface is for TransactionLogBean
 */
public interface TransactionLog extends Transaction {
	/**
	 * 
	 * This enum  for transaction type
	 *
	 */
	public enum TRANSACTIONTYPE{ 
			ADDRESS_VERIFICATION,
			AUTHORIZATION,
			SETTLEMENT
		}
	/**
	 * 
	 * @return TRANSACTIONTYPE
	 */
	public TRANSACTIONTYPE getTransactionType();
	/**
	 * 
	 * @param transactionType
	 */
	public void setTransactionType(TRANSACTIONTYPE transactionType);
	/**
	 * 
	 * @return String
	 */
	public String getTransactionStatus();
	/**
	 * 
	 * @param transactionStatus
	 */
	public void setTransactionStatus(String transactionStatus);
}
