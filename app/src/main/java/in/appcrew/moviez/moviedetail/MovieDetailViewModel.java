package in.appcrew.moviez.moviedetail;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.util.Log;

import java.util.List;

import data.Genre;
import data.MovieData;
import data.SpokenLanguage;
import data.source.MovieDataSource;
import data.source.MovieRepository;

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
    private MovieRepository mMovieRepository;
    private Context mContext;


    MovieDetailViewModel(MovieRepository movieRepository, Context context){
        this.mMovieRepository = movieRepository;
        this.mContext = context;
        this.mTitleList = getTitleList();
        mMovie.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                MovieData movie = mMovie.get();

                if (movie != null) {
                    mTitle.set(movie.getTitle());
                    mBackdropImage.set(movie.getBackdropPath());
                    mDescription.set(movie.getOverview());
                    mVoteAverage.set(String.valueOf(movie.getVoteAverage()));
                    mDescList.addAll(getDescList(movie));
                }
            }
        });
    }


    public void start(String movieId){
        loadMovies(movieId);
    }



    private void loadMovies(final String movieId){
        mMovieRepository = new MovieRepository();
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
            sb.append(genreList.get(i).getName() + ", ");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }

    public String getSpokenLanguages(List<SpokenLanguage> spokenList){
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<spokenList.size();i++){
            sb.append(spokenList.get(i).getName() + ", ");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }

}
