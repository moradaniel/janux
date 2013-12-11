package org.janux.bus.security;

import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.janux.bus.persistence.EntityNotFoundException;
import org.janux.bus.persistence.TransactionalTestAbstract;
import org.janux.bus.test.TransactionalBusTestAbstractGeneric;


/**
 * @author   <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version  $Revision: 1.4 $ - $Date: 2007-01-11 23:13:10 $
 *
 * @see TransactionalTestAbstract
 */
public class RoleDaoGenericTest extends TransactionalBusTestAbstractGeneric
{
	Log log = LogFactory.getLog(this.getClass());

	protected RoleDaoGeneric roleDaoGeneric;

	private static final int NUM_ROLES = 12;
	private static final String GODDESS     = "GODDESS";
	private static final String SUPERVISOR  = "WORK_SUPERVISOR";
	private static final String MANAGER     = "MANAGER";
	private static final String SUPER_HUMAN = "SUPER_HUMAN";
	private static final String ENGINEER    = "ENGINEER";
	private static final String ROLE_WITH_NULLS = "ROLE_WITH_NULLS";

	private static final String HOL_MAN   = "HOLIDAY_MANAGER";
	private static final String CTX_HOL   = "CTX_HOLIDAY";
	private static final int HOL_MAN_NUM_SUB_ROLES = 0;
	private static final int HOL_MAN_SORT_ORDER = 7;

	private static final int HOL_PERM_DECLARE = 1;
	private static final int HOL_PERM_APPROVE = 2;
	private static final int HOL_PERM_TAKE    = 4;

	private static final String CTX_WORK = "CTX_WORK";

	private static final int WORK_PERM_CREATE  = 1;
	private static final int WORK_PERM_PERFORM = 2;
	private static final int WORK_PERM_ASSIGN  = 4;
	private static final int WORK_PERM_SKIP    = 8;

	public RoleDaoGenericTest() {
		super();
	}

	public RoleDaoGenericTest(String name) {
		super(name);
	}

	public void testLoadAll() 
	{
		SortedSet<Role> set = roleDaoGeneric.loadAll();
		assertNotNull(set);
		assertEquals("num roles", NUM_ROLES, set.size());

		Iterator i = set.iterator();
		Role role;

		role = (Role)i.next();
		assertEquals(GODDESS, role.getName());

		role = (Role)i.next();
		assertEquals(SUPERVISOR, role.getName());

		role = (Role)i.next();
		assertEquals(MANAGER, role.getName());

		role = (Role)i.next();
		assertEquals(SUPER_HUMAN, role.getName());
	}


	public void testLoadByName() 
	{
		Role role = roleDaoGeneric.loadByName(HOL_MAN);

		assertEquals(HOL_MAN, role.getName());
		assertEquals(HOL_MAN + " aggrRoles", HOL_MAN_NUM_SUB_ROLES, role.getRoles().size());
		assertEquals(HOL_MAN + " sortOrder", HOL_MAN_SORT_ORDER, role.getSortOrder().intValue());
		assertTrue(HOL_MAN + " enabled", role.isEnabled());

		assertHolidayManager(role);

		try
		{ 
			roleDaoGeneric.loadByName("BOGUS");
			fail("Loading BOGUS role should have thrown EntityNotFoundException");
		}
		catch (EntityNotFoundException e) { 
			// expected behavior
		}
	}

	public void testFindByName() 
	{
		Role role = roleDaoGeneric.findByName(HOL_MAN);

		assertEquals(HOL_MAN, role.getName());
		assertEquals(HOL_MAN + " aggrRoles", HOL_MAN_NUM_SUB_ROLES, role.getRoles().size());
		assertEquals(HOL_MAN + " sortOrder", HOL_MAN_SORT_ORDER, role.getSortOrder().intValue());
		assertTrue(HOL_MAN + " enabled", role.isEnabled());

		assertHolidayManager(role);

		assertNull(roleDaoGeneric.findByName("BOGUS"));
	}

	/** 
	 * Tests a MANAGER role which does not have permissions assigned to it
	 * directly, but instead aggregates a WORK_MANAGER and HOL_MANAGER roles
	 *
	 * TODO: This should really be within a unit test rather than a DAO test, but for
	 * the time being we are putting it here for expediency
	 */
	public void testRoleWithAggrRoles()
	{
		Role role = roleDaoGeneric.findByName(MANAGER);
		assertNotNull(MANAGER, role);
		assertHolidayManager(role);
		assertWorkManager(role);
	}


	/**
	 * Tests a Role that has permissions inherited via aggregated roles, as well as
	 * additional permissions assigned directly
	 */
	public void testRoleWithAggrRolesAndPerms()
	{
		PermissionsCapable role = roleDaoGeneric.findByName(SUPERVISOR);
		assertNotNull(SUPERVISOR, role);

		assertTrue("can take holiday",           role.hasPermissions(CTX_HOL, HOL_PERM_TAKE));
		assertTrue("can approve holiday",        role.hasPermissions(CTX_HOL, HOL_PERM_APPROVE));
		assertTrue("can take & approve holiday", role.hasPermissions(CTX_HOL, HOL_PERM_TAKE + HOL_PERM_APPROVE));
		assertTrue("can declare holiday",        role.hasPermissions(CTX_HOL, HOL_PERM_DECLARE));
		assertTrue("all on holiday",             role.hasPermissions(CTX_HOL, HOL_PERM_TAKE + HOL_PERM_APPROVE + HOL_PERM_DECLARE));

		assertTrue("can create work",  role.hasPermissions(CTX_WORK, WORK_PERM_CREATE));
		assertTrue("can perform work", role.hasPermissions(CTX_WORK, WORK_PERM_PERFORM));
		assertTrue("can assign work",  role.hasPermissions(CTX_WORK, WORK_PERM_ASSIGN));
		assertTrue("can skip work",    role.hasPermissions(CTX_WORK, WORK_PERM_SKIP));
		assertTrue("all on work",      role.hasPermissions(CTX_WORK, WORK_PERM_CREATE + WORK_PERM_PERFORM + WORK_PERM_ASSIGN + WORK_PERM_SKIP));
		assertTrue("can create & perform work", role.hasPermissions(CTX_WORK, WORK_PERM_CREATE + WORK_PERM_PERFORM));
		assertTrue("can create & assign work",  role.hasPermissions(CTX_WORK, WORK_PERM_CREATE + WORK_PERM_ASSIGN));
		assertTrue("can perform & assign work", role.hasPermissions(CTX_WORK, WORK_PERM_PERFORM + WORK_PERM_ASSIGN));
		assertTrue("can create, perform & assign work", role.hasPermissions(CTX_WORK, WORK_PERM_CREATE + WORK_PERM_PERFORM + WORK_PERM_ASSIGN));
	}


	/**
	 * Tests a Role that has permissions inherited via multiple levels of aggregated roles, as well as
	 * additional permissions assigned directly
	 */
	public void testRoleWithMultiLevelAggrRoles()
	{
		PermissionsCapable role = roleDaoGeneric.findByName(GODDESS);
		assertNotNull(GODDESS, role);

		assertTrue("can take holiday",           role.hasPermissions(CTX_HOL, HOL_PERM_TAKE));
		assertTrue("can approve holiday",        role.hasPermissions(CTX_HOL, HOL_PERM_APPROVE));
		assertTrue("can take & approve holiday", role.hasPermissions(CTX_HOL, HOL_PERM_TAKE + HOL_PERM_APPROVE));
		assertTrue("can declare holiday",        role.hasPermissions(CTX_HOL, HOL_PERM_DECLARE));
		assertTrue("all on holiday",             role.hasPermissions(CTX_HOL, HOL_PERM_TAKE + HOL_PERM_APPROVE + HOL_PERM_DECLARE));

		assertTrue("can create work",  role.hasPermissions(CTX_WORK, WORK_PERM_CREATE));
		assertTrue("can perform work", role.hasPermissions(CTX_WORK, WORK_PERM_PERFORM));
		assertTrue("can assign work",  role.hasPermissions(CTX_WORK, WORK_PERM_ASSIGN));
		assertTrue("can skip work",    role.hasPermissions(CTX_WORK, WORK_PERM_SKIP));
		assertTrue("all on work",      role.hasPermissions(CTX_WORK, WORK_PERM_CREATE + WORK_PERM_PERFORM + WORK_PERM_ASSIGN + WORK_PERM_SKIP));
		assertTrue("can create & perform work", role.hasPermissions(CTX_WORK, WORK_PERM_CREATE + WORK_PERM_PERFORM));
		assertTrue("can create & assign work",  role.hasPermissions(CTX_WORK, WORK_PERM_CREATE + WORK_PERM_ASSIGN));
		assertTrue("can perform & assign work", role.hasPermissions(CTX_WORK, WORK_PERM_PERFORM + WORK_PERM_ASSIGN));
		assertTrue("can create, perform & assign work", role.hasPermissions(CTX_WORK, WORK_PERM_CREATE + WORK_PERM_PERFORM + WORK_PERM_ASSIGN));
	}

	/** 
	 * Tests a Role that has permissions inherited from a parent role, 
	 * but explicitly denies one of the Permissions inherited
	 */
	public void testRoleWithDeny()
	{
		Role role = roleDaoGeneric.findByName(SUPER_HUMAN);
		assertNotNull(SUPER_HUMAN, role);

		PermissionsCapable aggrRole = role.getRoles().get(0);
		assertNotNull(ENGINEER, aggrRole);

		assertTrue(ENGINEER + " can take holidays", aggrRole.hasPermissions(CTX_HOL, HOL_PERM_TAKE));
		assertTrue(SUPER_HUMAN + " cannot take holidays", !role.hasPermissions(CTX_HOL, HOL_PERM_TAKE));
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
	public void testRoleWithNullAggrRoles()
	{
		Role role = roleDaoGeneric.findByName(ROLE_WITH_NULLS);
		assertNotNull(ROLE_WITH_NULLS, role);

		List<Role> aggrRoles = role.getRoles();
		assertNotNull(ROLE_WITH_NULLS + " aggrRoles", aggrRoles);

		assertEquals(ROLE_WITH_NULLS + " aggrRoles size", 3, aggrRoles.size());
	}

	/** 
	 * This should really be within a unit test rather than a DAO test, but for
	 * the time being we are putting it here as a matter of convenience
	 */
	private void assertHolidayManager(Role role)
	{
		assertNotNull(role);
		assertNotNull(role.getDescription());

		log.debug("granted perms: " + ((RoleImpl)role).getPermissionsGranted());
		assertEquals("holiday permissions", HOL_PERM_TAKE + HOL_PERM_APPROVE, role.getPermissionsValue(CTX_HOL));
	}

	private void assertWorkManager(PermissionsCapable role)
	{
		assertNotNull(role);

		assertEquals("work permissions", WORK_PERM_CREATE + WORK_PERM_PERFORM + WORK_PERM_ASSIGN, role.getPermissionsValue(CTX_WORK));

		assertTrue("can create work",  role.hasPermissions(CTX_WORK, WORK_PERM_CREATE));
		assertTrue("can perform work", role.hasPermissions(CTX_WORK, WORK_PERM_PERFORM));
		assertTrue("can assign work",  role.hasPermissions(CTX_WORK, WORK_PERM_ASSIGN));
		assertFalse("can skip work",   role.hasPermissions(CTX_WORK, WORK_PERM_SKIP));
		assertTrue("not all on work",  !role.hasPermissions(CTX_WORK, WORK_PERM_CREATE + WORK_PERM_PERFORM + WORK_PERM_ASSIGN + WORK_PERM_SKIP));
		assertTrue("can create & perform work", role.hasPermissions(CTX_WORK, WORK_PERM_CREATE + WORK_PERM_PERFORM));
		assertTrue("can create & assign work",  role.hasPermissions(CTX_WORK, WORK_PERM_CREATE + WORK_PERM_ASSIGN));
		assertTrue("can perform & assign work", role.hasPermissions(CTX_WORK, WORK_PERM_PERFORM + WORK_PERM_ASSIGN));
		assertTrue("can create, perform & assign work", role.hasPermissions(CTX_WORK, WORK_PERM_CREATE + WORK_PERM_PERFORM + WORK_PERM_ASSIGN));
	}

} // end class PermissionDaoTest

