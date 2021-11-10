package com.example.grocemart.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocemart.R;
import com.example.grocemart.activity.ShopDetailsPage;
import com.example.grocemart.modelclass.AllProduct_ModelClass;
import com.example.grocemart.modelclass.SubCategoryName_ModelClass;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SubcategoryNameAdapter extends RecyclerView.Adapter<SubcategoryNameAdapter.MyViewHolder> {

    Context context;
    ArrayList<SubCategoryName_ModelClass> sub_Category;
    ArrayList<AllProduct_ModelClass> allProduct = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    AllProductDetailsAdapter allProductDetailsAdapter;

    public SubcategoryNameAdapter(ShopDetailsPage shopDetailsPage, ArrayList<SubCategoryName_ModelClass> allsubCategory, ArrayList<AllProduct_ModelClass> allProductList) {

        this.context = shopDetailsPage;
        this.sub_Category = allsubCategory;
        this.allProduct = allProductList;
    }

    @NonNull
    @NotNull
    @Override
    public SubcategoryNameAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategoryname,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SubcategoryNameAdapter.MyViewHolder holder, int position) {

        SubCategoryName_ModelClass subcategory = sub_Category.get(position);

        holder.text_SubCategoryName.setText(subcategory.getSubcategoryName());

        linearLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);

        Log.d("allProduct",allProduct.toString());

        allProductDetailsAdapter = new AllProductDetailsAdapter(context,allProduct);

        holder.recycler_AllProduct.setLayoutManager(linearLayoutManager);
        holder.recycler_AllProduct.setHasFixedSize(true);
        holder.recycler_AllProduct.setAdapter(allProductDetailsAdapter);

    }

    @Override
    public int getItemCount() {
        return sub_Category.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_SubCategoryName;
        RecyclerView recycler_AllProduct;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            text_SubCategoryName = itemView.findViewById(R.id.text_SubCategoryName);
            recycler_AllProduct = itemView.findViewById(R.id.recycler_AllProduct);
        }
    }
}
