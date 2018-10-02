package example.android.com.popularmoviesappstage.Activites;

import android.arch.lifecycle.LiveData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.android.com.popularmoviesappstage.AND_Arch_Comp_Database.MovieViewModel;
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

        MovieViewModel viewModel = new MovieViewModel(getApplication());
        LiveData<List<Movie>> movies = viewModel.getAllFavMovies();
        List<Movie> movieList = movies.getValue();

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        MoviesAdapter adapter = new MoviesAdapter(this,movieList,this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClickListener(int clickIndex) {

    }
}
