package data.source;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import ApiInterface.MovieInterface;
import data.Movie;
import data.Result;
import in.appcrew.moviez.movie.MovieActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static in.appcrew.moviez.movie.MovieActivity.API_KEY;

/**
 * Created by nmrafiq on 10/11/17.
 */

public class MovieRepository implements MovieDataSource {

    private static MovieRepository INSTANCE = null;

    @Override
    public void getMovie(@NonNull String taskId, @NonNull GetMovieCallback callback) {



    }

    @Override
    public void getMovies(@NonNull final LoadMoviesCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieInterface movieService = retrofit.create(MovieInterface.class);
        Call<Movie> call = movieService.getMovie(API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                ArrayList<Result> mMovieList = response.body().getResults();
                callback.onMoviesLoaded(mMovieList);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void refreshMovies() {

    }

    @Override
    public void saveMovie(@NonNull Movie movie) {

    }

    public static MovieRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MovieRepository();
        }
        return INSTANCE;
    }
}
