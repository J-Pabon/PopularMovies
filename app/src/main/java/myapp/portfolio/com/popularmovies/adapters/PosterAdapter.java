package myapp.portfolio.com.popularmovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import myapp.portfolio.com.popularmovies.R;
import myapp.portfolio.com.popularmovies.entities.Movie;

/**
 * Created by JPabon on 2015-08-12.
 * Adapted from: http://developer.android.com/guide/topics/ui/layout/gridview.html
 * By: Android
 * On: 2015-08-12
 */
public class PosterAdapter extends BaseAdapter {
    private Context context;
    private List<Movie> data;

    public List<Movie> getData() {
        return data;
    }

    public void setData(List<Movie> data) {
        this.data = data;
    }

    public PosterAdapter(Context c) {
        context = c;
        setData(new ArrayList<Movie>());
    }

    @Override
    public int getCount() {
        return getData().size();
    }

    @Override
    public Object getItem(int position) {
        return getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return getData().get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.layout_main_poster, parent, false);

        ImageView ivMainPoster = (ImageView) layout.findViewById(R.id.ivMainPoster);

        ivMainPoster.setPadding(15, 15, 15, 15);
        ivMainPoster.setAdjustViewBounds(true);
        ivMainPoster.setScaleType(ImageView.ScaleType.FIT_XY);

        Picasso.with(context).load(getData().get(position).getPoster_path()).into(ivMainPoster);

        TextView tvTitle = (TextView) layout.findViewById(R.id.tvMainTitle);
        tvTitle.setText(getData().get(position).getTitle());

        return layout;
    }
}
