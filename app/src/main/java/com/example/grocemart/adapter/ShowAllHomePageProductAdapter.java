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
import com.example.grocemart.activity.ProductShopDetails;
import com.example.grocemart.activity.ShowAllProductDetails;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ShowAllHomePageProductAdapter extends RecyclerView.Adapter<ShowAllHomePageProductAdapter.ViewHolder> {

    Context context;
    ArrayList<ShowHomeProduct> showProduct;
    String cityId;


    public ShowAllHomePageProductAdapter(ShowAllProductDetails showAllProductDetails, ArrayList<ShowHomeProduct> showHomeProduct, String cityid) {

        this.context = showAllProductDetails;
        this.showProduct = showHomeProduct;
        this.cityId = cityid;
    }

    @NonNull
    @NotNull
    @Override
    public ShowAllHomePageProductAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showallproduct,parent,false);
        return new ShowAllHomePageProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ShowAllHomePageProductAdapter.ViewHolder holder, int position) {

        ShowHomeProduct show_product = showProduct.get(position);
        holder.text_ProductName.setText(show_product.getProductName());

        //holder.text_RProductDesc.setText(restaurantProduct.getProductDesc());
        String image = "https://"+show_product.getProductImage();
        Picasso.with(context).load(image).into(holder.product_image);

        holder.clicl_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ProductShopDetails.class);
                intent.putExtra("productId",show_product.getProductId());
                intent.putExtra("cityId",cityId);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return showProduct.size();
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
