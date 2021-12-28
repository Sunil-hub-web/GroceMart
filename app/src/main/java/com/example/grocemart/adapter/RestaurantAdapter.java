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

import com.example.grocemart.activity.MainActivity;
import com.example.grocemart.activity.ProductShopDetails;
import com.example.grocemart.R;
import com.example.grocemart.modelclass.Grocery_ModelClass;
import com.example.grocemart.modelclass.Restaurant_ModelClass;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.Viewholdert> {

    Context context;
    ArrayList<Restaurant_ModelClass> restaurant;
    String cityId;

    public RestaurantAdapter(MainActivity mainActivity, ArrayList<Restaurant_ModelClass> home_restaurant, String cityId) {

        this.context = mainActivity;
        this.restaurant = home_restaurant;
        this.cityId = cityId;
    }

    @NonNull
    @NotNull
    @Override
    public RestaurantAdapter.Viewholdert onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_restaurant,parent,false);
        return new Viewholdert(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RestaurantAdapter.Viewholdert holder, int position) {

        Restaurant_ModelClass restaurantProduct = restaurant.get(position);

        holder.text_RProductname.setText(restaurantProduct.getProductName());
        //holder.text_RProductDesc.setText(restaurantProduct.getProductDesc());
        String image = "https://"+restaurantProduct.getProductImage();
        Picasso.with(context).load(image).into(holder.product_RImage);

        holder.rel_Click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ProductShopDetails.class);
                intent.putExtra("productId",restaurantProduct.getProductId());
                intent.putExtra("cityId",cityId);
                context.startActivity(intent);
            }
        });

       /* holder.btn_gotoshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });*/


    }

    @Override
    public int getItemCount() {

        return  restaurant.size()>5?5:restaurant.size();
    }

    public class Viewholdert extends RecyclerView.ViewHolder {

        ImageView product_RImage;
        TextView text_RProductname,text_RProductDesc;
        Button btn_gotoshop;
        RelativeLayout rel_Click;

        public Viewholdert(@NonNull @NotNull View itemView) {
            super(itemView);

            product_RImage = itemView.findViewById(R.id.product_RImage);
            text_RProductname = itemView.findViewById(R.id.text_RProductname);
            //text_RProductDesc = itemView.findViewById(R.id.text_RProductDesc);
            //btn_gotoshop = itemView.findViewById(R.id.gotoshop);
            rel_Click = itemView.findViewById(R.id.rel_Click);

        }
    }
}
