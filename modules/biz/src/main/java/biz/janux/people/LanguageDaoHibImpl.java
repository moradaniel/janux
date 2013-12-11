package biz.janux.people;

import java.util.List;

import org.janux.bus.persistence.DataAccessHibImplAbstract;
import org.janux.bus.persistence.EntityNotFoundException;

/**
 * Used to create, save, retrieve, update and delete Language objects from
 * persistent storage
 *
 * @author  <a href="mailto:daniel.mora@janux.org">Daniel Mora</a>
 *
 */
public class LanguageDaoHibImpl extends DataAccessHibImplAbstract implements LanguageDao 
{
	/* 
	 * deep loads a Language object from persistence using its id
	 */
	public Language load(Integer id) throws EntityNotFoundException
	{
		if (log.isDebugEnabled()) log.debug("attempting to retrieve Language with id '" + id + "'");

		Language language = (Language)getHibernateTemplate().load(LanguageImpl.class, id);

		if (language != null)
			if (log.isInfoEnabled()) log.info("successfully retrieved: " + language); 
		else
			log.warn("unable to find language with id: '" + id + "'");

		return language;
	}

	
	public Language findByCode(String code)
	{
		long begin = System.currentTimeMillis();

		if (log.isDebugEnabled()) log.debug("attempting to find language with code '" + code + "'");

		List list = getHibernateTemplate().find("from LanguageImpl where code=?", code);

		Language language = (list.size() > 0) ? (Language)list.get(0) : null;

		if (language == null) {
			log.warn("Unable to find language with code: '" + code + "'");
			return null;
		}

		if (log.isInfoEnabled()) log.info("successfully retrieved language with code: '" + code + "' in " + (System.currentTimeMillis() - begin) + " ms" ); 

		return language;
	}


	public Language loadByCode(String code) throws EntityNotFoundException
	{
		Language language = this.findByCode(code);

		if (language == null) 
			throw new EntityNotFoundException("Unable to retrieve language with code: '" + code + "'");

		return language;
	}


	public List loadAll()
	{
		long begin = System.currentTimeMillis();
		if (log.isDebugEnabled()) log.debug("attempting to load all languages...");

		List list = getHibernateTemplate().loadAll(LanguageImpl.class);

		if (log.isInfoEnabled()) recordTime("successfully retrieved all " + list.size() + " languages", begin);

		return list;
	}


	

}
