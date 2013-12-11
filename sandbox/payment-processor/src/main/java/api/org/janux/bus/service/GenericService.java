package api.org.janux.bus.service;

import java.io.Serializable;
import java.util.List;

import org.janux.bus.persistence.DataAccessException;
import org.janux.bus.search.SearchCriteria;

/**
 * 
 * @author albertobuffagni@gmail.com
 *
 * @param <T>
 * @param <ID>
 * @param <S>
 */
public interface GenericService<T, ID extends Serializable, S extends SearchCriteria> {

	public int count(S searchCriteria);
	public void delete(T persistentObject);
	public List<T> findAll(S searchCriteria);
	public T find(S searchCriteria);
	public T save(T persistentObject);
	public T load(ID id);
	public T load(String uuid);

}
