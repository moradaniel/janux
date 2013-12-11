package biz.janux.payment;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.janux.bus.persistence.PersistentVersionableIdentifiableAbstract;
import org.janux.util.JanuxToStringStyle;

public class BusinessUnitImpl extends PersistentVersionableIdentifiableAbstract implements BusinessUnit {
	
	private MerchantAccount merchantAccount;
	private IndustryType industryType;
	private boolean enabled;
	private String code;
	private String name;
	
	public MerchantAccount getMerchantAccount() {
		return merchantAccount;
	}
	public void setMerchantAccount(MerchantAccount merchantAccount) {
		this.merchantAccount = merchantAccount;
	}
	public IndustryType getIndustryType() {
		return industryType;
	}
	public void setIndustryType(IndustryType industryType) {
		this.industryType = industryType;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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

		if (getId() != null)             sb.append("id",   getId());
		if (getUuid() != null)           sb.append("uuid", getUuid());
		if (getCode() != null)   sb.append("code",  getCode());
		if (getName() != null)   sb.append("name",  getName());
		if (getMerchantAccount() != null)   sb.append("merchantAccount",  getMerchantAccount());
		if (getIndustryType() != null) sb.append("industryType", getIndustryType());
		if (getDateCreated() != null)    sb.append("created", getDateCreated());
		if (getDateUpdated() != null)    sb.append("updated", getDateUpdated());
		sb.append("enabled", isEnabled());

		return sb.toString();
	}


	public boolean equals(Object other)
	{
		if ( (this == other) ) return true;
		if ( !(other instanceof BusinessUnitImpl) ) return false;

		BusinessUnitImpl castOther = (BusinessUnitImpl)other; 

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
