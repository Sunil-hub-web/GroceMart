package com.example.grocemart.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.grocemart.RecyclerTouchListener;
import com.example.grocemart.SharedPrefManager;
import com.example.grocemart.activity.ProductShopDetails;
import com.example.grocemart.activity.ShopDetailsPage;
import com.example.grocemart.modelclass.ProductShop_ModelClass;
import com.example.grocemart.R;
import com.example.grocemart.modelclass.Variation_ModelClass;
import com.example.grocemart.url.APPURLS;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductShopAdapter extends RecyclerView.Adapter<ProductShopAdapter.ViewHolder> {

    Context context;
    ArrayList<ProductShop_ModelClass> product;
    String t;
    Dialog dialogMenu;
    ArrayList<Variation_ModelClass> variations;

    String userId,productId,varitionId,shopId,restt_price,varition_Id,countvalue;
    int count_value;


    public ProductShopAdapter(ProductShopDetails productShopDetails, ArrayList<ProductShop_ModelClass> itemArraylist) {

        this.context = productShopDetails;
        this.product = itemArraylist;
    }


    @NonNull
    @NotNull
    @Override
    public ProductShopAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productshopdetails, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductShopAdapter.ViewHolder holder, int position) {

        ProductShop_ModelClass productShop = product.get(position);
        Variation_ModelClass variation = productShop.getVariation().get(position);

        String url = "https://" + productShop.getProductImage();

        userId = SharedPrefManager.getInstance(context).getUser().getId();

        Picasso.with(context).load(url).into(holder.image_ProductImage);

       /* productId = productShop.getProductId();
        shopId = productShop.getShopId();*/

        holder.text_ProductNmae.setText(productShop.getProductName());
        holder.text_ShopName.setText(productShop.getShopName());
        holder.text_address.setText(productShop.getShopAddress());

        holder.text_Spinertext.setText("Select quantity");



        holder.btn_Gotoshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ShopDetailsPage.class);
                intent.putExtra("message", productShop.getShopName());
                context.startActivity(intent);
            }
        });

        holder.t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout(false);

                t = holder.t2.getText().toString().trim();
                count_value = Integer.valueOf(holder.t2.getText().toString());

                countvalue = String.valueOf(count_value);

            }
        });

        holder.t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout(true);

                t = holder.t2.getText().toString().trim();


            }
        });

        holder.text_Spinertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                variations = new ArrayList<Variation_ModelClass>();
                variations = product.get(position).getVariation();

                dialogMenu = new Dialog(context, android.R.style.Theme_Light_NoTitleBar);
                dialogMenu.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogMenu.setContentView(R.layout.variationrecycler_layout);
                dialogMenu.setCancelable(true);
                dialogMenu.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialogMenu.setCanceledOnTouchOutside(true);

                RecyclerView rv_vars = dialogMenu.findViewById(R.id.rv_vars);

                rv_vars.setLayoutManager(new LinearLayoutManager(context));
                rv_vars.setNestedScrollingEnabled(false);
                VariationAdapterforProductlist varad = new VariationAdapterforProductlist(variations, context);
                rv_vars.setAdapter(varad);

                rv_vars.addOnItemTouchListener(new RecyclerTouchListener(context, rv_vars, new RecyclerTouchListener.ClickListener() {

                    @Override
                    public void onClick(View view, int post) {

                        Log.d("gbrdsfbfbvdz", "clicked");

                        Variation_ModelClass parenting = variations.get(post);

                        product.get(position).setVariationId(parenting.getVariationId());
                        product.get(position).setSalesPrice(parenting.getSalesPrice());
                        product.get(position).setUnit(parenting.getUnit());

                        restt_price = parenting.getUnit();
                        varition_Id = parenting.getVariationId();

//                            programViewHolder.discount.setText(parenting.getWeighname());
                        holder.text_Spinertext.setText("RS " + parenting.getSalesPrice() + " " + " " + restt_price + " (" + parenting.getDiscount() + "%OFF" + ")");

                        dialogMenu.dismiss();
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }

                }));

                dialogMenu.show();

            }
        });

        holder.btn_AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                productId = productShop.getProductId();
                shopId = productShop.getShopId();
                count_value = Integer.valueOf(holder.t2.getText().toString());
                countvalue = String.valueOf(count_value);

                if(holder.text_Spinertext.getText().toString().trim().equals("Select Quantity")){

                    Toast.makeText(context, "Select Quantity", Toast.LENGTH_SHORT).show();

                }else{

                    addItemToCart(userId,productId,countvalue,varition_Id,shopId);
                }

            }
        });

        if (productShop.getVariation().size() == 0) {

            holder.text_Spinertext.setText("Select Quantity");
            varitionId = variation.getVariationId();

        } else {

            holder.text_Spinertext.setText("Select Quantity");

            product.get(position).setVariationId(productShop.getVariation().get(0).getVariationId());
            product.get(position).setUnit(productShop.getVariation().get(0).getUnit());
            product.get(position).setSalesPrice(variation.getSalesPrice());
        }
    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_ProductNmae, t1, t2, t3, text_ShopName, text_address, text_Spinertext;
        LinearLayout linearLayout;
        Button btn_AddToCart, btn_Gotoshop;
        ImageView image_ProductImage;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            btn_AddToCart = itemView.findViewById(R.id.addtocart);
            btn_Gotoshop = itemView.findViewById(R.id.btn_Gotoshop);
            linearLayout = itemView.findViewById(R.id.inc);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
            t3 = itemView.findViewById(R.id.t3);
            image_ProductImage = itemView.findViewById(R.id.productImage);
            text_ProductNmae = itemView.findViewById(R.id.productname);
            text_ShopName = itemView.findViewById(R.id.shopname);
            text_address = itemView.findViewById(R.id.address);
            text_Spinertext = itemView.findViewById(R.id.text_Spinertext);

        }

        private void linearLayout(Boolean x) {
            int y = Integer.parseInt(t2.getText().toString());
            if (x) {
                y++;
                t2.setText(String.valueOf(y));
            } else {
                y--;
                if (y <= 0) {
                    t2.setText("1");
                } else {
                    t2.setText(String.valueOf(y));
                }
            }
        }

    }


    public void addItemToCart(String userId,String productId,String quantity,String varitionsId,String ShopId){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APPURLS.addItemToCart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");
                    String message1 = jsonObject.getString("msg");

                    if(message.equals("true")){

                        Toast.makeText(context, message1, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace ();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("user_id",userId);
                params.put("product_id",productId);
                params.put("quantity",quantity);
                params.put("variation_id",varitionsId);
                params.put("shop_id",ShopId);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }
}
