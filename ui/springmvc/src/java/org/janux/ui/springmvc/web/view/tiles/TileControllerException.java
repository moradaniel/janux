package org.janux.ui.springmvc.web.view.tiles;

import org.springframework.core.NestedRuntimeException;

/**
 *
 * @author <a href="mailto:pavel@jehlanka.cz">Pavel Mueller</a>
 * @author <a href="mailto:kirlen@janux.org">Kevin Irlen</a>
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
