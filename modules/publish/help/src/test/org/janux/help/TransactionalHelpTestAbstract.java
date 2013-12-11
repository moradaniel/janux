package org.janux.help;
import org.janux.bus.persistence.TransactionalTestAbstract;


public class TransactionalHelpTestAbstract extends TransactionalTestAbstract {

	public TransactionalHelpTestAbstract()
	{
	}

	public TransactionalHelpTestAbstract(String name)
	{
		super(name);
	}

	@Override
	protected String[] getConfigLocations()
	{
		return new String[] {
				"classpath:ApplicationContext.xml",
				"classpath:DatabaseContext.xml",
				"classpath:HibernateContext.xml",
				"classpath:HibernateMappings.xml",
				"classpath:HelpContext.xml",
			};
	}

}
