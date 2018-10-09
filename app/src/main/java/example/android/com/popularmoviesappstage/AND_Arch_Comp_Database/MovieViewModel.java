package example.android.com.popularmoviesappstage.AND_Arch_Comp_Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import example.android.com.popularmoviesappstage.Models.Movie;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository repository;
    private LiveData<List<Movie>> allFav;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieRepository(application);
        allFav = repository.getAllFavMovies();
    }

    public LiveData<List<Movie>> getAllFav(){
        return allFav;
    }

    public void insertMovie(Movie movie){
        repository.addToFav(movie);
    }

    public void deletetMovie(Movie movie){
        repository.deleteFromFav(movie);
    }

    public void updateMovie(Movie movie){
        repository.updateFav(movie);
    }

    public void loadMovieById(int movie_id){
        repository.loadMovieById(movie_id);
    }

    public void deleteAll(){
        repository.deleteAll();
    }
}
