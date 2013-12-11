package biz.janux.commerce.payment.implementation.vendor.vital.transaction;

import java.math.BigDecimal;

import biz.janux.commerce.payment.implementation.vendor.vital.helper.VitalHelper;
import biz.janux.commerce.payment.interfaces.response.AuthorizationResponse;
import biz.janux.commerce.payment.interfaces.transaction.Authorization;
import biz.janux.commerce.payment.model.VitalMerchantAccount;
import biz.janux.commerce.payment.util.Constants;

public class Incremental extends VitalAuthorization{
	// Section 4.75
	// Transaction Identifier
	// 15 character field
	// ONLY USED IN INCREMENTAL/REVERSAL TRANSACTIONS
	String transactionIdentifier = null;
	
	/**
	 * Prepares an incremental authorization request.
	 * @param merchant
	 * @param response
	 */
	public Incremental(
		Authorization merchant,
		AuthorizationResponse response,
		BigDecimal additionalAmount)
	{
		super(merchant);
		
		this.requestedACI = Constants.REQUESTED_ACI_INCREMENTAL_AUTHORIZATION_REQUEST;
		setTransactionIdentifier(response.getTransactionIdentifier());
		setTransactionAmount(additionalAmount);
	}
   	public void setTransactionIdentifier(String transactionIdentifier)
   	{
		this.transactionIdentifier = transactionIdentifier;
   	}
   	public String getTransactionIdentifier()
   	{
		// NOTE, THIS SHOULD ALWAYS BE 15 CHARACTERS I THINK
	   	// MAY ONLY BE AVAILABLE FOR VISA/MASTERCARD
	   	return VitalHelper.leftJustifyZero(transactionIdentifier, 15);
   	}
	public String getTransactionID()
	{
		// TRANSACTION IDENTIFIER FOR INCREMENTAL (and REVERSAL)
		// HOWEVER, IT SEEMS THAT NON MC/VISA ARE BEING APPENDED AS 00000000000000
		// IN THE DATABASE INSTEAD OF NULL..	
		
		return getTransactionIdentifier();
	}
}
