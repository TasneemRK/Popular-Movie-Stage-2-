package example.android.com.popularmoviesappstage.AND_Arch_Comp_Database;


import android.arch.persistence.room.TypeConverter;

import example.android.com.popularmoviesappstage.Models.Movie;

public class MovieTypeConverter {

@TypeConverter
    public static Movie toMovie(int movie_id, String movie_Original_title, String movie_Overview, String movie_image, String movie_rating, String movie_release_date){
    Movie movie = new Movie(movie_id,movie_Original_title,movie_Overview,movie_image,movie_rating,movie_release_date);
    return movie;
}

}
