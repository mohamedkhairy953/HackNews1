package com.example.moham.hacknews.activity;

import android.app.ProgressDialog;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {
    private final static String API_KEY="dc325cab2744421ba956d08d427f26ba";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RecyclerView recyc = (RecyclerView) findViewById(R.id.recycler);
        recyc.setLayoutManager(new LinearLayoutManager(this));

        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("Loading our 10 news ...");
        dialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseModel> topArticles = apiService.getTopArticles(API_KEY);
        topArticles.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, final Response<ResponseModel> response) {
                final ArrayList<Articles> articles = response.body().getArticles();
                recyc.setAdapter(new RecyclerAdapter(articles,R.layout.list_item,getApplicationContext()));
                dialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                response.message()+" ",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseModel> call, final Throwable t) {
                dialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                t.getMessage()+" ",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
        });
    }
}
