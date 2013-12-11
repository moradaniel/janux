package biz.janux.payment;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.janux.bus.persistence.PersistentVersionableIdentifiableAbstract;
import org.janux.util.JanuxToStringStyle;


public class SettlementItemImpl extends PersistentVersionableIdentifiableAbstract implements SettlementItem {

	BigDecimal amount;
	Authorization authorization;
	SettlementItemType type;
	Date date;
	CreditCard creditCard;
	BusinessUnit businessUnit;
	Settlement settlement;
	String purchaseIdentifier;
	private String externalSourceId;
	
	
	/**
	 * Used by hibernate
	 * @deprecated
	 */
	public SettlementItemImpl() {
		super();
	}

	public SettlementItemImpl(BigDecimal amount, SettlementItemType type, Date date, CreditCard creditCard, BusinessUnit businessUnit, String purchaseIdentifier, String externalSourceId) {
		super();
		this.amount = amount;
		this.type = type;
		this.date = date;
		this.creditCard = creditCard;
		this.businessUnit = businessUnit;
		this.purchaseIdentifier = purchaseIdentifier;
		this.externalSourceId = externalSourceId;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Authorization getAuthorization() {
		return authorization;
	}
	public void setAuthorization(Authorization authorization) {
		this.authorization = authorization;
	}
	public SettlementItemType getType() {
		return type;
	}
	public void setType(SettlementItemType type) {
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public CreditCard getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public BusinessUnit getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(BusinessUnit businessUnit) {
		this.businessUnit = businessUnit;
	}

	public String toString() 
	{
		ToStringBuilder sb = new ToStringBuilder(this, JanuxToStringStyle.COMPACT);
		
		if (getId() != null)	sb.append("id",   getId());
		if (getUuid() != null)	sb.append("uuid", getUuid());
		if (getDate() != null)	sb.append("date", getDate());
		if (getAmount() != null)	sb.append("amount", getAmount());
		if (getType() != null)	sb.append("type", getType());
		if (getAuthorization() != null)	sb.append("authorization",  getAuthorization().getUuid());
		if (getCreditCard() != null)	sb.append("creditCard",  getCreditCard());
		if (getBusinessUnit() != null)	sb.append("businessUnit",  getBusinessUnit());
		if (getPurchaseIdentifier() != null)	sb.append("purchaseIdentifier",  getPurchaseIdentifier());
		if (getExternalSourceId() != null)	sb.append("externalSourceId",  getExternalSourceId());
		if (getDateCreated() != null)    sb.append("created", getDateCreated());
		if (getDateUpdated() != null)    sb.append("updated", getDateUpdated());
		return sb.toString();
	}


	public boolean equals(Object other)
	{
		if ( (this == other) ) return true;
		if ( !(other instanceof SettlementItemImpl) ) return false;

		SettlementItemImpl castOther = (SettlementItemImpl)other; 

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
	public Settlement getSettlement() {
		return settlement;
	}
	public void setSettlement(Settlement settlement) {
		this.settlement = settlement;
	}
	public String getPurchaseIdentifier() {
		return purchaseIdentifier;
	}
	public void setPurchaseIdentifier(String purchaseIdentifier) {
		this.purchaseIdentifier = purchaseIdentifier;
	}

	public void setExternalSourceId(String externalSourceId) {
		this.externalSourceId = externalSourceId;
	}

	public String getExternalSourceId() {
		return externalSourceId;
	}
}
