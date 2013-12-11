package biz.janux.ui.springmvc.people;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContextException;

import org.springframework.web.bind.RequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.RedirectView;

import biz.janux.geography.GeographyDao;
import biz.janux.geography.StateProvince;
import biz.janux.geography.City;
import biz.janux.geography.PostalAddressImpl;
import biz.janux.people.PhoneNumberImpl;
import biz.janux.people.net.EmailAddressImpl;
import biz.janux.people.net.HttpAddressImpl;

import biz.janux.people.Organization;
import biz.janux.people.OrganizationDao;

/**
 * <code>MultiActionController</code> that handles all non-form URL's.
 *
 * @author Ken Krebs
 */
public class OrganizationController extends MultiActionController implements InitializingBean {

	Log log = LogFactory.getLog(this.getClass());

	/** name of the view used to display organization name and description: 'Description' */
	public static final String VIEW_DESCRIPTION = "Description";

	/** name of the view used to display organization information: 'ContactInfo' */
	public static final String VIEW_CONTACT_INFO = "ContactInfo";

	/** name of a generic view used to display messages: 'MessagePage' */
	public static final String VIEW_MESSAGE = "MessagePage";

	/**  name of the http Request parameter which we use to pass an Organization's code: 'orgCode' */
	public static final String PARAM_ORGANIZATION_CODE = "orgCode";

	/** key that we use when placing an Organization instance in a model: 'org' */
	public static final String KEY_ORGANIZATION = "org";

	/** key that we use when placing a Organization Code instance in a model: 'orgCode' */
	public static final String KEY_ORGANIZATION_CODE = "orgCode";

	/** key that we use when placing a Message string in a model: 'message' */
	public static final String KEY_MESSAGE = "message";

	/** 
	 * The key by which we store in the Session the list of Organizations that the
	 * Principal is authorized to view: "myOrgs"
	 */
	public static final String KEY_ORGANIZATION_LIST = "myOrgs";


	private OrganizationDao orgDao;
	private GeographyDao    geoDao;


	/** used to inject finder class for Organizations */
	public void setOrganizationDao(OrganizationDao orgDao) {
		this.orgDao = orgDao;
	}

	/** used to inject finder class for Geographical entities */
	public void setGeographyDao(GeographyDao geoDao) {
		this.geoDao = geoDao;
	}

	public void afterPropertiesSet() throws Exception {
		if (orgDao == null)
			throw new ApplicationContextException("orgDao was not set in class " + getClass());

		if (geoDao == null)
			throw new ApplicationContextException("geoDao was not set in class " + getClass());
	}


	/** 
	 * deep loads an Organization based on a code passed in the query string parameter denoted by
	 * OrganizationController.PARAM_ORGANIZATION_CODE and forwards to a summary display page 
	 */
	public ModelAndView selectOrganization(HttpServletRequest request, HttpServletResponse response)
		throws ServletException 
	{
		String code = RequestUtils.getStringParameter(request, PARAM_ORGANIZATION_CODE); 
		Organization org = this.orgDao.loadByCode(code);

		// make sure that certain address and phone fields exist
		// TODO: fix this temporary hack
		if (org.getPostalAddress("PHYSICAL") == null) 
		{
			StateProvince unknownState = geoDao.loadUnknownState("US");
			City city = geoDao.findCityByName(unknownState, "Unknown City");

			if (city == null)
				city = geoDao.newCity(unknownState, "Unknown City");

			PostalAddressImpl address = new PostalAddressImpl();
			address.setCity(city);

			org.setContactMethod("PHYSICAL", address);
		}

		if (org.getPhoneNumber("PHONE1") == null)
			org.setContactMethod("PHONE1", new PhoneNumberImpl());

		if (org.getPhoneNumber("FAX1") == null)
			org.setContactMethod("FAX1", new PhoneNumberImpl());

		if (org.getPhoneNumber("FAX2") == null)
			org.setContactMethod("FAX2", new PhoneNumberImpl());

		if (org.getEmailAddress("ORGANIZATION") == null)
			org.setContactMethod("ORGANIZATION", new EmailAddressImpl());

		if (org.getEmailAddress("RESERVATION") == null)
			org.setContactMethod("RESERVATION", new EmailAddressImpl());

		if (org.getUrl("WEBSITE") == null)
			org.setContactMethod("WEBSITE", new HttpAddressImpl());


		if (org == null) {
			return new ModelAndView(VIEW_MESSAGE, KEY_MESSAGE, "Unable to load Organization with code '" + code + "'");
		}

		//request.getSession().setAttribute(KEY_ORGANIZATION_CODE, code);
		request.getSession().setAttribute(KEY_ORGANIZATION, org);

		return new ModelAndView(new RedirectView(VIEW_DESCRIPTION,false));
	}


	/** 
	 * searches for an Organization based on a code passed in the query string parameter denoted by
	 * OrganizationController.PARAM_ORGANIZATION_CODE, if found, redirects
	 */
	/*
	public ModelAndView findByCode(HttpServletRequest request, HttpServletResponse response)
		throws ServletException 
	{
		String code = RequestUtils.getStringParameter(request, PARAM_ORGANIZATION_CODE); 
		Organization org = this.orgDao.findByCode(code);

		if (org == null) {
			return new ModelAndView(VIEW_MESSAGE, KEY_MESSAGE, "Unable to find Organization with code '" + code + "'");
		}

		return new ModelAndView(VIEW_DESCRIPTION, KEY_ORGANIZATION, org);
	}
	*/


	/** 
	 * Used to redirect to the Welcome page
	 */
	public ModelAndView redirectHome(HttpServletRequest request, HttpServletResponse response)
		throws ServletException 
	{
		if (log.isDebugEnabled())
		{
			log.debug("url:         " + request.getRequestURL());
			log.debug("servletPath: " + request.getServletPath());
			log.debug("contextPath: " + request.getContextPath());
			log.debug("pathInfo:    " + request.getPathInfo());
		}

		// this redirects 
		// 		http://www.../organization    (no trailing slash - results in null 'pathInfo')
		// to
		// 		http://www.../organization/   (resolved to view '' - empty string)
		//
		// and lands on the 'welcome' page
		String servletPath = request.getServletPath() + "/";

		if (servletPath.startsWith("/")) 
			servletPath = servletPath.substring(1,servletPath.length());

		return new ModelAndView(new RedirectView(servletPath,false));
	}

}
