package example.android.com.popularmoviesappstage.Activites;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
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
import example.android.com.popularmoviesappstage.AND_Arch_Comp_Database.RecentMoviesViewModel;
import example.android.com.popularmoviesappstage.Adapters.MoviesAdapter;
import example.android.com.popularmoviesappstage.Models.Movie;
import example.android.com.popularmoviesappstage.R;
import example.android.com.popularmoviesappstage.Utils.Constant;
import example.android.com.popularmoviesappstage.Utils.NetworkUtils;

public class MainActivity extends NetworkUtils {

    public static final String RESULT_JSON = "results";
    public static final String ID_JSON = "id";
    public static final String TITLE_JSON = "original_title";
    public static final String IMAGE_JSON = "poster_path";
    public static final String OVERVIEW_JSON = "overview";
    public static final String DATE_JSON = "release_date";
    public static final String RATING_JSON = "vote_average";
    public static final int GRID_SPANCOUNT = 2;

    public static String STATE;
    public static final String POPOLAR = "popular";
    public static final String HIGHEST = "highest";
    public static final String FAVO = "favourite";

    private static final String SCROLL_POSITION_KEY = "scroll_position";
    private static int mCurrentPostion = 0;

    List<Movie> movies = new ArrayList<>();
    MoviesAdapter adapter;
    ActionBar actionBar;

    @BindView(R.id.moviesRecycleView)
    RecyclerView moviesRecycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();

        ButterKnife.bind(this);



        if (savedInstanceState != null) {
//            Log.d("savedInstanceState",savedInstanceState.getString("state"));
            if (savedInstanceState.getString("state").equals(POPOLAR)) {
                getPopularMovies();
            } else if (savedInstanceState.getString("state").equals(HIGHEST)) {
                getHighestRateMovies();
            } else if (savedInstanceState.getString("state").equals(FAVO)) {
                getFavouriteMovies();
            }
        } else {
            SetupRecycle();
            Log.d("savedInstanceStateNull","null");
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
                getPopularMovies();
                break;
            case R.id.highestRate_sort:
                getHighestRateMovies();
                break;
            case R.id.favourite:
                getFavouriteMovies();
                break;
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("state",STATE);
        int pos = ((GridLayoutManager) (moviesRecycle.getLayoutManager())).findFirstCompletelyVisibleItemPosition();
        outState.putInt(SCROLL_POSITION_KEY, pos);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        // Retrieve list state and list/item positions
        if (state != null) {
            mCurrentPostion = state.getInt(SCROLL_POSITION_KEY, 0);
        }
    }

    private void SetupRecycle() {
        adapter = new MoviesAdapter(this, movies);
        moviesRecycle.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, GRID_SPANCOUNT);
        moviesRecycle.setLayoutManager(layoutManager);
        getAllMovies();
    }

    private void getAllMovies() {
        if (isOnline()) {
            new GetMoviesAsyncTask().execute(Constant.ALL_MOIVES_API);
        } else {
            Toast.makeText(this, "you are not connected to the internet.", Toast.LENGTH_SHORT).show();
        }
    }

    private void getFavouriteMovies() {
        STATE = FAVO;
        MovieViewModel model = ViewModelProviders.of(this).get(MovieViewModel.class);
        model.getAllFav().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                adapter = new MoviesAdapter(MainActivity.this, movies);
                moviesRecycle.setAdapter(adapter);
                actionBar.setTitle(R.string.fav);
            }
        });
    }

    private void getHighestRateMovies() {
        STATE = HIGHEST;
        adapter.reset();
        if (isOnline()) {
            new GetMoviesAsyncTask().execute(Constant.SORT_BY_TOP_RATES_API);
            adapter.notifyDataSetChanged();
            actionBar.setTitle(R.string.highestSort);
        } else {
            Toast.makeText(this, "you are not connected to the internet.", Toast.LENGTH_SHORT).show();
        }
    }

    private void getPopularMovies() {
        STATE = POPOLAR;
        adapter.reset();
        if (isOnline()) {
            new GetMoviesAsyncTask().execute(Constant.SORT_BY_POPULARITY_API);
            adapter.notifyDataSetChanged();
            actionBar.setTitle(R.string.popularSort);
        } else {
            Toast.makeText(this, "you are not connected to the internet.", Toast.LENGTH_SHORT).show();
        }
    }


    public class GetMoviesAsyncTask extends AsyncTask<String, Void, List<Movie>> {

        List<Movie> movies = new ArrayList<>();

        @Override
        protected List<Movie> doInBackground(String... strings) {
            if (strings[0] == null) {
                return null;
            }

            URL url = buildUrl(strings[0]);
            try {
                String result = getResponseFromHttpUrl(url);
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
            adapter = new MoviesAdapter(MainActivity.this, movies1);
            moviesRecycle.setAdapter(adapter);
            moviesRecycle.scrollToPosition(mCurrentPostion);
        }

    }
}
