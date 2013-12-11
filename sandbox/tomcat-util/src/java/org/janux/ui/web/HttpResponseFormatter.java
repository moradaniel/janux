package org.janux.ui.web;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.Cookie;

import org.janux.bus.processor.ObjectFormatterGeneric;

/**
 ***************************************************************************************************
 * Used to serialize into a String the contents of a HttpServletResponse.  Note that the
 * javax.servlet.http.HttpServletResponse interface does not specify accessor methods for Cookies
 * and Headers, and hence, as part of this package, we have defined a HttpServletResponse interface
 * that provides such accessors.  We also provide a subclass of javax.servlet.http.HttpServletResponseWrappers
 * that implements the janux HttpServletResponse.
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.1 - 2011-03-23
 ***************************************************************************************************
 */
public class HttpResponseFormatter extends FreemarkerObjectFormatter<org.janux.ui.web.HttpServletResponse>
{
	public static final String KEY_RESPONSE = "response";
	public static final String KEY_COOKIES  = "cookies";
	public static final String DEFAULT_TEMPLATE_NAME = "response.ftl";

	private ObjectFormatterGeneric<Cookie[]> cookieFormatter;

	public ObjectFormatterGeneric<Cookie[]> getCookieFormatter() 
	{ 
		if (this.cookieFormatter == null && this.getFreemarkerConfig() != null) {
			this.cookieFormatter = new CookieFormatter(this.getFreemarkerConfig());
		}
		return cookieFormatter; 
	}

	public void setCookieFormatter(ObjectFormatterGeneric<Cookie[]> o) { cookieFormatter = o; }

	public HttpResponseFormatter() {
		super();
		this.setTemplateName(DEFAULT_TEMPLATE_NAME);
	}

	public HttpResponseFormatter(Configuration config) { 
		super(config); 
		this.setTemplateName(DEFAULT_TEMPLATE_NAME);
	}


	public Map<String,Object> buildModel(HttpServletResponse response)
	{
		final Map<String, Object> model = new HashMap<String, Object>();
		model.put(KEY_RESPONSE, response);

		if (this.getCookieFormatter() != null) 
		{
			model.put(KEY_COOKIES, this.getCookieFormatter().format(response.getCookies()));
		} else {
			String msg = "CookieFormatter is null";
			model.put(KEY_COOKIES, msg);
		}
		return model;
	}

} // end HttpResponseFormatter class
