package biz.janux.people;

import org.janux.bus.persistence.GenericDaoReadOnlyWithFacets;
import org.janux.bus.persistence.GenericDaoWrite;
import org.janux.bus.search.SearchCriteria;


/**
 * 
 * Used to create, save, retrieve, update and delete Party objects from
 * persistent storage
 *
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.4
 */

public interface PartyDaoGeneric<T extends Party> 
	extends GenericDaoWrite<T, Integer>, 
	GenericDaoReadOnlyWithFacets<T, Integer, SearchCriteria, PartyFacet>
{


}
