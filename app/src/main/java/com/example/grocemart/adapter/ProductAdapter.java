package com.example.grocemart.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.grocemart.SharedPrefManager;
import com.example.grocemart.SharedPreference;
import com.example.grocemart.activity.CartPage;
import com.example.grocemart.R;
import com.example.grocemart.activity.ProductShopDetails;
import com.example.grocemart.modelclass.CartItem;
import com.example.grocemart.url.APPURLS;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    int totalPrice = 0;
    String totPrice;
    private Context context;
    private ArrayList<CartItem> allCartItem = new ArrayList<CartItem>();

    public ProductAdapter(CartPage cartPage, ArrayList<CartItem> allCartItem) {

        this.context = cartPage;
        this.allCartItem = allCartItem;
    }

    @NonNull
    @NotNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_page,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductAdapter.ViewHolder holder, int position) {

        CartItem items = allCartItem.get(position);

        String price1 = items.getSalePrice();
        String quantity1 = items.getQuantity();

        int price = Integer.parseInt(price1);
        int quantity = Integer.parseInt(quantity1);

        int Total = price * quantity;

        String price_total = String.valueOf(Total);

        String url = "https://" + items.getProductImage();

        Picasso.with(context).load(url).into(holder.imageCart);

        holder.product_Name.setText(items.getProductName());
        holder.product_Price.setText("₹ "+price_total);
        holder.shop_Name.setText(items.getShopName());
        holder.quantity.setText(items.getUnit());
        holder.t2.setText(items.getQuantity());

        holder.t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.linearLayout(false);

                String t = holder.t2.getText().toString().trim();

                String p = items.getSalePrice();

                int t1 = Integer.parseInt(t);
                int p1 = Integer.parseInt(p);

                int m =  t1 * p1 ;

                Toast.makeText(context, ""+m, Toast.LENGTH_SHORT).show();

                String m1 = String.valueOf(m);
                holder.product_Price.setText("₹ "+m1);

                totPrice = holder.product_Price.getText().toString().trim();

            }
        });

        holder.t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.linearLayout(true);
                String t = holder.t2.getText().toString().trim();

                String p = items.getSalePrice();

                int t1 = Integer.parseInt(t);
                int p1 = Integer.parseInt(p);

                int m =  t1 * p1 ;

                Toast.makeText(context, ""+m, Toast.LENGTH_SHORT).show();
                String m1 = String.valueOf(m);
                holder.product_Price.setText("₹ "+m1);

                totPrice = holder.product_Price.getText().toString().trim();

            }
        });

        String userId = SharedPrefManager.getInstance(context).getUser().getId();
        String productId = items.getProductId();
        String variationId = items.getVariationId();

        holder.img_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteCard(userId,productId,variationId);
                allCartItem.remove(position);
                notifyDataSetChanged();

            }
        });

       /* totPrice = holder.price.getText().toString().trim();
        int price1 = Integer.parseInt(totPrice);
        int count = getItemCount();
        for (int i = 0; i<count; i++)
        {
            totalPrice = totalPrice + price1;
            Log.d("totalpay",String.valueOf(totalPrice));
            return;
        }*/

    }

    /*public int getvalue(){

        return totalPrice;
    }*/

    @Override
    public int getItemCount() {
        return allCartItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageCart,img_Delete;
        TextView product_Name,shop_Name,quantity,t1, t2, t3,product_Price;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imageCart = itemView.findViewById(R.id.imageCart);
            product_Name = itemView.findViewById(R.id.product_Name);
            shop_Name = itemView.findViewById(R.id.shop_Name);
            quantity = itemView.findViewById(R.id.quantity);
            product_Price = itemView.findViewById(R.id.product_Price);
            img_Delete = itemView.findViewById(R.id.img_Delete);

            linearLayout = itemView.findViewById(R.id.inc);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
            t3 = itemView.findViewById(R.id.t3);

        }
        private void linearLayout(Boolean x){
            int y = Integer.parseInt(t2.getText().toString());
            if(x){
                y++;
                t2.setText(String.valueOf(y));
            }else {
                y--;
                if(y <= 0){
                    t2.setText("1");
                }else {
                    t2.setText(String.valueOf(y));
                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void deleteCard(String userId,String productId,String variationId){


       StringRequest stringRequest = new StringRequest(Request.Method.POST, APPURLS.deleteCartItem, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {

               try {
                   JSONObject jsonObject = new JSONObject(response);

                   String message = jsonObject.getString("success");
                   if(message.equals("true")){

                       String msg = jsonObject.getString("msg");
                       Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                   }
               } catch (JSONException e) {
                   e.printStackTrace();
               }

           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {

               error.printStackTrace();
           }
       }){
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {

               Map<String,String> params = new HashMap<>();

               params.put("user_id",userId);
               params.put("product_id",productId);
               params.put("variation_id",variationId);

               return params;
           }
       };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }



}
