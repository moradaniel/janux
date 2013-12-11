package biz.janux.commerce.payment.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;
import biz.janux.commerce.payment.implementation.vendor.vital.response.VitalAuthorizationResponse;

/**
 * 
 * @author Nilesh
 * 
 * This Model is mapped to vital_authorization_response table
 * It keeps the record of  AuthorizationResponse
 *
 */

public class VitalAuthorizationResponseModel extends VitalAuthorizationResponse{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long Id;
	
	private long merchantId;
	
	private long InstrumentId;
	
	private BigDecimal AuthorizationAmount;
	
//	private String authResponseText;
	
	private int disabled;
	
	private int batched;
	
	private Timestamp localTransactionDateTime;
	
	private  BigDecimal originalAuthorizationAmount;
	
	private int offline;
	
	private int reversal;
	
	private int arAccountId;
	
	private Date systemDate;
	public VitalAuthorizationResponseModel(){}
	
	public VitalAuthorizationResponseModel(byte[] internalResponse){
		super(internalResponse);
	}
	/**
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getAuthorizationAmount() {
		return AuthorizationAmount;
	}
	/**
	 * set original Authorized amount
	 * @param authorizationAmount
	 */
	public void setAuthorizationAmount(BigDecimal authorizationAmount) {
		AuthorizationAmount = authorizationAmount;
	}
	/**
	 * 
	 * @return long
	 */
	public long getMerchantId() {
		return merchantId;
	}
	/**
	 * 
	 * @param merchantId
	 */
	public void setMerchantId(long merchantId) {
		this.merchantId = merchantId;
	}
	/**
	 * @return long
	 */
	public long getInstrumentId() {
		return InstrumentId;
	}
	/**
	 * @param instrumentId
	 */
	public void setInstrumentId(long instrumentId) {
		InstrumentId = instrumentId;
	}
	/**
	 * @return long
	 */
	public long getId() {
		return Id;
	}
	/**
	 * @param Id
	 */
	public void setId(long id) {
		Id = id;
	}
	/**
	 * 
	 * @return int
	 */
	public int getDisabled() {
		return disabled;
	}
	/**
	 * 
	 * @param disabled
	 */
	public void setDisabled(int disabled) {
		this.disabled = disabled;
	}
	/**
	 * 
	 * @return int
	 */
	public int getBatched() {
		return batched;
	}
	/**
	 * 
	 * @param batched
	 */
	public void setBatched(int batched) {
		this.batched = batched;
	}
	/**
	 * @return Timestamp
	 */
	public Timestamp getLocalTransactionDateTime() {
		return localTransactionDateTime;
	}
	/**
	 * @param localTransactionDateTime
	 */
	public void setLocalTransactionDateTime(Timestamp localTransactionDateTime) {
		this.localTransactionDateTime = localTransactionDateTime;
	}
	/**
	 * @return BigDecimal
	 */
	public BigDecimal getOriginalAuthorizationAmount() {
		return originalAuthorizationAmount;
	}
	/**
	 * @param originalAuthorizationAmount
	 */
	public void setOriginalAuthorizationAmount(
			BigDecimal originalAuthorizationAmount) {
		this.originalAuthorizationAmount = originalAuthorizationAmount;
	}
	/**
	 * 
	 * @return int
	 */
	public int getOffline() {
		return offline;
	}
	/**
	 * 
	 * @param offline
	 */
	public void setOffline(int offline) {
		this.offline = offline;
	}
	/**
	 * 
	 * @return int
	 */
	public int getReversal() {
		return reversal;
	}
	/**
	 * 
	 * @param reversal
	 */
	public void setReversal(int reversal) {
		this.reversal = reversal;
	}
	/**
	 * @return int
	 */
	public int getArAccountId() {
		return arAccountId;
	}
	/**
	 * @param arAccountId
	 */
	public void setArAccountId(int arAccountId) {
		this.arAccountId = arAccountId;
	}
	/**
	 * @return String
	 */
	public String getDetailedResponseDefinition() {
		return super.getDetailedResponseDefinition(this.responseCode);
	}
	/**
	 * @return java.sql.Date
	 */
	public Date getSystemDate() {
		return systemDate;
	}
	/**
	 * @param systemDate
	 */
	public void setSystemDate(Date systemDate) {
		this.systemDate = systemDate;
	}
}
