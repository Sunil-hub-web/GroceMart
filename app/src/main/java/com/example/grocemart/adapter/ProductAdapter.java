package com.example.grocemart.adapter;

import android.content.Context;
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
import com.example.grocemart.activity.CartPage;
import com.example.grocemart.R;
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

    String totPrice;
    private Context context;
    private ArrayList<CartItem> allCartItem = new ArrayList<CartItem>();
    int Total,price,quantity,arraySize;
    String price_total,sum,str_SumTotal,str_Shpping,str_TotalPrice,str_DeletePrice;
    String userid,productId,variationId,str_ArraySize;
    Double sumTotal,d_totPrice,d_Sum,d_sumTotal,d_ShppingCharges,d_TotlAmount,d_deletePrice;


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

        userid = SharedPrefManager.getInstance(context).getUser().getId();

        String price1 = items.getSalePrice();
        String quantity1 = items.getQuantity();

        price = Integer.parseInt(price1);
        quantity = Integer.parseInt(quantity1);

        Total = price * quantity;

        price_total = String.valueOf(Total);

        String url = "https://" + items.getProductImage();

        Picasso.with(context).load(url).into(holder.imageCart);

        holder.product_Name.setText(items.getProductName());
        holder.product_Price.setText(price_total);
        holder.shop_Name.setText(items.getShopName());
        holder.quantity.setText(items.getUnit());
        holder.t2.setText(items.getQuantity());

        holder.t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.linearLayout(false);

                String t = holder.t2.getText().toString().trim();
                productId = items.getProductId();
                variationId = items.getVariationId();

                String p = items.getSalePrice();

                int t1 = Integer.parseInt(t);
                int p1 = Integer.parseInt(p);

                int m =  t1 * p1 ;

                Toast.makeText(context, ""+m, Toast.LENGTH_SHORT).show();

                String m1 = String.valueOf(m);
                holder.product_Price.setText(m1);

                totPrice = holder.product_Price.getText().toString().trim();

                price = Integer.parseInt(price1);

                sum = CartPage.subTotalPrice.getText().toString().trim();

                d_totPrice = Double.valueOf(price);
                d_Sum = Double.valueOf(sum);

                sumTotal = d_Sum - d_totPrice;
                str_SumTotal = String.valueOf(sumTotal);

                CartPage.subTotalPrice.setText(str_SumTotal);

                str_Shpping = CartPage.shippingCharges.getText().toString().trim();

                d_ShppingCharges = Double.valueOf(str_Shpping);

                d_TotlAmount  = sumTotal + d_ShppingCharges;

                str_TotalPrice = String.valueOf(d_TotlAmount);

                CartPage.text_totalAmount.setText(str_TotalPrice);


                updateCart(userid,productId,t,variationId);



            }
        });

        holder.t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.linearLayout(true);
                String t = holder.t2.getText().toString().trim();

                productId = items.getProductId();
                variationId = items.getVariationId();

                String p = items.getSalePrice();

                int t1 = Integer.parseInt(t);
                int p1 = Integer.parseInt(p);

                int m =  t1 * p1 ;

                Toast.makeText(context, ""+m, Toast.LENGTH_SHORT).show();
                String m1 = String.valueOf(m);
                holder.product_Price.setText(m1);

                totPrice = holder.product_Price.getText().toString().trim();

                price = Integer.parseInt(price1);

                sum = CartPage.subTotalPrice.getText().toString().trim();

                d_totPrice = Double.valueOf(price);
                d_Sum = Double.valueOf(sum);

                sumTotal = d_totPrice + d_Sum;
                str_SumTotal = String.valueOf(sumTotal);

                CartPage.subTotalPrice.setText(str_SumTotal);

                str_Shpping = CartPage.shippingCharges.getText().toString().trim();

                d_ShppingCharges = Double.valueOf(str_Shpping);

                d_TotlAmount  = sumTotal + d_ShppingCharges;

                str_TotalPrice = String.valueOf(d_TotlAmount);

                CartPage.text_totalAmount.setText(str_TotalPrice);

                updateCart(userid,productId,t,variationId);

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
                arraySize = allCartItem.size();
                str_ArraySize = String.valueOf(arraySize);
                CartPage.text_ItemCount.setText(str_ArraySize);

                sum = CartPage.subTotalPrice.getText().toString().trim();

                totPrice = holder.product_Price.getText().toString().trim();

                d_sumTotal = Double.valueOf(totPrice);
                d_Sum = Double.valueOf(sum);

                d_deletePrice = d_Sum - d_sumTotal;

                str_DeletePrice = String.valueOf(d_deletePrice);

                CartPage.subTotalPrice.setText(str_DeletePrice);

                str_Shpping = CartPage.shippingCharges.getText().toString().trim();

                d_ShppingCharges = Double.valueOf(str_Shpping);

                d_TotlAmount  = d_deletePrice + d_ShppingCharges;

                str_TotalPrice = String.valueOf(d_TotlAmount);

                CartPage.text_totalAmount.setText(str_TotalPrice);

                CartPage.text_totalAmount.setText(str_TotalPrice);

                if(allCartItem.size() == 0){

                    CartPage.text_ItemCount.setVisibility(View.GONE);

                    CartPage.rel_MoneyTotal.setVisibility(View.GONE);
                    CartPage.rel_Total.setVisibility(View.GONE);
                    CartPage.img_Cart.setVisibility(View.GONE);
                    CartPage.image_NoResult.setVisibility(View.VISIBLE);
                }

            }
        });

    }

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


    public void updateCart(String user_id,String product_id,String quantity,String variation_id){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APPURLS.updateCartItem, new Response.Listener<String>() {
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

                params.put("user_id",user_id);
                params.put("product_id",product_id);
                params.put("quantity",quantity);
                params.put("variation_id",variation_id);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);


    }

}
