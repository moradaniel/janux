package biz.janux.commerce.payment.implementation.vendor.vital.transaction;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import biz.janux.commerce.payment.util.Constants;



public class CreditDetail extends FormatDetails{
	
	private static final Logger logger = Logger.getLogger(CreditDetail.class);
	public CreditDetail() {
		if (logger.isDebugEnabled()) {
			logger.debug("CreditDetail()");
		}
		setTransactionCode(Constants.TRANSACTION_CODE_CREDIT_RETURN);
		setRequestedACI(Constants.REQUESTED_ACI_CPS_CAPABLE);
		setReturnedACI(' ');
		setAuthorizationSourceCode(Constants.AUTHORIZATION_SOURCE_CODE_NOT_AUTHORIZED);
		// auto generate for credit card tran?
		// auto generated from settlement object?
		setTransactionSequenceNumber("0000");
		setResponseCode("  ");
		setApprovalCode("      ");
		setAvsResultCode('0');
		setTransactionIdentifier("000000000000000");
		setValidationCode("    ");

		setTransactionStatusCode(Constants.TRANSACTION_STATUS_CODE_NO_REVERSAL);
	}
	public String getAuthorizedAmountString() {
		return "000000000000";
	}
	public String getTotalAuthorizedAmountString() {
		return "000000000000";
	}
	public BigDecimal getSettlementAmount() {
		return settlementAmount.abs();
	}
}
