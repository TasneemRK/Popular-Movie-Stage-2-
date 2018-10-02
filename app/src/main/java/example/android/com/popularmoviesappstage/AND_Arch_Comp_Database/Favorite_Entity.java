package example.android.com.popularmoviesappstage.AND_Arch_Comp_Database;

import android.arch.persistence.room.Entity;
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

    Movie movie = new Movie(movie_id, movie_Original_title, movie_Overview, movie_image, movie_rating, movie_release_date);

    public Favorite_Entity() {
    }

    public Favorite_Entity(Movie movie) {
        this.movie = movie;
    }

}
