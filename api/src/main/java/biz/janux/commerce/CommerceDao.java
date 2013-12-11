package biz.janux.commerce;

import java.util.List;

import org.janux.bus.persistence.DataAccessObject;

import biz.janux.commerce.Currency;
import biz.janux.commerce.DepositMethod;
import biz.janux.commerce.GuaranteeMethod;
import biz.janux.commerce.PaymentMethod;

public interface CommerceDao extends DataAccessObject
{
	Currency findCurrencyByCode(final String code);
	
	PaymentMethod findPaymentMethodByCode(final String code);
	
	GuaranteeMethod findGuaranteeMethodByCode(final String code);
	
	DepositMethod findDepositMethodByCode(final String code);
	
	List<Currency> getCurrencies();
	
	List<PaymentMethod> getPaymentMethods();

	List<GuaranteeMethod> getGuaranteeMethods();
	
	List<DepositMethod> getDepositMethods();
}
