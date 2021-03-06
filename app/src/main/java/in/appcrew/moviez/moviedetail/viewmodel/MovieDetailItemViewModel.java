package in.appcrew.moviez.moviedetail.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;

/**
 * Created by practo on 05/12/17.
 */

public class MovieDetailItemViewModel extends ViewModel {
    public ObservableField<String> mTitleList = new ObservableField<>();
    public ObservableField<String> mDescList = new ObservableField<>();

    public MovieDetailItemViewModel(String title, String description){
        this.mTitleList.set(title);
        this.mDescList.set(description);
    }


}
