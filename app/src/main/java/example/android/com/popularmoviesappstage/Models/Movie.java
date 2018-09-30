package example.android.com.popularmoviesappstage.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private int id;
    private String Original_title;
    private String Overview;
    private String image;
    private String rating;
    private String release_date;

    public Movie() {
    }


    public Movie(String original_title, String overview, String image, String rating, String release_date) {
        this.Original_title = original_title;
        this.Overview = overview;
        this.image = image;
        this.rating = rating;
        this.release_date = release_date;
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

    public void setOriginal_title(String original_title) {
        Original_title = original_title;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
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
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.Original_title = in.readString();
        this.Overview = in.readString();
        this.image = in.readString();
        this.rating = in.readString();
        this.release_date = in.readString();
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
