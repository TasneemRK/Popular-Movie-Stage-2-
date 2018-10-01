package example.android.com.popularmoviesappstage.Activites;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.android.com.popularmoviesappstage.Adapters.TrailerAdapter;
import example.android.com.popularmoviesappstage.Models.Movie;
import example.android.com.popularmoviesappstage.R;
import example.android.com.popularmoviesappstage.Utils.AsyncTasks;
import example.android.com.popularmoviesappstage.Utils.NetworkUtils;


public class DetailsActivity extends AppCompatActivity {

    public static final String RESULTS = "results";

    List<String> list;
    TrailerAdapter adapter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        list = new ArrayList<>();

        trailer_recycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new TrailerAdapter(this, list);
        trailer_recycle.setAdapter(adapter);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(MainActivity.MOVIE_OBJECT)) {
            Movie movie = intent.getParcelableExtra(MainActivity.MOVIE_OBJECT);
            Picasso.get().load(movie.getImage()).into(movie_image);
            original_text.setText(movie.getOriginal_title());
            overview.setText(movie.getOverview());
            date.append(movie.getRelease_date());
            rating.append(movie.getRating());
            int id = movie.getId();
            new TrailerAsyncTask().execute(id + "");
            Log.d("listlist",list.toString());
        }

    }


    public  class TrailerAsyncTask extends AsyncTask<String, Void, List<String>> {

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
                    String key  = jsonObject.getString("key");
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
            adapter = new TrailerAdapter(DetailsActivity.this, strings);
            trailer_recycle.setAdapter(adapter);
        }
    }

}
