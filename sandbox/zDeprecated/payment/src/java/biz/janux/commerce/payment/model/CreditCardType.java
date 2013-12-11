package biz.janux.commerce.payment.model;
/**
 * 
 * @author Nilesh
 * 
 * This Model is mapped to creditcardtype table 
 *
 */
public class CreditCardType {
	
	private long creditCardTypeId;
	
	private String title;
	
	private String code;
	/**
	 * 
	 * @return long
	 */
	public long getCreditCardTypeId() {
		return creditCardTypeId;
	}
	/**
	 * 
	 * @param creditCardTypeId
	 */
	public void setCreditCardTypeId(long creditCardTypeId) {
		this.creditCardTypeId = creditCardTypeId;
	}
	/**
	 * 
	 * @return String
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 
	 * @return String
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}
}
