package example.android.com.popularmoviesappstage.Activites;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
//import example.android.com.popularmoviesappstage.AND_Arch_Comp_Database.MovieRoomvieDao;
//import example.android.com.popularmoviesappstage.AND_Arch_Comp_Database.MovieRoom;
//import example.android.com.popularmoviesappstage.AND_Arch_Comp_Database.MovieViewModel;
import example.android.com.popularmoviesappstage.Adapters.MoviesAdapter;
import example.android.com.popularmoviesappstage.Models.Movie;
import example.android.com.popularmoviesappstage.R;

public class FavouriteActivity extends AppCompatActivity implements MoviesAdapter.RecycleItemClick{

    @BindView(R.id.fav_recycleview)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        ButterKnife.bind(this);


//        MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

//        List<Movie> movies = movieViewModel.getAllFavMovies();
//        List<Movie> movieList = movies.getValue();
//        Log.d("listlisd",movies.get(0).toString());

//        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
//        MoviesAdapter adapter = new MoviesAdapter(this,movieList,this);
//        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClickListener(int clickIndex) {

    }
}
