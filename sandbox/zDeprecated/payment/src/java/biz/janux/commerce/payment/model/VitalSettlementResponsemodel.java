package biz.janux.commerce.payment.model;

import java.math.BigDecimal;
import java.util.Date;
import biz.janux.commerce.payment.implementation.vendor.vital.response.VitalSettlementResponse;
/**
 * 
 * @author Nilesh
 * Model to map vital settlement response
 *
 */
public class VitalSettlementResponsemodel extends VitalSettlementResponse{
	
	private long Id;
	
	private long merchantId;
	
	private long InstrumentId;
	
	private long authId;
	
	private BigDecimal settlementAmount;

	private Date systemDate;

	private int settled;
	
	public VitalSettlementResponsemodel(byte[] b){
		super(b);
	}
	/**
	 * 
	 * @return long
	 */
	public long getId() {
		return Id;
	}
	/**
	 * 
	 * @param id
	 */
	public void setId(long id) {
		Id = id;
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
	 * 
	 * @return long
	 */
	public long getInstrumentId() {
		return InstrumentId;
	}
	/**
	 * 
	 * @param instrumentId
	 */
	public void setInstrumentId(long instrumentId) {
		InstrumentId = instrumentId;
	}
	/**
	 * 
	 * @return long
	 */
	public long getAuthId() {
		return authId;
	}
	/**
	 * 
	 * @param authId
	 */
	public void setAuthId(long authId) {
		this.authId = authId;
	}
	/**
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getSettlementAmount() {
		return settlementAmount;
	}
	/**
	 * 
	 * @param settlementAmount
	 */
	public void setSettlementAmount(BigDecimal settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
	/**
	 * 
	 * @return java.util.Date
	 */
	public Date getSystemDate() {
		return systemDate;
	}
	/**
	 * 
	 * @param systemDate
	 */
	public void setSystemDate(Date systemDate) {
		this.systemDate = systemDate;
	}
	/**
	 * 
	 * @return int
	 */
	public int getSettled() {
		return settled;
	}
	/**
	 * 
	 * @param settled
	 */
	public void setSettled(int settled) {
		this.settled = settled;
	}
}
