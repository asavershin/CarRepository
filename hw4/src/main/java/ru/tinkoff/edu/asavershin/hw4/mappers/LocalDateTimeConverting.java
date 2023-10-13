package ru.tinkoff.edu.asavershin.hw4.mappers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverting {
    static String stringFormat = "dd-MM-yyyy";
    public static LocalDateTime stringToLocalDateTime(String date) throws ParseException {
        return new SimpleDateFormat(stringFormat)
                .parse(date).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
    public static String localDateTimeToString(LocalDateTime localDateTime){
        return localDateTime.format(DateTimeFormatter.ofPattern(stringFormat));
    }
}
