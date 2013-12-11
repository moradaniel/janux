package biz.janux.people;

import org.janux.bus.persistence.DataAccessException;



/**
 * 
 * Used to create, save, retrieve, update and delete Party objects from
 * persistent storage
 *
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version $Id: PartyDao.java,v 1.3 2006-02-18 03:05:46 philippe Exp $
 *
 * @deprecated use {@link PartyDaoGeneric}
 */
public interface PartyDao
{
	/** 
	 * loads a Party object from persistence using its id
	 *
	 * @throws DataAccessException if a Party object with that id is not found
	 */
	public Party load(Integer id) throws DataAccessException;

}
