package in.appcrew.moviez.moviedetail;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.util.Log;

import data.Movie;
import data.source.MovieDataSource;
import data.source.MovieRepository;

/**
 * Created by practo on 30/11/17.
 */

public class MovieDetailViewModel extends BaseObservable {
    private ObservableField<Movie> mMovie = new ObservableField<>();
    public ObservableField<String> mTitle = new ObservableField<>();
    public ObservableField<String> mBackdropImage = new ObservableField<>();
    private MovieRepository mMovieRepository;
    private Context mContext;


    MovieDetailViewModel(MovieRepository movieRepository, Context context){
        this.mMovieRepository = movieRepository;
        this.mContext = context;
        mMovie.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                Movie movie = mMovie.get();

                if (movie != null) {
                    mTitle.set(movie.getTitle());
                    mBackdropImage.set(movie.getBackdropPath());
                }
            }
        });
    }


    public void start(String movieId){
        loadMovies(movieId);
    }



    private void loadMovies(final String movieId){
        mMovieRepository = new MovieRepository();
        mMovieRepository.getMovie(movieId, new MovieDataSource.GetMovieCallback() {
            @Override
            public void onMovieLoaded(Movie movie) {
                Log.d("Movie ", movie.getOriginalTitle());
                mMovie.set(movie);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d("Movie Title","Movie Error");
            }
        });
    }

}
