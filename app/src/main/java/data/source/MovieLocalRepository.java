package data.source;

import android.content.AsyncQueryHandler;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Movie;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import data.MovieData;
import data.Movies;

/**
 * Created by nmrafiq on 13/12/17.
 */

public class MovieLocalRepository implements MovieDataSource {

    @Override
    public void getMovie(@NonNull Context context, @NonNull String movieId, @NonNull final GetMovieCallback callback) {
        String[] selectionArgs = {""};
        String selectionClause =  MoviePersistentContract.MovieEntry.MOVIE_ID + " = ?";
        selectionArgs[0] = movieId;
        Uri uri = ContentUris.withAppendedId(MovieContentProvider.CONTENT_URI,Long.valueOf(movieId));
        AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(context.getContentResolver()) {
            @Override
            protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                super.onQueryComplete(token, cookie, cursor);
                MovieData movieData = new MovieData();
                if (cursor != null && cursor.moveToNext()){
                    movieData.setId(cursor.getString(cursor.getColumnIndex(MoviePersistentContract.MovieEntry.MOVIE_ID)));
                    movieData.setLove(cursor.getInt(cursor.getColumnIndex(MoviePersistentContract.MovieEntry.MOVIE_FAVOURITE)));
                    callback.onMovieLoaded(movieData);
                }else{
                    callback.onDataNotAvailable();
                }
            }
        };
        asyncQueryHandler.startQuery(0,null,uri,null,selectionClause,selectionArgs,null);
    }

    @Override
    public void getMovies(@NonNull Context context, int page, @NonNull final LoadMoviesCallback callback) {

    }

    @Override
    public void insertMovie(@NonNull Context context, @NonNull final MovieData movie, @NonNull final UpdateMovieCallback updateMovieCallback) {
        ContentValues cv = new ContentValues();
        cv.put(MoviePersistentContract.MovieEntry.MOVIE_FAVOURITE,movie.getLove() == 0 ? 1 :0);
        cv.put(MoviePersistentContract.MovieEntry.MOVIE_ID,movie.getId());
        cv.put(MoviePersistentContract.MovieEntry.MOVIE_NAME,movie.getOriginalTitle());

        AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(context.getContentResolver()) {
            @Override
            protected void onInsertComplete(int token, Object cookie, Uri uri) {
                super.onInsertComplete(token, cookie, uri);
                if (uri != null){
                    movie.setLove(movie.getLove() == 0 ? 1 : 0);
                    updateMovieCallback.onMovieUpdated(movie);
                }
            }
        };
        asyncQueryHandler.startInsert(0,null,MovieContentProvider.CONTENT_URI,cv);

    }

    @Override
    public void updateMovie(@NonNull Context context, @NonNull final MovieData movie, @NonNull final UpdateMovieCallback updateMovieCallback) {
        String[] selectionArgs = {""};
        String selectionClause =  MoviePersistentContract.MovieEntry.MOVIE_ID + " = ?";
        selectionArgs[0] = movie.getId();
        ContentValues cv = new ContentValues();
        cv.put(MoviePersistentContract.MovieEntry.MOVIE_FAVOURITE,movie.getLove() == 0 ? 1 : 0);
        AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(context.getContentResolver()) {
            @Override
            protected void onUpdateComplete(int token, Object cookie, int result) {
                super.onUpdateComplete(token, cookie, result);
                if (result > 0){
                    movie.setLove(movie.getLove() == 0 ? 1 : 0);
                    updateMovieCallback.onMovieUpdated(movie);
                }
            }
        };
        asyncQueryHandler.startUpdate(0,null,MovieContentProvider.CONTENT_URI,cv,selectionClause,selectionArgs);

    }

    @Override
    public void refreshMovies() {

    }
}
