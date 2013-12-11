package biz.janux.geography;

import java.util.List;

import org.hibernate.Query;
import org.janux.bus.persistence.GenericDaoHibImpl;
import org.janux.bus.search.SearchCriteria;
import org.springframework.dao.DataAccessException;

/**
 ***************************************************************************************************
 * Dao used to read and write PostalAddress instances
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.4
 ***************************************************************************************************
 */
public class PostalAddressDaoHibImplGeneric 
	extends GenericDaoHibImpl<PostalAddressImpl, Integer, SearchCriteria> 
	implements PostalAddressDaoGeneric<PostalAddressImpl>
{
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
