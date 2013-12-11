package biz.janux.payment.mock;

/**
 ***************************************************************************************************
 * This is a simple username/password string tuple currently used for demonstrating how to unencrypt
 * credentials at initialization time; it may be desirable to eventually integrate and develop this
 * concept as an interface inside the org.janux.security package.
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.4.01 - 2012-04-26
 ***************************************************************************************************
 */
public class Credential
{
	private String username;
	private String password;

	/** A username */
  public String getUsername() { return this.username;}
  public void setUsername(String n) { this.username = n; }


	/** A password */
  public String getPassword() { return this.password;}
  public void setPassword(String n) { this.password = n; }

} // end class Credential
