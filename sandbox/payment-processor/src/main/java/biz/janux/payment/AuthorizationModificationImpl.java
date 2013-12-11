package biz.janux.payment;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.janux.bus.persistence.PersistentVersionableIdentifiableAbstract;
import org.janux.util.JanuxToStringStyle;

public class AuthorizationModificationImpl extends PersistentVersionableIdentifiableAbstract implements AuthorizationModification{

	
	
	Date date;
	BigDecimal amount;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public String toString() 
	{
		ToStringBuilder sb = new ToStringBuilder(this, JanuxToStringStyle.COMPACT);
		
		sb.append(super.toString());
		if (getAmount() != null)	sb.append("amountAuthorizationModification",   getAmount());
		if (getDate() != null)	sb.append("dateAuthorizationModification", getDate());

		return sb.toString();
	}


	public boolean equals(Object other)
	{
		if ( (this == other) ) return true;
		if ( !(other instanceof AuthorizationModificationImpl) ) return false;

		AuthorizationModificationImpl castOther = (AuthorizationModificationImpl)other; 

		return new org.apache.commons.lang.builder.EqualsBuilder()
			.append(this.getUuid(), castOther.getUuid())
			.isEquals();
	}

	public int hashCode() 
	{
		return new org.apache.commons.lang.builder.HashCodeBuilder()
			.append(this.getUuid())
			.toHashCode();
	}
	
	public AuthorizationModificationImpl() {
		super();
	}

}
