package org.janux.bus.persistence;

import java.io.Serializable;

/***************************************************************************************************
*
* Interface for a Data Access Object that can be used for a single specified
* type domain object for write operations. 
* A single instance implementing this interface can be used
* only for the type of domain object specified in the type parameters.
* 
* 
* @param <T>
*            The type of the domain object for which this instance is to be
*            used.
* @param <ID>
*            The type of the id of the domain object for which this instance is
*            to be used.
*
* @author  <a href="mailto:moradaniel@janux.org">Daniel Mora</a>
***************************************************************************************************
*/

public interface GenericDaoWrite<T, ID extends Serializable>
{
	/** 
	 * Updates an already existing object, throws an exception if the object has not yet been saved
	 */
	
	public T update(T persistentObject) throws DataAccessException;
	
	/** Saves an object into persistent storage */
	public T save(T persistentObject)throws DataAccessException;

	/** 
	 * Used when it is desireable to save objects, whether or not they have been saved before or not;
	 * this method will first check whether the object already exists in storage, and will than call
	 * Save or Update, accordingly
	 */
	public T saveOrUpdate(T persistentObject) throws DataAccessException;

	/** deletes an object from persistent storage */
	public void delete(T persistentObject) throws DataAccessException;

	/** Retrieves an object from persistent storage and refreshes its state in memory */
	public void refresh(final T persistentObject) throws DataAccessException;

	/** 
	 * Copied from the Hibernate docs: Copy the state of the given object onto the persistent object
	 * with the same identifier. If there is no persistent instance currently associated with the
	 * session, it will be loaded. Return the persistent instance. If the given instance is unsaved,
	 * save a copy of and return it as a newly persistent instance. The given instance does not become
	 * associated with the session. The semantics of this method are defined by JSR-220.
	 */
	public T merge(final T persistentObject) throws DataAccessException;
	
	public void evict(final T persistentObject) throws DataAccessException;

	void flush();

	void clear();
	
	/**
	 * Reattach an object with the current session. 
	 * This will not execute a select to the database so the detached instance has to be unmodified.
	 * 
	 * @param persistentObject
	 * @throws DataAccessException
	 */
	public void attachClean(final T persistentObject) throws DataAccessException;
}
