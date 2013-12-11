package biz.janux.people;

import org.janux.bus.persistence.DataAccessException;
import org.janux.bus.persistence.DataAccessObject;


/**
 * 
 * Used to create, save, retrieve, update and delete Person objects from
 * persistent storage
 *
 * @author  <a href="mailto:philippe.paravicini@eCommerceStudio.com">Philippe Paravicini</a>
 * @version $Id: PersonDao.java,v 1.4 2006-03-21 23:44:00 dfairchild Exp $
 *
 * @deprecated use {@link PersonDaoGeneric}
 */
public interface PersonDao extends DataAccessObject
{
	/** 
	 * loads a Person object from persistence using its id
	 *
	 * @throws DataAccessException if a Person object with that id is not found
	 */
	public Person load(Integer id) throws DataAccessException;
	
}
