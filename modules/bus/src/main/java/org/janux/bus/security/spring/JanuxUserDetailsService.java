package org.janux.bus.security.spring;

import org.janux.bus.security.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 ***************************************************************************************************
 * Adapter class that implements a spring security UserDetailsService for authentication; implements
 * the single UserDetailsService method by delegating to 
 * {@link AccountDaoGeneric#findByName(String username)}
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.4.0 - 2012-03-28
 ***************************************************************************************************
 */
public class JanuxUserDetailsService implements UserDetailsService
{
	private AccountDaoGeneric<Account> accountDao;

	/** The account dao to which this UserDetailsService delegates */
  public AccountDaoGeneric<Account> getAccountDao() { return accountDao;}
  public void setAccountDao(AccountDaoGeneric<Account> o) { accountDao = o; }

	public UserDetails loadUserByUsername(String username) 
	{
		Account account = this.getAccountDao().findByName(username);
		if (account != null) {
			return new JanuxUserDetails(account);
		}
		else {
			return null;
		}
	}

} // end class JanuxUserDetailsService
