package myapp.portfolio.com.popularmovies.helpers;

import android.content.res.Configuration;
import myapp.portfolio.com.popularmovies.tools.Constants;

/**
 * Created by JPabon on 2015-09-19.
 */
public class HelperUI {
    public static int FormatMainGrid (int nro_items, int orientation) {
            /*In case the number of movies is less than 9, we change the layout so it doesn't look so bad with all
                * that leftover space*/

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (nro_items < Constants.MIN_COLUMNS_4) {
                return Constants.GRID_POP_COLUMNS_FOR_4_PORTRAIT;
            } else if (nro_items < Constants.MIN_COLUMNS_9) {
                return Constants.GRID_POP_COLUMNS_FOR_9_PORTRAIT;
            } else {
                return Constants.GRID_POP_COLUMNS_DEFAULT_PORTRAIT;
            }
        } else {
            if (nro_items < Constants.MIN_COLUMNS_4) {
                return Constants.GRID_POP_COLUMNS_FOR_4_LANDSCAPE;
            } else if (nro_items < Constants.MIN_COLUMNS_9) {
                return Constants.GRID_POP_COLUMNS_FOR_9_LANDSCAPE;
            } else {
                return Constants.GRID_POP_COLUMNS_DEFAULT_LANDSCAPE;
            }
        }
    }
}
