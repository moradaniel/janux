package org.janux.help;

import java.util.List;

import org.janux.bus.persistence.GenericDaoReadOnlyWithFacets;
import org.janux.bus.persistence.GenericDaoWrite;
import org.janux.bus.search.SearchCriteria;

enum HelpCategoryFacet {};
public interface HelpCategoryDaoGeneric<T extends HelpCategory> extends GenericDaoWrite<T, Integer>,GenericDaoReadOnlyWithFacets<T, Integer, SearchCriteria, HelpCategoryFacet>

{

	public HelpCategory newHelpCategory(String title);
	
	public List<HelpCategory> loadAll();
	
	public HelpCategory findByTitle(String title);

	public void delete(HelpCategory entry);

}
