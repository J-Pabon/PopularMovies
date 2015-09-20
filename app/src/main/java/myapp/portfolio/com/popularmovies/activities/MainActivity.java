package myapp.portfolio.com.popularmovies.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import myapp.portfolio.com.popularmovies.R;
import myapp.portfolio.com.popularmovies.adapters.PosterAdapter;
import myapp.portfolio.com.popularmovies.entities.Movie;
import myapp.portfolio.com.popularmovies.helpers.HelperTMDB;
import myapp.portfolio.com.popularmovies.helpers.HelperUI;

import static myapp.portfolio.com.popularmovies.tools.Constants.movieDB_discovery;
import static myapp.portfolio.com.popularmovies.tools.Constants.movieDB_filter_date_2015;
import static myapp.portfolio.com.popularmovies.tools.Constants.movieDB_sort_popularity;
import static myapp.portfolio.com.popularmovies.tools.Constants.movieDB_sort_rate;


public class MainActivity extends AppCompatActivity {
    public static int width;
    public static int height;

    GridView gvPopMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        width = metrics.widthPixels;
        height = metrics.heightPixels;

        int height_bar = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height_bar = getResources().getDimensionPixelSize(resourceId);
        }

        height -= height_bar;

        gvPopMovies = (GridView) findViewById(R.id.gvPopMovies);
        gvPopMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(v.getContext(), MovieDetailsActivity.class);

                intent.putExtra("movie_details", (Movie) parent.getAdapter().getItem(position));
                startActivity(intent);
            }
        });

        int orientation = getResources().getConfiguration().orientation;

        if (savedInstanceState != null) {
            ArrayList<Movie> data = (ArrayList<Movie>) savedInstanceState.getSerializable("info_movies");
            PosterAdapter adapter = new PosterAdapter(getApplicationContext(), data);

            gvPopMovies.setAdapter(adapter);
            gvPopMovies.setNumColumns(HelperUI.FormatMainGrid(data.size(), orientation));
        } else {
            ExecuteQuery(movieDB_discovery, movieDB_filter_date_2015, movieDB_sort_popularity, orientation);
        }
    }

    public void ExecuteQuery (String type, String filter, String sorting, int orientation) {
        new HelperTMDB().execute(type, filter, sorting, gvPopMovies, this, orientation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);

        ArrayList<Movie> adapter = (ArrayList<Movie>)((PosterAdapter)gvPopMovies.getAdapter()).data;

        outState.putSerializable("info_movies", adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        int orientation = getResources().getConfiguration().orientation;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_popularity) {
            ExecuteQuery(movieDB_discovery, movieDB_filter_date_2015, movieDB_sort_popularity, orientation);

            ((PosterAdapter) gvPopMovies.getAdapter()).notifyDataSetChanged();
            return true;
        } else if (id == R.id.action_vote) {
            ExecuteQuery(movieDB_discovery, movieDB_filter_date_2015, movieDB_sort_rate, orientation);

            ((PosterAdapter) gvPopMovies.getAdapter()).notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
