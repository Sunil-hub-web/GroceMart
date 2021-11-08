package com.example.grocemart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocemart.R;
import com.example.grocemart.modelclass.Variation_ModelClass;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class VariationAdapterforProductlist extends RecyclerView.Adapter<VariationAdapterforProductlist.ViewHolder> {

    Context context;
    List<Variation_ModelClass> variation;

    public VariationAdapterforProductlist(List<Variation_ModelClass> variations, Context context) {

        this.variation = variations;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public VariationAdapterforProductlist.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_cat_item, parent, false);
        VariationAdapterforProductlist.ViewHolder holder = new VariationAdapterforProductlist.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VariationAdapterforProductlist.ViewHolder holder, int position) {

        Variation_ModelClass movie = variation.get(position);

        holder.heading.setText(movie.getUnit());

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
