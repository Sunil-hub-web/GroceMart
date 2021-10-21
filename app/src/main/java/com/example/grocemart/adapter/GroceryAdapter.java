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

import com.example.grocemart.MainActivity;
import com.example.grocemart.ProductShopDetails;
import com.example.grocemart.R;

import org.jetbrains.annotations.NotNull;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.ViewModel> {

    Context context;
    int[] imageArray;
    String[] name;

    public GroceryAdapter(MainActivity mainActivity, int[] images, String[] name) {

        this.context = mainActivity;
        this.imageArray = images;
        this.name = name;
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

        holder.imageView.setImageResource(imageArray[position]);
        holder.textView.setText(name[position]);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ProductShopDetails.class);
                intent.putExtra("message",holder.textView.getText().toString().trim());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return imageArray.length;
    }

    public class ViewModel extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        Button button;

        public ViewModel(@NonNull @NotNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.product_image);
            textView = itemView.findViewById(R.id.topProduct);
            button = itemView.findViewById(R.id.GoToShop);

        }
    }
}