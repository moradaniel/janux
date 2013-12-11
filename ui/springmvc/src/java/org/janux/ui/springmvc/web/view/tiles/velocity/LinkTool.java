package org.janux.ui.springmvc.web.view.tiles.velocity;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="mailto:pavel@jehlanka.cz">Pavel Mueller</a>
 */
public class LinkTool extends org.apache.velocity.tools.view.tools.LinkTool 
{
	/**
	 * 
	 */
	public LinkTool(HttpServletRequest request, HttpServletResponse response, ServletContext application) 
	{
		this.request     = request;
		this.response    = response;
		this.application = application;
	}
}
