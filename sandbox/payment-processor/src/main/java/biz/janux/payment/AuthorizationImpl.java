package biz.janux.payment;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.janux.util.JanuxToStringStyle;

public class AuthorizationImpl extends TransactionImpl<AuthorizationResponse> implements Authorization {

	BigDecimal amount;
	private CreditCard creditCard;
	
	List<AuthorizationModification> modifications ;
	
	private boolean batched;
	
	private String externalSourceId;

	
	/**
	 * used by hibernate 
	 */
	public AuthorizationImpl() {
		super();
	}

	public AuthorizationImpl(BusinessUnit businessUnit,CreditCard creditCard, BigDecimal amount, String externalSourceId) {
		super(businessUnit);
		this.amount = amount;
		this.creditCard = creditCard;
		this.externalSourceId = externalSourceId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public boolean isBatched() {
		return batched;
	}

	public void setBatched(boolean batched) {
		this.batched = batched;
	}

	public List<AuthorizationModification> getModifications() {
		return modifications;
	}

	public void setModifications(
			List<AuthorizationModification> modifications) {
		this.modifications = modifications;
	}

	public void setExternalSourceId(String externalSourceId) {
		this.externalSourceId = externalSourceId;
	}

	public String getExternalSourceId() {
		return externalSourceId;
	}

	public String toString() 
	{
		ToStringBuilder sb = new ToStringBuilder(this, JanuxToStringStyle.COMPACT);
		
		sb.append(super.toString());
		if (getAmount() != null)	sb.append("amount",   getAmount());
		if (getCreditCard() != null)	sb.append("creditCard", getCreditCard());
		if (getExternalSourceId() != null)	sb.append("exteralSourceId", getExternalSourceId());
		sb.append("batched", isBatched());

		return sb.toString();
	}


	public boolean equals(Object other)
	{
		if ( (this == other) ) return true;
		if ( !(other instanceof AuthorizationImpl) ) return false;

		AuthorizationImpl castOther = (AuthorizationImpl)other; 

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

}
