package org.janux.ui.web;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.HashMap;
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
//public class CookieFormatter implements ObjectFormatterGeneric<Cookie[]>
public class CookieFormatter extends FreemarkerObjectFormatter<Cookie[]>
{
	// Log log = LogFactory.getLog(this.getClass());

	public static final String KEY_COOKIES = "cookies";
	public static final String DEFAULT_TEMPLATE_NAME = "cookies.ftl";
	// private Configuration freemarkerConfig;	
	// private String templateName = DEFAULT_TEMPLATE_NAME;

	/*
  public Configuration getFreemarkerConfig() { return freemarkerConfig;}
  public void setFreemarkerConfig(Configuration o) { freemarkerConfig = o; }

  public String getTemplateName() { return templateName; }
  public void setTemplateName(String o) { templateName = o; }
	*/

	/** Empty constructor for total configuration via Spring */
	public CookieFormatter() {
		super();
		this.setTemplateName(DEFAULT_TEMPLATE_NAME);
	}

	/** 
	 * Constructor that takes a reference to an existing freemarker configuration, useful for
	 * quick and convenient configuration when a Freemarker configuration already exists
	 */
	public CookieFormatter(Configuration config) {
		super(config);
		this.setTemplateName(DEFAULT_TEMPLATE_NAME);
	}

	public Map<String,Object> buildModel(Cookie[] cookies) 
	{
		final Map<String, Object> model = new HashMap<String, Object>();
		if (cookies != null) {
			model.put(KEY_COOKIES, cookies);
		} else {
			model.put(KEY_COOKIES, new Cookie[0]);
		}
		return model;

	}

	/*
  public String format(Cookie[] cookies) 
	{
		final Map<String, Object> model = new HashMap<String, Object>();
		model.put(KEY_COOKIES, cookies);
		try
		{ 
			Template headerTemplate = freemarkerConfig.getTemplate(this.getTemplateName());
			return FreeMarkerTemplateUtils.processTemplateIntoString(headerTemplate, model);
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
} // end CookieFormatter class


