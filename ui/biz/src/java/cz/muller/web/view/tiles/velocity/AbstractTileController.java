package cz.muller.web.view.tiles.velocity;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;
import org.springframework.web.context.support.WebApplicationObjectSupport;

/**
 * Convenient superclass for tile controller implementations, 
 * using the Template Method design pattern.
 *
 * @author <a href="mailto:pavel@jehlanka.cz">Pavel Mueller</a>
 */
public abstract class AbstractTileController extends WebApplicationObjectSupport implements TileController {

    /**
     * @see cz.muller.web.view.tiles.velocity.TileController#perform(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.apache.struts.tiles.ComponentContext)
     */
    public Map perform(HttpServletRequest request, HttpServletResponse response, ComponentContext context) {
        try {
            return performInternal(request, response, context);
        } catch(Exception e) {
            if(logger.isErrorEnabled()) {
                logger.error("Error during processing tile controller.", e);
            }
            throw new TileControllerException("", e);
        }
    }

    /**
	 * Template method. Subclasses must implement this.
	 * The contract is the same as for perform.
	 * @see #perform
     */
   public abstract Map performInternal(HttpServletRequest request, HttpServletResponse response, ComponentContext context);

}
