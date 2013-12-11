package biz.janux.test;

import org.janux.bus.persistence.TransactionalTestAbstract;

/**
 * This class can be used for all transactional tests in the biz.janux module; it merely extends
 * {@link org.janux.bus.persistence.TransactionalTestAbstract} by specifying the location of the
 * Application Context files used to configure this module
 *
 * @author <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 *
 * @version  $Revision: 1.4 $ - $Date: 2007-03-06 16:03:33 $
 */
public abstract class TransactionalBizTestAbstractGeneric extends TransactionalTestAbstract
{
	protected String[] getConfigLocations(){
		return new String[] {
			"classpath:ApplicationContextGeneric.xml",
		};
	}
	/*
			"classpath:DatabaseContext.xml",
			"classpath:HibernateContextGeneric.xml",
			"classpath:SecurityContextGeneric.xml",
			"classpath:BizHibernateMappings.xml",
			"classpath:BizTransactionContext.xml",
			"classpath:PartyContextGeneric.xml",
			"classpath:GeographyContextGeneric.xml",
			"classpath:CommerceContext.xml"
	 */

	public TransactionalBizTestAbstractGeneric() {
		super();
	}

	/** used to provide the standard Test String constructor */
	public TransactionalBizTestAbstractGeneric(String name) {
		super(name);
	}

}
