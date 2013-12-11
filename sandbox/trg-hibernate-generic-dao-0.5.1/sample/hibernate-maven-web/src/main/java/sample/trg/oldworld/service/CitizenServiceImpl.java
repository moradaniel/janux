package sample.trg.oldworld.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sample.trg.oldworld.dao.CitizenDAO;
import sample.trg.oldworld.model.Citizen;

import com.trg.search.ISearch;
import com.trg.search.Search;
import com.trg.search.SearchResult;

/**
 * This is the implementation for our Citizen Service. The @Service annotation
 * allows Spring to automatically detect this as a component rather than having
 * to comfigure it in XML. The @Autowired annotation tells Spring to inject our
 * Citizen DAO using the setDao() method.
 * 
 * @author dwolverton
 * 
 */
@Service
public class CitizenServiceImpl implements CitizenService {

	CitizenDAO dao;

	@Autowired
	public void setDao(CitizenDAO dao) {
		this.dao = dao;
	}

	public void save(Citizen citizen) {
		dao.save(citizen);
	}

	public List<Citizen> findAll() {
		return dao.findAll();
	}

	public Citizen findByName(String name) {
		if (name == null)
			return null;
		return dao.searchUnique(new Search().addFilterEqual("name", name));
	}

	public void flush() {
		dao.flush();
	}

	public List<Citizen> search(ISearch search) {
		return dao.search(search);
	}

	public Citizen findById(Long id) {
		return dao.find(id);
	}

	public void delete(Long id) {
		dao.removeById(id);
	}

	public SearchResult<Citizen> searchAndCount(ISearch search) {
		return dao.searchAndCount(search);
	}
}
