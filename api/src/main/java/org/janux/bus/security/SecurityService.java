package org.janux.bus.security;

import biz.janux.people.Party;

public interface SecurityService {
	
	public Account findByParty(Integer partyId);
	
	public Party findByAccount(Integer accountId);
	
	//public List<AccountParty> find(SearchCriteria search);
	
	//public int count(SearchCriteria search);
}
