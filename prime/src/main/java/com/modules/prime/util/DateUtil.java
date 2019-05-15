package com.modules.prime.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static String getFormatTime(String dateFormat, long timeMillis){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        return sdf.format(calendar.getTime());
    }

    public static String getFormatTime2(String dateFormat, long timeMillis){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(new Date(timeMillis));
    }
}
