package in.appcrew.moviez.movie;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.Toast;

import data.Result;
import data.source.MovieRepository;

/**
 * Created by nmrafiq on 16/11/17.
 */

public class MovieViewModel extends BaseObservable {

    public final ObservableField<String> snackbarText = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> description = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();
    private final ObservableField<Result> mMovie = new ObservableField<>();
    private final MovieRepository mMovieRepository;
    private final Context mContext;
    private boolean mIsDataLoading;

    public MovieViewModel(Context context, MovieRepository tasksRepository) {
        mContext = context.getApplicationContext(); // Force use of Application Context.
        mMovieRepository = tasksRepository;
        // Exposed observables depend on the mTaskObservable observable:
        mMovie.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                Result movie = mMovie.get();
                if (movie != null) {
                    title.set(movie.getTitle());
                }
            }
        });
    }


    public void setMovieList(Result movie){
        mMovie.set(movie);
    }

    public View.OnClickListener onReadMoreClicked() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Opens article detail", Toast.LENGTH_SHORT).show();
//                setRead(true);
            }
        };
    }




}
