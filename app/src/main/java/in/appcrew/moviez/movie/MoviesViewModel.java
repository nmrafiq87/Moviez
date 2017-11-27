package in.appcrew.moviez.movie;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import java.util.ArrayList;

import data.Result;
import data.source.MovieDataSource;
import data.source.MovieRepository;

/**
 * Created by nmrafiq on 10/11/17.
 */

public class MoviesViewModel extends BaseObservable {
    // These observable fields will update Views automatically
    public final ObservableList<Result> item = new ObservableArrayList<>();
    public final ObservableBoolean dataLoading = new ObservableBoolean(false);
    final ObservableField<String> snackbarText = new ObservableField<>();
    private final MovieRepository mMovieRepository;
    private final ObservableBoolean mIsDataLoadingError = new ObservableBoolean(false);
    private Context mContext; // To avoid leaks, this must be an Application Context.

    public MoviesViewModel(
            MovieRepository repository,
            Context context) {
        mContext = context.getApplicationContext(); // Force use of Application Context.
        mMovieRepository = repository;
        // Set initial state
//        setFiltering(MovieFilterType.ALL_MOVIES);
    }

    public void start() {
        loadTasks(true);
    }

    public void loadTasks(final boolean showLoadingUI) {
        if (showLoadingUI) {
            dataLoading.set(true);
        }
        mMovieRepository.getMovies(new MovieDataSource.LoadMoviesCallback() {
            @Override
            public void onMoviesLoaded(ArrayList<Result> movies) {
                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.
//                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
//                    EspressoIdlingResource.decrement(); // Set app as idle.
//                }
                ArrayList<Result> moviesToShow = new ArrayList<Result>();
                for (Result movie : movies) {
                    moviesToShow.add(movie);
                }
                if (showLoadingUI) {
                    dataLoading.set(false);
                }
                mIsDataLoadingError.set(false);
                item.clear();
                item.addAll(moviesToShow);
            }

            @Override
            public void onDataNotAvailable() {
                mIsDataLoadingError.set(true);
            }
        });
    }
}
