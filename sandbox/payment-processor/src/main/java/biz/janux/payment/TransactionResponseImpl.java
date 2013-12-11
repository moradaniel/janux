package biz.janux.payment;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.janux.bus.persistence.PersistentVersionableAbstract;
import org.janux.util.JanuxToStringStyle;


public abstract class TransactionResponseImpl extends PersistentVersionableAbstract implements TransactionResponse{

	Date date;
	byte[] originalBytes;
	boolean approved;
	
	String errorDescription;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public byte[] getOriginalBytes() {
		return originalBytes;
	}
	public void setOriginalBytes(byte[] originalBytes) {
		this.originalBytes = originalBytes;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	public String toString() 
	{
		ToStringBuilder sb = new ToStringBuilder(this, JanuxToStringStyle.COMPACT);

		if (getId() != null) sb.append("id",   getId());
		if (getDate() != null) sb.append("date", getDate());
		sb.append("approved", isApproved());
		if (getErrorDescription() != null)    sb.append("errorDescription", getErrorDescription());
		if (getDateCreated() != null)    sb.append("created", getDateCreated());
		if (getDateUpdated() != null)    sb.append("updated", getDateUpdated());

		return sb.toString();
	}


	public boolean equals(Object other)
	{
		if ( (this == other) ) return true;
		if ( !(other instanceof TransactionResponseImpl) ) return false;

		TransactionResponseImpl castOther = (TransactionResponseImpl)other; 

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
