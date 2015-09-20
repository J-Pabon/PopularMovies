package myapp.portfolio.com.popularmovies.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static myapp.portfolio.com.popularmovies.tools.Constants.*;

/**
 * Created by JPabon on 2015-09-16.
 * Based in: http://stackoverflow.com/questions/10426492/change-date-string-format-in-android
 * By: 34erigobeli
 * On: Oct 17 '13 at 20:
 */
public class Converters {
    public static String getFormattedDate(String date) {
        String formatted_date = "";

        SimpleDateFormat date_format_input = new SimpleDateFormat(MOVIEDB_DATE_FORMAT, Locale.getDefault());
        SimpleDateFormat date_format_output = new SimpleDateFormat(READABLE_DATE_FORMAT, Locale.getDefault());

        try {
            formatted_date = date_format_output.format(date_format_input.parse (date));
        } catch (ParseException e) {
            e.printStackTrace();
            formatted_date = date;
        }

        return formatted_date;
    }
}