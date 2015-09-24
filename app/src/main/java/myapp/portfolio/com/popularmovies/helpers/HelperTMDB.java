package myapp.portfolio.com.popularmovies.helpers;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import myapp.portfolio.com.popularmovies.R;
import myapp.portfolio.com.popularmovies.adapters.PosterAdapter;
import myapp.portfolio.com.popularmovies.entities.Movie;
import myapp.portfolio.com.popularmovies.tools.Constants;
import myapp.portfolio.com.popularmovies.tools.TMDBKey;

import static myapp.portfolio.com.popularmovies.tools.Constants.*;
import static myapp.portfolio.com.popularmovies.tools.TMDBKey.*;

/**
 * Created by JPabon on 2015-08-24.
 */
public class HelperTMDB extends AsyncTask {
    GridView gvPopMovies;

    TextView tvError;
    Button btRetry;

    Context context;

    @Override
    protected ArrayList<Movie> doInBackground(Object[] params) {
        String type_query = (String) params [0];
        String filter_query = (String) params [1];
        String sort_query = (String) params [2];

        gvPopMovies = (GridView) params [3];

        tvError = (TextView) params [4];
        btRetry = (Button) params [5];

        context = (Context) params [6];

        int orientation = (Integer) params [7];

        try {
            return searchTMDB(type_query, filter_query, sort_query, orientation);
        } catch (IOException e) {
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Object result) {
        boolean ok = result != null;

        if (ok) {
            ((PosterAdapter) gvPopMovies.getAdapter()).setData((List<Movie>) result);
            ((PosterAdapter) gvPopMovies.getAdapter()).notifyDataSetChanged();
        } else {
            tvError.setText(context.getResources().getString(R.string.error_fetch_data));
        }

        gvPopMovies.setVisibility(ok ? View.VISIBLE : View.GONE);

        tvError.setVisibility(ok ? View.GONE : View.VISIBLE);
        btRetry.setVisibility(ok ? View.GONE : View.VISIBLE);
    };

    public ArrayList<Movie> searchTMDB(String type_query, String filter, String sort_query, int orientation) throws IOException, ParseException {
        URL url = buildURL(type_query, filter, sort_query);

        InputStream stream = null;

        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECT_TIMEOUT);

            conn.setRequestMethod("GET");
            conn.addRequestProperty("Accept", "application/json");

            conn.setDoInput(true);

            conn.connect();

            int responseCode = conn.getResponseCode();
            stream = conn.getInputStream();

            return parseResult(StreamToString(stream), orientation);
        } catch (Exception ex) {
            return null;
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    @NonNull
    private URL buildURL(String type_query, String filter, String sort_query) throws MalformedURLException {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(movieDB_base_query);
        stringBuilder.append(type_query);
        stringBuilder.append(filter);
        stringBuilder.append(sort_query);
        stringBuilder.append(movieDB_key);

        return new URL(stringBuilder.toString());
    }

    private ArrayList<Movie> parseResult(String result, int orientation) throws ParseException {
        ArrayList<Movie> results = new ArrayList<Movie>();

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray array = (JSONArray) jsonObject.get("results");

            gvPopMovies.setNumColumns(HelperUI.FormatMainGrid (array.length(), orientation));

            for (int i = 0; i < array.length(); i++) {
                Movie movie  = new Movie(array.getJSONObject(i));
                results.add(movie);
            }
        } catch (JSONException e) {
            System.err.println(e);
        }
        return results;
    }

    public String StreamToString(InputStream stream) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");

        BufferedReader bufferedReader = new BufferedReader(reader);

        String line = "";

        line = bufferedReader.readLine();

        /*If in the stream there is a date field in null, the bufferReader throws an error*/
        if (line.contains("\"release_date\":null")) {
            line = line.replace("\"release_date\":null", "\"release_date\":1900-01-01");
        }

        return line;
    }
}
