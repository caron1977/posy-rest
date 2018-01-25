package de.fhws.posy.rest;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import de.fhws.exam.model.ExamGrade;

public class CustomExamGradeDeserializer extends JsonDeserializer<ExamGrade> {

	private transient static final Logger logger = LoggerFactory.getLogger(CustomExamGradeDeserializer.class);

	@Override
	public ExamGrade deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

		logger.debug("try deserializing deputat item ...");

		JsonNode node = jp.getCodec().readTree(jp);
		String type = node.get("examGradeType").textValue();

		logger.debug("type: " + type + " json:" + node);

		Class<? extends ExamGrade> clazz = ExamGradeDeserializeHelper.deserialize(type);
		ExamGrade examGrade = jp.getCodec().treeToValue(node, clazz);

		logger.debug("... deserialized. return: " + examGrade);

		return examGrade;
	}

}
