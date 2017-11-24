package in.appcrew.moviez;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by nmrafiq on 03/11/17.
 */

public class MovieApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
