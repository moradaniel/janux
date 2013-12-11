package org.janux.bus.test;

import org.janux.bus.persistence.TransactionalTestAbstract;

/**
 * This class can be used for all transactional tests in the org.janux.bus module; it merely extends
 * {@link org.janux.bus.persistence.TransactionalTestAbstract} by specifying the location of the
 * Application Context files used to configure this module
 *
 * @author <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 *
 * @version  $Revision: 1.2 $ - $Date: 2006-08-23 22:21:59 $
 */
public abstract class TransactionalBusTestAbstractGeneric extends TransactionalTestAbstract
{
	/*
	protected String[] getConfigLocations(){
		return new String[] {
			"classpath:ApplicationContext.xml",
			"classpath:DatabaseContext.xml",
			"classpath:HibernateContextGeneric.xml",
			"classpath:BusHibernateMappings.xml",
			"classpath:SecurityContextGeneric.xml"
		};
	}
	*/

	protected String[] getConfigLocations(){
		return new String[] {
			"classpath:ApplicationContext.xml",
			"classpath:SecurityContextGeneric.xml",
		};
	}

	public TransactionalBusTestAbstractGeneric() {
		super();
	}

	/** used to provide the standard Test String constructor */
	public TransactionalBusTestAbstractGeneric(String name) {
		super(name);
	}
}
