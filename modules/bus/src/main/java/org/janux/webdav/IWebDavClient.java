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

/**
 * This is the WebDav Client interface.  Fill in info here
 * @author Tony Givargis
 * @version 1.0
 */
public interface IWebDavClient
{
	/**
	 * List files that are on the remote server
	 *
	 * @return a string array of file names on the remote server
	 * @throws Exception
	 */
	public String[] listFiles() throws WebDavException;

	/**
	 * Saves a local file to the server (similar to "put" in FTP)
	 *
	 * @param filename the fully qualified local file name
	 * @param path     optional: remote path to store the file.  If not is
	 *                 specified, then the default (root) is used.
	 * @return fully qualified remote path name if successful; null if not
	 * @throws IOException
	 * @throws Exception
	 */
	public String saveFile(String filename, String path) throws WebDavException;

	/**
	 * Gets a file from the server (similar to "get" in FTP)
	 *
	 * @param path     optional: local path to store the file.  If none specified, then
	 *                 it uses the current path
	 * @param filename name of remote file to get
	 * @return a File of the remote file that was copied
	 * @throws Exception
	 */
	public File getFile(String path, String filename) throws WebDavException;

	/**
	 * Deletes the remote file
	 *
	 * @param remoteFile name of remote file to be deleted
	 * @return true if successful
	 * @throws Exception
	 */
	public boolean deleteFile(String remoteFile) throws WebDavException;
	/**
	 * Gets last HTTP status Message
	 * @return HTTP status code from last transaction
	 */
	public String getLastMessage();

}
