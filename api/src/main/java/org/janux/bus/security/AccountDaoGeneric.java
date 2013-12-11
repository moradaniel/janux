package org.janux.bus.security;

import java.util.SortedSet;

import org.janux.bus.persistence.EntityNotFoundException;
import org.janux.bus.persistence.GenericDaoReadOnlyWithFacets;
import org.janux.bus.persistence.GenericDaoWrite;
import org.janux.bus.search.SearchCriteria;

/**
 * Used to create, save, retrieve, update and delete Account objects from
 * persistent storage
 *
 * @author  <a href="mailto:philippe@innpoints.com">Philippe Paravicini</a>
 */

public interface AccountDaoGeneric<T extends Account> 
	extends GenericDaoWrite<T, Integer>,
	GenericDaoReadOnlyWithFacets<T, Integer, SearchCriteria, AccountFacet>
{
	/**
	 * Returns an Account by names, or <code>null</code> if the Account is not found.
	 *
	 * @param name the Account name
	 */
	public Account findByName(String name);


	/**
	 * loads an Account object, or throws exception if Account with that name is not found
	 *
	 * @param name a name that uniquely identifies this Account
	 *
	 * @throws EntityNotFoundException if a Account object with that name is not found
	 */
	public Account loadByName(String name) throws EntityNotFoundException;

	
	/** Loads all Accounts defined in the system */
	public SortedSet<Account> loadAll(boolean initializeAccounts);

	
	/** returns a new Account instance */
	public Account newAccount();
}
