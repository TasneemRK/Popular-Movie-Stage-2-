package example.android.com.popularmoviesappstage.Activites;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
//import example.android.com.popularmoviesappstage.AND_Arch_Comp_Database.MovieRoomvieDao;
//import example.android.com.popularmoviesappstage.AND_Arch_Comp_Database.MovieRoom;
//import example.android.com.popularmoviesappstage.AND_Arch_Comp_Database.MovieViewModel;
import butterknife.OnClick;
import example.android.com.popularmoviesappstage.AND_Arch_Comp_Database.AppExecutors;
import example.android.com.popularmoviesappstage.AND_Arch_Comp_Database.MovieRoom;
import example.android.com.popularmoviesappstage.Adapters.MoviesAdapter;
import example.android.com.popularmoviesappstage.Models.Movie;
import example.android.com.popularmoviesappstage.R;

public class FavouriteActivity extends AppCompatActivity{

    @BindView(R.id.fav_recycleview)
    RecyclerView recyclerView;
    @BindView(R.id.clearAllFav)
    Button clearAll;
    MovieRoom movieRoom;
    int listsize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        ButterKnife.bind(this);
        movieRoom = MovieRoom.getInstance(this);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                movieRoom.movieDao().getAllFav().observe(FavouriteActivity.this, new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(@Nullable List<Movie> movies) {
                        listsize = movies.size();
                        recyclerView.setLayoutManager(new GridLayoutManager(FavouriteActivity.this,2));
                        MoviesAdapter adapter = new MoviesAdapter(FavouriteActivity.this,movies);
                        recyclerView.setAdapter(adapter);
                        if (listsize> 0) {
                            Log.d("lslslsl", movies.get(0).getOriginal_title());
                        } else {
                            Toast.makeText(FavouriteActivity.this, "there is no items", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });


    }

    @OnClick(R.id.clearAllFav)
    public void setClearAll(View view) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                movieRoom.movieDao().deleteAllFav();
            }
        });

    }

}
