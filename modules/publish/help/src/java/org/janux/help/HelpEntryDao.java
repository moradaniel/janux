package org.janux.help;

import java.util.List;

import org.janux.bus.persistence.DataAccessObject;
import org.janux.bus.persistence.EntityNotFoundException;


public interface HelpEntryDao extends DataAccessObject
{
	/** 
	 * loads a HelpEntry object from persistence using its id
	 *
	 * @param id the internal identifier of the HelpEntry
	 * @throws EntityNotFoundException if a HelpEntry object with that id is not found
	 */
	public HelpEntry load(Integer id) throws EntityNotFoundException;
	public HelpEntry loadByCode(String code) throws EntityNotFoundException;

	public HelpEntry findByCode(String code);
	public HelpEntry findByLabel(String label);

	public HelpEntry newHelpEntry(String code,String label,HelpCategory category);
	
	public List<HelpEntry> loadAll();

	public void delete(HelpEntry entry);

}
