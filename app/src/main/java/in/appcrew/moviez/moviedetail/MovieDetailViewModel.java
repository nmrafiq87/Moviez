package in.appcrew.moviez.moviedetail;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.net.Uri;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.net.URI;
import java.util.List;

import data.Genre;
import data.MovieData;
import data.SpokenLanguage;
import data.source.MovieContentProvider;
import data.source.MovieDataSource;
import data.source.MovieLocalRepository;
import data.source.MoviePersistentContract;
import data.source.MovieRemoteRepository;
import data.source.MovieRepository;
import in.appcrew.moviez.movie.MovieItemNavigator;

/**
 * Created by nmrafiq on 30/11/17.
 */

public class MovieDetailViewModel extends BaseObservable {
    public ObservableField<MovieData> mMovie = new ObservableField<>();
    public ObservableField<String> mTitle = new ObservableField<>();
    public ObservableField<String> mVoteAverage = new ObservableField<>();
    public ObservableField<String> mBackdropImage = new ObservableField<>();
    public ObservableArrayList<String> mTitleList = new ObservableArrayList<>();
    public ObservableArrayList<String> mDescList = new ObservableArrayList<>();
    public ObservableField<String> mMovieId = new ObservableField<>();
    public ObservableInt mLove = new ObservableInt();
    private MovieRepository mMovieRepository;
    private Context mContext;


    public MovieDetailViewModel(MovieRepository movieRepository, Context context){
        this.mMovieRepository = movieRepository;
        this.mContext = context;
        this.mTitleList = getTitleList();
        mLove.set(0);
        mMovie.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                MovieData movie = mMovie.get();

                if (movie != null) {
                    mTitle.set(movie.getTitle());
                    mMovieId.set(movie.getId());
                    mBackdropImage.set(movie.getBackdropPath());
                    mDescList.addAll(getDescList(movie));
                }
            }
        });
    }

    public void start(String movieId){
        loadMovies(movieId);
    }

    // Load movie from remote repository
    public void loadMovies(final String movieId){
        mMovieRepository.getMovie(mContext, movieId, new MovieDataSource.GetMovieCallback() {
            @Override
            public void onMovieLoaded(MovieData movie) {
                mMovie.set(movie);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

//     On clicking the love button verify whether the data exists or not, on basis of which
//        either an insert or update is made
    public void loveClicked(){
        if (mMovieId.get() == null){
            return;
        }
       saveMovie();
    }


    public void saveMovie(){
        mMovieRepository.insertMovie(mContext, mMovie.get(), new MovieDataSource.UpdateMovieCallback() {
            @Override
            public void onMovieUpdated(MovieData movie) {
                mMovie.get().setLove(movie.getLove());
                mLove.set(movie.getLove());
            }
        });
    }

    public ObservableArrayList<String> getTitleList(){
        ObservableArrayList<String> titleList = new ObservableArrayList<>();
        titleList.add("Description");
        titleList.add("Rating");
        titleList.add("Rating Count");
        titleList.add("Genre");
        titleList.add("Spoken Language");
        return titleList;
    }

    public ObservableArrayList<String> getDescList(MovieData movieData){
        ObservableArrayList<String> descList = new ObservableArrayList<>();
        descList.add(movieData.getOverview());
        descList.add(String.valueOf(movieData.getVoteAverage()));
        descList.add(String.valueOf(movieData.getVoteCount()));
        descList.add(getGenre(movieData.getGenres()));
        descList.add(getSpokenLanguages(movieData.getSpokenLanguages()));
        return descList;
    }


    public String getGenre(List<Genre> genreList){
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<genreList.size();i++){
            sb.append(genreList.get(i).getName());
            sb.append(", ");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }

    public String getSpokenLanguages(List<SpokenLanguage> spokenList){
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<spokenList.size();i++){
            sb.append(spokenList.get(i).getName());
            sb.append(", ");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }

}
