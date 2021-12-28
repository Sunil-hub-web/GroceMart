package com.example.grocemart.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocemart.R;
import com.example.grocemart.activity.SerachPage;
import com.example.grocemart.activity.ShopDetailsPage;
import com.example.grocemart.modelclass.GroceryShop_ModelClass;
import com.example.grocemart.modelclass.Serach_ModelClass;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class SerachAdapter extends RecyclerView.Adapter<SerachAdapter.ViewHolder> implements Filterable {

    ArrayList<Serach_ModelClass> serachItem;
    ArrayList<Serach_ModelClass> serachItemShow;
    Context context;
    String cityid;

    public SerachAdapter(SerachPage serachPage, ArrayList<Serach_ModelClass> serachProduct, String cityId) {

        this.context = serachPage;
        this.serachItem = serachProduct;
        this.serachItemShow = new ArrayList<>();
        this.serachItemShow.addAll(serachItem);
        this.cityid = cityId;

    }

    @NonNull
    @NotNull
    @Override
    public SerachAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.serachpage,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SerachAdapter.ViewHolder holder, int position) {

        Serach_ModelClass serachProduct = serachItem.get(position);


        holder.shopName.setText(serachProduct.getName());
        //holder.text_RProductDesc.setText(restaurantProduct.getProductDesc());
        String image = "https://"+serachProduct.getImage();
        Picasso.with(context).load(image).into(holder.productImage);

        holder.clicl_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ShopDetailsPage.class);
                intent.putExtra("cityId",cityid);
                intent.putExtra("shopId",serachProduct.getId());
                context.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return serachItem.size();
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {


                ArrayList<Serach_ModelClass> resultData = new ArrayList<>();

                if(constraint.toString().isEmpty()){

                    resultData.addAll(serachItemShow);

                }else{

                    for(Serach_ModelClass item : serachItemShow){

                        if(item.getName().toLowerCase().contains((constraint))){
                            resultData.add(item);
                        }else if(item.getName().toLowerCase().contains((constraint))){
                            resultData.add(item);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.count = resultData.size();
                filterResults.values = resultData;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                serachItem.clear();
                serachItem.addAll((Collection<? extends Serach_ModelClass>) results.values);
                notifyDataSetChanged();

            }
        };

        return filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView topProduct,shopName,price;
        RelativeLayout clicl_product;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            shopName = itemView.findViewById(R.id.shopName);
            clicl_product = itemView.findViewById(R.id.clicl_product);
            //price = itemView.findViewById(R.id.price);

        }
    }
}
