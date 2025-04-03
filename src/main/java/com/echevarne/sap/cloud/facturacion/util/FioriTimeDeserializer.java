package com.echevarne.sap.cloud.facturacion.util;

import com.fasterxml.jackson.databind.util.StdConverter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

@Slf4j
public class FioriTimeDeserializer extends StdConverter<String, Date> {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public Time convert(String value) {
            try {
                return DateUtils.fromFioriTime(value);
            } catch (Exception ex1) {
                try {
                    return Time.valueOf(LocalTime.parse(value, dtf));
                } catch (DateTimeParseException pe) {
                    log.error("Invalid time!" + pe.getMessage());
                    return null;
                }
            }
     }
}