package de.fhws.posy.rest;

import java.util.ArrayList;
import java.util.List;

public class SimpleRestCompositeException extends SimpleRestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<SimpleRestException> exceptions = new ArrayList<>();

	public SimpleRestCompositeException() {

	}

	public SimpleRestCompositeException(String messageKey) {

		super(messageKey);
	}

	public List<SimpleRestException> getExceptions() {

		return exceptions;
	}

}
