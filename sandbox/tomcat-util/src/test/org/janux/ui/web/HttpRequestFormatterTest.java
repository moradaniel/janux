package org.janux.ui.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
// import junit.extensions.TestSetup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// import org.apache.catalina.connector.Response;
// import org.apache.catalina.connector.ResponseFacade;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 ***************************************************************************************************
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.1 - 2011-03-22
 ***************************************************************************************************
 */
public class HttpRequestFormatterTest extends AbstractDependencyInjectionSpringContextTests 
{
	Log log = LogFactory.getLog(this.getClass());
	//static Log log = LogFactory.getLog(FooSetup.class);

	/** injected by Spring */
	protected HttpRequestFormatter  requestFormatter;
	protected HttpResponseFormatter responseFormatter;

	final static String[] APPLICATION_CONTEXT_FILES = {
		 "classpath:FormatterContext.xml"
	};

	protected String[] getConfigLocations() {
		return APPLICATION_CONTEXT_FILES;
	}

	public HttpRequestFormatterTest() {
		super();
		setPopulateProtectedVariables(true);
	}

	private MockHttpServletRequest     request  = new MockHttpServletRequest();
	private HttpServletResponseWrapper response = new HttpServletResponseWrapper(new MockHttpServletResponse());

	private final static String CONTEXT_PATH      = "/aContext";
	private final static String SERVLET_PATH      = "/aServlet";
	private final static String PATH_INFO         = "/aPathInfo";
	private final static String REQUEST_URI       = CONTEXT_PATH + SERVLET_PATH + PATH_INFO;
	private final static String QUERY_STRING      = "&ticket=ST-1-EJQR6TT0IJO09Qn5OrQn-host-01&service=https%3A%2F%2Fdev.janux.org%2Fadmin%2Fj_spring_cas_security_check";
	private final static String REQ_SESS_ID       = "DA3EF01212AC23470F7DDF37941788C2.host-01";

	private final static String PROTOCOL          = "HTTP/1.1";
	private final static String SCHEME            = "https";
	private final static String SERVER_NAME       = "server.name.com";
	private final static int    SERVER_PORT       = 443;

	private final static String REMOTE_ADDR       = "10.1.1.33";
	private final static String REMOTE_HOST       = "remote.host.org";

	private final static String CONTENT_TYPE_HTML = "text/html;charset=utf-8";
	private final static String CONTENT_TYPE_APP  = "application/x-www-form-urlencoded";
	// private final static byte[]    CONTENT     = ;
	// private final static String LOCALE_US         = "en_US";

	private final static String AUTH_TYPE      = HttpServletRequest.BASIC_AUTH;
	// CHAR_ENCODING

	/** executed prior to each test */
	protected void onSetUp()
	{ 
		request.setContextPath(CONTEXT_PATH);
		request.setServletPath(SERVLET_PATH);
		request.setPathInfo(PATH_INFO);
		request.setRequestURI(REQUEST_URI);
		request.setQueryString(QUERY_STRING);
		request.setRequestedSessionId(REQ_SESS_ID);
		 
		request.setProtocol(PROTOCOL);
		request.setScheme(SCHEME);
		request.setMethod("GET");
		request.setServerName(SERVER_NAME);
		request.setServerPort(SERVER_PORT);

		request.setRemoteAddr(REMOTE_ADDR);
		request.setRemoteHost(REMOTE_HOST);

		request.setContentType(CONTENT_TYPE_HTML);
		// request.setContent(CONTENT);
		// request.setLocale(LOCALE_US);
		
		request.addHeader("referer","https://mycrs-test-new.innpoints.com/auth/logout");
		request.addHeader("Keep-Alive","300");
		request.addParameter("submit","LOGIN");
		request.addParameter("logoutRequest","<samlp:LogoutRequest xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\" ID=\"LR-2-iiVb6CZvI39eG9Fbj04FWZgrt6iXgbJStN7\" Version=\"2.0\" IssueInstant=\"2011-03-08T17:38:16Z\"><saml:NameID xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\">@NOT_USED@</saml:NameID><samlp:SessionIndex>ST-1-EJQR6TT0IJO09Qn5OrQn-inn-id-01</samlp:SessionIndex></samlp:LogoutRequest>");

		Cookie[] cookies = new Cookie[2];

		cookies[0] = new Cookie("JSESSIONID",REQ_SESS_ID);
		cookies[0].setDomain(".sub.janux.org");
		cookies[0].setPath("/app");
		cookies[0].setMaxAge(60*15);
		cookies[0].setSecure(true);
		cookies[0].setVersion(1); // 0=Netscape orig spec; 1=RFC-2109

		cookies[1] = new Cookie("__utmz","1.1299269837.1.1.utmcsr");
		cookies[1].setDomain("");
		cookies[1].setPath("/");
		cookies[1].setMaxAge(-1);
		cookies[1].setSecure(false);
		cookies[1].setVersion(0); // 0=Netscape orig spec; 1=RFC-2109

		request.setCookies(cookies);


		//// Create a response
		
		// wire the Response with its runtime collaborators to prevent NPEs
		/*
		try { 
			Response resp = new Response();
			resp.setConnector(new org.apache.catalina.connector.Connector("HTTP/1.1"));
			resp.setCoyoteResponse(new org.apache.coyote.Response());
			response = new ResponseFacade(resp);
		}
		catch (Exception e) { throw new RuntimeException(e); }
		*/

		response.setContentType(CONTENT_TYPE_HTML);
		response.addCookie(cookies[0]);
		response.addCookie(cookies[1]);
		response.setStatus(200);
		response.addHeader("referer","https://mycrs-test-new.innpoints.com/auth/logout");
		response.addHeader("Keep-Alive","300");
	}

	/** executed after each test */
	protected void onTearDown() { }

	public void testHttpFormatting() 
	{
		log.info("Formatted Request:  [" + this.requestFormatter.format(this.request) + "]");
		log.info("Formatted Response: [" + this.responseFormatter.format(this.response) + "]");
	}

	/** define the tests to be run in this class */
	/*
	public static Test suite()
	{
		TestSuite suite = new TestSuite();

		// run all tests
		suite = new TestSuite(HttpRequestFormatterTest.class);

		// or a subset thereoff
		// suite.addTest(new TestHttpRequestFormatter("testFoo1"));

		return suite;

		// or
		// TestSetup wrapper= new SomeWrapper(suite);
		// return wrapper;
	}
	*/


} // end class TestHttpRequestFormatter

