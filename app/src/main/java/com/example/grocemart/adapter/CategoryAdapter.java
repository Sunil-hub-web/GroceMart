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
import com.example.grocemart.activity.MainActivity;
import com.example.grocemart.modelclass.Category_Modelcalss;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    Context context;
    ArrayList<Category_Modelcalss> category;

    public CategoryAdapter(MainActivity mainActivity, ArrayList<Category_Modelcalss> home_category) {

        this.context = mainActivity;
        this.category = home_category;
    }

    @NonNull
    @NotNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_category,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoryAdapter.ViewHolder holder, int position) {

        Category_Modelcalss categoryProduct = category.get(position);

        holder.text_categoryName.setText(categoryProduct.getCategoryName());
        String image = "https://"+categoryProduct.getCategoryImage();
        Picasso.with(context).load(image).into(holder.categoryImage);

    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_categoryName;
        ImageView categoryImage;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            text_categoryName = itemView.findViewById(R.id.text_categoryName);
            categoryImage = itemView.findViewById(R.id.categoryImage);
        }
    }
}
