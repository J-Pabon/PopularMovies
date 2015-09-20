package myapp.portfolio.com.popularmovies.entities;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import myapp.portfolio.com.popularmovies.tools.Constants;

/**
 * Created by JPabon on 2015-08-11.
 */
public class Movie implements Parcelable {
    private boolean adult;
    private String backdrop_path;
    private List<Integer> genre_ids;
    private long id;
    private String original_title;
    private String overview;
    private String release_date;
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

    public List<Integer> getGenre_ids() {
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

    public String getRelease_date() {
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

    public Movie(JSONObject jsonMovieDetails) throws JSONException, ParseException {
        genre_ids = new ArrayList<Integer>();
        int length = jsonMovieDetails.getJSONArray("genre_ids").length();

        for (int j = 0; j < length; j++) {
            if (jsonMovieDetails.getJSONArray("genre_ids").getInt(j) > 0) {
                genre_ids.add(jsonMovieDetails.getJSONArray("genre_ids").getInt(j));
            }
        }

        this.adult = jsonMovieDetails.getBoolean("adult");
        this.backdrop_path = jsonMovieDetails.getString("backdrop_path");
        this.id = Integer.parseInt(jsonMovieDetails.getString("id"));
        this.original_title = jsonMovieDetails.getString("original_title");
        this.overview = jsonMovieDetails.getString("overview");
        this.release_date = jsonMovieDetails.getString("release_date");
        this.poster_path = jsonMovieDetails.getString("poster_path");
        this.popularity = jsonMovieDetails.getDouble("popularity");
        this.title = jsonMovieDetails.getString("title");
        this.video = jsonMovieDetails.getBoolean("video");
        this.vote_average = jsonMovieDetails.getDouble("vote_average");
        this.vote_count = jsonMovieDetails.getInt("vote_count");
    }

    public String getBackdropPathInSize(String sizeCode) {
        return Constants.movieDB_base_url + sizeCode + backdrop_path;
    }

    public String getPosterPathInSize(String sizeCode) {
        return Constants.movieDB_base_url + sizeCode + poster_path;
    }

    protected Movie(Parcel in) {
        adult = in.readByte() != 0x00;
        backdrop_path = in.readString();
        if (in.readByte() == 0x01) {
            genre_ids = new ArrayList<Integer>();
            in.readList(genre_ids, Integer.class.getClassLoader());
        } else {
            genre_ids = null;
        }
        id = in.readLong();
        original_title = in.readString();
        overview = in.readString();
        release_date = in.readValue(String.class.getClassLoader()).toString();
        poster_path = in.readString();
        popularity = in.readDouble();
        title = in.readString();
        video = in.readByte() != 0x00;
        vote_average = in.readDouble();
        vote_count = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (adult ? 0x01 : 0x00));
        dest.writeString(backdrop_path);
        if (genre_ids == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(genre_ids);
        }
        dest.writeLong(id);
        dest.writeString(original_title);
        dest.writeString(overview);
        dest.writeValue(release_date);
        dest.writeString(poster_path);
        dest.writeDouble(popularity);
        dest.writeString(title);
        dest.writeByte((byte) (video ? 0x01 : 0x00));
        dest.writeDouble(vote_average);
        dest.writeInt(vote_count);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}