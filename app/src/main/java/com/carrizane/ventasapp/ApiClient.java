package com.carrizane.ventasapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String ENDPOINT = "https://create-ventas-service.herokuapp.com/";
    private static Retrofit retrofit;

    public static Retrofit getApiClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(ENDPOINT).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

}
