package biz.janux.payment.gateway.controllers.creditcard;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class JsonPropertyEditor <T> extends PropertyEditorSupport {

	ObjectMapper objectMapper;
	TypeReference<T> type;

	public JsonPropertyEditor(TypeReference<T> type, ObjectMapper objectMapper) {
		this.type = type;
		this.objectMapper = objectMapper;
	}

	public void setAsText(String text) {
		Object object = null;
		try {
			object = getObjectMapper().readValue(text, type);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setValue(object);
	}

	public String getAsText() {
		String string = null;
		try {
			string = getObjectMapper().writeValueAsString(getValue());
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return string;
	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public TypeReference<T> getType() {
		return type;
	}

	public void setType(TypeReference<T> type) {
		this.type = type;
	}

}
