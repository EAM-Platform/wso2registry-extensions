package at.rbg.registry.integration.service.adapter;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class CalendarAdapter {
	private static DatatypeFactory dtFactory;

	static { 
		try {
			dtFactory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	public static XMLGregorianCalendar adapt(Calendar cal) {
			GregorianCalendar utcCalendar = new GregorianCalendar(TimeZone
					.getTimeZone("UTC"));
			utcCalendar.setTimeInMillis(cal.getTimeInMillis());

			XMLGregorianCalendar xmlCalendar = dtFactory.newXMLGregorianCalendar(utcCalendar);

			return xmlCalendar;
	}

}
