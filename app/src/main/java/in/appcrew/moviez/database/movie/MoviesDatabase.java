package in.appcrew.moviez.database.movie;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {MovieEntity.class}, version = 1)
public abstract class MoviesDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

    private static volatile MoviesDatabase INSTANCE;

    public static MoviesDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MoviesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MoviesDatabase.class, "Movie")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
