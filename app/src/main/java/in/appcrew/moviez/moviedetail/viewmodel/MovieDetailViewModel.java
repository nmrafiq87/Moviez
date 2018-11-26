package in.appcrew.moviez.moviedetail.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayList;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import in.appcrew.moviez.database.movie.MovieEntity;
import in.appcrew.moviez.entity.Genre;
import in.appcrew.moviez.entity.MovieData;
import in.appcrew.moviez.entity.SpokenLanguage;
import in.appcrew.moviez.repository.MovieRepository;

/**
 * Created by nmrafiq on 30/11/17.
 */

public class MovieDetailViewModel extends ViewModel {
    public LiveData<MovieEntity> movieEntityLiveData;
    public LiveData<MovieData> movieLiveData;
    public LiveData<ArrayList<String>> mDescList;
    public ObservableArrayList<String> mTitleList = new ObservableArrayList<>();
    private MovieRepository mMovieRepository;


    public MovieDetailViewModel(MovieRepository movieRepository){
        this.mMovieRepository = movieRepository;
    }

    public void start(String movieId){
        loadMovies(movieId);
    }

    // Load movie from remote in.appcrew.moviez.repository
    public void loadMovies(final String movieId){
        mMovieRepository.getMovieDetailRemote(movieId);
        movieLiveData = mMovieRepository.getMovieDetailLiveData();
        movieEntityLiveData = mMovieRepository.getMovieFromRoom(movieId);
        mDescList = Transformations.map(movieLiveData, movieData -> getDescList(movieData));
    }

    public void loveClicked(){
        if (movieLiveData.getValue() != null){
            saveMovie(movieLiveData.getValue().getId());
        }
    }

    public void saveMovie(String movieId){
        int favourite = 0;
        if (movieLiveData.getValue() != null){
            if (movieEntityLiveData.getValue() != null){
                favourite = movieEntityLiveData.getValue().isFavourite;
            }
            MovieEntity movieEntity = new MovieEntity();
            movieEntity.movieId = movieId;
            movieEntity.isFavourite = favourite == 0 ? 1 : 0;
            mMovieRepository.insertMovieRoom(movieEntity);
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
