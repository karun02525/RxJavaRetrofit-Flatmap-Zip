package com.demo.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClass {

    private static String BASE_URL="https://api.openbrewerydb.org";


    private static Retrofit getRetrofitInstance()
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
    }

    private static Retrofit getNormalRetrofitInstance()
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com").addConverterFactory(GsonConverterFactory.create(gson)).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
    }

    public static ApiClient getApiService()
    {
        return getRetrofitInstance().create(ApiClient.class);
    }

    public static ApiClient getNormalApiService()
    {
        return getNormalRetrofitInstance().create(ApiClient.class);
    }
}
