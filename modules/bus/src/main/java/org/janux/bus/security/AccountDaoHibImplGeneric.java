package org.janux.bus.security;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.janux.bus.persistence.EntityNotFoundException;
import org.janux.bus.persistence.GenericDaoWithFacetsHibImpl;
import org.janux.bus.search.SearchCriteria;
import org.janux.util.Chronometer;

/**
 * TODO - pp-20101103: this class was based on the deprecated AccountDaoHibImpl and is not complete; in
 * particular, an analysis should be made as to whether the Facets pattern is necessary for this
 * DAO, and, if so, the 'initialize' method of this class should be properly defined
 *
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @author  <a href="mailto:daniel.mora@janux.org">Daniel Mora</a>
 * @see AccountDaoGeneric
 * @since 0.4
 */
public class AccountDaoHibImplGeneric 
	extends GenericDaoWithFacetsHibImpl<AccountImpl, Integer, SearchCriteria, AccountFacet> 
	implements AccountDaoGeneric<AccountImpl>
{
	public Account findByName(String name)
	{
		Chronometer timer = new Chronometer(true);

		if (log.isDebugEnabled()) log.debug("attempting to find Account with name '" + name + "'");

		List list = getHibernateTemplate().find("from " + this.getPersistentClass().getSimpleName() + " where name=?", name);

		Account account = (list.size() > 0) ? (Account)list.get(0) : null;

		if (account == null) {
			log.warn("Unable to find Account with name: '" + name + "'");
			return null;
		}

		initializeAccount(account);

		if (log.isInfoEnabled()) log.info("successfully retrieved account '" + name + "' in " + timer.printElapsedTime() + " - " + account);

		return account;
	}


	public Account loadByName(String name) throws EntityNotFoundException
	{
		Account account = this.findByName(name);

		if (account == null) 
			throw new EntityNotFoundException("Unable to retrieve account with name: '" + name + "'");

		return account;
	}

	public SortedSet<Account> loadAll(boolean initializeAccounts)
	{
		Chronometer timer = new Chronometer(true);
		if (log.isDebugEnabled()) log.debug("attempting to load all accounts...");

		@SuppressWarnings("unchecked")
		List<AccountImpl> list = getHibernateTemplate().loadAll(this.getPersistentClass());
		SortedSet<Account> set = new TreeSet<Account>();

		for (Account account : list) {
			if(initializeAccounts){
				initializeAccount(account);
			}
			set.add(account);
		}

		if (log.isInfoEnabled()) log.info("successfully retrieved all " + set.size() + " accounts in " + timer.printElapsedTime());

		return set;
	}

	public Account newAccount()
	{
		return new AccountImpl();
	}


	/** 
	 * recursively iterates and retrieves all roles of this account, and has a mechanism to prevent a
	 * looping condition. 
	 * This method also removes any roles that are null as a result of a non-sequential sortOrder
	 * index: Hibernate returns nulls when a List index has a non-sequential index, for example as a
	 * result of removing a list record from the database manually without adjusting the index of
	 * subsequent list members.
	 */
	protected void initializeAccount(Account account) 
	{
		if (log.isDebugEnabled()) log.debug("Initializing Account " + account.getName() + " ...");
		Chronometer timer = new Chronometer(true);
		Set<Role> foundSet = new HashSet<Role>();

		int i=0;
		for (Iterator roles = account.getRoles().iterator(); roles.hasNext(); i++)
		{
			Role role = (Role)roles.next();

			// remove any null roles from the list; this can happen when using hibernate 
			// and there are 'gaps' in the index that keys the List, for example when manually removing
			// records from the table without adjusting the sortOrder index
			if (role == null) 
			{
				log.warn("Found Null Role in Account '" + account.getName() + "' at position: " + i + " - removing...");
				roles.remove();
				continue;
			}
			this.initializeAggrRoles(role, foundSet);
		} // end for

		if (log.isDebugEnabled()) log.debug("Initialized Account " + account.getName() + " in " + timer.printElapsedTime());
	}

	public void initialize(AccountImpl account, AccountFacet facet) {
		throw new UnsupportedOperationException();
	}



	/** 
	 * recursively iterates and retrieves all sub-roles of this role, 
	 * and has a mechanism to prevent a looping condition.
	 * This method also removes any aggregated roles that are null as a result of a non-sequential
	 * sortOrder index: Hibernate returns nulls when a List index has a non-sequential index, for
	 * example as a result of removing a list record from the database manually without adjusting the
	 * index of subsequent list members.
	 */
	private void initializeAggrRoles(Role role, Set<Role> foundSet)
	{
		if (role == null) return;
		if (log.isDebugEnabled()) log.debug("initializing roles aggregated by Role " + role.getName());
		foundSet = (foundSet != null) ? foundSet : new HashSet<Role>();

		// add the current role to the found set to prevent a loop in the event
		// that we encounter this role in one of the recursive calls
		foundSet.add(role);

		int i=0;
		for (Iterator roles = role.getRoles().iterator(); roles.hasNext(); i++)
		{
			Role aggrRole = (Role)roles.next();

			if (aggrRole == null) {
				log.warn("Found Null sub-Role in Role '" + role.getName() + "' at position: " + i + " - removing...");
				roles.remove();
				continue;
			}

			if (foundSet.contains(aggrRole)) {
				if (log.isDebugEnabled()) log.debug("aggrRole " + aggrRole.getName() + " has already been initialized - skipping");
				continue;
			}
			else {
				this.initializeAggrRoles(aggrRole, foundSet);
			}
		}
	}

}
