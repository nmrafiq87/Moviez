package data.source;

import android.support.annotation.NonNull;

import ApiInterface.MovieInterface;
import data.MovieData;
import data.Movies;
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
    private Retrofit RETROFIT_INSTANCE = null;
    @Override
    public void getMovie(@NonNull String movieId, @NonNull final GetMovieCallback callback) {
        RETROFIT_INSTANCE = getRetroFit();
        MovieInterface movieService = RETROFIT_INSTANCE.create(MovieInterface.class);
        Call<MovieData> call = movieService.getMovie(movieId,API_KEY);
        call.enqueue(new Callback<MovieData>() {
            @Override
            public void onResponse(Call<MovieData> call, Response<MovieData> response) {
                callback.onMovieLoaded(response.body());
            }

            @Override
            public void onFailure(Call<MovieData> call, Throwable t) {
                callback.onDataNotAvailable();

            }
        });
    }

    @Override
    public void getMovies(final int page, @NonNull final LoadMoviesCallback callback) {
        RETROFIT_INSTANCE = getRetroFit();
        MovieInterface movieService = RETROFIT_INSTANCE.create(MovieInterface.class);
        Call<Movies> call = movieService.getMovies(API_KEY,page);
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                callback.onMoviesLoaded(response.body());
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void refreshMovies() {

    }

    @Override
    public void saveMovie(@NonNull Movies movie) {

    }

    public static MovieRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MovieRepository();
        }
        return INSTANCE;
    }

    public Retrofit getRetroFit(){
        if (RETROFIT_INSTANCE == null){
            RETROFIT_INSTANCE = new Retrofit.Builder()
                    .baseUrl(MovieActivity.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return RETROFIT_INSTANCE;
    }
}
