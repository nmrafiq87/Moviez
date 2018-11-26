package in.appcrew.moviez.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import in.appcrew.moviez.database.movie.MovieDao;
import in.appcrew.moviez.database.movie.MovieEntity;
import in.appcrew.moviez.database.movie.MoviesDatabase;
import in.appcrew.moviez.entity.MovieData;
import in.appcrew.moviez.entity.Movies;
import in.appcrew.moviez.MovieApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.appcrew.moviez.movie.ui.MovieActivity.API_KEY;

public class MovieRepository{
    private MovieDao movieDao;
    private MutableLiveData<Movies> moviesLivedata = new MutableLiveData<>();
    private MutableLiveData<MovieData> moviesDetailLivedata = new MutableLiveData<>();
    private ExecutorService executorService;

    public MovieRepository(Context context){
        MoviesDatabase db = MoviesDatabase.getDatabase(context);
        executorService = Executors.newSingleThreadExecutor();
        movieDao = db.movieDao();
    }

    public void getMoviesRemote(int page) {
        MovieInterface movieService = MovieApplication.getRetroFit().create(MovieInterface.class);
        Call<Movies> call = movieService.getMovies(API_KEY,page);
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                moviesLivedata.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                moviesLivedata.setValue(null);
            }
        });
    }

    public void getMovieDetailRemote(String movieId) {
        MovieInterface movieService = MovieApplication.getRetroFit().create(MovieInterface.class);
        Call<MovieData> call = movieService.getMovie(movieId,API_KEY);
        call.enqueue(new Callback<MovieData>() {
            @Override
            public void onResponse(Call<MovieData> call, Response<MovieData> response) {
                moviesDetailLivedata.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MovieData> call, Throwable t) {
                moviesDetailLivedata.setValue(null);
            }
        });
    }

    public LiveData<MovieEntity> getMovieFromRoom(String movieId){
        return movieDao.getMovie(movieId);
    }

    public void insertMovieRoom(MovieEntity movie){
        executorService.execute(() -> {
            movieDao.insert(movie);
        });
    }

    public LiveData<Movies> getMovies(){
        return moviesLivedata;
    }

    public LiveData<MovieData> getMovieDetailLiveData(){
        return moviesDetailLivedata;
    }

}
