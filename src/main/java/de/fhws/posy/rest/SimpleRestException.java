package de.fhws.posy.rest;

import java.io.Serializable;

public class SimpleRestException implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;

	public SimpleRestException() {

	}

	public SimpleRestException(String messageKey) {

		this.message = messageKey;
	}

	public String getMessage() {

		return message;
	}

	public void setMessage(String messageKey) {

		this.message = messageKey;
	}

	@Override
	public String toString() {

		return "RestException [message=" + message + "]";
	}

}
