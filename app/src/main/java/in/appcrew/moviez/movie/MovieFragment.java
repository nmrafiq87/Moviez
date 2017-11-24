package in.appcrew.moviez.movie;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import data.Result;
import in.appcrew.moviez.databinding.FragmentMovieBinding;


public class MovieFragment extends Fragment {
    private MoviesViewModel mMoviesViewModel;
    private FragmentMovieBinding mMovieFragBinding;
    public MovieFragment() {

    }

    public static MovieFragment newInstance() {
        MovieFragment fragment = new MovieFragment();
        return fragment;
    }

    public void setViewModel(MoviesViewModel viewModel) {
        mMoviesViewModel = viewModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        mMoviesViewModel.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMovieFragBinding = FragmentMovieBinding.inflate(inflater, container, false);
        mMovieFragBinding.setView(this);
        mMovieFragBinding.setViewmodel(mMoviesViewModel);
        View root = mMovieFragBinding.getRoot();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupListAdapter();
    }

    private void setupListAdapter() {
        RecyclerView listView =  mMovieFragBinding.movieRecyclerView;
        MovieAdapter movieAdapter = new MovieAdapter(new ArrayList<Result>(),getActivity());
        listView.setAdapter(movieAdapter);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

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
