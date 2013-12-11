package biz.janux.commerce.payment.interfaces.transaction;

import java.math.BigDecimal;
import java.sql.Date;

import biz.janux.commerce.payment.model.VitalAuthorizationResponseModel;
/**
 * 
 * @author Nilesh
 * </br>
 * Interface for Settlement
 * </br>
 * Extends Transaction
 */
public interface Settlement extends Transaction {
	/**
	 * 
	 * @return boolean
	 */
	public boolean getScheduled();
	/**
	 * 
	 * @param isScheduled
	 */
	public void setScheduled(boolean isScheduled);
	/**
	 * 
	 * @param date
	 */
	public void setScheduleTime(java.sql.Date date);
	/**
	 * 
	 * @return java.sql.Date
	 */
	public Date getScheduleTime();
	/**
	 * 
	 * @param vitalAuthorization Response Model
	 */
	
	public void setVitalAuthorizationResponseModel(VitalAuthorizationResponseModel vitalAuthorizationResponseModel);
	/**
	 * 
	 * @return VitalAuthorization Response Model
	 */
	public VitalAuthorizationResponseModel getVitalAuthorizationResponseModel();
	/**
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getSettlementAmount();
	/**
	 * 
	 * @param Settlement Amount
	 */
	public void setSettlementAmount(BigDecimal settlementAmount);
	/**
	 * 
	 * @return boolean
	 */
	public boolean isApproved();
	/**
	 * 
	 * @return long
	 */
	public long getAuthId();
	/**
	 * 
	 * @param authId
	 */
	public void setAuthId(long authId);
	
}
