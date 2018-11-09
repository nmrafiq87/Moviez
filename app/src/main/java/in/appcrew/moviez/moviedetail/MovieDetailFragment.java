package in.appcrew.moviez.moviedetail;

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
import data.MovieData;
import in.appcrew.moviez.databinding.FragmentMovieDetailBinding;

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
    private String movieId;
    private FragmentMovieDetailBinding fragmentMovieDetailBinding;

    public static MovieDetailFragment newInstance() {
        return  new MovieDetailFragment();
    }


    public void setDetailViewModel(MovieDetailViewModel mMovieDetailViewModel){
        this.mMovieDetailViewModel = mMovieDetailViewModel;
    }

    public void setMovieId(@NonNull String movieId){
        this.movieId = movieId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();
        mMovieDetailViewModel.start(movieId);
        mMovieDetailViewModel.movieLiveData.observe(this, movieData -> {
            if (movieData != null){
                movieDetail.set(movieData);
            }
        });
        mMovieDetailViewModel.movieLocalLiveData.observe(this, favouriteData -> {
            if (favouriteData != null){
                this.movieFavourite.set(favouriteData.getLove());
            }
        }) ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMovieDetailBinding = FragmentMovieDetailBinding.inflate(inflater,container,false);
        fragmentMovieDetailBinding.setView(this);
        fragmentMovieDetailBinding.setViewmodel(mMovieDetailViewModel);
        return fragmentMovieDetailBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        setUpAdapter();
    }


    public void setUpAdapter(){
        RecyclerView recyclerView =  fragmentMovieDetailBinding.movieList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MovieDetailAdapter movieAdapter = new MovieDetailAdapter(getActivity());
        movieAdapter.setHasStableIds(true);
        recyclerView.setAdapter(movieAdapter);
    }
}
