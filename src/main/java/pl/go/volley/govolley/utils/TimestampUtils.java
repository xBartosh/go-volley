package pl.go.volley.govolley.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimestampUtils {
    private static final String DATETIME_PATTERN = "dd.MM.yyyy HH:mm";
    public static Timestamp createTimestampFromDateAndHour(String date, String hour) throws ParseException {
        String datetimeStr = date + " " + hour;
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATETIME_PATTERN);
        Date datetime = dateFormat.parse(datetimeStr);
        long time = datetime.getTime();
        return new Timestamp(time);
    }

    public static String convertTimestampToFormattedString(Timestamp timestamp){
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
        return localDateTime.format(formatter);
    }
}
