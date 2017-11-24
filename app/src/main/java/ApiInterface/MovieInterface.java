package ApiInterface;

import data.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by nmrafiq on 27/10/17.
 */

public interface MovieInterface {
    @GET("discover/movie")
    Call<Movie> getMovie(@Query("api_key") String apiKey);
}
