package org.janux.ui.springmvc.web.view.tiles.freemarker;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.janux.ui.springmvc.web.view.tiles.AbstractTilesSpringViewResolver;

/**
 * Resolver for using Tiles and FreeMarker together. 
 * 
 * @author <a href="mailto: kirlen@janux.org">Kevin Irlen</a>
 */
public class TilesFreeMarkerViewResolver extends AbstractTilesSpringViewResolver
{

	/**
	 * Sets default viewClass to TilesFreeMarkerView.
	 * @see #setViewClass
	 */
	public TilesFreeMarkerViewResolver()
	{
		setViewClass(TilesFreeMarkerView.class);
	}

	/**
	 * Requires TilesFreeMarkerView.
	 * @see TilesFreeMarkerView
	 */
	protected Class requiredViewClass()
	{
		return TilesFreeMarkerView.class;
	}

	protected View loadView(String viewName, Locale locale) throws Exception
	{
		TilesFreeMarkerView view = (TilesFreeMarkerView) super.loadView(viewName, locale);
		view.setExposeRequestAttributes(true);
		view.setExposeSessionAttributes(true);

		return view;
	}

}
