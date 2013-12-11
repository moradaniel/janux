package org.janux.help;

import java.util.List;

import org.janux.bus.persistence.EntityNotFoundException;
import org.janux.bus.persistence.GenericDaoReadOnlyWithFacets;
import org.janux.bus.persistence.GenericDaoWrite;
import org.janux.bus.search.SearchCriteria;

public interface HelpEntryDaoGeneric<T extends HelpEntry> extends GenericDaoWrite<T, Integer>,GenericDaoReadOnlyWithFacets<T, Integer, SearchCriteria, HelpEntryFacet>
{
	public HelpEntry loadByCode(String code) throws EntityNotFoundException;

	public HelpEntry findByCode(String code);
	public HelpEntry findByLabel(String label);

	public HelpEntry newHelpEntry(String code,String label,HelpCategory category);
	
	public List<HelpEntry> loadAll();

	public void delete(HelpEntry entry);

}
