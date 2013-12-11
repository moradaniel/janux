package org.janux.bus;


/**
 ***************************************************************************************************
 * This interface should be used in Classes / Entity Types that require a suitably random identifier
 * by which their objects/entity instances can be uniquely identified; this is mainly intended in
 * the cases when it is necessary or desireable to pass data between systems, and where it is
 * necessary to implement a very random non-null unique identifier.
 * <p>
 * This interface should generally not be used to implement a natural business key implementation
 * (such as 'SocialSecurityNumber', 'EmployerTaxId', etc...), since it is more likely that in such
 * cases one would define domain-specific subclasses with domain/industry specific business
 * identifiers.
 * </p><p>
 * The Uid string generation strategy that underlies implementations of this method should be
 * 'pluggable'.  We provide the following:
 * <code>None yet</code>
 * </p><p>
 * Other uid generators could be used, such as: 
 * </p>
 * <ul>
 * <li><b>uuid-java</b><br/>
 * one generated with java.util.UUID
 *
 * </li><li><b>uuid-hibernate</b><br/>
 * uses a 128-bit UUID algorithm to generate identifiers of type string, unique within a network
 * (the IP address is used). The UUID is encoded as a string of hexadecimal digits of length 32.
 * See the hibernate-3.2.5.ga manual, section '5.1.4.1. Generator'.
 *
 * </li><li><b>uuid-db</b><br/>
 * database-generated GUID string on MS SQL Server and MySQL.
 * </li></ul>
 *
 * </li><li><b>readable object locator</b><br/>
 * A 'pretty' string string locator that is easy to read, such as one generated using the
 * ostermiller-utils-1.05.jar library in janux.  This would be akin to the reservation confirmation
 * codes used in the travel industry.  
 *
 * </li><li><b>user-supplied</b><br/>
 * One supplied by an adopter of the janux project who wants to provide yet a different UUID
 * generation strategy.
 * </li></ul>
 * 
 * 
 * @author  
 * <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>,
 * <a href="mailto:dfairchild@janux.org">David Fairchild</a>
 *
 * @since 0.3.1
 ***************************************************************************************************
 */
public interface UniquelyIdentifiable
{
	String getUid();
	void setUid(String uid);
}
