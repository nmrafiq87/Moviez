package data.source;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Movie;
import android.net.Uri;
import android.support.annotation.NonNull;

import ApiInterface.MovieInterface;
import data.MovieData;
import data.Movies;
import data.Result;
import in.appcrew.moviez.MovieApplication;
import in.appcrew.moviez.movie.MovieActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static in.appcrew.moviez.movie.MovieActivity.API_KEY;

public class MovieRepository{
    private Context context;
    private MutableLiveData<Movies> moviesLivedata = new MutableLiveData<>();
    private MutableLiveData<MovieData> moviesDetailLivedata = new MutableLiveData<>();
    private MutableLiveData<MovieData> localMovieLivedata = new MutableLiveData<>();

    public MovieRepository(Context context){
        this.context = context;
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

    public LiveData<Movies> getMovies(){
        return moviesLivedata;
    }

    public void getMovie(String movieId){
        String[] selectionArgs = {""};
        String selectionClause =  MoviePersistentContract.MovieEntry.MOVIE_ID + " = ?";
        selectionArgs[0] = movieId;
        AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(context.getContentResolver()) {
            @Override
            protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                super.onQueryComplete(token, cookie, cursor);
                MovieData movieData = new MovieData();
                if (cursor != null && cursor.moveToNext()){
                    movieData.setId(cursor.getString(cursor.getColumnIndex(MoviePersistentContract.MovieEntry.MOVIE_ID)));
                    movieData.setLove(cursor.getInt(cursor.getColumnIndex(MoviePersistentContract.MovieEntry.MOVIE_FAVOURITE)));
                    localMovieLivedata.setValue(movieData);
                }
            }
        };
        asyncQueryHandler.startQuery(0,null,MovieContentProvider.CONTENT_URI,null,selectionClause,selectionArgs,null);
    }

    public void getMovieDetailRemote(String movieId) {
        MovieInterface movieService = MovieApplication.getRetroFit().create(MovieInterface.class);
        MutableLiveData<MovieData> movieLiveData = new MutableLiveData<>();
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

    public void insertMovie(MovieData movie) {
        ContentValues cv = new ContentValues();
        cv.put(MoviePersistentContract.MovieEntry.MOVIE_FAVOURITE, movie.getLove() == 0 ? 1 : 0);
        cv.put(MoviePersistentContract.MovieEntry.MOVIE_ID,movie.getId());
        cv.put(MoviePersistentContract.MovieEntry.MOVIE_NAME,movie.getOriginalTitle());

        AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(context.getContentResolver()) {
            @Override
            protected void onInsertComplete(int token, Object cookie, Uri uri) {
                super.onInsertComplete(token, cookie, uri);
                if (uri != null){
                    movie.setLove(movie.getLove() == 0 ? 1 : 0);
                    localMovieLivedata.setValue(movie);
                }
            }
        };
        asyncQueryHandler.startInsert(0,null,MovieContentProvider.CONTENT_URI,cv);
    }

    public void updateMovie(MovieData movie) {
        String[] selectionArgs = {""};
        String selectionClause =  MoviePersistentContract.MovieEntry.MOVIE_ID + " = ?";
        selectionArgs[0] = movie.getId();
        ContentValues cv = new ContentValues();
        cv.put(MoviePersistentContract.MovieEntry.MOVIE_FAVOURITE, movie.getLove() == 0 ? 1 : 0);
        AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(context.getContentResolver()) {
            @Override
            protected void onUpdateComplete(int token, Object cookie, int result) {
                super.onUpdateComplete(token, cookie, result);
                if (result > 0){
                    MovieData movieData = new MovieData();
                    movieData.setId(movie.getId());
                    movieData.setLove(movie.getLove() == 0 ? 1 : 0);
                    localMovieLivedata.setValue(movieData);
                }
            }
        };
        asyncQueryHandler.startUpdate(0,null,MovieContentProvider.CONTENT_URI,cv,selectionClause,selectionArgs);
    }

    public LiveData<MovieData> getMovieLocalLiveData(){
        return localMovieLivedata;
    }

    public LiveData<MovieData> getMovieRemoteLiveData(){
        return moviesDetailLivedata;
    }


}
