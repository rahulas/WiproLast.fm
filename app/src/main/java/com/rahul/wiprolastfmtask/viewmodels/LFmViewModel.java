package com.rahul.wiprolastfmtask.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rahul.wiprolastfmtask.models.Album;
import com.rahul.wiprolastfmtask.models.Artist;
import com.rahul.wiprolastfmtask.models.Track;
import com.rahul.wiprolastfmtask.repos.LFmRepo;

import java.util.List;

public class LFmViewModel extends ViewModel {

    // Mutable Object: when it changes , it rises an event
    public MutableLiveData<List<Album>> albums;
    public MutableLiveData<List<Artist>> artists;
    public MutableLiveData<List<Track>> tracks;
    private LFmRepo repo;

    public LFmViewModel() {
        albums = new MutableLiveData<List<Album>>();
        artists = new MutableLiveData<List<Artist>>();
        tracks = new MutableLiveData<List<Track>>();
        repo = new LFmRepo();
    }

    public MutableLiveData<List<Album>> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> list) {
        albums.setValue(list);
    }

    public MutableLiveData<List<Artist>> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> list) {
        artists.setValue(list);
    }

    public MutableLiveData<List<Track>> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> list) {
        tracks.setValue(list);
    }

    public void searchAlbumsOnQuery(String query) {
        repo.getAlbumsOnSearch(query, this);
    }

    public void searchArtistsOnQuery(String query) {
        repo.getArtistsOnSearch(query, this);
    }

    public void searchTracksOnQuery(String query) {
        repo.getTracksOnSearch(query, this);
    }

}
