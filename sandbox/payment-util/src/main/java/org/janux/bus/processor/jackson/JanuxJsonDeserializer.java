package org.janux.bus.processor.jackson;

import org.codehaus.jackson.map.JsonDeserializer;

public abstract class JanuxJsonDeserializer<T> extends JsonDeserializer<T>{

	/**
     * Method for accessing type of Objects this deserializer can handle.
     */
	public abstract Class<T> handledType();
	
}
