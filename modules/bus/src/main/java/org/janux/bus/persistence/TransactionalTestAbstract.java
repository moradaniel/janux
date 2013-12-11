package org.janux.bus.persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 ***************************************************************************************************
 * Extend this class when the Test suite must run tests that change the state of the test database, 
 * and when it is desireable to rollback the changes to the database for one or all of the tests;
 * this class implements beginTransaction() and rollbackTransaction() methods that retrieve the
 * Spring TransactionManager defined in the Spring configuration file, and initiate a new
 * Transaction;  The tearDown method, in turn, rollsback the transaction and any changes that may
 * have occurred to the database during the test.
 * <p>
 * Note that the setUp and tearDown methods are declared final in springs
 * AbstractDependencyInjectionSpringContextTests and hence cannot be overriden by sub-classes.  In
 * their stead onSetUp() and onTearDown() methods should be implemented by sub-classes requiring
 * this functionality.
 * </p><p>
 * This class extends a Spring class which provides the option to wire dependencies by type or by
 * name.  Typically, we use transaction proxies to wrap our Access objects.  This breaks the
 * wire-by-type option, and hence this class forces the wire-by-name alternative in all cases, based
 * on the name of protected variables. Hence, if you are getting unexplained NullPointerExceptions,
 * check that the name of the protected variables that you access in the class match the id of a
 * bean in the Spring application context.
 * </p>
 * 
 * @see org.springframework.test.AbstractDependencyInjectionSpringContextTests
 * @author   <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 *
 * @version  $Revision: 1.7 $ - $Date: 2008-12-06 00:05:24 $
 ***************************************************************************************************
 */
public abstract class TransactionalTestAbstract extends AbstractDependencyInjectionSpringContextTests 
{
	protected Log log = LogFactory.getLog(this.getClass());

	/** 
	 * gets wired by name, requires a bean named 'transactionManager' in the ApplicationContext
	 */
	protected PlatformTransactionManager transactionManager;
	private TransactionStatus transaction;

	/**  causes the default behavior to be wire-by-name because the proxies cause a failure */
	public TransactionalTestAbstract() {
		try { 
			setPopulateProtectedVariables(true);
		}
		catch (Exception e) {
			String msg="Unable to wire-in test dependencies";
			log.error(msg,e);
			throw new RuntimeException(msg, e);
		}
	}

	/** used to provide the standard Test String constructor */
	public TransactionalTestAbstract(String name) {
		this();
		this.setName(name);
	}


	/** 
	 * initiates a Transaction - if all of the tests in the Test suite should be rolled back, 
	 * this method can be called in the setUp() method of a subclass, 
	 * and rollbackTransaction() in the tearDown() method; 
	 * otherwise if only discrete tests should be wrapped in a transaction,
	 * beginTransaction() and rollbackTransaction() can be called at the
	 * suitable points within individual tests
	 */
	public void beginTransaction()
	{
		if (log.isDebugEnabled()) log.debug("initiating transaction...");
		//CollageFactory collage = new CollageFactory(); 
		//assertNotNull("collage factory", collage);

		//transactionManager = 
		//	(PlatformTransactionManager)collage.getAPIObject(CollageFactory.TRANSACTION_MANAGER);
		assertNotNull("transaction manager", transactionManager);

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		//def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);

		transaction = transactionManager.getTransaction(def);
	}


	/** 
	 * rollsback the transaction to prevent changes to the database - 
	 * depending on where a TestCase calls beginTransaction(),
	 * rollbackTransaction() should be called either in the tearDown() method 
	 * or at a suitable point within a test
	 */
	public void rollbackTransaction() {
		if (log.isDebugEnabled()) log.debug("rolling back transaction...");
		transactionManager.rollback(transaction);
    transaction = null;
	}


	/** 
	 * rollback the transaction if it exists; this is to make sure that any
	 * open transactions are rolled back after an assertion fails
	 */
	protected void onTearDown()
	{
		if (transaction != null) {
			if (log.isDebugEnabled()) log.debug("rolling back interrupted transaction...");
			transactionManager.rollback(transaction);
		}
	}

}
