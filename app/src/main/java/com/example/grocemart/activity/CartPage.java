package com.example.grocemart.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.grocemart.adapter.ProductAdapter;
import com.example.grocemart.adapter.ShappingAdapter;
import com.example.grocemart.adapter.VariationAdapterforProductlist;
import com.example.grocemart.modelclass.CartItem;
import com.example.grocemart.modelclass.ShappingCharges_ModelClass;
import com.example.grocemart.modelclass.Variation_ModelClass;
import com.example.grocemart.url.APPURLS;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class CartPage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ProductAdapter productAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Button btn_CheckOut,btn_ApplyCopun;
    public static TextView subTotalPrice, shippingCharges, coupanCode, text_totalAmount, text_Shapping,text_ItemCount;
    int value;
    ArrayList<CartItem> allCartItem = new ArrayList<CartItem>();

    ArrayList<ShappingCharges_ModelClass> ShippingCharges = new ArrayList<ShappingCharges_ModelClass>();
    public static ImageView image_NoResult,img_Cart;
    public String productId, variationId, shopId, productImage, productName, shopName, unit,
            salePrice, discount, quantity, userId, str_SubTotalPrice, str_ShippingCharges, str_TotalAmount;

    double totalprice, sales_Price, quanTity, totalAmount = 0, shipCharge;

    ShappingAdapter shappingAdapter;
    Dialog dialogMenu;
    RecyclerView rv_vars;
    EditText edit_coupanCode;

    public static RelativeLayout rel_MoneyTotal, rel_Total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);

        userId = SharedPrefManager.getInstance(CartPage.this).getUser().getId();

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        recyclerView = findViewById(R.id.recycler);
        btn_CheckOut = findViewById(R.id.checkOut);
        subTotalPrice = findViewById(R.id.subTotalPrice);
        image_NoResult = findViewById(R.id.image_NoResult);
        img_Cart = findViewById(R.id.img_Cart);
        rel_MoneyTotal = findViewById(R.id.rel_MoneyTotal);
        rel_Total = findViewById(R.id.rel_Total);
        shippingCharges = findViewById(R.id.shippingCharges);
        text_Shapping = findViewById(R.id.text_Shapping);
        coupanCode = findViewById(R.id.coupanCode);
        text_totalAmount = findViewById(R.id.totalAmount);
        btn_ApplyCopun = findViewById(R.id.ApplyCopun);
        edit_coupanCode = findViewById(R.id.coupanCode);
        text_ItemCount = findViewById(R.id.text_ItemCount);

        bottomNavigationView.setSelectedItemId(R.id.cart);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.cart:
                        return true;

                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), SerachPage.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });

        btn_CheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (text_Shapping.getText().toString().trim().equals("Select")) {

                    Toast.makeText(CartPage.this, "Select Charges", Toast.LENGTH_SHORT).show();

                } else {

                    str_SubTotalPrice = subTotalPrice.getText().toString().trim();
                    str_ShippingCharges = shippingCharges.getText().toString().trim();
                    str_TotalAmount = text_totalAmount.getText().toString().trim();

                    Intent intent = new Intent(CartPage.this, CheckoutPage.class);
                    intent.putExtra("subTotalPrice", subTotalPrice.getText().toString().trim());
                    intent.putExtra("shippingCharges", shippingCharges.getText().toString().trim());
                    intent.putExtra("totalAmount", text_totalAmount.getText().toString().trim());
                    intent.putExtra("ShappingName", text_Shapping.getText().toString().trim());
                    startActivity(intent);
                }

            }
        });

        getCartItem(userId);

        getShapping();

        text_Shapping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogMenu = new Dialog(CartPage.this, android.R.style.Theme_Light_NoTitleBar);
                dialogMenu.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogMenu.setContentView(R.layout.shapping_layout);
                dialogMenu.setCancelable(true);
                dialogMenu.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialogMenu.setCanceledOnTouchOutside(true);

                rv_vars = dialogMenu.findViewById(R.id.rv_vars);

                rv_vars.setLayoutManager(new LinearLayoutManager(CartPage.this));
                rv_vars.setNestedScrollingEnabled(false);
                ShappingAdapter varad = new ShappingAdapter(ShippingCharges, CartPage.this);
                rv_vars.setAdapter(varad);

                rv_vars.addOnItemTouchListener(new RecyclerTouchListener(CartPage.this, rv_vars, new RecyclerTouchListener.ClickListener() {

                    @Override
                    public void onClick(View view, int post) {

                        Log.d("gbrdsfbfbvdz", "clicked");

                        ShappingCharges_ModelClass parenting = ShippingCharges.get(post);

                        String shippingPrice = parenting.getPrice();
                        String shippingName = parenting.getShappingName();

                        text_Shapping.setText(shippingName);
                        shippingCharges.setText(shippingPrice);

                        shipCharge = Double.valueOf(shippingPrice);

                        Double AmountTotal = totalAmount + shipCharge;

                        String tot_Amount = String.valueOf(AmountTotal);

                        text_totalAmount.setText(tot_Amount);

                        dialogMenu.dismiss();
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }

                }));

                dialogMenu.show();

            }
        });

        btn_ApplyCopun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = edit_coupanCode.getText().toString().trim();
                String totAmount = subTotalPrice.getText().toString().trim();

                applyCupon(code,totAmount);

            }
        });

    }

    public void getCartItem(String userId) {

        ProgressDialog progressDialog = new ProgressDialog(CartPage.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APPURLS.getCartItem, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");

                    if (message.equals("true")) {

                        String allcart = jsonObject.getString("All_Cart");

                        JSONArray jsonArray_AllCart = new JSONArray(allcart);

                        for (int i = 0; i < jsonArray_AllCart.length(); i++) {


                            JSONObject jsonObject_allCart = jsonArray_AllCart.getJSONObject(i);

                            productId = jsonObject_allCart.getString("product_id");
                            variationId = jsonObject_allCart.getString("variation_id");
                            shopId = jsonObject_allCart.getString("shop_id");
                            productImage = jsonObject_allCart.getString("product_img");
                            productName = jsonObject_allCart.getString("product_name");
                            shopName = jsonObject_allCart.getString("shop_name");
                            unit = jsonObject_allCart.getString("unit");
                            salePrice = jsonObject_allCart.getString("sale_price");
                            discount = jsonObject_allCart.getString("discount");
                            quantity = jsonObject_allCart.getString("quantity");

                            CartItem cartItem = new CartItem(
                                    productId, variationId, shopId, productImage, productName, shopName,
                                    unit, salePrice, discount, quantity, salePrice
                            );

                            sales_Price = Double.valueOf(salePrice);

                            quanTity = Double.valueOf(quantity);

                            totalprice = sales_Price * quanTity;

                            totalAmount = totalAmount + totalprice;

                            allCartItem.add(cartItem);

                        }

                        String total_price = String.valueOf(totalAmount);

                        subTotalPrice.setText(total_price);

                        text_totalAmount.setText(total_price);


                        if (allCartItem.size() == 0) {

                            rel_MoneyTotal.setVisibility(View.GONE);
                            rel_Total.setVisibility(View.GONE);
                            img_Cart.setVisibility(View.GONE);
                            text_ItemCount.setVisibility(View.GONE);
                            image_NoResult.setVisibility(View.VISIBLE);

                        } else {

                            int size =  allCartItem.size();

                            String str_size = String.valueOf(size);

                            text_ItemCount.setText(str_size);

                            rel_MoneyTotal.setVisibility(View.VISIBLE);
                            rel_Total.setVisibility(View.VISIBLE);
                            text_ItemCount.setVisibility(View.VISIBLE);
                            image_NoResult.setVisibility(View.GONE);

                            linearLayoutManager = new LinearLayoutManager(CartPage.this, LinearLayoutManager.VERTICAL, false);
                            productAdapter = new ProductAdapter(CartPage.this, allCartItem);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(productAdapter);


                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("user_id", userId);

                return params;
            }
        };

        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(CartPage.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    public void getShapping(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APPURLS.getShappingCharges, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");

                    if (message.equals("true")) {

                        String All_shipping_chr = jsonObject.getString("All_shipping_chr");

                        JSONArray jsonArray_Shipping = new JSONArray(All_shipping_chr);

                        for (int i = 0; i < jsonArray_Shipping.length(); i++) {

                            JSONObject jsonObject_Shipping = jsonArray_Shipping.getJSONObject(i);

                            String shipping_id = jsonObject_Shipping.getString("shipping_id");
                            String price = jsonObject_Shipping.getString("price");
                            String delivery_price = jsonObject_Shipping.getString("delivery_price");
                            String name = jsonObject_Shipping.getString("name");

                            ShappingCharges_ModelClass shappingCharges_modelClass = new ShappingCharges_ModelClass(shipping_id,
                                    price, delivery_price, name
                            );

                            ShippingCharges.add(shappingCharges_modelClass);

                        }

                        Log.d("ShippingCharges", ShippingCharges.toString());

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
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(CartPage.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    public void applyCupon(String cuponCode, String subTotal) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APPURLS.applyCupon, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");

                    if(message.equals("true")){

                        String cupon_appaly = jsonObject.getString("cupon_appay");

                        Toast.makeText(CartPage.this, cupon_appaly, Toast.LENGTH_SHORT).show();

                        String cupon_discount_amount = jsonObject.getString("cupon_discount_amount");
                        String grand_total = jsonObject.getString("grand_total");


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("coupon_code",cuponCode);
                params.put("sub_total",subTotal);
                return super.getParams();
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(CartPage.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(CartPage.this, MainActivity.class);
        startActivity(intent);
    }
}