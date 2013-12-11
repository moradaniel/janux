package biz.janux.payment.gateway.controllers.creditcard;

import java.util.List;

import org.janux.bus.search.SearchCriteria;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import biz.janux.payment.CreditCardType;
import biz.janux.payment.CreditCardTypeService;

public class CreditCardTypeControllerRestImpl {

	private	CreditCardTypeService<SearchCriteria> creditCardTypeService;
	
	@RequestMapping(params = "creditCardTypes", method = RequestMethod.GET)
    public String loadCreditCardTypes(ModelMap modelMap)
	{
		List<CreditCardType> creditCardTypes = getCreditCardTypeService().findAll();
		modelMap.addAttribute("creditCardTypes",creditCardTypes);
        return "json";
	}

	public CreditCardTypeService<SearchCriteria> getCreditCardTypeService() {
		return creditCardTypeService;
	}

	public void setCreditCardTypeService(CreditCardTypeService<SearchCriteria> creditCardTypeService) {
		this.creditCardTypeService = creditCardTypeService;
	}
	
}
