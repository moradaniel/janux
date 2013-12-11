package org.janux.ui.web;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;

// import org.apache.commons.logging.Log;
// import org.apache.commons.logging.LogFactory;

import org.janux.bus.processor.ObjectFormatterGeneric;

// import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

/**
 ***************************************************************************************************
 * 
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since
 ***************************************************************************************************
 */
// public class HttpRequestFormatter implements ObjectFormatterGeneric<HttpServletRequest>
public class HttpRequestFormatter extends FreemarkerObjectFormatter<HttpServletRequest>
{
	// Log log = LogFactory.getLog(this.getClass());

	public static final String KEY_REQUEST = "request";
	public static final String KEY_COOKIES = "cookies";
	public static final String DEFAULT_TEMPLATE_NAME = "request.ftl";

	// private Configuration freemarkerConfig;	
	// private String templateName = DEFAULT_TEMPLATE_NAME;
	private ObjectFormatterGeneric<Cookie[]> cookieFormatter;

	/*
  public Configuration getFreemarkerConfig() { return freemarkerConfig;}
  public void setFreemarkerConfig(Configuration o) { freemarkerConfig = o; }

  public String getTemplateName() { return templateName; }
  public void setTemplateName(String o) { templateName = o; }
	*/

	public ObjectFormatterGeneric<Cookie[]> getCookieFormatter() 
	{ 
		if (this.cookieFormatter == null && this.getFreemarkerConfig() != null) {
			this.cookieFormatter = new CookieFormatter(this.getFreemarkerConfig());
		}
		return cookieFormatter; 
	}

	public void setCookieFormatter(ObjectFormatterGeneric<Cookie[]> o) { cookieFormatter = o; }


	/** Empty constructor when full external configuration is desirable */
	public HttpRequestFormatter() {
		super();
		this.setTemplateName(DEFAULT_TEMPLATE_NAME);
	}


	/** 
	 * Constructor that takes a reference to an existing freemarker configuration, useful for
	 * quick and convenient configuration when a Freemarker configuration already exists; will
	 * initialize a {@CookieFormatter} by default using the same Freemarker Configuration
	 */
	public HttpRequestFormatter(Configuration config) {
		super(config);
		this.setTemplateName(DEFAULT_TEMPLATE_NAME);
		// if (config == null) throw new IllegalArgumentException("Attempting to initialize " + this.getClass().getName() + " with null Freemarker Configuration");
		// this.setFreemarkerConfig(config);
	}

	public Map<String,Object> buildModel(HttpServletRequest request)
	{
		final Map<String, Object> model = new HashMap<String, Object>();
		model.put(KEY_REQUEST, request);

		if (this.getCookieFormatter() != null) 
		{
			model.put(KEY_COOKIES, this.getCookieFormatter().format(request.getCookies()));
		} else {
			String msg = "CookieFormatter is null";
			model.put(KEY_COOKIES, msg);
		}
		return model;
	}


	/*
  public String format(HttpServletRequest request) 
	{
		final Map<String, Object> model = new HashMap<String, Object>();
		model.put(KEY_REQUEST, request);

		if (this.getCookieFormatter() != null) 
		{
			model.put(KEY_COOKIES, this.getCookieFormatter().format(request.getCookies()));
		} else {
			String msg = "CookieFormatter is null";
			model.put(KEY_COOKIES, msg);
			log.warn(msg);
		}

		try
		{ 
			Template headerTemplate = freemarkerConfig.getTemplate(this.getTemplateName());
			return FreemarkerObjectFormatter.processTemplateIntoString(headerTemplate, model);
		}
		catch (Exception e) {
			return "Unable to format request: " + e.toString();
		}
	}


  public void process(InputStream in, OutputStream out)
  {
    String msg = "process(in,out) not yet implemented";
    log.error(msg);
    throw new UnsupportedOperationException(msg);
  }
	*/

} // end HttpRequestFormatter class


