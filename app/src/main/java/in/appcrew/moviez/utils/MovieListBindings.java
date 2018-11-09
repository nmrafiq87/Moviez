package in.appcrew.moviez.utils;

import android.database.Observable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.ArrayList;

import data.MovieData;
import data.Result;
import in.appcrew.moviez.R;
import in.appcrew.moviez.movie.MovieActivity;
import in.appcrew.moviez.movie.MovieAdapter;
import in.appcrew.moviez.moviedetail.MovieDetailAdapter;

/**
 * Created by nmrafiq on 17/11/17.
 */

public class MovieListBindings {

    @BindingAdapter({"bind:imgView"})
    public static void loadImage(ImageView imgView, String imgUrl) {
        imgView.setImageURI(Uri.parse(MovieActivity.IMAGE_API + imgUrl));
    }

    @BindingAdapter({"bind:movieTitle","bind:movieDescription"})
    public static void setMovie(RecyclerView view, ArrayList<String> arrListTitle,
                                ArrayList<String> arrListDesc) {
        MovieDetailAdapter movieDetailAdapter = (MovieDetailAdapter)view.getAdapter();
        if (movieDetailAdapter != null){
            movieDetailAdapter.replaceData(arrListTitle,arrListDesc);
        }
    }

    @BindingAdapter({"movieLove"})
    public static void setMovieLove(FloatingActionButton floatingActionButton, int love){
            if (love == 1) {
                floatingActionButton.setImageResource(R.drawable.ic_favorite_fill);
            } else {
                floatingActionButton.setImageResource(R.drawable.ic_favorite_border);
            }
    }
}
