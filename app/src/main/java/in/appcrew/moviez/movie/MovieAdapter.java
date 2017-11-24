package in.appcrew.moviez.movie;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import data.Result;
import data.source.MovieRepository;
import in.appcrew.moviez.R;
import in.appcrew.moviez.databinding.MovieRowBinding;

/**
 * Created by nmrafiq on 27/10/17.
 */
//Test
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.BindingHolder> {
    private ArrayList<Result> mMovieList = new ArrayList<>();
    private Context mContext;


    public MovieAdapter(ArrayList<Result> mMovieList, Context mContext){
        this.mMovieList = mMovieList;
        this.mContext = mContext;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MovieRowBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.movie_row,parent,
                false);
        return new BindingHolder(binding);
    }

//    }

//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        // each data item is just a string in this case
//        public TextView mTextView;
//        public SimpleDraweeView mImageView;
//        public ViewHolder(View v) {
//            super(v);
//            mTextView = (TextView) v.findViewById(R.id.txt_name);
//            mImageView = (SimpleDraweeView)v.findViewById(R.id.img_movie);
//        }
//    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        MovieRowBinding binding = holder.binding;

        final MovieViewModel viewModel = new MovieViewModel(mContext,
                new MovieRepository()
        );
        viewModel.setMovieList(mMovieList.get(position));
        binding.setResult(mMovieList.get(position));
        binding.setViewmodel(viewModel);
    }

    public void replaceData(ArrayList<Result> movies){
        this.mMovieList = movies;
        notifyDataSetChanged();
    }

//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.mTextView.setText(mMovieList.get(position).getTitle());
//        holder.mImageView.setImageURI(Uri.parse(MovieActivity.IMAGE_API + mMovieList.get(position).getBackdropPath()));
//    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private MovieRowBinding binding;

        public BindingHolder(MovieRowBinding binding) {
            super(binding.movieRowCard);
            this.binding = binding;
        }
    }
}
