/*
 * Copyright 2006 Kevin Irlen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.janux.webdav;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpURL;
import org.apache.commons.httpclient.HttpsURL;
import org.apache.commons.httpclient.URIException;
import org.apache.webdav.lib.WebdavResource;
import org.apache.webdav.lib.properties.ResourceTypeProperty;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Client
{
	Log log = LogFactory.getLog(this.getClass());
	/**
	 * The WebDAV resource.
	 */
	private WebdavResource webdavResource = null;
	/**
	 * The http URL on the client connection.
	 */
	private HttpURL httpURL;
	
	/**
	 * Debug level for all debug messages
	 */
	static final int DEBUG_ON = Integer.MAX_VALUE;

	/**
	 * Debug level for no debug messages
	 */
	static final int DEBUG_OFF = 0;

	/**
	 * Debug level
	 */
	int debugLevel = 0;

	private static HttpURL uriToHttpURL(String uri) throws URIException
	{
		return uri.startsWith("https") ? new HttpsURL(uri) : new HttpURL(uri);
	}
	
	protected Client()
	{
		
	}
	
	protected void connect(String uri, String userName, String password, boolean requirePassword) throws WebDavException
	{
		boolean success = false;
		
		if (!uri.endsWith("/") && !uri.endsWith("\\"))
		{
			uri += "/"; // append / to the path
		}

		try
		{
			// Set up for processing WebDAV resources
			httpURL = Client.uriToHttpURL(uri);
			
			if (requirePassword)
			{
				httpURL.setUserinfo(userName, password);
			}
			
			if (webdavResource == null)
			{
				webdavResource = new WebdavResource(httpURL,true);
				
				webdavResource.setDebug(debugLevel);
//				setPath(webdavResource.getPath());
			}
			else
			{
				webdavResource.close();
				webdavResource.setHttpURL(httpURL);
			}
			
			if (!((ResourceTypeProperty) webdavResource.getResourceType()).isCollection())
			{
				throw new WebDavException("Host is not a Collection");
			}
			
			success = true;
		}
		catch (HttpException ex)
		{
			if (!requirePassword && (ex.getReasonCode() == HttpStatus.SC_UNAUTHORIZED))
			{
				this.connect(uri, userName, password, true);
				success = true;
			}
			else
			{
				throw new WebDavException(ex);
			}
		}
		catch (IOException ex)
		{
			throw new WebDavException(ex);
		}
		finally
		{
			if (!success)
			{
				httpURL = null;
				webdavResource = null;
			}
		}
	}

	protected void disconnect()
	{
		try
		{
			webdavResource.close();
		}
		catch (IOException e)
		{
			// no point in throwing. TODO: is this true?
		}
		finally
		{
			// Make sure the connection closed.
			httpURL = null;
			webdavResource = null;
		}
	}
	
	private void buildPath(String path) throws IOException, WebDavException
	{
		String savePath = webdavResource.getPath();

		try
		{
			webdavResource.setPath(path);	// see if path exists
		}
		catch (HttpException buildItOurselves)
		{
			// nope. need to build it ourselves	
			StringTokenizer tokenizer = new StringTokenizer(path, "/\\");
			String partialPath = "";
	
			while (tokenizer.hasMoreTokens())
			{
				String dir = tokenizer.nextToken();
				partialPath += "/" + dir;
	
				try
				{
					webdavResource.setPath(partialPath);
				}
				catch(HttpException ex)
				{
					if (!webdavResource.mkcolMethod(partialPath))
					{
						throw new WebDavException("Couldn't make dir: " + partialPath);
					}
				}
			}
		}
		finally
		{
			webdavResource.setPath(savePath);
		}
	}

	private String getLeafName(String filename)
	{
		// get target filename from last portion of filename
		String leafName = null;
		StringTokenizer tokenizer = new StringTokenizer(filename, "/\\");

		while (tokenizer.hasMoreTokens())
		{
			leafName = tokenizer.nextToken();
		}
		
		return leafName;
	}
	
	private String getRemotePath(String path) throws WebDavException, IOException
	{
		String target = webdavResource.getPath();

		if (path != null)
		{
			target += path;
			
			this.buildPath(target);
		}

		return target;
	}

	protected boolean delete(String fileURL) throws WebDavException
	{
		boolean deleted = false;
		
		try
		{
			deleted = webdavResource.deleteMethod(fileURL);
		}
		catch (HttpException ex)
		{
			throw new WebDavException(ex);
		}
		catch (IOException ex)
		{
			throw new WebDavException(ex);
		}
		
		return deleted;
	}
	
	protected String put(String filename, String path) throws WebDavException
	{
		File file = new File(filename);
		String leafName = null;
		String dest = null;
		
		if (!file.exists())
		{
			String msg = "Cannot find file: " + filename;
			log.error(msg);
			throw new WebDavException(msg);
		}
		
		try
		{
			leafName = getLeafName(filename);
			dest = getRemotePath(path);
			dest += leafName;
			
			if (!webdavResource.putMethod(dest, file))
			{
				String msg = "Unable to PUT file: " + webdavResource.getStatusMessage();
				log.error(msg);
				throw new WebDavException(msg);
			}
		}
		catch (HttpException ex)
		{
			String msg = "HttpException while attempting to PUT file";
			log.error(msg, ex);
			throw new WebDavException(ex);
		}
		catch (IOException ex)
		{
			String msg = "IOException while attempting to PUT file";
			log.error(msg, ex);
			throw new WebDavException(ex);
		}
		
		return leafName;
	}
}
