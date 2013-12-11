package biz.janux.geography;

import java.util.Map;

import org.janux.bus.persistence.EntityNotFoundException;
import org.janux.bus.persistence.DataAccessObject;


/**
 ***************************************************************************************************
 * Performs commonly requested operations on Country objects
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version $Revision: 1.3 $ - $Date: 2006-07-17 05:18:23 $
 * @since 2006-02-17
 ***************************************************************************************************
 *
 * @deprecated use {@link CountryDaoGeneric}
 */
public interface CountryDao extends DataAccessObject
{
	/** loads a Country object from persistence using its id */
	Country load(Integer id) throws EntityNotFoundException;

	/**
	 * Returns a map of all Countries in the system, keyed by ISO code; 
	 * the keys of the map may be ordered by the implementation 
	 * (for example according to Country.getSortOrder())
	 */
	Map<String, Country> loadAll();

	/**
	 * retrieves a Country using a standard ISO code, or returns
	 * <code>null</code> if a Country with that code is not found 
	 */
	Country findByCode(String code);

	/** 
	 * attempts to find a Country with the name provided 
	 * TODO: this method needs to be internationalized
	 */
	Country findByName(String code);

}
