package data.source;

import android.support.annotation.NonNull;

import data.Movies;

/**
 * Created by nmrafiq on 13/12/17.
 */

public class MovieLocalRepository implements MovieDataSource {

    @Override
    public void getMovie(@NonNull String movieId, @NonNull GetMovieCallback callback) {

    }

    @Override
    public void getMovies(int page, @NonNull LoadMoviesCallback callback) {

    }

    @Override
    public void saveMovie(@NonNull Movies movie) {

    }

    @Override
    public void refreshMovies() {

    }
}
