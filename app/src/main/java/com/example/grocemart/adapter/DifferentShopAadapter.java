package com.example.grocemart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocemart.R;
import com.example.grocemart.activity.DifferentShopName;
import com.example.grocemart.modelclass.DifferentShopDetails_ModelClass;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DifferentShopAadapter extends RecyclerView.Adapter<DifferentShopAadapter.MyViewHolder> {

    Context context;
    ArrayList<DifferentShopDetails_ModelClass> shopdetails;

    public DifferentShopAadapter(DifferentShopName differentShopName, ArrayList<DifferentShopDetails_ModelClass> shopDetails) {

        this.context = differentShopName;
        this.shopdetails = shopDetails;
    }

    @NonNull
    @NotNull
    @Override
    public DifferentShopAadapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.differentshopdetails,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DifferentShopAadapter.MyViewHolder holder, int position) {

        DifferentShopDetails_ModelClass shop_Details = shopdetails.get(position);

        String url = "https://"+shop_Details.getShopBanner();

        Picasso.with(context).load(url).into(holder.img_ShopeImage);

        String url1 = "https://"+shop_Details.getShopLogo();

        Picasso.with(context).load(url1).into(holder.img_ShopeLogo);

        if(shop_Details.getShopAddress().equals("")){

            holder.text_ShopAddress.setText(shop_Details.getShopAddress());
            holder.text_ShopName.setText(shop_Details.getShopName());
            holder.text_ShopAddress1.setText(shop_Details.getCity()+", "+shop_Details.getCountry()+", "+shop_Details.getState());
            holder.text_ShopAddress.setVisibility(View.GONE);

        }else{

            holder.text_ShopAddress.setText(shop_Details.getShopAddress());
            holder.text_ShopName.setText(shop_Details.getShopName());
            holder.text_ShopAddress1.setText(shop_Details.getCity()+", "+shop_Details.getCountry()+", "+shop_Details.getState());

        }

    }

    @Override
    public int getItemCount() {
        return shopdetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_ShopName,text_ShopAddress,text_ShopAddress1;
        ImageView img_ShopeImage,img_ShopeLogo;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            img_ShopeLogo = itemView.findViewById(R.id.img_ShopeLogo);
            text_ShopName = itemView.findViewById(R.id.text_ShopName);
            text_ShopAddress = itemView.findViewById(R.id.text_ShopAddress);
            text_ShopAddress1 = itemView.findViewById(R.id.text_ShopAddress1);
            img_ShopeImage = itemView.findViewById(R.id.img_ShopeImage);
        }
    }
}
