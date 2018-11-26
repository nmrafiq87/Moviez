package in.appcrew.moviez;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import in.appcrew.moviez.entity.Genre;
import in.appcrew.moviez.entity.MovieData;
import in.appcrew.moviez.entity.SpokenLanguage;
import in.appcrew.moviez.moviedetail.viewmodel.MovieDetailViewModel;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by nmrafiq on 19/01/18.
 */

public class MovieDetailViewModelTest {
    private MovieData MOVIE;
    private MovieData EMPTY_MOVIE;
    private ArrayList<Genre> GENRE_LIST;
    private ArrayList<SpokenLanguage> SPOKEN_LANGUAGE;
    private ArrayList<String> DESC_LIST;

    @Mock
    private MovieLocalRepository movieLocalRepository;

    @Mock
    MovieRemoteRepository movieRemoteRepository;

    @Mock
    private Context context;

    private MovieDetailViewModel movieDetailViewModel;

    @Captor
    private ArgumentCaptor<MovieDataSource.GetMovieCallback> movieRemoteCallbackCaptor;

    @Captor
    private ArgumentCaptor<MovieDataSource.GetMovieCallback> movieLocalCallbackCaptor;

    @Captor
    private ArgumentCaptor<MovieDataSource.UpdateMovieCallback> movieUpdateCallbackCaptor;


    @Before
    public void setUpViewModel(){
        MockitoAnnotations.initMocks(this);

        setUpContext();
        // Get a reference to the class under test
        movieDetailViewModel = new MovieDetailViewModel(movieRemoteRepository, movieLocalRepository, context);

        MOVIE = new MovieData();
        MOVIE.setLove(0);
        MOVIE.setId("1234");
        MOVIE.setTitle("title");
        MOVIE.setOverview("Overview");
        MOVIE.setBackdropPath("Test");
        MOVIE.setVoteAverage(7.89);
        MOVIE.setVoteCount(1000);
        MOVIE.setLove(1);

        GENRE_LIST = new ArrayList<>();
        GENRE_LIST.add(new Genre(123,"Horror"));
        GENRE_LIST.add(new Genre(234,"Comedy"));

        SPOKEN_LANGUAGE = new ArrayList<>();
        SPOKEN_LANGUAGE.add(new SpokenLanguage("abc", "English"));
        SPOKEN_LANGUAGE.add(new SpokenLanguage("def","Hindi"));

        MOVIE.setGenres(GENRE_LIST);
        MOVIE.setSpokenLanguages(SPOKEN_LANGUAGE);

        movieDetailViewModel.mLove.set(0);
        movieDetailViewModel.mMovie.set(MOVIE);

        getDescriptionList();
    }

    @Test
    public void validateOnPropertyChange(){
        movieDetailViewModel.mMovie.set(MOVIE);
        Assert.assertEquals(MOVIE.getTitle(),movieDetailViewModel.mTitle.get());
        Assert.assertEquals(MOVIE.getBackdropPath(),movieDetailViewModel.mBackdropImage.get());
        Assert.assertTrue(DESC_LIST.size() == movieDetailViewModel.getDescList(MOVIE).size());
        Assert.assertTrue(DESC_LIST.size() == movieDetailViewModel.getTitleList().size());
    }

    @Test
    public void getDescriptionList(){
        DESC_LIST = new ArrayList<>();
        DESC_LIST.add(MOVIE.getTitle());
        DESC_LIST.add(String.valueOf(MOVIE.getVoteAverage()));
        DESC_LIST.add(String.valueOf(MOVIE.getVoteCount()));
        DESC_LIST.add(movieDetailViewModel.getGenre(GENRE_LIST));
        DESC_LIST.add(movieDetailViewModel.getSpokenLanguages(SPOKEN_LANGUAGE));
    }

    @Test
    public void validateOnPropertyChangedWhenNull(){
        movieDetailViewModel.mMovie.set(EMPTY_MOVIE);
    }



    private void setUpContext(){
        when(context.getApplicationContext()).thenReturn(context);
    }

    @Test
    public void onStart(){
        movieDetailViewModel.start(MOVIE.getId());
    }

    @Test
    public void loadFromRemote(){
        movieDetailViewModel.loadMovies(MOVIE.getId());
        verify(movieRemoteRepository).getMovie(any(Context.class),any(String.class),movieRemoteCallbackCaptor.capture());
        movieRemoteCallbackCaptor.getValue().onMovieLoaded(MOVIE);
        movieDetailViewModel.mMovie.set(MOVIE);
        movieDetailViewModel.loadMovieFromLocalRepository();
        movieRemoteCallbackCaptor.getValue().onDataNotAvailable();
    }

    @Test
    public void favouriteClick(){
        movieDetailViewModel.loveClicked();
        verify(movieLocalRepository).getMovie(any(Context.class),any(String.class),movieLocalCallbackCaptor.capture());
        movieLocalCallbackCaptor.getValue().onMovieLoaded(MOVIE);
        verify(movieLocalRepository).updateMovie(any(Context.class),any(MovieData.class),movieUpdateCallbackCaptor.capture());
        movieUpdateCallbackCaptor.getValue().onMovieUpdated(MOVIE);
        checkLove();
        movieLocalCallbackCaptor.getValue().onDataNotAvailable();
        verify(movieLocalRepository).insertMovie(any(Context.class),any(MovieData.class),movieUpdateCallbackCaptor.capture());
        movieUpdateCallbackCaptor.getValue().onMovieUpdated(MOVIE);
        checkLove();

    }

    private void checkLove(){
        Assert.assertTrue(MOVIE.getLove() == movieDetailViewModel.mMovie.get().getLove());
        Assert.assertTrue(movieDetailViewModel.mMovie.get().getLove() == movieDetailViewModel.mLove.get());
    }

}
