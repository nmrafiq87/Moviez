package in.appcrew.moviez.movie;

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

import data.Result;
import data.source.MovieLocalRepository;
import data.source.MovieRemoteRepository;
import data.source.MovieRepository;
import in.appcrew.moviez.databinding.FragmentMovieBinding;


public class MovieFragment extends Fragment  {
    private MoviesViewModel moviesViewModel;
    private int visibleThreshold = 8;
    private int lastVisibleItem, totalItemCount;
    private FragmentMovieBinding mMovieFragBinding;
    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;

    public static MovieFragment newInstance() {
        MovieFragment fragment = new MovieFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        moviesViewModel.loadTasks(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMovieFragBinding = FragmentMovieBinding.inflate(inflater, container, false);
        View root = mMovieFragBinding.getRoot();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        moviesViewModel.setMovieRepository(new MovieRepository(new MovieLocalRepository(), new MovieRemoteRepository()));
        mMovieFragBinding.setViewmodel(moviesViewModel);
        mMovieFragBinding.setView(this);
        setupListAdapter();

        final Observer<ArrayList<Result>> movieListObserver = new Observer<ArrayList<Result>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Result> results) {
                if (movieAdapter != null){
                    movieAdapter.replaceData(results);
                }
            }
        };

        final Observer<Boolean> movieProgressObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean movieProgress) {
                mMovieFragBinding.progressBar.setVisibility(movieProgress ? View.VISIBLE : View.GONE);
            }
        };

        moviesViewModel.item.observe(this,movieListObserver);
        moviesViewModel.dataLoading.observe(this,movieProgressObserver);
    }

    private void setupListAdapter() {
        recyclerView =  mMovieFragBinding.movieRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        movieAdapter = new MovieAdapter(new ArrayList<Result>(),getContext(),(MovieActivity)getActivity());
        movieAdapter.setHasStableIds(true);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                Log.d("Total Last Visible Item", totalItemCount + " " + lastVisibleItem);
                if (!moviesViewModel.isLoading() && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
