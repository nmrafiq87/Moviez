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
import data.source.MoviePersistentContract;
import data.source.MovieRemoteRepository;
import in.appcrew.moviez.movie.MovieItemNavigator;

/**
 * Created by practo on 30/11/17.
 */

public class MovieDetailViewModel extends BaseObservable {
    private ObservableField<MovieData> mMovie = new ObservableField<>();
    public ObservableField<String> mTitle = new ObservableField<>();
    public ObservableField<String> mDescription = new ObservableField<>();
    public ObservableField<String> mVoteAverage = new ObservableField<>();
    public ObservableField<String> mBackdropImage = new ObservableField<>();
    public ObservableArrayList<String> mTitleList = new ObservableArrayList<>();
    public ObservableArrayList<String> mDescList = new ObservableArrayList<>();
    public ObservableInt mLove = new ObservableInt();
    private MovieRemoteRepository mMovieRepository;
    private Context mContext;


    MovieDetailViewModel(MovieRemoteRepository movieRepository, Context context){
        this.mMovieRepository = movieRepository;
        this.mContext = context;
        this.mTitleList = getTitleList();
        mLove.set(1);
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

    public void loveClicked(){
        favouriteMovie();
    }

    private void favouriteMovie(){
        Log.d("Click","Click done");
//        mContext.getContentResolver().query()
        String[] selectionArgs = {""};
        String selectionClause =  MoviePersistentContract.MovieEntry.MOVIE_ID + " = ?";
        selectionArgs[0] = mMovie.get().getId();
        Uri singleUri = ContentUris.withAppendedId(MovieContentProvider.CONTENT_URI,Long.valueOf(mMovie.get().getId()));
        Cursor cursor = mContext.getContentResolver().query(singleUri,null,selectionClause,selectionArgs,null);
        ContentValues cv = new ContentValues();
        Uri uri;
        cv.put(MoviePersistentContract.MovieEntry.MOVIE_ID,mMovie.get().getId());
        cv.put(MoviePersistentContract.MovieEntry.MOVIE_NAME,mMovie.get().getOriginalTitle());
        cv.put(MoviePersistentContract.MovieEntry.MOVIE_FAVOURITE,mLove.get());
        if (cursor.getCount() == 0){
            uri = mContext.getContentResolver().insert(MovieContentProvider.CONTENT_URI,cv);
            Log.d("Uri"," "+ uri.toString());
        }else{
            int affectedRows = mContext.getContentResolver().update(MovieContentProvider.CONTENT_URI,cv,selectionClause,selectionArgs);
            Log.d("Query ","Updated rows" + affectedRows);
        }


    }

    public void start(String movieId){
        loadMovies(movieId);
    }

    private void loadMovies(final String movieId){
        mMovieRepository = new MovieRemoteRepository();
        mMovieRepository.getMovie(movieId, new MovieDataSource.GetMovieCallback() {
            @Override
            public void onMovieLoaded(MovieData movie) {
                Log.d("Movie ", movie.getOriginalTitle());
                mMovie.set(movie);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d("Movie Title","Movie Error");
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
