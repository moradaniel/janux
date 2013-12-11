package biz.janux.ui.springmvc.people;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import biz.janux.people.Organization;
import biz.janux.people.OrganizationDao;

/**
 ***************************************************************************************************
 * Used to edit basic information about a organization
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.biz">Philippe Paravicini</a>
 ***************************************************************************************************
 */
public class OrgDescriptionForm extends SimpleFormController
{
	Log log = LogFactory.getLog(this.getClass());

	private OrganizationDao orgDao;

	/** used to inject accessor class for Organizations */
	public void setOrganizationDao(OrganizationDao orgDao) {
		this.orgDao = orgDao;
	}


	protected Object formBackingObject(HttpServletRequest request) throws Exception 
	{
		return (Organization)request.getSession().getAttribute(OrganizationController.KEY_ORGANIZATION);
	}

	/** Saves a recently edited Organization */
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, 
			Object command, BindException errors) throws Exception 
	{
		Organization org = (Organization)command;

		if (log.isDebugEnabled()) log.debug("attempting to update organization " + org.getName().getShort());

		orgDao.update(org);

		if (log.isInfoEnabled()) log.info("saved organization " + org.getName().getShort());

		return new ModelAndView(getSuccessView());
	}


} // end class
