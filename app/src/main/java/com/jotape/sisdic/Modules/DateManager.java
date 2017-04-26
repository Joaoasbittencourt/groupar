package com.jotape.sisdic.Modules;



import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by João Bittencourt on 25/04/2017.
 */

public class DateManager {


    public static Map<String,Integer> getLocalDate(Calendar calendar)
    {

        Map<String,Integer> datePack = new HashMap<String,Integer>();

        datePack.put("day",calendar.DAY_OF_MONTH);
        datePack.put("month",calendar.MONTH);
        datePack.put("year",calendar.YEAR);

        return datePack;
    }

    public String formatDatePack(Map<String,Integer> datePack)
    {
        String  result;
        String  dayString,monthString,yearString;

        Integer day,month,year;

        day = datePack.get("day");
        month = datePack.get("month");
        year = datePack.get("year");

        dayString = handleFirst0(day);
        monthString = handleFirst0(month);
        yearString = handleFirst0(year);

        result = dayString + "/" + monthString + "/" + yearString;

        return result;
    }

    public static String formatDateInts(Integer day, Integer month, Integer year){

        String  result;
        String  dayString,monthString,yearString;

        dayString = handleFirst0(day);
        monthString = handleFirst0(month);
        yearString = handleFirst0(year);

        result = dayString + "/" + monthString + "/" + yearString;

        return result;
    }


    public static String handleFirst0(Integer num)
    {

        String result;

        if (num < 10)
        {
            result = "0"+num;
        }
        else
        {
            result = String.valueOf(num);
        }

        return result;
    }

}
