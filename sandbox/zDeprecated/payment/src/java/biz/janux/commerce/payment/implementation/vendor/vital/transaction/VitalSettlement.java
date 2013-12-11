package biz.janux.commerce.payment.implementation.vendor.vital.transaction;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import org.apache.log4j.Logger;  
import biz.janux.commerce.payment.implementation.common.dao.hibernate.HibernateDao;
import biz.janux.commerce.payment.implementation.vendor.vital.helper.VitalHelper;
import biz.janux.commerce.payment.implementation.vendor.vital.transaction.VitalTransaction;
import biz.janux.commerce.payment.implementation.vendor.vital.transaction.FormatDetails.DetailType;
import biz.janux.commerce.payment.interfaces.transaction.Settlement;
import biz.janux.commerce.payment.model.CreditCard;
import biz.janux.commerce.payment.model.VitalAuthorizationResponseModel;
import biz.janux.commerce.payment.model.VitalMerchantAccount;
import biz.janux.commerce.payment.util.BatchUtil;
import biz.janux.commerce.payment.util.Constants;
import biz.janux.commerce.payment.util.CreditCardUtil;
import biz.janux.commerce.payment.util.DataFormattingUtil;

public class VitalSettlement implements VitalTransaction{
 	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VitalSettlement.class);

	Collection<FormatDetails> details = new ArrayList<FormatDetails>();

	protected VitalMerchantAccount merchant;
	
	CreditCard creditCard ;
	
	private Settlement settlement;
	
	private DetailType type;
	
	private int stayDuration;
	
	private BigDecimal averageRate;
	
	private String purchaseIdentifier;
	
	List list = new ArrayList();
	
	// Header Members

	// Section 4.22
	// Length 4, MMDD Format
	String batchTransmissionDate;

	// Section 4.18
	// Length 3, 001-999
	int batchNumber;

	// Parameter Records
	String merchantLocationNumber = Constants.MERCHANT_LOCATION_NUMBER;

	// Trailer Records
	// Length = 16 RIGHT JUSTIFY ZERO FILLED
	// Section 4.16
	BigDecimal batchHashingTotal = new BigDecimal(0);

	// Section 4.17
	// Length = 16 RIGHT JUSTIFY ZERO FILLED
	BigDecimal batchNetDeposit = new BigDecimal(0);
	
	public VitalSettlement(VitalMerchantAccount merchant) { 
		this.merchant = merchant;

		// Set Batch Transmission Date
		// NOTE: This is using GMT instead of local time for property
		this.batchTransmissionDate = new SimpleDateFormat("MMdd")
				.format(new java.util.Date());
	}
  
	public VitalSettlement(Settlement settlement , DetailType type , int stayDuration , BigDecimal averageRate , String purchaseIdentifier){
		this.batchTransmissionDate = new SimpleDateFormat("MMdd")
			.format(new java.util.Date());
		this.settlement = settlement;
		this.type=type;
		this.stayDuration= stayDuration;
		this.averageRate=averageRate;
		this.purchaseIdentifier=purchaseIdentifier;
	}
	
	public VitalSettlement(Settlement settlement , DetailType type){
		this(settlement , type , 0 , new BigDecimal(0.0) , "4");
	}
	
	public byte[] getVitalMessage(){
		
		this.batchNumber = BatchUtil.getBatchNumber(settlement.getMerchantId()); 
		setBatchNumber(BatchUtil.getBatchNumber(settlement.getMerchantId()));
		/**
 		  * Used Hibernate for loading DB
 		  * VitalMerchantAccount
 		  * CreditCard
 		  * 
 		  */
		HibernateDao hibernateDao = HibernateDao.getInstance();
		merchant= (VitalMerchantAccount)hibernateDao.loadfromDB(VitalMerchantAccount.class , settlement.getMerchantId());
		creditCard = (CreditCard)hibernateDao.loadfromDB(CreditCard.class , settlement.getInstrumentId());
		
		/**
		 * Called the constructor of FormatDetails and
		 * pass the authorization response as a parameter
		 * 
		 */
		merchant.setTimeZoneDifferential(merchant.getTimeZoneDifferential());
		Date date=(Date)getLocalDate(merchant , settlement.getVitalAuthorizationResponseModel().getSystemDate());
		getDetail(type , settlement.getVitalAuthorizationResponseModel() , stayDuration , averageRate , date , settlement.getSettlementAmount() , purchaseIdentifier);
		StringBuffer buffer = new StringBuffer();
		buffer.append(formatHeader());
		buffer.append(formatParameter());
		buffer.append(formatDetails());
		buffer.append(formatTrailer());
 		return buffer.toString().getBytes();
	}
	
	public String formatHeader() {
		if (logger.isDebugEnabled()) {
			logger.debug("formatHeader()");
		}
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(Constants.STX);
		buffer.append(Constants.RECORD_FORMAT_SETTLEMENT);
		buffer.append(Constants.APPLICATION_TYPE_SETTLEMENT);
		buffer.append(Constants.MESSAGE_DELIMITER);
		buffer.append(Constants.X25_ROUTING_ID);
		buffer.append(Constants.RECORD_TYPE_HEADER);
		buffer.append(merchant.getAcqBankIdentificationNumber());
		buffer.append(merchant.getAgentBankNumber());
		buffer.append(merchant.getChainNumber());
		buffer.append(merchant.getNumber());
		buffer.append(merchant.getStoreNumber());
		buffer.append(merchant.getTerminalNumber());
		buffer.append(Constants.DEVICE_CODE);
		buffer.append(Constants.INDUSTRY_CODE);
		buffer.append(merchant.getCurrencyCode());
		buffer.append(Constants.LANGUAGE_INDICATOR_ENGLISH);
		buffer.append(merchant.getTimeZoneDifferential());
		buffer.append(batchTransmissionDate);
		// Keep track of batchNumber
		buffer.append(VitalHelper.rightJustifyZero(String.valueOf(batchNumber), 3));
		// ?
		buffer.append(Constants.BLOCKING_INDICATOR_NON_BLOCKING);
		buffer.append(merchant.getLocalPhoneNumber());
		buffer.append(merchant.getCardholderServiceNumber());
		buffer.append(Constants.ETB);

		buffer.append((char) DataFormattingUtil.calcLRC(buffer.toString().getBytes()));
		return buffer.toString();
	}

	public String formatParameter() {
		if (logger.isDebugEnabled()) {
			logger.debug("formatParameter()");
		}

		StringBuffer buffer = new StringBuffer();

		buffer.append(Constants.STX);
		buffer.append(Constants.RECORD_FORMAT_SETTLEMENT);
		buffer.append(Constants.APPLICATION_TYPE_SETTLEMENT);
		buffer.append(Constants.MESSAGE_DELIMITER);
		buffer.append(Constants.X25_ROUTING_ID);
		buffer.append(Constants.RECORD_TYPE_PARAMETER);
		buffer.append(merchant.getCountry().getIso3numeric());
		buffer.append(merchant.getCityCode());
		buffer.append(Constants.MERCHANT_CATEGORY_CODE_HOTEL);
		buffer.append(merchant.getName());
		buffer.append(merchant.getCity());
		buffer.append(merchant.getState().getCode());
		buffer.append(merchantLocationNumber);
		buffer.append(merchant.getTerminalID());
		buffer.append(Constants.ETB);
		buffer.append((char) DataFormattingUtil.calcLRC(buffer.toString().getBytes()));
		
		return buffer.toString();
	}

	public String formatDetails() {
		if (logger.isDebugEnabled()) {
			logger.debug("formatDetails()");
		}
		StringBuffer buffer = new StringBuffer();
		Iterator<FormatDetails> iterator = details.iterator();
		
		while (iterator.hasNext()) {
			FormatDetails detail = iterator.next();			
			// Add absolute transaction amount to batchHashingTotal
			// The actual value will only be adjusted by a CreditDetail (which
			// will always return its .abs() value)
			batchHashingTotal = batchHashingTotal.add(detail
					.getSettlementAmount());
		
			// Add transaction amount to batchNetDeposit
			batchNetDeposit = batchNetDeposit.add(detail
					.getSettlementAmountUnAltered());
			buffer.append(detail.format());
		}
		// Note, the batchNetDeposit is calculated by Purchases - Returns
		// If a batch is all credits, this could be a negative number
		// the actual value must be the absolute value of Purchases - Returns
		batchNetDeposit = batchNetDeposit.abs();
		
		return buffer.toString();
	}

	public String formatTrailer() {
		if (logger.isDebugEnabled()) {
			logger.debug("formatTrailer()");
		}

		StringBuffer buffer = new StringBuffer();

		buffer.append(Constants.STX);
		buffer.append(Constants.RECORD_FORMAT_SETTLEMENT);
		buffer.append(Constants.APPLICATION_TYPE_SETTLEMENT);
		buffer.append(Constants.MESSAGE_DELIMITER);
		buffer.append(Constants.X25_ROUTING_ID);
		buffer.append(Constants.RECORD_TYPE_TRAILER);
		buffer.append(batchTransmissionDate);
		buffer.append(VitalHelper.rightJustifyZero(String.valueOf(batchNumber), 3));
		buffer.append(VitalHelper.rightJustifyZero(String
				.valueOf(3 + details.size()), 9));
		buffer.append(VitalHelper.rightJustifyZero(DataFormattingUtil
				.formatAmount(batchHashingTotal), 16));
		// CASH BACK TOTAL is not used by hotel industry
		buffer.append(Constants.CASH_BACK_TOTAL);
		buffer.append(VitalHelper.rightJustifyZero(DataFormattingUtil
				.formatAmount(batchNetDeposit), 16));
		buffer.append(Constants.ETX);

		buffer.append((char) DataFormattingUtil.calcLRC(buffer.toString().getBytes()));

		return buffer.toString();
	}

	/**
	 * @return
	 */
	public String getMerchantLocationNumber() {
		return merchantLocationNumber;
	}

	/**
	 * @param string
	 */
	public void setMerchantLocationNumber(String string) {
		merchantLocationNumber = string;
	}
	public void addDetail(FormatDetails detail) {
		details.add(detail);
	}

	 	
 	/**
	 * @return String
	 */
	public int getBatchNumber() {
		return batchNumber;
	}

	/**
	 * @param string
	 */
	public void setBatchNumber(int batchNumber) {
		this.batchNumber = batchNumber;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getBatchHashingTotal() {
		return batchHashingTotal;
	}
	/**
	 * @return
	 */
	public BigDecimal getBatchNetDeposit() {
		return batchNetDeposit;
	}
	/**
	 * Method to get local date
	 * @param vma
	 * @param date
	 * @return
	 */
	public static java.sql.Date getLocalDate(VitalMerchantAccount vma, java.sql.Date date) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("getLocalDate(hotel=" + vma + ", date=" + date
							+ ")");
		}
		vma.setTimezone(TimeZone.getTimeZone(vma.getMerchenttimezone()));

		return DataFormattingUtil.getLocalDate(vma.getTimezone(), date);
	}
	/**
	 * Method to get Detail of format Detail and 
	 * checks Credit Card Type whether it is AMERICAN_EXPRESS or NON-AMERICAN_EXPRESS  
	 * @param type
	 * @param response
	 * @param stayDuration
	 * @param averageRate
	 * @param checkOutDate
	 * @param settlementAmount
	 * @param purchaseIdentifier
	 */
	public void getDetail(DetailType type , VitalAuthorizationResponseModel response , int stayDuration , BigDecimal averageRate , Date checkOutDate , BigDecimal settlementAmount , String purchaseIdentifier){
		String cardNumber=creditCard.getCardNumber();
		if(type == DetailType.PURCHASE)
		{
			logger.info("preparing authId = " + response.getId() + " for $" + settlementAmount);
			// NOTE CHECK IN DATE == AUTHORIZATION/TRANSACTION DATE 
			Date checkInDate = new Date(response.getLocalTransactionDateTime().getTime());				
			FormatDetails detail;
			if(creditCard.getCreditCardType().getCreditCardTypeId()== CreditCardUtil.AMERICAN_EXPRESS)
			{															
				detail = new AmexDetail(response , checkOutDate , stayDuration , averageRate , type);
			}
			else
			{
				detail = new FormatDetails(response , type);
			}
			
			// Set the amount that is going to be settled
			detail.setSettlementAmount(settlementAmount);
			
			// Include original authorization and total authorization amount,
			// but only if the authorization originated on-line.				
			if(!response.isOffline())
			{
				
				detail.setAuthorizedAmount(response.getOriginalAuthorizationAmount());
				detail.setTotalAuthorizedAmount(response.getAuthorizationAmount());
			}
							
			// Swipe Read
			// If we have swipe data, set field in Authorization object
			if (creditCard.isSwiped())
			{	  
				try {
					detail.setCardHolderAccountNumber(creditCard.getTrack2() , creditCard.decrypt(cardNumber));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}
			else
			{	
				//detail.setCardHolderAccountNumber(";4111111111111111=0601101352010678?", creditCard.getNumber());
				try {
					detail.setCardHolderAccountNumber(creditCard.decrypt(cardNumber) , creditCard.decrypt(cardNumber));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			// TODO: NOSHOW/DEPOSIT/ETC LOGIC
			detail.setCheckInDate(checkInDate);
			detail.setCheckOutDate(checkOutDate);
			detail.setFolioNumber(purchaseIdentifier);
			
			addDetail(detail);

			// Queue up for setting batched flags
			// NOTE: CREDITS DON'T HAVE AUTHID, WILL NEED TO USE creditCardSettlementId
			// or something
			// HOWEVER, 
			list.add(new Integer((int) response.getId()));
		}
		else
		{
			FormatDetails detail;
			if(creditCard.getCreditCardType().getCreditCardTypeId() == CreditCardUtil.AMERICAN_EXPRESS)
			{
				// TODO: NOSHOW/DEPOSIT/ETC LOGIC
				// NOTE CONSTANT DURATION OF 1 FOR CREDITS...
				detail = new AmexDetail(response , checkOutDate , 1 , averageRate , type);
			}
			else
			{
				detail = new FormatDetails(response , type);
			}
			
			// A Credit requires a transaction sequence number
			detail.setTransactionSequenceNumber(String.valueOf(BatchUtil.transactionSequenceNumber(response.getMerchantId())));
			
			// A credit requires us to specify a local transaction time
			// This should be when the transaction occurred (as opposed to
			// right now when it is being sent in the batch).
			// We get this from the timestamp (should be localized already)
			// in the folioLine for this transaction. Otherwise, we send
			// the current hotel time if we can't look up the folioLine for some
			// reason
			//FolioLine folioLine = new FolioLine();
			//folioLine.load(folioLineId);
			
			//			Timestamp transactionTimeStamp = folioLine.getTimestamp();
			//if(transactionTimeStamp == null)
			//{
	    	//	transactionTimeStamp = NewMyHMS.getLocalTimestamp(hotel);
			//}
			Timestamp transactionTimeStamp = response.getLocalTransactionDateTime();
			detail.setLocalTransactionTime(DataFormattingUtil.formatTimestampToHHMMSS(transactionTimeStamp));				
			
			// absolute value -- but what about hashing totals.
			detail.setSettlementAmount(settlementAmount);
			
			// Swipe Read
			// If we have swipe data, set field in Authorization object
			if (creditCard.isSwiped())
			{	    	
				try {
					detail.setCardHolderAccountNumber(creditCard.getTrack2() , creditCard.decrypt(cardNumber));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}
			else
			{		
				//detail.setCardHolderAccountNumber( ";4111111111111111=0601101352010678?", creditCard.getNumber());
				try{
					detail.setCardHolderAccountNumber(creditCard.decrypt(cardNumber) , creditCard.decrypt(cardNumber));
				}catch (Exception e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
			
			// TODO: Noshow, Advanced deposit.
			// NOTE: BECAUSE THIS IS A CREDIT, WE SEND CHECK OUT DATE
			// AS THE CHECK IN DATE
			detail.setCheckInDate(checkOutDate);
			detail.setCheckOutDate(checkOutDate);
			detail.setFolioNumber(purchaseIdentifier);
			
			addDetail(detail);
		}
	}
}
  
