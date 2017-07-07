package com.example.moham.hacknews.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moham.hacknews.R;
import com.example.moham.hacknews.model.Articles;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by moham on 7/5/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {
    private ArrayList<Articles> articles;
    private int rowLayout;
    private Context cx;

    public RecyclerAdapter(ArrayList<Articles> articles, int rowLayout, Context cx) {
        this.articles = articles;
        this.rowLayout = rowLayout;
        this.cx = cx;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        String author = articles.get(position).getAuthor();
        String publishedAt = articles.get(position).getPublishedAt();

        if (author == null) author = "no author";
        if (publishedAt == null) publishedAt = "no date";

        holder.author.setText("By : " + author);
        holder.title.setText(articles.get(position).getTitle());
        holder.desc.setText(articles.get(position).getDescription());
        holder.date.setText("Date : " + publishedAt);
        Picasso.with(cx)
                .load(articles.get(position).getUrlToImage())
                .placeholder(R.drawable.ph) // optional
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {

        TextView title, date, desc, author;
        ImageView imageView;

        public RecyclerHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            desc = (TextView) itemView.findViewById(R.id.description);
            date = (TextView) itemView.findViewById(R.id.published_at);
            author = (TextView) itemView.findViewById(R.id.author);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = articles.get(getAdapterPosition()).getUrl();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    cx.startActivity(i);
                }
            });
        }

    }
}
