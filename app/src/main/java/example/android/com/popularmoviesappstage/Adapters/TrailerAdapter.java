package example.android.com.popularmoviesappstage.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;

import java.util.List;

import example.android.com.popularmoviesappstage.R;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>{

    Context context;
    List<String> stringList;
    TrailerClickListener clickListener;


    public TrailerAdapter(Context context, List<String> stringList,TrailerClickListener clickListener) {
        this.context = context;
        this.stringList = stringList;
        this.clickListener = clickListener;
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
        holder.trailer_text.append(" " +(position + 1));
//        holder.youTubePlayerView.initialize(new YouTubePlayerInitListener() {
//            @Override
//            public void onInitSuccess(@NonNull final YouTubePlayer youTubePlayer) {
//                youTubePlayer.addListener(new AbstractYouTubePlayerListener() {
//                    @Override
//                    public void onReady() {
//                        super.onReady();
//                        youTubePlayer.loadVideo(stringList.get(position),0);
//                    }
//                });
//            }
//        },true);
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }


    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView trailer_text;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            trailer_text = itemView.findViewById(R.id.trailer_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            clickListener.trailerOnClick(position);
        }
    }

    public interface TrailerClickListener{
        void trailerOnClick(int position);
    }

}
