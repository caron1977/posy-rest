package de.fhws.posy.rest;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import de.fhws.posy.api.entities.ExamPeriod;
import de.fhws.posy.rest.models.IExamPeriod;

public class CustomExamPeriodDeserializer extends JsonDeserializer<IExamPeriod> {

	private transient static final Logger logger = LoggerFactory.getLogger(CustomExamPeriodDeserializer.class);

	@Override
	public IExamPeriod deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

		logger.debug("try deserializing exam period ...");

		JsonNode node = jp.getCodec().readTree(jp);

		Class<? extends IExamPeriod> clazz = ExamPeriod.class;
		IExamPeriod examPeriod = jp.getCodec().treeToValue(node, clazz);

		logger.debug("... deserialized. return: " + examPeriod);

		return examPeriod;
	}

}
