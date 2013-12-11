package org.janux.help;

import java.util.List;

import org.janux.bus.persistence.DataAccessObject;
import org.janux.bus.persistence.EntityNotFoundException;


public interface HelpCategoryDao extends DataAccessObject
{
	/** 
	 * loads a HelpCategory object from persistence using its id
	 *
	 * @param id the internal identifier of the HelpCategory
	 * @throws EntityNotFoundException if a HelpEntry object with that id is not found
	 */
	public HelpCategory load(Integer id) throws EntityNotFoundException;

	public HelpCategory newHelpCategory(String title);
	
	public List<HelpCategory> loadAll();
	
	public HelpCategory findByTitle(String title);

	public void delete(HelpCategory entry);

}
