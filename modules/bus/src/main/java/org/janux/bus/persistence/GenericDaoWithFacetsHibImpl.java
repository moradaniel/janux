package org.janux.bus.persistence;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Set;

import org.janux.bus.search.SearchCriteria;
import org.springframework.dao.DataAccessException;

import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;

/**
 * Extends GenericDaoHibImpl with load method that can be parametrized with a set of Facets
 * 
 * @author  <a href="mailto:moradaniel@janux.org">Daniel Mora</a>
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.4
 */
public abstract class GenericDaoWithFacetsHibImpl<T, ID extends Serializable, S extends SearchCriteria, F extends Enum<F>> 
	extends GenericDaoHibImpl<T, ID, S>
	implements GenericDaoReadOnlyWithFacets<T, ID, S, F>
{
	Set<F> defaultFacetSetOnLoad;
	
	@SuppressWarnings("unchecked")
	public GenericDaoWithFacetsHibImpl() {
		super();
	}
	

	@SuppressWarnings("unchecked")
	public T load(ID id) throws DataAccessException
	{
		if (log.isDebugEnabled()) log.debug("attempting to retrieve "+this.persistentClass.getSimpleName()+" with id '" + id + "'");
		return this.load(id, this.getDefaultFacetSetOnLoad());
	}
	

	public T load(ID id, Set<F> facetSet) throws DataAccessException
	{
		if (log.isDebugEnabled()) log.debug("attempting to retrieve "+this.persistentClass.getSimpleName()+" with id '" + id + "'");

		T object = (T)getHibernateTemplate().get(this.persistentClass, id);

		if (facetSet!=null)
		{
			for ( F facet : facetSet ) {
				if (log.isDebugEnabled()) log.debug("initializing '" + facet + "' of "+this.persistentClass.getSimpleName()+ " witd id '" + id + "'");
				this.initialize(object, facet);
			}
		}

		if (log.isDebugEnabled()) log.info("Successfully retrieved: " + this.persistentClass.getSimpleName()+" object with id: '" +id + "'");

		return object;
	}

	public abstract void initialize(T entity, F facet);

	/** 
	 * Default template method implementation that iterates over the facet set and
	 * calls {@link #initialize(Object, Enum)}; override this method in a
	 * specific DAO if performance is a concern and there exists a more efficient
	 * manner to initialize multiple facets/relationships at once
	 */
	public void initialize(T entity, Set<F> facetSet)
	{
		for ( F facet : facetSet ) 
		{
			this.initialize(entity, facet);
		}
	}

	public Set<F> getDefaultFacetSetOnLoad(){
		return defaultFacetSetOnLoad;
	}
	
	public void setDefaultFacetSetOnLoad(Set<F> facetSet){
		this.defaultFacetSetOnLoad=facetSet;
	}

}
