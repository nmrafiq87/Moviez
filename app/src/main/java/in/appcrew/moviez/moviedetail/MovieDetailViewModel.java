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
import in.appcrew.moviez.movie.MovieItemNavigator;

/**
 * Created by practo on 30/11/17.
 */

public class MovieDetailViewModel extends BaseObservable {
    public ObservableField<MovieData> mMovie = new ObservableField<>();
    public ObservableField<String> mTitle = new ObservableField<>();
    public ObservableField<String> mVoteAverage = new ObservableField<>();
    public ObservableField<String> mBackdropImage = new ObservableField<>();
    public ObservableArrayList<String> mTitleList = new ObservableArrayList<>();
    public ObservableArrayList<String> mDescList = new ObservableArrayList<>();
    public ObservableInt mLove = new ObservableInt();
    private MovieRemoteRepository mMovieRepository;
    private MovieLocalRepository mMovieLocalRepository;
    private Context mContext;


    MovieDetailViewModel(MovieRemoteRepository movieRepository, MovieLocalRepository mMovieLocalRepository, Context context){
        this.mMovieRepository = movieRepository;
        this.mMovieLocalRepository = mMovieLocalRepository;
        this.mContext = context;
        this.mTitleList = getTitleList();
        mLove.set(0);
        mMovie.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                MovieData movie = mMovie.get();

                if (movie != null) {
                    mTitle.set(movie.getTitle());
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
    private void loadMovies(final String movieId){
        mMovieRepository = new MovieRemoteRepository();
        mMovieRepository.getMovie(mContext, movieId, new MovieDataSource.GetMovieCallback() {
            @Override
            public void onMovieLoaded(MovieData movie) {
                mMovie.set(movie);
                // Once successfully loaded use query the DB to populate whether the movie is favourite or not
                loadMovieFromLocalRepository();
            }

            @Override
            public void onDataNotAvailable() {
                Log.d("Movie Title","Movie Error");
            }
        });
    }

//     On clicking the love button verify whether the data exists or not, on basis of which
//        either an insert or update is made
    public void loveClicked(){
        mMovieLocalRepository.getMovie(mContext, mMovie.get().getId(), new MovieDataSource.GetMovieCallback() {
            @Override
            public void onMovieLoaded(MovieData movie) {
                updateMovie();
            }

            @Override
            public void onDataNotAvailable() {
                saveMovie();
            }
        });
    }

    private void loadMovieFromLocalRepository(){
        mMovieLocalRepository.getMovie(mContext, mMovie.get().getId(), new MovieDataSource.GetMovieCallback() {
            @Override
            public void onMovieLoaded(MovieData movie) {
                mMovie.get().setLove(movie.getLove());
                if (movie.getLove() != null){
                    mLove.set(movie.getLove());
                }
            }
            @Override
            public void onDataNotAvailable() {

            }
        });

    }


    private void saveMovie(){
        mMovieLocalRepository.insertMovie(mContext, mMovie.get(), new MovieDataSource.UpdateMovieCallback() {
            @Override
            public void onMovieUpdated(MovieData movie) {
                mMovie.get().setLove(movie.getLove());
                mLove.set(movie.getLove());
            }
        });
    }

    private void updateMovie(){
        mMovieLocalRepository.updateMovie(mContext, mMovie.get(), new MovieDataSource.UpdateMovieCallback() {
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
