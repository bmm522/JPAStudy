package com.personal.community.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateFormatter {

    private static DateFormatter dateFormatter = new DateFormatter();

    public static DateFormatter getInstance(){
        return dateFormatter;
    }

    private DateFormatter(){}

    public String formatDate(LocalDateTime date){
        return date.format(DateTimeFormatter
                .ofPattern("yyyy년 MM월 dd일 E요일 a HH시 mm분 ss초")
                .withLocale(Locale.forLanguageTag("ko")));
    }
}
