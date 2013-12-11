package org.janux.bus.persistence;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.janux.bus.search.SearchCriteria;
import org.janux.util.RandomStringGenerator;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;

import com.trg.search.ISearch;
import com.trg.search.SearchFacade;

/**
 * Base class for all Data Access classes implemented using Hibernate and the Generic DAO pattern.
 * Transactions are injected using declarative Spring declarations.
 * Subclasses the Spring class HibernateDaoSupport to get Springs support for handling the Hibernate Sessionfactory and Sessions.
 * 
 * See <a href="./doc-files/GenericDaoPattern.html#section1">this</a> diagram for more information. 
 * 
 * @author  <a href="mailto:moradaniel@janux.org">Daniel Mora</a>
 *
 */
public abstract class GenericDaoHibImpl<T, ID extends Serializable, S extends SearchCriteria> 
	extends HibernateDaoSupport
	implements GenericDaoWrite<T, ID>,
		GenericDaoReadOnly<T, ID, S>,
		HibernateDataAccessObjectGeneric 
{
	/** Properties*/
	protected Log log = LogFactory.getLog(this.getClass());
	
	protected RandomStringGenerator uuidGenerator;
	
	protected Class<T> persistentClass;


	/** This is the bean for using the Hibernate DAO Framework Search facade */
	SearchFacade searchFacade;
	
	
	/** Methods*/
	
	protected Class<T> getPersistentClass() {
		return persistentClass;
	}

	/*
	protected void setPersistentClass(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}
	*/
	
	public SearchFacade getSearchFacade() {
		return searchFacade;
	}

	public void setSearchFacade(SearchFacade searchFacade) {
		this.searchFacade = searchFacade;
	}
	
	
	
	public static Object firstResult(List results) {
		return (results != null && results.size()>0) ? results.get(0) : null;
	}

	
	@SuppressWarnings("unchecked")
	public GenericDaoHibImpl() {
		super();
		// the following code is used to cache the runtime Class of the 'T' type,
		// or, in other words, the Class of the entity that this DAO is managing
		
		//It can be throw a ClassCastException when a class that extends GenericDaoHibImpl does not define a type generic
		//For example: 
		//In this case no throws the exception: public class PersonDaoHibImplGeneric extends GenericDaoHibImpl<PersonImpl, Integer,... 
		//In this case throw the exception: public class PersonDaoHibImplGeneric<T extends PersonImpl> extends GenericDaoHibImpl<T, Integer,...
		
		/**try {
			this.persistentClass = (Class<T>)
			( (ParameterizedType) getClass().getGenericSuperclass() )
				.getActualTypeArguments()[0];
		
		} catch (ClassCastException e) {
			
			this.persistentClass =  (Class<T>)((TypeVariableImpl)
					( (ParameterizedType) getClass().getGenericSuperclass() )
						.getActualTypeArguments()[0]).getBounds()[0];
		}*/
		
		this.persistentClass = discoverPersistentClass(this);
		
	}
	
	private Class<T> discoverPersistentClass(Object o)
	{
		ParameterizedType parameterizedType = null;
		Class<T> class1 = null;
		TypeVariableImpl typeVariableImpl = null;
		try {
			
			parameterizedType = ((ParameterizedType)getClass().getGenericSuperclass());
			class1 = (Class<T>) parameterizedType.getActualTypeArguments()[0];
			return class1;
		
		} catch (ClassCastException e) {
			try
			{
				typeVariableImpl = (TypeVariableImpl) parameterizedType.getActualTypeArguments()[0];
				class1 = (Class<T>) typeVariableImpl.getBounds()[0];
				return class1;
			}
			catch (ClassCastException e1) {
				ParameterizedType parameterizedType2 = (ParameterizedType) typeVariableImpl.getBounds()[0];
				class1 = (Class<T>) parameterizedType2.getRawType();
				return class1;
			}
		}			
	}
	
	public T update(T persistentObject) throws DataAccessException{
		getHibernateTemplate().update(persistentObject);
		return persistentObject;
	}

	public T save(T persistentObject)throws DataAccessException{
		
		setUuid(persistentObject);
		setDateCreated(persistentObject);
		
		getHibernateTemplate().save(persistentObject);
		return persistentObject;
	}

	/**
	 * set the created date if the object is new and implements {@link Versionable}
	 * @param persistentObject
	 */
	private void setDateCreated(T persistentObject) {
		/**
		 * check if the object is not persistent, identifiable and new
		 */
		if (persistentObject instanceof Persistent 
				&& ( ((Persistent)persistentObject).getId()==null || ((Persistent)persistentObject).getId()==Persistent.UNSAVED_ID)
				&& persistentObject instanceof Versionable)
			
			((Versionable)persistentObject).setDateCreated(new Date());
	}

	/**
	 * set the uuid for objects that are new and implements {@link Identifiable}
	 * 
	 * @param persistentObject
	 */
	private void setUuid(T persistentObject) {
		
		/**
		 * check if the object is not persistent, identifiable and new
		 */
		if (persistentObject instanceof Persistent 
				&& (((Persistent)persistentObject).getId() ==null || ((Persistent)persistentObject).getId()==Persistent.UNSAVED_ID)
				&& persistentObject instanceof Identifiable)
			
			((Identifiable)persistentObject).setUuid(getUuidGenerator().getString());
	}

	public T saveOrUpdate(T persistentObject) throws DataAccessException{
		setUuid(persistentObject);
		setDateCreated(persistentObject);
		
		getHibernateTemplate().saveOrUpdate(persistentObject);
		return persistentObject;
	}
	
	public T merge(final T persistentObject) {
		getHibernateTemplate().merge(persistentObject);
		return persistentObject;
	}
	
	public void delete(T persistentObject) throws DataAccessException{
		getHibernateTemplate().delete(persistentObject);
	}

	public void refresh(final T persistentObject) throws DataAccessException{
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session hibSession) throws HibernateException {
				hibSession.refresh(persistentObject);
				return null;
			}
		});
	}
	
	public void evict(final T persistentObject) throws DataAccessException {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session hibSession) throws HibernateException {
				hibSession.evict(persistentObject);
				return null;
			}
		});
	}

	public void flush() 
	{
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session hibSession) throws HibernateException {
				hibSession.flush();
				return null;
			}
		});
	}

	public void clear() 
	{
		getHibernateTemplate().execute(
		new HibernateCallback() 
		{
			public Object doInHibernate(Session hibSession) throws HibernateException 
			{
				hibSession.clear();
				return (null);
			}
		});
	}
	
	/**
	 * Attach an object to the current session given a lockMode. See {@link LockMode}.
	 * @param persistentObject
	 * @param lockMode
	 * @throws DataAccessException
	 */
	private void attach(final T persistentObject, final LockMode lockMode) throws DataAccessException {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session hibSession) throws HibernateException {
				hibSession.lock(persistentObject,lockMode);
				return null;
			}
		});
	}

	/**
	 * Reattach an object with the current session. This will not execute a select to the database so the detached instance has to be unmodified.
	 * The logical use would be the Hibernate refresh(object, LockMode.NONE) method but this is always executing a select even though the object was not 
	 * modified. So we have used the hibernate lock method with LockMode.NONE as a parameter to reattach an unmodified object to the current session 
	 * without executing a Select or Update to the database.
	 * @param persistentObject
	 * @throws DataAccessException
	 */
	public void attachClean(final T persistentObject) throws DataAccessException {
		this.attach(persistentObject, LockMode.NONE);
	}
	
	
	public Session getHibernateSession()
	{
		return super.getSession();
	}

	@SuppressWarnings("unchecked")
	public T load(ID id) throws DataAccessException
	{
		if (log.isDebugEnabled()) log.debug("attempting to retrieve "+this.persistentClass.getSimpleName()+" with id '" + id + "'");

		T object = (T)getHibernateTemplate().get(this.persistentClass, id);

		if (log.isInfoEnabled()) log.info("successfully retrieved: " + this.persistentClass.getSimpleName()+" object with id: '" +id);

		return object;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(SearchCriteria search){
		List results = getSearchFacade().search(this.persistentClass, (ISearch)search);
		return results; 
	}
	
	public int count(SearchCriteria search){
		return getSearchFacade().count((ISearch)search);
	}

	public RandomStringGenerator getUuidGenerator() {
		return uuidGenerator;
	}

	public void setUuidGenerator(RandomStringGenerator uuidGenerator) {
		this.uuidGenerator = uuidGenerator;
	}

}
