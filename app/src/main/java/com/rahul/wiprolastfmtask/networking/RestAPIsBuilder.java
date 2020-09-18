package com.rahul.wiprolastfmtask.networking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestAPIsBuilder {

    private static OkHttpClient client;

    private static Retrofit retrofit;

    private static OkHttpClient getClient() {
        client = new OkHttpClient.Builder().build();
        return client;
    }

    private static Gson gson;

    private static Gson getGsonFactory() {
        gson = new GsonBuilder()
                .setLenient()
                .create();
        return gson;
    }

    private static Retrofit getRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(RestConstants.BASE_URL)
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(getGsonFactory()))
                .client(getClient())
                .build();

        return retrofit;
    }

    public static RestAPIsEndPointInterface buildService() {
        return getRetrofit().create(RestAPIsEndPointInterface.class);
    }
}
