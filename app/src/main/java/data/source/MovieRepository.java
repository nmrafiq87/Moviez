package data.source;

import android.content.Context;
import android.support.annotation.NonNull;

import data.MovieData;

public class MovieRepository implements MovieDataSource {
    private MovieRemoteRepository movieRemoteRepository;
    private MovieLocalRepository movieLocalRepository;

    public MovieRepository(MovieLocalRepository movieLocalRepository,
                    MovieRemoteRepository movieRemoteRepository){
        this.movieLocalRepository = movieLocalRepository;
        this.movieRemoteRepository = movieRemoteRepository;
    }

    @Override
    public void getMovies(int page, @NonNull LoadMoviesCallback callback) {
        movieRemoteRepository.getMovies(page,callback);
    }

    @Override
    public void getMovie(@NonNull Context context, @NonNull String movieId, @NonNull GetMovieCallback callback) {
        movieRemoteRepository.getMovie(context,movieId,callback);
    }

    @Override
    public void insertMovie(@NonNull final Context context, @NonNull final MovieData movie, @NonNull final UpdateMovieCallback callback) {
        movieLocalRepository.getMovie(context, movie.getId(), new GetMovieCallback() {
            @Override
            public void onMovieLoaded(MovieData movie) {
                if (movie != null){
                    updateMovie(context,movie,callback);
                }
            }

            @Override
            public void onDataNotAvailable() {
                movieLocalRepository.insertMovie(context,movie, callback);
            }
        });
    }

    @Override
    public void updateMovie(@NonNull Context context, @NonNull MovieData movieData, @NonNull UpdateMovieCallback callback) {
        movieLocalRepository.updateMovie(context, movieData, callback);
    }

    @Override
    public void refreshMovies() {

    }
}
