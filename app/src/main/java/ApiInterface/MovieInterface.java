package ApiInterface;

import data.MovieData;
import data.Movies;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by nmrafiq on 27/10/17.
 */

public interface MovieInterface {
    @GET("discover/movie")
    Call<Movies> getMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/{movie_id}")
    Call<MovieData> getMovie(@Path("movie_id") String movieId, @Query("api_key") String apiKey);
}
