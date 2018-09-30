package example.android.com.popularmoviesappstage.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;

import java.util.List;

import example.android.com.popularmoviesappstage.R;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>{

    Context context;
    List<String> stringList;


    public TrailerAdapter(Context context, List<String> stringList) {
        this.context = context;
        this.stringList = stringList;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.trailer_item,null);
        TrailerViewHolder holder = new TrailerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, final int position) {
        holder.youTubePlayerView.initialize(new YouTubePlayerInitListener() {
            @Override
            public void onInitSuccess(@NonNull final YouTubePlayer youTubePlayer) {
                youTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady() {
                        super.onReady();
                        youTubePlayer.loadVideo(stringList.get(position),0);
                    }
                });
            }
        },true);
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder {

        YouTubePlayerView youTubePlayerView;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            youTubePlayerView = itemView.findViewById(R.id.trailer_item);
        }
    }



}
