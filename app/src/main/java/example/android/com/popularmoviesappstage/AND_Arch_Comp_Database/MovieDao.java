package example.android.com.popularmoviesappstage.AND_Arch_Comp_Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import example.android.com.popularmoviesappstage.Models.Movie;

@Dao
public interface MovieDao {

    @Insert
    void addToFav(Movie movie);

    @Delete
    void deleteFromFav(int movie_id);

    @Query("Delete from favourite_table")
    void deleteAllFav();

    @Query("select * from favourite_table")
    LiveData<List<Movie>> getAllFav();
}
