package com.echevarne.sap.cloud.facturacion.util;

import com.fasterxml.jackson.databind.util.StdConverter;

import lombok.var;
import lombok.extern.slf4j.Slf4j;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class FioriDateDeserializer extends StdConverter<String, Calendar> {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Calendar convert(String value) {
		log.info("Esta llegando el valor : " + value);
		Date dateAux = null;
		try {
			Long millis = Long.parseLong(value);
			dateAux = new Date(millis);
			log.info("Lo convierto a fecha : " + dateAux);
		} catch (Exception ex) {
			try {
				dateAux = DateUtils.fromFiori(value);
				log.info("Lo convierto a fechaFiori : " + dateAux);
			} catch (Exception ex1) {
				try {
					dateAux = sdf.parse(value);
					log.info("Lo convierto a fecha sdf : " + dateAux);
				} catch (ParseException pe) {
					log.error("Invalid date!" + pe.getMessage());
					return null;
				}
			}
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateAux);
		var hourDiff = DateUtils.getHourDiff();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hourDiff);
		log.info("Fecha tras conversion de zona: " + calendar.getTime());
		return calendar;
	}
}