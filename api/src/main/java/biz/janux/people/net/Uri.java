package biz.janux.people.net;

import java.net.URI;

import biz.janux.people.ContactMethod;

/**
 * sub-interface of ContactMethod that generically represents an electronic
 * address or Uniform Resource Identifier; this interface provides a
 * string 'address' that can be used to persist a string representation of the
 * URI, and a getUri method that parses the string and returns a java.net.URI
 * instance
 */
public interface Uri extends ContactMethod
{
	/** the string representation of this Uniform Resource Identifier */
	String getAddress();
	void setAddress(String s);

	/** the java language representation of this Uniform Resource Identifier */
	URI getUri() throws java.net.URISyntaxException;
}
