package in.appcrew.moviez.movie;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.graphics.Movie;

import java.util.ArrayList;
import java.util.List;

import data.Movies;
import data.MoviesUiState;
import data.Result;
import data.source.MovieRepository;

/**
 * Created by nmrafiq on 10/11/17.
 */

public class MoviesViewModel extends ViewModel {

    public MutableLiveData<ArrayList<Result>> movieList = new MutableLiveData<>();
    public MutableLiveData<MoviesUiState> movieStateLiveData = new MutableLiveData<>();
    private ArrayList<Result> movieListTemp = new ArrayList<>();
    public final MutableLiveData<Boolean> dataLoading = new MutableLiveData<>();
    private MovieRepository mMovieRepository;
    private int currentPage = 1;
    private boolean isLoading = false;
    public void setMovieRepository(MovieRepository movieRepository){
        this.mMovieRepository = movieRepository;
    }

    public boolean isLoading(){
        return isLoading;
    }

    public void loadTasks(final boolean showLoadingUI) {
        mMovieRepository.getMoviesRemote(currentPage);
        MoviesUiState moviesUiState = new MoviesUiState();
        if (showLoadingUI){
            moviesUiState.setShowProgress(true);
        }
        moviesUiState.setLoading(true);
        movieStateLiveData.setValue(moviesUiState);
    }

    public MutableLiveData<MoviesUiState> setMoviesUI(Movies movie){
        MoviesUiState uiState = new MoviesUiState();
        boolean isError;
        if (movie != null && movie.getPage() != null) {
            currentPage = movie.getPage() + 1;
            isError = false;
        } else {
            isError = true;
        }
        uiState.setCurrentPage(currentPage);
        uiState.setShowProgress(false);
        uiState.setLoading(false);
        uiState.setLoadingError(isError);
        movieStateLiveData.setValue(uiState);
        return movieStateLiveData;
    }

    public LiveData<ArrayList<Result>> getMovies(){
        return Transformations.switchMap(mMovieRepository.getMovies(), movies-> setUpData(movies));
    }

    private LiveData<ArrayList<Result>> setUpData(Movies movies){
        MoviesUiState uiState = new MoviesUiState();
        boolean isError;
        if (movies != null) {
            currentPage = movies.getPage() + 1;
            isError = false;
        } else {
            isError = true;
        }
        uiState.setCurrentPage(currentPage);
        uiState.setShowProgress(false);
        uiState.setLoading(false);
        uiState.setLoadingError(isError);
        movieStateLiveData.setValue(uiState);
        movieListTemp.addAll(movies.getResults());
        movieList.setValue(movieListTemp);
        return movieList;
    }
}
