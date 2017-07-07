package com.example.moham.hacknews.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by moham on 7/5/2017.
 */

public class ApiClient {
    private static String BASE_URL="https://newsapi.org/v1/";
    private static Retrofit retrofit=null;
    public static Retrofit getClient(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
            return retrofit;
    }
}
