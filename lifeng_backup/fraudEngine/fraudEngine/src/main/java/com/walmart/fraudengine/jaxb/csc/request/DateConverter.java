package com.walmart.fraudengine.jaxb.csc.request;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.logging.Logger;

/**
* Description:This class is used to convert some value to a date.
* @author: 
* @Version:
* Copyright: 2010-2011 Wal-Mart China All rights reserved
*/
public class DateConverter {
    private static final Logger logger = Logger.getLogger(DateConverter.class.getName());
    public final static String DATE_PATTERN = "MM/dd/yyyy";
    
    /**
     * Description:This method is used to convert a calendar value to a date.
     * @param <cal> Calendar
     * @return <<return new SimpleDateFormat(DATE_PATTERN)).format(cal.getTime()>>
     */
    public static String printDate(Calendar cal){
        return (new SimpleDateFormat(DATE_PATTERN)).format(cal.getTime());
    }

    /**
     * Description:This method is used to convert a string to a date.
     * @param <dateStr> String
     * @return <<return calendar>>
     */
    public static GregorianCalendar parseDate(String dateStr){
        if (dateStr == null) return null;
        GregorianCalendar calendar = null;
        SimpleDateFormat _simpleDateFormat =(new SimpleDateFormat(DATE_PATTERN));
        try {
            Date _date = _simpleDateFormat.parse(dateStr);
            calendar = (GregorianCalendar) GregorianCalendar.getInstance();
            calendar.setTime(_date);
        } catch (ParseException e) {
            logger.warning("Unable to parse string " + dateStr + " as a date in format " + _simpleDateFormat.toPattern());
        }
        return calendar;
    }
}
