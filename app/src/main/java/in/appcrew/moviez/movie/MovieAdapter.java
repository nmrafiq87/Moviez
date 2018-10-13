package in.appcrew.moviez.movie;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import data.Result;
import in.appcrew.moviez.R;
import in.appcrew.moviez.databinding.MovieRowBinding;

/**
 * Created by nmrafiq on 27/10/17.
 */
//Test
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.BindingHolder> {
    private ArrayList<Result> mMovieList = new ArrayList<>();
    private MovieItemNavigator mMovieItemNavigator;
    private Context mContext;


    public MovieAdapter(ArrayList<Result> mMovieList,Context context, MovieActivity movieItemNavigator){
        this.mMovieList = mMovieList;
        this.mMovieItemNavigator = movieItemNavigator;
        this.mContext = context;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MovieRowBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.movie_row,parent,
                false);
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        Log.d("Position", " "+ position);
        MovieRowBinding binding = holder.binding;
        final MovieItemViewModel viewModel = new MovieItemViewModel();
        viewModel.setMovieList(mMovieList.get(position));
        viewModel.setNavigator(mMovieItemNavigator);
        binding.setResult(mMovieList.get(position));
        binding.setViewmodel(viewModel);
    }

    public void replaceData(ArrayList<Result> movies){
        this.mMovieList.addAll(movies);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(mMovieList.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private MovieRowBinding binding;

        public BindingHolder(MovieRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
