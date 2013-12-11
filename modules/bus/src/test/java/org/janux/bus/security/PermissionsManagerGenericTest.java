package org.janux.bus.security;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 ***************************************************************************************************
 *
 *
 * @author  <a href="mailto:philippe.paravicini@eCommerceStudio.com">Philippe Paravicini</a>
 * @version $Revision: 1.1 $ - $Date: 2006-12-07 01:55:18 $
 *
 * @see
 ***************************************************************************************************
 */
public class PermissionsManagerGenericTest extends TestCase
{
	Log log = LogFactory.getLog(this.getClass());
	//static Log log = LogFactory.getLog(FooSetup.class);
	
	private MockObjectFactory mock = new MockObjectFactory();

	static final String PERM_MAN = "aPermissionManager";
	
	private PermissionContext  workContext = mock.getPermissionContext(mock.CTX_WORK);
	private PermissionsManager permsMan    = new PermissionsManager(PERM_MAN);


	public PermissionsManagerGenericTest() {
		super();
	}

	public PermissionsManagerGenericTest(String name) {
		super(name);
	}

	/** executed prior to each test */
	protected void setUp() 
	{ 
	}

	/** executed after each test */
	protected void tearDown() { }

	public void testGrantPermissions()
	{
		permsMan.grantPermissions(workContext, workContext.getMaxValue());
		assertTrue(permsMan.hasPermissions(workContext.getName(), workContext.getMaxValue()));

		for (PermissionBit permBit : workContext.getPermissionBits())
		{
			log.debug("testing hasPermissions '" + permBit.getName() + "': " + permBit.getValue());
			assertTrue(permsMan.hasPermissions( workContext.getName(), permBit.getValue() ));
			assertTrue(permsMan.hasPermissions( workContext.getName(), new String[] {permBit.getName()} ));
		}

		// test that when we set a permission of 0 we remove the permission record
		permsMan.grantPermissions(workContext, 0);
		assertNull(permsMan.permissionsGranted.get(new PermissionGrantedKey(workContext, false)));

		try { 
			log.debug("Test null permissionContext");
			permsMan.grantPermissions(null, 0);
			fail("null permissionContexts are not allowed");
		}
		catch (IllegalArgumentException e) {
			// all is well
		}

		try { 
			log.debug("Test negative permission value Failure");
			permsMan.grantPermissions(workContext, -1);
			fail("negative permissions are not allowed");
		}
		catch (IllegalArgumentException e) {
			// all is well
		}

		try { 
			log.debug("Test maximum permission Failure");
			permsMan.grantPermissions(workContext, workContext.getMaxValue() + 1);
			fail("permissions outside the range are not allowed");
		}
		catch (IllegalArgumentException e) {
			// all is well
		}
	}

	/** 
	 * tests a PermissionsManager that does not have Roles attached to it (and
	 * hence no inherited Permissions
	 */
	public void testHasPermissionsWithoutRoles()
	{
		permsMan.grantPermissions(workContext, workContext.getMaxValue());

		assertTrue(permsMan.hasPermissions(workContext.getName(), workContext.getMaxValue()));

		for (PermissionBit permBit : workContext.getPermissionBits())
		{
			log.debug("testing hasPermissions '" + permBit.getName() + "': " + permBit.getValue());
			assertTrue(permsMan.hasPermissions( workContext.getName(), permBit.getValue() ));
			assertTrue(permsMan.hasPermissions( workContext.getName(), new String[] {permBit.getName()} ));
		}

		String[] READ_PERMS          = {mock.PERM_READ};
		String[] READ_UPDATE_PERMS   = {mock.PERM_READ, mock.PERM_UPDATE};
		String[] READ_DISABLE_PERMS  = {mock.PERM_READ, mock.PERM_DISABLE};

		permsMan.grantPermissions(workContext, workContext.getValue(READ_PERMS));
		assertTrue(permsMan.hasPermissions(  workContext.getName(), workContext.getValue(READ_PERMS) ));
		assertTrue(permsMan.hasPermissions(  workContext.getName(), READ_PERMS ));

		assertFalse(permsMan.hasPermissions( workContext.getName(), workContext.getValue(READ_UPDATE_PERMS) ));
		assertFalse(permsMan.hasPermissions( workContext.getName(), READ_UPDATE_PERMS ));

		for (int i=0; i < READ_PERMS.length ; i++) {
			assertEquals( READ_PERMS[i], permsMan.getPermissions(workContext.getName())[i] );
		}

		permsMan.grantPermissions(workContext, workContext.getValue(READ_UPDATE_PERMS));
		assertTrue(permsMan.hasPermissions(  workContext.getName(), workContext.getValue(READ_PERMS) ));
		assertTrue(permsMan.hasPermissions(  workContext.getName(), READ_PERMS ));

		for (int i=0; i < READ_PERMS.length ; i++) {
			assertEquals( READ_UPDATE_PERMS[i], permsMan.getPermissions(workContext.getName())[i] );
		}

		assertTrue(permsMan.hasPermissions(  workContext.getName(), workContext.getValue(READ_UPDATE_PERMS) ));
		assertTrue(permsMan.hasPermissions(  workContext.getName(), READ_UPDATE_PERMS ));

		assertFalse(permsMan.hasPermissions( workContext.getName(), workContext.getValue(READ_DISABLE_PERMS) ));
		assertFalse(permsMan.hasPermissions( workContext.getName(), READ_DISABLE_PERMS ));

		try { 
			log.debug("Test querying for 0 perms failure");
			permsMan.hasPermissions(workContext.getName(), 0);
			fail("querying for 0 perms not allowed");
		}
		catch (IllegalArgumentException e) {
			// all is well
		}

		try { 
			log.debug("Test querying for negative perms failure");
			permsMan.hasPermissions(workContext.getName(), -1);
			fail("querying for neg perms not allowed");
		}
		catch (IllegalArgumentException e) {
			// all is well
		}
	}

} // end class PermissionsManagerTest
