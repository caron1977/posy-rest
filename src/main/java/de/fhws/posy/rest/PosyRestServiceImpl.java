package de.fhws.posy.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhws.exam.exceptions.PosyApiCompositeException;
import de.fhws.exam.exceptions.PosyApiException;
import de.fhws.exam.exceptions.PosyApiScheduleException;
import de.fhws.exam.model.ExamRegistration;
import de.fhws.exam.model.ExamSchedule;
import de.fhws.posy.dto.FindExamRegistrationsRequest;
import de.fhws.posy.dto.FindExamRegistrationsResponse;
import de.fhws.posy.dto.GrantedRequest;
import de.fhws.posy.dto.GrantedSchedulesResponse;
import de.fhws.posy.rest.exceptions.SimpleRestCompositeException;
import de.fhws.posy.rest.exceptions.SimpleRestException;
import de.fhws.posy.rest.exceptions.SimpleRestScheduleException;
import de.fhws.posy.rest.models.RecordManyGradesRequest;
import de.fhws.posy.services.PosyService;
import de.fhws.posy.services.RecordGradesService;

/**
 * 
 * {@link Stateless} seems necessary to let {@link EJB} inside the class work
 * 
 * see also: http://stackoverflow.com/questions/3027834/inject-a-ejb-into-jax-rs-restfull-service
 * 
 * @author grimmer
 *
 */
@Path("/posy-rest-service")
@Stateless
public class PosyRestServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(PosyRestServiceImpl.class);

	@EJB(lookup = "java:global/posy-impl/PosyServiceImpl")
	private PosyService posyService;

	@EJB(lookup = "java:global/posy-impl/RecordGradesServiceImpl")
	private RecordGradesService gradesService;

	@POST
	@Path("/find-exam-registrations")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findExamRegistrations(FindExamRegistrationsRequest request) {

		try {

			logger.info("... try");

			FindExamRegistrationsResponse response = posyService.findExamRegistrations(request);

			// TODO grimmer: improve this. get rid of bi-directional relation schedule <-> registration
			for (ExamSchedule s : response.getExamSchedules()) {

				for (ExamRegistration r : s.getExamRegistrations()) {

					r.setExamSchedule(null);
				}
			}

			logger.info("... done.");

			return CustomResponseBuilder.initBuilder(Response.Status.OK, response).build();

		} catch (Exception e) {

			logger.error("error while finding exam registrations.", e);
			// return CustomResponseBuilder.build(Response.Status.INTERNAL_SERVER_ERROR, new SimpleRestException(e.getMessage()));
			return CustomResponseBuilder.build(Response.Status.INTERNAL_SERVER_ERROR, null);
		}
	}

	@GET
	@Path("/find-granted-schedules")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findGrantedSchedules(@QueryParam("account") String account) {

		try {

			logger.info("... try");

			GrantedRequest request = new GrantedRequest();
			request.setAccount(account);
			request.setExamPeriodId(8L);
			GrantedSchedulesResponse response = posyService.findGrantedSchedules(request);

			logger.info("... done.");

			return CustomResponseBuilder.initBuilder(Response.Status.OK, response).build();

		} catch (Exception e) {

			logger.error("error while finding granted schedules.", e);
			// return CustomResponseBuilder.build(Response.Status.INTERNAL_SERVER_ERROR, new SimpleRestException(e.getMessage()));
			return CustomResponseBuilder.build(Response.Status.INTERNAL_SERVER_ERROR, null);
		}
	}

	@POST
	@Path("/record-many-grades")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response recordManyGrades(RecordManyGradesRequest request) {

		try {

			logger.info("... try");

			gradesService.recordManyGrades(request);

			logger.info("... done.");

			return CustomResponseBuilder.initBuilder(Response.Status.OK, null).build();

		} catch (PosyApiException e) {

			logger.error("posy api exception while recording grades", e);
			return CustomResponseBuilder.build(Response.Status.INTERNAL_SERVER_ERROR, transformPosyApiException(e));

		} catch (Exception e) {

			logger.error("error while recording grades", e);
			return CustomResponseBuilder.build(Response.Status.INTERNAL_SERVER_ERROR, new SimpleRestException(e.getMessage()));
		}
	}

	/**
	 * transform API exception to REST exception RECURSIVELY
	 * 
	 * @param apiException
	 * @return
	 */
	private SimpleRestException transformPosyApiException(PosyApiException apiException) {

		SimpleRestException restException = null;

		// check whether it is a composite exception or not
		if (apiException instanceof PosyApiCompositeException) { // composite

			SimpleRestCompositeException restCompositeException = null;
			PosyApiCompositeException apiCompositeException = (PosyApiCompositeException) apiException;

			// which kind of composite is it?
			if (apiException instanceof PosyApiScheduleException) { // schedule composite

				PosyApiScheduleException apiScheduleException = (PosyApiScheduleException) apiException;

				SimpleRestScheduleException restScheduleException = new SimpleRestScheduleException();
				restScheduleException.setMessage(apiScheduleException.toString());
				restScheduleException.setCourse(apiScheduleException.getCourse());
				restScheduleException.setExamCatalogId(apiScheduleException.getExamCatalogId());
				restCompositeException = restScheduleException;

			} else { // default composite

				restCompositeException = new SimpleRestCompositeException(apiCompositeException.toString());
			}

			for (PosyApiException pae : apiCompositeException.getExceptions()) {

				SimpleRestException sre = transformPosyApiException(pae);
				restCompositeException.getExceptions().add(sre);
				restException = restCompositeException;
			}

		} else { // leaf

			restException = new SimpleRestException(apiException.getMessage());
		}

		return restException;

	}
}
