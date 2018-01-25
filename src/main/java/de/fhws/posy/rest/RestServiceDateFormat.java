package de.fhws.posy.rest;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestServiceDateFormat extends DateFormat {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient static final Logger logger = LoggerFactory.getLogger(RestServiceDateFormat.class);

	@Override
	public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {

		return toAppendTo.append(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date));
	}

	@Override
	public Date parse(String source, ParsePosition pos) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Date parse(String source) throws ParseException {

		logger.info("try parsing string to date. string: " + source + " ...");

		Date date = null;

		try {

			date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(source);

		} catch (ParseException e) {
			// swallow this
			logger.debug("could not parse string to date with pattern dd.MM.yyyy HH:mm:ss.");
		}

		try {

			if (date == null) {
				date = new SimpleDateFormat("dd.MM.yyyy").parse(source);
			}

		} catch (ParseException e) {
			logger.error("could not parse string to date neither with pattern dd.MM.yyyy HH:mm:ss nor dd.MM.yyyy.");
			throw e;
		}

		logger.info("... parsed string to date.");

		return date;
	}

	@Override
	public Object clone() {

		return new RestServiceDateFormat();
	}

}
