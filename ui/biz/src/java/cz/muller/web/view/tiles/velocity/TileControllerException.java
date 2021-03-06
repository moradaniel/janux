package cz.muller.web.view.tiles.velocity;

import org.springframework.core.NestedRuntimeException;

/**
 *
 * @author <a href="mailto:pavel@jehlanka.cz">Pavel Mueller</a>
 */
public class TileControllerException extends NestedRuntimeException {

    /**
     * Constructor
     * @param msg message
     */
    public TileControllerException(String msg) {
        super(msg);
    }
    
    /**
     * Constructor
     * @param msg message
     * @param ex exception
     */
    public TileControllerException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
