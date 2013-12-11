package org.janux.ui.springmvc.web.view.tiles;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.ComponentDefinition;
import org.apache.struts.tiles.DefinitionsFactory;
import org.apache.struts.tiles.TilesUtilImpl;

import org.janux.ui.springmvc.web.view.tiles.TileController;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContextException;
import org.springframework.web.servlet.view.AbstractTemplateView;
import org.springframework.web.servlet.view.tiles.TilesView;

/**
 * <p>Abstract Spring view class for combining Tiles and your favorite view technology</p>
 * 
 * @author <a href="mailto:pavel@jehlanka.cz">Pavel Mueller</a>
 * @author <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @author <a href="mailto:kirlen@janux.org">Kevin Irlen</a>
 */
public abstract class AbstractTilesView extends AbstractTemplateView {

	/**
	 * Name of the attribute that will override the path of the layout page
	 * to render. A Tiles component controller can set such an attribute
	 * to dynamically switch the look and feel of a Tiles page.
	 * @see #setPath
	 */
	protected static final String PATH_ATTRIBUTE = TilesView.class.getName() + ".PATH";

	protected static final String DEFAULT_HTTP_REQUEST_ATTR = "httpRequest";
	protected static final String DEFAULT_CONTEXT_PATH_ATTR = "contextPath";
	protected static final String DEFAULT_SERVLET_PATH_ATTR = "servletPath";
	protected static final String DEFAULT_PATH_INFO_ATTR    = "pathInfo";
	protected static final String DEFAULT_ENV_VAR_ATTR      = "global";
	
	protected static final String DEFAULT_TILES_TOOL_ATTR = "tiles";

	protected String encoding = null;

	protected String tilesToolAttribute   = DEFAULT_TILES_TOOL_ATTR;
	protected String httpRequestAttribute = DEFAULT_HTTP_REQUEST_ATTR;
	protected String contextPathAttribute = DEFAULT_CONTEXT_PATH_ATTR;
	protected String servletPathAttribute = DEFAULT_SERVLET_PATH_ATTR;
	protected String pathInfoAttribute    = DEFAULT_PATH_INFO_ATTR;
	protected String environmentVariablesAttribute = DEFAULT_ENV_VAR_ATTR;

	protected Map environmentVariables;

	protected boolean cacheTemplate;

	private DefinitionsFactory definitionsFactory;


	/**
	 * Set the path of the layout page to render.
	 * @param request current HTTP request
	 * @param path the path of the layout page
	 * @see #PATH_ATTRIBUTE
	 */
	public static void setPath(HttpServletRequest request, String path) {
		request.setAttribute(PATH_ATTRIBUTE, path);
	}

	/**
	 * Set the encoding of the Velocity template file. Default is determined
	 * by the VelocityEngine: "ISO-8859-1" if not specified otherwise.
	 * <p>Specify the encoding in the VelocityEngine rather than per template
	 * if all your templates share a common encoding.
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * Return the encoding for the Velocity template.
	 */
	protected String getEncoding() {
		return encoding;
	}

	/**
	 * Set the name of the TilesTool helper object to expose in the Velocity context
	 * of this view. Default is 'tiles'.
	 * @see TilesVelocityTool
	 */
	public void setTilesToolAttribute(String tilesToolAttribute) {
		this.tilesToolAttribute = tilesToolAttribute;
	}

	/**
	 * Set the key by which we can expose the http request to the model; the
	 * default key/attribute is 'contextPath'
	 */
	public void setHttpRequestAttribute(String key) {
		this.httpRequestAttribute = key;
	}

	/**
	 * Set the key by which we can expose the context path where the web
	 * application is deployed; this attribute is important to parametrize the
	 * views of web application so that it can be easily moved between different
	 * context paths; the default key/attribute is 'contextPath'
	 */
	public void setContextPathAttribute(String key) {
		this.contextPathAttribute = key;
	}

	/**
	 * Set the key by which we can expose the servlet path (the portion of the
	 * url following the context path) of the current page; this attribute can be
	 * used to parametrize a certain set of views so that they can be moved to a
	 * different servlet path if needed; the default key/attribute is 'servletPath'
	 */
	public void setServletPathAttribute(String key) {
		this.servletPathAttribute = key;
	}

	/**
	 * Set the key by which we can expose the path information path (the portion
	 * of the url that follows the context and servlet paths the context path) of
	 * the current page; the default key/attribute is 'pathInfo'
	 */
	public void setPathInfoAttribute(String key) {
		this.pathInfoAttribute = key;
	}

	/**
	 * Set the key by which we expose in the model the map of global environemnt variables that may
	 * have been set for this deployment; the default key/attribute is 'global'
	 */
	public void setEnvironmentVariablesAttribute(String key) {
		this.environmentVariablesAttribute = key;
	}

	/**
	 * Used to expose environment variables defined in the concrete subclass of
	 * org.janux.ui.springmvc.web.view.tiles.AbstractTilesResolver used in the
	 * deployment; in particular, this mechanism can be used to expose these
	 * variables declaratively, such as in a spring configuration
	 */
	public void setEnvironmentVariables(Map map) {
		this.environmentVariables = map;
	}

	/**
	 * Set whether the Velocity template should be cached. Default is false.
	 * It should normally be true in production, but setting this to false enables us to
	 * modify Velocity templates without restarting the application (similar to JSPs).
	 * <p>Note that this is a minor optimization only, as Velocity itself caches
	 * templates in a modification-aware fashion.
	 */
	public void setCacheTemplate(boolean cacheTemplate) {
		this.cacheTemplate = cacheTemplate;
	}

	/**
	 * Invoked on startup. Looks for a single VelocityConfig bean to
	 * find the relevant VelocityEngine for this factory.
	 */
	protected void initApplicationContext() throws BeansException {
		super.initApplicationContext();

		// get definitions factory
		if (this.definitionsFactory == null) {
			this.definitionsFactory = (DefinitionsFactory) getServletContext().getAttribute(TilesUtilImpl.DEFINITIONS_FACTORY);

			if (this.definitionsFactory == null) {
				throw new ApplicationContextException("Tiles definitions factory not found: TilesConfigurer not defined?");
			}
		}
	}

	/**
	 * Renders one tile definition. Sets Velocity engine to handle the template.
	 * @see org.springframework.web.servlet.view.AbstractTemplateView#renderMergedTemplateModel(java.util.Map, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void renderMergedTemplateModel(Map model, HttpServletRequest request, 
			HttpServletResponse response) throws Exception 
	{
		// set the content type
		response.setContentType(getContentType());

		// TODO: need to create a flag so that this is only done when strictly necessary
		model.put(this.httpRequestAttribute, request);

		// expose the contextPath/servletPath/pathInfo gleamed from the http request
		model.put(this.contextPathAttribute, request.getContextPath());
		model.put(this.servletPathAttribute, request.getServletPath());
		model.put(this.pathInfoAttribute,    request.getPathInfo());

		// expose any global environment variables that all views should contain
		if (this.environmentVariables != null) {
			model.put(this.environmentVariablesAttribute, this.environmentVariables);
		}

		// get component definition
		ComponentDefinition definition = getComponentDefinition(this.definitionsFactory, request);
		if (definition == null) {
			throw new ServletException("No Tiles definition found for name '" + getUrl() + "'");
		}

		// get current component context
		ComponentContext context = getComponentContext(definition, request);
		
		// execute component controller associated with definition, if any
		TileController controller = getController(definition, request);
		if (controller != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Executing Tiles controller [" + controller + "]");
			}
			
			Map tileModel = executeController(controller, context, request, response);
			if(tileModel != null) {
			    model.putAll(tileModel);
			}
		}

		// determine the path of the definition
		String path = getDefinitionPath(definition, request);
		if (path == null) {
			throw new ServletException("Could not determine a path for Tiles definition '" +
									   definition.getName() + "'");
		}

		this.doRender(path,model,request,response);
	}

	protected abstract void doRender(String path, Map model, HttpServletRequest request,
			   HttpServletResponse response) throws Exception;




	/**
	 * Determine the Tiles component definition for the given Tiles
	 * definitions factory.
	 * @param factory the Tiles definitions factory
	 * @param request current HTTP request
	 * @return the component definition
	 */
	protected ComponentDefinition getComponentDefinition(DefinitionsFactory factory, HttpServletRequest request)
		throws Exception {
		return factory.getDefinition(getUrl(), request, getServletContext());
	}

	/**
	 * Determine the Tiles component context for the given Tiles definition.
	 * @param definition the Tiles definition to render
	 * @param request current HTTP request
	 * @return the component context
	 * @throws Exception if preparations failed
	 */
	protected ComponentContext getComponentContext(ComponentDefinition definition, HttpServletRequest request)
		throws Exception {
		ComponentContext context = ComponentContext.getContext(request);
		if (context == null) {
			context = new ComponentContext(definition.getAttributes());
			ComponentContext.setContext(context, request);
		}
		else {
			context.addMissing(definition.getAttributes());
		}
		return context;
	}

	/**
	 * Determine and initialize the Tiles component controller for the
	 * given Tiles definition, if any.
	 * @param definition the Tiles definition to render
	 * @param request current HTTP request
	 * @return the component controller to execute, or null if none
	 * @throws Exception if preparations failed
	 */
	protected TileController getController(ComponentDefinition definition, HttpServletRequest request)
			throws Exception {
		String beanName = definition.getController();
		if(beanName == null) {
		    return null;
		}
		
		return (TileController) getApplicationContext().getBean(beanName, TileController.class);
	}

	/**
	 * Execute the given Tiles controller.
	 * @param controller the component controller to execute
	 * @param context the component context
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @throws Exception if controller execution failed
	 */
	protected Map executeController(TileController controller, ComponentContext context,
									 HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		return controller.perform(request, response, context);
	}

	/**
	 * Determine the dispatcher path for the given Tiles definition,
	 * i.e. the request dispatcher path of the layout page.
	 * @param definition the Tiles definition to render
	 * @param request current HTTP request
	 * @return the path of the layout page to render
	 * @throws Exception if preparations failed
	 */
	protected String getDefinitionPath(ComponentDefinition definition, HttpServletRequest request)
		throws Exception {
		Object pathAttr = request.getAttribute(PATH_ATTRIBUTE);
		return (pathAttr != null ? pathAttr.toString() : definition.getPath());
	}

}
