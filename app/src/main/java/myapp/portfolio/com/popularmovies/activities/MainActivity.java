package myapp.portfolio.com.popularmovies.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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

    TextView tvError;
    Button btRetry;

    PosterAdapter adapterPosters;

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

        gvPopMovies.setAdapter(new PosterAdapter(getApplicationContext()));
        gvPopMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(v.getContext(), MovieDetailsActivity.class);

                intent.putExtra("movie_details", (Movie) parent.getAdapter().getItem(position));
                startActivity(intent);
            }
        });

        tvError = (TextView) findViewById(R.id.tvError);
        btRetry = (Button) findViewById(R.id.btRetry);

        if (savedInstanceState != null) {
            ArrayList<Movie> data = (ArrayList<Movie>) savedInstanceState.getSerializable("info_movies");
            int orientation = getResources().getConfiguration().orientation;

            ((PosterAdapter) gvPopMovies.getAdapter()).setData(data);
            gvPopMovies.setNumColumns(HelperUI.FormatMainGrid(data.size(), orientation));
        } else {
            ExecuteQuery(movieDB_discovery, movieDB_filter_date_2015, movieDB_sort_popularity);
        }
    }

    public void ExecuteQuery (String type, String filter, String sorting) {
        boolean onLine = isOnline();

        if (onLine) {
            HelperTMDB helper = new HelperTMDB();
            int orientation = getResources().getConfiguration().orientation;

            helper.execute(new Object[]{type, filter, sorting, gvPopMovies, tvError, btRetry, this, orientation});
        } else {
            tvError.setText(getResources().getString(R.string.error_network_unavailable));
        }

        gvPopMovies.setVisibility(onLine ? View.VISIBLE : View.GONE);

        tvError.setVisibility(onLine ? View.GONE : View.VISIBLE);
        btRetry.setVisibility(onLine ? View.GONE : View.VISIBLE);
    }

    /*
    * From: https://developer.android.com/training/basics/network-ops/managing.html
    * On: 2015-09-22
    * */
    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }

    public void onRetry (View view)
    {
        ExecuteQuery(movieDB_discovery, movieDB_filter_date_2015, movieDB_sort_popularity);
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

        ArrayList<Movie> adapter = (ArrayList<Movie>) ((PosterAdapter) gvPopMovies.getAdapter()).getData();
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

            ExecuteQuery(movieDB_discovery, movieDB_filter_date_2015, movieDB_sort_popularity);
            return true;
        } else if (id == R.id.action_vote) {

            ExecuteQuery(movieDB_discovery, movieDB_filter_date_2015, movieDB_sort_rate);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
