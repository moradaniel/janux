package biz.janux.payment;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.janux.util.JanuxToStringStyle;


public class AuthorizationResponseImpl extends TransactionResponseImpl implements AuthorizationResponse{

    String approvalCode;
    String storeNumber;
    String terminalNumber;
    
    String requestedACI;
    String returnedACI;
    String authSourceCode;
    String transactionSequenceNumber;

    String responseCode;
    String avsResultCode;
    String authResponseText;
    String retrievalReferenceNumber;
    String transactionIdentifier;
    String validationCode;
    String localTransDate;
    String localTransTime;
    boolean reversal;
    boolean offline;
    boolean declined;
    
    public AuthorizationResponseImpl() {
        super();
    }
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }
    
    public String getAuthResponseText() {
        return authResponseText;
    }

    public void setAuthResponseText(String authResponseText) {
        this.authResponseText = authResponseText;
    }

    public String getAuthSourceCode() {
        return authSourceCode;
    }

    public void setAuthSourceCode(String authSourceCode) {
        this.authSourceCode = authSourceCode;
    }

    public String getAvsResultCode() {
        return avsResultCode;
    }

    public void setAvsResultCode(String avsResultCode) {
        this.avsResultCode = avsResultCode;
    }

    public String getLocalTransDate() {
        return localTransDate;
    }

    public void setLocalTransDate(String localTransDate) {
        this.localTransDate = localTransDate;
    }

    public String getLocalTransTime() {
        return localTransTime;
    }

    public void setLocalTransTime(String localTransTime) {
        this.localTransTime = localTransTime;
    }

    public byte[] getOriginalBytes() {
        return originalBytes;
    }

    public void setOriginalBytes(byte[] originalBytes) {
        this.originalBytes = originalBytes;
    }

    public String getRequestedACI() {
        return requestedACI;
    }

    public void setRequestedACI(String requestedACI) {
        this.requestedACI = requestedACI;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getRetrievalReferenceNumber() {
        return retrievalReferenceNumber;
    }

    public void setRetrievalReferenceNumber(String retrievalReferenceNumber) {
        this.retrievalReferenceNumber = retrievalReferenceNumber;
    }

    public String getReturnedACI() {
        return returnedACI;
    }

    public void setReturnedACI(String returnedACI) {
        this.returnedACI = returnedACI;
    }

    public boolean isReversal() {
        return reversal;
    }

    public void setReversal(boolean reversal) {
        this.reversal = reversal;
    }

    public String getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public String getTerminalNumber() {
        return terminalNumber;
    }

    public void setTerminalNumber(String terminalNumber) {
        this.terminalNumber = terminalNumber;
    }

    public String getTransactionSequenceNumber() {
        return this.transactionSequenceNumber;
    }

    public void setTransactionSequenceNumber(String transactionSequenceNumber) {
        this.transactionSequenceNumber = transactionSequenceNumber;
    }

    public String getValidationCode() {
        return validationCode; 
    }

    public void setValidationCode(String validationCode) {
        this.validationCode = validationCode;
    }

    public String getTransactionIdentifier() {
        return transactionIdentifier;
    }

    public void setTransactionIdentifier(String transactionIdentifier) {
        this.transactionIdentifier = transactionIdentifier;
    }
    
    public boolean isOffline() {
        return offline;
    }

    public void setOffline(boolean offline) {
        this.offline = offline;
    }

    public boolean isDeclined() {
        return declined;
    }

    public void setDeclined(boolean declined) {
        this.declined = declined;
    }
    
    public String toString() 
    {
        ToStringBuilder sb = new ToStringBuilder(this, JanuxToStringStyle.COMPACT);
        
        sb.append(super.toString());
        
        if (getApprovalCode() != null)    sb.append("approvalCode", getApprovalCode());
        if (getAuthResponseText() != null)    sb.append("authResponseText", getAuthResponseText());
        if (getAuthSourceCode() != null)    sb.append("authSourceCode", getAuthSourceCode());
        if (getAvsResultCode() != null)    sb.append("avsResultCode", getAvsResultCode());
        if (getErrorDescription() != null)    sb.append("errorDescription", getErrorDescription());
        if (getLocalTransDate() != null)    sb.append("localTransDate", getLocalTransDate());
        if (getLocalTransTime() != null)    sb.append("localTransTime", getLocalTransTime());
        if (getRequestedACI() != null)    sb.append("requestedACI", getRequestedACI());
        if (getResponseCode() != null)    sb.append("responseCode", getResponseCode());
        if (getRetrievalReferenceNumber() != null)    sb.append("retrievalReferenceNumber", getRetrievalReferenceNumber());
        if (getReturnedACI() != null)    sb.append("returnedACI", getReturnedACI());
        if (getStoreNumber() != null)    sb.append("storeNumber", getStoreNumber());
        if (getTerminalNumber() != null)    sb.append("terminalNumber", getTerminalNumber());
        if (getTransactionIdentifier() != null)    sb.append("transactionIdentifier", getTransactionIdentifier());
        if (getTransactionSequenceNumber() != null)    sb.append("transactionSequenceNumber", getTransactionSequenceNumber());
        if (getValidationCode() != null)    sb.append("validationCode", getValidationCode());
        
        sb.append("approved", isApproved());
        sb.append("declined", isDeclined());
        sb.append("offline", isOffline());
        sb.append("reversal", isReversal());

        return sb.toString();
    }


    public boolean equals(Object other)
    {
        if ( (this == other) ) return true;
        if ( !(other instanceof AuthorizationResponseImpl) ) return false;

        AuthorizationResponseImpl castOther = (AuthorizationResponseImpl)other; 

        return new org.apache.commons.lang.builder.EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() 
    {
        return new org.apache.commons.lang.builder.HashCodeBuilder()
            .append(this.getId())
            .toHashCode();
    }
}