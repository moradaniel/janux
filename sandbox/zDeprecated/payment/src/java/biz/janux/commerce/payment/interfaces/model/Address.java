package biz.janux.commerce.payment.interfaces.model;
/**
 * 
 * @author Nilesh
 * </br>
 * Interface for Address
 *
 */
public interface Address {
	/**
	 * 
	 * @return String
	 */
	public String getAddressLine1();
	/**
	 * 
	 * @param addressLine1
	 */
	public void setAddressLine1(String addressLine1);
	/**
	 * 
	 * @return String
	 */
	public String getAddressLine2();
	/**
	 * 
	 * @param addressLine2
	 */
	public void setAddressLine2(String addressLine2);
	/**
	 * 
	 * @return String
	 */
	public String getCity();
	/**
	 * 
	 * @param city
	 */
	public void setCity(String city);
	/**
	 * 
	 * @return String
	 */
	public String getState();
	/**
	 * 
	 * @param state
	 */
	public void setState(String state);
	/**
	 * 
	 * @return String
	 */
	public String getCountry();
	/**
	 * 
	 * @param country
	 */
	public void setCountry(String country);
	/**
	 * 
	 * @return String
	 */
	public String getZipCode();
	/**
	 * 
	 * @param zipCode
	 */
	public void setZipCode(String zipCode);
}
