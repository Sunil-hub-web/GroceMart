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

import com.example.grocemart.activity.MainActivity;
import com.example.grocemart.activity.ProductShopDetails;
import com.example.grocemart.R;
import com.example.grocemart.modelclass.Grocery_ModelClass;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.ViewModel> {

    Context context;
    ArrayList<Grocery_ModelClass> grocery;
    String cityId;

    public GroceryAdapter(MainActivity mainActivity, ArrayList<Grocery_ModelClass> home_grocery, String cityId) {

        context = mainActivity;
        grocery = home_grocery;
        this.cityId = cityId;
    }


    @NonNull
    @NotNull
    @Override
    public GroceryAdapter.ViewModel onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_grocery,parent,false);
        return new ViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GroceryAdapter.ViewModel holder, int position) {

        Grocery_ModelClass groceryProduct = grocery.get(position);

        holder.txt_GproductName.setText(groceryProduct.getProductName());
        holder.txt_GproductDesc.setText(groceryProduct.getProductDesc());
        String image = "https://"+groceryProduct.getProductImage();
        Picasso.with(context).load(image).into(holder.gproduct_Image);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ProductShopDetails.class);
                intent.putExtra("productId",groceryProduct.getProductId());
                intent.putExtra("cityId",cityId);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return grocery.size();
    }

    public class ViewModel extends RecyclerView.ViewHolder {

        ImageView gproduct_Image;
        TextView txt_GproductName,txt_GproductDesc;
        Button button;

        public ViewModel(@NonNull @NotNull View itemView) {
            super(itemView);

            gproduct_Image = itemView.findViewById(R.id.gproduct_Image);
            txt_GproductName = itemView.findViewById(R.id.txt_GproductName);
            txt_GproductDesc = itemView.findViewById(R.id.txt_GproductDesc);
            button = itemView.findViewById(R.id.GoToShop);

        }
    }
}
