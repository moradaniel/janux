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
 * @version $Revision: 1.5 $ - $Date: 2006-10-20 16:28:18 $
 * @since 2005.11.14
 ***************************************************************************************************
 */

public interface Extensible
{
	public CompositeProperty getPropertyMap();
}
