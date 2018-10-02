package example.android.com.popularmoviesappstage.AND_Arch_Comp_Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.database.Cursor;
import android.support.annotation.NonNull;

import java.util.List;

import example.android.com.popularmoviesappstage.Models.Movie;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;
    private LiveData<List<Movie>> allFavMovies;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
        allFavMovies = movieRepository.getAllFavMovies();
    }

    public LiveData<List<Movie>> getAllFavMovies(){
        return allFavMovies;
    }

    public void addToFav(Movie movie){
        movieRepository.addToFav(movie);
    }

    public void removeFromFav(Movie movie){
        movieRepository.deleteFromFav(movie);
    }

    public void deleteAll(){
        movieRepository.deleteAll();
    }
}
