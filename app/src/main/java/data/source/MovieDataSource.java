package data.source;

import android.support.annotation.NonNull;

import data.Movie;

/**
 * Created by nmrafiq on 10/11/17.
 */

public interface MovieDataSource {
    interface LoadMoviesCallback {

        void onMoviesLoaded(Movie movie);

        void onDataNotAvailable();
    }

    interface GetMovieCallback {

        void onTaskLoaded(Movie movie);

        void onDataNotAvailable();
    }

    void getMovies(int page, @NonNull LoadMoviesCallback callback);

    void getMovie(@NonNull String taskId, @NonNull GetMovieCallback callback);

    void saveMovie(@NonNull Movie movie);

    void refreshMovies();

}
