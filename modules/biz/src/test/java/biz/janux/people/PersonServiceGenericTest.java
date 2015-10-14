package biz.janux.people;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import biz.janux.geography.PostalAddressImpl;
import biz.janux.people.service.PersonService;
import biz.janux.test.TransactionalBizTestAbstractGeneric;

import com.trg.search.Filter;
import com.trg.search.ISearch;
import com.trg.search.Search;


/**
 * @author <a href="mailto:moradaniel@janux.org">Daniel Mora</a>
 *   
 */
public class PersonServiceGenericTest extends TransactionalBizTestAbstractGeneric
{
	protected PersonService<Person,ISearch> personService;

	public PersonServiceGenericTest() {
		super();
	}

	/*
	public PersonServiceGenericTest(String name) {
		super(name);
	}*/
	
	public void setPersonService(PersonService<Person, ISearch> personService) {
		this.personService = personService;
	}
	
	public void testFindPersons() 
	{
		Search search = new Search(PersonImpl.class);

		/*test search by last name
		Search for Person instances by Last Name or partial Last Name (entering
		'Smith' would return all persons whose last name contain 'smith', performing a
		cap-insensitive search	*/
		search.addFilter(Filter.ilike("name.last", "%fairchild%"));
		
		List<Person> persons = personService.find(search);
		
		assertNotNull("persons",persons);
		assertTrue(persons.size() == 1);

		Person person = persons.get(0);
		assertEquals("prefix", "Mr.",        person.getName().getHonorificPrefix());
		assertEquals("first",  "David",   person.getName().getFirst());
		assertEquals("middle", "M.",       person.getName().getMiddle());
		assertEquals("last",   "Fairchild", person.getName().getLast());
		assertEquals("suffix", "B.A.", person.getName().getHonorificSuffix());

		//Test Search by Contact Method
		search= new Search(PersonImpl.class);
		search.addFilterSome("contactMethods", Filter.and(Filter.equal("class", PostalAddressImpl.class),Filter.equal("line1", "751 Paula Lane")));
		//search.addFilterSome("contactMethods", Filter.equal("address", "philippe.paravicini@janux.org" ));
		
		persons = personService.find(search);
		
		assertNotNull("persons",persons);
		assertTrue(persons.size() == 1);
		
		person = persons.get(0);
		assertEquals("prefix", "Mr.",        person.getName().getHonorificPrefix());
		assertEquals("first",  "Philippe",   person.getName().getFirst());
		assertEquals("middle", "Jean",       person.getName().getMiddle());
		assertEquals("last",   "Paravicini", person.getName().getLast());
		assertEquals("suffix", "M.F.A., C.F.P.", person.getName().getHonorificSuffix());

	}

	public void testLoadPersonByFacets(){
		Set<PersonFacet> facetSet = new HashSet<PersonFacet>();
		facetSet.add(PersonFacet.EMAIL_ADDRESSES);
		facetSet.add(PersonFacet.POSTAL_ADDRESSES);
		
		Person person = personService.find(1);
		assertEquals("prefix", "Mr.", person.getName().getHonorificPrefix());
	}

	
    /**
     * Main function for unit tests
     * @param args command line args
     */
    public static void main(String[] args)
    {
        // create the suite of tests
        /*final TestSuite tSuite = new TestSuite();
        tSuite.addTest(new PersonServiceGenericTest("testFindPersons"));
        //tSuite.addTest(new PersonServiceTest("testLoadPersonByFacets"));
        TestRunner.run(tSuite);*/
    }
	
} 
