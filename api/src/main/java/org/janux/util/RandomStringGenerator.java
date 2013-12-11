package org.janux.util;

/**
 * Interface for a class that generates random string codes of 128bits; such generators can
 * be used to generate passwords, alpha-numeric identifiers for orders, or mock data;
 * 
 * 
 * @author albertobuffagni@gmail.com
 * @since 0.4
 */
public interface RandomStringGenerator {

	/** returns a random string with the default length */
	String getString();
	
}
