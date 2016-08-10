package com.jian.universaldownloader.utils;

/**
 * Created by 七月在线科技 on 2016/8/10.
 */

public class TimeUtils {

    public static String second2TimeStr(long second) {
        long oneMinute = 60;
        long oneHour = 60 * oneMinute;
        long oneDay = 24 * oneHour;
        if (second < oneMinute) {
            return second + "s";
        } else if (second < oneHour) {
            return second / oneMinute + "m" + second % oneMinute + "s";
        } else if (second < oneDay) {
            long hour = second / oneHour;
            return hour + "h";
        } else {
            return second / oneDay + "d";
        }
    }

}
