package in.appcrew.moviez.moviedetail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Movie;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import data.Genre;
import data.MovieData;
import data.SpokenLanguage;
import data.source.MovieRepository;

/**
 * Created by nmrafiq on 30/11/17.
 */

public class MovieDetailViewModel extends ViewModel {
    public MutableLiveData<MovieData> mMovie = new MutableLiveData<>();
    public MediatorLiveData<MovieData> mMovieDataLiveData = new MediatorLiveData<MovieData>();
    public ObservableField<String> mTitle = new ObservableField<>();
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
    }

    public void start(String movieId){
        loadMovies(movieId);
    }

    // Load movie from remote repository
    public void loadMovies(final String movieId){
        mMovieRepository.getMovieDetailRemote(movieId);
        mMovieRepository.getMovie(movieId);
        mMovieDataLiveData.addSource(mMovieRepository.getMovieLocalLiveData(), new Observer<MovieData>() {
            @Override
            public void onChanged(@Nullable MovieData movieData) {
                int love = movieData.getLove();
                Log.d("Love Local ", "Love Local" + love);
                MovieData movie = mMovieDataLiveData.getValue() != null ? mMovieDataLiveData.getValue() : movieData;
                mMovieDataLiveData.setValue(movie);
            }
        });

        mMovieDataLiveData.addSource(mMovieRepository.getMovieRemoteLiveData(), new Observer<MovieData>() {
            @Override
            public void onChanged(@Nullable MovieData movieData) {
                int love = mMovieDataLiveData.getValue() != null ? mMovieDataLiveData.getValue().getLove() : 0;
                Log.d("Love Remote ", "Love Remote" + love);
                movieData.setLove(love);
                mMovieDataLiveData.setValue(movieData);
            }
        });
    }

    private void setMovieDetails() {
        mTitle.set(mMovie.getValue().getTitle());
        mMovieId.set(mMovie.getValue().getId());
        mBackdropImage.set(mMovie.getValue().getBackdropPath());
        mDescList.addAll(getDescList(mMovie.getValue()));
    }

    public void loveClicked(){
       saveMovie();
    }

    public void saveMovie(){
        if (mMovieDataLiveData.getValue() != null && !TextUtils.isEmpty(mMovieDataLiveData.getValue().getId())){
            Log.d("Before Love Update", "Love Update" + mMovieDataLiveData.getValue().getLove());
            mMovieRepository.updateMovie(mMovieDataLiveData.getValue());
        } else {
            Log.d("Before Love Insert", "Love Insert" + mMovieDataLiveData.getValue().getLove());
            mMovieRepository.insertMovie(mMovieDataLiveData.getValue());
        }
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
