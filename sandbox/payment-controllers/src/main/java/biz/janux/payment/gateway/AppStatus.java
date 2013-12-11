package biz.janux.payment.gateway;

import java.util.Properties;

/**
 ***************************************************************************************************
 * Convenience class used to declare a Properties object to hold application-scoped variables that
 * are shared by all components, can be accessed across components, and may possibly be changed
 * dynamically at run-time.  This class simply extends a java.util.Properties with static variables
 * representing the keys, and get/setProperties methods that make it possible to override the values
 * of this map using Spring's PropertyOverrideConfigurer
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.4.0 - since 2012-02-07
 ***************************************************************************************************
 */
public class AppStatus extends Properties
{
	/** Variable name used to store whether the app is initialized or not: "isInitialized" */
	public static final String IS_INITIALIZED = "isInitialized";

	/** 
	 * Variable name used for the name of the view to which a caller is redirected 
	 * if the app has not been initialized: "viewInitError" 
	 */
	public static final String VIEW_INIT_ERROR = "viewInitError";

	/** Delegates to parent class */
	public AppStatus() { super(); }

	/** Delegates to parent class */
	public AppStatus(Properties props) { super(props); }

	// private Properties properties = new Properties();

	/** 
	 * returns this object itself; this is a hack so that we can override the property values via a
	 * PropertyOverrideConfigurer which requires the format 'beanName.fieldName[key]', and does not
	 * allow a map to be overriden directly using something like 'beanName[key]'
	 */
  public Object getProperties() { return this;}

	/** Does nothing, simply here to fool the PropertyOverrideConfigurer */
  public void setProperties(Properties o) { /* do nothing */ }
}
