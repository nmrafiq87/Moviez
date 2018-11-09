package in.appcrew.moviez.movie.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import entity.MoviesUiState;
import entity.Result;
import repository.MovieRepository;
import in.appcrew.moviez.databinding.FragmentMovieBinding;
import in.appcrew.moviez.movie.viewmodel.MoviesViewModel;


public class MovieFragment extends Fragment  {
    private MoviesViewModel moviesViewModel;
    private static final int visibleThreshold = 8;
    private FragmentMovieBinding mMovieFragBinding;
    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private boolean isLoading;

    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        moviesViewModel.loadTasks(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMovieFragBinding = FragmentMovieBinding.inflate(inflater, container, false);
        return mMovieFragBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        moviesViewModel = ViewModelProviders.of(getActivity()).get(MoviesViewModel.class);
        moviesViewModel.setMovieRepository(new MovieRepository(getActivity()));
        mMovieFragBinding.setViewmodel(moviesViewModel);
        mMovieFragBinding.setView(this);
        setupListAdapter();

        final Observer<ArrayList<Result>> movieListObserver =  results -> {
            if (movieAdapter != null && results != null){
                movieAdapter.replaceData(results);
            }
        };

        final Observer<MoviesUiState> moviesUiStateObserver = uiState -> {
            if (uiState != null){
                isLoading = uiState.getLoading();
                mMovieFragBinding.progressBar.setVisibility(uiState.isShowProgress() ? View.VISIBLE : View.GONE);
            }
        };


        moviesViewModel.getMovies().observe(this,movieListObserver);
        moviesViewModel.movieStateLiveData.observe(this,moviesUiStateObserver);
    }

    private void setupListAdapter() {
        recyclerView =  mMovieFragBinding.movieRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        movieAdapter = new MovieAdapter(new ArrayList<>(),getContext(),(MovieActivity)getActivity());
        movieAdapter.setHasStableIds(true);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = linearLayoutManager.getItemCount();
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                Log.d("Total Last Visible Item", totalItemCount + " " + lastVisibleItem);
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    moviesViewModel.loadTasks(false);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
