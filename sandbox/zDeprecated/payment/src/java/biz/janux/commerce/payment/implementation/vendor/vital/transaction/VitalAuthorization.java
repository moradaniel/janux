package biz.janux.commerce.payment.implementation.vendor.vital.transaction;

import java.math.BigDecimal;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import biz.janux.commerce.payment.implementation.common.dao.hibernate.HibernateDao;
import biz.janux.commerce.payment.interfaces.transaction.Authorization;
import biz.janux.commerce.payment.model.CreditCard;
import biz.janux.commerce.payment.model.VitalMerchantAccount;
import biz.janux.commerce.payment.util.Constants;
import biz.janux.commerce.payment.util.DataFormattingUtil;

/**
 * 
 * @author Nilesh
 * 
 */
public class VitalAuthorization  implements VitalTransaction , Constants {
	private Authorization authorization;
    
	private static final Logger logger = Logger.getLogger(VitalAuthorization.class);
 	
	// CONSTANT
	// Section 4.69
	// 'Y' -- Device is CPS capable
	// 'P' -- Device is CPS capable, Hotel Lodging preferred customer
	//        authorization request? Manually keyed only.
	//char requestedACI = REQUESTED_ACI_CPS_CAPABLE;
	char requestedACI = REQUESTED_ACI_CPS_CAPABLE;
//	 Section 4.89
	// "54" -- Card Present
	// "56" -- Card Not Present
	// "58" -- Card Authentication (Check if lost/stolen, also AVS)
	String transactionCode = TRANSACTION_CODE_CARD_AUTHENTICATION  ;

	// CONSTANT
	// Section 4.23
	// TODO: May not need to be a constant.
	//char cardHolderID = CARD_HOLDER_ID_SIGNATURE;
	char cardHolderID = CARD_HOLDER_ID_SIGNATURE;
	// Section 4.1
	char accountDataSource;

	// Section 4.31
	// Can contain either TRACK 1, TRACK 2, OR ACCT#<FS>EXP DATE<FS>
	// CONTENTS DEPENDENT ON ACCOUNT DATA SOURCE
	// Length MAX 76 CHARS, TERMINATED WITH FIELD SEPARATOR
	String customerDataField;

	// Section 4.24.4
	// Street Address<SP>Zip Code
	// Length MIN 0, MAX 29
	// TODO: REVIEW SECTION 4.24.4 FOR CONTENT FORMATTING
	// DEFAULT BLANK
	// IF RESET WITH SETTER METHOD, CHANGE AVS OPTIONS?
	String addressVerificationData = "";

	// Section 4.88
	// $12.50 = "1250"
	// MIN 1 MAX 12
	String transactionAmount;                       //get from authorization

	// Section 4.54.3
	int stayOfDuration;

	// Section 4.92
	// Transaction Sequence Number
	// Value: 0001-9999
	int transactionSequenceNumber;
	
	String approvalCode="";
   
	VitalMerchantAccount merchant;
	CreditCard creditCard ;
	public VitalAuthorization(Authorization authorization){
		 this.authorization = authorization;
 	}
	
	public byte[] getVitalMessage(){
		
		if (logger.isDebugEnabled()) {
			logger.debug("getVitalMessage()");
		}	
		/* 
		 *  hibernate used  for getting data from database  
		 */
		HibernateDao hibernateDao = HibernateDao.getInstance();
		merchant= (VitalMerchantAccount)hibernateDao.loadfromDB(VitalMerchantAccount.class , authorization.getMerchantId());
		creditCard = (CreditCard)hibernateDao.loadfromDB(CreditCard.class , authorization.getInstrumentId());
		
		// Load the merchant from the DB.
		// USe HIbernate ??
		//merchant = Merchant.load(merchantId)	
		// use above info to create vital message of address verification in vital forma 
			StringBuffer buffer = new StringBuffer(256);
			buffer.append(STX);
			buffer.append(getAuthContents());
			buffer.append(ETX);
			return buffer.toString().getBytes();	
		}
  		
	public String getAuthContents(){
	
		if (logger.isDebugEnabled()) {
			logger.debug("getAuthContents()");
		}
		String cardnumber = creditCard.getCardNumber();
		merchant.setTimeZoneDifferential(merchant.getTimeZoneDifferential());
		try {
			setTransactionAmount(authorization.getAuthAmount());
			setCustomerDataField(creditCard.decrypt(cardnumber) , creditCard.getExpirationDate());
			setStayOfDuration(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 		StringBuffer buf = new StringBuffer(249);
			buf.append(RECORD_FORMAT_AUTHORIZATION);
			buf.append(APPLICATION_TYPE_INTERLEAVED);
			buf.append(MESSAGE_DELIMITER);
			buf.append(merchant.getAcqBankIdentificationNumber());
			buf.append(merchant.getNumber());
			buf.append(merchant.getStoreNumber());
			buf.append(merchant.getTerminalNumber());
			buf.append(DEVICE_CODE);
			buf.append(INDUSTRY_CODE);
			buf.append(merchant.getCurrencyCode());
			buf.append(merchant.getCountry().getIso3numeric());			
			buf.append(merchant.getCityCode());
			buf.append(LANGUAGE_INDICATOR_ENGLISH);
			buf.append(merchant.getTimeZoneDifferential());
			buf.append(MERCHANT_CATEGORY_CODE_HOTEL);
			// For REVERSAL, this is the returned ACI
			buf.append(requestedACI);
			
			// NEEDS TO BE GENERATED OUTSIDE THIS CLASS
			// HOWEVER, IF IT IS NOT, WE WILL SEND 0001, WHICH HAS WORKED
			if (transactionSequenceNumber == 0)
				transactionSequenceNumber = 1;
			buf.append(DataFormattingUtil.rightJustifyZero(String
					.valueOf(transactionSequenceNumber), 4));
			
			buf.append(transactionCode); 								//**** 
			buf.append(cardHolderID);
			buf.append(accountDataSource);                             //****
			buf.append(customerDataField);								//****
	
			// ABOVE IS COMMON TO AUTHORIZATION, INCREMENTAL, AND REVERSAL

			// Cardholder Identificiation Data
			buf.append(FIELD_SEPARATOR);
			buf.append(getCardHolderIdentificationData());
			// Receiving Institution ID (Not used by Hotel)
			buf.append(FIELD_SEPARATOR);
			buf.append(FIELD_SEPARATOR);	
			buf.append(transactionAmount);
//			buf.append(DataFormattingUtil.formatAmount(authorization.getAuthAmount()));   //get from Authorization
			// Secondary Amount
			buf.append(FIELD_SEPARATOR);
			buf.append(getSecondaryAmountString());
			// MARKET SPECIFIC DATA
			buf.append(FIELD_SEPARATOR);
			buf.append(getMarketSpecificData());
			// CARD ACCEPTOR DATA
			buf.append(FIELD_SEPARATOR);
			buf.append(getCardAcceptorData());
			// TRANSACTION ID
			buf.append(FIELD_SEPARATOR);
			buf.append(getTransactionID());
			// REVERSAL AND CANCEL DATA I
			buf.append(FIELD_SEPARATOR);
			buf.append(getReversalAndCancelDataI());
			buf.append(FIELD_SEPARATOR);
			buf.append(GROUP_III_VERSION_NUMBER_DEVELOPER_INFORMATION);
			buf.append(DEVELOPER_ID);
			buf.append(VERSION_ID);
			buf.append(FIELD_SEPARATOR);
			buf.append(FIELD_SEPARATOR);
			return buf.toString();
		}
 
	// AUTHORIZATION, INCREMENTAL, REVERSAL
	public String getCardAcceptorData() {

		if (logger.isDebugEnabled()) {
			logger.debug("getCardAcceptorData()");
		}
		StringBuffer buf = new StringBuffer();
		buf.append(merchant.getName());
		buf.append(merchant.getLocation());
		buf.append(merchant.getState().getCode());
		return buf.toString();
	}

	// AUTHORIZATION
	public String getMarketSpecificData() {

		if (logger.isDebugEnabled()) {
			logger.debug("getMarketSpecificData()");
		}

		StringBuffer buf = new StringBuffer();
		buf.append(PRESTIGIOUS_PROPERTY_INDICATOR_NOT_PARTICIPATING);
		buf.append(MARKET_SPECIFIC_DATA_ID_HOTEL);
		buf.append(DataFormattingUtil.rightJustifyZero(String.valueOf(stayOfDuration), 2));   //**
    	return buf.toString();
	}

	// AUTHORIZATION
	public String getCardHolderIdentificationData() {
		return addressVerificationData;                                //**
	}

	/**
	 * @return
	 */
	public String getCustomerDataField() {
		return customerDataField;
	}
	
	// INCREMENTAL AND REVERSAL
	public String getTransactionID() {
		// NO-OP FOR AUTHORIZATION
		return "";
	}

	// REVERSAL
	public String getReversalAndCancelDataI() {
		// NO-OP FOR AUTHORIZATION, INCREMENTAL
		return "";
	}

	// REVERSAL
	public String getSecondaryAmountString() {
		// NO-OP FOR AUTHORIZATION, INCREMENTAL
		return "";
	}
	public String toString() {
		StringBuffer buf = new StringBuffer();

		buf.append(" ***************************************************************************** \n");
		buf.append("ACQ Bank Identifier Number: \""
				+ merchant.getAcqBankIdentificationNumber() + "\"\n");
		buf.append("           Merchant Number: \""
				+ merchant.getNumber() + "\"\n");
		buf.append("              Store Number: \"" + merchant.getStoreNumber()
				+ "\"\n");
		buf.append("           Terminal Number: \""
				+ merchant.getTerminalNumber() + "\"\n");
		buf.append("                 City Code: \"" + merchant.getCityCode()
				+ "\"\n");
		buf.append("            Time Zone Diff: \""
				+ merchant.getTimeZoneDifferential() + "\"\n");
		buf.append("             Requested ACI: \"" + requestedACI + "\"\n");
		buf.append("          Transaction Code: \"" + transactionCode + "\"\n");
		buf.append("            Card Holder ID: \"" + cardHolderID + "\"\n");
		buf.append("       Account Data Source: \"" + accountDataSource
				+ "\"\n");
		buf.append("       Customer Data Field: \"" + customerDataField
				+ "\"\n");
		buf.append(" Address Verification Data: \"" + addressVerificationData
				+ "\"\n");
		buf.append("        Transaction Amount: \"" + transactionAmount
				+ "\"\n");
		buf.append("          Stay of Duration: \"" + stayOfDuration + "\"\n");
		buf.append("             Merchant Name: \""
				+ merchant.getName() + "\"\n");
		buf.append("         Merchant Location: \""
				+ merchant.getLocation() + "\"\n");
		buf.append("            Merchant State: \""
				+ merchant.getState().getCode() + "\"\n");
		buf.append(" ***************************************************************************** \n");

		return buf.toString();
	}     
	
	public void setCustomerDataField(String string, String expDate) {

		if (logger.isDebugEnabled()) {
			logger.debug("setCustomerDataField(string=" + string + ", expDate="
					+ expDate + ")");
		}

		char ch = string.charAt(0);
		switch (ch) {
		case '%':
			this.accountDataSource = ACCOUNT_DATA_SOURCE_TRACK_1_READ;
			// NOTE: The Reversal Constructor will set this value
			// to be TRANSACTION_CODE_REVERSAL_PRE_SETTLEMENT
			if (!(this instanceof Reversal)) {
				this.transactionCode = TRANSACTION_CODE_CARD_PRESENT;
			}
			this.customerDataField = DataFormattingUtil.getTrack1(string);
			break;
		case ';':
			this.accountDataSource = ACCOUNT_DATA_SOURCE_TRACK_2_READ;
			// NOTE: The Reversal Constructor will set this value
			// to be TRANSACTION_CODE_REVERSAL_PRE_SETTLEMENT
			if (!(this instanceof Reversal)) {
				this.transactionCode = TRANSACTION_CODE_CARD_PRESENT;
			}
			this.customerDataField = DataFormattingUtil.getTrack2(string);
			break;
		default:
			// TODO: Default to track 2 capable but manually keyed?
			// ACCOUNT_DATA_SOURCE_KEYED_TRACK_2_CAPABLE
			this.accountDataSource = ACCOUNT_DATA_SOURCE_NO_CARDREADER;

			// ?
			// This could probably be a constant of CARD_PRESENT
			// Unless we can easily differentiate???
			// In reality, a hotel should probably aways ask to see the card
			// to take an imprint, attempt to swipe, etc.

			// NOTE: The Reversal Constructor will set this value
			// to be TRANSACTION_CODE_REVERSAL_PRE_SETTLEMENT
			if (!(this instanceof Reversal)) {
				this.transactionCode = TRANSACTION_CODE_CARD_NOT_PRESENT;
			}

			// For a manually keyed entry, we need send a 'P' for the requested
			// ACI.
			// However, the Incremental constructor will set this to 'I'. We
			// don't
			// want to overwrite this.
			if (!(this instanceof Incremental)) {
				this.requestedACI = REQUESTED_ACI_CPS_CAPABLE_PREFERRED_CUSTOMER_AUTHORIZATION;
			}
			this.customerDataField = string + FIELD_SEPARATOR + expDate
					+ FIELD_SEPARATOR;
			break;
		}
	}
	/**
	 * @param requestedACI 
	 */
	public void setRequestedACI(char requestedACI) {
		this.requestedACI = requestedACI;
	}
	/**
	 * @param stayOfDuration 
	 */
	public void setStayOfDuration(int stayOfDuration) {
		this.stayOfDuration = stayOfDuration;
	}
	/**
	 * @param amount
	 */
	public void setTransactionAmount(BigDecimal amount) {
		transactionAmount = DataFormattingUtil.formatAmount(amount);
	}

	/**
	 * @param transactionCode
	 */
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}
 	/**
	 * @return int
	 */
	public int getTransactionSequenceNumber() {
		return transactionSequenceNumber;
	}
 	/** 
	 * @param transactionSequenceNumber 
	 */
	public void setTransactionSequenceNumber(int transactionSequenceNumber) {
		this.transactionSequenceNumber = transactionSequenceNumber;
	}

	
  }
