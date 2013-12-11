package biz.janux.commerce.payment.implementation.vendor.vital.transaction;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import biz.janux.commerce.payment.implementation.common.dao.hibernate.HibernateDao;
import biz.janux.commerce.payment.interfaces.model.Address;
import biz.janux.commerce.payment.interfaces.model.MerchantAccount;
import biz.janux.commerce.payment.interfaces.transaction.AddressVerification;
import biz.janux.commerce.payment.interfaces.transaction.Authorization; 
import biz.janux.commerce.payment.model.CreditCard;
import biz.janux.commerce.payment.model.State;
import biz.janux.commerce.payment.model.VitalMerchantAccount;
import biz.janux.commerce.payment.util.*;

/**
 * 
 * @author Nilesh
 * @implements VitalTransaction and Constants
 * 
 * This will create vital specific message for Address Verification 
 *  
 * */
public class VitalAddressVerification implements VitalTransaction , Constants{
	 
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VitalAddressVerification.class);

	/*
	 *  CONSTANT
	 * 	Section 4.69
	 *  'Y' -- Device is CPS capable
	 *  'P' -- Device is CPS capable, Hotel Lodging preferred customer
	 *  authorization request? Manually keyed only.
	 */
	 char requestedACI = REQUESTED_ACI_CPS_CAPABLE;

	 /*
	  * Section 4.89
	  * "54" -- Card Present
	  * "56" -- Card Not Present
	  * "58" -- Card Authentication (Check if lost/stolen, also AVS)
	  */
 	 String transactionCode;

 	 /*
 	  *	CONSTANT 
 	  * Section 4.23
	  * TODO: May not need to be a constant.
	  * 
 	  */ 
	 char cardHolderID = CARD_HOLDER_ID_SIGNATURE;
 	 
	/*
	 *  Section 4.1
	 */
	char accountDataSource;

	/*
	 * 	Section 4.31
	 *  Can contain either TRACK 1, TRACK 2, OR ACCT#<FS>EXP DATE<FS>
	 *  CONTENTS DEPENDENT ON ACCOUNT DATA SOURCE
	 *  Length MAX 76 CHARS, TERMINATED WITH FIELD SEPARATOR
	 *  
	 */
	String customerDataField;

	/*
	 * Section 4.24.4
	 * Street Address<SP>Zip Code
	 * Length MIN 0, MAX 29
	 * TODO: REVIEW SECTION 4.24.4 FOR CONTENT FORMATTING
	 * DEFAULT BLANK
	 * IF RESET WITH SETTER METHOD, CHANGE AVS OPTIONS?
	 */
	 String addressVerificationData = "";

	 /*
	  * Section 4.88
	  * $12.50 = "1250"
	  * MIN 1 MAX 12
	  * 
	  */
 	String transactionAmount;

 	/*
 	 * Section 4.54.3
 	 * 
 	 */
	int stayOfDuration;

	/*
	 * Section 4.92
	 * Transaction Sequence Number
	 * Value: 0001-9999
	 * 
	 */
 	int transactionSequenceNumber;
 	private MerchantAccount  merchant;
	private CreditCard creditCard;
	private AddressVerification addressVerification;
	
	public VitalAddressVerification(AddressVerification addressVerification){
		this.addressVerification = addressVerification; 
	}
	/*
 	 * @return byte[]   
 	 * This method will return vital message in vital format which is used for address verification  
 	 */
 	public byte[] getVitalMessage(){
		
			/* 
			 *  hibernate used  for getting data from database  
			 */
			HibernateDao hibernateDao =HibernateDao.getInstance();
			merchant= (VitalMerchantAccount)hibernateDao.loadfromDB(VitalMerchantAccount.class , addressVerification.getMerchantId());
			creditCard = (CreditCard)hibernateDao.loadfromDB(CreditCard.class , addressVerification.getInstrumentId());
			this.setCardHolderID(CARD_HOLDER_ID_AVS);
	        this.setTransactionAmount(new BigDecimal(0));
	        this.setTransactionCode(TRANSACTION_CODE_CARD_AUTHENTICATION);
	        this.setStayOfDuration(0);
	        String cardnumber = creditCard.getCardNumber();
	        try{
				this.setCustomerDataField(creditCard.decrypt(cardnumber) , creditCard.getExpirationDate());
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.setAddressVerificationData(creditCard);
	 	 	StringBuffer buffer = new StringBuffer(256);
		  	buffer.append(STX);
			buffer.append(getAddContents());
			buffer.append(ETX);
		    return buffer.toString().getBytes();
 	}
 
	public String getAddContents() {

		if (logger.isDebugEnabled()) {
			logger.debug("getAuthContents()");
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
 	 	buf.append(transactionCode);                                       
		buf.append(cardHolderID);
		buf.append(accountDataSource);
		buf.append(customerDataField);
		/*
		 * ABOVE IS COMMON TO AUTHORIZATION, INCREMENTAL, AND REVERSAL
 		 */
		/*
		 * Cardholder Identificiation Data
		 */ 
		buf.append(FIELD_SEPARATOR);
		buf.append(getCardHolderIdentificationData());
		
		/*
		 * Receiving Institution ID (Not used by Hotel)
		 */ 
		buf.append(FIELD_SEPARATOR);

		/*
		 * Transaction Amount
		 */ 
		buf.append(FIELD_SEPARATOR);
		buf.append(transactionAmount);

		/*
		 * Secondary Amount
		 */ 
		buf.append(FIELD_SEPARATOR);
		buf.append(getSecondaryAmountString());
		/*
		 * Market Specific Data  
		 */ 
		buf.append(FIELD_SEPARATOR);
		
		buf.append(getMarketSpecificData());				 
		/*
		 * Card Acceptor Data
		 */ 
		buf.append(FIELD_SEPARATOR);
		buf.append(getCardAcceptorData()); 
		/*
		 * Transaction  ID
		 */ 
		buf.append(FIELD_SEPARATOR);
		buf.append(getTransactionID());
		/*
		 *  Reversal and Cancel Data Info
		 */
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
 	 
	public void setAddressVerificationData(CreditCard creditCard)
	{
 		/*
 		 * TODO: Field can not contain more than 29 characters
 		 */  
		StringBuffer buffer = new StringBuffer();
 		String address = getAddress(creditCard);	
 		
		buffer.append(address);
		/*
		 * If Address field is given, add a space between
		 * Address & Zip.
		 */ 
		if(address != null && !address.trim().equals("")){
	 	    buffer.append(' ');
		}
		buffer.append(getZip(creditCard));
		setAddressVerificationData(buffer.toString());	 
	}
 	
	public String getAddress(CreditCard creditCard){
 		String address1 = creditCard.getAddress1();	
 		
	 	StringBuffer address = new StringBuffer();
	 	/*
	 	 * ONLY THE STREET NUMBER IS REQUIRED. NOT STREET NAME?
	 	 * MAYBE I MISUNDERSTOOD JOE, AND HE MEANT STREET
	 	 * ADDRESS IS REQUIRED, AS OPPOSED TO ENTIRE ADDRESS.
	 	 * JJM 8/5/04: There was a bug where the address was never
	 	 * being parsed out. While fixing this, I tested  
	 	 * remark about only the street number being checked. I
	 	 * have verified this with my card. Using "16", it worked.
	 	 * Using "16NOTMYSTREETDR.", it worked. So I'm going to
	 	 * make this happen.
	 	 * 
	 	 */
	 	if(address1 != null && !address1.trim().equals("")){
		 	address1 = address1.toUpperCase();
			for(int i=0; i < address1.length(); i++){
			 	char ch = address1.charAt(i);
		 		/*
				 * Strip out spaces
				 */ 
				if(ch == ' ')
				   continue;
	 			/*
				 * Technically, isDigit returns true for non-latin(?)
				 * digits (see javadoc for Character#isDigit). Beats
				 * if(ch >= '0' && ch <= '9') though!
				 * 
				 */ 
			 	if(!Character.isDigit(ch))
				    continue;
				address.append(ch);
			}
		}
 		return address.toString();
	  }	//getAddress end
 		
	/*
	 * Per Documentation (EIS 1081 v 6.7 Section 4.24.4)
	 * "For non-US ZIP Codes only numerics can be sent. if the
	 * ZIP Code contains non-numeric data, the ZIP code should
	 * be zero filled.
	 */
 	public String getZip(CreditCard creditCard){
	 	String zip = creditCard.getZip();
		StringBuffer buffer = new StringBuffer();
	 	if(zip != null && !zip.trim().equals("")){
		 	 //buffer.append(' ');
		 	for(int i=0; i < zip.length(); i++){
		 		char ch = zip.charAt(i);
		  if(!Character.isDigit(ch))
					return "00000";
			 	buffer.append(ch);
			}
		}
 		return buffer.toString();
	}
	 
	public void setCustomerDataField(String creditcardno, String expDate) {
 		if (logger.isDebugEnabled()) {
			logger.debug("setCustomerDataField(string=" + creditcardno + ", expDate="
					+ expDate + ")");
		}
		char ch = creditcardno.charAt(0); 
		switch (ch) {
		case '%':
			this.accountDataSource = ACCOUNT_DATA_SOURCE_TRACK_1_READ;
			// NOTE: The Reversal Constructor will set this value
			// to be TRANSACTION_CODE_REVERSAL_PRE_SETTLEMENT
			
//			 if (!authorization.getAuthType().equals(REVERSAL)) {
				
				this.transactionCode = TRANSACTION_CODE_CARD_PRESENT;
		//	 }
			this.customerDataField = DataFormattingUtil.getTrack1(creditcardno);
			break;
		case ';':
		 	this.accountDataSource = ACCOUNT_DATA_SOURCE_TRACK_2_READ;
			// NOTE: The Reversal Constructor will set this value
			// to be TRANSACTION_CODE_REVERSAL_PRE_SETTLEMENT
			//		 if (!authorization.getAuthType().equals(REVERSAL)) {
			//			this.transactionCode = TRANSACTION_CODE_CARD_PRESENT;
			//		}
					this.customerDataField = DataFormattingUtil.getTrack2(creditcardno);
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
			
			//		if (! authorization.getAuthType().equals(REVERSAL)) {
			//			this.transactionCode = TRANSACTION_CODE_CARD_NOT_PRESENT;
			//		}
		this.transactionCode = TRANSACTION_CODE_CARD_NOT_PRESENT;
 			// For a manually keyed entry, we need send a 'P' for the requested
			// ACI.
			// However, the Incremental constructor will set this to 'I'. We
			// don't
			// want to overwrite this.
			
			//		if (!authorization.getAuthType().equals(INCREMENT))  {
			//			this.requestedACI = REQUESTED_ACI_CPS_CAPABLE_PREFERRED_CUSTOMER_AUTHORIZATION;
			//		}
		
			this.requestedACI = REQUESTED_ACI_CPS_CAPABLE_PREFERRED_CUSTOMER_AUTHORIZATION;
			
			this.customerDataField = creditcardno + FIELD_SEPARATOR + expDate
					+ FIELD_SEPARATOR;
			
			break;
		}
 	}
 
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}
	
	/**
	 * Get the next transaction sequence number for the hotel.
	 * 
	 * @param hotel The hotel
	 * @return the next batch number
	 * @throws SQLException if a JDBC error occurs
	 */
 	/*
 	 * AUTHORIZATION
 	 */	 
	public String getMarketSpecificData() {

		if (logger.isDebugEnabled()) {
			logger.debug("getMarketSpecificData()");
		}
 		StringBuffer buf = new StringBuffer();
 		buf.append(PRESTIGIOUS_PROPERTY_INDICATOR_NOT_PARTICIPATING);
		buf.append(MARKET_SPECIFIC_DATA_ID_HOTEL);
		buf.append(DataFormattingUtil.rightJustifyZero(String.valueOf(stayOfDuration), 2));
 		return buf.toString();
	}

	/*
	 * AUTHORIZATION, INCREMENTAL, REVERSAL
	 */ 
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
 
	/*
	 * AUTHORIZATION
	 */ 
	public String getCardHolderIdentificationData() {
		return addressVerificationData;
	}

	 /*
	  * INCREMENTAL AND REVERSAL
	  */ 
	public String getTransactionID() {
		// NO-OP FOR AUTHORIZATION
		return "";
	}

	 /*
	  * REVERSAL
	  */ 
	public String getReversalAndCancelDataI() {
		// NO-OP FOR AUTHORIZATION, INCREMENTAL
		return "";
	}

	/*
	 * REVERSAL
	 */ 
	public String getSecondaryAmountString() {
		// NO-OP FOR AUTHORIZATION, INCREMENTAL
		return "";
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();

		buf
				.append(" ***************************************************************************** \n");
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
		buf
				.append(" ***************************************************************************** \n");

		return buf.toString();
	}

	/**
	 * @return char
	 */
	public char getAccountDataSource() {
		return accountDataSource;
	}

	/**
	 * @return String
	 */
	public String getAddressVerificationData() {
		return addressVerificationData;
	}

	/**
	 * @return char
	 */
	public char getCardHolderID() {
		return cardHolderID;
	}

	/**
	 * @return String
	 */
	public String getCustomerDataField() {
		return customerDataField;
	}

	/**
	 * @return char
	 */
	public char getRequestedACI() {
		return requestedACI;
	}

	/**
	 * @return int
	 */
	public int getStayOfDuration() {
		return stayOfDuration;
	}

	/**
	 * @return String
	 */
	public String getTransactionAmount() {
		return transactionAmount;
	}

	/**
	 * @return String
	 */
	public String getTransactionCode() {
		return transactionCode;
	}

	/**
	 * @param accountDataSource
	 */
	public void setAccountDataSource(char accountDataSource) {
		this.accountDataSource = accountDataSource;
	}

	/**
	 * @param addressVerificationData
	 */
	public void setAddressVerificationData(String addressVerificationData) {
		this.addressVerificationData = addressVerificationData; 
	}

	/**
	 * @param cardHolderID
	 */
	public void setCardHolderID(char cardHolderID) {
		this.cardHolderID = cardHolderID;
	}
	
	/**
	 * @param cardHolderID
	 */
	public void setRequestedACI(char cardHolderID) {
		requestedACI = requestedACI;
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
		this.transactionAmount = DataFormattingUtil.formatAmount(amount); 
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

