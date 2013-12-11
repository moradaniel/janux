package biz.janux.people;

import java.util.List;

import org.hibernate.Hibernate;
import org.janux.bus.persistence.EntityNotFoundException;
import org.janux.bus.persistence.GenericDaoWithFacetsHibImpl;
import org.janux.bus.search.SearchCriteria;
import org.janux.util.Chronometer;
import org.springframework.dao.DataAccessException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Used to create, save, retrieve, update and delete Organization objects from persistent storage.
 * TODO - pp-20101103: need to properly define the 'initialize' method
 *
 * @author  <a href="mailto:philippe.paravicini@janux.biz">Philippe Paravicini</a>
 * @since 0.4
 */
public class OrganizationDaoHibImplGeneric 
	extends GenericDaoWithFacetsHibImpl<OrganizationImpl, Integer, SearchCriteria, OrganizationFacet> 
	implements OrganizationDaoGeneric<OrganizationImpl>
{
	Log log = LogFactory.getLog(this.getClass());

	public OrganizationImpl load(OrganizationImpl organization, List<OrganizationFacet> facetSet) throws DataAccessException{
		if(facetSet.contains(OrganizationFacet.CONTACT_METHODS)){
			Hibernate.initialize(organization.getContactMethods());
		}
		return organization;
	}
	
	public Organization findByCode(String code)
	{
		long begin = System.currentTimeMillis();

		if (log.isDebugEnabled()) log.debug("attempting to find Organization with code '" + code + "'");


		List list = getHibernateTemplate().find("from OrganizationImpl where code=?", code);

		Organization org = (list.size() > 0) ? (Organization)list.get(0) : null;

		if (org == null) {
			log.warn("Unable to find Organization with code: '" + code + "'");
			return null;
		}

		initialize(org);

		/*
		Hibernate.initialize(org.getEmailAddresses());
		Hibernate.initialize(org.getUrls());
		Hibernate.initialize(org.getPhoneNumbers());
		Hibernate.initialize(org.getPostalAddresses());
		*/

		if (log.isInfoEnabled()) log.info("successfully retrieved organization with code: '" + code + "' in " + (System.currentTimeMillis() - begin) + " ms" ); 

		return org;
	}


	public Organization loadByCode(String code) throws EntityNotFoundException
	{
		Organization org = this.findByCode(code);

		if (org == null) 
			throw new EntityNotFoundException("Unable to retrieve organization with code: '" + code + "'");

		return org;
	}


	public List loadAll()
	{
		Chronometer timer = new Chronometer(true);
		if (log.isDebugEnabled()) log.debug("attempting to load all organizations...");

		List list = getHibernateTemplate().loadAll(this.getPersistentClass());

		if (log.isInfoEnabled()) log.info("successfully retrieved all '" + list.size() + "' organizations in " + timer.printElapsedTime());

		return list;
	}


	/** actively loads (initializes) ContactMethod fields on a newly-retrieved Hotel object */
	private void initialize(Organization org) 
	{
		Hibernate.initialize(org.getContactMethods());
		/*
		Hibernate.initialize(org.getEmailAddresses());
		Hibernate.initialize(org.getUrls());
		Hibernate.initialize(org.getPhoneNumbers());
		Hibernate.initialize(org.getPostalAddresses());
		*/
	}

	public void initialize(OrganizationImpl org, OrganizationFacet facet) {
		throw new UnsupportedOperationException();
	}


}
