package biz.janux.geography;

import java.util.List;

import org.hibernate.Query;
import org.janux.bus.persistence.DataAccessHibImplAbstract;
import org.janux.bus.persistence.EntityNotFoundException;
import org.janux.util.Chronometer;
import org.springframework.dao.DataAccessException;

/**
 * 
 * @deprecated use {@link PostalAddressDaoGeneric}
 *
 */

public class PostalAddressDaoHibImpl extends DataAccessHibImplAbstract implements PostalAddressDao
{

	public PostalAddress load(Integer id) throws EntityNotFoundException
	{
		Chronometer timer = new Chronometer();

		if (log.isDebugEnabled()) log.debug("attempting to load PostalAddress with id '" + id + "'");

		PostalAddress address;

		try {
			address = (PostalAddress)getHibernateTemplate().load(PostalAddressImpl.class, id);
		} 
		catch (DataAccessException e) {
			String msg = "Unable to load PostalAddress with id " + id;
			log.error(msg, e);
			throw new EntityNotFoundException(msg,e);
		}

		if (log.isInfoEnabled()) log.info("successfully retrieved: " + address + " in " + timer.printElapsedTime());

		return address;
	}


	public int countByCountryAsString()
	{
		List l = getHibernateTemplate().find("SELECT COUNT(address) FROM biz.janux.geography.PostalAddress address WHERE address.countryAsString is not null");
		return ((Long)firstResult(l)).intValue();
	}


	public List<PostalAddress> findByCountryAsString()
	{
		return this.findByCountryAsString(null, null);
	}


	@SuppressWarnings("unchecked")
	public List<PostalAddress> findByCountryAsString(final Integer numRecords, final Integer offset) 
	{
		Query query = this.getSession().createQuery("FROM biz.janux.geography.PostalAddress as address WHERE address.countryAsString is not null ORDER BY address.id");
		if (numRecords instanceof Integer) query.setMaxResults(numRecords);
		if (offset instanceof Integer) query.setFirstResult(offset);

		return (List<PostalAddress>)query.list();
	}


	public int countByCountryAsString(String country) 
	{
		Object[] parms = {country};
		List l = getHibernateTemplate().find("SELECT COUNT(address) FROM biz.janux.geography.PostalAddress address WHERE address.countryAsString=?", parms);
		return ((Long)firstResult(l)).intValue();
	}


	@SuppressWarnings("unchecked")
	public List<PostalAddress> findByCountryAsString(final String country, final Integer numRecords, final Integer offset) 
	{
		Query query = this.getSession().createQuery("FROM biz.janux.geography.PostalAddress as address WHERE address.countryAsString = ? ORDER BY address.id").setString(0, country);
		if (numRecords instanceof Integer) query.setMaxResults(numRecords);
		if (offset instanceof Integer) query.setFirstResult(offset);

		return (List<PostalAddress>)query.list();
		// we could also use a HibernateCallback to do this - I suppose that these
		// are no longer necessary since in hibernate3 hibernate exceptions are unchecked
		/*
		return (List<PostalAddress>)this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session hibSession) throws HibernateException 
			{
				Object[] params = {country};
				Query query = hibSession.createQuery("SELECT from PostalAddress address WHERE address.country = ?").setString(0, country);
				if (numRecords instanceof Integer) query.setMaxResults(numRecords);
				if (offset instanceof Integer) query.setMaxResults(numRecords);

				return query.list();
			}
		});
		*/
	}

	public List<PostalAddress> findByCountryAsString(String country)
	{
		return this.findByCountryAsString(country, null, null);
	}

}
