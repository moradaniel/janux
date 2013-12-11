package biz.janux.geography;

import java.util.Map;

import org.janux.bus.persistence.GenericDaoReadOnly;
import org.janux.bus.persistence.GenericDaoWrite;
import org.janux.bus.search.SearchCriteria;

/**
 ***************************************************************************************************
 * Performs commonly requested operations on Country objects
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.4
 ***************************************************************************************************
 */
public interface CountryDaoGeneric<T extends Country> 
	extends GenericDaoWrite<T, Integer>,
	GenericDaoReadOnly<T, Integer, SearchCriteria>
{

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
