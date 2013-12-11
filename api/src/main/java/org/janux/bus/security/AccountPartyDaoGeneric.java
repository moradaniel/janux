package org.janux.bus.security;

import java.util.SortedSet;

import org.janux.bus.persistence.GenericDaoReadOnlyWithFacets;
import org.janux.bus.persistence.GenericDaoWrite;
import org.janux.bus.search.SearchCriteria;

import biz.janux.people.Party;

public interface AccountPartyDaoGeneric<T extends AccountParty> 
	extends GenericDaoWrite<T, Integer>,
	GenericDaoReadOnlyWithFacets<T, Integer, SearchCriteria, AccountPartyFacet>
{
	
	/** Loads all AccountParties defined in the system */
	public SortedSet<AccountParty> loadAll();
	
	public Account getAccount(Integer partyId);
	
	public Party getParty(Integer accountId);

}
