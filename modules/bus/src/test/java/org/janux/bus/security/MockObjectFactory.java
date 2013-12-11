package org.janux.bus.security;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 ***************************************************************************************************
 * Singleton class used to create sample PermissionContext and Roles for testing purposes
 *
 * @author  <a href="mailto:philippe.paravicini@eCommerceStudio.com">Philippe Paravicini</a>
 * @version $Revision: 1.1 $ - $Date: 2006-12-07 01:55:18 $
 *
 * @see
 ***************************************************************************************************
 */
public class MockObjectFactory
{
	Log log = LogFactory.getLog(this.getClass());
	//static Log log = LogFactory.getLog(FooSetup.class);
	
	static final String ACCOUNT      = "ACCOUNT";

	static final String ROLE_GODDESS         = "GODDESS";
	static final String ROLE_WORK_SUPERVISOR = "WORK_SUPERVISOR";
	static final String ROLE_MANAGER         = "MANAGER";
	static final String ROLE_SUPER_HUMAN     = "SUPER_HUMAN";
	static final String ROLE_ENGINEER        = "ENGINEER";
	static final String ROLE_HUMAN           = "HUMAN";
	static final String ROLE_WORK_MANAGER    = "WORK_MANAGER";
	static final String ROLE_HOLIDAY_MANAGER = "HOLIDAY_MANAGER";
	static final String ROLE_HOLIDAY_TAKER   = "HOLIDAY_TAKER";
	static final String ROLE_ACCOUNT_ADMIN   = "ACCOUNT_ADMIN";
	static final String ROLE_ROLE_ADMIN      = "ROLE_ADMIN";

	static final String CTX_ACCOUNT = "CTX_ACCOUNT";
	static final String CTX_ROLE    = "CTX_ROLE";
	static final String CTX_HOLIDAY = "CTX_HOLIDAY";
	static final String CTX_WORK    = "CTX_WORK";

	static final String PERM_READ    = "READ";
	static final String PERM_UPDATE  = "UPDATE";
	static final String PERM_CREATE  = "CREATE";
	static final String PERM_DISABLE = "DISABLE";
	static final String PERM_PURGE   = "PURGE";
	
	static final String PERM_DECLARE = "DECLARE";
	static final String PERM_APPROVE = "APPROVE";
	static final String PERM_TAKE    = "TAKE";
	static final String PERM_DO      = "DO";
	static final String PERM_ASSIGN  = "ASSIGN";
	static final String PERM_SKIP    = "SKIP";

	static final String[] STANDARD_PERMS = {PERM_READ, PERM_UPDATE, PERM_CREATE, PERM_DISABLE, PERM_PURGE};

	/*
	static MockObjectFactory singleton;

	public MockObjectFactory() {
		if (singleton == null)
			singleton = new MockObjectFactory();
	}
	*/

	/** 
	 * returns a PermissionContext with the name specified, and with the
	 * standard permissions 'READ', 'UPDATE', 'CREATE', 'DISABLE', 'PURGE'
	 */
	public static PermissionContext getPermissionContext(String name) 
	{
		return getPermissionContext(name, STANDARD_PERMS);
	}

	/** 
	 * returns a PermissionContext with the name specified, and the Permissions
	 * provided in the string, where the bit position of the permissions
	 * corresponds to the position of the permission name in the string array
	 */
	public static PermissionContext getPermissionContext(String name, String[] perms) 
	{
		PermissionContext permContext = new PermissionContextImpl(name);

		for (int i=0; i < perms.length ; i++)
			permContext.addPermissionBit(new PermissionBitImpl(perms[i]));

		return permContext;
	}

	/** 
	 * Returns a simple role named 'ROLE_ADMIN' with READ, UPDATE, CREATE, DISABLE permissions on
	 * ROLE entities
	 */
	public static Role getSimpleRole_RoleAdmin() 
	{
		PermissionContext ctx_role = getPermissionContext(CTX_ROLE, STANDARD_PERMS);

		Role role = new RoleImpl();
		role.setName(ROLE_ROLE_ADMIN);

		//String[] grant_perms = {PERM_READ, PERM_UPDATE, PERM_CREATE, PERM_DISABLE};
	
		//role.grantPermissions( ctx_role, ctx_role.getValue(grant_perms));
		role.grantPermissions( ctx_role, ctx_role.getValue(new String[]{PERM_READ, PERM_UPDATE, PERM_CREATE, PERM_DISABLE}) );

		return role;
	}

	/** 
	 * Returns a complex role named 'ACCOUNT_ADMIN' with READ, UPDATE, CREATE, DISABLE permissions on
	 * ACCOUNT entities, and also aggregates the role 'ROLE_ADMIN'
	 */
	public static Role getComplexRole_AccountAdmin()
	{
		PermissionContext ctx_account = getPermissionContext(CTX_ACCOUNT, STANDARD_PERMS);

		Role role = new RoleImpl();
		role.setName(ROLE_ACCOUNT_ADMIN);

		role.grantPermissions(ctx_account, ctx_account.getValue(new String[]{PERM_READ, PERM_UPDATE, PERM_CREATE, PERM_DISABLE}));
		role.getRoles().add(getSimpleRole_RoleAdmin());
		

		return role;
	}


} // end class MockObjectFactory

