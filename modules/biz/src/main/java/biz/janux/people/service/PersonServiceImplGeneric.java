package biz.janux.people.service;

import java.util.List;

import biz.janux.people.PersonDaoGeneric;
import biz.janux.people.PersonImpl;

import com.trg.search.ISearch;

/**
 * 
 * @author <a href="mailto:moradaniel@janux.org">Daniel Mora</a>
 *
 */

public class PersonServiceImplGeneric implements PersonService<PersonImpl,ISearch> {
	
	protected PersonDaoGeneric<PersonImpl> personDaoGeneric;
	
	public void setPersonDaoGeneric(PersonDaoGeneric<PersonImpl> personDao) {
		this.personDaoGeneric = personDao;
	}
	
	public List<PersonImpl> find(ISearch search){
		return personDaoGeneric.findByCriteria(search);
	}

	public int count(ISearch search){
		return this.personDaoGeneric.count(search);
	}
	
	public PersonImpl find(Integer idPerson){
		return (PersonImpl) personDaoGeneric.load(idPerson);
	}
	
	public void delete(Integer idPerson){
		personDaoGeneric.delete(find(idPerson));
	}


	public Integer save(PersonImpl person){
		this.personDaoGeneric.save(person);
		return person.getId();
	}
	
	public Integer saveOrUpdate(PersonImpl person){
		this.personDaoGeneric.saveOrUpdate(person);
		return person.getId();
	}
	

	
	public void save(List<PersonImpl> Persons){
		//TODO implement this
	}

}

