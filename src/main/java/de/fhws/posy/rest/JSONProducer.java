package de.fhws.posy.rest;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import de.fhws.posy.api.entities.ExamGrade;

/**
 * configure the production of JSON objects for example configure the serialization of java date objects
 * 
 * see also: http://stackoverflow.com/questions/22126131/how-do-i-get-wildfly-to-use-additional-jackson-datatypes might be necessary to add a web.xml
 * and a jboss-deployment-structure.xml on servers lower than wildfly-8.1.0.Final
 * 
 * @author grimmer
 * 
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JSONProducer implements ContextResolver<ObjectMapper> {

	public JSONProducer() throws Exception {

		SimpleModule module = new SimpleModule();
		// de-serialize item list of DeputatHead
		module.addDeserializer(ExamGrade.class, new CustomExamGradeDeserializer());
		this.json = new ObjectMapper()
		// .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.setDateFormat(new RestServiceDateFormat()).registerModule(module);

	}

	@Override
	public ObjectMapper getContext(Class<?> objectType) {

		return json;
	}

	private final ObjectMapper json;
}