package in.appcrew.moviez.movie.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

import java.lang.ref.WeakReference;

import in.appcrew.moviez.entity.Result;
import in.appcrew.moviez.movie.ui.MovieItemNavigator;

/**
 * Created by nmrafiq on 16/11/17.
 */

public class MovieItemViewModel extends ViewModel {

    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> id = new ObservableField<>();
    private final ObservableField<Result> mMovie = new ObservableField<>();
    private WeakReference<MovieItemNavigator> mNavigator;


    public void setNavigator(MovieItemNavigator movieItemNavigator){
        mNavigator = new WeakReference<>(movieItemNavigator);
    }

    public void taskClicked() {
        if (id == null) {
            // Click happened before task was loaded, no-op.
            return;
        }
        if (mNavigator != null && mNavigator.get() != null) {
            mNavigator.get().onItemClick(String.valueOf(id.get()));
        }
    }


    public void setMovieList(Result movie) {
        mMovie.set(movie);
        id.set(movie.getId());
        title.set(movie.getOriginalTitle());
    }
}
