package biz.janux.people;

import org.janux.bus.persistence.GenericDaoReadOnlyWithFacets;
import org.janux.bus.persistence.GenericDaoWrite;
import org.janux.bus.search.SearchCriteria;

/**
 * 
 * Used to create, save, retrieve, update and delete Person objects from
 * persistent storage
 *
 * @author  <a href="mailto:philippe.paravicini@eCommerceStudio.com">Philippe Paravicini</a>
 * @version $Id: PersonDao.java,v 1.4 2006-03-21 23:44:00 dfairchild Exp $
 *
 */

public interface PersonDaoGeneric<T extends Person> 
	extends GenericDaoWrite<T, Integer>,
	GenericDaoReadOnlyWithFacets<T, Integer, SearchCriteria, PersonFacet>
{
						
	
}
