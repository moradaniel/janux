package biz.janux.commerce;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateTemplate;

import biz.janux.people.Language;
import biz.janux.people.LanguageImpl;
import biz.janux.test.TransactionalBizTestAbstract;

public class CurrencyTest extends TransactionalBizTestAbstract
{
    /*
	public CurrencyTest(String aTestName)
	{
		super(aTestName);
	}*/


	
	/** define the tests to be run in this class */
	/*public static Test suite() throws Exception
	{
		final TestSuite suite = new TestSuite();

		// run all tests
		suite.addTestSuite(CurrencyTest.class);

		// or a subset thereoff
	//	suite.addTest(new CurrencyTest("testFindCurrency"));
	//	suite.addTest(new CurrencyTest("testFindPaymentMethod"));
	//	suite.addTest(new CurrencyTest("testFindDepositMethod"));
	//	suite.addTest(new CurrencyTest("testFindGuaranteeMethod"));
    //	suite.addTest(new CurrencyTest("testFindLanguage"));
	// 	suite.addTest(new CurrencyTest("testCurrencyDefaultDigits"));

		return suite;
	}*/



	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
        //TestRunner.run(suite());
	}


	@SuppressWarnings("unchecked")
	@Test
	public void testFindCurrency()
	{
		final SessionFactory sessFactory = (SessionFactory ) applicationContext.getBean("hibernateSessionFactory");
		final HibernateTemplate template = new HibernateTemplate(sessFactory);


		final Currency newCurrency = new CurrencyImpl();
		newCurrency.setCode("TEST_CUR");
        newCurrency.setDescription("This is a test currency");
        template.save(newCurrency);

		final List<CurrencyImpl> currencies = template.loadAll(CurrencyImpl.class);
        assertEquals(176, currencies.size());

        template.delete(newCurrency);
	}

	@Test
	public void testCurrencyDefaultDigits()
	{
		final Currency newCurrency = new CurrencyImpl();
		newCurrency.setCode("USD");
		assertEquals(2, newCurrency.getDefaultFractionDigits());

		newCurrency.setCode("JPY");
		assertEquals(0, newCurrency.getDefaultFractionDigits());
		
		newCurrency.setCode("EUR");
		assertEquals(2, newCurrency.getDefaultFractionDigits());
		
		newCurrency.setCode("ZZZ");
		assertEquals(-1, newCurrency.getDefaultFractionDigits());
		
		newCurrency.setCode(null);
		assertEquals(-1, newCurrency.getDefaultFractionDigits());
	}

	
	

	@SuppressWarnings("unchecked")
	@Test
	public void testFindPaymentMethod()
	{
		final SessionFactory sessFactory = (SessionFactory ) applicationContext.getBean("hibernateSessionFactory");
		final HibernateTemplate template = new HibernateTemplate(sessFactory);


		final PaymentMethod newMethod = new PaymentMethodImpl();
		newMethod.setCode("TEST_PAYMENT");
        newMethod.setDescription("This is a test payment method");
        template.save(newMethod);

		final List<PaymentMethod> methods = template.loadAll(PaymentMethod.class);
        assertEquals(9, methods.size());

        template.delete(newMethod);
	}


	@SuppressWarnings("unchecked")
	@Test
	public void testFindDepositMethod()
	{
		final SessionFactory sessFactory = (SessionFactory ) applicationContext.getBean("hibernateSessionFactory");
		final HibernateTemplate template = new HibernateTemplate(sessFactory);


		final DepositMethod newMethod = new DepositMethodImpl();
		newMethod.setCode("TEST_DEPOSIT");
        newMethod.setDescription("This is a test deposit method");
        template.save(newMethod);

		final List<DepositMethodImpl> methods = template.loadAll(DepositMethodImpl.class);
        assertEquals(4, methods.size());

        template.delete(newMethod);
	}


	@SuppressWarnings("unchecked")
	@Test
	public void testFindGuaranteeMethod()
	{
		final SessionFactory sessFactory = (SessionFactory ) applicationContext.getBean("hibernateSessionFactory");
		final HibernateTemplate template = new HibernateTemplate(sessFactory);


		final GuaranteeMethod newMethod = new GuaranteeMethodImpl();
		newMethod.setCode("TEST_GUARANTEE");
        newMethod.setDescription("This is a test guarantee method");
        template.save(newMethod);

		final List<GuaranteeMethod> methods = template.loadAll(GuaranteeMethod.class);
        assertEquals(9, methods.size());

        template.delete(newMethod);
	}


	@SuppressWarnings("unchecked")
	@Test
	public void testFindLanguage()
	{
		final SessionFactory sessFactory = (SessionFactory ) applicationContext.getBean("hibernateSessionFactory");
		final HibernateTemplate template = new HibernateTemplate(sessFactory);


		final Language newLanguage = new LanguageImpl();
		newLanguage.setCode("TEST_LANG");
        newLanguage.setDescription("This is a test language");
        template.save(newLanguage);

		final List<LanguageImpl> methods = template.loadAll(LanguageImpl.class);
        assertEquals(3, methods.size());

        template.delete(newLanguage);
	}





}