package com.example.moham.hacknews.rest;

import com.example.moham.hacknews.model.Articles;
import com.example.moham.hacknews.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by moham on 7/5/2017.
 */

public interface ApiInterface {
    @GET("articles?source=hacker-news&sortBy=top")
    Call<ResponseModel> getTopArticles(@Query("apiKey") String ApiKey);
    @GET("articles?source=hacker-news&sortBy=lates")
    Call<ResponseModel> getLatestArticles(@Query("apiKey") String ApiKey);

}
