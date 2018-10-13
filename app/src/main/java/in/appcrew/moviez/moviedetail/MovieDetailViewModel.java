package in.appcrew.moviez.moviedetail;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

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
    }

    public void start(String movieId){
        loadMovies(movieId);
    }

    // Load movie from remote repository
    public void loadMovies(final String movieId){
//        mMovieRepository.getMovie(mContext, movieId, new MovieDataSource.GetMovieCallback() {
//            @Override
//            public void onMovieLoaded(MovieData movie) {
//                mMovie.setValue(movie);
//                setMovieDetails();
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//
//            }
//        });
    }

    private void setMovieDetails(){
        mTitle.set(mMovie.getValue().getTitle());
        mMovieId.set(mMovie.getValue().getId());
        mBackdropImage.set(mMovie.getValue().getBackdropPath());
        mDescList.addAll(getDescList(mMovie.getValue()));
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
//        mMovieRepository.insertMovie(mContext, mMovie.getValue(), new MovieDataSource.UpdateMovieCallback() {
//            @Override
//            public void onMovieUpdated(MovieData movie) {
//                mMovie.getValue().setLove(movie.getLove());
//                mLove.set(movie.getLove());
//            }
//        });
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
