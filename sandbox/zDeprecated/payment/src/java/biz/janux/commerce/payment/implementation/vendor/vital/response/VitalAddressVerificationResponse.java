package biz.janux.commerce.payment.implementation.vendor.vital.response;

import org.apache.log4j.Logger;
 
import biz.janux.commerce.payment.implementation.vendor.vital.helper.VitalHelper;
import biz.janux.commerce.payment.interfaces.response.AddressVerificationResponse;
import biz.janux.commerce.payment.interfaces.response.codes.AddressVerificationResponseCodes;

/**
 * @author Nilesh
 * @implements AddressVerificationResponse,AddressVerificationResponseCodes
 * This class is used to generate response for Address Verification  
 */
public class VitalAddressVerificationResponse implements
		AddressVerificationResponse , AddressVerificationResponseCodes {
	
	private static final long serialVersionUID = 9179642326924160594L;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(VitalAddressVerificationResponse.class);
  	
	/**
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
	protected String respCode;
	public String avsResultCode;
	protected String approvalCode;
	protected String authResponseText;
	protected String retrievalReferenceNumber;
	protected String validationCode;
	protected byte[] originalBytes;
	protected boolean reversal;
	protected boolean offline;
	public byte[] vitalResponse;
	char responseCode;
	
	public VitalAddressVerificationResponse(){}
	 
	public VitalAddressVerificationResponse(byte[] internalResponse){
		/**
		 * parse internal response so that following methods give valid output
		 */ 
		this.vitalResponse =internalResponse;
		if (logger.isDebugEnabled()) {
			logger.debug("AuthorizationResponse(internalResponse=" + internalResponse + ")");
		}
		/**
		 * temporary, as to not mess up future processing
		 */ 
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
		avsResultCode = new String(bytes, 54, 1);
		approvalCode = new String(bytes, 20, 6).trim();
		setApprovalCode(approvalCode);
		authResponseText = new String(bytes, 38, 16).trim();
		retrievalReferenceNumber = new String(bytes, 55, 12);
 		transactionIdentifier = "";
		validationCode = "";
		int index = 68;

		if (bytes.length >= 68 && bytes[68] != 0x1C) {
			do {
				transactionIdentifier += (char) bytes[index++];
			   }
			while (index < bytes.length && bytes[index] != 0x1C);
		}
		/**
		 * Needed to increment index, otherwise the body is never entered
		 * (should always be pointing at previous terminating 0x1C byte)
		 */
		 
		if (bytes.length >= ++index && bytes[index] != 0x1C) {
			do {
				validationCode += (char) bytes[index++];
			} while (index < bytes.length && bytes[index] != 0x1C);
		}
		if (avsResultCode != null && avsResultCode.length() == 1) {
			this.responseCode = avsResultCode.charAt(0);
		}
 	}
	
	/**
	 *@param avsResultCode
	 */
	
	public String addressVerificationResponse() {
		String responseCode=avsResultCode;
		if (responseCode != null && responseCode.length() == 1) {
			this.responseCode = responseCode.charAt(0);
		}
		return responseCode;
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
 	 * (non-Javadoc)
 	 * @see biz.janux.commerce.payment.interfaces.response.AddressVerificationResponse#isAddressMatch()
 	 */
	public boolean isAddressMatch() {
		if (logger.isDebugEnabled()) {
			logger.debug("isAddressMatch()");
		}
 		return responseCode == AVS_ADDRESS_MATCH
				|| responseCode == AVS_ADDRESS_MATCH_INTERNATIONAL;
 	}
	/**
	 * (non-Javadoc)
	 * @see biz.janux.commerce.payment.interfaces.response.AddressVerificationResponse#isExactMatch()
	 */
	  public boolean isExactMatch() {
 		if (logger.isDebugEnabled()) {
			logger.debug("isExactMatch()");
		}
 	 return responseCode == AVS_EXACT_MATCH_5_CHAR_ZIP
				|| responseCode == AVS_EXACT_MATCH_9_CHAR_ZIP
				|| responseCode == AVS_EXACT_MATCH_INTERNATIONAL_1
				|| responseCode == AVS_EXACT_MATCH_INTERNATIONAL_2;
	 }
	/**
	 * (non-Javadoc)
	 * @see biz.janux.commerce.payment.interfaces.response.AddressVerificationResponse#isNoMatch()
	 */
	 public boolean isNoMatch() {
 		if (logger.isDebugEnabled()) {
			logger.debug("isNoMatch()");
		}
	  return responseCode == AVS_NO_MATCH;
	}
	/**
	 * (non-Javadoc)
	 * @see biz.janux.commerce.payment.interfaces.response.AddressVerificationResponse#isUnavailable()
	 */
	public boolean isUnavailable() {
		if (logger.isDebugEnabled()) {
			logger.debug("isUnavailable()");
		}
 		return responseCode == AVS_VER_UNAVAILABLE_ADDRESS_UNAVAILABLE
				|| responseCode == AVS_VER_UNAVAILABLE_NON_US_ISSUER_DOES_NOT_PARTICIPATE
				|| responseCode == AVS_RETRY
				|| responseCode == AVS_ERROR_INELIGIBLE
				|| responseCode == AVS_SERV_UNAVAILABLE_SERVICE_NOT_SUPPORTED
				|| responseCode == AVS_SERV_UNAVAILABLE_INTERNATIONAL
				|| responseCode == AVS_VER_UNAVAILABLE_INTERNATIONAL;
	 	}
	/**
	 * (non-Javadoc)
	 * @see biz.janux.commerce.payment.interfaces.response.AddressVerificationResponse#isZipMatch()
	 */
	public boolean isZipMatch() {

		if (logger.isDebugEnabled()) {
			logger.debug("isZipMatch()");
		}

		return responseCode == AVS_ZIP_MATCH_5_CHAR_ZIP
				|| responseCode == AVS_ZIP_MATCH_9_CHAR_ZIP
				|| responseCode == AVS_ZIP_MATCH_INTERNATIONAL;
	}
	/**
	 * (non-Javadoc)
	 * @see biz.janux.commerce.payment.interfaces.response.TransactionResponse#getBytes()
	 */
	public byte[] getBytes() {
 		return vitalResponse;
	}

	/**
	 * (non-Javadoc)
	 * @see biz.janux.commerce.payment.interfaces.response.TransactionResponse#getDetailedResponseDefinition()
	 */
	public String getDetailedResponseDefinition() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDetailedResponseDefinition()");
		}
    	if (respCode.equals(APPROVAL)) {
			return "Approved and completed";
	   } else if (respCode.equals(CARD_OK)) {
			return "No reason to decline";
		} else if (respCode.equals(CALL_REFER_TO_ISSUER)) {
			return "Refer to issuer";
		} else if (respCode.equals(CALL_REFER_TO_ISSUER_SPECIAL_CONDITION)) {
			return "Refer to issuer - Special condition";
		} else if (respCode.equals(PICK_UP_CARD)) {
			return "Pick up card";
		} else if (respCode.equals(PICK_UP_CARD_SPECIAL_CONDITION)) {
			return "Pick up card - Special condition";
		} else if (respCode.equals(PICK_UP_CARD_LOST)) {
			return "Pick up card - Lost";
		} else if (respCode.equals(PICK_UP_CARD_STOLEN)) {
			return "Pick up card - Stolen";
		} else if (respCode.equals(ALREADY_REVERSED)) {
			return "Already reversed at switch";
		} else if (respCode.equals(AMOUNT_ERROR)) {
			return "Invalid amount";
		} else if (respCode.equals(CARD_NO_ERROR)) {
			return "Invalid card number";
		} else if (respCode.equals(DECLINE)) {
			return "Do not honor";
		} else if (respCode.equals(DECLINE_INSUFFICIENT_FUNDS)) {
			return "Insufficient funds";
		} else if (respCode.equals(DECLINE_EXCEEDS_ISSUER_WITHDRAWAL_LIMIT)) {
			return "Exceeds issuer withdrawl limit";
		} else if (respCode.equals(DECLINE_EXCEEDS_WITHDRAWAL_LIMIT)) {
			return "Exceeds withdrawl limit";
		} else if (respCode.equals(DECLINE_INVALID_SERVICE_CODE)) {
			return "Invalid service code, restricted";
		} else if (respCode.equals(DECLINE_ACTIVITY_LIMIT_EXCEEDED)) {
			return "Activity limit exceeded";
		} else if (respCode.equals(DECLINE_VIOLATION)) {
			return "Violation, cannot complete";
		} else if (respCode.equals(EXPIRED_CARD)) {
			return "Expired card";
		} else if (respCode.equals(INVALID_ROUTING)) {
			return "Destination not found";
		} else if (respCode.equals(INVALID_TRANSACTION)) {
			return "Invalid transaction";
		} else if (respCode.equals(NO_ACCOUNT)) {
			return "No account";
		} else if (respCode.equals(NO_SUCH_ISSUER)) {
			return "No such issuer";
		} else if (respCode.equals(RE_ENTER)) {
			return "Re-enter transaction";
		} else if (respCode.equals(SECURITY_VIOLATION)) {
			return "Security violation";
		} else if (respCode.equals(SERV_NOT_ALLOWED_CARD)) {
			return "Transaction not permitted-Card";
		} else if (respCode.equals(SERV_NOT_ALLOWED_TERMINAL)) {
			return "Transaction not permitted-Terminal";
		} else if (respCode.equals(SYSTEM_MALFUNCTION)) {
			return "System malfunction";
		} else if (respCode.equals(TERM_ID_ERROR_INVALID_MERCHANT_ID)) {
			return "Invalid Merchant ID";
		} else if (respCode.equals(DUPLICATE_TRANSACTION)) {
			return "Unable to locate, no match";
		} else {
			return "UNKNOWN RESPONSE, CODE: " + respCode;
		}
	}
  
	/**
	 * (non-Javadoc)
	 * @see biz.janux.commerce.payment.interfaces.response.TransactionResponse#getErrorCode()
	 */
	public String getErrorCode() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * (non-Javadoc)
	 * @see biz.janux.commerce.payment.interfaces.response.TransactionResponse#getErrorData()
	 */
	public String getErrorData() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * (non-Javadoc)
	 * @see biz.janux.commerce.payment.interfaces.response.TransactionResponse#getLocalTransDate()
	 */
	public String getLocalTransDate() {
		return localTransDate;
	}
	/**
	 * (non-Javadoc)
	 * @see biz.janux.commerce.payment.interfaces.response.TransactionResponse#getLocalTransTime()
	 */
	public String getLocalTransTime() {
		return localTransTime;
 	}
	/**
	 * (non-Javadoc)
	 * @see biz.janux.commerce.payment.interfaces.response.TransactionResponse#getStatus()
	 */
	public String getStatus() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * (non-Javadoc)
	 * @see biz.janux.commerce.payment.interfaces.response.TransactionResponse#getTransactionIdentifier()
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
	 * (non-Javadoc)
	 * @see biz.janux.commerce.payment.interfaces.response.TransactionResponse#getTransactionSequenceNumber()
	 */
	public String getTransactionSequenceNumber() {
		return VitalHelper.rightJustifyZero(transactionSequenceNumber, 4);
	}
	/**
	 * 
	 * @return String
	 */
	public String getAuthResponseText() {
		return authResponseText;
	}
	/**
	 * 
	 * @param authResponseText
	 */
	public void setAuthResponseText(String authResponseText) {
		this.authResponseText = authResponseText;
	}
	/**
	 * @return char
	 */
	public char getAvsResultCode() {
		return responseCode; 
	}
	/**
	 * 
	 * @param avsResultCode
	 */
	public void setAvsResultCode(String avsResultCode) {
		if (avsResultCode != null && avsResultCode.length() == 1) {
			this.responseCode = avsResultCode.charAt(0);
  	 } 
   }
}

