package example.android.com.popularmoviesappstage.AND_Arch_Comp_Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import example.android.com.popularmoviesappstage.Models.Movie;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> allFavMovies;
    private Movie movie;

    public MainViewModel(@NonNull Application application) {
        super(application);
        MovieRoom movieRoom = MovieRoom.getInstance(application);
        allFavMovies = movieRoom.movieDao().getAllFav();
    }

    public LiveData<List<Movie>> getAllFavMovies(){
        return allFavMovies;
    }



}
