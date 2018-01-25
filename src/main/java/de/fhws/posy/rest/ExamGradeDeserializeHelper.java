package de.fhws.posy.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhws.exam.enums.ExamGradeTypes;
import de.fhws.posy.api.entities.ExamGrade;
import de.fhws.posy.api.entities.ExamGradeAverage;
import de.fhws.posy.api.entities.ExamGradeInteger;
import de.fhws.posy.api.entities.ExamGradeNoGrade;

public class ExamGradeDeserializeHelper {

	private static Logger logger = LoggerFactory.getLogger(ExamGradeDeserializeHelper.class);

	public static Class<? extends ExamGrade> deserialize(String type) {

		Class<? extends ExamGrade> clazz = null;

		if (type.equals(ExamGradeTypes.AVERAGE)) {

			clazz = ExamGradeAverage.class;

		} else if (type.equals(ExamGradeTypes.NOGRADE)) {

			clazz = ExamGradeNoGrade.class;

		} else if (type.equals(ExamGradeTypes.INTEGERGRADE)) {

			clazz = ExamGradeInteger.class;

		} else {

			logger.error("unknown exam grade type: " + type);
			throw new IllegalArgumentException("unknown exam grade type : " + type);
		}

		return clazz;

	}

}
