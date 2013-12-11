package org.janux.bus.security;

/** 
 * This is a temporary interface to store account settings that will be deprecated in favor of the
 * more general interfaces in the org.janux.adapt package
 */
public interface AccountSetting
{
	void setTag(String tag);
	String getTag();
	
	void setValue(String value);
	String getValue();
}
