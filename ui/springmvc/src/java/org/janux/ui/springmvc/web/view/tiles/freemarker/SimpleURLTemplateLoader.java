package org.janux.ui.springmvc.web.view.tiles.freemarker;

import java.net.MalformedURLException;
import java.net.URL;

import freemarker.cache.URLTemplateLoader;

/*
 * Support including FreeMarker templates from web URLs. It's surprising to me that there's
 * not better built-in support for this.
 * The existing FreeMarker flow is kind of weird. FreeMarker will actually barf
 * if getURL() return a value, but the URL doesn't exist. This is especially annoying
 * because FreeMarker does locale-checking (e.g. looks for filename_en_US and filename_en).
 * This can be turned off, but we may want to take advantage of it to some extent. Ugh.
 * 
 * So, I've implemented a simple approach where you specify a URL base in the config, and
 * then use the full URL in your #include or #import statement. This limits the barfing to
 * files that are at least being deliberately referenced. For the moment, though, clients
 * will have to make sure to provide the proper locale suffix.
 */
public class SimpleURLTemplateLoader extends URLTemplateLoader
{
	String urlBase;
	
	public SimpleURLTemplateLoader(String urlBase) throws MalformedURLException
	{
		this.urlBase = urlBase;
	}
	
	@Override
	protected URL getURL(String urlStr)
	{
		URL url = null;
		
		try
		{
			if (urlStr.startsWith(urlBase))
			{
				url = new URL(urlStr);
			}
		}
		catch (MalformedURLException e)
		{
			// nothing to do?
		}

		return url;
	}
}
