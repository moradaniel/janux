package biz.janux.people;

import java.util.List;

import org.janux.bus.persistence.GenericDaoWithFacetsHibImpl;
import org.janux.bus.search.SearchCriteria;
import org.springframework.dao.DataAccessException;

/**
 * Used to create, save, retrieve, update and delete Person objects from persistent storage.
 * TODO - pp-20101103: need to properly define the 'initialize' method
 *
 * @author  <a href="mailto:philippe.paravicini@janux.biz">Philippe Paravicini</a>
 * @version $Id: PersonDaoHibImpl.java,v 1.6 2005-12-21 02:12:30 philippe Exp $
 *
 */
public class PersonDaoHibImplGeneric 
	extends GenericDaoWithFacetsHibImpl<PersonImpl, Integer, SearchCriteria, PersonFacet>
	implements PersonDaoGeneric<PersonImpl>
{
	public PersonImpl load(PersonImpl person, List<PersonFacet> facetSet) throws DataAccessException{
		/*if(facetSet.contains(PersonFacet.EMAIL_ADDRESSES)){
			Hibernate.initialize(person.getEmailAddresses());
		}
		if(facetSet.contains(PersonFacet.URLS)){
			Hibernate.initialize(person.getUrls());
		}
		if(facetSet.contains(PersonFacet.PHONE_NUMBERS)){
			Hibernate.initialize(person.getPhoneNumbers());
		}
		if(facetSet.contains(PersonFacet.POSTAL_ADDRESSES)){
			Hibernate.initialize(person.getPostalAddresses());
		}*/
		getHibernateTemplate().initialize(person.getContactMethods());
		return person;
	}


	public void initialize(PersonImpl person, PersonFacet facet) {
		// does nothing for now
	}


	/*private void initialize(Person person) 
	{
		Hibernate.initialize(person.getContactMethods());
	}*/
	
}
