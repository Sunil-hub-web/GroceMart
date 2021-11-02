package com.example.grocemart.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocemart.RecyclerTouchListener;
import com.example.grocemart.activity.CartPage;
import com.example.grocemart.activity.ProductShopDetails;
import com.example.grocemart.activity.ShopDetailsPage;
import com.example.grocemart.activity.UserProfile;
import com.example.grocemart.modelclass.ProductShop_ModelClass;
import com.example.grocemart.R;
import com.example.grocemart.modelclass.Variation_ModelClass;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProductShopAdapter extends RecyclerView.Adapter<ProductShopAdapter.ViewHolder>  {

    Context context;
    ArrayList<ProductShop_ModelClass> product;
    String t;
    Dialog dialogMenu;
    ArrayList<Variation_ModelClass> variations;

    public ProductShopAdapter(ProductShopDetails productShopDetails, ArrayList<ProductShop_ModelClass> itemArraylist) {

        this.context = productShopDetails;
        this.product = itemArraylist;
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

        ProductShop_ModelClass productShop = product.get(position);
        Variation_ModelClass variation = productShop.getVariation().get(position);

        String url = "https://"+productShop.getProductImage();

        Picasso.with(context).load(url).into(holder.image_ProductImage);

        holder.text_ProductNmae.setText(productShop.getProductName());
        holder.text_ShopName.setText(productShop.getShopName());
        holder.text_address.setText(productShop.getShopAddress());

        holder.btn_AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        holder.btn_Gotoshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ShopDetailsPage.class);
                intent.putExtra("message",productShop.getShopName());
                context.startActivity(intent);
            }
        });

        holder.t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout(false);

                 t = holder.t2.getText().toString().trim();

            }
        });

        holder.t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout(true);

                t = holder.t2.getText().toString().trim();
            }
        });

        holder.text_Spinertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                variations = new ArrayList<Variation_ModelClass>();
                variations = product.get(position).getVariation();

                dialogMenu = new Dialog(context, android.R.style.Theme_Light_NoTitleBar);
                dialogMenu.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogMenu.setContentView(R.layout.variationrecycler_layout);
                dialogMenu.setCancelable(true);
                dialogMenu.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialogMenu.setCanceledOnTouchOutside(true);

                RecyclerView rv_vars = dialogMenu.findViewById(R.id.rv_vars);

                rv_vars.setLayoutManager(new LinearLayoutManager(context));
                rv_vars.setNestedScrollingEnabled(false);
                VariationAdapterforProductlist varad = new VariationAdapterforProductlist(variations, context);
                rv_vars.setAdapter(varad);

                rv_vars.addOnItemTouchListener(new RecyclerTouchListener(context, rv_vars, new RecyclerTouchListener.ClickListener() {

                    @Override
                    public void onClick(View view, int post) {

                        Log.d("gbrdsfbfbvdz", "clicked");

                        Variation_ModelClass parenting = variations.get(post);

                        product.get(position).setVariationId(parenting.getVariationId());
                        product.get(position).setSalesPrice(parenting.getSalesPrice());
                        product.get(position).setUnit(parenting.getUnit());

                        String restt_price = parenting.getUnit();

//                            programViewHolder.discount.setText(parenting.getWeighname());
                        holder.text_Spinertext.setText("RS " +parenting.getSalesPrice()+" "+" "+restt_price+" ("+parenting.getDiscount()+"%OFF"+")");

                        dialogMenu.dismiss();
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }

                }));

                dialogMenu.show();

            }
        });

        if(productShop.getVariation().size()==0){
            holder.text_Spinertext.setText("RS " +variation.getSalesPrice()+" "+" "+variation.getUnit()+" ("+variation.getDiscount()+"%OFF"+")");
        }else{

            holder.text_Spinertext.setText("RS " +variation.getMrpPrice()+" "+variation.getUnit());

            product.get(position).setVariationId(productShop.getVariation().get(0).getVariationId());
            product.get(position).setUnit(productShop.getVariation().get(0).getUnit());
            product.get(position).setSalesPrice(productShop.getVariation().get(0).getSalesPrice());
        }
    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_ProductNmae,t1, t2, t3,text_ShopName,text_address,text_Spinertext;
        LinearLayout linearLayout;
        Button btn_AddToCart,btn_Gotoshop;
        ImageView image_ProductImage;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            btn_AddToCart = itemView.findViewById(R.id.addtocart);
            btn_Gotoshop = itemView.findViewById(R.id.btn_Gotoshop);
            linearLayout = itemView.findViewById(R.id.inc);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
            t3 = itemView.findViewById(R.id.t3);
            image_ProductImage = itemView.findViewById(R.id.productImage);
            text_ProductNmae = itemView.findViewById(R.id.productname);
            text_ShopName = itemView.findViewById(R.id.shopname);
            text_address = itemView.findViewById(R.id.address);
            text_Spinertext = itemView.findViewById(R.id.text_Spinertext);

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
