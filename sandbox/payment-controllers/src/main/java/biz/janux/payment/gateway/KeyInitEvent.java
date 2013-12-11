package biz.janux.payment.gateway;

import org.springframework.context.ApplicationEvent;


/**
 ***************************************************************************************************
 * In order for the payment-gateway to become operational, it must be initialized with the Password
 * Encryption Key that can be used to decrypt the credentials to the encrypted back-end stoarge
 * engine; this event is triggered when the Password Encryption Key is successfully provided
 * (presumably via the web ui), and contains the key; the key should not be logged, nor should it
 * be kept in memory after the necessary credentials are decrypted, since it is no longer needed at
 * that point.
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.4.0
 ***************************************************************************************************
 */
public class KeyInitEvent extends ApplicationEvent
{
	/** plain vanilla constructor that calls the parent's constructor */
	public KeyInitEvent(Object aKey) {
		super(aKey);
	}

	/** the Password Encryption Key necessary to initialize the payment gateway */
	public String getKey() {
		return (String)this.getSource();
	}

} // end class
