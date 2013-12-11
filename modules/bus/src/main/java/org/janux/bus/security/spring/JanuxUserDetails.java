package org.janux.bus.security.spring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.janux.bus.security.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;


/**
 ***************************************************************************************************
 * Adapter class used to integrate Janux Security with Spring Security; uses
 * org.apache.commons.beanutils.BeanUtils to perform a shallow clone of an Account object; by
 * 'shallow' we mean that the primitives are cloned, and non-primitives are referenced in the new
 * object.
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.4.0 - 2012-03-20
 * @see org.apache.commons.beanutils.PropertyUtils#copyProperties(Object, Object)
 ***************************************************************************************************
 */
public class JanuxUserDetails extends AccountImpl implements UserDetails
{
	Logger log = LoggerFactory.getLogger(this.getClass());

	/** 
	 * Constructor that performs a shallow clone of an Account into a new JanuxUserDetails object
	 */
	public JanuxUserDetails(Account anAccount) 
	{
		try
		{ 
			PropertyUtils.copyProperties(this, anAccount);
		}
		catch (Exception e)
		{
			String msg = "Unable to create UserDetails object from Account object: " + e;
			log.error(msg, e);
			throw new RuntimeException(msg,e);
		}
	}

	/**
	 * Returns the role names that are directly assigned to the account, and does not include the sub-roles that
	 * may be aggregated by each role; the janux security authorization scheme does not generally use
	 * roles directly, and this method is mostly ceremonial. The method does not return all the
	 * sub-roles aggregated inside the directly assigned roles, because a role that aggregates other
	 * roles may at the same time deny a subset of the permissions granted to the aggregated roles,
	 * and in such case it would be misleading to say that the account has all of the sub-roles
	 * because it may not have all of the permissions granted to the sub-roles.
	 */
	public Collection<GrantedAuthority> getAuthorities() 
	{
		List<GrantedAuthority> grantedAuthoritiesList = new ArrayList<GrantedAuthority>();
		for(Role role : getRoles()){
			if (role != null) {
				GrantedAuthorityImpl grantedAuthorityImpl = new GrantedAuthorityImpl(role.getName());
				grantedAuthoritiesList.add(grantedAuthorityImpl);
			}
		}
		return grantedAuthoritiesList;
	}


} // end class


