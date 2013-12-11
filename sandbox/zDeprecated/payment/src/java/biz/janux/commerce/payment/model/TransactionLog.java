package biz.janux.commerce.payment.model;

import java.util.Date;
/**
 * 
 * @author Nilesh
 * </br>
 * This Class for TransactionLogBean
 *
 */
public class TransactionLog implements 
		biz.janux.commerce.payment.interfaces.model.TransactionLog {
	
	private long transactionId;
	
	private String transactionStatus;
	
	private TRANSACTIONTYPE  transactionType;
	
	private long instrumentId;
	
	private Date localTransactionDateTime;
	
	private long merchantId;

	/**
	 * @return long 
	 * Instrument Id
	 * */ 
	public long getInstrumentId() {
		return instrumentId;
	}

	/**
	 * @param long 
	 * Sets the Instrument Id
	 */
	public void setInstrumentId(long instrumentId) {
		this.instrumentId = instrumentId;
	}
	/**
	 * @return Date 
	 * Local Transaction Date Time
	 * */ 
	public Date getLocalTransactionDateTime() {
		return localTransactionDateTime;
	}

	/**
	 * @param Date 
	 * Sets the Local Transaction Date Time
	 */
	public void setLocalTransactionDateTime(Date localTransactionDateTime) {
		this.localTransactionDateTime = localTransactionDateTime;
	}
	/**
	 * @return long 
	 * Merchant Id
	 * */ 
	public long getMerchantId() {
		return merchantId;
	}

	/**
	 * @param long 
	 * Sets the Merchant Id
	 */
	public void setMerchantId(long merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * @return String 
	 * Transaction Status
	 * */ 
	public String getTransactionStatus() {
		return transactionStatus;
	}

	/**
	 * @param String 
	 * Sets the Transaction Status
	 */
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	/**
	 * @return TRANSACTIONTYPE 
	 * Transaction Type
	 * */ 
	public TRANSACTIONTYPE getTransactionType() {
		return transactionType;
	}

	/**
	 * @param TRANSACTIONTYPE 
	 * Sets the Transaction Type
	 */
	public void setTransactionType(TRANSACTIONTYPE transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @return long 
	 * Transaction Id
	 * */ 
	public long getTransactionId() {
		return transactionId;
	}

	/**
	 * @param long 
	 * Sets the Transaction Id
	 */
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
}
