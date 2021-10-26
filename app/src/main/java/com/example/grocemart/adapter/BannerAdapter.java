package com.example.grocemart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocemart.R;
import com.example.grocemart.activity.MainActivity;
import com.example.grocemart.activity.UserProfile;
import com.example.grocemart.modelclass.Banner_ModelClass;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {

    Context context;
    ArrayList<Banner_ModelClass> banneradp;

    public BannerAdapter(MainActivity mainActivity, ArrayList<Banner_ModelClass> home_banner) {

        this.context = mainActivity;
        banneradp = home_banner;
    }

    @NonNull
    @NotNull
    @Override
    public BannerAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_custom_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BannerAdapter.ViewHolder holder, int position) {

        Banner_ModelClass banner = banneradp.get(position);

        String image = "https://"+banner.getBannerImage();
        Picasso.with(context).load(image).into(holder.banner_Image);

    }

    @Override
    public int getItemCount() {
        return banneradp.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView banner_Image;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            banner_Image = itemView.findViewById(R.id.banner_Image);
        }
    }
}
