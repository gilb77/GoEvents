package com.GoEvent.util;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.codec.binary.Base64;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class ParseUtil {


    private static DateFormat formatterDate = new SimpleDateFormat("MM/dd/yyyy");
    private static DateFormat formatterTime = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat FullFormatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

    public static Date parseStringToDate(String date) throws ParseException {
        return formatterDate.parse(date);
    }

    public static Date parseStringToTime(String Time) throws ParseException {
        return formatterTime.parse(Time);
    }

    public static String parseDateToString(Date date) {
        return formatterDate.format(date);
    }


    public static String parseTimeToString(Date date) {
        return formatterTime.format(date);
    }


    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    public static String encodePhoto(byte[] data) {
        return Base64.encodeBase64String(data);
    }
}
