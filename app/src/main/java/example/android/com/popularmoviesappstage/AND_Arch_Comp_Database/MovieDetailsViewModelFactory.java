package example.android.com.popularmoviesappstage.AND_Arch_Comp_Database;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import example.android.com.popularmoviesappstage.Models.Movie;

public class MovieDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final MovieRoom movieRoom;
    private final Movie movie;

    public MovieDetailsViewModelFactory(MovieRoom movieRoom, Movie movie) {
        this.movieRoom = movieRoom;
        this.movie = movie;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieViewModel(movieRoom,movie);
    }
}
