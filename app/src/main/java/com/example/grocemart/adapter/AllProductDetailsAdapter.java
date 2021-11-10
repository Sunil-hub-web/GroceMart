package com.example.grocemart.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocemart.R;
import com.example.grocemart.RecyclerTouchListener;
import com.example.grocemart.SharedPrefManager;
import com.example.grocemart.modelclass.AllProductVariation_ModelClass;
import com.example.grocemart.modelclass.AllProduct_ModelClass;
import com.example.grocemart.modelclass.ProductShop_ModelClass;
import com.example.grocemart.modelclass.Variation_ModelClass;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AllProductDetailsAdapter extends RecyclerView.Adapter<AllProductDetailsAdapter.ViewHolder> {

    Context context;
    ArrayList<AllProduct_ModelClass> allProduct;
    Dialog dialogMenu;
    List<AllProductVariation_ModelClass> model_variations;
    String userId,productId,cityId,shopId,restt_price,varition_Id,countvalue;

    public AllProductDetailsAdapter(Context context, ArrayList<AllProduct_ModelClass> allProduct) {

        this.context = context;
        this.allProduct = allProduct;
    }

    @NonNull
    @NotNull
    @Override
    public AllProductDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allproductdetails,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AllProductDetailsAdapter.ViewHolder holder, int position) {

        Log.d("allProduct1",allProduct.toString());

        AllProduct_ModelClass product = allProduct.get(position);

        String url = "https://" + product.getProductImage();

        //userId = SharedPrefManager.getInstance(context).getUser().getId();

        Picasso.with(context).load(url).into(holder.img_productImage);

        holder.text_Productname.setText(product.getProductName());
        holder.text_Description.setText(product.getProductDesc());

        holder.text_Spinertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                model_variations = new ArrayList<AllProductVariation_ModelClass>();
                model_variations = product.getVariation();


                dialogMenu = new Dialog(context, android.R.style.Theme_Light_NoTitleBar);
                dialogMenu.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogMenu.setContentView(R.layout.variationrecycler_layout);
                dialogMenu.setCancelable(true);
                dialogMenu.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialogMenu.setCanceledOnTouchOutside(true);

                RecyclerView rv_vars = dialogMenu.findViewById(R.id.rv_vars);

                rv_vars.setLayoutManager(new LinearLayoutManager(context));
                rv_vars.setNestedScrollingEnabled(false);
                VariationAdapterforAllProductlist varad = new VariationAdapterforAllProductlist(model_variations, context);
                rv_vars.setAdapter(varad);

                rv_vars.addOnItemTouchListener(new RecyclerTouchListener(context, rv_vars, new RecyclerTouchListener.ClickListener() {

                    @Override
                    public void onClick(View view, int post) {

                        Log.d("gbrdsfbfbvdz", "clicked");

                        AllProductVariation_ModelClass parenting = model_variations.get(post);

                        allProduct.get(position).setVariationId(parenting.getVariation_Id());
                        allProduct.get(position).setSalesPrice(parenting.getVariation_salesPrice());
                        allProduct.get(position).setUnit(parenting.getVariation_unit());

                        restt_price = parenting.getVariation_unit();
                        varition_Id = parenting.getVariation_Id();

                        holder.text_Spinertext.setText("RS " + parenting.getVariation_salesPrice() + " " + " " + restt_price + " (" + parenting.getVariation_discount() + "%OFF" + ")");

                        dialogMenu.dismiss();
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }

                }));

                dialogMenu.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return allProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_Productname,text_Description,text_Spinertext;
        ImageView img_productImage;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            text_Productname = itemView.findViewById(R.id.text_Productname);
            text_Description = itemView.findViewById(R.id.text_Description);
            img_productImage = itemView.findViewById(R.id.img_productImage);
            text_Spinertext = itemView.findViewById(R.id.text_Spinertext);
        }
    }
}
