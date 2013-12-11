package org.janux.ui.springmvc.web.view.tiles.velocity;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.app.tools.VelocityFormatter;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.NumberTool;
import org.janux.ui.springmvc.web.view.tiles.AbstractTilesView;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContextException;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.velocity.VelocityConfig;

/**
 * View implementation for using Tiles with Velocity. Sets content type
 * and exposes DateTool and NumberTool for views.
 * 
 * @author <a href="mailto:pavel@jehlanka.cz">Pavel Mueller</a>
 * @author <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @author <a href="mailto:kirlen@janux.org">Kevin Irlen</a>
 */
public class TilesVelocityView extends AbstractTilesView {
	private static final String DEFAULT_LINK_TOOL_ATTR = "linkTool";

	private String velocityFormatterAttribute;

	private String dateToolAttribute;

	private String numberToolAttribute;

	private String linkToolAttribute = DEFAULT_LINK_TOOL_ATTR;

	private VelocityEngine velocityEngine;

	private Template template;

	/**
	 * Set the name of the VelocityFormatter helper object to expose in the
	 * Velocity context of this view, or null if not needed.
	 * VelocityFormatter is part of the standard Velocity distribution.
	 * @see org.apache.velocity.app.tools.VelocityFormatter
	 */
	public void setVelocityFormatterAttribute(String velocityFormatterAttribute) {
		this.velocityFormatterAttribute = velocityFormatterAttribute;
	}

	/**
	 * Set the name of the DateTool helper object to expose in the Velocity context
	 * of this view, or null if not needed. DateTool is part of Velocity Tools 1.0.
	 * @see org.apache.velocity.tools.generic.DateTool
	 */
	public void setDateToolAttribute(String dateToolAttribute) {
		this.dateToolAttribute = dateToolAttribute;
	}

	/**
	 * Set the name of the NumberTool helper object to expose in the Velocity context
	 * of this view, or null if not needed. NumberTool is part of Velocity Tools 1.1.
	 * @see org.apache.velocity.tools.generic.NumberTool
	 */
	public void setNumberToolAttribute(String numberToolAttribute) {
		this.numberToolAttribute = numberToolAttribute;
	}

	/**
	 * Set the name of the LinkTool helper object to expose in the Velocity context
	 * of this view. Default is 'linkTool'.
	 * @see LinkTool
	 */
	public void setLinkToolAttribute(String linkToolAttribute) {
		this.linkToolAttribute = linkToolAttribute;
	}

	/**
	 * Set the VelocityEngine to be used by this view.
	 * If this is not set, the default lookup will occur: A single VelocityConfig
	 * is expected in the current web application context, with any bean name.
	 * @see VelocityConfig
	 */
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	/**
	 * Return the VelocityEngine used by this view.
	 */
	protected VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}


	/**
	 * Invoked on startup. Looks for a single VelocityConfig bean to
	 * find the relevant VelocityEngine for this factory.
	 */
	protected void initApplicationContext() throws BeansException {
		super.initApplicationContext();

		if (this.velocityEngine == null) {
			try {
				VelocityConfig velocityConfig = (VelocityConfig)
						BeanFactoryUtils.beanOfTypeIncludingAncestors(getApplicationContext(), VelocityConfig.class, true, true);
				this.velocityEngine = velocityConfig.getVelocityEngine();
			} catch (NoSuchBeanDefinitionException ex) {
				throw new ApplicationContextException("Must define a single VelocityConfig bean in this web application " +
														"context (may be inherited): VelocityConfigurer is the usual implementation. " +
														"This bean may be given any name.", ex);
			}
		}

	}

	protected void doRender(String path, Map model, HttpServletRequest request,
			   HttpServletResponse response) throws Exception
	{

		// We already hold a reference to the template, but we might want to load it
		// if not caching. As Velocity itself caches templates, so our ability to
		// cache templates in this class is a minor optimization only.
		if (!this.cacheTemplate) {
			this.template = getTemplate(path);
		}

		Context velocityContext = new VelocityContext(model);
		exposeTools(velocityContext, request, response);
		exposeHelpers(velocityContext, request);

		//render
		mergeTemplate(template, velocityContext, response);
		if (logger.isDebugEnabled()) {
			logger.debug("Merged with Velocity template '" + path + "' in VelocityView '" + getBeanName() + "'");
		}
	}
	
	/**
	 * Retrieve the Velocity template for given path.
	 * @param path path of the template
	 * @return the Velocity template to process
	 * @throws Exception if thrown by Velocity
	 */
	protected Template getTemplate(String path) throws Exception {
		try {
			return (this.encoding != null ? this.velocityEngine.getTemplate(path, this.encoding) :
					this.velocityEngine.getTemplate(path));
		} catch (ResourceNotFoundException ex) {
			throw new ApplicationContextException("Cannot find Velocity template for URL [" + getUrl() +
																						"]: Did you specify the correct resource loader path?", ex);
		} catch (Exception ex) {
			throw new ApplicationContextException("Cannot load Velocity template for URL [" + getUrl() + "]", ex);
		}
	}


	/**
	 * Exposes additional tools into velocity context.
	 * @param velocityContext
	 * @param request
	 * @throws Exception
	 */
	private void exposeTools(Context velocityContext, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//expose tiles tool
		TilesVelocityTool tilesTool = new TilesVelocityTool(velocityContext, request, response, getApplicationContext(), velocityEngine);
		velocityContext.put(this.tilesToolAttribute, tilesTool);

		//expose link tool
		LinkTool linkTool = new LinkTool(request, response, getServletContext());
		velocityContext.put(this.linkToolAttribute, linkTool);

		// expose formatter
		if (this.velocityFormatterAttribute != null) {
			velocityContext.put(this.velocityFormatterAttribute, new VelocityFormatter(velocityContext));
		}

		// expose date and number tool if required
		if (this.dateToolAttribute != null || this.numberToolAttribute != null) {
			Locale locale = RequestContextUtils.getLocale(request);
			if (this.dateToolAttribute != null) {
				velocityContext.put(this.dateToolAttribute, new LocaleAwareDateTool(locale));
			}
			if (this.numberToolAttribute != null) {
				velocityContext.put(this.numberToolAttribute, new LocaleAwareNumberTool(locale));
			}
		}
	}

	/**
	 * Expose helpers unique to each rendering operation. This is necessary so that
	 * different rendering operations can't overwrite each other's formats etc.
	 * <p>
	 * Called by renderMergedOutputModel.  This method can be overridden to add
	 * custom helpers to the Velocity context.
	 *
	 * @param velocityContext Velocity context that will be passed to the template at merge time
	 * @param request current HTTP request
	 * @throws Exception if there's a fatal error while we're adding information to the context
	 * @see #renderMergedOutputModel
	 */
	protected void exposeHelpers(Context velocityContext, HttpServletRequest request) throws Exception 
	{
	}

	/**
	 * Merge the template with the context.
	 * Can be overridden to customize the behavior.
	 * @param template the template to merge
	 * @param context the Velocity context
	 * @param response servlet response (use this to get the OutputStream or Writer)
	 * @see org.apache.velocity.Template#merge
	 */
	protected void mergeTemplate(Template template, Context context, HttpServletResponse response) throws Exception {
		template.merge(context, response.getWriter());
	}


	/**
	 * Subclass of DateTool from Velocity tools,
	 * using the RequestContext Locale instead of the default Locale.
	 * @see org.springframework.web.servlet.support.RequestContextUtils#getLocale
	 */
	private static class LocaleAwareDateTool extends DateTool {

		private Locale locale;

		private LocaleAwareDateTool(Locale locale) {
			this.locale = locale;
		}

		public Locale getLocale() {
			return this.locale;
		}
	}


	/**
	 * Subclass of NumberTool from Velocity tools,
	 * using the RequestContext Locale instead of the default Locale.
	 * @see org.springframework.web.servlet.support.RequestContextUtils#getLocale
	 */
	private static class LocaleAwareNumberTool extends NumberTool {

		private Locale locale;

		private LocaleAwareNumberTool(Locale locale) {
			this.locale = locale;
		}

		public Locale getLocale() {
			return this.locale;
		}
	}

}
