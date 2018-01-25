package de.fhws.posy.rest;

public class SimpleRestScheduleException extends SimpleRestCompositeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String course;

	private String examCatalogId;

	public SimpleRestScheduleException() {

	}

	public SimpleRestScheduleException(String message) {

		super(message);
	}

	public String getCourse() {

		return course;
	}

	public void setCourse(String course) {

		this.course = course;
	}

	public String getExamCatalogId() {

		return examCatalogId;
	}

	public void setExamCatalogId(String examCatalogId) {

		this.examCatalogId = examCatalogId;
	}

	@Override
	public String toString() {

		return "SimpleRestScheduleException [course=" + course + ", examCatalogId=" + examCatalogId + "]";
	}

}
