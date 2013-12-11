package org.janux.ui.web;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Map;
import java.util.HashMap;

// import org.apache.commons.logging.Log;
// import org.apache.commons.logging.LogFactory;

import org.janux.bus.processor.ObjectFormatterGeneric;

// import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

/**
 ***************************************************************************************************
 * Abstract Formatter class that uses Freemarker to format the serialize the object into a String.
 * This class provides the various methods and fields to inject a Freemarker Configuration in the
 * Formatter, as well as the name of a template; extending classes need to implement the {@link
 * #buildModel()} method. A template method {@link #format} is provided, which simply merges the
 * template with the model.  This method may be overriden if your formatting requirements are more
 * complex.
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.1 - 2011-03-23
 ***************************************************************************************************
 */
public abstract class FreemarkerObjectFormatter<T> implements ObjectFormatterGeneric<T>
{
	// Log log = LogFactory.getLog(this.getClass());

	private Configuration freemarkerConfig;	
	private String templateName;

  public Configuration getFreemarkerConfig() { return freemarkerConfig;}
  public void setFreemarkerConfig(Configuration o) { freemarkerConfig = o; }

  public String getTemplateName() { return templateName; }
  public void setTemplateName(String o) { templateName = o; }


	/** Empty constructor when full external configuration is desirable */
	public FreemarkerObjectFormatter() {}

	/** 
	 * Constructor that takes a reference to an existing freemarker configuration, useful for
	 * quick and convenient configuration when a Freemarker configuration already exists
	 */
	public FreemarkerObjectFormatter(Configuration config) {
		if (config == null) throw new IllegalArgumentException("Attempting to initialize " + this.getClass().getName() + " with null Freemarker Configuration");
		this.setFreemarkerConfig(config);
	}


  public String format(T object) 
	{
		final Map<String, Object> model = this.buildModel(object);

		try
		{ 
			Template headerTemplate = freemarkerConfig.getTemplate(this.getTemplateName());
			return FreemarkerObjectFormatter.processTemplateIntoString(headerTemplate, model);
		}
		catch (Exception e) {
			return "Unable to format object: " + e.toString();
		}
	}

	/** 
	 * The model that is created to format the object in question. A simple implementation may simply
	 * return the object to be formatted.  Nevertheless, in the case of a complex object graph, it may
	 * be desirable to use other formatters to format part of the object graph, and add them to the
	 * model as strings
	 */
	public abstract Map<String,Object> buildModel(T object);

	/** for the moment empty implementation of the parent Processor interface */
  public void process(InputStream in, OutputStream out)
  {
    String msg = "process(in,out) not yet implemented";
    // log.error(msg);
    throw new UnsupportedOperationException(msg);
  }

  public static String processTemplateIntoString(Template template, Object model)
      throws IOException, TemplateException {
    StringWriter result = new StringWriter();
    template.process(model, result);
    return result.toString();
  }
} // end FreemarkerObjectFormatter class


