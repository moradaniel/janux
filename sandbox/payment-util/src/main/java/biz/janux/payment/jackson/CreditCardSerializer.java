package biz.janux.payment.jackson;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.SerializerProvider;

import biz.janux.payment.CreditCard;
import org.janux.bus.processor.jackson.JanuxJsonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *************************************************************************************************** 
 * This class is used to serialize a CreditCard into a Json string
 * 
 * @author <a href="mailto:alberto.buffagni@janux.org">Alberto Buffagni</a>
 * @since 0.4.0 - 2011-11-17
 *************************************************************************************************** 
 */
public class CreditCardSerializer extends JanuxJsonSerializer<CreditCard> {
	SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
	Logger log = LoggerFactory.getLogger(this.getClass());

	public Class<CreditCard> handledType() {
		return CreditCard.class;
	}

	public void serialize(CreditCard creditCard, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		try {
			jgen.writeStartObject();

			if (creditCard.getUuid() != null)
				jgen.writeStringField("uuid", creditCard.getUuid());

			if (creditCard.getBusinessUnitCode() != null)
				jgen.writeObjectField("businessUnitCode", creditCard.getBusinessUnitCode());

			if (creditCard.getNumber() != null)
				jgen.writeStringField("number", creditCard.getNumber());

			if (creditCard.getNumberMasked() != null)
				jgen.writeStringField("numberMasked", creditCard.getNumberMasked());

			if (creditCard.getExpirationDate() != null)
				jgen.writeStringField("expiration", formatter.format(creditCard.getExpirationDate()));

			/*
			 * if (creditCard.getType()!=null)
			 * jgen.writeObjectField("type",creditCard.getType());
			 */

			if (creditCard.getTypeCode() != null)
				jgen.writeObjectField("typeCode", creditCard.getTypeCode());

			if (creditCard.getCardholderName() != null)
				jgen.writeStringField("cardholderName", creditCard.getCardholderName());

			if (creditCard.getBillingAddress() != null)
				jgen.writeObjectField("billingAddress", creditCard.getBillingAddress());
			
			jgen.writeObjectField("swiped", creditCard.isSwiped());

			jgen.writeEndObject();

		} catch (Exception e) {
			log.error("Error in the serialization", e);
			throw new RuntimeException("Error in the serialization", e);
		}

	}
}
