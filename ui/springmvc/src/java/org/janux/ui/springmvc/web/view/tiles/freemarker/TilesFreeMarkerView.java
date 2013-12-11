package org.janux.ui.springmvc.web.view.tiles.freemarker;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.janux.ui.springmvc.web.view.tiles.AbstractTilesView;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContextException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import freemarker.template.*;

/**
 * View implementation for using Tiles with FreeMarker.
 * 
 * @author <a href="mailto:kirlen@janux.org">Kevin Irlen</a>
 */
public class TilesFreeMarkerView  extends AbstractTilesView
{
	Configuration configuration = null;

	/**
	 * Invoked on startup. Looks for a single FreeMarkerConfig bean to
	 * find the relevant FreeMarkerEngine for this factory.
	 */
	protected void initApplicationContext() throws BeansException {
		super.initApplicationContext();

		if (configuration == null) 
		{
			try
			{
				FreeMarkerConfig config = (FreeMarkerConfig)
						BeanFactoryUtils.beanOfTypeIncludingAncestors(getApplicationContext(), FreeMarkerConfig.class, true, true);
				this.configuration = config.getConfiguration();
			}
			catch (NoSuchBeanDefinitionException ex)
			{
				throw new ApplicationContextException("Must define a single FreeMarkerConfig bean in this web application " +
														"context (may be inherited): FreeMarkerConfigurer is the usual implementation. " +
														"This bean may be given any name.", ex);
			}
		}

	}

	protected void doRender(String path, Map model, HttpServletRequest request,
			   HttpServletResponse response) throws Exception
	{
		TilesFreeMarkerTool tilesTool = new TilesFreeMarkerTool(request, response, getApplicationContext(), configuration,model);
		model.put(this.tilesToolAttribute, tilesTool);
	
	 	Template template = (this.encoding != null ? this.configuration.getTemplate(path, this.encoding) :
			this.configuration.getTemplate(path));
	 	
		template.process(model,response.getWriter());
	}
}
