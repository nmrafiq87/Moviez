package database.movie;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import entity.MovieData;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieEntity movieEntity);

    @Query("SELECT * FROM movie_favourite WHERE movie_id = :movieId")
    public LiveData<MovieEntity> loadMovie(int movieId);
}
