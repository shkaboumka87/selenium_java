package org.selenium.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonMethods {

    private CommonMethods(){}


    /**
     * Return current date
     *
     * @return {String} return current date in yyy-MM-dd_hh-mm-ss format
     */
    public static String getTime() {

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd_hh-mm-ss");
        return dateFormat.format(date);

    }
}
