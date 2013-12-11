package org.janux.ui.springmvc.basic;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value="/basic")
public class BasicController 
{
	/** map "/basic" and "/basic/" to the default home page */
	@RequestMapping(value={"","/"})
	public String index() 
	{
		// if not explicitly configuring an InternalResourceViewResolver, 
		// one can return the full path to the resource, though this is not recommended
		// return "/WEB-INF/view/basic/index.jsp";
		
		// return a logical name
		return "basic/index";
	}

	/** Simply returns the form, could be used to prepare the form */
	@RequestMapping(value="updateName", method=RequestMethod.GET )
	public String nameForm() 
	{
		return "basic/updateName";
	}

	/** 
	 * Processes the form, implicitly returns to the same url, though it could be directed elsewhere
	 */
	@RequestMapping(value="updateName", method=RequestMethod.POST)
	public ModelMap updateName( 
		@RequestParam(value="firstName", required = false) final String firstName,
		@RequestParam(value="lastName",  required = false) final String lastName
		)
	{
		ModelMap model = new ModelMap();
		model.addAttribute("firstName", firstName);
		model.addAttribute("lastName", lastName);
		return model;
	}

} // end class BasicController

	
