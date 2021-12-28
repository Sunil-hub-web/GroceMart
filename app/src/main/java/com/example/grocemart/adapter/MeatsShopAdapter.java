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
import com.example.grocemart.modelclass.MeatShop_Modelclass;
import com.example.grocemart.modelclass.Restaurant_ModelClass;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MeatsShopAdapter extends RecyclerView.Adapter<MeatsShopAdapter.ViewHolder> {

    Context context;
    ArrayList<MeatShop_Modelclass> meatshop;
    String cityid;

    public MeatsShopAdapter(MainActivity mainActivity, ArrayList<MeatShop_Modelclass> home_meatsShop, String cityId) {

        this.context = mainActivity;
        this.meatshop = home_meatsShop;
        this.cityid = cityId;
    }

    @NonNull
    @NotNull
    @Override
    public MeatsShopAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meatsshop,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MeatsShopAdapter.ViewHolder holder, int position) {

        MeatShop_Modelclass meatsProduct = meatshop.get(position);

        holder.text_RProductname.setText(meatsProduct.getShopName());
        //holder.text_RProductDesc.setText(restaurantProduct.getProductDesc());
        String image = "https://"+meatsProduct.getShopBanner();
        Picasso.with(context).load(image).into(holder.product_RImage);

        holder.rel_Click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ShopDetailsPage.class);
                intent.putExtra("cityId",cityid);
                intent.putExtra("shopId",meatsProduct.getShopid());
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {


        return  meatshop.size()>5?5:meatshop.size();
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
