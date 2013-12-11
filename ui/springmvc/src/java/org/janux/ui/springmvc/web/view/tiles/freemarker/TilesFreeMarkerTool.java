package org.janux.ui.springmvc.web.view.tiles.freemarker;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.janux.ui.springmvc.web.view.tiles.TilesTool;
import org.springframework.context.ApplicationContext;

import freemarker.template.*;

/**
 * <p>View tool to use struts-tiles with FreeMarker</p>
 * <p><pre>
 * Template example(s):
 *  &lt;!-- insert a tile --&gt;
 *  ${tiles.myTileDefinition}
 *
 *  &lt;!-- get named attribute value from the current tiles-context --&gt;
 *  ${tiles.getAttribute("myTileAttribute")}
 *
 *  &lt;!-- import all attributes of the current tiles-context into the velocity-context. --&gt;
 *  ${tiles.importAttributes()}
 *
 * @author <a href="mailto:kirlen@janux.org">Kevin Irlen</a>
 */
public class TilesFreeMarkerTool extends TilesTool
{
	Configuration configuration;
	Map model;
	
	public TilesFreeMarkerTool(HttpServletRequest request,
			HttpServletResponse response, ApplicationContext applicationContext, Configuration configuration,Map model)
	{
		super(request, response, applicationContext);
		
		this.configuration = configuration;
		this.model = model;
	}

	protected void putAttribute(String name, Object value)
	{
		model.put(name,value);
	}

	protected String doRender(String page) throws IOException, TemplateException
	{
		String result = null;
		
		try {
		if (page.endsWith(".ftl"))
		{
			Template template = configuration.getTemplate(page);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			template.process(model, pw);
			
			result = sw.getBuffer().toString();
		}
		} catch (TemplateException e)
		{
			e.printStackTrace();
			throw e;
		}
	
	return result;
	}

}
