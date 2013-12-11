package org.janux.ui.web.catalina;

import freemarker.template.Configuration;

import java.io.File;
import java.io.IOException;

import java.util.Enumeration;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.StringTokenizer;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.util.StringManager;
import org.apache.catalina.valves.ValveBase;
import org.apache.catalina.valves.Constants;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import org.janux.ui.web.HttpRequestFormatter;
import org.janux.ui.web.ModelFormatter;
import org.janux.ui.web.catalina.HttpResponseFormatter;


/**
 *************************************************************************************************
 * By default the logger will default to the logger of the tomcat container that contains this
 * valve. Namely, if the valve element is under a Host element it would be something like:
 * <code>org.apache.catalina.core.ContainerBase.[Catalina].[localhost]</code>
 * and if the valve is under a Context element if would be something like:
 * <code>org.apache.catalina.core.ContainerBase.[Catalina].[localhost].[/some_context_path]</code>
 * Alternatively, a different logger name can be provided by setting the loggerName attribute in the
 * RequestLoggingValve configuration.
 *************************************************************************************************
 */
public class RequestLoggingValve extends ValveBase 
{
	private Log log;

	private static final String INFO =
		"org.janux.ui.web.catalina.RequestLoggingValve/1.0";

	/** the name of the request variable in the freemarker template: "request" */
	public final static String KEY_REQUEST          = "request";

	/** the name of the request header variable in the freemarker template: "req_headers" */
	public final static String KEY_REQUEST_HEADERS  = "req_headers";

	/** the name of the request paramaters variable in the freemarker template: "req_parms" */
	public final static String KEY_REQUEST_PARMS    = "req_parms";

	/** the name of the response variable in the freemarker template: "response" */
	public final static String KEY_RESPONSE         = "response";

	/** the name of the response headers variable in the freemarker template: "resp_headers" */
	public final static String KEY_RESPONSE_HEADERS = "resp_headers";

	/** the name of the request/response duration variable in the freemarker template: "duration" */
	public final static String KEY_DURATION         = "duration";


	private String templatePath; // initialized at runtime via Valve configuration
	private String templateName; // initialized at runtime via Valve configuration
	private String loggerName;   // initialized at runtime via Valve configuration


	/** 
	 * A comma separated list of regular expressions strings, which are matched against the URI of
	 * the request to determine which requests should be logged.  If the URI matches any of the
	 * expressions in the list, the request will be logged.  If this field is empty, all requests are
	 * logged, provided that they do not match a pattern in the exclude list.  The exclude list is
	 * matched after the include list. If both the include list and the exclude list are null or
	 * empty, all requests are logged.  
	 * <p>
	 * This field is initiliazed directly at runtime when the Valve is configured by the container. We
	 * thus made the field public and ommitted accessors to make it clear that accessors are not
	 * used to initialize this field.  
	 * </p>
	 */
	private String includes;

	/** 
	 * A comma separated list of regular expressions strings, which are matched against the URI of
	 * the request to prevent requests from being logged.  If the URI matches any of the
	 * expressions in the list, the request will not be logged.  If this field is empty, all
	 * requests that pass the include list test pass. The exclude list is matched after the include
	 * list. If both the include list and the exclude list are null or empty, all requests are logged.
	 * <p>
	 * This field is initiliazed directly at runtime when the Valve is configured by the container. We
	 * thus made the field public and ommitted accessors to make it clear that accessors are not
	 * used to initialize this field.
	 * </p>
	 */
	private String excludes;

	private Configuration freemarkerConfig;

	private HttpRequestFormatter  requestFormatter;
	private HttpResponseFormatter responseFormatter;
	private ModelFormatter        modelFormatter;
	private List<Pattern> includePatterns;
	private List<Pattern> excludePatterns;

	/**  The StringManager for this package.  */
	protected static StringManager sm = StringManager.getManager(Constants.Package);


	/** 
	 * Use a logger named after this class, or one specifically provided via the getLoggerName()
	 * attribute. We need both fields to be instantiated prior in order to determine which logger to
	 * use.  ValveBase does not provide a constructor, and the docs do not make it clear when/how
	 * ValveBase.container and the parsed configuration attributes are instantiated.  To prevent
	 * potential NullPointerExceptions, this method instantiates the log field upon its first usage,
	 * presumably after the Valve has been fully instantiated.
	 */
	private Log getLog() 
	{
		if (this.log == null) 
		{
			this.log = LogFactory.getLog(this.getLoggerName());
			this.log.info("RequestLoggingValve: initializing valve with named logger: " + this.getLoggerName()) ;
		}

		return this.log;
	}


	/** Return descriptive information about this Valve implementation. */
	public String getInfo() { return INFO; }

	/** 
	 * The location of the directory in which the freemarker templates are to be found; 
	 * for the moment this should be an absolute filesystem path.
	 */
  public String getTemplatePath() { return templatePath;}
  public void setTemplatePath(String s) { templatePath = s; }

	/** 
	 * The name of the freemarker template to be used to format request/responses
	 */
  public String getTemplateName() { return templateName;}
  public void setTemplateName(String s) { templateName = s; }

	/** 
	 * Sets an explicit logger name; if null or the empty string in the config file, 
	 * defaults to this.getClass().getName()
	 */
  public String getLoggerName() 
	{ 
		if (this.loggerName == null || this.loggerName.trim().length() == 0) {
			this.loggerName = this.getClass().getName();
		}
		return loggerName;
	}
  public void setLoggerName(String s) { loggerName = s; }

	
  public String getIncludes() {return includes;}
  public void setIncludes(String regexList) { this.includes = regexList; }


  public String getExcludes() { return excludes;}
  public void setExcludes(String regexList) { this.excludes = regexList; }


	/** Lazily instantiates a list of compiled include patterns based on the list returned by this.includes */
  private List<Pattern> getIncludePatterns() 
	{ 
		if (this.includePatterns == null) {
			if (getLog().isInfoEnabled()) this.getLog().info("RequestLoggingValve: instantiating includePatterns with: '" + this.includes + "'");
			this.includePatterns = compilePatterns(parseStructuredString(this.includes));
		}

		return this.includePatterns;
	}


	/** Lazily instantiates a list of compiled exclude patterns based on the list returned by this.excludes */
  private List<Pattern> getExcludePatterns() 
	{ 
		if (this.excludePatterns == null) {
			if (getLog().isInfoEnabled()) this.getLog().info("RequestLoggingValve: instantiating excludePatterns with: '" + this.excludes + "'");
			this.excludePatterns = compilePatterns(parseStructuredString(this.excludes));
		}
		return this.excludePatterns;
	}



	private Configuration getFreemarkerConfiguration() throws IOException
	{
		if (this.freemarkerConfig == null) {
			freemarkerConfig = new Configuration();
			freemarkerConfig.setDirectoryForTemplateLoading(new File(this.getTemplatePath()));
		}
		return this.freemarkerConfig;
	}

	private HttpRequestFormatter getRequestFormatter() throws IOException
	{
		if (this.requestFormatter == null) {
			requestFormatter = new HttpRequestFormatter(this.getFreemarkerConfiguration());
		}
		return this.requestFormatter;
	}

	private HttpResponseFormatter getResponseFormatter() throws IOException
	{
		if (this.responseFormatter == null) {
			responseFormatter = new HttpResponseFormatter(this.getFreemarkerConfiguration());
		}
		return this.responseFormatter;
	}

	private ModelFormatter getModelFormatter() throws IOException
	{
		if (this.modelFormatter == null) {
			modelFormatter = new ModelFormatter(this.getFreemarkerConfiguration(), this.getTemplateName() );
		}
		return this.modelFormatter;
	}


	/**
	 * Log the interesting request parameters, invoke the next Valve in the
	 * sequence, and log the interesting response parameters.
	 *
	 * @param request The servlet request to be processed
	 * @param response The servlet response to be created
	 *
	 * @exception IOException if an input/output error occurs
	 * @exception ServletException if a servlet error occurs
	 */
	public void invoke(Request request, Response response)
		throws IOException, ServletException 
	{
		long recvd = System.currentTimeMillis();
		boolean logRequest = getLog().isInfoEnabled() && this.logRequest(request.getRequestURI());

		if (logRequest && getLog().isDebugEnabled()) getLog().debug(this.getRequestFormatter().format((HttpServletRequest)request));

		// Perform the request
		getNext().invoke(request, response);

		if (logRequest) getLog().info(this.getModelFormatter().format(this.buildModel(request, response, System.currentTimeMillis() - recvd)));
		// if (logRequest) getLog().info(this.getResponseFormatter().format(response));
	}


	/**
	 * Determines whether or not the request passed should be logged based on the uri, and the include
	 * and exclude patterns of the Valve
	 */
	private boolean logRequest(String uri) 
	{
		if (uri == null) { throw new IllegalArgumentException("Attempting to log request with null URI"); }

		boolean pleaseLog = true;

		// if an include pattern is specified, make sure that it matches
		if (this.getIncludePatterns() != null && this.getIncludePatterns().size() > 0) {
			pleaseLog = this.matchesAny(uri, this.getIncludePatterns());
		}

		// if pleaseLog is already false, don't check the exclude list
		if ( pleaseLog && this.matchesAny(uri, this.getExcludePatterns()) ) {
			pleaseLog = false;
		}

		return pleaseLog;
	}


	private Map<String,Object> buildModel(Request request, Response response, long duration) 
	{
		final Map<String, Object> model = new HashMap<String, Object>();
		model.put(KEY_REQUEST,          request);
		model.put(KEY_REQUEST_HEADERS,  mapifyHeaders(request));
		model.put(KEY_REQUEST_PARMS,    request.getParameterMap());
		model.put(KEY_RESPONSE,         response);
		model.put(KEY_RESPONSE_HEADERS, mapifyHeaders(response));
		model.put(KEY_DURATION,         duration);

		return model;
	}

	/** 
	 * The Response object has similar but distinct methods for retrieving request headers and
	 * parameters, and response headers; these differences make it difficult to factor similar code at
	 * the freemarker level; converting the headers and parameters into maps of string arrays makes it
	 * possible to properly factor the display logic
	 */
	private static Map<String, List<String>> mapifyHeaders(Request request) 
	{
		Map<String, List<String>> out = new HashMap<String, List<String>>();

		Enumeration keys = request.getHeaderNames();
		
		while (keys.hasMoreElements()) 
		{
			String k = (String)keys.nextElement();
			Enumeration values = request.getHeaders(k);
			List<String> list = new ArrayList<String>();
			while (values.hasMoreElements()) { list.add(values.nextElement().toString()); }
			out.put(k, list);
		}

		return out;
	}


	private static Map<String, List<String>> mapifyHeaders(Response response) 
	{
		Map<String, List<String>> out = new HashMap<String, List<String>>();

		String[] keys = response.getHeaderNames();

		for (int i=0; i < keys.length ; i++)
		{
			String k = keys[i];
			String[] values = response.getHeaderValues(k);
			List<String> list = new ArrayList<String>();
			for (int j=0; j < values.length ; j++) { list.add(values[j]); }
			out.put(k,list);
		}
		
		return out;
	}


	/** 
	 * Parses a comma separated list of Strings into a List of Strings, 
	 * not including the separators
	 */
	protected static List<String> parseStructuredString(String regexList) 
	{
		List<String> out = new ArrayList<String>();
		
		if (regexList != null) {
			StringTokenizer tokens = new StringTokenizer(regexList, ",");
			while (tokens.hasMoreTokens()) { out.add(tokens.nextToken().trim()); }
		}

		return out;
	}

	/** 
	 * Takes a list of regexs in String form and returns the corresponding List of
	 * {@Link Pattern} instances. If the list of regExps is null or empty, 
	 * returns an empty List<Pattern>.
	 */
	protected static List<Pattern> compilePatterns(final List<String> regExps)
	{
		List<Pattern> out = new ArrayList<Pattern>();
		
		if (regExps != null) {
			for ( String regex : regExps ) {
				out.add(Pattern.compile(regex));
			}
		}

		return out;
	}


	/**
	 * Iterates through the list of Patterns, and returns true if the string matches at least one of
	 * the Pattern objects provided; returns false if the list is null or empty.
	 *
	 * @throws IllegalArgumentException if the string is null
	 */
	protected boolean matchesAny(String aString, List<Pattern> patterns) 
	{
		if (aString == null) { throw new IllegalArgumentException("Attempting to match null String"); }

		if (patterns != null) {
			for ( Pattern p : patterns ) {
				if (getLog().isDebugEnabled()) {
					getLog().debug("string: '" + aString + "' matches: '" + p + "' is " + p.matcher(aString).matches() );
				}
				if (p.matcher(aString).matches()) { return true; }
			}
		}

		return false;
	}


	/**
	 * Return a String rendering of this object.
	 */
	public String toString() 
	{
		StringBuffer sb = new StringBuffer("RequestLoggingValve[");
		if (container != null)
			sb.append(container.getName());
		sb.append("]");
		return (sb.toString());
	}

}
