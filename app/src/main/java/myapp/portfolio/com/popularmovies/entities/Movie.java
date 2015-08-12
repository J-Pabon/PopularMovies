package myapp.portfolio.com.popularmovies.entities;

import java.util.Date;

import myapp.portfolio.com.popularmovies.tools.Constants;

/**
 * Created by JPabon on 2015-08-11.
 */
public class Movie {
    private boolean adult;
    private String backdrop_path;
    private int[] genre_ids;
    private long id;
    private String original_title;
    private String overview;
    private Date release_date;
    private String poster_path;
    private double popularity;
    private String title;
    private boolean video;
    private double vote_average;
    private int vote_count;

    public boolean isAdult() {
        return adult;
    }

    public String getBackdrop_path() {
        return Constants.movieDB_base_url + Constants.movieDB_w154 + backdrop_path;
    }

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public long getId() {
        return id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public String getPoster_path() {
        return Constants.movieDB_base_url + Constants.movieDB_w154 + poster_path;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getTitle() {
        return title;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public Movie(boolean adult, String backdrop_path, int[] genre_ids, long id, String original_title,
                 String overview, Date release_date, String poster_path, double popularity,
                 String title, boolean video, double vote_average, int vote_count) {
        this.adult = adult;
        this.backdrop_path = backdrop_path;
        this.genre_ids = genre_ids;
        this.id = id;
        this.original_title = original_title;
        this.overview = overview;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.popularity = popularity;
        this.title = title;
        this.video = video;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    public String getBackdropPathInSize(String sizeCode) {
        return Constants.movieDB_base_url + sizeCode + backdrop_path;
    }

    public String getPosterPathInSize(String sizeCode) {
        return Constants.movieDB_base_url + sizeCode + poster_path;
    }
}
