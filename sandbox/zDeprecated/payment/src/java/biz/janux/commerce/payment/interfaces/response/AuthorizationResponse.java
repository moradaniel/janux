package biz.janux.commerce.payment.interfaces.response;

import java.math.BigDecimal;

import biz.janux.commerce.payment.interfaces.response.codes.AuthorizationResponseCodes;
/**
 * 
 * @author Nilesh
 * </br>
 * Interface for AuthorizationResponse
 * </br>
 * It extends TransactionResponse and AuthorizationResponseCodes
 */ 
public interface AuthorizationResponse extends TransactionResponse, AuthorizationResponseCodes{

	/**
     * @return boolean
     * is Approved
     * */
	public boolean isApproved() ;
	/**
     * @return boolean
     * is Declined
     * */
	public boolean isDeclined() ;
	/**
     * 
     * @return string 
     */
	public String getApprovalCode();
	/**
     * 
     * @return string 
     */
	public String getAuthResponseText();
	/**
     * @return boolean
     * is Offline
     * */
	public boolean isOffline() ;
	/**
     * @return boolean
     * is Reversal
     * */
	public boolean isReversal();
	/**
     * 
     * @return long 
     */
	public long getFolioId();
	/**
	 *  @param long i
	 */
	public void setFolioId(long i);
	/**
     * @return BigDecimal
     * AuthorizationAmoun
     * */
	public BigDecimal getAuthorizationAmount();
	/**
	 *  @param BigDecimal 
	 */
	public void setAuthorizationAmount(BigDecimal authorizationAmount);
	/**
	 * 
	 * @param AuthorizationId
	 */
	public void setId(long id);
	/**
	 * 
	 * @return long
	 */
	public long getId();
	
	
}
