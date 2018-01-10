package in.appcrew.moviez.moviedetail;


import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import data.source.MovieContentProvider;
import data.source.MovieDbHelper;
import data.source.MoviePersistentContract;
import in.appcrew.moviez.databinding.FragmentMovieDetailBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private MovieDetailViewModel mMovieDetailViewModel;
    private String movieId;
    private FragmentMovieDetailBinding fragmentMovieDetailBinding;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public static MovieDetailFragment newInstance() {
        MovieDetailFragment fragment = new MovieDetailFragment();
        return fragment;
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mMovieDetailViewModel.start(movieId);

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
        setUpAdapter();
    }

    public void setUpAdapter(){
        RecyclerView recyclerView =  fragmentMovieDetailBinding.movieList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MovieDetailAdapter movieAdapter = new MovieDetailAdapter(getActivity());
        movieAdapter.setHasStableIds(true);
        recyclerView.setAdapter(movieAdapter);
        fragmentMovieDetailBinding.btnLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Love Click","Love Click");
            }
        });
    }
}
