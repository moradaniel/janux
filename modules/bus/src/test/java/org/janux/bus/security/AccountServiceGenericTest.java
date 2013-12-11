package org.janux.bus.security;

import java.util.Set;
import java.util.List;

import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.janux.bus.persistence.TransactionalTestAbstract;
import org.janux.bus.test.TransactionalBusTestAbstractGeneric;


/**
 * @author   <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version  $Revision: 1.4 $ - $Date: 2007-02-19 21:31:51 $
 *
 * @see TransactionalTestAbstract
 */
public class AccountServiceGenericTest extends TransactionalBusTestAbstractGeneric
{
	Log log = LogFactory.getLog(this.getClass());

	protected AccountService accountServiceGeneric;

	private final static String ACCOUNT_HERA = "hera";
	private final static String ACCOUNT_WITH_NULL_ROLES = "funky";

	/*
	public static Test suite()
	{
		TestSuite suite = new TestSuite();
		suite.addTest(new AccountServiceTest("testAccountWithNullRoles"));
		return suite;
	}
	*/

	public AccountServiceGenericTest() {
		super();
	}

	public AccountServiceGenericTest(String name) {
		super(name);
	}

	public void testFindByName() 
	{
		Account account = accountServiceGeneric.findAccountByName(ACCOUNT_HERA);
		assertNotNull(account);
		if (log.isDebugEnabled()) log.debug(account);

		account = accountServiceGeneric.findAccountByName("heracles");
		assertNotNull(account);
		if (log.isDebugEnabled()) log.debug(account);

		account = accountServiceGeneric.findAccountByName("ariadne");
		assertNotNull(account);
		if (log.isDebugEnabled()) log.debug(account);

		account = accountServiceGeneric.findAccountByName("dilbert");
		assertNotNull(account);
		if (log.isDebugEnabled()) log.debug(account);

		account = accountServiceGeneric.findAccountByName("topham");
		assertNotNull(account);
		if (log.isDebugEnabled()) log.debug(account);
	}

	public void testAccountSettings()
	{
		final String settingTag1 = "setting1";
		final String settingValue1 = "123";
		final String settingTag2 = "setting2";
		final String settingValue2 = "456";
		Account account = accountServiceGeneric.findAccountByName(ACCOUNT_HERA);
		assertNotNull(account);
		if (log.isDebugEnabled()) log.debug(account);
		
		Set<AccountSetting> settings = account.getSettings();
		AccountSetting setting1 = new AccountSettingImpl(settingTag1,settingValue1);
		AccountSetting setting2 = new AccountSettingImpl(settingTag2,settingValue2);
		
		settings.add(setting1);
		settings.add(setting2);

		accountServiceGeneric.saveOrUpdateAccount(account);
		
		Account modifiedAccount = accountServiceGeneric.findAccountByName(ACCOUNT_HERA);
		assertNotNull(account);
		if (log.isDebugEnabled()) log.debug(account);
		
		settings = modifiedAccount.getSettings();
		assertNotNull(settings);
		assertTrue(settings.size() == 2);
	}

	/** 
	 * Hibernate treats List index mappings quite literrally and expects them to be sequential, or
	 * will otherwise return a Null at the location of the missing index; for example, if the
	 * aggregated roles of a role return a sortOrder sequence of 0,1,3,4 (rather than 0,1,2,3) the
	 * getRoles method will return a list with 5 entries, the third one of which, at position 2, will
	 * be empty.  This is a rather annoying quirk, because it makes it more difficult to manage roles
	 * via the database.  In particular, if one removes an aggregated role from a parent
	 * role without 'fixing' the sortOrder field of the other roles in the list, the Role (or Account)
	 * may contain null-roles and may cause a NullPointerException.  We have enhanced the
	 * implementation to automatically remove these null entries from the list at the time that the
	 * List<Role> is retrieved, and consequently on the next 'save' operation the sortOrder sequence
	 * should be set properly.
	 */
	public void testAccountWithNullRoles()
	{
		Account account = accountServiceGeneric.findAccountByName(ACCOUNT_WITH_NULL_ROLES);
		assertNotNull(ACCOUNT_WITH_NULL_ROLES, account);

		List<Role> aggrRoles = account.getRoles();
		assertNotNull(ACCOUNT_WITH_NULL_ROLES + " aggrRoles", aggrRoles);

		assertEquals(ACCOUNT_WITH_NULL_ROLES + " aggrRoles size", 3, aggrRoles.size());
	}

	public static void main(String[] args)
	{
			// create the suite of tests
			final TestSuite tSuite = new TestSuite();
			tSuite.addTest(new AccountServiceGenericTest("testFindByName"));
			TestRunner.run(tSuite);
	}
} // end class AccountServiceTest
