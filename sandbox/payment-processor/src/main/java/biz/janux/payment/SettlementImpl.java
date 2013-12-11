package biz.janux.payment;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.janux.util.JanuxToStringStyle;

import biz.janux.payment.MerchantAccount;


public class SettlementImpl extends TransactionImpl<SettlementResponse> implements Settlement {

	/**
	 * used by hibernate 
	 */
	public SettlementImpl() {
		super();
	}
	
	public SettlementImpl(BusinessUnit businessUnit) {
		super(businessUnit);
	}

	List<SettlementItem> settlementItems;
	boolean enabled;
	int batchNumber;

	public List<SettlementItem> getSettlementItems() {
		return settlementItems;
	}

	public void setSettlementItems(List<SettlementItem> settlementItems) {
		this.settlementItems = settlementItems;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String toString() 
	{
		ToStringBuilder sb = new ToStringBuilder(this, JanuxToStringStyle.COMPACT);
		
		sb.append(super.toString());
		sb.append("enabled", isEnabled());
		sb.append("batchNumber", getBatchNumber());

		return sb.toString();
	}


	public boolean equals(Object other)
	{
		if ( (this == other) ) return true;
		if ( !(other instanceof SettlementImpl) ) return false;

		SettlementImpl castOther = (SettlementImpl)other; 

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

	public int getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(int batchNumber) {
		this.batchNumber = batchNumber;
	}
	
}
