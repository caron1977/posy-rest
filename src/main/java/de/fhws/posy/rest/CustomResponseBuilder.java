package de.fhws.posy.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

public class CustomResponseBuilder {

	/**
	 * see as explanation: <br>
	 * http://stackoverflow.com/questions/23450494/how-to-enable-cross-domain-requests-on-jax-rs-web-services
	 * 
	 * @param status
	 * @param entity
	 * @return
	 */
	public static Response build(int status, Object entity) {

		//@formatter:off
		ResponseBuilder builder = Response
	            .status(status)
	            .header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	            .header("Access-Control-Allow-Credentials", "true")
	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	            .header("Access-Control-Max-Age", "1209600");
		//@formatter:on

		if (entity != null) {
			builder.entity(entity);

		}
		/* had a problem using rest easy in firefox ...
		 * 
		 * WARNING: do not set empty response due to conflicts with response handling in studentenportal!
		 * 
		else {
			builder.entity(new EmptyRestResponse());
		}
		*/

		return builder.build();
	}

	public static Response build(Status status, Object entity) {

		return build(status.getStatusCode(), entity);
	}

	public static ResponseBuilder initBuilder(Status status, Object entity) {

		//@formatter:off
		ResponseBuilder builder = Response
	            .status(status)
	            .header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	            .header("Access-Control-Allow-Credentials", "true")
	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	            .header("Access-Control-Max-Age", "1209600");
		//@formatter:on

		if (entity != null) {
			builder.entity(entity);

		}

		return builder;
	}
}
