package database;

import android.provider.BaseColumns;

/**
 * Created by nmrafiq on 13/12/17.
 */

public final class MoviePersistentContract {
        MoviePersistentContract(){}

        public static class MovieEntry implements BaseColumns{
            public static final String FAVOURITE_MOVIE_TABLE = "movie_favourite";
            public static final String MOVIE_NAME = "movie_name";
            public static final String MOVIE_ID = "movie_id";
            public static final String MOVIE_FAVOURITE = "is_favourite";
        }
}
