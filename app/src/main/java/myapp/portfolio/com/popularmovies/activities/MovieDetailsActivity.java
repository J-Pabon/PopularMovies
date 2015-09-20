package myapp.portfolio.com.popularmovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import myapp.portfolio.com.popularmovies.R;
import myapp.portfolio.com.popularmovies.entities.Movie;
import myapp.portfolio.com.popularmovies.tools.Constants;
import static myapp.portfolio.com.popularmovies.tools.Converters.getFormattedDate;

public class MovieDetailsActivity extends AppCompatActivity {
    Movie movieDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        if (savedInstanceState != null) {
            movieDetails = savedInstanceState.getParcelable("saved_details");
        } else {
            Intent intent = getIntent();
            movieDetails = intent.getParcelableExtra("movie_details");
        }

        ImageView ivBackdropPath = (ImageView) findViewById(R.id.ivBackdrop);
        Picasso.with(this).load(movieDetails.getBackdropPathInSize(Constants.movieDB_w342)).into(ivBackdropPath);

        TextView tvOriginalTitle = (TextView) findViewById(R.id.tvOriginalTitle);
        tvOriginalTitle.setText(movieDetails.getOriginal_title());

        ImageView ivPoster = (ImageView) findViewById(R.id.ivPoster);
        Picasso.with(this).load(movieDetails.getPosterPathInSize(Constants.movieDB_w342)).into(ivPoster);

        TextView tvReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);
        tvReleaseDate.setText(getFormattedDate(movieDetails.getRelease_date()));

        TextView tvUserRating = (TextView) findViewById(R.id.tvUserRating);
        tvUserRating.setText(String.valueOf(movieDetails.getVote_average()));

        TextView tvOverview = (TextView) findViewById(R.id.tvOverview);
        tvOverview.setText(movieDetails.getOverview());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("saved_details", movieDetails);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_popularity) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
