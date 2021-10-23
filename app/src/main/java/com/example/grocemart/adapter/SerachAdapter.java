package com.example.grocemart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocemart.R;
import com.example.grocemart.activity.SerachPage;
import com.example.grocemart.modelclass.Serach_ModelClass;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class SerachAdapter extends RecyclerView.Adapter<SerachAdapter.ViewHolder> implements Filterable {

    ArrayList<Serach_ModelClass> serachItem;
    ArrayList<Serach_ModelClass> serachItemShow;
    Context context;

    public SerachAdapter(SerachPage serachPage, ArrayList<Serach_ModelClass> serachProduct) {

        this.context = serachPage;
        this.serachItem = serachProduct;
        this.serachItemShow = new ArrayList<>();
        this.serachItemShow.addAll(serachItem);

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

        Serach_ModelClass ser = serachItem.get(position);

        holder.productImage.setImageResource(ser.getImage());
        holder.shopName.setText(ser.getShop());
        holder.topProduct.setText(ser.getName());
        holder.price.setText(ser.getPrice());

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
                        }else if(item.getShop().toLowerCase().contains((constraint))){
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
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            topProduct = itemView.findViewById(R.id.topProduct);
            shopName = itemView.findViewById(R.id.shopName);
            price = itemView.findViewById(R.id.price);

        }
    }
}
