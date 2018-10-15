package example.android.com.popularmoviesappstage.Activites;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.android.com.popularmoviesappstage.AND_Arch_Comp_Database.MovieViewModel;
import example.android.com.popularmoviesappstage.Adapters.MoviesAdapter;
import example.android.com.popularmoviesappstage.Models.Movie;
import example.android.com.popularmoviesappstage.R;
import example.android.com.popularmoviesappstage.Utils.CalNoOfColumnsAccordingToWidth;
import example.android.com.popularmoviesappstage.Utils.Constant;
import example.android.com.popularmoviesappstage.Utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    public static final String RESULT_JSON = "results";
    public static final String ID_JSON = "id";
    public static final String TITLE_JSON = "original_title";
    public static final String IMAGE_JSON = "poster_path";
    public static final String OVERVIEW_JSON = "overview";
    public static final String DATE_JSON = "release_date";
    public static final String RATING_JSON = "vote_average";
    public static int GRID_SPANCOUNT ;

    public static String PARCABLE_KEY = "PARCABLE_KEY";
    public static String Position_KEY = "Position_KEY";
    public static String STATE_KEY = "STATE_KEY";
    public static final String POPOLAR = "popular";
    public static final String HIGHEST = "highest";
    public static final String FAVO = "favourite";
    public static String STATE = POPOLAR;

    public static int position = 0;

    private GridLayoutManager layoutManager;
    private Parcelable parcelable;

    MoviesAdapter adapter;
    ActionBar actionBar;

    @BindView(R.id.moviesRecycleView)
    RecyclerView moviesRecycle;

    NetworkUtils networkUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();

        networkUtils = new NetworkUtils(this);

        ButterKnife.bind(this);

        CalNoOfColumnsAccordingToWidth noOfColumnsAccordingToWidth = new CalNoOfColumnsAccordingToWidth(this);
        GRID_SPANCOUNT = noOfColumnsAccordingToWidth.calculateNoOfColumns();

        SetupRecycle();
        if (STATE .equals(POPOLAR)) {
            getPopularMovies();
        } else if (STATE.equals(HIGHEST)) {
            getHighestRateMovies();
        } else if (STATE.equals(FAVO)) {
            getFavouriteMovies();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.popular_sort:
                adapter.reset();
                position = 0;
                getPopularMovies();
                break;
            case R.id.highestRate_sort:
                adapter.reset();
                position = 0;
                getHighestRateMovies();
                break;
            case R.id.favourite:
                adapter.reset();
                position = 0;
                getFavouriteMovies();
                break;
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_KEY, STATE);
        outState.putParcelable(PARCABLE_KEY,layoutManager.onSaveInstanceState());
        outState.putInt(Position_KEY,layoutManager.findFirstVisibleItemPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        STATE = state.getString(STATE_KEY);
        parcelable = state.getParcelable(PARCABLE_KEY);
        position = state.getInt(Position_KEY);
    }



    @Override
    protected void onResume() {
        super.onResume();
        if (parcelable != null){
            moviesRecycle.getLayoutManager().onRestoreInstanceState(parcelable);
        }
    }

    private void SetupRecycle() {
        adapter = new MoviesAdapter(this);
        layoutManager = new GridLayoutManager(this, GRID_SPANCOUNT);
        moviesRecycle.setLayoutManager(layoutManager);
        moviesRecycle.setAdapter(adapter);
        moviesRecycle.setHasFixedSize(true);
    }




    private void getFavouriteMovies() {
        STATE = FAVO;
        MovieViewModel model = ViewModelProviders.of(this).get(MovieViewModel.class);
        model.getAllFav().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> moviess) {
                adapter.setMoviesList(moviess);
                moviesRecycle.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                actionBar.setTitle(R.string.fav);
            }
        });
    }

    private void getHighestRateMovies() {
        STATE = HIGHEST;
        if (networkUtils.isOnline()) {
            new GetMoviesAsyncTask().execute(Constant.SORT_BY_TOP_RATES_API);
            adapter.notifyDataSetChanged();
            actionBar.setTitle(R.string.highestSort);
            moviesRecycle.scrollToPosition(position);
        } else {
            Toast.makeText(this, "you are not connected to the internet.", Toast.LENGTH_SHORT).show();
        }
    }

    private void getPopularMovies() {
        STATE = POPOLAR;
        if (networkUtils.isOnline()) {
            new GetMoviesAsyncTask().execute(Constant.SORT_BY_POPULARITY_API);
            adapter.notifyDataSetChanged();
            actionBar.setTitle(R.string.popularSort);
            moviesRecycle.scrollToPosition(position);
        } else {
            Toast.makeText(this, "you are not connected to the internet.", Toast.LENGTH_SHORT).show();
        }
    }


    public class GetMoviesAsyncTask extends AsyncTask<String, Void, List<Movie>> {



        @Override
        protected List<Movie> doInBackground(String... strings) {
            if (strings[0] == null) {
                return null;
            }

            List<Movie> movies = new ArrayList<>();

            URL url = networkUtils.buildUrl(strings[0]);
            try {
                String result = networkUtils.getResponseFromHttpUrl(url);
                JSONObject jsonObject = new JSONObject(result);
                JSONArray resultsArray = jsonObject.getJSONArray(RESULT_JSON);
                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject resultJson = resultsArray.getJSONObject(i);


                    Movie movie = new Movie();
                    if (resultJson.has(ID_JSON)) {
                        movie.setId(resultJson.getInt(ID_JSON));
                    }
                    if (resultJson.has(TITLE_JSON)) {
                        movie.setOriginal_title(resultJson.getString(TITLE_JSON));
                    }
                    if (resultJson.has(IMAGE_JSON)) {
                        movie.setImage(Constant.IMAGE_URL + resultJson.getString(IMAGE_JSON));
                    }
                    if (resultJson.has(OVERVIEW_JSON)) {
                        movie.setOverview(resultJson.getString(OVERVIEW_JSON));
                    }
                    if (resultJson.has(DATE_JSON)) {
                        movie.setRelease_date(resultJson.getString(DATE_JSON));
                    }
                    if (resultJson.has(RATING_JSON)) {
                        movie.setRating(resultJson.getDouble(RATING_JSON) + "");
                    }

                    movies.add(movie);

                }

                return movies;

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return movies;
        }

        @Override
        protected void onPostExecute(List<Movie> movies1) {
            adapter.setMoviesList(movies1);
            if (parcelable != null){
                moviesRecycle.getLayoutManager().onRestoreInstanceState(parcelable);
            }
        }

    }
}
