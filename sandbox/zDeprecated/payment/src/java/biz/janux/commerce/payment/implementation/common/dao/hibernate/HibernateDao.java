package biz.janux.commerce.payment.implementation.common.dao.hibernate;

import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import biz.janux.commerce.payment.util.PasswordUtil;
/**
 * @author Nilesh
 * </br>
 * Class for getting the Hibernate session factory and 
 * the methods of Hibernate.
 */
public class HibernateDao {
	
	private static HibernateDao instance ;
	
	private static SessionFactory sessionFactory=null;	
	private static Session session=null;	
	private static Transaction tx=null;
	
	private HibernateDao(){
		if(session == null)
			initSession();
	}
	
	@Deprecated
	public static  Session  getSessionFactory(){
		if(session == null)
			initSession();
		return session;
	}
	/**
	 * method to close Session Factory
	 */
	public static void closeSessionFactory(){		
		session.flush();
		session.close();
	}
	/**
	 * Method to initialize the variable
	 */
	private static void initSession(){
		Configuration config = new Configuration().configure();
		String pass = config.getProperty("hibernate.connection.password");		
		PasswordUtil ccUtil = new PasswordUtil();
		String pass_decrypted = ""; 
		try {
			pass_decrypted = ccUtil.decrypt(pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		config.setProperty("hibernate.connection.password", pass_decrypted);
		pass = config.getProperty("hibernate.connection.password");				
        sessionFactory = config.buildSessionFactory(); 
		session=sessionFactory.openSession();
	}
	
	@Deprecated
	public static boolean startTx(){
		if(session != null) tx = session.beginTransaction();
		if(tx == null) return false;
		return true;
	}
	
	@Deprecated
	public static boolean commitTx(){
		if(tx != null){
			tx.commit();
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * public methods for accessing the data.
	 * */	
	public static HibernateDao getInstance(){
		if(instance == null)
			instance = new HibernateDao();
		return instance;
	}
	/**
	 * Method to insert data into table
	 * @param object
	 * @return boolean
	 */
	public boolean save(Object object){
		// TODO Add Synchronized block
		Transaction tx = session.beginTransaction();
		session.save(object);
		tx.commit();
		return true;
	}
	/**
	 * Method to update table
	 * @param object
	 * @return boolean
	 */
	public boolean update(Object object){
		Transaction tx = session.beginTransaction();
		session.update(object);
		tx.commit();
		return true;
	}
	@SuppressWarnings("unchecked")
	public Object loadfromDB(Class clazz , long id){
		return session.load(clazz , id);
	}
	@SuppressWarnings("unchecked")
	public Criteria getCriteria(Class clazz){
		Criteria crit = session.createCriteria(clazz);
		return crit;
	}
	@SuppressWarnings("unchecked")
	public Object loadfromUUID(Class clazz, String uuid) {
	
		Object obj = null;
		String query_str = "from " + clazz.getName() + " as obj where obj.UUID = '" + uuid + "' ";

		Query query = session.createQuery(query_str);
		for (Iterator it= query.iterate();it.hasNext();) {
			obj = it.next();
		}
		return obj;
	} 
}
