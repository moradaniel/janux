package org.janux.ui.web;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.janux.bus.processor.ObjectFormatterGeneric;

/**
 ***************************************************************************************************
 * Standand servlet api filter that can be used to log detailed information of a HttpServletRequest
 * inside a web application containing; the class that formats the string representation of the Request
 * can be injected by providing an instance of {@link ObjectFormatterGeneric<HttpServletRequest>}.
 * For this purpose, we provide {@link HttpRequestFormatter} and its collaborator {@link CookieFormatter}
 * which externalize the formatting via Freemarker Templates, thus making it possible to change the
 * formatting of the request without restarting the servlet container.
 *
 * Use Spring's org.springframework.web.filter.DelegatingFilterProxy to configure this Filter via
 * the Spring ApplicationContext
 * 
 * @see org.janux.ui.web.HttpRequestFormatter
 * @see org.janux.ui.web.HttpResponseFormatter
 * @see org.janux.ui.web.CookiesFormatter
 *
 * @author	<a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.1 - 2011-03-22
 ***************************************************************************************************
 */
public class HttpRequestLoggingFilter implements Filter
{
	Log log = LogFactory.getLog(this.getClass());

	private FilterConfig filterConfig;

	private ObjectFormatterGeneric<HttpServletRequest>   requestFormatter;
	private ObjectFormatterGeneric<HttpServletResponse>  responseFormatter;


  public ObjectFormatterGeneric<HttpServletRequest> getRequestFormatter() { 
		return requestFormatter;
	}

  public void setRequestFormatter(ObjectFormatterGeneric<HttpServletRequest> o) { 
		requestFormatter = o; 
	}


  public ObjectFormatterGeneric<HttpServletResponse> getResponseFormatter() { 
		return responseFormatter;
	}

  public void setResponseFormatter(ObjectFormatterGeneric<HttpServletResponse> o) { 
		responseFormatter = o; 
	}


	public void doFilter(
			ServletRequest request, ServletResponse response, FilterChain chain)
	{
		try
		{
			// log/filter request prior to invocation
			if (log.isInfoEnabled()) {
				if (this.getRequestFormatter() == null) log.warn ("RequestFormatter is null");
				log.info("Request = " + this.getRequestFormatter().format( (HttpServletRequest)request ));
			}

			HttpServletResponseWrapper wrappedResponse = 
				new HttpServletResponseWrapper((javax.servlet.http.HttpServletResponse)response);

			chain.doFilter (request, wrappedResponse);

			// log/filter response after invocation
			if (log.isInfoEnabled()) {
				if (this.getResponseFormatter() == null) log.warn ("ResponseFormatter is null");
				log.info("Response = " + this.getResponseFormatter().format( wrappedResponse ));
			}

		} catch (IOException io) {
			log.warn(io);
		} catch (ServletException se) {
			log.warn(se);
		}
	}


	public void init(FilterConfig filterConfig) {
		this.setFilterConfig(filterConfig);
	}


	public void destroy() 
	{
		if (log.isInfoEnabled()) log.info("Destroying " + this.getClass() );
		this.setFilterConfig(null);
	}


	public FilterConfig getFilterConfig() {
		return this.filterConfig;
	}

	public void setFilterConfig (FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

} // end class
