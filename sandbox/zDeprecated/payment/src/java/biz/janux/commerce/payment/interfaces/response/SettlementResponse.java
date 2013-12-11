package biz.janux.commerce.payment.interfaces.response;

import biz.janux.commerce.payment.interfaces.response.codes.SettlementResponseCodes;
/**
 * 
 * @author Nilesh
 * </br>
 * Interface for SettlementResponse
 *</br>
 *It extends TransactionResponse and SettlementResponseCodes
 */
public interface SettlementResponse extends TransactionResponse, SettlementResponseCodes {

	/**
	 * 
	 * @return boolean
	 */
	public boolean isApproved();
	/**
	 * 
	 * @return boolean
	 */
	public boolean isDeclined();
	/**
	 * 
	 * @return String
	 */
	public String getBatchNumber();
	/**
	 * 
	 * @return string
	 */
	public String getBatchRecordCount();
	/**
	 * 
	 * @return String
	 */
	public String getBatchResponseCode();
}
