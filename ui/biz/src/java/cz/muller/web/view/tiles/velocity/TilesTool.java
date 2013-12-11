package cz.muller.web.view.tiles.velocity;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.tiles.AttributeDefinition;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.ComponentDefinition;
import org.apache.struts.tiles.DefinitionAttribute;
import org.apache.struts.tiles.DefinitionNameAttribute;
import org.apache.struts.tiles.DefinitionsFactoryException;
import org.apache.struts.tiles.DirectStringAttribute;
import org.apache.struts.tiles.TilesUtil;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

/**
 * <p>View tool to use struts-tiles with Velocity</p>
 * <p><pre>
 * Template example(s):
 *  &lt;!-- insert a tile --&gt;
 *  $tiles.myTileDefinition
 *
 *  &lt;!-- get named attribute value from the current tiles-context --&gt;
 *  $tiles.getAttribute("myTileAttribute")
 *
 *  &lt;!-- import all attributes of the current tiles-context into the velocity-context. --&gt;
 *  $tiles.importAttributes()
 *
 * @author <a href="mailto:marinoj@centrum.is">Marino A. Jonsson</a>
 * @author <a href="mailto:pavel@jehlanka.cz">Pavel Mueller</a>
 */
public class TilesTool {
	public static final String PAGE_SCOPE = "page";
	public static final String REQUEST_SCOPE = "request";
	public static final String SESSION_SCOPE = "session";
	public static final String APPLICATION_SCOPE = "application";

	private Context velocityContext;
	private ApplicationContext applicationContext;
	private ServletContext application;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private VelocityEngine velocityEngine;

	/**
	 * A stack to hold ComponentContexts while nested tile-definitions
	 * are rendered.
	 */
	protected Stack contextStack;

	/******************************* Constructors ****************************/

	/**
	 * Default constructor. Tool must be initialized before use.
	 */
	public TilesTool(Context velocityContext, HttpServletRequest request, HttpServletResponse response, ApplicationContext applicationContext, VelocityEngine velocityEngine) {
		this.velocityContext = velocityContext;
		this.request = request;
		this.response = response;
		this.applicationContext = applicationContext;
		this.application = ((WebApplicationContext)applicationContext).getServletContext();
		this.velocityEngine = velocityEngine;
	}



	/**
	 * <p>A generic tiles insert function</p>
	 *
	 * <p>This is functionally equivalent to
	 * <code>&lt;tiles:insert attribute="foo" /&gt;</code>.</p>
	 *
	 * @param attr - can be any of the following:
	 *        AttributeDefinition,
	 *        tile-definition name,
	 *        tile-attribute name,
	 *        regular uri.
	 *        (checked in that order)
	 * @return the rendered template or value as a String
	 * @throws Exception on failure
	 */
	public String get(Object obj) {
		try {
			Object value = getCurrentContext().getAttribute(obj.toString());
			if (value != null) {
				return processObjectValue(value);
			}
			return processAsDefinitionOrURL(obj.toString());
		} catch (Exception e) {
			Velocity.error("TilesTool: Exeption while rendering Tile " + obj + ": " + e.getMessage());
			return null;
		}
	}

	/**
	 * Fetches a named attribute-value from the current tiles-context.
	 *
	 * <p>This is functionally equivalent to
	 * <code>&lt;tiles:getAsString name="foo" /&gt;</code>.</p>
	 *
	 * @param name the name of the tiles-attribute to fetch
	 * @return attribute value for the named attribute
	 */
	public Object getAttribute(String name) {
		Object value = getCurrentContext().getAttribute(name);
		if (value == null) {
			Velocity.warn("TilesTool: Tile attribute '"	+ name + "' was not found in context.");
		}
		return value;
	}

	/**
	 * Imports the named attribute-value from the current tiles-context into the
	 * current Velocity context.
	 *
	 * <p>This is functionally equivalent to
	 * <code>&lt;tiles:importAttribute name="foo" /&gt;</code>
	 *
	 * @param name the name of the tiles-attribute to import
	 */
	public void importAttribute(String name) {
		this.importAttribute(name, PAGE_SCOPE);
	}

	/**
	 * Imports the named attribute-value from the current tiles-context into the
	 * named context ("page", "request", "session", or "application").
	 *
	 * <p>This is functionally equivalent to
	 * <code>&lt;tiles:importAttribute name="foo" scope="scopeValue" /&gt;</code>
	 *
	 * @param name the name of the tiles-attribute to import
	 * @param scope the named context scope to put the attribute into.
	 */
	public void importAttribute(String name, String scope) {
		Object value = getCurrentContext().getAttribute(name);
		if (value == null) {
			Velocity.warn("TilesTool: Tile attribute '" + name + "' was not found in context.");
		}

		if (scope.equals(PAGE_SCOPE)) {
			velocityContext.put(name, value);
		} else if (scope.equals(REQUEST_SCOPE)) {
			request.setAttribute(name, value);
		} else if (scope.equals(SESSION_SCOPE)) {
			request.getSession().setAttribute(name, value);
		} else if (scope.equals(APPLICATION_SCOPE)) {
			application.setAttribute(name, value);
		}
	}

	/**
	 * Imports all attributes in the current tiles-context into the 
	 * current velocity-context.
	 *
	 * <p>This is functionally equivalent to
	 * <code>&lt;tiles:importAttribute /&gt;</code>.</p>
	 */
	public void importAttributes() {
		this.importAttributes(PAGE_SCOPE);
	}

	/**
	 * Imports all attributes in the current tiles-context into the named
	 * context ("page", "request", "session", or "application").
	 *
	 * <p>This is functionally equivalent to
	 * <code>&lt;tiles:importAttribute scope="scopeValue" /&gt;</code>.</p>
	 *
	 * @param scope the named context scope to put the attributes into.
	 */
	public void importAttributes(String scope) {
		ComponentContext context = getCurrentContext();
		Iterator names = context.getAttributeNames();

		if (scope.equals(PAGE_SCOPE)) {
			while (names.hasNext()) {
				String name = (String) names.next();
				velocityContext.put(name, context.getAttribute(name));
			}
		} else if (scope.equals(REQUEST_SCOPE)) {
			while (names.hasNext()) {
				String name = (String) names.next();
				request.setAttribute(name, context.getAttribute(name));
			}
		} else if (scope.equals(SESSION_SCOPE)) {
			HttpSession session = request.getSession();
			while (names.hasNext()) {
				String name = (String) names.next();
				session.setAttribute(name, context.getAttribute(name));
			}
		} else if (scope.equals(APPLICATION_SCOPE)) {
			while (names.hasNext()) {
				String name = (String) names.next();
				application.setAttribute(name, context.getAttribute(name));
			}
		}
	}




	/**
	 * Process an object retrieved as a bean or attribute.
	 *
	 * @param value - Object can be a typed attribute, a String, or anything
	 *        else. If typed attribute, use associated type. Otherwise, apply
	 *        toString() on object, and use returned string as a name.
	 * @throws Exception - Throws by underlying nested call to
	 *         processDefinitionName()
	 * @return the fully processed value as String
	 */
	protected String processObjectValue(Object value) throws Exception {
		// first, check if value is one of the Typed Attribute 
		if (value instanceof AttributeDefinition) {
			// we have a type => return appropriate IncludeType 
			return processTypedAttribute((AttributeDefinition) value);
		} else if (value instanceof ComponentDefinition) {
			return processDefinition((ComponentDefinition) value);
		}
		// value must denote a valid String 
		return processAsDefinitionOrURL(value.toString());
	}

	/**
	 * Process typed attribute according to its type.
	 *
	 * @param value Typed attribute to process.
	 * @return the fully processed attribute value as String.
	 * @throws Exception - Throws by underlying nested call to processDefinitionName()
	 */
	protected String processTypedAttribute(AttributeDefinition value) throws Exception {
		
		if (value instanceof DirectStringAttribute) {
			return (String) value.getValue();
		} else if (value instanceof DefinitionAttribute) {
			return processDefinition((ComponentDefinition) value.getValue());
		} else if (value instanceof DefinitionNameAttribute) {
			return processAsDefinitionOrURL((String) value.getValue());
		}

		// else if( value instanceof PathAttribute ) 
		return doInsert((String) value.getValue(), null, null);
	}

	/**
	 * Try to process name as a definition, or as an URL if not found.
	 *
	 * @param name Name to process.
	 * @return the fully processed definition or URL
	 * @throws Exception
	 */
	protected String processAsDefinitionOrURL(String name) throws Exception {
		try {
			ComponentDefinition definition =
				TilesUtil.getDefinition(name, this.request, this.application);
			if (definition != null) {
				return processDefinition(definition);
			}
		} catch (DefinitionsFactoryException ex) {
			/* silently failed, because we can choose to not define a factory. */
		}
		/* no definition found, try as url */
		return processUrl(name);
	}

	/**
	 * End of Process for definition.
	 *
	 * @param definition Definition to process.
	 * @return the fully processed definition.
	 * @throws Exception from InstantiationException Can't create requested controller
	 */
	protected String processDefinition(ComponentDefinition definition) throws Exception {
		try {
			TileController controller = getController(definition, request);

			String role = definition.getRole();
			String page = definition.getTemplate();

			return doInsert(definition.getAttributes(), page, role, controller);
		} catch (InstantiationException ex) {
			throw new Exception(ex.getMessage());
		}
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
		
		return (TileController) applicationContext.getBean(beanName, TileController.class);
	}
	
	
	
	/**
	 * Processes an url
	 *
	 * @param url the URI to process.
	 * @return the rendered template as String.
	 * @throws Exception
	 */
	protected String processUrl(String url) throws Exception {
		return doInsert(url, null, null);
	}

	/**
	 * Use this if there is no nested tile.
	 *
	 * @param page the page to process.
	 * @param role possible user-role
	 * @param controller possible tiles-controller
	 * @return the rendered template as String.
	 * @throws Exception
	 */
	protected String doInsert(String page, String role, TileController controller)
		throws Exception {
		if (role != null && !this.request.isUserInRole(role)) {
			return null;
		}

		ComponentContext subCompContext = new ComponentContext();
		return doInsert(subCompContext, page, role, controller);
	}

	/**
	 * Use this if there is a nested tile.
	 *
	 * @param attributes attributes for the sub-context
	 * @param page the page to process.
	 * @param role possible user-role
	 * @param controller possible tiles-controller
	 * @return the rendered template as String.
	 * @throws Exception
	 */
	protected String doInsert(Map attributes, String page, String role, TileController controller) throws Exception {
		if (role != null && !this.request.isUserInRole(role)) {
			return null;
		}

		ComponentContext subCompContext = new ComponentContext(attributes);
		return doInsert(subCompContext, page, role, controller);
	}

	/**
	 * An extension of the other two doInsert functions
	 *
	 * @param subCompContext the sub-context to set in scope when the
	 *        template is rendered.
	 * @param page the page to process.
	 * @param role possible user-role
	 * @param controller possible tiles-controller
	 * @return the rendered template as String.
	 * @throws Exception
	 */
	protected String doInsert(ComponentContext subCompContext, String page, String role, TileController controller) throws Exception {

		pushTilesContext();
		try {
			ComponentContext.setContext(subCompContext, this.request);

			// call controller if any 
			if (controller != null) {
				Map model = controller.perform(this.request, this.response, subCompContext);
				if(model != null) {
				    for (Iterator it = model.keySet().iterator(); it.hasNext(); ) {
                        String key = (String) it.next();
                        velocityContext.put(key, model.get(key));
                    }
				}
			}
			
			// render tile 
			return render(page);
		} finally {
			popTilesContext();
		}
	}

	/**
	 * Renders tile into string.
	 * 
	 * @param page relative path to resource file
	 * @return tile content in string representation
	 */
	private String render(String page) {
		try {
			if(!page.endsWith(".jsp")) {
				//merge Velocity template
				Template template = this.velocityEngine.getTemplate(page);
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				template.merge(velocityContext, pw);
				return sw.getBuffer().toString();
			} else {
				//include request
				return new ImportSupport(application, request, response).acquireString(page);
			}
		} catch(Exception e) {
			Velocity.warn("TilesTool: Exception during rendering tile: "+page);
			return null;
		}
	}



	/**
	 * Retrieve the current tiles component context.
	 * This is pretty much just a convenience method.
	 */
	protected ComponentContext getCurrentContext() {
		return ComponentContext.getContext(this.request);
	}

	/**
	 * <p>pushes the current tiles context onto the context-stack.
	 * preserving the context is necessary so that a sub-context can be
	 * put into request scope and lower level tiles can be rendered</p>
	 */
	protected void pushTilesContext() {
		if (this.contextStack == null) {
			this.contextStack = new Stack();
		}
		contextStack.push(getCurrentContext());
	}
	/**
	 * <p>pops the tiles sub-context off the context-stack after the lower level
	 * tiles have been rendered</p>
	 */
	protected void popTilesContext() {
		ComponentContext context = (ComponentContext) this.contextStack.pop();
		ComponentContext.setContext(context, this.request);
	}

}
