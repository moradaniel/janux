package biz.janux.commerce.payment.interfaces.transaction;

import biz.janux.commerce.payment.interfaces.model.Address;
/**
 * 
 * @author Nilesh
 * </br>
 * Interface for Address Verification
 * </br>
 * Extends Transaction
 */
public interface AddressVerification extends Transaction {
	/**
	 * 
	 * @param address
	 */
	public void setAddress(Address address);
	/**
	 * 
	 * @return object of Address
	 */
	public Address getAddress();
}
