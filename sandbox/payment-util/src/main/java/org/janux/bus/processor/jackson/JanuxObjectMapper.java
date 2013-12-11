package org.janux.bus.processor.jackson;

import java.util.List;

import org.codehaus.jackson.map.Module;
import org.codehaus.jackson.map.ObjectMapper;

/**
 ***************************************************************************************************
 * This is a convenience class that extends org.codehaus.jackson.map.ObjectMapper, with constructors
 * that will register either a single or a List of org.codehaus.jackson.map.Module instances, and
 * which make the ObjectMapper class easier to configure via Spring; ObjectMapper is the main facade
 * class in the jackson model that provides a unique facade to the various for
 * serializers/deserializers that may be necessary to map a java object graph to/from a json string;
 * an ObjectMapper's behavior can be customized by registering Module instances with custom
 * Serializers and Deserializers to handle specific types.
 * 
 * @author  <a href="mailto:alberto.buffagni@janux.org">Alberto Buffagni</a>
 * @since 0.4.0 - 2011-11-17
 ***************************************************************************************************
 */
public class JanuxObjectMapper extends ObjectMapper 
{
	public JanuxObjectMapper(Module module) {
		this.registerModule(module);
	}
	
	public JanuxObjectMapper(List<Module> moduleList) {
		for (int i = 0; i < moduleList.size(); i++) {
			Module module = moduleList.get(i);
			this.registerModule(module);			
		}
	}
}


