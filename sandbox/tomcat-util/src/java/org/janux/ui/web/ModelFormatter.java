package org.janux.ui.web;

import java.util.Map;

import freemarker.template.Configuration;


/**
 ***************************************************************************************************
 * Implementation of FreemarkerObjectFormatter where it is desirable to serialize a set of objects 
 * rather than a single object, and where the model is created outside of this formatter.
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.1 - 2011-04-06
 ***************************************************************************************************
 */
public class ModelFormatter extends FreemarkerObjectFormatter<Map<String,Object>>
{
	public ModelFormatter(Configuration config, String aTemplateName) 
	{
		if (config == null) throw new IllegalArgumentException("Attempting to initialize " + this.getClass().getName() + " with null Freemarker Configuration");
		this.setFreemarkerConfig(config);

		if (aTemplateName == null) throw new IllegalArgumentException("Attempting to initialize " + this.getClass().getName() + " with null Template Name");
		this.setTemplateName(aTemplateName);
	}

	public Map<String,Object> buildModel(Map<String, Object> model) {
		return model;
	}

} // end class


