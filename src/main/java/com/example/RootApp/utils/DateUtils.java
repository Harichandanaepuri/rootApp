package com.example.RootApp.utils;

import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;
import java.util.Calendar;
import java.util.Date;

@UtilityClass
public class DateUtils {

    public static boolean isSameDay(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
              && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
              && calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Given hours, minutes, seconds fetch date. Here we are assuming date as Date(0)
     * @return
     */
    public Date getDate(int hours, int minutes, int seconds) {
        Date date = new Date(0);
        date.setHours(hours);
        date.setMinutes(minutes);
        date.setSeconds(seconds);
        return date;
    }

    /**
     * Convert time to date format
     * @param time time in MM:HH:SS format
     * @param regex regex to split time, example: ":"
     * @return date
     */
    public static Date getDate(@NonNull String time, @NonNull String regex) {

        String[] splitStrings = time.split(regex);

        int hours = Integer.parseInt(splitStrings[0]);
        int minutes = 0;
        int seconds = 0;

        if(splitStrings.length > 1) {
            minutes = Integer.parseInt(splitStrings[1]);
        }
        if(splitStrings.length > 2) {
            seconds = Integer.parseInt(splitStrings[2]);
        }

        return getDate(hours, minutes, seconds);
    }

    /**
     * Verify if startDate is after end date
     * @return true if startDate is after end date, else return false
     */
    public static boolean isAfter(Date startDate, Date endDate) {
        return startDate.after(endDate);
    }
}
