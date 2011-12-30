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
public class DateTimeConverter {
    private static final Logger logger = Logger.getLogger(DateConverter.class.getName());
    public final static String DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    
    /**
     * Description:This method is used to convert a calendar value to a string.
     * @param <cal> Calendar
     * @return <<return (new SimpleDateFormat(DATETIME_PATTERN)).format(cal.getTime())>>
     */
    public static String printDateTime(Calendar cal){
        return (new SimpleDateFormat(DATETIME_PATTERN)).format(cal.getTime());
    }
   
    /**
     * Description:This method is used to convert a dateStr to a GregorianCalendar.
     * @param <dateStr> String
     * @return <<return calendar>>
     */
    public static GregorianCalendar parseDateTime(String dateStr){
        if (dateStr == null) return null;
        GregorianCalendar calendar = null;
        SimpleDateFormat _simpleDateFormat =(new SimpleDateFormat(DATETIME_PATTERN));
        try {
            Date _date = _simpleDateFormat.parse(dateStr);
            calendar = (GregorianCalendar) Calendar.getInstance();
            calendar.setTime(_date);
        } catch (ParseException e) {
            logger.warning("Unable to parse string " + dateStr + " as a date in format " + _simpleDateFormat.toPattern());
        }
        return calendar;
    }
}