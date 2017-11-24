package data.source;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import data.Movie;
import data.Result;

/**
 * Created by nmrafiq on 10/11/17.
 */

public interface MovieDataSource {
    interface LoadMoviesCallback {

        void onMoviesLoaded(ArrayList<Result> movies);

        void onDataNotAvailable();
    }

    interface GetMovieCallback {

        void onTaskLoaded(Movie movie);

        void onDataNotAvailable();
    }

    void getMovies(@NonNull LoadMoviesCallback callback);

    void getMovie(@NonNull String taskId, @NonNull GetMovieCallback callback);

    void saveMovie(@NonNull Movie movie);

    void refreshMovies();

}
