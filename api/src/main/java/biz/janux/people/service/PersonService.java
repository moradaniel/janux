package biz.janux.people.service;

import java.util.List;

import org.janux.bus.search.SearchCriteria;

import biz.janux.people.Person;

/**
 * 
 * @author <a href="mailto:moradaniel@janux.org">Daniel Mora</a>
 *
 */
public interface PersonService<P extends Person, S extends SearchCriteria> {
	
	public List<P> find(S search);
	public int count(S search);
	public P find(Integer idPerson);
	public void delete(Integer idPerson);
	public Integer save(P person);
	public Integer saveOrUpdate(P person);
	public void save(List<P> Persons);

}