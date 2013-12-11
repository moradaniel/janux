package org.janux.bus.dataloader;

/**
 ***************************************************************************************************
 * Specifies a 'load' method that is used to start the loading process
 *
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version $Revision: 1.1 $ - $Date: 2005-11-17 19:46:16 $
 * @since Nov 14, 2005
 ***************************************************************************************************
 */

public interface Loader
{
	/** perform some processing before calling load() */
	void preProcess();

	void load();

	/** perform some processing after calling load */
	void postProcess();
}
