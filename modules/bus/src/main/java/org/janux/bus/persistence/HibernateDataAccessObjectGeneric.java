package org.janux.bus.persistence;

import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 ***************************************************************************************************
 * This interface contains convenience methods that are Hibernate specific; they save the trouble of
 * calling getHibernateTemplate(), and it provides a non-protected accessor for the current Hibernate
 * Session, which can be useful when troubleshooting Hibernate issues
 *
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.2.05
 ***************************************************************************************************
 */
public interface HibernateDataAccessObjectGeneric
{

	HibernateTemplate getHibernateTemplate();

	Session getHibernateSession();
	
}

