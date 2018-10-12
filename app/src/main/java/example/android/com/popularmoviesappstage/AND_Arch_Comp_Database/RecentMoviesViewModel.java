package example.android.com.popularmoviesappstage.AND_Arch_Comp_Database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import example.android.com.popularmoviesappstage.Models.Movie;

public class RecentMoviesViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> recentMovies;


    public LiveData<List<Movie>> getRecentMovies() {
        if (recentMovies == null){
            recentMovies = new MutableLiveData<List<Movie>>();
        }
        return recentMovies;
    }

    private void setRecentMovies(MutableLiveData<List<Movie>> recentMovies){
        this.recentMovies = recentMovies;
    }
}
