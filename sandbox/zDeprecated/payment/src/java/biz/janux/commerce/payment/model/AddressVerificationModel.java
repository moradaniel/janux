package biz.janux.commerce.payment.model;
 
import java.io.Serializable;
import java.util.Date;

import biz.janux.commerce.payment.interfaces.model.Address;
import biz.janux.commerce.payment.interfaces.transaction.AddressVerification;
/**
 * 
 * @author Nilesh
 * </br>
 * Address Verification Model
 * </br>
 * Implements AddressVerification and Serializable
 *
 */
public class AddressVerificationModel implements AddressVerification, Serializable {
	
	private static final long serialVersionUID = 1L;

	private long merchantId;
	
	private long instrumentId;
	
 	private String authType;
 	
 	private Date localTransactionDateTime;
 	
 	private Address address;
 	
 	/*
 	 * (non-Javadoc)
 	 * @see biz.janux.commerce.payment.interfaces.transaction.AddressVerification#getAddress()
 	 */
	public Address getAddress() {
	 	return address;
	}
	
	/*
	 * (non-Javadoc)
	 * @see biz.janux.commerce.payment.interfaces.transaction.AddressVerification#setAddress(biz.janux.commerce.payment.interfaces.model.Address)
	 */
	public void setAddress(Address address) {
	 	this.address = address;
	}
	
	/*
	 * (non-Javadoc)
	 * @see biz.janux.commerce.payment.interfaces.transaction.Transaction#getInstrumentId()
	 */
	public long getInstrumentId() {
	 	return instrumentId;
	}
	/*
	 * (non-Javadoc)
	 * @see biz.janux.commerce.payment.interfaces.transaction.Transaction#getLocalTransactionDateTime()
	 */
	public Date getLocalTransactionDateTime() {
	 	return localTransactionDateTime;
	}
	/*
	 * (non-Javadoc)
	 * @see biz.janux.commerce.payment.interfaces.transaction.Transaction#getMerchantId()
	 */
	public long getMerchantId() {
	 	return merchantId;
	}
	/*
	 * (non-Javadoc)
	 * @see biz.janux.commerce.payment.interfaces.transaction.Transaction#setInstrumentId(long)
	 */
	public void setInstrumentId(long instrumentId) {
		this.instrumentId = instrumentId;
		
	}
	/*
	 * (non-Javadoc)
	 * @see biz.janux.commerce.payment.interfaces.transaction.Transaction#setLocalTransactionDateTime(java.util.Date)
	 */
	public void setLocalTransactionDateTime(Date localTransactionDateTime) {
		this.localTransactionDateTime =  localTransactionDateTime;
		
	}
	/*
	 * (non-Javadoc)
	 * @see biz.janux.commerce.payment.interfaces.transaction.Transaction#setMerchantId(long)
	 */
	public void setMerchantId(long merchantId) {
		this.merchantId = merchantId;
	 	
	}
	/*
	 * @return String
	 */
	public String getAuthType() {
		return authType;
	}
	
	/*
	 * @param authtype
	 */
	public void setAuthType(String authType) {
		this.authType = authType;
	}
 	
}
