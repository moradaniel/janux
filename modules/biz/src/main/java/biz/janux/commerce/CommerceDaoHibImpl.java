package biz.janux.commerce;

import java.util.List;

import org.janux.bus.persistence.DataAccessHibImplAbstract;

import biz.janux.geography.CountryDaoHibImplGeneric;

/**
 * 
 * @deprecated use {@link CountryDaoHibImplGeneric}
 */
public class CommerceDaoHibImpl extends DataAccessHibImplAbstract implements CommerceDao
{
	
	@SuppressWarnings("unchecked")
	public Currency findCurrencyByCode(final String code)
	{
		final List<Currency> list = (List<Currency>) getHibernateTemplate().find("from CurrencyImpl where code=?", code);

		if (list.size() == 0)
		{
			throw new IllegalStateException("Currency code " + code + " not found");
		}
		else if (list.size() > 1)
		{
			throw new IllegalStateException("More than one entry with currency code " + code + " returned");
		}
		
		// return the single instance
		return ( list.get(0));
	}
	
	
	@SuppressWarnings("unchecked")
	public PaymentMethod findPaymentMethodByCode(final String code)
	{
		final List<PaymentMethod> list = (List<PaymentMethod>)getHibernateTemplate().find("from PaymentMethodImpl where code=?", code);

		if (list.size() == 0)
		{
			throw new IllegalStateException("Payment method code " + code + " not found");
		}
		else if (list.size() > 1)
		{
			throw new IllegalStateException("More than one entry for payment method code " + code + " returned");
		}
		
		// return the single instance
		return ( list.get(0));
	}
	
	
	@SuppressWarnings("unchecked")
	public GuaranteeMethod findGuaranteeMethodByCode(final String code)
	{
		final List<GuaranteeMethod> list = (List<GuaranteeMethod>)getHibernateTemplate().find("from GuaranteeMethodImpl where code=?", code);

		if (list.size() == 0)
		{
			throw new IllegalStateException("Guarantee method code " + code + " not found");
		}
		else if (list.size() > 1)
		{
			throw new IllegalStateException("More than one entry for guarantee method code " + code + " returned");
		}
		
		// return the single instance
		return ( list.get(0));
	}
	
	
	@SuppressWarnings("unchecked")
	public DepositMethod findDepositMethodByCode(final String code)
	{
		final List<DepositMethod> list = (List<DepositMethod>)getHibernateTemplate().find("from DepositMethodImpl where code=?", code);

		if (list.size() == 0)
		{
			throw new IllegalStateException("Deposit method code " + code + " not found");
		}
		else if (list.size() > 1)
		{
			throw new IllegalStateException("More than one entry for deposit method code " + code + " returned");
		}
		
		// return the single instance
		return ( list.get(0));
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Currency> getCurrencies()
	{
		final List<Currency> list = (List<Currency>)getHibernateTemplate().find("from CurrencyImpl");
		return (list);
	}
	
	@SuppressWarnings("unchecked")
	public List<PaymentMethod> getPaymentMethods()
	{
		final List<PaymentMethod> list = (List<PaymentMethod>)getHibernateTemplate().find("from PaymentMethodImpl");
		return (list);
	}

	@SuppressWarnings("unchecked")
	public List<GuaranteeMethod> getGuaranteeMethods()
	{
		final List<GuaranteeMethod> list = (List<GuaranteeMethod>)getHibernateTemplate().find("from GuaranteeMethodImpl");
		return (list);
	}
	
	@SuppressWarnings("unchecked")
	public List<DepositMethod> getDepositMethods()
	{
		final List<DepositMethod> list = (List<DepositMethod>)getHibernateTemplate().find("from DepositMethodImpl");
		return (list);
	}
	
}
