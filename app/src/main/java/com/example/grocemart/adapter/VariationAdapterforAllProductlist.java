package com.example.grocemart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocemart.R;
import com.example.grocemart.modelclass.AllProductVariation_ModelClass;
import com.example.grocemart.modelclass.Variation_ModelClass;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VariationAdapterforAllProductlist extends RecyclerView.Adapter<VariationAdapterforAllProductlist.ViewHolder> {

    Context context;
    List<AllProductVariation_ModelClass> variation;

    public VariationAdapterforAllProductlist(List<AllProductVariation_ModelClass> model_variations, Context context) {

        this.variation = model_variations;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public VariationAdapterforAllProductlist.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_cat_item, parent, false);
        VariationAdapterforAllProductlist.ViewHolder holder = new VariationAdapterforAllProductlist.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VariationAdapterforAllProductlist.ViewHolder holder, int position) {

        AllProductVariation_ModelClass movie = variation.get(position);

        holder.heading.setText(movie.getVariation_unit());


    }

    @Override
    public int getItemCount() {
        return variation.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView heading;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            heading = (TextView) itemView.findViewById(R.id.heading);
        }
    }
}
