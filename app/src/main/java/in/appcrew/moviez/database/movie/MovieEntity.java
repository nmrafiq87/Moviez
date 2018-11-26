package in.appcrew.moviez.database.movie;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity (tableName = "movie_favourite")

public class MovieEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movie_id")
    public String movieId;

    @ColumnInfo(name = "movie_name")
    public String movieName;

    @ColumnInfo(name = "is_favourite")
    public Integer isFavourite;


}
