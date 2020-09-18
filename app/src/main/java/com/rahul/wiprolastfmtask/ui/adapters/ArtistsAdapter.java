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
import com.rahul.wiprolastfmtask.models.Artist;

import java.util.List;

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Artist> artists;
    private ArtistsAdapterListener listener;
    private final RequestManager glideManager;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView artistNameTv;
        private AppCompatTextView listenerTv;
        private AppCompatImageView artistIv;

        public MyViewHolder(View view) {
            super(view);
            artistIv = view.findViewById(R.id.img);
            artistNameTv = view.findViewById(R.id.artist_name_tv);
            listenerTv = view.findViewById(R.id.listeners_tv);
        }
    }

    public ArtistsAdapter(Context context, List<Artist> artists) {
        this.mContext = context;
        this.artists = artists;
        this.glideManager = Glide.with(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artist_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Artist artist = artists.get(position);

        holder.artistNameTv.setText(artist.name);
        holder.listenerTv.setText(artist.listeners);

        List<Artist.Image> images = artist.image;

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
                            .into(holder.artistIv);
                }
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onArtistClicked(holder.getAdapterPosition(), artist);
            }
        });
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public interface ArtistsAdapterListener {
        void onArtistClicked(int position, Artist album);
    }

    public void setArtistsAdapterListener(ArtistsAdapterListener listener) {
        this.listener = listener;
    }

}

