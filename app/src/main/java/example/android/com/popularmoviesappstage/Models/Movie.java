package example.android.com.popularmoviesappstage.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;


@Entity(tableName = "favourite_table")
public class Movie implements Parcelable {

    @PrimaryKey
    @NonNull
    private int id;
    private String Original_title;
    private String Overview;
    private String image;
    private String rating;
    private String release_date;
    private boolean isFav;

    @Ignore
    public Movie() {
    }

    public Movie(@NonNull int id, String Original_title, String Overview, String image, String rating, String release_date ,boolean isFav) {
        this.id = id;
        this.Original_title = Original_title;
        this.Overview = Overview;
        this.image = image;
        this.rating = rating;
        this.release_date = release_date;
        this.isFav = isFav;

    }

    @Ignore
    public Movie(String original_title, String overview, String image, String rating, String release_date,boolean isFav) {
        this.Original_title = original_title;
        this.Overview = overview;
        this.image = image;
        this.rating = rating;
        this.release_date = release_date;
        this.isFav = isFav;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return Original_title;
    }

    public void setOriginal_title(String Original_title) {
        this.Original_title = Original_title;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String Overview) {
        this.Overview = Overview;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.Original_title);
        dest.writeString(this.Overview);
        dest.writeString(this.image);
        dest.writeString(this.rating);
        dest.writeString(this.release_date);
        dest.writeByte(this.isFav ? (byte) 1 : (byte) 0);
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.Original_title = in.readString();
        this.Overview = in.readString();
        this.image = in.readString();
        this.rating = in.readString();
        this.release_date = in.readString();
        this.isFav = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
