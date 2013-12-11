package org.janux.bus.security;

import java.util.SortedSet;
import java.util.List;
import java.util.Iterator;

import org.janux.bus.persistence.EntityNotFoundException;
import org.janux.bus.test.TransactionalBusTestAbstract;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author   <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version  $Revision: 1.2 $ - $Date: 2006-08-29 21:02:28 $
 *
 * @see TransactionalTestAbstract
 */
public class PermissionContextDaoTest extends TransactionalBusTestAbstract
{
	Log log = LogFactory.getLog(this.getClass());

	protected PermissionContextDao permContextDao;

	private static final int NUM_PERM_CONTEXTS = 4;
	private static final String ACCOUNT = "CTX_ACCOUNT";
	private static final String ROLE    = "CTX_ROLE";
	private static final String HOLIDAY = "CTX_HOLIDAY";
	private static final String WORK    = "CTX_WORK";

	private static final int NUM_HOLIDAY_PERM_BITS = 3;
	private static final String HOL_DECLARE = "DECLARE";
	private static final String HOL_APPROVE = "APPROVE";
	private static final String HOL_TAKE    = "TAKE";

	public PermissionContextDaoTest() {
		super();
	}

	public PermissionContextDaoTest(String name) {
		super(name);
	}

	public void testLoadAll() 
	{
		SortedSet<PermissionContext> set = permContextDao.loadAll();
		assertNotNull(set);
		assertEquals("perm num", NUM_PERM_CONTEXTS, set.size());

		Iterator i = set.iterator();
		PermissionContext permContext;

		permContext = (PermissionContext)i.next();
		assertEquals(ACCOUNT, permContext.getName());

		permContext = (PermissionContext)i.next();
		assertEquals(ROLE, permContext.getName());

		permContext = (PermissionContext)i.next();
		assertEquals(HOLIDAY, permContext.getName());

		permContext = (PermissionContext)i.next();
		assertEquals(WORK, permContext.getName());
	}


	public void testLoadByName() 
	{
		PermissionContext permContext = permContextDao.loadByName(HOLIDAY);
		assertContext(permContext);

		try
		{ 
			permContextDao.loadByName("BOGUS");
			fail("Loading BOGUS permission context should have thrown EntityNotFoundException");
		}
		catch (EntityNotFoundException e) { 
			// expected behavior
		}
	}

	public void testFindByName() 
	{
		PermissionContext permContext = permContextDao.findByName(HOLIDAY);
		assertContext(permContext);

		assertNull(permContextDao.findByName("BOGUS"));
	}

	private void assertContext(PermissionContext permContext)
	{
		assertNotNull(permContext);
		assertEquals(HOLIDAY, permContext.getName());

		List<PermissionBit> bits = permContext.getPermissionBits();
		assertNotNull(bits);
		assertEquals("num " + HOLIDAY + " bits", NUM_HOLIDAY_PERM_BITS, bits.size());

		PermissionBit bit;

		bit = bits.get(0);
		assertEquals(HOL_DECLARE, bit.getName());
		assertEquals(HOL_DECLARE + " sort order", new Integer(1), bit.getSortOrder());
		assertEquals(bit, permContext.getPermissionBit(HOL_DECLARE));

		bit = bits.get(1);
		assertEquals(HOL_APPROVE, bit.getName());
		assertEquals(HOL_APPROVE + " sort order", new Integer(0), bit.getSortOrder());
		assertEquals(bit, permContext.getPermissionBit(HOL_APPROVE));

		bit = bits.get(2);
		assertEquals(HOL_TAKE, bit.getName());
		assertEquals(HOL_TAKE + " sort order", new Integer(2), bit.getSortOrder());
		assertEquals(bit, permContext.getPermissionBit(HOL_TAKE));
	}

} // end class PermissionDaoTest
