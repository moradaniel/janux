package org.janux.ui.web;

import java.util.Enumeration;

import javax.servlet.http.Cookie;

/**
 ***************************************************************************************************
 * This interface extends the Servlet API interface with the same name with accessors for the various
 * fields of the response; the intent of this interface is to make it possible to inject different
 * implementations of HttpServletResponseWrappers in filters that can inspect and report on the
 * HttpServletResponse, for example in a logging filter.  Whenever possible, the signature of the
 * interface was made similar to those in the interface HttpServletRequest to facilitate code
 * re-use.
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.1 - 2011-03-24
 ***************************************************************************************************
 */
public interface HttpServletResponse extends javax.servlet.http.HttpServletResponse
{
	/** Returns the length of the content body in the response, also available as the HTTP Content-Length header. */
	int getContentLength();

	/** 
	 * Returns an array containing all of the Cookie objects set by the application and/or the app
	 * server in the response. 
	 */
	Cookie[] getCookies();

	/** 
	 * Returns the value of the specified response header as a long value that represents a Date object, 
	 * or -1 if the header has not been set.
	 */
	long getDateHeader(String name);

	/** Returns the value of the specified request header as a String. */
	String getHeader(String name);

	/** Returns an enumeration of all the header names this request contains. */
	Enumeration getHeaderNames();

	/** Returns all the values of the specified request header as an Enumeration of String objects. */
	Enumeration getHeaders(String name);

	/** Returns the value of the specified request header as an int, or -1 if the header has not been set*/
	int getIntHeader(String name);

	/** Returns the URL of the temporory redirect response being sent to the client */
	String getRedirectURL();

	/** Returns the error code sent to the client, or -1 if none was set */
	int getErrorCode();

	/** Returns the error message sent to the client, or the empty String if none was set */
	String getErrorMessage();

	/** Returns the status code for this response, or -1 if none was set (abnormal) */
	int getStatusCode();

	String getStatusMessage();

	/** Translates the status or error code to a readable String */
	String getReadableStatus();
}
