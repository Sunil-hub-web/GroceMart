package com.example.grocemart.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocemart.activity.CartPage;
import com.example.grocemart.activity.ProductShopDetails;
import com.example.grocemart.modelclass.ProductShop_ModelClass;
import com.example.grocemart.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProductShopAdapter extends RecyclerView.Adapter<ProductShopAdapter.ViewHolder>  {

    String[] reason = { "Rs 180 1 kG (10%OFF)",};
    Context context;
    ArrayList<ProductShop_ModelClass> product;

    public ProductShopAdapter(ProductShopDetails productShopDetails, ArrayList<ProductShop_ModelClass> productShop) {

        this.context = productShopDetails;
        this.product = productShop;
    }

    @NonNull
    @NotNull
    @Override
    public ProductShopAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productshopdetails,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductShopAdapter.ViewHolder holder, int position) {

        ProductShop_ModelClass productmodel = product.get(position);

        holder.image_ProductImage.setImageResource(productmodel.getImage());
        holder.text_ProductNmae.setText(productmodel.getProductName());
        holder.text_address.setText(productmodel.getAddress());
        holder.text_ShopName.setText(productmodel.getShopName());

        holder.btn_AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CartPage.class);
                context.startActivity(intent);
            }
        });


        holder.t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout(false);
            }
        });

        holder.t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_ProductNmae,t1, t2, t3,text_ShopName,text_address;
        LinearLayout linearLayout;
        Button btn_AddToCart,btn_GoToShop;
        ImageView image_ProductImage;
        Spinner spiner_OFFERS;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            btn_AddToCart = itemView.findViewById(R.id.addtocart);
            btn_GoToShop = itemView.findViewById(R.id.gotoshop);
            linearLayout = itemView.findViewById(R.id.inc);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
            t3 = itemView.findViewById(R.id.t3);
            image_ProductImage = itemView.findViewById(R.id.productImage);
            text_ProductNmae = itemView.findViewById(R.id.productname);
            text_ShopName = itemView.findViewById(R.id.shopname);
            text_address = itemView.findViewById(R.id.address);
            spiner_OFFERS = itemView.findViewById(R.id.spinner_offer);

            ArrayAdapter reson_for_city = new ArrayAdapter(context,android.R.layout.simple_spinner_item,reason);
            // Drop down layout style - list view with radio button
            reson_for_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spiner_OFFERS.setAdapter(reson_for_city);
            spiner_OFFERS.setSelection(-1,true);

        }

        private void linearLayout(Boolean x){
            int y = Integer.parseInt(t2.getText().toString());
            if(x){
                y++;
                t2.setText(String.valueOf(y));
            }else {
                y--;
                if(y <= 0){
                    t2.setText("1");
                }else {
                    t2.setText(String.valueOf(y));
                }
            }
        }
    }
}
