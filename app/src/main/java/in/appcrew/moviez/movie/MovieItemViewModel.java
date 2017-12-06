package in.appcrew.moviez.movie;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableField;

import java.lang.ref.WeakReference;

import data.Result;
import data.source.MovieRepository;

/**
 * Created by nmrafiq on 16/11/17.
 */

public class MovieItemViewModel extends BaseObservable {

    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> id = new ObservableField<>();
    private final ObservableField<Result> mMovie = new ObservableField<>();
    private WeakReference<MovieItemNavigator> mNavigator;

    public MovieItemViewModel(Context context, MovieRepository tasksRepository) {
        mMovie.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                Result movie = mMovie.get();
                if (movie != null) {
                    title.set(movie.getTitle());
                    id.set(String.valueOf(movie.getId()));
                }
            }
        });
    }

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
    }
}
