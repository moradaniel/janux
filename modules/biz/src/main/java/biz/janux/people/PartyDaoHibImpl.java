package biz.janux.people;

import java.util.Iterator;

import org.springframework.dao.DataAccessException;
import org.janux.bus.persistence.DataAccessHibImplAbstract;
import org.janux.bus.persistence.GenericDaoHibImpl;

import org.hibernate.Hibernate;

/**
 * 
 * Used to create, save, retrieve, update and delete Party objects from
 * persistent storage
 *
 * @author  <a href="mailto:philippe.paravicini@janux.biz">Philippe Paravicini</a>
 * @version $Id: PartyDaoHibImpl.java,v 1.6 2006-04-17 21:49:14 dfairchild Exp $
 *
 * @deprecated use {@link GenericDaoHibImpl}
 */
public class PartyDaoHibImpl extends DataAccessHibImplAbstract implements PartyDao 
{
	/* 
	 * deep loads a Party object from persistence using its id, this includes
	 * all the ContactMethods
	 */
	public Party load(Integer id) throws DataAccessException
	{
		if (log.isDebugEnabled()) log.debug("attempting to retrieve Party with id '" + id + "'");

		Party party = (Party)getHibernateTemplate().load(Party.class, id);

		initialize(party);
		/*
		Hibernate.initialize(party.getEmailAddresses());
		Hibernate.initialize(party.getUrls());
		Hibernate.initialize(party.getPhoneNumbers());
		Hibernate.initialize(party.getPostalAddresses());
		*/

		if (party != null)
			if (log.isInfoEnabled()) log.info("successfully retrieved: " + party); 
		else
			log.warn("unable to find Party with id: '" + id + "'");

		return party;
	}

	private void initialize(Party party) 
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
		} // end for
		
		
	}
}
