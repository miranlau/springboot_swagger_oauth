package com.tieto.springbootdemo.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampUtil {

    static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    public static String now() {
        return dateFormat.format(new Date());
    }

    public static long getMillis(String timestamp) throws ParseException {
        return dateFormat.parse(timestamp).getTime();
    }

    public static String fromTime(long time) {
        return dateFormat.format(new Date(time));
    }
}
