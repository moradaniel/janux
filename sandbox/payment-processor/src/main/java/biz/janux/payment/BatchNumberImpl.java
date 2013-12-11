package biz.janux.payment;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.janux.bus.persistence.PersistentVersionableAbstract;
import org.janux.util.JanuxToStringStyle;

public class BatchNumberImpl extends PersistentVersionableAbstract implements BatchNumber{

	int number;
	MerchantAccount merchantAccount;

	public BatchNumberImpl() {
		super();
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public MerchantAccount getMerchantAccount() {
		return merchantAccount;
	}

	public void setMerchantAccount(MerchantAccount merchantAccount) {
		this.merchantAccount = merchantAccount;
	}
	
	public String toString() 
	{
		ToStringBuilder sb = new ToStringBuilder(this, JanuxToStringStyle.COMPACT);
		
		sb.append(super.toString());
		sb.append("number", getNumber());
		if (getMerchantAccount() != null)
			sb.append("merchantAccount", getMerchantAccount());

		return sb.toString();
	}

	public boolean equals(Object other)
	{
		if ( (this == other) ) return true;
		if ( !(other instanceof BatchNumberImpl) ) return false;

		BatchNumberImpl castOther = (BatchNumberImpl)other; 

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
