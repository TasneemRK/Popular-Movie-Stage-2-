package example.android.com.popularmoviesappstage.AND_Arch_Comp_Database;

import android.arch.lifecycle.ViewModel;
import example.android.com.popularmoviesappstage.Models.Movie;

public class MovieViewModel extends ViewModel {

    private Movie movie;
    private MovieRoom movieRoom;


    public MovieViewModel(MovieRoom movieRoom , Movie movie){
        this.movieRoom = movieRoom;
        this.movie = movie;
    }

    public void addToFav(){
        movieRoom.movieDao().addToFav(movie);
    }
    public void RemoveFromFav(){
        movieRoom.movieDao().deleteFromFav(movie);
    }

    public void deleteAllFav(){
        movieRoom.movieDao().deleteAllFav();
    }

}
