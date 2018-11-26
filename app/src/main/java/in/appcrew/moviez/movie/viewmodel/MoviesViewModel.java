package in.appcrew.moviez.movie.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import in.appcrew.moviez.entity.Movies;
import in.appcrew.moviez.entity.MoviesUiState;
import in.appcrew.moviez.entity.Result;
import in.appcrew.moviez.repository.MovieRepository;

/**
 * Created by nmrafiq on 10/11/17.
 */

public class MoviesViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Result>> movieList = new MutableLiveData<>();
    private MutableLiveData<MoviesUiState> movieStateLiveData = new MutableLiveData<>();
    private ArrayList<Result> movieListTemp = new ArrayList<>();
    private MovieRepository mMovieRepository;
    private int currentPage = 1;

    public void setMovieRepository(MovieRepository movieRepository){
        this.mMovieRepository = movieRepository;
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

    public LiveData<ArrayList<Result>> getMovies(){
        return Transformations.switchMap(mMovieRepository.getMovies(), movies-> setUpData(movies));
    }

    public LiveData<MoviesUiState> getUIState() {
        return movieStateLiveData;
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
