package in.appcrew.moviez.movie;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import java.util.ArrayList;
import data.Movies;
import data.Result;
import data.source.MovieDataSource;
import data.source.MovieRepository;

/**
 * Created by nmrafiq on 10/11/17.
 */

public class MoviesViewModel extends ViewModel {
    // These observable fields will update Views automatically
    public final MutableLiveData<ArrayList<Result>> movieList = new MutableLiveData<>();
    private ArrayList<Result> movieListTemp = new ArrayList<>();
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
            if (movieList.getValue() != null && movieList.getValue().size() > 0){
                return;
            }
            dataLoading.setValue(true);
        }
        isLoading = true;
        mMovieRepository.getMovies(currentPage, new MovieDataSource.LoadMoviesCallback() {
            @Override
            public void onMoviesLoaded(Movies movies) {
                if (movies != null && movies.getPage() != null) {
                    currentPage = movies.getPage() + 1;
                    if (movies.getResults()!=null){
                        movieListTemp.addAll(movies.getResults());
                        movieList.setValue(movieListTemp);
                    }
                }
                isLoading = false;
                if (showLoadingUI) {
                    dataLoading.setValue(false);
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
