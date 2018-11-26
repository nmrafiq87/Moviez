package in.appcrew.moviez.movie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import in.appcrew.moviez.R;
import in.appcrew.moviez.moviedetail.ui.MovieDetailActivity;
import in.appcrew.moviez.utils.ActivityUtils;


public class MovieActivity extends AppCompatActivity implements MovieItemNavigator {
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_KEY = "0e12101a22c608993caa890e9dabea92";
    public static final String IMAGE_API = "https://image.tmdb.org/t/p/w500/";
    public static final String MOVIE_ID = "MOVIE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviez_home);
        findOrCreateViewFragment();
    }

    @NonNull
    private void findOrCreateViewFragment() {
        MovieFragment movieFragment =
                (MovieFragment) getSupportFragmentManager().findFragmentById(R.id.content);
        if (movieFragment == null) {
            movieFragment = MovieFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), movieFragment, R.id.contentFrame);
        }
    }

    @Override
    public void onItemClick(String movieId) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MOVIE_ID,movieId);
        startActivity(intent);
    }
}