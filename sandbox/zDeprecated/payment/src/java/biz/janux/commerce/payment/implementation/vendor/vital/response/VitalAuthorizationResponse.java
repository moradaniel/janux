package biz.janux.commerce.payment.implementation.vendor.vital.response;

import java.io.PrintStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import org.apache.log4j.Logger;
  
import biz.janux.commerce.payment.implementation.vendor.vital.helper.VitalHelper;
import biz.janux.commerce.payment.interfaces.response.AuthorizationResponse;
/**
 * 
 * @author Nilesh
 * </br>
 * Implements AuthorizationResponse and Serializable
 * </br>
 * This class is used to generate the response of Authorization
 *
 */
public class VitalAuthorizationResponse implements AuthorizationResponse , Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(VitalAuthorizationResponse.class);
 	
	/*
	 * protected member variables
	 */ 
	protected String requestedACI;
	protected String returnedACI;
	protected String storeNumber;
	protected String terminalNumber;
	protected String authSourceCode;
	protected String transactionSequenceNumber;
	protected String localTransDate;
	protected String localTransTime;
	protected String transactionIdentifier;
	protected String responseCode;
	protected String avsResultCode;
	protected String approvalCode;
	public String authResponseText;
	protected String retrievalReferenceNumber;
	protected String validationCode;
	protected byte[] originalBytes;
	protected boolean reversal;
	protected boolean offline;
	protected long folioId;
	private long Id;
	private BigDecimal AuthorizationAmount;
	protected VitalAddressVerificationResponse AVSResponse;
	protected BigDecimal originalAuthorizationAmount;
	protected Timestamp localTransactionDateTime;
	protected int arAccountId;
	protected Date systemDate;
	protected long InstrumentId;
	protected long merchentId;
	
	public VitalAuthorizationResponse(){}
	
  	public VitalAuthorizationResponse(byte[] internalResponse){
	 
		if (logger.isDebugEnabled()) {
			logger.debug("AuthorizationResponse(internalResponse=" + internalResponse + ")");
		}
		// temporary, as to not mess up future processing
		byte[] bytes = new byte[internalResponse.length];
		originalBytes = new byte[internalResponse.length];
		System.arraycopy(internalResponse, 0, bytes, 0, internalResponse.length);
		System.arraycopy(internalResponse, 0, originalBytes, 0, internalResponse.length);
		VitalHelper.fixBytes(bytes);
		returnedACI = new String(bytes, 4, 1);
		storeNumber = new String(bytes, 5, 4);
		terminalNumber = new String(bytes, 9, 4);
		authSourceCode = new String(bytes, 13, 1);
		transactionSequenceNumber = new String(bytes, 14, 4);
		localTransDate = new String(bytes, 26, 6);
		localTransTime = new String(bytes, 32, 6);
		responseCode = new String(bytes, 18, 2);
		avsResultCode = new String(bytes, 54, 1);
		approvalCode = new String(bytes, 20, 6).trim();
		authResponseText = new String(bytes, 38, 16).trim();
		retrievalReferenceNumber = new String(bytes, 55, 12);
		transactionIdentifier = "";
		validationCode = "";
		int index = 68;
		if (bytes.length >= 68 && bytes[68] != 0x1C) {
			do {
				transactionIdentifier += (char) bytes[index++];
			} while (index < bytes.length && bytes[index] != 0x1C);
		}

		// Needed to increment index, otherwise the body is never entered
		// (should always be pointing at previous terminating 0x1C byte)
		if (bytes.length >= ++index && bytes[index] != 0x1C) {
			do {
				validationCode += (char) bytes[index++];
			} while (index < bytes.length && bytes[index] != 0x1C);
		}
		
		// parse internal response so that following methods give valid output
		//vitalResponse = internalResponse;
		
		System.out.println(toString());
	}
  	/**
	 * @return String
	 */
	public String getApprovalCode() {
		return VitalHelper.leftJustifySpace(approvalCode, 6);
	}

	/**
	 * @param approvalCode
	 */
	public void setApprovalCode(String approvalCode) {
		this.approvalCode = approvalCode;
	}

	/**
	 * @return String
	 */
	public String getAuthResponseText() {
		return authResponseText;
	}

	/**
	 * @param authResponseText
	 */
	public void setAuthResponseText(String authResponseText) {
		this.authResponseText = authResponseText;
	}

	/**
	 * @return String
	 */
	public String getAuthSourceCode() {
		return authSourceCode;
	}

	/**
	 * @param authSourceCode
	 */
	public void setAuthSourceCode(String authSourceCode) {
		this.authSourceCode = authSourceCode;
	}

	/**
	 * @return String
	 */
	public VitalAddressVerificationResponse getAVSResponse() {
		return AVSResponse;
	}

	/**
	 * @param response
	 */
	public void setAVSResponse(VitalAddressVerificationResponse response) {
		AVSResponse = response;
	}

	/**
	 * @return String
	 */
	public String getAvsResultCode() {
		return avsResultCode;
	}

	/**
	 * @param avsResultCode
	 */
	public void setAvsResultCode(String avsResultCode) {
		this.avsResultCode = avsResultCode;
	}

	/**
	 * @return String
	 */
	public String getLocalTransDate() {
		return localTransDate;
	}

	/**
	 * @param localTransDate
	 */
	public void setLocalTransDate(String localTransDate) {
		this.localTransDate = localTransDate;
	}

	/**
	 * @return String
	 */
	public String getLocalTransTime() {
		return localTransTime;
	}

	/**
	 * @param localTransTime
	 */
	public void setLocalTransTime(String localTransTime) {
		this.localTransTime = localTransTime;
	}

	/**
	 * @return boolean
	 */
	public boolean isOffline() {
		return offline;
	}

	/**
	 * @param offline
	 */
	public void setOffline(boolean offline) {
		this.offline = offline;
	}

	/**
	 * @return  byte
	 */
	public byte[] getOriginalBytes() {
		return originalBytes;
	}

	/**
	 * @param originalBytes
	 */
	public void setOriginalBytes(byte[] originalBytes) {
		this.originalBytes = originalBytes;
	}

	/**
	 * @return String
	 */
	public String getRequestedACI() {
		return requestedACI;
	}

	/**
	 * @param requestedACI
	 */
	public void setRequestedACI(String requestedACI) {
		this.requestedACI = requestedACI;
	}

	/**
	 * @return String
	 */
	public String getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode
	 */
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * @return String
	 */
	public String getRetrievalReferenceNumber() {
		return retrievalReferenceNumber;
	}

	/**
	 * @param retrievalReferenceNumber
	 */
	public void setRetrievalReferenceNumber(String retrievalReferenceNumber) {
		this.retrievalReferenceNumber = retrievalReferenceNumber;
	}

	/**
	 * @return String
	 */
	public String getReturnedACI() {
		return returnedACI;
	}

	/**
	 * @param returnedACI
	 */
	public void setReturnedACI(String returnedACI) {
		this.returnedACI = returnedACI;
	}

	/**
	 * @return String
	 */
	public boolean isReversal() {
		return reversal;
	}

	/**
	 * @param reversal
	 */
	public void setReversal(boolean reversal) {
		this.reversal = reversal;
	}

	/**
	 * @return String
	 */
	public String getStoreNumber() {
		return storeNumber;
	}

	/**
	 * @param storeNumber
	 */
	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

	/**
	 * @return String
	 */
	public String getTerminalNumber() {
		return terminalNumber;
	}

	/**
	 * @param terminalNumber
	 */
	public void setTerminalNumber(String terminalNumber) {
		this.terminalNumber = terminalNumber;
	}

	/**
	 * @return String
	 */
	public String getTransactionIdentifier() {
		if (logger.isDebugEnabled()) {
			logger.debug("getTransactionIdentifier()");
		}

		if (transactionIdentifier == null || transactionIdentifier.equals("")) {
			return "000000000000000";
		} else {
			return VitalHelper.leftJustifySpace(transactionIdentifier, 15);
		}
	}
 	/**
	 * @param transactionIdentifier
	 */
	public void setTransactionIdentifier(String transactionIdentifier) {
		this.transactionIdentifier = transactionIdentifier;
	}

	/**
	 * @return String
	 */
	public String getTransactionSequenceNumber() {
		return VitalHelper.rightJustifyZero(transactionSequenceNumber, 4);
	}

	/**
	 * @param transactionSequenceNumber
	 */
	public void setTransactionSequenceNumber(String transactionSequenceNumber) {
		this.transactionSequenceNumber = transactionSequenceNumber;
	}

	/**
	 * @return String
	 */
	public String getValidationCode() {
		return VitalHelper.leftJustifySpace(validationCode, 4); 
	}

	/**
	 * @param validationCode
	 */
	public void setValidationCode(String validationCode) {
		this.validationCode = validationCode;
	}

	/**
	 * @return boolean
	 */
	public boolean isApproved() {
		if (logger.isDebugEnabled()) {
			logger.debug("isApproved()");
		}
		
		return responseCode.equals(APPROVAL) || responseCode.equals(CARD_OK);
	}

	/**
	 * @return boolean
	 */
	public boolean isDeclined() {
		if (logger.isDebugEnabled()) {
			logger.debug("isDeclined()");
		}
 		return responseCode.equals(DECLINE)
				|| responseCode.equals(DECLINE_INSUFFICIENT_FUNDS)
				|| responseCode.equals(DECLINE_EXCEEDS_ISSUER_WITHDRAWAL_LIMIT)
				|| responseCode.equals(DECLINE_EXCEEDS_WITHDRAWAL_LIMIT)
				|| responseCode.equals(DECLINE_INVALID_SERVICE_CODE)
				|| responseCode.equals(DECLINE_ACTIVITY_LIMIT_EXCEEDED)
				|| responseCode.equals(DECLINE_VIOLATION);
	}

	/**
	 * @return byte
	 */
	public byte[] getBytes() {
		return originalBytes;
	}

	/**
	 * @return String
	 */
	public String getDetailedResponseDefinition() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDetailedResponseDefinition()");
		}
		
		return getDetailedResponseDefinition(responseCode);
 	}
	
	/**
	 * @return String
	 */
	public String getDetailedResponseDefinition(String responseCode) {
		if (responseCode.equals(APPROVAL)) {
			return "Approved and completed";
		} else if (responseCode.equals(CARD_OK)) {
			return "No reason to decline";
		} else if (responseCode.equals(CALL_REFER_TO_ISSUER)) {
			return "Refer to issuer";
		} else if (responseCode.equals(CALL_REFER_TO_ISSUER_SPECIAL_CONDITION)) {
			return "Refer to issuer - Special condition";
		} else if (responseCode.equals(PICK_UP_CARD)) {
			return "Pick up card";
		} else if (responseCode.equals(PICK_UP_CARD_SPECIAL_CONDITION)) {
			return "Pick up card - Special condition";
		} else if (responseCode.equals(PICK_UP_CARD_LOST)) {
			return "Pick up card - Lost";
		} else if (responseCode.equals(PICK_UP_CARD_STOLEN)) {
			return "Pick up card - Stolen";
		} else if (responseCode.equals(ALREADY_REVERSED)) {
			return "Already reversed at switch";
		} else if (responseCode.equals(AMOUNT_ERROR)) {
			return "Invalid amount";
		} else if (responseCode.equals(CARD_NO_ERROR)) {
			return "Invalid card number";
		} else if (responseCode.equals(DECLINE)) {
			return "Do not honor";
		} else if (responseCode.equals(DECLINE_INSUFFICIENT_FUNDS)) {
			return "Insufficient funds";
		} else if (responseCode.equals(DECLINE_EXCEEDS_ISSUER_WITHDRAWAL_LIMIT)) {
			return "Exceeds issuer withdrawl limit";
		} else if (responseCode.equals(DECLINE_EXCEEDS_WITHDRAWAL_LIMIT)) {
			return "Exceeds withdrawl limit";
		} else if (responseCode.equals(DECLINE_INVALID_SERVICE_CODE)) {
			return "Invalid service code, restricted";
		} else if (responseCode.equals(DECLINE_ACTIVITY_LIMIT_EXCEEDED)) {
			return "Activity limit exceeded";
		} else if (responseCode.equals(DECLINE_VIOLATION)) {
			return "Violation, cannot complete";
		} else if (responseCode.equals(EXPIRED_CARD)) {
			return "Expired card";
		} else if (responseCode.equals(INVALID_ROUTING)) {
			return "Destination not found";
		} else if (responseCode.equals(INVALID_TRANSACTION)) {
			return "Invalid transaction";
		} else if (responseCode.equals(NO_ACCOUNT)) {
			return "No account";
		} else if (responseCode.equals(NO_SUCH_ISSUER)) {
			return "No such issuer";
		} else if (responseCode.equals(RE_ENTER)) {
			return "Re-enter transaction";
		} else if (responseCode.equals(SECURITY_VIOLATION)) {
			return "Security violation";
		} else if (responseCode.equals(SERV_NOT_ALLOWED_CARD)) {
			return "Transaction not permitted-Card";
		} else if (responseCode.equals(SERV_NOT_ALLOWED_TERMINAL)) {
			return "Transaction not permitted-Terminal";
		} else if (responseCode.equals(SYSTEM_MALFUNCTION)) {
			return "System malfunction";
		} else if (responseCode.equals(TERM_ID_ERROR_INVALID_MERCHANT_ID)) {
			return "Invalid Merchant ID";
		} else if (responseCode.equals(DUPLICATE_TRANSACTION)) {
			return "Unable to locate, no match";
		} else {
			return "UNKNOWN RESPONSE, CODE: " + responseCode;
		}
	}
	
	public void print(PrintStream out) {
		
		out.println(" ***************************************************************************** ");
		out.println("Returned ACI : \"" + returnedACI + "\"");
		out.println("Store Number : \"" + storeNumber + "\"");
		out.println("Terminal Numb: \"" + terminalNumber + "\"");
		out.println("Auth Src Code: \"" + authSourceCode + "\"");
		out.println("Trans SEQ Num: \"" + transactionSequenceNumber + "\"");
		out.println("Loc Tran Date: \"" + localTransDate + "\"");
		out.println("Loc Tran Time: \"" + localTransTime + "\"");

		out.println("Response Code: \"" + responseCode + "\"");
		out.println("AVS Response : \"" + avsResultCode + "\"");
		out.println("Approval Code: \"" + approvalCode + "\"");
		out.println("Auth Resp Txt: \"" + authResponseText + "\"");
		out.println("Retr Ref Numb: \"" + retrievalReferenceNumber + "\"");
		out.println("Response Def : \"" + getDetailedResponseDefinition() + "\"");
		out.println("Trans Ident  : \"" + transactionIdentifier + "\"");
		out.println("Validation CD: \"" + validationCode + "\"");
		out.println("Approved?    : " + isApproved());
		out.println("Declined?    : " + isDeclined());
		out.println(" ****************************************************************************** ");
	}
	public String toString() {
		
		StringBuffer bufr = new StringBuffer();
		bufr.append(" ***************************************************************************** \n");
		bufr.append("Returned ACI: \""	+ returnedACI + "\"\n");
		bufr.append("Store Number: \"" + storeNumber + "\"\n");
		bufr.append(" Terminal Numb: \"" +  terminalNumber + "\"\n");
		bufr.append(" Auth Src Code: \"" + authSourceCode + "\"\n");
		bufr.append("Trans SEQ Num: \"" + transactionSequenceNumber	+ "\"\n");
		bufr.append("Loc Tran Date: \""	+ localTransDate + "\"\n");
		bufr.append("Loc Tran Time: \"" + localTransTime + "\"\n");
		bufr.append("Response Code: \"" + responseCode  + "\"\n");
		bufr.append("AVS Response : \"" + avsResultCode + "\"\n");
		bufr.append("Approval Code: \"" + approvalCode + "\"\n");
		bufr.append("Auth Resp Txt: \"" + authResponseText + "\"\n");
		bufr.append("Retr Ref Numb: \"" + retrievalReferenceNumber + "\"\n");
		bufr.append("Response Def : \"" + getDetailedResponseDefinition() + "\"\n");
		bufr.append("Trans Ident  : \"" + transactionIdentifier + "\"\n");
		bufr.append("Validation CD: \"" + validationCode + "\"\n");
		bufr.append("Approved?    :\"" + isApproved() + "\"\n");
		bufr.append("Declined?    : \"" + isDeclined() + "\"\n");
		bufr.append(" ***************************************************************************** \n");

		return bufr.toString();
		
	}
	
	/**
	 * @return String
	 */
	public String getErrorCode() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return String
	 */
	public String getErrorData() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return String
	 */
	public String getStatus() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * @return long
	 */
	public long getFolioId() {
		return folioId;
	}
	/**
	 * Set FolioId
	 */
	public void setFolioId(long i) {
		folioId = i;
	}
	/**
	 * @return BigDecimal
	 */
	public BigDecimal getAuthorizationAmount() {
		return AuthorizationAmount;
	}
	/**
	 * @param authorizationAmount
	 */
	public void setAuthorizationAmount(BigDecimal authorizationAmount) {
		this.AuthorizationAmount=authorizationAmount;
		
	}
	/**
	 * @return long
	 */
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		this.Id=id;
		
	}
	/**
	 * @return BigDecimal
	 */
	public BigDecimal getOriginalAuthorizationAmount() {
		return originalAuthorizationAmount;
	}
	public void setOriginalAuthorizationAmount(
			BigDecimal originalAuthorizationAmount) {
		this.originalAuthorizationAmount = originalAuthorizationAmount;
	}
	/**
	 * @return Timestamp
	 */
	public Timestamp getLocalTransactionDateTime() {
		return localTransactionDateTime;
	}
	public void setLocalTransactionDateTime(Timestamp localTransactionDateTime) {
		this.localTransactionDateTime = localTransactionDateTime;
	}
	/**
	 * @return int
	 */
	public int getArAccountId() {
		return arAccountId;
	}
	public void setArAccountId(int arAccountId) {
		this.arAccountId = arAccountId;
	}
	/**
	 * @return java.sql.Date
	 */
	public Date getSystemDate() {
		return systemDate;
	}
	public void setSystemDate(Date systemDate) {
		this.systemDate = systemDate;
	}
	/**
	 * @return long
	 */
	public long getInstrumentId() {
		return InstrumentId;
	}
	public void setInstrumentId(long instrumentId) {
		InstrumentId = instrumentId;
	}
	/**
	 * @return long
	 */
	public long getMerchentId() {
		return merchentId;
	}
	/**
	 * 
	 * @param merchentId
	 */
	public void setMerchentId(long merchentId) {
		this.merchentId = merchentId;
	}

}
