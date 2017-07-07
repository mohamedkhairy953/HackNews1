package com.example.moham.hacknews.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moham.hacknews.R;
import com.example.moham.hacknews.adapter.RecyclerAdapter;
import com.example.moham.hacknews.model.Articles;
import com.example.moham.hacknews.model.ResponseModel;
import com.example.moham.hacknews.rest.ApiClient;
import com.example.moham.hacknews.rest.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by moham on 7/7/2017.
 */

public class HotNews extends Fragment {
    RecyclerView recyc;
    private final static String API_KEY="dc325cab2744421ba956d08d427f26ba";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.popular_news, container, false);
         recyc = (RecyclerView) rootView.findViewById(R.id.recycler);
        recyc.setLayoutManager(new LinearLayoutManager(getContext()));
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        final ProgressDialog dialog=new ProgressDialog(getContext());
        dialog.setMessage("Loading...");
        dialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseModel> topArticles = apiService.getTopArticles(API_KEY);
        topArticles.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, final Response<ResponseModel> response) {
                final ArrayList<Articles> articles = response.body().getArticles();
                recyc.setAdapter(new RecyclerAdapter(articles,R.layout.list_item,getContext()));
                dialog.dismiss();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),
                                response.message()+" ",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseModel> call, final Throwable t) {
                dialog.dismiss();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),
                                t.getMessage()+" ",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
        });
        super.onCreate(savedInstanceState);

    }
}
