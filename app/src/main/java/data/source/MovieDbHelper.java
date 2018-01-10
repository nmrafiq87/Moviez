package data.source;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nmrafiq on 13/12/17.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String MOVIE_DB = "Movie.db";
    private static final int MOVIE_DB_VERSION = 1;


    private static final String TEXT_TYPE = " TEXT";
    private static final String BOOLEAN_TYPE = " INTEGER";
    private static final String PRIMARY_KEY = " PRIMARY KEY";
    private static final String COMMA_SEP = ",";


    private static final String SQL_CREATE_FAVOURITE_MOVIE =
            "CREATE TABLE " + MoviePersistentContract.MovieEntry.FAVOURITE_MOVIE_TABLE + "(" +
                    MoviePersistentContract.MovieEntry.MOVIE_ID + TEXT_TYPE + PRIMARY_KEY + COMMA_SEP +
                    MoviePersistentContract.MovieEntry.MOVIE_NAME + TEXT_TYPE + COMMA_SEP +
                    MoviePersistentContract.MovieEntry.MOVIE_FAVOURITE + BOOLEAN_TYPE +
                    " )";

    MovieDbHelper(Context context){
        super(context,MOVIE_DB,null,MOVIE_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FAVOURITE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
