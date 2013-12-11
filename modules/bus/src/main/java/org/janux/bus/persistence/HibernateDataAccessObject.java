package org.janux.bus.persistence;

import java.io.Serializable;

import org.springframework.dao.DataAccessException;

import org.springframework.orm.hibernate3.HibernateTemplate;

import org.hibernate.Session;
import org.janux.bus.security.AccountDaoGeneric;

/**
 ***************************************************************************************************
 * This interface contains convenience methods that are Hibernate specific; they save the trouble of
 * calling getHibernateTemplate(), and it provides a non-protected accessor for the current Hibernate
 * Session, which can be useful when troubleshooting Hibernate issues
 *
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.2.05
 ***************************************************************************************************
 * @deprecated use {@link HibernateDataAccessObjectGeneric}
 */
public interface HibernateDataAccessObject extends DataAccessObject
{
	void evict(final Object persistentObject) throws DataAccessException;

	void flush();

	void clear();

	HibernateTemplate getHibernateTemplate();

	Session getHibernateSession();

	/**
	 * Reattach an object with the current session. 
	 * This will not execute a select to the database so the detached instance has to be unmodified.
	 * 
	 * @param persistentObject
	 * @throws DataAccessException
	 */
	public void attachClean(final Object persistentObject) throws DataAccessException;
}

