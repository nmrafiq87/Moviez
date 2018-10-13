package in.appcrew.moviez.movie;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.graphics.Movie;

import java.util.ArrayList;

import data.Movies;
import data.Result;
import data.source.MovieRepository;

/**
 * Created by nmrafiq on 10/11/17.
 */

public class MoviesViewModel extends ViewModel {

    public final MutableLiveData<Movies> movieList = new MutableLiveData<>();
    private ArrayList<Movies> movieListTemp = new ArrayList<>();
    public final MutableLiveData<Boolean> dataLoading = new MutableLiveData<>();
    private MovieRepository mMovieRepository;
    private final ObservableBoolean mIsDataLoadingError = new ObservableBoolean(false);
    private int currentPage = 1;
    private boolean isLoading = false;
    public void setMovieRepository(MovieRepository movieRepository){
        this.mMovieRepository = movieRepository;
    }

    public boolean isLoading(){
        return isLoading;
    }

    public void loadTasks(final boolean showLoadingUI) {
        if (showLoadingUI) {
            if (movieList.getValue() != null && movieList.getValue().getResults().size() > 0){
                return;
            }
            dataLoading.setValue(true);
        }
        isLoading = true;
        mMovieRepository.getMoviesRemote(currentPage);
        LiveData<Movies> movieResult = mMovieRepository.getMovies();
//        movieList.setValue(movieResult.getValue());

//        if (movies.getValue() != null) {
//            if (movies.getValue().getPage() != null) {
//                currentPage = movies.getValue().getPage() + 1;
//                if (movies.getValue().getResults()!=null){
//                    movieListTemp.addAll(movies.getValue().getResults());
//                    movieList.setValue(movieListTemp);
//                }
//            }
//            isLoading = false;
//            if (showLoadingUI) {
//                dataLoading.setValue(false);
//            }
//            mIsDataLoadingError.set(false);
//        } else {
//            isLoading = false;
//            mIsDataLoadingError.set(true);
//        }
    }

    public LiveData<Movies> getMovies(){
        return mMovieRepository.getMovies();
    }

}
