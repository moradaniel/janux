package biz.janux.commerce.payment.interfaces.transaction;

/**
 * 
 * @author Nilesh
 * </br>
 * Interface for Transaction
 */
public interface Transaction {
	/**
	 * 
	 * @return long
	 */
	public long getInstrumentId();
	/**
	 * 
	 * @param instrumentId
	 */
	public void setInstrumentId(long instrumentId);
	/**
	 * 
	 * @return long
	 */
	public long getMerchantId();
	/**
	 * 
	 * @param merchantId
	 */
	public void setMerchantId(long merchantId);
}
