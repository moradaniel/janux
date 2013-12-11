package org.janux.ui.web;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.LinkedHashSet;

import javax.servlet.http.Cookie;

// import org.apache.commons.logging.Log;
// import org.apache.commons.logging.LogFactory;




/**
 ***************************************************************************************************
 * The HttpServletResponse interface and the implementing class HttpServletResponse response
 * provided by the javax.servlet packages do no have accessors for the various fields in the http
 * response, such as the status, error message, cookies set, and headers set; we provide here an
 * implementation that captures these fields as they are being set and provides accessors that make
 * it possible to inspect these fields.  This class can be used, for example, to log detailed
 * information on an http response sent back to a client inside a standard servlet API Filter.  Note
 * that this class will capture most actions taken by a web application, but not may reflect changes
 * to the http response that are made after execution of the servlet filter.
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.1 - 2011-03-24
 ***************************************************************************************************
 */
public class HttpServletResponseWrapper extends javax.servlet.http.HttpServletResponseWrapper
implements org.janux.ui.web.HttpServletResponse
{

	private static final Map<Integer,String> statusToString = new HashMap<Integer,String>();
	
	static {
		statusToString.put(new Integer(SC_ACCEPTED),                        "ACCEPTED");
		statusToString.put(new Integer(SC_BAD_GATEWAY),                     "BAD_GATEWAY");
		statusToString.put(new Integer(SC_BAD_REQUEST),                     "BAD_REQUEST");
		statusToString.put(new Integer(SC_CONFLICT),                        "CONFLICT");
		statusToString.put(new Integer(SC_CONTINUE),                        "CONTINUE");
		statusToString.put(new Integer(SC_CREATED),                         "CREATED");
		statusToString.put(new Integer(SC_EXPECTATION_FAILED),              "EXPECTATION_FAILED");
		statusToString.put(new Integer(SC_FORBIDDEN),                       "FORBIDDEN");
		// statusToString.put(new Integer(SC_FOUND),                        "FOUND");
		statusToString.put(new Integer(SC_GATEWAY_TIMEOUT),                 "GATEWAY_TIMEOUT");
		statusToString.put(new Integer(SC_GONE),                            "GONE");
		statusToString.put(new Integer(SC_HTTP_VERSION_NOT_SUPPORTED),      "HTTP_VERSION_NOT_SUPPORTED");
		statusToString.put(new Integer(SC_INTERNAL_SERVER_ERROR),           "INTERNAL_SERVER_ERROR");
		statusToString.put(new Integer(SC_LENGTH_REQUIRED),                 "LENGTH_REQUIRED");
		statusToString.put(new Integer(SC_METHOD_NOT_ALLOWED),              "METHOD_NOT_ALLOWED");
		statusToString.put(new Integer(SC_MOVED_PERMANENTLY),               "MOVED_PERMANENTLY");
		statusToString.put(new Integer(SC_MOVED_TEMPORARILY),               "MOVED_TEMPORARILY");
		statusToString.put(new Integer(SC_MULTIPLE_CHOICES),                "MULTIPLE_CHOICES");
		statusToString.put(new Integer(SC_NO_CONTENT),                      "NO_CONTENT");
		statusToString.put(new Integer(SC_NON_AUTHORITATIVE_INFORMATION),   "NON_AUTHORITATIVE_INFORMATION");
		statusToString.put(new Integer(SC_NOT_ACCEPTABLE),                  "NOT_ACCEPTABLE");
		statusToString.put(new Integer(SC_NOT_FOUND),                       "NOT_FOUND");
		statusToString.put(new Integer(SC_NOT_IMPLEMENTED),                 "NOT_IMPLEMENTED");
		statusToString.put(new Integer(SC_NOT_MODIFIED),                    "NOT_MODIFIED");
		statusToString.put(new Integer(SC_OK),                              "OK");
		statusToString.put(new Integer(SC_PARTIAL_CONTENT),                 "PARTIAL_CONTENT");
		statusToString.put(new Integer(SC_PAYMENT_REQUIRED),                "PAYMENT_REQUIRED");
		statusToString.put(new Integer(SC_PRECONDITION_FAILED),             "PRECONDITION_FAILED");
		statusToString.put(new Integer(SC_PROXY_AUTHENTICATION_REQUIRED),   "PROXY_AUTHENTICATION_REQUIRED");
		statusToString.put(new Integer(SC_REQUEST_ENTITY_TOO_LARGE),        "REQUEST_ENTITY_TOO_LARGE");
		statusToString.put(new Integer(SC_REQUEST_TIMEOUT),                 "REQUEST_TIMEOUT");
		statusToString.put(new Integer(SC_REQUEST_URI_TOO_LONG),            "REQUEST_URI_TOO_LONG");
		statusToString.put(new Integer(SC_REQUESTED_RANGE_NOT_SATISFIABLE), "REQUESTED_RANGE_NOT_SATISFIABLE");
		statusToString.put(new Integer(SC_RESET_CONTENT),                   "RESET_CONTENT");
		statusToString.put(new Integer(SC_SEE_OTHER),                       "SEE_OTHER");
		statusToString.put(new Integer(SC_SERVICE_UNAVAILABLE),             "SERVICE_UNAVAILABLE");
		statusToString.put(new Integer(SC_SWITCHING_PROTOCOLS),             "SWITCHING_PROTOCOLS");
		statusToString.put(new Integer(SC_TEMPORARY_REDIRECT),              "TEMPORARY_REDIRECT");
		statusToString.put(new Integer(SC_UNAUTHORIZED),                    "UNAUTHORIZED");
		statusToString.put(new Integer(SC_UNSUPPORTED_MEDIA_TYPE),          "UNSUPPORTED_MEDIA_TYPE");
		statusToString.put(new Integer(SC_USE_PROXY),                       "USE_PROXY");
	}


	// Log log = LogFactory.getLog(this.getClass());

	private int                 contentLength  = -1;
	private int                 statusCode     = -1;
	private int                 errorCode      = -1;
	private String              contentType    = "";
	private String              statusMessage  = "";
	private String              errorMessage   = "";
	private String              redirectURL    = "";
	private List<Cookie>        cookies        = new ArrayList<Cookie>();
	private Set<String>         headerNames    = new LinkedHashSet<String>();
	private Map<String,Long>    dateHeaders    = new HashMap<String,Long>();
	private Map<String,Integer> intHeaders     = new HashMap<String,Integer>();
	private Map<String,Vector<String>> headers = new HashMap<String,Vector<String>>();


	public HttpServletResponseWrapper(javax.servlet.http.HttpServletResponse response) {
		super(response);
	}


  public int getContentLength() { return contentLength;}

  public void setContentLength(int i) 
	{ 
		contentLength = i; 
		super.setContentLength(i);
	}


  public String getContentType() { return contentType;}

  public void setContentType(String s) 
	{ 
		contentType = s; 
		super.setContentType(s);
	}


	public void addCookie(Cookie cookie) 
	{
		cookies.add(cookie);
		super.addCookie(cookie);
	}

	public Cookie[] getCookies() {
		return (Cookie[])this.cookies.toArray(new Cookie[this.cookies.size()]);
	}


	public void addDateHeader(String name, long date)
	{
		this.headerNames.add(name);
		this.dateHeaders.put(name, new Long(date));
		super.addDateHeader(name,date);
	}

	public void setDateHeader(String name, long date)
	{
		this.headerNames.add(name);
		this.dateHeaders.put(name, new Long(date));
		super.setDateHeader(name,date);
	}

	public long getDateHeader(String name) 
	{
		Long out = this.dateHeaders.get(name);
		return (out != null) ? out.longValue() : -1;
	}


	public void addIntHeader(String name, int i)
	{
		this.headerNames.add(name);
		this.intHeaders.put(name, new Integer(i));
		super.addIntHeader(name,i);
	}

	public void setIntHeader(String name, int i)
	{
		this.headerNames.add(name);
		this.intHeaders.put(name, new Integer(i));
		super.setIntHeader(name,i);
	}

	public int getIntHeader(String name) 
	{
		Integer out = this.intHeaders.get(name);
		return (out != null) ? out.intValue() : -1;
	}


	public void addHeader(String name, String v)
	{
		this.headerNames.add(name);

		if (this.headers.get(name) != null) {
			this.headers.get(name).add(v);
		} else {
			Vector<String> list = new Vector<String>();
			list.add(v);
			this.headers.put(name, list);
		}

		super.addHeader(name,v);
	}


	public void setHeader(String name, String v)
	{
		this.headerNames.add(name);

		Vector<String> list = new Vector<String>();
		list.add(v);
		this.headers.put(name, list);

		super.setHeader(name,v);
	}


	public String getHeader(String name)
	{
		Vector<String> out = this.headers.get(name);
		return (out != null && out.size() > 0 && out.get(0) != null) ? out.get(0) : "";
	}

	public Enumeration getHeaderNames()
	{
		Vector<String> out = new Vector<String>(this.headerNames);
		return out.elements();
	}

	public Enumeration getHeaders(String name)
	{
		Vector<String> out = this.headers.get(name);
		return out != null ? out.elements() : new Vector<String>().elements();
	}


	public void sendRedirect(String location) throws IOException {
		this.redirectURL = location;
		super.sendRedirect(location);
	}

	public String getRedirectURL() {
		return this.redirectURL;
	}


	public void sendError(int i) throws IOException
	{
		this.errorCode = i;
		super.sendError(i);
	}

	public void sendError(int i, String msg) throws IOException
	{
		this.errorCode    = i;
		this.errorMessage = msg;
		super.sendError(i,msg);
	}

	public int getErrorCode() {
		return this.errorCode;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}


	public void setStatus(int i)
	{
		this.statusCode = i;
		super.setStatus(i);
	}

	public void setStatus(int i, String msg)
	{
		this.statusCode    = i;
		this.statusMessage = msg;
		super.setStatus(i,msg);
	}


	public int getStatusCode() {
		return this.statusCode;
	}

	public String getStatusMessage() {
		return this.statusMessage;
	}

	public String getReadableStatus() {
		String out = statusToString.get(new Integer(this.statusCode));
		return out != null ? out : "UNKNOWN_STATUS_CODE";
	}

} // end class


