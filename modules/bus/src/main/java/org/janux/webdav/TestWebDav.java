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

public class TestWebDav
{

	public static void main(String[] args)
	{
		WebDavClient client = null;
		try
		{
			//	WebDavClient client = new WebDavClient("http://mail.incisix.com/incisix/tgivargis/Inbox",
			//	                                       "tgivargis",

			client = new WebDavClient("http://webdav.venuecom.com/calendar/test/private/calendars",
			                                       "test", "test");

			String[] files = client.listFiles();
			client.saveFile("joe.txt", null);
			File joe = client.getFile("", "Changes.eml");
			System.out.println("There are: " + files.length + " on the server");

		}
		catch (WebDavException ex)
		{
			String joe = ex.getMessage();
		}
	}


}
