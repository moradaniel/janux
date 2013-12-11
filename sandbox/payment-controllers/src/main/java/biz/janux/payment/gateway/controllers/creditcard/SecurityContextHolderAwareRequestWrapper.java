package biz.janux.payment.gateway.controllers.creditcard;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;

/**
 * It is a wrapper of {@link SecurityContextHolderAwareRequestWrapper}
 * 
 * Allow change the parameters of security requests 
 * 
 * @author albertobuffagni@gmail.com
 * 
 */
public class SecurityContextHolderAwareRequestWrapper extends org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper {

	private final Map<String, String[]> parameters = new LinkedHashMap<String, String[]>(16);

	public SecurityContextHolderAwareRequestWrapper(HttpServletRequest request,String rolePrefix) {
		super(request,rolePrefix);
		
		final Enumeration<String> iter = super.getParameterNames();
		while (iter.hasMoreElements()) {
			final String p = iter.nextElement();
			parameters.put(p, new String[]{super.getParameter(p)});
		}
	}

	public String getParameter(String name) {
		Assert.notNull(name, "Parameter name must not be null");
		String[] arr = this.parameters.get(name);
		return (arr != null && arr.length > 0 ? arr[0] : null);
	}

	public Enumeration<String> getParameterNames() {
		return Collections.enumeration(this.parameters.keySet());
	}

	public String[] getParameterValues(String name) {
		Assert.notNull(name, "Parameter name must not be null");
		return this.parameters.get(name);
	}

	public Map<String, String[]> getParameterMap() {
		return this.parameters;
	}
	
}
