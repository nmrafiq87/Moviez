package database.movie;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity (tableName = "movie_favourite")

public class MovieEntity {

    @PrimaryKey
    @NonNull
    private String movie_id;

    private String movie_name;

    private Boolean is_favourite;


    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(@NonNull String movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public Boolean getIs_favourite() {
        return is_favourite;
    }

    public void setIs_favourite(Boolean is_favourite) {
        this.is_favourite = is_favourite;
    }
}
