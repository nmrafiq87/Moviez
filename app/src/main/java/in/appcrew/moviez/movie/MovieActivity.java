package in.appcrew.moviez.movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import in.appcrew.moviez.R;
import in.appcrew.moviez.moviedetail.MovieDetailActivity;
import in.appcrew.moviez.utils.ActivityUtils;


public class MovieActivity extends AppCompatActivity implements MovieItemNavigator{
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_KEY = "0e12101a22c608993caa890e9dabea92";
    public static final String IMAGE_API = "https://image.tmdb.org/t/p/w500/";
    private MovieFragment movieFragment;
    public static final String MOVIES_VIEWMODEL_TAG = "MOVIES_VIEWMODEL_TAG";
    public static final String MOVIE_ID = "MOVIE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviez_home);
        movieFragment = findOrCreateViewFragment();
//        MoviesViewModel moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
//        moviesViewModel.setMovieRepository(new MovieRepository(new MovieLocalRepository(), new MovieRemoteRepository()));
//        movieFragment.setMoviesViewModel(moviesViewModel);
    }

    @NonNull
    private MovieFragment findOrCreateViewFragment() {
        MovieFragment movieFragment =
                (MovieFragment) getSupportFragmentManager().findFragmentById(R.id.content);
        if (movieFragment == null) {
            // Create the fragment
            movieFragment = MovieFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), movieFragment, R.id.contentFrame);
        }
        return movieFragment;
    }

    @Override
    public void onItemClick(String movieId) {
        Log.d("On Item Clicked","On Item Clicked" + movieId);
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MOVIE_ID,movieId);
        startActivity(intent);
    }
}
