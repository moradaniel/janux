package org.janux.util;

import org.springframework.beans.factory.config.PropertyOverrideConfigurer;

/**
 * Wrapper for CAS properties used as a bean in CAS client applications.
 * This is useful for configuring application context cas properties using 
 * the {@link PropertyOverrideConfigurer}
 *
 * @author  <a href="mailto:moradaniel@janux.org">Daniel Mora</a>
 */

public class CasProperties {
	
	private String casServerUrlPrefix;
	
	private String proxyCallbackUrl;

	public String getCasServerUrlPrefix() {
		return casServerUrlPrefix;
	}

	public void setCasServerUrlPrefix(String casServerUrlPrefix) {
		this.casServerUrlPrefix = casServerUrlPrefix;
	}
	
	public String getProxyCallbackUrl() {
		return proxyCallbackUrl;
	}

	public void setProxyCallbackUrl(String proxyCallbackUrl) {
		this.proxyCallbackUrl = proxyCallbackUrl;
	}

}
