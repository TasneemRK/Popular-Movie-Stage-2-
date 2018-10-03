package example.android.com.popularmoviesappstage.Activites;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.android.com.popularmoviesappstage.Adapters.ReviewAdapter;
import example.android.com.popularmoviesappstage.Adapters.TrailerAdapter;
import example.android.com.popularmoviesappstage.Models.Movie;
import example.android.com.popularmoviesappstage.Models.Review;
import example.android.com.popularmoviesappstage.R;
import example.android.com.popularmoviesappstage.Utils.AsyncTasks;
import example.android.com.popularmoviesappstage.Utils.NetworkUtils;


public class DetailsActivity extends NetworkUtils implements TrailerAdapter.TrailerClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

//        MovieViewModel movieViewModel = ViewModelProviders.of(DetailsActivity.this).get(MovieViewModel.class);

        ButterKnife.bind(this);

        list = new ArrayList<>();
        reviewList = new ArrayList<>();

        trailer_recycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new TrailerAdapter(this, list,this);
        trailer_recycle.setAdapter(adapter);

        reviewsReycle.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        reviewAdapter = new ReviewAdapter(this,reviewList);
        reviewsReycle.setAdapter(reviewAdapter);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(MainActivity.MOVIE_OBJECT)) {
            final Movie movie = intent.getParcelableExtra(MainActivity.MOVIE_OBJECT);
            Picasso.get().load(movie.getImage()).into(movie_image);
            original_text.setText(movie.getOriginal_title());
            overview.setText(movie.getOverview());
            date.append(movie.getRelease_date());
            rating.append(movie.getRating());
            int id = movie.getId();
            if(isOnline()){
                new TrailerAsyncTask().execute(id + "");
                new ReviewAsyncTask().execute(id+"");
            }else {
                Toast.makeText(this, "check internet connection", Toast.LENGTH_SHORT).show();
            }

            addToFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(DetailsActivity.this, "addToFav", Toast.LENGTH_SHORT).show();
//                    movieViewModel.addToFav(movie);
                }
            });
        }

    }

    @Override
    public void trailerOnClick(int position) {
    }


    public  class TrailerAsyncTask extends AsyncTask<String, Void, List<String>> implements TrailerAdapter.TrailerClickListener {

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
                for (int i=0 ; i<resultArray.length();i++){
                    JSONObject jsonObject = resultArray.getJSONObject(i);
                    String key  = jsonObject.getString(KEY);
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
            adapter = new TrailerAdapter(DetailsActivity.this, strings,this);
            trailer_recycle.setAdapter(adapter);
        }

        @Override
        public void trailerOnClick(final int position) {
            Dialog dialog = new Dialog(DetailsActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.youtube_player);
            dialog.setCancelable(true);
            dialog.show();

            YouTubePlayerView youTubePlayerView = dialog.findViewById(R.id.youtube_player);
            youTubePlayerView.initialize(new YouTubePlayerInitListener() {
            @Override
            public void onInitSuccess(@NonNull final YouTubePlayer youTubePlayer) {
                youTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady() {
                        super.onReady();
                        youTubePlayer.loadVideo(list.get(position),0);
                    }
                });
            }
        },true);
        }
    }

    public  class ReviewAsyncTask extends AsyncTask<String,Void,List<Review>>{

        List<Review> list = new ArrayList<>();

        @Override
        protected List<Review> doInBackground(String... strings) {
            if (strings[0] == null){
                return null;
            }
            try{
            String result = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildReviewUrl(strings[0]));
            JSONObject jsonObjectResult = new JSONObject(result);
            JSONArray resultArray = jsonObjectResult.getJSONArray(RESULTS);
            for (int i=0 ; i<resultArray.length();i++) {
                JSONObject jsonObject = resultArray.getJSONObject(i);
                String author = jsonObject.getString(AUTHOR);
                String content = jsonObject.getString(CONTENT);
                Review review = new Review(author,content);
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
            reviewAdapter = new ReviewAdapter(DetailsActivity.this,reviews);
            reviewsReycle.setAdapter(reviewAdapter);
        }
    }


}
