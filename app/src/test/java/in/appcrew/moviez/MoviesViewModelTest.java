package in.appcrew.moviez;

import android.content.Context;
import android.content.res.Resources;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import data.Result;
import data.source.MovieDataSource;
import data.source.MovieRepository;
import in.appcrew.moviez.movie.MovieActivity;
import in.appcrew.moviez.movie.MoviesViewModel;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by nmrafiq on 24/11/17.
 */
public class MoviesViewModelTest {

    private static ArrayList<Result> MOVIES;
    private static ArrayList<Result> EMPTY_MOVIES;

    @Mock
    private MovieRepository mMoviesRepository;

    @Mock
    private Context mContext;

    @Mock
    private MovieActivity mMovieNavigator;

    @Captor
    private ArgumentCaptor<MovieDataSource.LoadMoviesCallback> mLoadTasksCallbackCaptor;

    private MoviesViewModel mMoviesViewModel;

    @Before
    public void setupTasksViewModel() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        setupContext();
        // Get a reference to the class under test
        mMoviesViewModel = new MoviesViewModel(mMoviesRepository, mContext);
        // We initialise the tasks to 3, with one active and two completed
        MOVIES = new ArrayList<>();
        MOVIES.add(new Result("Test","abc.jpg"));
        MOVIES.add(new Result("Test 1","def.jpg"));
    }

    private void setupContext() {
        when(mContext.getApplicationContext()).thenReturn(mContext);
        when(mContext.getResources()).thenReturn(mock(Resources.class));
    }

    @Test
    public void onStart(){
        mMoviesViewModel.start();
    }

    @Test
    public void loadAllTasksFromRepository_dataLoaded() {
        // Given an initialized TasksViewModel with initialized tasks
        // When loading of Tasks is requested
        // Callback is captured and invoked with stubbed tasks
        mMoviesViewModel.loadTasks(true);
        verify(mMoviesRepository).getMovies(mLoadTasksCallbackCaptor.capture());
        // Then progress indicator is shown
        Assert.assertTrue(mMoviesViewModel.dataLoading.get());
        mLoadTasksCallbackCaptor.getValue().onMoviesLoaded(MOVIES);

        // Then progress indicator is hidden
        Assert.assertFalse(mMoviesViewModel.dataLoading.get());

        // And data loaded
        Assert.assertFalse(mMoviesViewModel.item.isEmpty());
        Assert.assertTrue(mMoviesViewModel.item.size() == 2);

        mLoadTasksCallbackCaptor.getValue().onDataNotAvailable();
        Assert.assertFalse(mMoviesViewModel.dataLoading.get());
    }


}
