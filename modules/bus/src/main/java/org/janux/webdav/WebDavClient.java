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

/**
 * add comments here
 * @author Tony Givargis
 * @version 1.0
 */
public class WebDavClient implements IWebDavClient
{
	private Client client = null;

    /**
     * Constructor for the WebDavClient
     * @param host fully qualified URL to the remote WebDAV server
     * @param userName optional: if required, then the user name
     * @param password optional: if required, then the password
     * @throws Exception
     */
	public WebDavClient(String host, String userName, String password) throws WebDavException
	{
		this.client = new Client();
		this.client.connect(host, userName, password,false);
	}

	public String[] listFiles() throws WebDavException
	{
		return null;
//		return this.client.ls();
	}

	public String saveFile(String filename, String path) throws WebDavException
	{
		return (this.client.put(filename, path));
	}

	public File getFile(String path, String filename) throws WebDavException
	{
		return null;
//		return (this.client.getFile(path, filename));
	}

	public boolean deleteFile(String remoteFile) throws WebDavException
	{
		return (this.client.delete(remoteFile));
	}

	public void finalize() throws Throwable
	{
		super.finalize();
		client.disconnect();
	}

	public String getLastMessage()
	{
		return null;
//		return (this.client.status());
	}

}
