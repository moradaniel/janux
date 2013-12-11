package org.janux.bus.security;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class AccountSettingImpl implements AccountSetting, java.io.Serializable
{
	private static final long serialVersionUID = 20070620L;

	String tag;
	String value;
	
	public AccountSettingImpl() {}
	
	public AccountSettingImpl(String tag,String value)
	{
		this.tag = tag;
		this.value = value;
	}

	public String getTag() {
		return tag;
	}

	public String getValue() {
		return value;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean equals(Object other)
	{
		if ( (this == other ) ) return true;
		if ( !(other instanceof AccountSettingImpl) ) return false;
		AccountSettingImpl castOther = (AccountSettingImpl)other; 

		return new EqualsBuilder()
			.append(this.getTag(), castOther.getTag())
			.append(this.getValue(), castOther.getValue())
			.isEquals();
	}


	public int hashCode() 
	{
		return new HashCodeBuilder()
		.append(this.getTag())
		.append(this.getValue())
		.toHashCode();
	}

}
