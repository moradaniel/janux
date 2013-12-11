package org.janux.ui.springmvc.web.view.tiles;

import java.util.Locale;

import java.util.Map;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>Abstract Spring view resolver class for combining Tiles and your favorite view technology</p>
 * @author <a href="mailto:kirlen@janux.org">Kevin Irlen</a>
 */
public abstract class AbstractTilesSpringViewResolver extends UrlBasedViewResolver
{
	Log log = LogFactory.getLog(this.getClass());

	protected boolean exposeSpringMacroHelpers = false;
	protected Map environmentVariables;

	/**
	 * Used to expose environment variables defined in the spring configuration;
	 * these environment variables are passed to the view when the resolver
	 * instantiates the view in loadView
	 */
	public Map getEnvironmentVariables() {
		return this.environmentVariables;
	}

	public void setEnvironmentVariables(Map map) {
		if (log.isDebugEnabled()) log.debug("Setting Environment Variables map: '" + map + "'");
		this.environmentVariables = map;
	}


	/** set to true if you want to use the spring html-generating macros */
	public void setExposeSpringMacroHelpers(boolean expose)
	{
		this.exposeSpringMacroHelpers = expose;
	}

	protected View loadView(String viewName, Locale locale) throws Exception 
	{
		AbstractTilesView view = (AbstractTilesView) super.loadView(viewName, locale);
		view.setExposeSpringMacroHelpers(this.exposeSpringMacroHelpers);
		view.setEnvironmentVariables(this.environmentVariables);

		return view;
	}

}
