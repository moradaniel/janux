package biz.janux.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This class can be used for all transactional tests in the biz.janux module; it merely extends
 * {@link org.janux.bus.persistence.TransactionalTestAbstract} by specifying the location of the
 * Application Context files used to configure this module
 *
 * @author <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 *
 * @version  $Revision: 1.4 $ - $Date: 2007-03-06 16:03:33 $
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:ApplicationContext.xml"})
public abstract class TransactionalBizTestAbstract// extends TransactionalTestAbstract
{
    @Autowired
    protected ApplicationContext applicationContext;
    
    /*
	protected String[] getConfigLocations(){
		return new String[] {
			"classpath:ApplicationContext.xml",
		};
	}*/
	/*
			"classpath:DatabaseContext.xml",
			"classpath:HibernateContext.xml",
			"classpath:SecurityContext.xml",
			"classpath:BizHibernateMappings.xml",
			"classpath:BizTransactionContext.xml",
			"classpath:PartyContext.xml",
			"classpath:GeographyContext.xml",
			"classpath:CommerceContext.xml"
	 */

	public TransactionalBizTestAbstract() {
		super();
	}

	/** used to provide the standard Test String constructor */
	/*public TransactionalBizTestAbstract(String name) {
		super(name);
	}*/

}
