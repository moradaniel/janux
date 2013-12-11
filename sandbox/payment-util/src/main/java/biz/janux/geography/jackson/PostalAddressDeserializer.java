package biz.janux.geography.jackson;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.ObjectMapper;

import biz.janux.geography.*;

import org.janux.bus.processor.jackson.JanuxJsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *************************************************************************************************** 
 * This PostalAddressDeserializer parses a json string of the form:
 * 
 * <pre>
 * {"line1":"123 Main Street",
 * "line2":"Annex C",
 * "line3":"Suite 104",
 * "city":"Albuquerque",
 * "stateProvince":"CA",
 * "postalCode":"94952",
 * "country":"US"}
 * </pre>
 * 
 * This Deserializer assumes that the PostalAddress representation is being
 * provided in string format, for example via a form in a ui, and makes no
 * attempt to convert the string representation of the Country, StateProvince or
 * City, into a corresponding object; this task is left to the back-end and/or a
 * suitable implementation of the GeographyService.setCityStateCountry method
 * 
 * @author <a href="mailto:philippe.paravicini@janux.org">Philippe
 *         Paravicini</a>
 * @since 0.4.0 - 2011-11-28
 *************************************************************************************************** 
 */
public class PostalAddressDeserializer extends JanuxJsonDeserializer<PostalAddress> {
	SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
	Logger log = LoggerFactory.getLogger(this.getClass());

	public Class<PostalAddress> handledType() {
		return PostalAddress.class;
	}

	@Override
	public PostalAddress deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		PostalAddress ob = new PostalAddressImpl();
		JsonToken t = jp.getCurrentToken();

		try {

			if (t == JsonToken.START_OBJECT) {
				t = jp.nextToken();
			}

			String fieldName = jp.getCurrentName();
			if (t == JsonToken.FIELD_NAME && fieldName.equals("postalAddress")) {
				t = jp.nextToken();
				t = jp.nextToken();
			}

			for (; t == JsonToken.FIELD_NAME; t = jp.nextToken()) {
				fieldName = jp.getCurrentName();
				t = jp.nextToken();

				if (fieldName.equals("line1")) {
					ob.setLine1(jp.getText());
				} else if (fieldName.equals("line2")) {
					ob.setLine2(jp.getText());
				} else if (fieldName.equals("line3")) {
					ob.setLine3(jp.getText());
				} else if (fieldName.equals("postalCode")) {
					ob.setPostalCode(jp.getText());
				} else if (fieldName.equals("city")) {
					ob.setCityAsString(jp.getText());
				} else if (fieldName.equals("stateProvince")) {
					ob.setStateProvinceAsString(jp.getText());
				} else if (fieldName.equals("country")) {
					ob.setCountryAsString(jp.getText());
				}
				continue;
			}
		} catch (Exception e) {
			log.error("Error in the deserialization", e);
			throw new RuntimeException("Error in the deserialization", e);
		}
		return ob;
	}
}
