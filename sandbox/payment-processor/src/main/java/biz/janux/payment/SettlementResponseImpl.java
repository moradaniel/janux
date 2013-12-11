package biz.janux.payment;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.janux.util.JanuxToStringStyle;


public class SettlementResponseImpl extends TransactionResponseImpl implements SettlementResponse{

	String recordFormat;
	String applicationType;
	String routingID;
	String recordType;
	String batchRecordCount;
	String batchNetDeposit;
	String batchResponseCode;
	String batchNumber;
	String batchResponseText;
	String batchTransmissionDate;
	String errorType;
	String errorRecordSeq;
	String errorRecordType;
	String errorDataFieldNum;
	String errorData;

	public String getRecordFormat() {
		return recordFormat;
	}

	public void setRecordFormat(String recordFormat) {
		this.recordFormat = recordFormat;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public String getRoutingID() {
		return routingID;
	}

	public void setRoutingID(String routingID) {
		this.routingID = routingID;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getBatchRecordCount() {
		return batchRecordCount;
	}

	public void setBatchRecordCount(String batchRecordCount) {
		this.batchRecordCount = batchRecordCount;
	}

	public String getBatchNetDeposit() {
		return batchNetDeposit;
	}

	public void setBatchNetDeposit(String batchNetDeposit) {
		this.batchNetDeposit = batchNetDeposit;
	}

	public String getBatchResponseCode() {
		return batchResponseCode;
	}

	public void setBatchResponseCode(String batchResponseCode) {
		this.batchResponseCode = batchResponseCode;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getBatchResponseText() {
		return batchResponseText;
	}

	public void setBatchResponseText(String batchResponseText) {
		this.batchResponseText = batchResponseText;
	}

	public String getBatchTransmissionDate() {
		return batchTransmissionDate;
	}

	public void setBatchTransmissionDate(String batchTransmissionDate) {
		this.batchTransmissionDate = batchTransmissionDate;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public String getErrorRecordSeq() {
		return errorRecordSeq;
	}

	public void setErrorRecordSeq(String errorRecordSeq) {
		this.errorRecordSeq = errorRecordSeq;
	}

	public String getErrorRecordType() {
		return errorRecordType;
	}

	public void setErrorRecordType(String errorRecordType) {
		this.errorRecordType = errorRecordType;
	}

	public String getErrorDataFieldNum() {
		return errorDataFieldNum;
	}

	public void setErrorDataFieldNum(String errorDataFieldNum) {
		this.errorDataFieldNum = errorDataFieldNum;
	}

	public String getErrorData() {
		return errorData;
	}

	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}
	
	public String toString() 
	{
		ToStringBuilder sb = new ToStringBuilder(this, JanuxToStringStyle.COMPACT);
		
		sb.append(super.toString());
		
		if (getApplicationType() != null)    sb.append("approvalCode", getApplicationType());
		if (getBatchNetDeposit() != null)    sb.append("authResponseText", getBatchNetDeposit());
		if (getBatchNumber() != null)    sb.append("authSourceCode", getBatchNumber());
		if (getBatchRecordCount() != null)    sb.append("avsResultCode", getBatchRecordCount());
		if (getBatchResponseCode() != null)    sb.append("batchResponseCode", getBatchResponseCode());
		if (getBatchResponseText() != null)    sb.append("localTransDate", getBatchResponseText());
		if (getBatchTransmissionDate() != null)    sb.append("localTransTime", getBatchTransmissionDate());
		if (getErrorData() != null)    sb.append("requestedACI", getErrorData());
		if (getErrorDataFieldNum() != null)    sb.append("responseCode", getErrorDataFieldNum());
		if (getErrorRecordSeq() != null)    sb.append("retrievalReferenceNumber", getErrorRecordSeq());
		if (getErrorRecordType() != null)    sb.append("returnedACI", getErrorRecordType());
		if (getErrorType() != null)    sb.append("storeNumber", getErrorType());
		if (getRecordFormat() != null)    sb.append("terminalNumber", getRecordFormat());
		if (getRecordType() != null)    sb.append("transactionIdentifier", getRecordType());
		if (getRoutingID() != null)    sb.append("transactionSequenceNumber", getRoutingID());
				
		return sb.toString();
	}


	public boolean equals(Object other)
	{
		if ( (this == other) ) return true;
		if ( !(other instanceof SettlementResponseImpl) ) return false;

		SettlementResponseImpl castOther = (SettlementResponseImpl)other; 

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
