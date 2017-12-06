package in.appcrew.moviez.moviedetail;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.appcrew.moviez.R;
import in.appcrew.moviez.databinding.MovieDetailRowBinding;

/**
 * Created by practo on 04/12/17.
 */

public class MovieDetailAdapter extends RecyclerView.Adapter<MovieDetailAdapter.DetailBindingHolder> {
    private ArrayList<String> mTitleList = new ArrayList<>();
    private ArrayList<String> mDescList = new ArrayList<>();
    private Context context;

    public MovieDetailAdapter(Context context){
        this.context = context;
    }

    @Override
    public DetailBindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MovieDetailRowBinding detailRowBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.movie_detail_row,parent,false);
        return new DetailBindingHolder(detailRowBinding);
    }

    @Override
    public void onBindViewHolder(DetailBindingHolder holder, int position) {
        MovieDetailRowBinding detailRowBinding = holder.binding;
        final MovieDetailItemViewModel movieDetailItemViewModel = new MovieDetailItemViewModel(
                mTitleList.get(position), mDescList.get(position));
        detailRowBinding.setDetailmodel(movieDetailItemViewModel);
    }

    public void replaceData(ArrayList<String> arrTitle, ArrayList<String> arrDesc){
        mTitleList.clear();
        mDescList.clear();
        this.mTitleList.addAll(arrTitle);
        this.mDescList.addAll(arrDesc);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDescList.size();
    }


    public static class DetailBindingHolder extends RecyclerView.ViewHolder {
        private MovieDetailRowBinding binding;

        public DetailBindingHolder(MovieDetailRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
