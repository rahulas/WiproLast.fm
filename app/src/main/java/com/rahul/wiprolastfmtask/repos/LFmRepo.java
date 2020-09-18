package com.rahul.wiprolastfmtask.repos;

import android.util.Log;

import com.rahul.wiprolastfmtask.models.Results;
import com.rahul.wiprolastfmtask.networking.RestAPIsBuilder;
import com.rahul.wiprolastfmtask.networking.RestConstants;
import com.rahul.wiprolastfmtask.viewmodels.LFmViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LFmRepo {

    private LFmViewModel lFmViewModel;

    public void getAlbumsOnSearch(String query, LFmViewModel viewModel) {
        lFmViewModel = viewModel;
        Call<Results> resultCall = RestAPIsBuilder
                .buildService()
                .getAlbums(RestConstants.METHOD_ALBUM, RestConstants.FORMAT,
                        RestConstants.LAST_FM_API_KEY, query);

        resultCall.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                if (response.isSuccessful()) {
                    Results output = response.body();
                    lFmViewModel.setAlbums(output.results.albummatches.album);
                } else {
                    lFmViewModel.setAlbums(null);
                }
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
                lFmViewModel.setAlbums(null);
            }
        });
    }

    public void getArtistsOnSearch(String query, LFmViewModel viewModel) {
        lFmViewModel = viewModel;
        Call<Results> resultCall = RestAPIsBuilder
                .buildService()
                .getArtists(RestConstants.METHOD_ARTIST, RestConstants.FORMAT,
                        RestConstants.LAST_FM_API_KEY, query);

        resultCall.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                if (response.isSuccessful()) {
                    Results output = response.body();
                    lFmViewModel.setArtists(output.results.artistmatches.artist);
                } else {
                    lFmViewModel.setArtists(null);
                }
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
                lFmViewModel.setArtists(null);
            }
        });
    }

    public void getTracksOnSearch(String query, LFmViewModel viewModel) {
        lFmViewModel = viewModel;
        Call<Results> resultCall = RestAPIsBuilder
                .buildService()
                .getTracks(RestConstants.METHOD_TRACK, RestConstants.FORMAT,
                        RestConstants.LAST_FM_API_KEY, query);

        resultCall.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                if (response.isSuccessful()) {
                    Results output = response.body();
                    lFmViewModel.setTracks(output.results.trackmatches.track);
                } else {
                    lFmViewModel.setTracks(null);
                }
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
                lFmViewModel.setTracks(null);
            }
        });
    }
}
