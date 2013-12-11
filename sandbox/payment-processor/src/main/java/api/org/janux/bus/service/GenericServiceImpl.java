package api.org.janux.bus.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.PersistentObjectException;
import org.janux.bus.persistence.GenericDaoReadOnly;
import org.janux.bus.persistence.GenericDaoWrite;
import org.janux.bus.persistence.Persistent;
import org.janux.bus.search.SearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trg.search.Search;

/**
 * 
 * @author albertobuffagni@gmail.com
 *
 * @param <T>
 * @param <S>
 */
public class GenericServiceImpl<T,ID extends Serializable,S extends SearchCriteria,DW extends GenericDaoWrite<T, ID>,DR extends GenericDaoReadOnly<T, ID, S>> 
	implements GenericService<T, ID, S>
	{

	public Logger log = LoggerFactory.getLogger(this.getClass());
	
	DW genericDaoWrite;
	DR genericDaoRead;
	
	public int count(S searchCriteria) {
		return getGenericDaoRead().count(searchCriteria);
	}

	public void delete(T persistentObject) {
		log.debug("Deleting {}",persistentObject);
		getGenericDaoWrite().delete(persistentObject);
		log.debug("Deleted {}",persistentObject);
	}

	public List<T> findAll(S searchCriteria) {
		log.debug("Finding all by:{}",searchCriteria);
		List<T> list = getGenericDaoRead().findByCriteria(searchCriteria);
		log.debug("Found {} objects",list.size());
		return list;
	}
	
	public T find(S searchCriteria) {
		
		((Search)searchCriteria).setMaxResults(1);
		
		log.debug("Finding by:{}",searchCriteria);
		List<T> list = getGenericDaoRead().findByCriteria(searchCriteria);
		log.debug("Found {} objects",list.size());
		if (!list.isEmpty())
		{
			T persistentObject = list.get(0);
			persistentObject = load(persistentObject);
			return persistentObject;
		}
		return null;
	}

	public T save(T persistentObject) {
		log.debug("Saving object:{}",persistentObject);
		persistentObject = (T)getGenericDaoWrite().save(persistentObject);
		persistentObject = load(persistentObject);
		log.debug("Saved object:{}",persistentObject);
		return persistentObject;
	}

	public T saveOrUpdate(T persistentObject) {
		log.debug("Updating object:{}",persistentObject);
		persistentObject = (T)getGenericDaoWrite().saveOrUpdate(persistentObject);
		persistentObject = load(persistentObject);
		log.debug("Updated object:{}",persistentObject);
		return persistentObject;
	}
	
	public T update(T persistentObject) {
		log.debug("Updating object:{}",persistentObject);
		persistentObject = (T)getGenericDaoWrite().update(persistentObject);
		persistentObject = load(persistentObject);
		log.debug("Updated object:{}",persistentObject);
		return persistentObject;
	}

	public DW getGenericDaoWrite() {
		return genericDaoWrite;
	}

	public void setGenericDaoWrite(DW genericDaoWrite) {
		this.genericDaoWrite = genericDaoWrite;
	}

	public void setGenericDaoRead(DR genericDaoRead) {
		this.genericDaoRead = genericDaoRead;
	}

	public DR getGenericDaoRead() {
		return genericDaoRead;
	}

	public T load(ID id) {
		log.debug("Loading object id :{}",id);
		T persistentObject = getGenericDaoRead().load(id);
		log.debug("Loaded object:{}",persistentObject);
		return persistentObject;
	}
	
	public T load(String uuid) {
		log.debug("Loading object uuid :{}",uuid);
		
		if (uuid==null)
			throw new RuntimeException("uuid is null");
				
		S searchCriteria = (S) new Search();
		((Search)searchCriteria).addFilterEqual("uuid", uuid);
		T persistentObject = find(searchCriteria);
		log.debug("Loaded object:{}",persistentObject);
		return persistentObject;
	}
	
	public T load(T persistentObject) {
		return getGenericDaoRead().load((ID)((Persistent)persistentObject).getId());
	}

}
