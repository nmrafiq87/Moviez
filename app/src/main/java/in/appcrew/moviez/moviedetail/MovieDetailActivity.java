package in.appcrew.moviez.moviedetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import data.source.MovieLocalRepository;
import data.source.MovieRemoteRepository;
import in.appcrew.moviez.R;
import in.appcrew.moviez.ViewModelHolder;
import in.appcrew.moviez.movie.MovieActivity;
import in.appcrew.moviez.utils.ActivityUtils;

public class MovieDetailActivity extends AppCompatActivity {


    private MovieDetailFragment mMovieDetailFragment;
    private MovieDetailViewModel mMovieDetailViewModel;
    public static final String TAG = "MOVIE_DETAIL_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        mMovieDetailFragment = findOrCreateFragment();
        mMovieDetailViewModel = findOrCreateViewModel();
        mMovieDetailFragment.setDetailViewModel(mMovieDetailViewModel);
        mMovieDetailFragment.setMovieId(getIntent().getStringExtra(MovieActivity.MOVIE_ID));
    }

    private MovieDetailFragment findOrCreateFragment(){
        MovieDetailFragment movieDetailFragment = (MovieDetailFragment)getSupportFragmentManager().findFragmentById(R.id.detail_content);
        if (movieDetailFragment == null){
            movieDetailFragment = new MovieDetailFragment().newInstance();
        }
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),movieDetailFragment,R.id.detail_content);
        return movieDetailFragment;
    }

    private MovieDetailViewModel findOrCreateViewModel(){
        ViewModelHolder<MovieDetailViewModel> retainedViewModel = (ViewModelHolder<MovieDetailViewModel>)getSupportFragmentManager().findFragmentByTag(TAG);
        if (retainedViewModel != null && retainedViewModel.getViewmodel() != null){
            return retainedViewModel.getViewmodel();
        }else{
            MovieDetailViewModel movieDetailViewModel = new MovieDetailViewModel(new MovieRemoteRepository(), new MovieLocalRepository(),getApplicationContext());
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    retainedViewModel.createContainer(movieDetailViewModel),
                    TAG);
            return movieDetailViewModel;
        }
    }


}
