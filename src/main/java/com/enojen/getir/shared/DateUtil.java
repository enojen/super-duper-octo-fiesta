package com.enojen.getir.shared;


import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DateUtil {

    public static DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)
                .withLocale(new Locale("tr-TR"))
                .withZone(ZoneId.systemDefault());
    }
}
