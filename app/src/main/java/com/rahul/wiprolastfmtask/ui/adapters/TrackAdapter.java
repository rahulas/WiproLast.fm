package com.rahul.wiprolastfmtask.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.rahul.wiprolastfmtask.R;
import com.rahul.wiprolastfmtask.models.Track;

import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.MyViewHolder> {

    private Context mContext;
    private List<Track> tracksList;
    private TrackAdapterListener listener;
    private final RequestManager glideManager;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView artistNameTv, trackNameTv;
        private AppCompatTextView listenerTv;
        private AppCompatImageView trackIv;

        public MyViewHolder(View view) {
            super(view);
            trackIv = view.findViewById(R.id.img);
            artistNameTv = view.findViewById(R.id.artist_name_tv);
            trackNameTv = view.findViewById(R.id.track_name_tv);
            listenerTv = view.findViewById(R.id.listeners_tv);
        }
    }

    public TrackAdapter(Context context, List<Track> tracks) {
        this.mContext = context;
        this.tracksList = tracks;
        this.glideManager = Glide.with(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.track_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Track track = tracksList.get(position);

        holder.trackNameTv.setText(track.name);
        holder.artistNameTv.setText(track.artist);
        holder.listenerTv.setText(track.listeners);

        List<Track.Image> images = track.image;

        if (images != null && images.size() > 0) {
            if (images.get(1) != null) {
                if (images.get(1).text != null && images.get(1).text.length() > 0) {
                    glideManager
                            .load(images.get(1).text)
                            .apply(RequestOptions
                                    .noTransformation()
                                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                    .placeholder(R.drawable.ic_loading)
                                    .fallback(R.drawable.ic_menu_album))
                            .into(holder.trackIv);
                }
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTrackClicked(holder.getAdapterPosition(), track);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tracksList.size();
    }

    public interface TrackAdapterListener {
        void onTrackClicked(int position, Track track);
    }

    public void setTrackAdapterListener(TrackAdapterListener listener) {
        this.listener = listener;
    }

}

