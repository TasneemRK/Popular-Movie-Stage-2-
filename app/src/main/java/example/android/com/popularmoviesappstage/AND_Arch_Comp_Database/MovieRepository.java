package example.android.com.popularmoviesappstage.AND_Arch_Comp_Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import example.android.com.popularmoviesappstage.Models.Movie;

public class MovieRepository {

    private MovieDao movieDao;
    private LiveData<List<Movie>> allFavMovies;
    static Movie movieloaded;

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

    public void deleteFromFav(Movie movie) {
        new RemoveFavAsyncTask(movieDao).execute(movie);
    }

    public void deleteAll() {
        new DeleteAllAsyncTask(movieDao).execute();
    }

    public Movie loadMovieById(int movie_id) {
        new loadMovieByIdAsyncTask(movieDao).execute(movie_id);
        return movieloaded;
    }

    public void updateFav(Movie movie) {
        new updateFavAsyncTask(movieDao).equals(movie);
    }

    /// ------------------------------------  All AsyncTasks Classes --------------------------------------------

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

    private static class RemoveFavAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao movieDao;

        public RemoveFavAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }


        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.deleteFromFav(movies[0]);
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

    public static class loadMovieByIdAsyncTask extends AsyncTask<Integer, Void, Movie> {

        private MovieDao movieDao;

        public loadMovieByIdAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Movie doInBackground(Integer... integers) {
            return movieDao.loadMovieById(integers[0]);
        }

        @Override
        protected void onPostExecute(Movie movie) {
            super.onPostExecute(movie);
            movieloaded = movie;
        }
    }

    public class updateFavAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao movieDao;

        public updateFavAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.updateFav(movies[0]);
            return null;
        }
    }
}
