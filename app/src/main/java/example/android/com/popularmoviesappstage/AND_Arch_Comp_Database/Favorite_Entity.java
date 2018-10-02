package example.android.com.popularmoviesappstage.AND_Arch_Comp_Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import example.android.com.popularmoviesappstage.Models.Movie;

@Entity(tableName = "favourite_table")
public class Favorite_Entity {

    @PrimaryKey
    @NonNull
    private int movie_id;
    private String movie_Original_title;
    private String movie_Overview;
    private String movie_image;
    private String movie_rating;
    private String movie_release_date;

    @Ignore
    public Favorite_Entity() {
    }

    public Favorite_Entity(int movie_id, String movie_Original_title, String movie_Overview, String movie_image, String movie_rating, String movie_release_date) {
        this.movie_id = movie_id;
        this.movie_Original_title = movie_Original_title;
        this.movie_Overview = movie_Overview;
        this.movie_image = movie_image;
        this.movie_rating = movie_rating;
        this.movie_release_date = movie_release_date;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovie_Original_title() {
        return movie_Original_title;
    }

    public void setMovie_Original_title(String movie_Original_title) {
        this.movie_Original_title = movie_Original_title;
    }

    public String getMovie_Overview() {
        return movie_Overview;
    }

    public void setMovie_Overview(String movie_Overview) {
        this.movie_Overview = movie_Overview;
    }

    public String getMovie_image() {
        return movie_image;
    }

    public void setMovie_image(String movie_image) {
        this.movie_image = movie_image;
    }

    public String getMovie_rating() {
        return movie_rating;
    }

    public void setMovie_rating(String movie_rating) {
        this.movie_rating = movie_rating;
    }

    public String getMovie_release_date() {
        return movie_release_date;
    }

    public void setMovie_release_date(String movie_release_date) {
        this.movie_release_date = movie_release_date;
    }
}
