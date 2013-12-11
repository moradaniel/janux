package org.janux.bus.security;

import java.util.SortedSet;

import org.hibernate.NonUniqueObjectException;
import org.janux.bus.persistence.EntityNotFoundException;
import org.springframework.orm.hibernate3.HibernateSystemException;

/**
 * @author  <a href="mailto:philippe.paravicini@janux.biz">Philippe Paravicini</a>
 * @see AccountService
 */
public class AccountServiceImpl implements AccountService
{
	private AccountDao accountDao;
	private RoleDao roleDao;
	private PermissionContextDao permissionContextDao;


	public AccountServiceImpl(AccountDao accountDao,RoleDao roleDao,PermissionContextDao permissionContextDao)
	{
		this.accountDao = accountDao;
		this.roleDao = roleDao;
		this.permissionContextDao = permissionContextDao;
	}


	public Account findAccountByName(String name)
	{
		return this.accountDao.findByName(name);
	}


	public Account loadUserByUsername(String name)
	{
		return this.findAccountByName(name);
	}


	public Account loadAccountByName(String name) throws EntityNotFoundException
	{
		return this.accountDao.loadByName(name);
	}


	public Account newAccount()
	{
		return this.accountDao.newAccount();
	}

	public void saveAccount(final Account account)
	{
		accountDao.save(account);
	}


	/**
	 * Try to save an account. If the object is already in session
	 * then capture the exception and try a merge
	 */
	public void saveOrUpdateAccount(final Account account)
	{
		try{
			accountDao.saveOrUpdate(account);
		}
		catch(HibernateSystemException ex){
			if(ex.getCause() instanceof NonUniqueObjectException){
				accountDao.merge(account);
			}
		}
	}

	public void deleteAccount(final Account account)
	{
		accountDao.delete(account);
	}
	
	public SortedSet<Account> loadAllAccounts(boolean initializeAccounts)
	{
		return this.accountDao.loadAll(initializeAccounts);
	}

	public SortedSet<Role> loadAllRoles()
	{
		return this.roleDao.loadAll();
	}


	public SortedSet<PermissionContext> loadAllPermissionContexts()
	{
		return this.permissionContextDao.loadAll();
	}


	public Role findRoleByName(String name) {
		return this.roleDao.findByName(name);
	}


	public Role loadRoleByName(String name) throws EntityNotFoundException {
		return this.roleDao.loadByName(name);
	}

}
