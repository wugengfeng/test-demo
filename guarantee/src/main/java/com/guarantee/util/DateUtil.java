package com.guarantee.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    /**
     * 获取两个时间的间隔
     *
     * @param endDate
     * @param nowDate
     * @return
     */
    public static BigDecimal getDatePoor(Date endDate, Date nowDate, TimeUnit timeUnit) {

        float nd = 0l;
        long diff = endDate.getTime() - nowDate.getTime();
        switch (timeUnit) {
            case DAYS:
                nd = 1000.0f * 24 * 60 * 60;
                break;
            case HOURS:
                nd = 1000.0f * 60 * 60;
                break;
            case MINUTES:
                nd = 1000.0f * 60;
                break;
            default:
                return BigDecimal.ZERO;
        }

        return new BigDecimal(String.valueOf(diff / nd)).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
