package example.android.com.popularmoviesappstage.Activites;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.android.com.popularmoviesappstage.AND_Arch_Comp_Database.MovieViewModel;
import example.android.com.popularmoviesappstage.Adapters.MoviesAdapter;
import example.android.com.popularmoviesappstage.Adapters.ReviewAdapter;
import example.android.com.popularmoviesappstage.Adapters.TrailerAdapter;
import example.android.com.popularmoviesappstage.Models.Movie;
import example.android.com.popularmoviesappstage.Models.Review;
import example.android.com.popularmoviesappstage.R;
import example.android.com.popularmoviesappstage.Utils.NetworkUtils;


public class DetailsActivity extends AppCompatActivity implements TrailerAdapter.TrailerClickListener {

    public static final String RESULTS = "results";
    public static final String KEY = "key";
    public static final String CONTENT = "content";
    public static final String AUTHOR = "author";

    List<String> list;
    List<Review> reviewList;
    TrailerAdapter adapter;
    ReviewAdapter reviewAdapter;

    @BindView(R.id.movie_image)
    ImageView movie_image;
    @BindView(R.id.original_text)
    TextView original_text;
    @BindView(R.id.overview)
    TextView overview;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.rating)
    TextView rating;
    @BindView(R.id.trailerReycle)
    RecyclerView trailer_recycle;
    @BindView(R.id.addToFav_button)
    ImageButton addToFav;
    @BindView(R.id.reviewsReycle)
    RecyclerView reviewsReycle;

    NetworkUtils networkUtils;

    Movie movie;
    MovieViewModel movieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        networkUtils = new NetworkUtils(this);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        LoadTrailers();

        LoadReviews();

        // ---------------- get Movie details from intent --------------------
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(MoviesAdapter.MOVIE_OBJECT)) {
            movie = intent.getParcelableExtra(MoviesAdapter.MOVIE_OBJECT);
            loadMovieData(movie);
            actionBar.setTitle(movie.getOriginal_title());
        }

    }

    private void LoadReviews() {
        reviewList = new ArrayList<>();
        reviewsReycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        reviewAdapter = new ReviewAdapter(this, reviewList);
        reviewsReycle.setAdapter(reviewAdapter);
    }

    private void LoadTrailers() {
        list = new ArrayList<>();
        trailer_recycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new TrailerAdapter(this, list, this);
        trailer_recycle.setAdapter(adapter);
    }

    private void loadMovieData(Movie movie) {
        Picasso.get().load(movie.getImage())
                .placeholder(R.drawable.ic_image_placeholder_24dp)
                .error(R.drawable.ic_error_black_24dp)
                .into(movie_image);
        original_text.setText(movie.getOriginal_title());
        overview.setText(movie.getOverview());
        date.append(movie.getRelease_date());
        rating.append(movie.getRating());
        int id = movie.getId();
        if (movie.isFav()){
            addToFav.setImageResource(R.drawable.ic_star_fill_24dp);
        }else {
            addToFav.setImageResource(R.drawable.ic_star_black_24dp);
        }
        if (networkUtils.isOnline()) {
            new TrailerAsyncTask().execute(id + "");
            new ReviewAsyncTask().execute(id + "");
        } else {
            Toast.makeText(this, "check internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.addToFav_button)
    void setAddToFav() {
        if (movie.isFav()) {
            movie.setFav(false);
            movieViewModel.deletetMovie(movie);
            addToFav.setImageResource(R.drawable.ic_star_black_24dp);
            movieViewModel.updateMovie(movie);
            Toast.makeText(this, "remove from fav", Toast.LENGTH_SHORT).show();

        } else {
            movieViewModel.insertMovie(movie);
            addToFav.setImageResource(R.drawable.ic_star_fill_24dp);
            movie.setFav(true);
            movieViewModel.updateMovie(movie);
            Toast.makeText(this, "add to fav", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            Intent intent = new Intent(DetailsActivity.this,MainActivity.class);
            startActivity(intent);
            finish();

        }
        return true;
    }

    @Override
    public void trailerOnClick(int position) {
    }


    public class TrailerAsyncTask extends AsyncTask<String, Void, List<String>> implements TrailerAdapter.TrailerClickListener {

        List<String> list = new ArrayList<>();

        @Override
        protected List<String> doInBackground(String... strings) {
            if (strings[0] == null) {
                return null;
            }

            try {
                String result = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildTrailerUrl(strings[0]));
                JSONObject jsonObjectResult = new JSONObject(result);
                JSONArray resultArray = jsonObjectResult.getJSONArray(RESULTS);
                for (int i = 0; i < resultArray.length(); i++) {
                    JSONObject jsonObject = resultArray.getJSONObject(i);
                    String key = jsonObject.getString(KEY);
                    list.add(key);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            adapter = new TrailerAdapter(DetailsActivity.this, strings, this);
            trailer_recycle.setAdapter(adapter);
        }

        @Override
        public void trailerOnClick(final int position) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/" + list.get(position)));
            startActivity(intent);
        }
    }

    public class ReviewAsyncTask extends AsyncTask<String, Void, List<Review>> {

        List<Review> list = new ArrayList<>();

        @Override
        protected List<Review> doInBackground(String... strings) {
            if (strings[0] == null) {
                return null;
            }
            try {
                String result = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildReviewUrl(strings[0]));
                JSONObject jsonObjectResult = new JSONObject(result);
                JSONArray resultArray = jsonObjectResult.getJSONArray(RESULTS);
                for (int i = 0; i < resultArray.length(); i++) {
                    JSONObject jsonObject = resultArray.getJSONObject(i);
                    String author = jsonObject.getString(AUTHOR);
                    String content = jsonObject.getString(CONTENT);
                    Review review = new Review(author, content);
                    list.add(review);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<Review> reviews) {
            super.onPostExecute(reviews);
            reviewAdapter = new ReviewAdapter(DetailsActivity.this, reviews);
            reviewsReycle.setAdapter(reviewAdapter);
        }
    }


}
