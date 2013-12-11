package biz.janux.commerce.payment.interfaces.model;

/**
 * @author Nilesh
 * Interface for PaymentInstrument
 *
 */
public interface PaymentInstrument {
	
	/**
     * 
     * @return long
     */
	public long getCreditCardId();

	/**
     * 
     * @return String
     */
    public String getCardNumber();

    /**
     * 
     * @return String
     */
    public String getExpirationDate();

    /**
     * 
     * @return String
     */
    public String getLoggableInfo();
    
    
    /**
     * 
     * @return boolean
     */
    public boolean encryptCardNumber();
    
    /**
     * 
     * @return boolean
     */
    public boolean decryptCardNumber();
    
    /**
     * 
     * @return String
     */
    public String getUUID();
    
    /**
     * 
     * @param String
     */
    public void setUUID(String UUID);
}
