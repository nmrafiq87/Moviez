package data.source;

import android.content.Context;
import android.graphics.Movie;
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

    interface UpdateMovieCallback {

        void onMovieUpdated(MovieData movie);

    }

    void getMovies(int page, @NonNull LoadMoviesCallback callback);

    void getMovie(@NonNull Context context, @NonNull String movieId, @NonNull GetMovieCallback callback);

    void insertMovie(@NonNull Context context, @NonNull MovieData movie, @NonNull UpdateMovieCallback callback);

    void updateMovie(@NonNull Context context, @NonNull MovieData movieData, @NonNull UpdateMovieCallback callback);

    void refreshMovies();

}
