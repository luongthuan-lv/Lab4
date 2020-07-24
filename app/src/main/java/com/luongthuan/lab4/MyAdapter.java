package com.luongthuan.lab4;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.luongthuan.lab4.model.Example;
import com.luongthuan.lab4.model.Photo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.zip.CheckedOutputStream;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.PhotoHolder> {
    private List<Photo> photoList;
    Context context;
    Photo photo;

    public MyAdapter(List<Photo> photoList, Context context) {
        this.photoList = photoList;
        this.context = context;
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new PhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PhotoHolder holder, final int position) {
        photo = photoList.get(position);
        Glide.with(context).load(photo.getUrlSq()).into(holder.imgPhoto);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,MainActivity2.class);
                Bundle bundle=new Bundle();

                bundle.putString("title",photoList.get(position).getTitle());
                bundle.putString("url",photoList.get(position).getUrlL());
                bundle.putString("path",photoList.get(position).getPathalias());
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {

        return photoList.size();

    }

    public class PhotoHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;

        public PhotoHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
        }
    }
}
