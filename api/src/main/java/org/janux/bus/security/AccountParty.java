package org.janux.bus.security;

import biz.janux.people.Party;

public interface AccountParty
{
	public Account getAccount();
	public void setAccount(Account account);
	
	
	public Party getParty();
	public void setParty(Party party);
}
