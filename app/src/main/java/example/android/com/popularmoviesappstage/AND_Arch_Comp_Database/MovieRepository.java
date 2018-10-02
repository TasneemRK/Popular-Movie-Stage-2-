package example.android.com.popularmoviesappstage.AND_Arch_Comp_Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import example.android.com.popularmoviesappstage.Models.Movie;

public class MovieRepository {

    private MovieDao movieDao;
    private LiveData<List<Movie>> allFavMovies;

    public MovieRepository(Application application) {
        MovieRoom movieRoom = MovieRoom.getInstance(application);
        movieDao = movieRoom.movieDao();
        allFavMovies = movieDao.getAllFav();
    }

    LiveData<List<Movie>> getAllFavMovies() {
        return allFavMovies;
    }

    public void addToFav(Movie movie) {
        new AddToFavAsyncTask(movieDao).execute(movie);
    }

    public void deleteFromFav(int movie_id) {
        new RemoveFavAsyncTask(movieDao).execute(movie_id);
    }

    public void deleteAll() {
        new DeleteAllAsyncTask(movieDao).execute();
    }

    /// ------------------------------------ AsyncTasks --------------------------------------------

    private static class AddToFavAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao movieDao;

        public AddToFavAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.addToFav(movies[0]);
            return null;
        }
    }

    private static class RemoveFavAsyncTask extends AsyncTask<Integer, Void, Void> {

        private MovieDao movieDao;

        public RemoveFavAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }


        @Override
        protected Void doInBackground(Integer... integers) {
            movieDao.deleteFromFav(integers[0]);
            return null;
        }
    }

    public static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private MovieDao movieDao;

        public DeleteAllAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            movieDao.deleteAllFav();
            return null;
        }
    }
}
