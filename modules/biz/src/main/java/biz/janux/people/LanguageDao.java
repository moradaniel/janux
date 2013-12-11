package biz.janux.people;

import java.util.List;

import org.janux.bus.persistence.EntityNotFoundException;

/**
 * 
 * Used to create, save, retrieve, update and delete Language objects from
 * persistent storage
 *
 * @author  <a href="mailto:daniel.mora@janux.org">Daniel Mora</a>
 *
 */
public interface LanguageDao extends org.janux.bus.persistence.DataAccessObject
{
	/**
	 * Returns a list of all Languages in the system; the language objects returned should
	 * have at least their id, code and name fields initialized (retrieved from persistence) but need
	 * not be fully initialized; that is, the full object graph that includes the main language
	 * object and its associated associations may not have been fully retrieved.
	 */
	public List loadAll();

	/**
	 * Deep loads a Language object, or throws exception if Language with
	 * that code is not found
	 *
	 * @param code a business code that uniquely identifies this Language
	 * @return an object graph representing this Language and including associated objects
	 *
	 * @throws EntityNotFoundException if a Language object with that id is not found
	 */
	public Language loadByCode(String code) throws EntityNotFoundException;


	/**
	 * Returns a possibly lightweight representation of the corresponding Language, which may not
	 * contain all associated objects, or <code>null</code> if the Language is not found.
	 *
	 * @param code a business code that uniquely identifies this Language
	 */
	public Language findByCode(String code);


	/** 
	 * loads a Language object from persistence using its id; we include
	 * this for completeness, but we anticipate that most hotels will be loaded
	 * via a unique business code that is assigned to the hotel at the time that
	 * its record is first created in the database
	 *
	 * @throws EntityNotFoundException if a Language object with that id is not found
	 */
	public Language load(Integer id) throws EntityNotFoundException;

}
