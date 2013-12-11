package org.janux.ui.springmvc.web.view.tiles.freemarker;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;

/**
 * Extends Spring's FreeMarkerConfigurer in order to support multiple shared FreeMarker template
 * libraries.
 */
public class TilesFreeMarkerConfigurer extends FreeMarkerConfigurer
{
	private String templateClasspaths[] = null;
	private String templateUrlpaths[] = null;
	
	public TilesFreeMarkerConfigurer()
	{
		super();
	}

	/**
	 * Post-processes the config to add additional classpath-based template loaders that may
	 * be specified when this bean is defined.
	 * 
	 * Overrides base-class method which added just one hard-coded extra loader for Spring's 
	 * form-binding macros (spring.ftl). Rather than hard-coding that functionality here, clients
	 * who wish to use those macros can add that classpath like any other. For example:
	 * 
	 * 	<property name="templateClasspaths">
	 * 		<list>
	 *         <value>org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer</value><!-- spring.ftl -->
	 *         <value>org.xyz.somepackage.someclass</value><!-- mymacros.ftl -->
	 *      </list>
	 *  </property>
	 * 
	 * NOTE: Due to the way FreeMarker implements classpath-based template loaders, the "paths" must refer not 
	 * to the directory where your .ftl files live, but to an actual Java class within the corresponding package.
	 * If necessary, deploy a dummy class to the directory of your choice to satisfy this implementation quirk.
	 * @see freemarker.cache.ClassTemplateLoader
	 */
	protected void postProcessConfiguration(Configuration config)
	{
		List<TemplateLoader> loaderList = new ArrayList<TemplateLoader>();
		
		// start with template loader defined in config
		TemplateLoader loader = config.getTemplateLoader();
		loaderList.add(loader);
		
		// add any additional classpaths
		if (templateClasspaths != null)
		{
			for (int i = 0; i < templateClasspaths.length; i++)
			{
				try
				{
					Class templateClass = Class.forName(templateClasspaths[i]);
					
					loader = new ClassTemplateLoader(templateClass,"");
					
					loaderList.add(loader);
				}
				catch (ClassNotFoundException e)
				{
					logger.warn("Can't create Template Loader for " + templateClasspaths[i] + "!");
				}
			}
		}
		
		// add any additional URL paths
		if (templateUrlpaths != null)
		{
			for (int i = 0; i < templateUrlpaths.length; i++)
			{
				try
				{
					loader = new SimpleURLTemplateLoader(templateUrlpaths[i]);
					
					loaderList.add(loader);
				}
				catch (MalformedURLException e)
				{
					logger.warn("Can't create Template Loader for " + templateUrlpaths[i] + "!");
				}
			}
		}
		
		TemplateLoader[] loaders = (TemplateLoader[]) loaderList.toArray(new TemplateLoader[2]);
		MultiTemplateLoader multiLoader = new MultiTemplateLoader(loaders);

		config.setTemplateLoader(multiLoader);
	}

	public void setTemplateClasspaths(String[] extraTemplateClasspaths) {
		this.templateClasspaths = extraTemplateClasspaths;
	}

	public void setTemplateUrlpaths(String[] templateUrlpaths) {
		this.templateUrlpaths = templateUrlpaths;
	}

}
