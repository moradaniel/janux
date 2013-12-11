package org.janux.bus.persistence;

import java.io.Serializable;
import java.util.Set;

import org.janux.bus.search.SearchCriteria;

/**
 ***************************************************************************************************
 * A data access object that uses a variation of the Fly Weight Pattern to retrieve specific subsets
 * of an entity's object graph. In object oriented approaches, Business Entities are represented as
 * object graphs, and data access objects are tasked with retrieving and storing these object
 * graphs.  
 * <p>
 * For complex entities, these object graphs can be extensive, involve multiple
 * relationships, and entail a 'deep' retrieval from the storage mechanism, possibly one that is
 * performance intensive.  In certain contexts, it may be desirable to instantiate smaller object
 * graphs that represents only a sub-set of the entities' information.  
 * </p><p>
 * The pattern in this class defines the notion of a Facet as the various subsets of information
 * that represent a complex entity, and leaves it to the developer to define Enumeration of such
 * Facets, a Set of which can than be passed to the DAOs 'load' or 'find' method to determine at
 * runtime the extent of the object graph that should be retrieved.
 * </p>
 * 
 * @param <T>
 *            The type of the domain object for which this instance is to be
 *            used.
 * @param <ID>
 *            The type of the id of the domain object for which this instance is
 *            to be used.
 * @param <S>
 *            The type of criteria strategy used for searching objects.
 * @param <F>
 *            The type of facets to specify the relations to be initialized when 
 *            loading an object.
 *
 * @author  <a href="mailto:moradaniel@janux.org">Daniel Mora</a>
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.4
 ***************************************************************************************************
 */

public interface GenericDaoReadOnlyWithFacets<T, ID extends Serializable, S extends SearchCriteria, F extends Enum<F>> 
	extends GenericDaoReadOnly<T, ID, S>
{
	/** Configure the default facet set on load */
	public Set<F> getDefaultFacetSetOnLoad();
	
	/**
	 * TODO
	 * @param facetSet
	 */
	public void setDefaultFacetSetOnLoad(Set<F> facetSet);
	
	/**
	 * Loads an entity and initializes only the facets passed in the facet Set passed.
	 * 
	 * @param  id
	 * @param  facetSet
	 * @throws DataAccessException
	 */
	public T load(ID id, Set<F> facetSet) throws DataAccessException;

	/** 
	 * Explicitly initializes a specific facet (relationship) of an entity's object graph; the
	 * expectation is that this method will be called repeatedly in the 'load' method to initialize
	 * all the facets passed in the facet set.
	 */
	public void initialize(T entity, F facet);

	/** 
	 * Iterates over the provided facet set and initializes the entity accordingly
	 */
	public void initialize(T entity, Set<F> facetSet);
}
