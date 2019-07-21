package com.guarantee.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static String format(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static String formatYMD(Date date) {
        return format(date, "yyyy-MM-dd");
    }

    public static Date calculateDate(Date date, int num, int timeUnit) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(timeUnit, num);
        Date newTime = calendar.getTime();
        return newTime;
    }
}
