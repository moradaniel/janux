package org.janux.bus.security;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.extensions.TestSetup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** @author  <a href="mailto:philippe.paravicini@eCommerceStudio.com">Philippe Paravicini</a> */
public class PermissionContextTest extends TestCase
{
	Log log = LogFactory.getLog(this.getClass());

	PermissionContext permContext;

	final static String CTX_ACCOUNT = "ACCOUNT";

	/** executed prior to each test */
	protected void setUp() { 
		permContext = MockObjectFactory.getPermissionContext(CTX_ACCOUNT);
	}


	public void testPermissionContext()
	{
		String[] perms = MockObjectFactory.STANDARD_PERMS;

		assertEquals(CTX_ACCOUNT, permContext.getName());
		assertEquals(perms.length, permContext.getPermissionBits().size());

		for (int i=0; i < perms.length; i++)
		{
			PermissionBit permBit = permContext.getPermissionBit(perms[i]);
			assertEquals(perms[i], permBit.getName());
			assertEquals(i, permBit.getPosition());
		} // end for

		assertEquals( 1, permContext.getPermissionBit(perms[0]).getValue());
		assertEquals( 2, permContext.getPermissionBit(perms[1]).getValue());
		assertEquals( 4, permContext.getPermissionBit(perms[2]).getValue());
		assertEquals( 8, permContext.getPermissionBit(perms[3]).getValue());
		assertEquals(16, permContext.getPermissionBit(perms[4]).getValue());
	}

	public void testPermissionBit()
	{
		String PERM_NAME = "DESTROY";
		PermissionBit permBit = new PermissionBitImpl(PERM_NAME);

		// A permission bit that has not been added to a PermissionContext should have a position of -1
		assertEquals( PERM_NAME, permBit.getName());
		assertEquals( -1, permBit.getPosition());
		 
		// try some things that should not be tried
		
		try
		{ 
			permBit = new PermissionBitImpl(null);
			fail("Attempting to create a PermissionBit with a null name should have failed !");
		}
		catch (Exception e)
		{
			// we're good
		}

		String ALL_WHITE_SPACE = "  	";

		try
		{ 
			permBit = new PermissionBitImpl(ALL_WHITE_SPACE);
			fail("Attempting to create a PermissionBit with a name with all white space should have failed !");
		}
		catch (Exception e)
		{
			// we're good
		}

	}


	public void testAddPermissionBit() 
	{
		// add a new PermissionBit to a PermissionContext
		String PERM_ARCHIVE = "ARCHIVE";
		permContext.addPermissionBit(new PermissionBitImpl(PERM_ARCHIVE));

		// verify the PermissionContext after the addition
		int NUM_BITS = MockObjectFactory.STANDARD_PERMS.length + 1;
		PermissionBit permBit = permContext.getPermissionBit(PERM_ARCHIVE);

		assertEquals(NUM_BITS, permContext.getPermissionBits().size());
		assertNotNull(permBit);
		assertEquals(PERM_ARCHIVE, permBit.getName());
		assertEquals(NUM_BITS - 1, permBit.getPosition());
		assertEquals(permContext, permBit.getPermissionContext());

		// attempt to add a PermissionBit with a duplicate name
		try
		{ 
			permContext.addPermissionBit(new PermissionBitImpl(PERM_ARCHIVE));
			fail("Attempting to add a PermissionBit under a name that already exists should have failed !");
		}
		catch (Exception e) {
			// all is well !
		}

		// re-verify the PermissionContext after the failed duplicate insert
		permBit = permContext.getPermissionBit(PERM_ARCHIVE);

		assertEquals(NUM_BITS, permContext.getPermissionBits().size());
		assertNotNull(permBit);
		assertEquals(PERM_ARCHIVE, permBit.getName());
		assertEquals(NUM_BITS - 1, permBit.getPosition());
		assertEquals(permContext, permBit.getPermissionContext());
	}

} // end class PermissionContextTest

