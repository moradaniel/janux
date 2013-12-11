package org.janux.ui.springmvc.basic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController {

	/** Programatically maps '/' to view 'test/index' */
	// @RequestMapping(value="/", method = {RequestMethod.GET})
	@RequestMapping(value={"/","/test/","/test"})
	public String index() 
	{
		// if not explicitly configuring an InternalResourceViewResolver, 
		// one can return the full path to the resource, though this is not recommended
		// return "/WEB-INF/view/test/index.jsp";
		
		// return a logical name
		return "test/index";
	}

} // end class TestController

	
