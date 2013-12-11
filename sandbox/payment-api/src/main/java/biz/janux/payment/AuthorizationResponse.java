package biz.janux.payment;

/**
 * 
 * @author Nilesh
 * @author albertobuffagni@gmail.com
 * 
 */ 
public interface AuthorizationResponse extends TransactionResponse {

	/**
	 * This 6-character field contains an authorization code when a transaction has been approved. 
	 * If the Response Code (Section 4.73) returned indicates that the transaction is not approved, 
	 * then the contents of the field should be ignored. The approval code must be stored and 
	 * submitted in the settlement data capture record.
	 */
	public String getApprovalCode();
	public void setApprovalCode(String approvalCode);
	
	/**
	 * This field contains a 16-character response or display text message. This message can be used 
	 * by the terminal to display the authorization result. The display text must not be used to 
	 * determine the nature of a response message. Vital translates the responses according to the 
	 * Language Indicator submitted in the authorization request message. Table 4.31 provides the 
	 * (U.S.) English message summary.
	 * In the case of Check Guarantee response message, the text message can originate from the 
	 * service provider and not Vital. Since Vital does not control the response text in this instance, 
	 * it is imperative that the POS system evaluate the response code and not response text to 
	 * determine the nature of a response message. Refer to the check service provider for 
	 * explanation of proprietary error responses. If no error response text is received by Vital from 
	 * the check service provider, 'ERROR xx' is returned to the terminal.
	 */
	public String getAuthResponseText();
	public void setAuthResponseText(String authResponseText);

	/**
	 * 
	 * This field contains a 1-character code indicating the source of the authorization code. The 
	 * received code must be stored and resubmitted in the data capture settlement record.
	 */
	public String getAuthSourceCode();
	public void setAuthSourceCode(String authSourceCode);

	/**
	 * This 1-character field contains the Address Verification Result Code. An Address Verification 
	 * Result Code can provide additional information concerning the authentication of a particular 
	 * transaction for which cardholder Address Verification was requested. Address Verification is 
	 * performed when utilizing transaction code 56 (Card Not Present) as well as transaction code 
	 * 54 (Standard Authorization Request) when the Magnetic Stripe is unable to be read in a Retail 
	 * environment. An Address Verification result code of "0" is returned in the response message 
	 * when no address verification has been requested. The value returned should be stored and 
	 * submitted as part of the data capture settlement record.
	 * Table 4.31  provides a summary of Address Verification Result Codes.
	 */
	public String getAvsResultCode();
	public void setAvsResultCode(String avsResultCode);

	/**
	 * This 6-character numeric field contains a Local Transaction Date (MMDDYY) calculated by 
	 * the authorization center using the time zone differential. This field can be used as the 
	 * transaction date printed on the receipt and as input for the data capture settlement record. 
	 */
	public String getLocalTransDate();
	public void setLocalTransDate(String localTransDate);

	/**
	 * This 6-character numeric field contains the Local Transaction Time returned by the 
	 * authorizing system (HHMMSS). For direct debit, the transaction time should be printed on 
	 * the receipt. The Settlement Time should be recorded and submitted in the transaction data 
	 * capture settlement record. 
	 */
	public String getLocalTransTime();
	public void setLocalTransTime(String localTransTime);

	/**
	 * This 1-character field contains the Requested ACI used to identify an authorization request as 
	 * potentially qualifying for CPS (Custom Payment Services) and MasterCard Merit programs. If 
	 * a merchant chooses not to participate in CPS, the Requested ACI value should default to an 
	 * "N". Table 4.29  and Table 4.30  provide a summary of the codes currently supported by both 
	 * Visa and MasterCard.
	 */
	public String getRequestedACI();
	public void setRequestedACI(String requestedACI);

	/**
	 * This field contains a 2-character response code indicating the status of
	 * the authorization request. The POS system must evaluate the response code
	 * and NOT the response text to determine the nature of a response message.
	 * Table 4.31 provides a listing of currently defined response codes. A
	 * response code of "00" represents an approval. A response code of "85"
	 * represents a successful card verification returned normally by
	 * transaction codes 58, 68, and 88. All other response codes represent
	 * non-approved requests. Do NOT interpret all nonapproved response codes as
	 * "DECLINED".
	 */
	public String getResponseCode();
	public void setResponseCode(String responseCode);

	/**
	 * This field contains a 12-character value reflecting the transaction Retrieval Reference Number 
	 * returned by the authorizing system. The POS system should record the retrieval reference 
	 * number received in the original authorization response. The Retrieval Reference Number is 
	 * utilized when performing authorization reversal transactions and must be settled for direct 
	 * debit transactions.
	 */
	public String getRetrievalReferenceNumber();
	public void setRetrievalReferenceNumber(String retrievalReferenceNumber);

	/**
	 * This 1-character field contains the Returned Authorization Characteristics Indicator (ACI). 
	 * This value provides information concerning the transactions' CPS qualification status. It is 
	 * not recommended that the POS system attempt to interpret the meaning of this value. 
	 * Instead, the POS system should extract whatever value is returned in this field and submit it 
	 * in the data capture settlement record. The POS system should not interpret any relationship 
	 * between this field and the presence of data in either the Transaction Identifier or Validation 
	 * Code fields 
	 */
	public String getReturnedACI();
	public void setReturnedACI(String returnedACI);

	/**
	 * This 4-character numeric field contains a number assigned by the signing member, processor, 
	 * or merchant to identify a specific merchant store within the Vital system. This field must be 
	 * right-justified and zero-filled.
	 * This field should be configured as a parameter.
	 */
	public String getStoreNumber();
	public void setStoreNumber(String storeNumber);

	/**
	 * This 4-character numeric field contains a number assigned by the signing
	 * member, processor, or merchant to identify a unique terminal within a
	 * merchant location. Because the terminal number submitted in the
	 * authorization request is echoed back to the terminal in the authorization
	 * response, this field can additionally be used in controller-based
	 * environments to assist in the matching and routing of authorization
	 * request and response messages at the point of concentration. This field
	 * should be configured as a parameter
	 */
	public String getTerminalNumber();
	public void setTerminalNumber(String terminalNumber);

	
	/**
	 * This 4-character numeric field contains a terminal-generated transaction sequence number to 
	 * be submitted in all authorization request messages. This number is echoed back to the 
	 * terminal for purposes of assisting in the matching of authorization request and response 
	 * messages. This value must be in the range of 0001 - 9999 and is incremented on each 
	 * authorization request message. This number is automatically incremented from 9999 to 0001.
	 */
	public String getTransactionSequenceNumber();
	public void setTransactionSequenceNumber(String transactionSequenceNumber);

	/**
	 * This optional 4-character field can contain specific information generated by the card issuer. 
	 * The POS device should not attempt to interpret the meaning of any data appearing in this 
	 * field. Data returned in this field is recorded and submitted as part of the data capture 
	 * settlement format.
	 */
	public String getValidationCode();
	public void setValidationCode(String validationCode);

	/**
	 * This 15-character field can contain a Visa Transaction Identifier or MasterCard Reference 
	 * Number. The POS device does not attempt to interpret the meaning of any data appearing in 
	 * this field. Data returned in this field is recorded and submitted as part of the data capture 
	 * settlement format.
	 */
	public String getTransactionIdentifier();
	public void setTransactionIdentifier(String transactionIdentifier);

	/**
	 * Indicates that the {@link Authorization} was off line.
	 * The approvalCode has to be set when the {@link AuthorizationResponse} is created.
	 */
	public boolean isOffline();
	public void setOffline(boolean offline);

	/**
	 * This value depends of the response code
	 */
	public boolean isDeclined();
	public void setDeclined(boolean Declined);

	/**
	 * If true the authorization was reverted
	 */
	public boolean isReversal();
	public void setReversal(boolean reversal);

}
