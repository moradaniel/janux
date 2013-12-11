package biz.janux.geography.controllers;

import java.util.Map;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import biz.janux.geography.GeographyService;

public class CountryControllerRestImpl {

	private	GeographyService geographyService;
	
	@RequestMapping(params = "countries", method = RequestMethod.GET)
    public String loadCountries(ModelMap modelMap)
	{
		Map countries = getGeographyService().loadAllCountries();
		modelMap.addAttribute("countries",countries.values());
        return "json";
	}

	public GeographyService getGeographyService() {
		return geographyService;
	}

	public void setGeographyService(GeographyService geographyService) {
		this.geographyService = geographyService;
	}

}
