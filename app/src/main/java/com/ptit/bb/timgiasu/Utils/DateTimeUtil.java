package com.ptit.bb.timgiasu.Utils;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = sdf.format(date.getTime());
        return dateString;
    }

    public static String longToString(Long miliTime) {
        String dateString = DateFormat.format("dd-MM-yyyy", new Date(miliTime)).toString();
        return dateString;
    }
}
