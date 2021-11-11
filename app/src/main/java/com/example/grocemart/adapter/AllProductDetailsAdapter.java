package com.example.grocemart.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.grocemart.R;
import com.example.grocemart.RecyclerTouchListener;
import com.example.grocemart.SharedPrefManager;
import com.example.grocemart.modelclass.AllProductVariation_ModelClass;
import com.example.grocemart.modelclass.AllProduct_ModelClass;
import com.example.grocemart.modelclass.ProductShop_ModelClass;
import com.example.grocemart.modelclass.Variation_ModelClass;
import com.example.grocemart.url.APPURLS;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllProductDetailsAdapter extends RecyclerView.Adapter<AllProductDetailsAdapter.ViewHolder> {

    Context context;
    ArrayList<AllProduct_ModelClass> allProduct;
    Dialog dialogMenu;
    List<AllProductVariation_ModelClass> model_variations;
    String userId,productId,cityId,shopId,restt_price,varition_Id,countvalue,t;
    int count_value;

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

        holder.text_Spinertext.setText("Select Quantity");

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

        holder.btn_AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                productId = product.getProductId();
                shopId = product.getShopId();
                count_value = Integer.valueOf(holder.t2.getText().toString());
                countvalue = String.valueOf(count_value);
                userId = SharedPrefManager.getInstance(context).getUser().getId();

                if(holder.text_Spinertext.getText().toString().trim().equals("Select Quantity")){

                    Toast.makeText(context, "Select Quantity", Toast.LENGTH_SHORT).show();

                }else{

                    addItemToCart(userId,productId,countvalue,varition_Id,shopId);
                }

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

    }

    @Override
    public int getItemCount() {
        return allProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_Productname,text_Description,text_Spinertext,t1, t2, t3;
        ImageView img_productImage;
        Button btn_AddToCart;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            text_Productname = itemView.findViewById(R.id.text_Productname);
            text_Description = itemView.findViewById(R.id.text_Description);
            img_productImage = itemView.findViewById(R.id.img_productImage);
            text_Spinertext = itemView.findViewById(R.id.text_Spinertext);
            btn_AddToCart = itemView.findViewById(R.id.btn_AddToCart);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
            t3 = itemView.findViewById(R.id.t3);
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
