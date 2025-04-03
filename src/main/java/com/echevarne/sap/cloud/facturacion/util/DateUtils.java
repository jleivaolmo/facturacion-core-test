package com.echevarne.sap.cloud.facturacion.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Hernan Girardi
 * @since 15/04/2020
 * @updated 27/06/2021 by David Bolet
 */
public class DateUtils {
    final static String FIORI_PREFIX = "/Date(";
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
    private static SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
    private static SimpleDateFormat dateTimeFmtS4 = new SimpleDateFormat("yyyyMMdd");

    /**
     * @return today's date as java.util.Date object
     */
    public static Date today() {
        return new Date();
    }

    /**
     * @return today's date as yyyy-MM-dd format
     */
    public static String todayStr() {
        return dateTimeFormat.format(today());
    }
    
    /**
     * @return today's date as yyyyMMdd format
     */
    public static String todayStrS4() {
        return dateTimeFmtS4.format(today());
    }

    /**
     * Returns the formatted String date for the passed java.util.Date object
     *
     * @param date
     * @return
     */
    public static String formattedDate(Calendar calendar) {
        return calendar != null ? sdf.format(DateUtils.convertToDate(calendar)) : todayStr();
    }

    /**
     * Returns the formatted String date for the passed java.util.Date object
     *
     * @param date
     * @return
     */
    public static String formattedDate(Date date) {
        return date != null ? sdf.format(date) : todayStr();
    }

    /**
     * Returns the date adding given numbr of days.
     * @param date
     * @param numDays
     * @return
     */
    public static Date addDays(Date date, int numDays) {
        return new Date(date.getTime()+numDays*24*60*60*1000);
    }

    /**
     * @return date from string object object in format dd-MM-yyyy
     */
    public static Date fromDDMMYYYY(String dateStr) throws java.text.ParseException {
        return inputDateFormat.parse(dateStr);
    }
    
    public static Date fromDateS4(String dateStr) throws java.text.ParseException {
        return dateTimeFmtS4.parse(dateStr);
    }

    public static Date from(int year, int month, int day, int hour) throws java.text.ParseException {
        return new Date(toTimeStamp(LocalDateTime.of(year,month,day,hour,0)).getTime());
    }

    public static Date fromFiori(String fioriDateStr) throws java.text.ParseException {
        if (fioriDateStr == null || fioriDateStr.length()<4 || !fioriDateStr.startsWith(FIORI_PREFIX)) {
            throw new ParseException(" Fiori date should look like /Date(1644234794423)/",0);
        }
        String clean = fioriDateStr.substring(6);
        clean = clean.substring(0, clean.length()-2);
        Long millis = Long.parseLong(clean);
        return new Date(millis);
    }

    public static Time fromFioriTime(String fioriTimeStr) throws java.text.ParseException {
        String clean = fioriTimeStr.substring(fioriTimeStr.length()-9); //P00DT07H06M17S
        int hour = Integer.parseInt(clean.substring(0,2));
        int minutes = Integer.parseInt(clean.substring(3,clean.lastIndexOf("M")));
        int seconds = Integer.parseInt(clean.substring(6,clean.length()-1));
        return Time.valueOf(LocalTime.of(hour,minutes,seconds));
    }

    /**
     *
     * Returns the Timestamp date from the LocalDateTime
     *
     * @param date
     * @return
     */
    public static Timestamp toTimeStamp(LocalDateTime date) {
    	return date != null ? Timestamp.from(date.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }

    /**
	 * Gets the timestamp now.
	 *
	 * @return the timestamp now
	 */
	public static Timestamp getTimestampNow() {
		return Timestamp.from(Instant.now());
	}

    public static String getNowFormatted() {
        return Instant.now().toString();
    }

    public static Date convertToDate(Calendar calendar) {
        return calendar != null ? calendar.getTime() : null;
    }

    public static Calendar convertToCalendar(Date date) {
        Calendar calendar = null;
        if (date != null) {
            calendar = Calendar.getInstance();
            calendar.setTime(date);
        }
        return calendar;
    }
    
    public static Calendar convertToCalendar(Long lngDate) {
        Calendar calendar = null;
        if (lngDate != null) {
        	Date date = new Date(lngDate);
            calendar = Calendar.getInstance();
            calendar.setTime(date);
        }
        return calendar;
    }

    public static Date roundToQuarterMinutes(Date date){
        Date roundedDate = null;

        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int unroundedMinutes = calendar.get(Calendar.MINUTE);
            int remainder = unroundedMinutes % 15;
            int targetMinutes = unroundedMinutes - remainder;
            calendar.set(Calendar.MINUTE, targetMinutes);

            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            roundedDate = calendar.getTime();
        }

        return roundedDate;
    }

    public static Calendar roundToQuarterMinutes(Calendar calendar){
        Date date = convertToDate(calendar);
        Date roundedDate = roundToQuarterMinutes(date);
        return convertToCalendar(roundedDate);
    }
    
    public static Calendar odataDateToCalendar(String dateTimeValue) throws ParseException {
    	//Example: "datetime'2023-06-12T00:00:00'";
    	Date date = odataDateToDate(dateTimeValue);
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
    	return calendar;
    }

	public static Date odataDateToDate(String dateTimeValue) throws ParseException {
		String fmtDateTimeValue = dateTimeValue.split("'")[1];
    	SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    	Date date = inFormat.parse(fmtDateTimeValue);
		return date;
	}
	
	public static Date stringDateToDate(String dateTimeValue) throws ParseException {
    	SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    	Date date = inFormat.parse(dateTimeValue);
		return date;
	}
    
	public static Calendar odataDateToCalendarTZ(String dateTimeValue) throws ParseException {
    	Date date = odataDateToDateTZ(dateTimeValue);
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
		return cal;
	}
	
	public static Date odataDateToDateTZ(String dateTimeValue) throws ParseException {
		String fmtDateTimeValue = dateTimeValue.split("\\.")[0];
    	SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    	Date date = inFormat.parse(fmtDateTimeValue);
		return date;
	}
	
	public static String dateToCron(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int second = calendar.get(Calendar.SECOND);
		int minute = calendar.get(Calendar.MINUTE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH) + 1; // En Calendar, los meses son 0-based, por eso sumamos 1
		int year = calendar.get(Calendar.YEAR);
		return String.format("%d %d %d %d %d ? %d", second, minute, hour, dayOfMonth, month, year);
    }
    
	public static Calendar toIniDate(Calendar calDate) {
		if (calDate != null) {
			calDate.set(Calendar.HOUR_OF_DAY, 0);
			calDate.set(Calendar.MINUTE, 0);
			calDate.set(Calendar.SECOND, 0);
			calDate.set(Calendar.MILLISECOND, 0);
		}
		return calDate;
	}
	
	public static Calendar toFinalDate(Calendar calDate) {
		if (calDate != null) {
			calDate.set(Calendar.HOUR_OF_DAY, 23);
			calDate.set(Calendar.MINUTE, 59);
			calDate.set(Calendar.SECOND, 59);
			calDate.set(Calendar.MILLISECOND, 999);
		}
		return calDate;
	}

	public static long getDaysBetweenDates(Date fecha1, Date fecha2) {
		LocalDate localFecha1 = fecha1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localFecha2 = fecha2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return ChronoUnit.DAYS.between(localFecha1, localFecha2);
	}
	
	public static int getHourDiff() {
    	Calendar calendar = Calendar.getInstance();
		int hourSrv = calendar.get(Calendar.HOUR_OF_DAY);
		Date toDate = calendar.getTime();
		Instant instant = toDate.toInstant();
		ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("Europe/Madrid"));
		int hourClient = zonedDateTime.getHour();
		int diffHour = hourClient - hourSrv;
		return diffHour;
	}
}
