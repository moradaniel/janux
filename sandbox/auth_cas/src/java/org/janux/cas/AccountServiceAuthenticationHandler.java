package org.janux.cas;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.janux.bus.security.AccountService;
import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.handler.AuthenticationHandler;
import org.jasig.cas.authentication.handler.UnknownUsernameAuthenticationException;
import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;


/**
 * CAS AuthenticationHandler that allows to authenticate against the janux {@link AccountService}
 *
 * @author  <a href="mailto:moradaniel@janux.org">Daniel Mora</a>
 */
public class AccountServiceAuthenticationHandler implements AuthenticationHandler {
	
	/** Class logger */
	private static final Log log = LogFactory.getLog(AccountServiceAuthenticationHandler.class);
	
	
	private UserDetailsService userDetailsService;
	

	public boolean authenticate(Credentials credentials) throws AuthenticationException {
		
		UsernamePasswordCredentials usernameAndPassword = (UsernamePasswordCredentials) credentials;
		
		UserDetails userDetails = getUserDetailsService().loadUserByUsername(usernameAndPassword.getUsername());
		
		if(userDetails!=null){
			if(userDetails.getPassword().equals(usernameAndPassword.getPassword())){
				return true;
			}
		}else{
			log.warn("Couldn't find any user with username [" + usernameAndPassword.getUsername() + "]"); 
			throw new UnknownUsernameAuthenticationException("error.authentication.credentials.bad");

		}
		
		return false;
	}

	public boolean supports(Credentials credentials) {
		if (credentials instanceof UsernamePasswordCredentials) {
			return true;
		} else {
			return false;
		}
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
}
