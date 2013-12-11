package biz.janux.ui.springmvc.people;

import java.util.List;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import biz.janux.geography.GeographyDao;
import biz.janux.geography.StateProvince;
import biz.janux.geography.City;
import biz.janux.geography.PostalAddressImpl;
import biz.janux.people.PhoneNumberImpl;
import biz.janux.people.net.EmailAddressImpl;
import biz.janux.people.net.HttpAddressImpl;

import biz.janux.people.OrganizationDao;
import biz.janux.people.Organization;


/**
 ***************************************************************************************************
 * This interceptor wraps all actions involving Organizations; it performs the following actions:
 *
 * <ul>
 * 	<li>ensures that a Session exists</li>
 * 	<li>
 * 		ensures that a list of the Organizations that the current Principal can edit has
 * 		been loaded into the session; this list should contain at least the Code
 * 		and Name of the Organization
 * 	</li>
 * </ul>
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 *
 ***************************************************************************************************
 */
public class OrgActionInterceptor extends HandlerInterceptorAdapter
{
	Log log = LogFactory.getLog(this.getClass());
	
	private OrganizationDao orgDao;
	private GeographyDao geoDao;


	/** instantiates an interceptor with a Organization Accessor that it can use to perform its work */
	public OrgActionInterceptor(OrganizationDao orgDao, GeographyDao geoDao)
	{
		this.orgDao = orgDao;
		this.geoDao = geoDao;
	}


	/** 
	 * for the time being, retrieved all orgs, and make the first one the 'current' org
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	{
		boolean create = true;

		if (log.isDebugEnabled()) {
			log.debug("url: " + request.getRequestURL());
		}

		if (request.getSession(create).getAttribute(OrganizationController.KEY_ORGANIZATION_LIST) == null || 
		    request.getSession(create).getAttribute(OrganizationController.KEY_ORGANIZATION)      == null)
		{
			List orgs = this.orgDao.loadAll();
			Organization org = orgDao.load( ((Organization)orgs.get(0)).getId());

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


			request.getSession().setAttribute(
					OrganizationController.KEY_ORGANIZATION_LIST, createOrganizationMap(orgs));

			request.getSession().setAttribute(OrganizationController.KEY_ORGANIZATION, org);

			if (log.isInfoEnabled()) log.info("Populated org list and set '" + org.getName().getShort() + "' as active org");
		}

		return true;
	}

	/** given a list of orgs, returns a SortedMap of orgs keyed by org code */
	private static java.util.SortedMap createOrganizationMap(List orgs) {
		SortedMap map = new TreeMap();

		for (Iterator i = orgs.iterator(); i.hasNext();)
		{
			Organization org = (Organization)i.next();
			map.put(org.getCode(), org.getName().getShort());
		}

		return map;
	}


} // end class


