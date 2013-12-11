package biz.janux.payment;


/**
 * 
 * @author Nilesh
 * @author albertobuffagni@gmail.com
 *
 */
public interface AddressVerificationResponse extends TransactionResponse{
	
	public boolean isExactMatch();
	public void setExactMatch(boolean exactMatch);

	public boolean isAddressMatch();
	public void setAddressMatch(boolean addressMatch);
    
	public boolean isZipMatch();
	public void setZipMatch(boolean zipMatch);
    
    public boolean isNoMatch();
    public void setNoMatch(boolean noMatch);
    
    public boolean isUnavailable();
    public void setUnavailable(boolean unavailable);
    
    public char getAvsResultCode();
    public void setAvsResultCode(char AvsResultCode);

}
