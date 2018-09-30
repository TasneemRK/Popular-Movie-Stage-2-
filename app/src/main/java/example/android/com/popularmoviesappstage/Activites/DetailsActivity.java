package example.android.com.popularmoviesappstage.Activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.android.com.popularmoviesappstage.Adapters.TrailerAdapter;
import example.android.com.popularmoviesappstage.Models.Movie;
import example.android.com.popularmoviesappstage.R;


public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.movie_image) ImageView movie_image;
    @BindView(R.id.original_text)  TextView original_text;
    @BindView(R.id.overview)  TextView overview;
    @BindView(R.id.date)  TextView date;
    @BindView(R.id.rating)  TextView rating;
    @BindView(R.id.trailerReycle) RecyclerView trailer_recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        List<String> list = new ArrayList<>();
        list.add("dNW0B0HsvVs");
        list.add("9Szts88zY4o");
        list.add("jPEYpryMp2s");
        list.add("WL8cFL83o_A");
        list.add("CBCenR1aWoE");
        list.add("fS37K3Lcf6g");
        list.add("i3Cyofu6hLE");

        trailer_recycle.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        TrailerAdapter adapter = new TrailerAdapter(this,list);
        trailer_recycle.setAdapter(adapter);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(MainActivity.MOVIE_OBJECT)){
            Movie movie = intent.getParcelableExtra(MainActivity.MOVIE_OBJECT);
            Picasso.get().load(movie.getImage()).into(movie_image);
            original_text.setText(movie.getOriginal_title());
            overview.setText(movie.getOverview());
            date.append(movie.getRelease_date());
            rating.append(movie.getRating());
        }

    }


}
