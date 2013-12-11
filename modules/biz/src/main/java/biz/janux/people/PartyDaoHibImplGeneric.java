package biz.janux.people;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.janux.bus.persistence.GenericDaoWithFacetsHibImpl;
import org.janux.bus.search.SearchCriteria;
import org.springframework.dao.DataAccessException;

/**
 * Used to create, save, retrieve, update and delete Party objects from persistent storage.
 * TODO - pp-20101103: need to properly define the 'initialize' method
 *
 *
 * @author  <a href="mailto:philippe.paravicini@janux.biz">Philippe Paravicini</a>
 * @since 0.4
 */

public class PartyDaoHibImplGeneric 
	extends GenericDaoWithFacetsHibImpl<Party, Integer, SearchCriteria, PartyFacet> 
	implements PartyDaoGeneric<Party>
{
	public Party load(Party party, List<PartyFacet> facetSet) throws DataAccessException{
		if(facetSet!=null && facetSet.contains(PartyFacet.CONTACT_METHODS)){
			Hibernate.initialize(party.getContactMethods());
			
			final Iterator it = party.getContactMethods().values().iterator();
			while (it.hasNext())
			{
				Object obj = it.next();
				if (obj instanceof ContactMethod) 
				{
					final ContactMethod cm = (ContactMethod ) obj;
					Hibernate.initialize(cm);
				}
			}
		}
		return party;
	}
	
	public void initialize(Party party, PartyFacet facet) {
		throw new UnsupportedOperationException();
	}


/*	private void initialize(Party party) 
	{
		Hibernate.initialize(party.getContactMethods());
		
		final Iterator it = party.getContactMethods().values().iterator();
		while (it.hasNext())
		{
			Object obj = it.next();
			if (obj instanceof ContactMethod) 
			{
				final ContactMethod cm = (ContactMethod ) obj;
				Hibernate.initialize(cm);
			}
		}
		
		
	}*/
}
