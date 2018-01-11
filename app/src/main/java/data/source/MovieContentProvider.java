package data.source;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by nmrafiq on 13/12/17.
 */

public class MovieContentProvider extends ContentProvider {
    private MovieDbHelper movieDbHelper;
    private static final String AUTHORITY = "in.appcrew.moviez.data.provider";
    private static final String BASE_PATH = "movie";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    private static final String CONTENT_MOVIE = "vnd.android.cursor.dir/vnd.in.appcrew.moviez.data.provider/movie";
    private static final String CONTENT_MOVIE_ID = "vnd.android.cursor.item/vnd.in.appcrew.moviez.data.provider/movie";
    private static final int MOVIES = 1;
    private static final int MOVIE_ID = 2;
    private static final UriMatcher uriMatcher = getUriMatcher();
    private static UriMatcher getUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, BASE_PATH, MOVIES);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", MOVIE_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        movieDbHelper = new MovieDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        Cursor retCursor;
        long _id;
        switch(uriMatcher.match(uri)){
            case MOVIE_ID:
                _id = ContentUris.parseId(uri);
                retCursor = db.query(
                        MoviePersistentContract.MovieEntry.FAVOURITE_MOVIE_TABLE,
                        projection, selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        switch (uriMatcher.match(uri)) {
            case MOVIES:
                return CONTENT_MOVIE;
            case MOVIE_ID:
                return CONTENT_MOVIE_ID;
            default:
                return "";
        }
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        int affectedRows;
        switch(uriMatcher.match(uri)){
            case MOVIES:
                affectedRows = db.update(MoviePersistentContract.MovieEntry.FAVOURITE_MOVIE_TABLE,values,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return affectedRows;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        long _id;
        Uri returnUri;

        switch(uriMatcher.match(uri)){
            case MOVIES:
                _id = db.insert(MoviePersistentContract.MovieEntry.FAVOURITE_MOVIE_TABLE, null, values);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(_id > 0){
            returnUri = ContentUris.withAppendedId(CONTENT_URI, _id);
        } else{
            throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
        }
        // Use this on the URI passed into the function to notify any observers that the uri has
        // changed.
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
