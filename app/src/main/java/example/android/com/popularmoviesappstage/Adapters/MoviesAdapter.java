package example.android.com.popularmoviesappstage.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import example.android.com.popularmoviesappstage.Models.Movie;
import example.android.com.popularmoviesappstage.R;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.movieViewHolder> {

    Context context;
    List<Movie> movies;
    final private RecycleItemClick recycleItemClick;

    public MoviesAdapter(Context context, List<Movie> movies,RecycleItemClick recycleItemClick) {
        this.context = context;
        this.movies = movies;
        this.recycleItemClick = recycleItemClick;
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
            recycleItemClick.onItemClickListener(position);
        }
    }

    public interface RecycleItemClick{
        void onItemClickListener(int clickIndex);
    }

}
