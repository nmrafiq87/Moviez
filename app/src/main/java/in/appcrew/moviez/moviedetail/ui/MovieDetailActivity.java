package in.appcrew.moviez.moviedetail.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import in.appcrew.moviez.R;
import in.appcrew.moviez.movie.ui.MovieActivity;
import in.appcrew.moviez.utils.ActivityUtils;

public class MovieDetailActivity extends AppCompatActivity {


    private MovieDetailFragment mMovieDetailFragment;
    public static final String TAG = "MOVIE_DETAIL_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        mMovieDetailFragment = findOrCreateFragment();
        mMovieDetailFragment.setMovieId(getIntent().getStringExtra(MovieActivity.MOVIE_ID));
    }

    private MovieDetailFragment findOrCreateFragment(){
        MovieDetailFragment movieDetailFragment = (MovieDetailFragment)getSupportFragmentManager().findFragmentById(R.id.detail_content);
        if (movieDetailFragment == null){
            movieDetailFragment = new MovieDetailFragment().newInstance();
        }
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),movieDetailFragment,R.id.detail_content);
        return movieDetailFragment;
    }
}
