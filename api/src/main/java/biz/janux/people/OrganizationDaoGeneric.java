package biz.janux.people;

import java.util.List;

import org.janux.bus.persistence.EntityNotFoundException;
import org.janux.bus.persistence.GenericDaoReadOnlyWithFacets;
import org.janux.bus.persistence.GenericDaoWrite;
import org.janux.bus.search.SearchCriteria;

/**
 * 
 * Used to create, save, retrieve, update and delete Organization objects from
 * persistent storage
 *
 * @author  <a href="mailto:philippe.paravicini@eCommerceStudio.com">Philippe Paravicini</a>
 * @version $Id: OrganizationDao.java,v 1.7 2006-05-06 02:00:21 dfairchild Exp $
 *
 */

public interface OrganizationDaoGeneric<T extends Organization> 
	extends GenericDaoWrite<T, Integer>,
	GenericDaoReadOnlyWithFacets<T, Integer, SearchCriteria, OrganizationFacet>
{
	/**
	 * Returns a list of all Organizations in the system; the organization objects returned should
	 * have at least their id, code and name fields initialized (retrieved from persistence) but need
	 * not be fully initialized; that is, the full object graph that includes the main organization
	 * object and its associated associations (Contact Methods, associations, etc....) may not have
	 * been fully retrieved.
	 */
	public List loadAll();

	/**
	 * Deep loads an Organization object, or throws exception if Organization with
	 * that code is not found
	 *
	 * @param code a business code that uniquely identifies this Organization
	 * @return an object graph representing this Organization and including associated objects
	 *
	 * @throws EntityNotFoundException if a Organization object with that id is not found
	 */
	public Organization loadByCode(String code) throws EntityNotFoundException;


	/**
	 * Returns a possibly lightweight representation of the corresponding Organization, which may not
	 * contain all associated objects, or <code>null</code> if the Organization is not found.
	 *
	 * @param code a business code that uniquely identifies this Organization
	 */
	public Organization findByCode(String code);


}
