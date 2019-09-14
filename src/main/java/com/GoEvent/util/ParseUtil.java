package com.GoEvent.util;

import org.apache.tomcat.util.codec.binary.Base64;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseUtil {


    public static Date parseStringToDate(String date) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("MM/DD/yyyy");
        return formatter.parse(date);
    }

    public static Date parseStringToTime(String Time) throws ParseException{
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.parse(Time);
    }

    public String parseDateToString(Date date){
        DateFormat formatter = new SimpleDateFormat("MM/DD/yyyy");
        return formatter.format(date);
    }


    public String parseTimeToString(Date date){
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(date);
    }


    public static String encodePhoto(byte[] data){
        return Base64.encodeBase64String(data) ;
    }
}
