package biz.janux.geography.controllers;

import java.util.Map;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import biz.janux.geography.GeographyService;

public class StateControllerRestImpl {

	private	GeographyService geographyService;
	
	@RequestMapping(params = "states", method = RequestMethod.GET)
    public String loadStates(@RequestParam("countryCode") String countryCode,ModelMap modelMap)
	{
		Map states = getGeographyService().findStatesByCountry(countryCode);
		modelMap.addAttribute("states",states.values());
        return "json";
	}
	
	public GeographyService getGeographyService() {
		return geographyService;
	}

	public void setGeographyService(GeographyService geographyService) {
		this.geographyService = geographyService;
	}

}
