package in.appcrew.moviez.movie;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import java.util.ArrayList;

import data.Movies;
import data.Result;
import data.source.MovieDataSource;
import data.source.MovieRemoteRepository;
import data.source.MovieRepository;

/**
 * Created by nmrafiq on 10/11/17.
 */

public class MoviesViewModel extends ViewModel {
    // These observable fields will update Views automatically
    public final ObservableList<Result> item = new ObservableArrayList<>();
    public final ObservableBoolean dataLoading = new ObservableBoolean(false);
    private MovieRepository mMovieRepository;
    private final ObservableBoolean mIsDataLoadingError = new ObservableBoolean(false);
    private int currentPage = 1;
    private boolean isLoading = false;

    public void setMovieRepository(MovieRepository movieRepository){
        this.mMovieRepository = movieRepository;
    }

    public void start() {
        loadTasks(true);
    }

    public boolean isLoading(){
        return isLoading;
    }

    public void loadTasks(final boolean showLoadingUI) {
        isLoading = true;
        if (showLoadingUI) {
            dataLoading.set(true);
        }
        mMovieRepository.getMovies(currentPage, new MovieDataSource.LoadMoviesCallback() {
            @Override
            public void onMoviesLoaded(Movies movies) {
                if (movies != null && movies.getPage() != null) {
                    currentPage = movies.getPage() + 1;
                    if (movies.getResults()!=null){
                        ArrayList<Result> moviesToShow = movies.getResults();
                        item.clear();
                        item.addAll(moviesToShow);
                    }
                }
                isLoading = false;
                if (showLoadingUI) {
                    dataLoading.set(false);
                }
                mIsDataLoadingError.set(false);
            }
            @Override
            public void onDataNotAvailable() {
                isLoading = false;
                mIsDataLoadingError.set(true);
            }
        });
    }

}
