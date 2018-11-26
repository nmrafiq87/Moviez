package in.appcrew.moviez.moviedetail.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import in.appcrew.moviez.R;
import in.appcrew.moviez.database.movie.MovieEntity;
import in.appcrew.moviez.entity.MovieData;
import in.appcrew.moviez.databinding.FragmentMovieDetailBinding;
import in.appcrew.moviez.moviedetail.viewmodel.MovieDetailViewModel;
import in.appcrew.moviez.repository.MovieRepository;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private MovieDetailViewModel mMovieDetailViewModel;
    public ObservableField<MovieData> movieDetail = new ObservableField<>();
    public ObservableField<Integer> movieFavourite = new ObservableField<>();
    public ObservableField<MovieEntity> movieLocal = new ObservableField<>();
    public ObservableArrayList<String> arrayDescList = new ObservableArrayList<>();
    public ObservableArrayList<String> arrayTitleList = new ObservableArrayList<>();
    private String movieId;
    private FragmentMovieDetailBinding fragmentMovieDetailBinding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMovieDetailBinding = FragmentMovieDetailBinding.inflate(inflater,container,false);
        return fragmentMovieDetailBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMovieDetailViewModel = ViewModelProviders.of(MovieDetailFragment.this).get(MovieDetailViewModel.class);
        mMovieDetailViewModel.setMovieRepository(new MovieRepository(getActivity()));
        fragmentMovieDetailBinding.setViewmodel(mMovieDetailViewModel);
        fragmentMovieDetailBinding.setView(this);
        initTitleList();
        setUpAdapter();
    }

    public static MovieDetailFragment newInstance() {
        return  new MovieDetailFragment();
    }

    public void setMovieId(@NonNull String movieId){
        this.movieId = movieId;
    }

    private void initTitleList() {
        arrayTitleList.add(getResources().getString(R.string.description));
        arrayTitleList.add(getResources().getString(R.string.rating));
        arrayTitleList.add(getResources().getString(R.string.rating_count));
        arrayTitleList.add(getResources().getString(R.string.genre));
        arrayTitleList.add(getResources().getString(R.string.spoken_language));
    }

    @Override
    public void onStart() {
        super.onStart();
        mMovieDetailViewModel.start(movieId);
        mMovieDetailViewModel.movieLiveData.observe(this, movieData -> {
            if (movieData != null){
                movieDetail.set(movieData);
                movieFavourite.set(movieData.getLove());
            }
        });

        mMovieDetailViewModel.movieEntityLiveData.observe(this, favouriteData -> {
            if (favouriteData != null){
                this.movieLocal.set(favouriteData);
            }
        }) ;
        mMovieDetailViewModel.mDescList.observe(this, arrDetail -> {
            if (arrDetail != null){
                arrayDescList.clear();
                arrayDescList.addAll(arrDetail);
            }
        });
    }

    public void setUpAdapter(){
        RecyclerView recyclerView =  fragmentMovieDetailBinding.movieList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MovieDetailAdapter movieAdapter = new MovieDetailAdapter();
        movieAdapter.setHasStableIds(true);
        recyclerView.setAdapter(movieAdapter);
    }
}
