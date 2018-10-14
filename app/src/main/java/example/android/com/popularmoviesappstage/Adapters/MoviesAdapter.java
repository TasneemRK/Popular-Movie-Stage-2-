package example.android.com.popularmoviesappstage.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import example.android.com.popularmoviesappstage.Activites.DetailsActivity;
import example.android.com.popularmoviesappstage.Models.Movie;
import example.android.com.popularmoviesappstage.R;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.movieViewHolder> {

    private Context context;
    private List<Movie> movies;
    public static final String MOVIE_OBJECT = "movie_object";

    public MoviesAdapter(Context context) {
        this.context = context;
    }

    public void setMoviesList(List<Movie>movies){
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public movieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item_layout, viewGroup, false);
        movieViewHolder holder = new movieViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull movieViewHolder movieViewHolder, int position) {
        movieViewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (movies == null){
            return 0;
        }
        return movies.size();
    }


    class movieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView original_title;


        public movieViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_item);
            original_title = itemView.findViewById(R.id.title_item);
            itemView.setOnClickListener(this);
        }

        public void bind(int position){
            Picasso.get().load(movies.get(position).getImage()).into(image);
            original_title.setText(movies.get(position).getOriginal_title());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Intent intent = new Intent(context,DetailsActivity.class);
            intent.putExtra(MOVIE_OBJECT,movies.get(position));
            context.startActivity(intent);
        }
    }

    public interface RecycleItemClick{
        void onItemClickListener(int clickIndex);
    }


    public void reset() {
        movies.clear();
        notifyDataSetChanged();
    }

}
