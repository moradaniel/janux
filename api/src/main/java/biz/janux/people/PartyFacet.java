package biz.janux.people;

import biz.janux.people.Party;

/**
 * Enumeration that defines the possible relations to a {@link Party}.
 * Using a set of facets we can specify which relations to load when 
 * retrieving a {@link Party} instance from database.
 * 
 * @author <a href="mailto:moradaniel@janux.org">Daniel Mora</a>
 *
 */
public enum PartyFacet {
	CONTACT_METHODS
}
