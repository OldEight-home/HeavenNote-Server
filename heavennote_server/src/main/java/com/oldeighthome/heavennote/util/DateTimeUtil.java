package com.oldeighthome.heavennote.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static String getDateTime(){
        DateTimeFormatter dateTimeFormatter =DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.now().format(dateTimeFormatter);
    }
    public static String timeToString(LocalDateTime localDateTime){
        DateTimeFormatter dateTimeFormatter =DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return  localDateTime.format(dateTimeFormatter);
    }
    public static String timeToStringBrief(LocalDateTime localDateTime){
        DateTimeFormatter dateTimeFormatter =DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return  localDateTime.format(dateTimeFormatter);
    }
}
