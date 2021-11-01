package com.example.grocemart.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocemart.R;
import com.example.grocemart.activity.MainActivity;
import com.example.grocemart.activity.ProductShopDetails;
import com.example.grocemart.modelclass.Grocery_ModelClass;
import com.example.grocemart.modelclass.Meats_ModelClass;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MeatsAdapter extends RecyclerView.Adapter<MeatsAdapter.ViewHolder> {

    Context context;
    ArrayList<Meats_ModelClass> meats;
    String cityId;

    public MeatsAdapter(MainActivity mainActivity, ArrayList<Meats_ModelClass> home_meats, String cityId) {

        this.context = mainActivity;
        this.meats = home_meats;
        this.cityId = cityId;
    }

    @NonNull
    @NotNull
    @Override
    public MeatsAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_meats,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MeatsAdapter.ViewHolder holder, int position) {

        Meats_ModelClass meatsProduct = meats.get(position);

        holder.text_MProductname.setText(meatsProduct.getProductName());
        holder.text_MProductDesc.setText(meatsProduct.getProductDesc());
        String image = "https://"+meatsProduct.getProductImage();
        Picasso.with(context).load(image).into(holder.mproduct_Image);

        holder.btn_mgotoShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ProductShopDetails.class);
                intent.putExtra("productId",meatsProduct.getProductId());
                intent.putExtra("cityId",cityId);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return meats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mproduct_Image;
        TextView text_MProductname,text_MProductDesc;
        Button btn_mgotoShop;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            mproduct_Image = itemView.findViewById(R.id.mproduct_Image);
            text_MProductname = itemView.findViewById(R.id.text_MProductname);
            text_MProductDesc = itemView.findViewById(R.id.text_MProductDesc);
            btn_mgotoShop = itemView.findViewById(R.id.btn_mgotoShop);
        }
    }
}
