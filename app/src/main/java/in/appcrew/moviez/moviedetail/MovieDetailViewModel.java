package in.appcrew.moviez.moviedetail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayList;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import entity.Genre;
import entity.MovieData;
import entity.SpokenLanguage;
import repository.MovieRepository;

/**
 * Created by nmrafiq on 30/11/17.
 */

public class MovieDetailViewModel extends ViewModel {
    public LiveData<MovieData> movieLiveData;
    public LiveData<MovieData> movieLocalLiveData;
    public LiveData<ArrayList<String>> mDescList;
    public ObservableArrayList<String> mTitleList = new ObservableArrayList<>();
    private MovieRepository mMovieRepository;


    public MovieDetailViewModel(MovieRepository movieRepository){
        this.mMovieRepository = movieRepository;
    }

    public void start(String movieId){
        loadMovies(movieId);
    }

    // Load movie from remote repository
    public void loadMovies(final String movieId){
        mMovieRepository.getMovieDetailRemote(movieId);
        mMovieRepository.getMovie(movieId);
        movieLocalLiveData = mMovieRepository.getMovieLocalLiveData();
        movieLiveData = mMovieRepository.getMovieRemoteLiveData();
        mDescList = Transformations.map(movieLiveData, movieData -> getDescList(movieData));
    }

    public void loveClicked(){
       saveMovie();
    }

    public void saveMovie(){
        if (movieLocalLiveData.getValue() != null && !TextUtils.isEmpty(movieLocalLiveData.getValue().getId())){
            mMovieRepository.updateMovie(movieLocalLiveData.getValue());
        } else {
            mMovieRepository.insertMovie(movieLiveData.getValue());
        }
    }

    private ArrayList<String> getDescList(MovieData movieData){
        ArrayList<String> arrList = new ArrayList<>();
        arrList.add(movieData.getOverview());
        arrList.add(String.valueOf(movieData.getVoteAverage()));
        arrList.add(String.valueOf(movieData.getVoteCount()));
        arrList.add(getGenre(movieData.getGenres()));
        arrList.add(getSpokenLanguages(movieData.getSpokenLanguages()));
        return arrList;
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
