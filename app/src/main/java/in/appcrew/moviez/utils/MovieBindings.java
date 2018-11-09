package in.appcrew.moviez.utils;

import android.databinding.BindingAdapter;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.ArrayList;

import in.appcrew.moviez.R;
import in.appcrew.moviez.movie.ui.MovieActivity;
import in.appcrew.moviez.moviedetail.MovieDetailAdapter;

/**
 * Created by nmrafiq on 17/11/17.
 */

public class MovieBindings {

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
