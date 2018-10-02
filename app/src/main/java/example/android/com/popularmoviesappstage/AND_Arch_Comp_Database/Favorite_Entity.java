package example.android.com.popularmoviesappstage.AND_Arch_Comp_Database;

import android.arch.persistence.room.Entity;

import example.android.com.popularmoviesappstage.Models.Movie;

@Entity(tableName = "favourite_table")
public class Favorite_Entity {

    Movie movie;

    public Favorite_Entity(Movie movie) {
        this.movie = movie;
    }

    public Movie getFavMovie() {
        return movie;
    }

    public void setFavMovie(Movie movie) {
        this.movie = movie;
    }
}
