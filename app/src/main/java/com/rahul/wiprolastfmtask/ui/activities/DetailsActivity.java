package com.rahul.wiprolastfmtask.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.rahul.wiprolastfmtask.R;
import com.rahul.wiprolastfmtask.models.Album;
import com.rahul.wiprolastfmtask.models.Artist;
import com.rahul.wiprolastfmtask.models.Track;

public class DetailsActivity extends AppCompatActivity {

    public static final String TAG = DetailsActivity.class.getSimpleName();

    public static final String SELECTED_TYPE = "selected_type";
    public static final String SELECTED_TYPE_POJO = "selected_type_pojo";

    private AppCompatImageView imageView;
    private FloatingActionButton fab;
    private View albumLayout, artistLayout, trackLayout;

    private Integer selectedType = 0;

    private Album selectedAlbum;
    private Artist selectedArtist;
    private Track selectedTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initViews();

        Gson gson = new Gson();
        if (getIntent() != null) {
            selectedType = getIntent().getIntExtra(SELECTED_TYPE, 0);
            if (selectedType == MainActivity.MENU_ALBUMS) {
                albumLayout.setVisibility(View.VISIBLE);
                initAlbumLayoutViews();
                if (getIntent().getStringExtra(SELECTED_TYPE_POJO) != null) {
                    selectedAlbum = gson.fromJson(getIntent().getStringExtra(SELECTED_TYPE_POJO), Album.class);
                }
            } else if (selectedType == MainActivity.MENU_ARTISTS) {
                artistLayout.setVisibility(View.VISIBLE);
                initArtistLayoutViews();
                if (getIntent().getStringExtra(SELECTED_TYPE_POJO) != null) {
                    selectedArtist = gson.fromJson(getIntent().getStringExtra(SELECTED_TYPE_POJO), Artist.class);
                }
            } else if (selectedType == MainActivity.MENU_SONGS) {
                trackLayout.setVisibility(View.VISIBLE);
                initTrackLayoutViews();
                if (getIntent().getStringExtra(SELECTED_TYPE_POJO) != null) {
                    selectedTrack = gson.fromJson(getIntent().getStringExtra(SELECTED_TYPE_POJO), Track.class);
                }
            }
        }

        loadData();
    }

    private void loadData() {
        switch (selectedType) {
            case MainActivity.MENU_ALBUMS: {
                loadAlbumDetails();
                break;
            }
            case MainActivity.MENU_ARTISTS: {
                loadArtistDetails();
                break;
            }
            case MainActivity.MENU_SONGS: {
                loadTrackDetails();
                break;
            }
            default: {
                break;
            }
        }
    }

    private void loadAlbumDetails() {
        if (selectedAlbum != null) {
            if (selectedAlbum.name != null && selectedAlbum.name.length() > 0)
                albumLayoutAlbumNameTv.setText(selectedAlbum.name);

            if (selectedAlbum.artist != null && selectedAlbum.artist.length() > 0)
                albumLayoutArtistNameTv.setText(selectedAlbum.artist);

            if (selectedAlbum.streamable != null && selectedAlbum.streamable.length() > 0)
                albumLayoutStreamableTv.setText(selectedAlbum.streamable);

            if (selectedAlbum.mbid != null && selectedAlbum.mbid.length() > 0)
                albumLayoutMbidTv.setText(selectedAlbum.mbid);

            if (selectedAlbum.url != null && selectedAlbum.url.length() > 0)
                setUrlToFab(selectedAlbum.url);

            if (selectedAlbum.image != null && selectedAlbum.image.size() > 0) {
                int image = selectedAlbum.image.size() - 1;
                if (selectedAlbum.image.get(image) != null)
                    if (selectedAlbum.image.get(image).text != null && selectedAlbum.image.get(image).text.length() > 0) {
                        loadImage(selectedAlbum.image.get(image).text);
                    }
            }
        }
    }

    private void loadArtistDetails() {
        if (selectedArtist != null) {
            if (selectedArtist.name != null && selectedArtist.name.length() > 0)
                artistLayoutArtistNameTv.setText(selectedArtist.name);

            if (selectedArtist.listeners != null && selectedArtist.listeners.length() > 0)
                artistLayoutListenersTv.setText(selectedArtist.listeners);

            if (selectedArtist.streamable != null && selectedArtist.streamable.length() > 0)
                artistLayoutStreamableTv.setText(selectedArtist.streamable);

            if (selectedArtist.mbid != null && selectedArtist.mbid.length() > 0)
                artistLayoutMbidTv.setText(selectedArtist.mbid);

            if (selectedArtist.url != null && selectedArtist.url.length() > 0)
                setUrlToFab(selectedArtist.url);

            if (selectedArtist.image != null && selectedArtist.image.size() > 0) {
                int image = selectedArtist.image.size() - 1;
                if (selectedArtist.image.get(image) != null)
                    if (selectedArtist.image.get(image).text != null && selectedArtist.image.get(image).text.length() > 0) {
                        loadImage(selectedArtist.image.get(image).text);
                    }
            }
        }
    }

    private void loadTrackDetails() {
        if (selectedTrack != null) {
            if (selectedTrack.name != null && selectedTrack.name.length() > 0)
                trackLayoutTrackNameTv.setText(selectedTrack.name);

            if (selectedTrack.artist != null && selectedTrack.artist.length() > 0)
                trackLayoutArtistNameTv.setText(selectedTrack.artist);

            if (selectedTrack.listeners != null && selectedTrack.listeners.length() > 0)
                trackLayoutListenersTv.setText(selectedTrack.listeners);

            if (selectedTrack.streamable != null && selectedTrack.streamable.length() > 0)
                trackLayoutStreamableTv.setText(selectedTrack.streamable);

            if (selectedTrack.mbid != null && selectedTrack.mbid.length() > 0)
                trackLayoutMbidTv.setText(selectedTrack.mbid);

            if (selectedTrack.url != null && selectedTrack.url.length() > 0)
                setUrlToFab(selectedTrack.url);

            if (selectedTrack.image != null && selectedTrack.image.size() > 0) {
                int image = selectedTrack.image.size() - 1;
                if (selectedTrack.image.get(image) != null)
                    if (selectedTrack.image.get(image).text != null && selectedTrack.image.get(image).text.length() > 0) {
                        loadImage(selectedTrack.image.get(image).text);
                    }
            }

        }
    }

    private void setUrlToFab(final String infoUrl) {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(infoUrl)));
            }
        });
    }

    private void loadImage(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .apply(RequestOptions
                        .noTransformation()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .placeholder(R.drawable.ic_loading)
                        .fallback(R.drawable.ic_menu_album))
                .into(imageView);
    }

    private void initViews() {
        imageView = findViewById(R.id.image_view);
        fab = findViewById(R.id.fab);
        albumLayout = findViewById(R.id.album_content_lay);
        albumLayout.setVisibility(View.GONE);
        artistLayout = findViewById(R.id.artist_content_lay);
        artistLayout.setVisibility(View.GONE);
        trackLayout = findViewById(R.id.track_content_lay);
        trackLayout.setVisibility(View.GONE);
    }

    private AppCompatTextView albumLayoutAlbumNameTv, albumLayoutArtistNameTv, albumLayoutStreamableTv, albumLayoutMbidTv;

    private AppCompatTextView artistLayoutArtistNameTv, artistLayoutListenersTv, artistLayoutStreamableTv, artistLayoutMbidTv;

    private AppCompatTextView trackLayoutTrackNameTv, trackLayoutArtistNameTv, trackLayoutListenersTv, trackLayoutStreamableTv, trackLayoutMbidTv;

    private void initAlbumLayoutViews() {
        albumLayoutAlbumNameTv = findViewById(R.id.album_name_tv);
        albumLayoutArtistNameTv = findViewById(R.id.album_artist_name_tv);
        albumLayoutStreamableTv = findViewById(R.id.album_streamable_tv);
        albumLayoutMbidTv = findViewById(R.id.album_mbid_tv);
    }

    private void initArtistLayoutViews() {
        artistLayoutArtistNameTv = findViewById(R.id.artist_name_tv);
        artistLayoutListenersTv = findViewById(R.id.artist_listeners_tv);
        artistLayoutStreamableTv = findViewById(R.id.artist_streamable_tv);
        artistLayoutMbidTv = findViewById(R.id.artist_mbid_tv);
    }

    private void initTrackLayoutViews() {
        trackLayoutTrackNameTv = findViewById(R.id.track_name_tv);
        trackLayoutArtistNameTv = findViewById(R.id.track_artist_name_tv);
        trackLayoutListenersTv = findViewById(R.id.track_listeners_tv);
        trackLayoutStreamableTv = findViewById(R.id.track_streamable_tv);
        trackLayoutMbidTv = findViewById(R.id.track_mbid_tv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.app_bar_search);
        searchMenuItem.setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }
}