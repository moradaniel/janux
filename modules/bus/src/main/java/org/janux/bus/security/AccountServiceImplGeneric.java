package org.janux.bus.security;

import java.util.SortedSet;

import org.janux.bus.persistence.EntityNotFoundException;

/**
 * @author  <a href="mailto:philippe.paravicini@janux.biz">Philippe Paravicini</a>
 * @see AccountService
 */
public class AccountServiceImplGeneric implements AccountService
{
	private AccountDaoGeneric<Account> accountDaoGeneric;
	private RoleDaoGeneric<Role> roleDaoGeneric;
	private PermissionContextDaoGeneric<PermissionContext> permissionContextDaoGeneric;


	public AccountServiceImplGeneric(AccountDaoGeneric accountDaoGeneric,RoleDaoGeneric roleDaoGeneric,PermissionContextDaoGeneric permissionContextDaoGeneric)
	{
		this.accountDaoGeneric = accountDaoGeneric;
		this.roleDaoGeneric = roleDaoGeneric;
		this.permissionContextDaoGeneric = permissionContextDaoGeneric;
	}


	public Account findAccountByName(String name)
	{
		return this.accountDaoGeneric.findByName(name);
	}


	public Account loadUserByUsername(String name)
	{
		return this.findAccountByName(name);
	}


	public Account loadAccountByName(String name) throws EntityNotFoundException
	{
		return this.accountDaoGeneric.loadByName(name);
	}


	public Account newAccount()
	{
		return this.accountDaoGeneric.newAccount();
	}

	public void saveAccount(final Account account)
	{
		accountDaoGeneric.save(account);
	}

	public void saveOrUpdateAccount(final Account account)
	{
		accountDaoGeneric.saveOrUpdate(account);
	}

	public void deleteAccount(final Account account)
	{
		accountDaoGeneric.delete(account);
	}
	
	public SortedSet<Account> loadAllAccounts(boolean initializeAccounts)
	{
		return this.accountDaoGeneric.loadAll(initializeAccounts);
	}

	public SortedSet<Role> loadAllRoles()
	{
		return this.roleDaoGeneric.loadAll();
	}


	public SortedSet<PermissionContext> loadAllPermissionContexts()
	{
		return this.permissionContextDaoGeneric.loadAll();
	}


	public Role findRoleByName(String name) {
		return this.roleDaoGeneric.findByName(name);
	}


	public Role loadRoleByName(String name) throws EntityNotFoundException {
		return this.roleDaoGeneric.loadByName(name);
	}

}
