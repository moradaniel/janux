package biz.janux.commerce.payment.interfaces.response;

import biz.janux.commerce.payment.interfaces.response.codes.AddressVerificationResponseCodes;
/**
 * 
 * @author Nilesh
 * </br>
 * Interface for AddressVerificationResponse
 * </br>
 * It extends TransactionResponse and AddressVerificationResponseCodes
 *
 */
public interface AddressVerificationResponse extends TransactionResponse, AddressVerificationResponseCodes{
	
	 /**
     * @return boolean
     * is ExactMatch
     * */
	public boolean isExactMatch();
	
	 /**
     * @return boolean
     * is AddressMatch
     * */
    public boolean isAddressMatch();
    
    /**
     * @return boolean
     * is Zip Match
     * */
    public boolean isZipMatch();
    
    /**
     * @return boolean
     * is No Match
     * */
    public boolean isNoMatch();
    
    
    /**
     * @return boolean
     * is unavailable
     * */
    public boolean isUnavailable();
    
    /**
     * 
     * @return string 
     */
    public String addressVerificationResponse();
    /**
     * 
     * @return String
     */
    public String getApprovalCode();
    /**
     * 
     * @return char
     */
    public char getAvsResultCode();

}
