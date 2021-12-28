package com.example.grocemart.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocemart.R;
import com.example.grocemart.activity.MainActivity;
import com.example.grocemart.activity.ShopDetailsPage;
import com.example.grocemart.modelclass.GroceryShop_ModelClass;
import com.example.grocemart.modelclass.RestrurantShop_ModelClass;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RestaurantShopAdapter extends RecyclerView.Adapter<RestaurantShopAdapter.ViewHolder> {

    Context context;
    ArrayList<RestrurantShop_ModelClass> RestrurantShop;
    String cityid;

    public RestaurantShopAdapter(MainActivity mainActivity, ArrayList<RestrurantShop_ModelClass> home_restaurantShop, String cityId) {

        this.context = mainActivity;
        this.RestrurantShop = home_restaurantShop;
        this.cityid = cityId;
    }

    @NonNull
    @NotNull
    @Override
    public RestaurantShopAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurantshop,parent,false);

        return new RestaurantShopAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RestaurantShopAdapter.ViewHolder holder, int position) {

        RestrurantShop_ModelClass RestrurantProduct = RestrurantShop.get(position);

        holder.text_RProductname.setText(RestrurantProduct.getShopName());
        //holder.text_RProductDesc.setText(restaurantProduct.getProductDesc());
        String image = "https://"+RestrurantProduct.getShopBanner();
        Picasso.with(context).load(image).into(holder.product_RImage);

        holder.rel_Click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ShopDetailsPage.class);
                intent.putExtra("cityId",cityid);
                intent.putExtra("shopId",RestrurantProduct.getShopid());
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {

        return  RestrurantShop.size()>5?5:RestrurantShop.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView product_RImage;
        TextView text_RProductname,text_RProductDesc;
        Button btn_gotoshop;
        RelativeLayout rel_Click;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            product_RImage = itemView.findViewById(R.id.product_RImage);
            text_RProductname = itemView.findViewById(R.id.text_RProductname);
            //text_RProductDesc = itemView.findViewById(R.id.text_RProductDesc);
            //btn_gotoshop = itemView.findViewById(R.id.gotoshop);
            rel_Click = itemView.findViewById(R.id.rel_Click);
        }
    }
}
