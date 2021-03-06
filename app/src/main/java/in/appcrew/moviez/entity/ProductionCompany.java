package in.appcrew.moviez.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by practo on 30/11/17.
 */
public class ProductionCompany implements Parcelable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;
    public final static Creator<ProductionCompany> CREATOR = new Creator<ProductionCompany>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ProductionCompany createFromParcel(Parcel in) {
            return new ProductionCompany(in);
        }

        public ProductionCompany[] newArray(int size) {
            return (new ProductionCompany[size]);
        }

    }
            ;

    protected ProductionCompany(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public ProductionCompany() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(id);
    }

    public int describeContents() {
        return 0;
    }

}
