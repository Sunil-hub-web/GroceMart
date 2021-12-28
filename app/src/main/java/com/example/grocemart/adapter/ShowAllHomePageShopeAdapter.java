package com.example.grocemart.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocemart.R;
import com.example.grocemart.activity.ShopDetailsPage;
import com.example.grocemart.activity.ShowAllShopDetails;
import com.example.grocemart.modelclass.GroceryShop_ModelClass;
import com.example.grocemart.modelclass.ShowAllHomeShop;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ShowAllHomePageShopeAdapter extends RecyclerView.Adapter<ShowAllHomePageShopeAdapter.ViewHolder> {

    Context context;
    ArrayList<ShowAllHomeShop> productshop;
    String cityid;

    public ShowAllHomePageShopeAdapter(ShowAllShopDetails showAllShopDetails, ArrayList<ShowAllHomeShop> showHomeShop, String cityId) {

        this.context = showAllShopDetails;
        this.productshop = showHomeShop;
        this.cityid = cityId;
    }

    @NonNull
    @NotNull
    @Override
    public ShowAllHomePageShopeAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showallproduct,parent,false);
        return new ShowAllHomePageShopeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ShowAllHomePageShopeAdapter.ViewHolder holder, int position) {

        ShowAllHomeShop shopeProduct = productshop.get(position);

        holder.text_ProductName.setText(shopeProduct.getShopName());
        //holder.text_RProductDesc.setText(restaurantProduct.getProductDesc());
        String image = "https://"+shopeProduct.getShopBanner();
        Picasso.with(context).load(image).into(holder.product_image);

        holder.clicl_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ShopDetailsPage.class);
                intent.putExtra("cityId",cityid);
                intent.putExtra("shopId",shopeProduct.getShopid());
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return productshop.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView product_image;
        TextView text_ProductName;
        RelativeLayout clicl_product;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            product_image = itemView.findViewById(R.id.product_image);
            text_ProductName = itemView.findViewById(R.id.text_ProductName);
            clicl_product = itemView.findViewById(R.id.clicl_product);
        }
    }
}
