package myapp.portfolio.com.popularmovies.tools;

/**
 * Created by JPabon on 2015-08-11.
 */
public final class Constants {
    public static final String movieDB_base_url = "http://image.tmdb.org/t/p/";
    public static final String movieDB_base_query = "http://api.themoviedb.org/3/";
    public static final String movieDB_discovery = "discover/movie";

    public static final String movieDB_sort_popularity = "&sort_by=popularity.desc";
    public static final String movieDB_sort_rate = "&sort_by=vote_average.desc";

    public static final String movieDB_filter_date_2015 = "?filter=year(release_date)%20eq%20%272015%27"; //tried to encode, but still need to work more and find a better way to do it

    public static final String movieDB_original = "original/";
    public static final String movieDB_w92 = "w92/";
    public static final String movieDB_w154 = "w154/";
    public static final String movieDB_w185 = "w185/";
    public static final String movieDB_w342 = "w342/";
    public static final String movieDB_w500 = "w500/";
    public static final String movieDB_w780 = "w780/";

    public static final int GRID_POP_COLUMNS_DEFAULT_PORTRAIT = 3;
    public static final int GRID_POP_COLUMNS_DEFAULT_LANDSCAPE = 6;

    public static final int MIN_COLUMNS_4 = 4;
    public static final int GRID_POP_COLUMNS_FOR_4_PORTRAIT = 1;
    public static final int GRID_POP_COLUMNS_FOR_4_LANDSCAPE = 3;

    public static final int MIN_COLUMNS_9 = 9;
    public static final int GRID_POP_COLUMNS_FOR_9_PORTRAIT = 2;
    public static final int GRID_POP_COLUMNS_FOR_9_LANDSCAPE = 6;

    public static final int READ_TIMEOUT = 10000;
    public static final int CONNECT_TIMEOUT = 15000;

    public static final String MOVIEDB_DATE_FORMAT = "yyyy-MM-dd";
    public static final String READABLE_DATE_FORMAT = "MMMM dd (yyyy)";
}
