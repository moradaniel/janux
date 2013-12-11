package cz.muller.web.view.tiles.velocity;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.velocity.VelocityView;

/**
 * Resolver for using Tiles and Velocity together. Allows to define attribute
 * names for DateTool and NumberTool for all views.
 * 
 * @author <a href="mailto: pavel@jehlanka.cz">Pavel Mueller</a>
 */
public class TilesVelocityViewResolver extends UrlBasedViewResolver {

	private String velocityFormatterAttribute;

	private String dateToolAttribute;

	private String numberToolAttribute;

	/**
	 * Sets default viewClass to VelocityView.
	 * @see #setViewClass
	 */
	public TilesVelocityViewResolver() {
		setViewClass(TilesVelocityView.class);
	}

	/**
	 * Requires VelocityView.
	 * @see VelocityView
	 */
	protected Class requiredViewClass() {
		return TilesVelocityView.class;
	}

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

	protected View loadView(String viewName, Locale locale) throws Exception {
		TilesVelocityView view = (TilesVelocityView) super.loadView(viewName, locale);
		view.setVelocityFormatterAttribute(this.velocityFormatterAttribute);
		view.setDateToolAttribute(this.dateToolAttribute);
		view.setNumberToolAttribute(this.numberToolAttribute);
		view.setExposeRequestAttributes(true);
		view.setExposeSessionAttributes(true);
		return view;
	}

}
