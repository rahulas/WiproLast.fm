package com.rahul.wiprolastfmtask.networking;

import com.rahul.wiprolastfmtask.models.Results;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RestAPIsEndPointInterface {


    @Headers({"Accept: application/json"})
    @GET(RestConstants.SEARCH)
    Call<Results> getAlbums(
            @Query("method") String method,
            @Query("format") String format,
            @Query("api_key") String api_key,
            @Query("album") String album);


    @Headers({"Accept: application/json"})
    @GET(RestConstants.SEARCH)
    Call<Results> getArtists(
            @Query("method") String method,
            @Query("format") String format,
            @Query("api_key") String api_key,
            @Query("artist") String artist);


    @Headers({"Accept: application/json"})
    @GET(RestConstants.SEARCH)
    Call<Results> getTracks(
            @Query("method") String method,
            @Query("format") String format,
            @Query("api_key") String api_key,
            @Query("track") String track);

}
