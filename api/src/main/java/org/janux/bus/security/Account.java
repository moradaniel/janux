package org.janux.bus.security;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.janux.bus.persistence.Persistent;

/**
 ***************************************************************************************************
 * Represents a login account in the application, could also have been called a User or a Principal,
 * see more below. 
 * <p>
 * Note that what we call an Account here is what many applications call a 'User'.  We chose to use
 * the term 'Account' because we believe that the semantics of the term 'User' are closer to those
 * of a Person or Organization, which are represented by the biz.janux.people.Party interface and
 * its sub-interfaces and implementing classes.  If necessary, the Person or Organization that
 * 'owns' the Account can be referenced by the getParty field.  Thus, the user can be considered to
 * be the Party for whom the Account was created, although in many cases such a Party may not be
 * a Person or may not even exist (such as is the case for a 'system' account).
 * </p><p>
 * In an Authentication and Authorization context, another term for an Account is a Principal
 * representing an identity (the account name) and a set of credentials auntenticating that
 * identity (a password, or digital certificate).  We chose the term Account because we feel it is
 * most accessible to every day business people than the more technical term Principal.
 * </p>
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.1
 ***************************************************************************************************
 */
public interface Account extends Persistent, PermissionsCapable, Comparable
{
	/** A unique name that identifies this Account - returns the same value as the getUsername inherited frmo the UserDetails interface */
	String getName();
	void setName(String name);

	/** A password that can be used for password authentication */
	void setPassword(String password);

	/** indicates whether the account is valid and can be used */
	void setEnabled(boolean enabled);

	/** unlocks account */
	void setAccountNonLocked(boolean b);

	/** if not null, the date of expiration of the account */
	Date getExpiration();
	void setExpiration(Date date);

	/** if not null, the date of expiration of the password */
	Date getPasswordExpiration();
	void setPasswordExpiration(Date date);

	/** The Roles that have been granted to this Account */
	List<Role> getRoles();
	void setRoles(List<Role> roles);

	/** This Account's settings, if any */
	Set<AccountSetting> getSettings();
	void setSettings(Set<AccountSetting> settings);
}
