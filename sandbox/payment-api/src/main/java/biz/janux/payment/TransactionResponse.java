package biz.janux.payment;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * Represents the response of a request for a {@link Transaction}
 * 
 * @author Nilesh
 * @author albertobuffagni@gmail.com
 * 
 **/
public interface TransactionResponse  extends Serializable {
	
	/**
	 * Date of the transaction response.
	 */
	public Date getDate();
	public void setDate(Date date);
	
	/**
	 * The bytes received from the payment processor. 
	 */
	public byte[] getOriginalBytes();
	public void setOriginalBytes(byte[] originalBytes);
	
	/**
	 * It is not persist.
	 */
	public String getErrorDescription();
	public void setErrorDescription(String errorDescription);
	
	/**
	 * This value depends of the response code
	 */
	public boolean isApproved();
	public void setApproved(boolean approved);
	
}
