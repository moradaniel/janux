package org.janux.ui.springmvc.web.view.tiles.velocity;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.context.ApplicationContext;
import org.janux.ui.springmvc.web.view.tiles.TilesTool;

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
 * @author <a href="mailto:kirlen@janux.org">Kevin Irlen</a>
 */
public class TilesVelocityTool extends TilesTool
{
	private Context velocityContext;
	private VelocityEngine velocityEngine;

	/******************************* Constructors ****************************/

	/**
	 * Default constructor. Tool must be initialized before use.
	 */
	public TilesVelocityTool(Context velocityContext, HttpServletRequest request, HttpServletResponse response, 
							ApplicationContext applicationContext, VelocityEngine velocityEngine)
	{
		super(request, response, applicationContext);
		
		this.velocityContext = velocityContext;
		this.velocityEngine = velocityEngine;
	}


	/**
	 * Renders tile into string.
	 * 
	 * @param page relative path to resource file
	 * @return tile content in string representation
	 * @throws Exception 
	 * @throws ParseErrorException 
	 * @throws ResourceNotFoundException 
	 */
	protected String doRender(String page) throws Exception
	{
		String result = null;

		if (page.endsWith(".vm"))
		{
			//merge Velocity template
			Template template = this.velocityEngine.getTemplate(page);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			template.merge(velocityContext, pw);
			return sw.getBuffer().toString();
		}

		return result;
	}

	protected void putAttribute(String name, Object value)
	{
		velocityContext.put(name, value);
	}

}

