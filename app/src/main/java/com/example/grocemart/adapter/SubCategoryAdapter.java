package com.example.grocemart.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocemart.R;
import com.example.grocemart.activity.DifferentShopName;
import com.example.grocemart.activity.MainActivity;
import com.example.grocemart.modelclass.SubCategory_ModelClass;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewModel> {

    Context context;
    ArrayList<SubCategory_ModelClass> subcategory;
    String cityId;


    public SubCategoryAdapter(MainActivity mainActivity, ArrayList<SubCategory_ModelClass> sub_category, String cityId) {

        this.context = mainActivity;
        this.subcategory = sub_category;
        this.cityId = cityId;
    }

    @NonNull
    @NotNull
    @Override
    public SubCategoryAdapter.ViewModel onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategory,parent,false);
        return new ViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SubCategoryAdapter.ViewModel holder, int position) {

        SubCategory_ModelClass sub_category = subcategory.get(position);

        holder.text_Subcategory.setText(sub_category.getSubcategoryName());

        holder.text_Subcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DifferentShopName.class);
                intent.putExtra("sucategoryId",sub_category.getSubcategoryID());
                intent.putExtra("cityId",cityId);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return subcategory.size();
    }

    public class ViewModel extends RecyclerView.ViewHolder {

        TextView text_Subcategory;

        public ViewModel(@NonNull @NotNull View itemView) {
            super(itemView);

            text_Subcategory = itemView.findViewById(R.id.text_Subcategory);
        }
    }
}
