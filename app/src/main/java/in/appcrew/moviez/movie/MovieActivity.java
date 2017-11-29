package in.appcrew.moviez.movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import data.source.MovieRepository;
import in.appcrew.moviez.R;
import in.appcrew.moviez.ViewModelHolder;
import in.appcrew.moviez.moviedetail.MovieDetailActivity;
import in.appcrew.moviez.utils.ActivityUtils;


public class MovieActivity extends AppCompatActivity implements MovieItemNavigator{
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_KEY = "0e12101a22c608993caa890e9dabea92";
    public static final String IMAGE_API = "https://image.tmdb.org/t/p/w500/";
    private MovieFragment movieFragment;
    private MoviesViewModel moviesViewModel;
    public static final String MOVIES_VIEWMODEL_TAG = "MOVIES_VIEWMODEL_TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviez_home);
        movieFragment = findOrCreateViewFragment();
        moviesViewModel= findOrCreateViewModel();
        movieFragment.setViewModel(moviesViewModel);
    }


    private MoviesViewModel findOrCreateViewModel() {
        // In a configuration change we might have a ViewModel present. It's retained using the
        // Fragment Manager.
        @SuppressWarnings("unchecked")
        ViewModelHolder<MoviesViewModel> retainedViewModel =
                (ViewModelHolder<MoviesViewModel>) getSupportFragmentManager()
                        .findFragmentByTag(MOVIES_VIEWMODEL_TAG);

        if (retainedViewModel != null && retainedViewModel.getViewmodel() != null) {
            // If the model was retained, return it.
            return retainedViewModel.getViewmodel();
        } else {
            // There is no ViewModel yet, create it.
            MoviesViewModel viewModel = new MoviesViewModel(MovieRepository.getInstance(), getApplicationContext());
            // and bind it to this Activity's lifecycle using the Fragment Manager.
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel),
                    MOVIES_VIEWMODEL_TAG);
            return viewModel;
        }
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
        startActivity(intent);
    }
}
