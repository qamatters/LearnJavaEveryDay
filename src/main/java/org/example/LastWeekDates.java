package org.example;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LastWeekDates {
    public static void main(String[] args) {
        Calendar calendarDate = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        String dayNamesValue =  todaysDay(calendarDate);
        String startDay = "";
        String endDay = "";
        System.out.println(dayNamesValue);

        switch(dayNamesValue){
            case "Monday":
                startDay = date(7, dateFormat);
                endDay = date( 3, dateFormat);
                break;
            case "Tuesday":
                startDay = date( 8, dateFormat);
                endDay = date(4, dateFormat);
                break;
            case "Wednesday":
                startDay = date( 9, dateFormat);
                endDay = date( 5, dateFormat);
                break;
            case "Thursday":
                startDay = date( 10, dateFormat);
                endDay = date( 6, dateFormat);
                break;
            case "Friday":
                startDay = date( 11, dateFormat);
                endDay = date( 7, dateFormat);
                break;
        }

        System.out.println("start day :" + startDay);
        System.out.println("end day :" + endDay);

    }

    public static String date(int noOfDaysBack, SimpleDateFormat dateFormat) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-noOfDaysBack);
       return dateFormat.format(calendar.getTime());
    }

    public static String todaysDay (Calendar calendarDate) {
        String[] dayNames = new DateFormatSymbols().getWeekdays();
        String dayNamesValue =  dayNames[calendarDate.get(calendarDate.DAY_OF_WEEK)];
        return dayNamesValue;
    }

}