package biz.janux.payment.jackson;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.management.RuntimeErrorException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.janux.bus.processor.jackson.JanuxJsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biz.janux.geography.PostalAddress;
import biz.janux.payment.CreditCard;
import biz.janux.payment.CreditCardImpl;
import biz.janux.payment.CreditCardType;
import biz.janux.payment.CreditCardTypeImpl;

public class CreditCardDeserializer extends JanuxJsonDeserializer<CreditCard> 
{
	
	SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	public Class<CreditCard> handledType() {
		return CreditCard.class;
	}


	@Override
	public CreditCard deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		CreditCard ob = new CreditCardImpl();
		JsonToken t = jp.getCurrentToken();

		try {
			if (t == JsonToken.START_OBJECT) {
				t = jp.nextToken();
			}

			String fieldName = jp.getCurrentName();
			if (t == JsonToken.FIELD_NAME && fieldName.equals("creditCard")) {
				t = jp.nextToken();
				t = jp.nextToken();
			}

			for (; t == JsonToken.FIELD_NAME; t = jp.nextToken()) {
				fieldName = jp.getCurrentName();
				t = jp.nextToken();

				if (fieldName.equals("cardholderName")) {
					ob.setCardholderName(jp.getText());
				} else if (fieldName.equals("expiration")) {
					ob.setExpirationDate(formatter.parse(jp.getText()));
				} else if (fieldName.equals("number")) {
					if (jp.getText() != null && !jp.getText().equalsIgnoreCase(""))
						ob.setNumber(jp.getText());
				} else if (fieldName.equals("uuid")) {
					if (jp.getText() != null && !jp.getText().equalsIgnoreCase(""))
						((CreditCardImpl) ob).setUuid(jp.getText());
				} else if (fieldName.equals("numberMasked")) {
					ob.setNumberMasked(jp.getText());
				} else if (fieldName.equals("typeCode")) {
					ob.setTypeCode(jp.getText());
				} else if (fieldName.equals("type")) {
					String objectStringJson = jp.readValueAsTree().toString();
					ObjectMapper objectMapper = (ObjectMapper) jp.getCodec();
					Object desObject = objectMapper.readValue(objectStringJson, CreditCardTypeImpl.class);
					ob.setType((CreditCardType) desObject);
				} else if (fieldName.equals("billingAddress")) {
					String objectStringJson = jp.readValueAsTree().toString();
					ObjectMapper objectMapper = (ObjectMapper) jp.getCodec();
					Object desObject = objectMapper.readValue(objectStringJson, PostalAddress.class);
					ob.setBillingAddress((PostalAddress) desObject);
				} else if (fieldName.equals("businessUnitCode")) {
					ob.setBusinessUnitCode(jp.getText());
				} else if (fieldName.equals("swiped")) {
					ob.setSwiped(jp.getValueAsBoolean());
				}
				continue;
			}
			t = jp.nextToken();
		} catch (Exception e) {
			log.error("Error in the deserialization",e);
			throw new RuntimeException("Error in the deserialization", e);
		}
		return ob;
	}
}
