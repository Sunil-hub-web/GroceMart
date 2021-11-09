package com.example.grocemart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocemart.R;
import com.example.grocemart.activity.ShopDetailsPage;
import com.example.grocemart.modelclass.CategoryName_ModelClass;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CategoryNameAdapter extends RecyclerView.Adapter<CategoryNameAdapter.ViewHolder> {

    Context context;
    ArrayList<CategoryName_ModelClass> category;

    public CategoryNameAdapter(ShopDetailsPage shopDetailsPage, ArrayList<CategoryName_ModelClass> categoryName) {

        this.context = shopDetailsPage;
        this.category = categoryName;

    }

    @NonNull
    @NotNull
    @Override
    public CategoryNameAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showcategoryname,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoryNameAdapter.ViewHolder holder, int position) {

        CategoryName_ModelClass category_name = category.get(position);

        holder.btn_CategoryName.setText(category_name.getCategoryName());

    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button btn_CategoryName;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            btn_CategoryName = itemView.findViewById(R.id.btn_CategoryName);
        }
    }
}
