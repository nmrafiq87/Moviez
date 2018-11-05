package in.appcrew.moviez;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;

import in.appcrew.moviez.movie.MovieActivity;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nmrafiq on 03/11/17.
 */

public class MovieApplication extends Application {
    private  static Retrofit RETROFIT_INSTANCE = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    synchronized
    public static Retrofit getRetroFit(){
        if (RETROFIT_INSTANCE == null){
            RETROFIT_INSTANCE = new Retrofit.Builder()
                    .baseUrl(MovieActivity.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return RETROFIT_INSTANCE;
    }
}
