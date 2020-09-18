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
import com.rahul.wiprolastfmtask.models.Album;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyViewHolder> {

    private Context mContext;
    private List<Album> albums;
    private AlbumAdapterListener listener;
    private final RequestManager glideManager;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView albumNameTv;
        private AppCompatTextView artistTv;
        private AppCompatImageView albumImage;

        public MyViewHolder(View view) {
            super(view);
            albumImage = view.findViewById(R.id.img);
            albumNameTv = view.findViewById(R.id.name_tv);
            artistTv = view.findViewById(R.id.artist_tv);
        }
    }

    public AlbumAdapter(Context context, List<Album> albums) {
        this.mContext = context;
        this.albums = albums;
        this.glideManager = Glide.with(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Album album = albums.get(position);

        holder.albumNameTv.setText(album.name);
        holder.artistTv.setText(album.artist);

        List<Album.Image> images = album.image;

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
                            .into(holder.albumImage);
                }
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAlbumClicked(holder.getAdapterPosition(), album);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public interface AlbumAdapterListener {
        void onAlbumClicked(int position, Album album);
    }

    public void setAlbumAdapterListener(AlbumAdapterListener listener) {
        this.listener = listener;
    }

}
