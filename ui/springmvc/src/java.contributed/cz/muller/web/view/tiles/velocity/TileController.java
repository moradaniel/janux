package cz.muller.web.view.tiles.velocity;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

/**
 * Base tile controller interface that receives request when merging a tile into a Tiles layout.
 * <p>
 * Any implementation of the TileController interface should be a reusable, threadsafe class, 
 * capable of handling multiple HTTP requests throughout the lifecycle of an application. 
 * To be able to configure TileController in an easy, TileControllers are usually JavaBeans.
 *
 * @author <a href="mailto:pavel@jehlanka.cz">Pavel Mueller</a>
 */
public interface TileController {

    /**
     * Process the request and return the model for tile to which the controller belongs.
     * 
     * @param request current HTTP request
     * @param response current HTTP response
     * @param context tile context
     * @return model for the tile, or null if no model needed
     */
    public Map perform(HttpServletRequest request, HttpServletResponse response, ComponentContext context);
    
}
