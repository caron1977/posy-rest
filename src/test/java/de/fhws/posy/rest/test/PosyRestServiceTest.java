package de.fhws.posy.rest.test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.Test;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import de.fhws.exam.enums.AverageGradesEnum;
import de.fhws.exam.enums.WorkflowStatus;
import de.fhws.exam.model.ExamGradeAverage;
import de.fhws.exam.model.ExamSchedule;
import de.fhws.exam.model.GradesWorkflow;
import de.fhws.posy.dto.RecordGradesCSVRequest;
import de.fhws.posy.dto.RecordGradesManyRequest;

public class PosyRestServiceTest {

	@Test
	public void testRecordGrades() throws Exception {

		RecordGradesCSVRequest request = new RecordGradesCSVRequest();

		ExamSchedule s = new ExamSchedule();
		s.setId(11335L);
		request.setExamscheduleId(s.getId());

		GradesWorkflow w = new GradesWorkflow();
		// w.setId(1092L);
		w.setAccount("braunp");
		w.setStatus(WorkflowStatus.TEMPLATE);
		w.setExamSchedule(s);
		request.setGradesWorkflow(w);

		ExamGradeAverage g = new ExamGradeAverage();
		g.setExamSchedule(s);
		g.setMatnr("6114079");
		g.setAverageGradeEnum(AverageGradesEnum.GRADE_17);
		request.getExamGrades().add(g);

		Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);

		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().nonPreemptive().credentials("RESTful", "_eyIn23n").build();
		client.register(feature);
		WebTarget webTarget = client.target("https://java-test.fhws.de/posy-rest/rest/posy-rest-service/").path("record-grades");

		// WebTarget webTarget = client.target("http://localhost:8080/posy-rest/rest/posy-rest-service/").path("record-grades");

		Response response = webTarget.request().post(Entity.entity(request, MediaType.APPLICATION_JSON));

		if (response.getStatus() == Response.Status.OK.ordinal()) {

			System.out.println("OK");

		} else {
			System.out.println("failed: " + response.getStatus());
		}
	}

	@Test
	public void testRecordManyGrades() throws Exception {

		RecordGradesManyRequest rgmr = new RecordGradesManyRequest();

		// 1. schedule
		{
			RecordGradesCSVRequest rgr = new RecordGradesCSVRequest();

			ExamSchedule s = new ExamSchedule();
			s.setId(11458L); // Parallele und verteile Systeme // BIN
			rgr.setExamscheduleId(s.getId());

			GradesWorkflow w = new GradesWorkflow();
			// w.setId(1092L);
			w.setAccount("braunp");
			w.setStatus(WorkflowStatus.TEMPLATE);
			w.setExamSchedule(s);
			rgr.setGradesWorkflow(w);

			ExamGradeAverage g = new ExamGradeAverage();
			g.setExamSchedule(s);
			g.setMatnr("6114079");
			g.setAverageGradeEnum(AverageGradesEnum.GRADE_17);
			rgr.getExamGrades().add(g);

			// rgmr.getRecordGradesList().add(rgr);
		}

		// 2. schedule
		{
			RecordGradesCSVRequest rgr = new RecordGradesCSVRequest();

			ExamSchedule s = new ExamSchedule();
			s.setId(11334L); // Software industry, education and economy in India // BIN
			rgr.setExamscheduleId(s.getId());

			GradesWorkflow w = new GradesWorkflow();
			// w.setId(1092L);
			w.setAccount("braunp");
			w.setStatus(WorkflowStatus.TEMPLATE);
			w.setExamSchedule(s);
			rgr.setGradesWorkflow(w);

			ExamGradeAverage g = new ExamGradeAverage();
			g.setExamSchedule(s);
			g.setMatnr("6114076");
			g.setAverageGradeEnum(AverageGradesEnum.GRADE_13);
			rgr.getExamGrades().add(g);

			// rgmr.getRecordGradesList().add(rgr);
		}

		Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);

		/*
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().nonPreemptive().credentials("RESTful", "_eyIn23n").build();
		client.register(feature);
		WebTarget webTarget = client.target("https://java-test.fhws.de/posy-rest/rest/posy-rest-service/").path("record-grades");
		*/

		WebTarget webTarget = client.target("http://localhost:8080/posy-rest/rest/posy-rest-service/").path("record-many-grades");

		Response response = webTarget.request().post(Entity.entity(rgmr, MediaType.APPLICATION_JSON));

		if (response.getStatus() == Response.Status.OK.ordinal()) {

			System.out.println("OK");

		} else {
			System.out.println("failed: " + response.getStatus());
		}
	}

}
