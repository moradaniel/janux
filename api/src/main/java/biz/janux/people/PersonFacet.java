package biz.janux.people;

/**
 * Enumeration that defines the possible relations to a {@link Person}.
 * Using a set of facets we can specify which relations to load when 
 * retrieving a {@link Person} instance from database.
 * 
 * @author <a href="mailto:moradaniel@janux.org">Daniel Mora</a>
 *
 */
public enum PersonFacet {
	URLS,
	PHONE_NUMBERS,
	POSTAL_ADDRESSES,
	EMAIL_ADDRESSES
}

 
	
