package org.janux.adapt;


/**
 ***************************************************************************************************
 * Defines methods for classes that can be extended via 'soft-coded' properties without changing the
 * class declaration, and, in particular, without the necessity to declare getter/setters for these
 * properties.  
 * <p>
 * The general concept at this time borrows from from loosely-typed languages such as
 * javascript, where a class and its properties can be represented as a map (associative array), and
 * accessed via the keys of such map.
 * </p>
 *
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.3
 ***************************************************************************************************
 */
public interface Extensible
{
	Object getAttribute(String key);
	void setAttribute(String key, Object value);
}


