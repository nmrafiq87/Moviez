package data.source;

import android.support.annotation.NonNull;

import data.MovieData;
import data.Movies;

/**
 * Created by nmrafiq on 10/11/17.
 */

public interface MovieDataSource {
    interface LoadMoviesCallback {

        void onMoviesLoaded(Movies movie);

        void onDataNotAvailable();
    }

    interface GetMovieCallback {

        void onMovieLoaded(MovieData movie);

        void onDataNotAvailable();
    }

    void getMovies(int page, @NonNull LoadMoviesCallback callback);

    void getMovie(@NonNull String movieId, @NonNull GetMovieCallback callback);

    void saveMovie(@NonNull Movies movie);

    void refreshMovies();

}
