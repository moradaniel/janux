package biz.janux.commerce.payment.implementation.vendor.vital.transaction;

import java.math.BigDecimal;

import biz.janux.commerce.payment.implementation.vendor.vital.helper.VitalHelper;
import biz.janux.commerce.payment.interfaces.transaction.Authorization;
import biz.janux.commerce.payment.util.DataFormattingUtil;

public class Reversal extends VitalAuthorization {
	// This is the "settlement amount", or total auth - reversal amount	
	BigDecimal secondaryAmount;
	
	// Section 4.75
	// Transaction Identifier
	// 15 character field
	// ONLY USED IN INCREMENTAL/REVERSAL TRANSACTIONS
	String transactionIdentifier = null;
	
	// JJM TODO: Document the fields below
	String approvalCode;
	String retreivalReferenceNumber;
	String localTransactionDate;
	String localTransactionTime;
	
	public Reversal(Authorization merchant)	{
		super(merchant);
	}
	public void setTransactionIdentifier(String transactionIdentifier) {
		this.transactionIdentifier = transactionIdentifier;
	}
	public String getTransactionIdentifier() {
		// NOTE, THIS SHOULD ALWAYS BE 15 CHARACTERS I THINK
		// MAY ONLY BE AVAILABLE FOR VISA/MASTERCARD
		return VitalHelper.leftJustifyZero(transactionIdentifier, 15);
	}
	public String getTransactionID() {
		// TRANSACTION IDENTIFIER FOR INCREMENTAL (and REVERSAL)
		// HOWEVER, IT SEEMS THAT NON MC/VISA ARE BEING APPENDED AS 00000000000000
		// IN THE DATABASE INSTEAD OF NULL..	
	
		return getTransactionIdentifier();
	}
	public String getSecondaryAmountString() {
		return DataFormattingUtil.formatAmount(secondaryAmount);
	}
	// AUTHORIZATION ONLY
	public String getMarketSpecificData() {
		return "";
	}
	// AUTHORIZATION ONLY
	public String getCardHolderIdentificationData()	{
		return "";
	}
	// REVERSAL ONLY
	public String getReversalAndCancelDataI() {
		StringBuffer buf = new StringBuffer();
		
		buf.append(getApprovalCode());
				
		buf.append(getLocalTransactionDate());		
		buf.append(getLocalTransactionTime());		

		buf.append(getRetreivalReferenceNumber());
		
		return buf.toString();
	}
    /**
     * @return
     */
    public String getApprovalCode() {
    	// JJM TODO: FORMAT
        return approvalCode;
    }
    /**
     * @return
     */
    public String getRetreivalReferenceNumber() {
    	// JJM TODO: FORMAT
        return retreivalReferenceNumber;
    }
    /**
     * @param string
     */
    public void setApprovalCode(String string) {
        approvalCode = string;
    }
    /**
     * @param string
     */
    public void setRetreivalReferenceNumber(String string) {
        retreivalReferenceNumber = string;
    }
    /**
     * @return
     */
    public String getLocalTransactionDate() {
        return localTransactionDate;
    }
    /**
     * @return
     */
    public String getLocalTransactionTime() {
        return localTransactionTime;
    }
    /**
     * @param string
     */
    public void setLocalTransactionDate(String string) {
        localTransactionDate = string;
    }
    /**
     * @param string
     */
    public void setLocalTransactionTime(String string) {
        localTransactionTime = string;
    }
    /**
     * @return
     */
    public BigDecimal getSecondaryAmount() {
        return secondaryAmount;
    }
    /**
     * @param decimal
     */
    public void setSecondaryAmount(BigDecimal decimal) {
        secondaryAmount = decimal;
    }
}
