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
import com.example.grocemart.modelclass.ProductDetails_ModelClass;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class OrderDetAdapter extends RecyclerView.Adapter<OrderDetAdapter.MyViewHolder> {

    Context context;
    ArrayList<ProductDetails_ModelClass> product;

    public OrderDetAdapter(ArrayList<ProductDetails_ModelClass> product, Context context) {

        this.context = context;
        this.product = product;
    }

    @NonNull
    @NotNull
    @Override
    public OrderDetAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderhistory,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrderDetAdapter.MyViewHolder holder, int position) {

        ProductDetails_ModelClass productdet = product.get(position);
        holder.product_Name.setText(productdet.getProductName());
        holder.quantity.setText(productdet.getProductQuantity());
        holder.product_Price.setText(productdet.getProductPrice());


    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageCart;
        TextView product_Name,quantity,product_Price;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imageCart = itemView.findViewById(R.id.imageCart);
            product_Name = itemView.findViewById(R.id.product_Name);
            quantity = itemView.findViewById(R.id.quantity);
            product_Price = itemView.findViewById(R.id.product_Price);
        }
    }
}
