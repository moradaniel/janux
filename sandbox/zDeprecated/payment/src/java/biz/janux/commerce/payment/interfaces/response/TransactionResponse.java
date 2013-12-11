package biz.janux.commerce.payment.interfaces.response;

import java.io.Serializable;
import biz.janux.commerce.payment.interfaces.response.codes.ResponseCodes;
/**
 * @author Nilesh
 * extends Serializable, ResponseCodes
 *
 **/
public interface TransactionResponse extends Serializable, ResponseCodes{
 	/**
 	 * 
 	 * @return String
 	 */	
	public String getErrorCode();
	/**
	 * 
	 * @return String
	 */
	public String getStatus();
	/**
	 * 
	 * @return String
	 */
	public String getErrorData();
	/**
	 * 
	 * @return byte[]
	 */
	public byte[] getBytes();
	/**
	 * 
	 * @return String
	 */
	public String getDetailedResponseDefinition();
	/**
	 * 
	 * @return String
	 */
	public String getTransactionSequenceNumber();
	/**
	 * 
	 * @return String
	 */
	public String getTransactionIdentifier();
	/**
	 * 
	 * @return String
	 */
	public String getLocalTransDate() ;
	/**
	 * 
	 * @return String
	 */
	public String getLocalTransTime();
}
