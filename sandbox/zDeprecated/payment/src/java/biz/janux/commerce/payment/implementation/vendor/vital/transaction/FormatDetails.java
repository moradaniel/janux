package biz.janux.commerce.payment.implementation.vendor.vital.transaction;

import java.math.BigDecimal;
import java.sql.Date;

import org.apache.log4j.Logger;
import biz.janux.commerce.payment.implementation.vendor.vital.helper.VitalHelper;
import biz.janux.commerce.payment.model.VitalAuthorizationResponseModel;
import biz.janux.commerce.payment.util.BatchUtil;
import biz.janux.commerce.payment.util.Constants;
import biz.janux.commerce.payment.util.DataFormattingUtil;
/**
 * 
 * @author Nilesh
 *
 */
public class FormatDetails {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FormatDetails.class);
	/**
	 * @Nilesh
	 * 
	 * */
	public enum DetailType { PURCHASE, CREDIT };
	
	BigDecimal settlementAmount ;

	BigDecimal authorizedAmount;

	BigDecimal totalAuthorizedAmount;

	// Section 4.158
	// Record Type
	// D@@@P = Non-Amex settlement
	// D@@AP = Amex Settlement
	String recordType = Constants.RECORD_TYPE_DETAIL;

	// Section 4.30
	// Length 22, Left justified space filled
	String cardHolderAccountNumber;

	// Section 4.1
	// Account Data Source -- This field contains a 1-character code specifying
	// the method used to obtain the cardholder account number.
	char accountDataSource;

	// Section 4.208
	// Length 2, "54" = Purchase, "56" = Card not present, "CR" = Credit/Return
	String transactionCode = Constants.TRANSACTION_CODE_CARD_PRESENT;

	// Section 4.40
	// Length 6, YYMMDD
	// NOTE, this is labeled as CHECK IN DATE in the documentation.
	// This is actually the authorization date. For example, for an advanced
	// deposit
	// this should be the date the reservation was made / date the charge
	// was applied
	String checkInDate;//="081211";

	// Section 4.113.3
	// Length 4, MMDD
	// NOTE, this is labeled as CHECK OUT DATE in the documentation.
	// This is actually the settlement date!
	String checkOutDate;//="1211";

	String folioNumber;

	char returnedACI ;

	// Section 4.166
	// Length 1
	char requestedACI = Constants.REQUESTED_ACI_CPS_CAPABLE;

	char authorizationSourceCode = Constants.AUTHORIZATION_SOURCE_CODE_DEVICE_GENERATED;

	String transactionSequenceNumber;

	String responseCode;

	String approvalCode;

	String localTransactionTime;

	char avsResultCode;

	String transactionIdentifier;

	String validationCode;

	String transactionStatusCode;

	/*
	 * FOR American Express Cards, this field is "Special Program Indicator"
	 * Section 4.186 '1' == PURCHASE (
	 */
	char noShowIndicator = Constants.NO_SHOW_INDICATOR_NOT_APPLICABLE;

	public FormatDetails() {
	}

	public FormatDetails(VitalAuthorizationResponseModel authresponse , DetailType type) {
		if (logger.isDebugEnabled()) {
			logger.debug("Detail(response=" + authresponse + ")");
		}
		// Convert string to char..
			char requestedACI = authresponse.getRequestedACI().charAt(0);
			char returnedACI = authresponse.getReturnedACI().charAt(0);
			char avsResultCode = authresponse.getAvsResultCode().charAt(0);
			char authorizationSourceCode = authresponse.getAuthSourceCode().charAt(0);
			this.transactionSequenceNumber=BatchUtil.transactionSequenceNumber(authresponse.getMerchantId());
			//If type is purchase
			if(type == DetailType.PURCHASE){
				setTransactionSequenceNumber(transactionSequenceNumber);
				setRequestedACI(requestedACI);
				setReturnedACI(returnedACI);
				setResponseCode(authresponse.getResponseCode());
				setApprovalCode(authresponse.getApprovalCode());
				setLocalTransactionTime(authresponse.getLocalTransTime());
				setAvsResultCode(avsResultCode);
				setTransactionIdentifier(authresponse.getTransactionIdentifier());
				setValidationCode(authresponse.getValidationCode());
				setAuthorizationSourceCode(authorizationSourceCode);
	
			setTransactionStatusCode(Constants.TRANSACTION_STATUS_CODE_NO_REVERSAL);
			}
			//If type is credit
		else{
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
			if (authresponse.isReversal())
				setTransactionStatusCode(Constants.TRANSACTION_STATUS_CODE_REVERSAL);
	}
	public String format() {
		if (logger.isDebugEnabled()) {
			logger.debug("format()");
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(Constants.STX);
		buffer.append(formatDetailContents());
		buffer.append(Constants.ETB);
		buffer.append((char) DataFormattingUtil.calcLRC(buffer.toString().getBytes()));
		return buffer.toString();
	}
	public String formatDetailContents() {
		if (logger.isDebugEnabled()) {
			logger.debug("formatDetailContents()");
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(Constants.RECORD_FORMAT_SETTLEMENT);
		buffer.append(Constants.APPLICATION_TYPE_SETTLEMENT);
		buffer.append(Constants.MESSAGE_DELIMITER);
		buffer.append(Constants.X25_ROUTING_ID);
		// Default to RECORD_TYPE_DETAIL
		// IS SET BY AmexDetail class to be RECORD_TYPE_AMEX_DETAIL
		buffer.append(recordType);
		buffer.append(getTransactionCode());
		// or card not present
		// or Credit (new subclass..)
		// Or AVS?
		buffer.append(Constants.CARD_HOLDER_ID_SIGNATURE);
		// SAME ACCT DATA SOURCE AS AUTHORIZATION?
		// buffer.append(Constants.ACCOUNT_DATA_SOURCE_KEYED_TRACK_2_CAPABLE);
		buffer.append(accountDataSource);
		buffer.append(VitalHelper.leftJustifySpace(cardHolderAccountNumber, 22));
		// The Requested ACI
		buffer.append(getRequestedACI());
		// The Returned ACI
		buffer.append(getReturnedACI());
		buffer.append(getAuthorizationSourceCode());
		buffer.append(getTransactionSequenceNumber());
		buffer.append(getResponseCode());
		buffer.append(getApprovalCode());
		buffer.append(getCheckOutDate());
		buffer.append(getLocalTransactionTime());
		buffer.append(getAvsResultCode());
		buffer.append(getTransactionIdentifier());
		buffer.append(getValidationCode());
		buffer.append(Constants.VOID_INDICATOR_NOT_VOIDED);
		buffer.append(getTransactionStatusCode());
		buffer.append(Constants.REIMBURSEMENT_ATTRIBUTE);
		// Amount we are settling for
		buffer.append(getSettlementAmountString());
		// Original Authorized Amount
		buffer.append(getAuthorizedAmountString());
		// Total Authorized Amount (Original, + total increments - total
		// reversals)
		buffer.append(getTotalAuthorizedAmountString());
		buffer.append(Constants.PURCHASE_IDENTIFIER_FORMAT_CODE_HOTEL);
		// uncomment if weare using the folioNumber 
		//buffer.append(VitalHelper.leftJustifySpace(folioNumber, 25));
		buffer.append(VitalHelper.leftJustifySpace("", 25));
		buffer.append(Constants.MARKET_SPECIFIC_DATA_ID_HOTEL);
		buffer.append(noShowIndicator);
		// TODO: Extra Charges
		buffer.append(VitalHelper.leftJustifyZero("", 6));
		buffer.append(getCheckInDate());
		return buffer.toString();
	}

	/**
	 * @param string
	 */
	public void setCardHolderAccountNumber(String track2, String cardNumber) {
		if (logger.isDebugEnabled()) {
			logger.debug("setCardHolderAccountNumber(track2=" + track2
					+ ", cardNumber=" + cardNumber + ")");
		}
		char ch = track2.charAt(0);
		switch (ch) {
		case '%':
			this.accountDataSource = Constants.ACCOUNT_DATA_SOURCE_TRACK_1_READ;
			if (!(this instanceof CreditDetail)) {
				this.transactionCode = Constants.TRANSACTION_CODE_CARD_PRESENT;
			}
			break;
		case ';':
			this.accountDataSource = Constants.ACCOUNT_DATA_SOURCE_TRACK_2_READ;
			if (!(this instanceof CreditDetail)) {
				this.transactionCode = Constants.TRANSACTION_CODE_CARD_PRESENT;
			}
			break;
		default:
			
			// TODO: Default to track 2 capable but manually keyed?
			// ACCOUNT_DATA_SOURCE_KEYED_TRACK_2_CAPABLE
			this.accountDataSource = Constants.ACCOUNT_DATA_SOURCE_NO_CARDREADER;
			// ?
			// This could probably be a constant of CARD_PRESENT
			// Unless we can easily differentiate???
			// In reality, a hotel should probably aways ask to see the card
			// to take an imprint, attempt to swipe, etc.
			if (!(this instanceof CreditDetail)) {
				this.transactionCode = Constants.TRANSACTION_CODE_CARD_NOT_PRESENT;
			}
			break;
		}

		// WE ONLY WANT THE CARD #, NOT THE WHOLE TRACK DATA
		this.cardHolderAccountNumber = cardNumber;
	}
	/**
	 * @param decimal
	 */
	public void setSettlementAmount(BigDecimal decimal) {
		settlementAmount = decimal;
	}
	/**
	 * 
	 * @param decimal
	 */
	public void setAuthorizedAmount(BigDecimal decimal) {
		authorizedAmount = decimal;
	}
	/**
	 * 
	 * @param decimal
	 */
	public void setTotalAuthorizedAmount(BigDecimal decimal) {
		totalAuthorizedAmount = decimal;
	}

	/**
	 * @return String
	 */
	public String getCardHolderAccountNumber() {
		return cardHolderAccountNumber;
	}

	/**
	 * @return String
	 */
	public String getCheckInDate() {
		return checkInDate;
	}

	/**
	 * @return String
	 */
	public String getCheckOutDate() {
		return checkOutDate;
	}

	/**
	 * @return String
	 */
	public String getFolioNumber() {
		return folioNumber;
	}

	/**
	 * @param string
	 */
	public void setCheckInDate(Date date) {
		checkInDate = DataFormattingUtil.formatDateToYYMMDD(date);
	}

	/**
	 * @param string
	 */
	public void setCheckOutDate(Date date) {
		checkOutDate = DataFormattingUtil.formatDateToMMDD(date);
	}

	/**
	 * @param string
	 */
	public void setFolioNumber(String string) {
		folioNumber = string;
	}

	/**
	 * @return String
	 */
	public String getApprovalCode() {
		return approvalCode;
	}

	/**
	 * @return String
	 */
	public char getAvsResultCode() {
		return avsResultCode;
	}

	/**
	 * @return String
	 */
	public String getResponseCode() {
		return responseCode;
	}

	/**
	 * @return char
	 */
	public char getReturnedACI() {
		return returnedACI;
	}

	/**
	 * @return String
	 */
	public String getTransactionSequenceNumber() {
		return VitalHelper.rightJustifyZero(transactionSequenceNumber, 4);
	}

	/**
	 * @return String
	 */
	public String getValidationCode() {
		return validationCode;
	}

	/**
	 * @param string
	 */
	public void setApprovalCode(String string) {
		approvalCode = string;
	}

	/**
	 * @param char
	 */
	public void setAvsResultCode(char c) {
		avsResultCode = c;
	}

	/**
	 * @param string
	 */
	public void setResponseCode(String string) {
		responseCode = string;
	}

	/**
	 * @param c
	 */
	public void setReturnedACI(char c) {
		returnedACI = c;
	}

	/**
	 * @param string
	 */
	public void setTransactionSequenceNumber(String transactionSequenceNumber) {
		this.transactionSequenceNumber = transactionSequenceNumber;
	}

	/**
	 * @param string
	 */
	public void setValidationCode(String string) {
		validationCode = string;
	}

	/**
	 * @return
	 */
	public String getTransactionIdentifier() {
		return transactionIdentifier;
	}

	/**
	 * @param string
	 */
	public void setTransactionIdentifier(String string) {
		transactionIdentifier = string;
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
	public void setLocalTransactionTime(String string) {
		localTransactionTime = string;
	}

	/**
	 * @return
	 */
	public String getTransactionCode() {
		return transactionCode;
	}

	/**
	 * @param string
	 */
	public void setTransactionCode(String string) {
		transactionCode = string;
	}

	public BigDecimal getAuthorizedAmount() {
		return authorizedAmount;
	}

	public BigDecimal getTotalAuthorizedAmount() {
		return totalAuthorizedAmount;
	}

	// For CreditDetail, we can keep the original negative amount
	// But the getSettelemtnAmount() (used by format) will return the absolute
	// value
	public BigDecimal getSettlementAmountUnAltered() {
		return settlementAmount;
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

	public String getSettlementAmountString() {
		return VitalHelper.rightJustifyZero(DataFormattingUtil
				.formatAmount(getSettlementAmount()), 12);
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();

		buf
				.append(" ***************************************************************************** \n");
		buf.append("Transaction Sequence Number: \""
				+ getTransactionSequenceNumber() + "\"\n");
		buf.append("     Transaction Identifier: \""
				+ getTransactionIdentifier() + "\"\n");
		buf.append("              Approval Code: \"" + getApprovalCode()
				+ "\"\n");
//		buf.append("          Settlement Amount: \""
//				+ getSettlementAmountString() + "\"\n");
		buf.append("          Authorized Amount: \""
				+ getAuthorizedAmountString() + "\"\n");
		buf.append("    Total Authorized Amount: \""
				+ getTotalAuthorizedAmountString() + "\"\n");
		buf
				.append(" ***************************************************************************** \n");

		return buf.toString();
	}

	/**
	 * @return
	 */
	public char getAuthorizationSourceCode() {
		return authorizationSourceCode;
	}

	/**
	 * @param c
	 */
	public void setAuthorizationSourceCode(char c) {
		authorizationSourceCode = c;
	}

	/**
	 * @return
	 */
	public char getNoShowIndicator() {
		return noShowIndicator;
	}

	/**
	 * @param c
	 */
	public void setNoShowIndicator(char c) {
		noShowIndicator = c;
	}

	/**
	 * @return
	 */
	public String getRecordType() {
		return recordType;
	}

	/**
	 * @param string
	 */
	public void setRecordType(String string) {
		recordType = string;
	}

	/**
	 * @return
	 */
	public char getRequestedACI() {
		return requestedACI;
	}

	/**
	 * @param c
	 */
	public void setRequestedACI(char c) {
		requestedACI = c;
	}

	/**
	 * @return
	 */
	public String getTransactionStatusCode() {
		return transactionStatusCode;
	}

	/**
	 * @param string
	 */
	public void setTransactionStatusCode(String string) {
		transactionStatusCode = string;
	}
}
