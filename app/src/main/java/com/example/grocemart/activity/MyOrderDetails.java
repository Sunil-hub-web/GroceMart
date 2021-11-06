package com.example.grocemart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.grocemart.R;
import com.example.grocemart.SharedPrefManager;
import com.example.grocemart.adapter.OrderDetailsadapter;
import com.example.grocemart.modelclass.OrderDetails_ModelClass;
import com.example.grocemart.modelclass.ProductDetails_ModelClass;
import com.example.grocemart.url.APPURLS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyOrderDetails extends AppCompatActivity {

    Button btn_Shooping;
    ImageView image_back;
    RecyclerView recyclerOrder;
    LinearLayoutManager linearLayoutManager;
    String order_id,order_status,shiping_type,shipping_charge,payment_mode,subtotal,
            total,delivery_date,timeSlot,userId,productId,productName,productQuantity,
            productImage,productPrice;

    OrderDetailsadapter orderDetailsadapter;

    ArrayList<OrderDetails_ModelClass> orderDetails = new ArrayList<>();
    ArrayList<ProductDetails_ModelClass> product = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_details);

        btn_Shooping = findViewById(R.id.continueShoping);
        image_back = findViewById(R.id.back);
        recyclerOrder = findViewById(R.id.recyclerOrder);

        btn_Shooping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MyOrderDetails.this,MainActivity.class);
                startActivity(intent);
            }
        });

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MyOrderDetails.this,UserDashboard.class);
                startActivity(intent);
            }
        });

        userId = SharedPrefManager.getInstance(MyOrderDetails.this).getUser().getId();

        orderDetails(userId);

    }


    public void orderDetails(String UserId){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APPURLS.getorderDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");

                    if(message.equals("true")){

                        String All_Orders = jsonObject.getString("All_Orders");

                        JSONArray jsonArray_OrderDetails = new JSONArray(All_Orders);

                        for(int i=0;i<jsonArray_OrderDetails.length();i++){

                            JSONObject jsonObject_OrderDetails = jsonArray_OrderDetails.getJSONObject(i);


                            order_id = jsonObject_OrderDetails.getString("order_id");
                            order_status = jsonObject_OrderDetails.getString("order_status");
                            shiping_type = jsonObject_OrderDetails.getString("shiping_type");
                            shipping_charge = jsonObject_OrderDetails.getString("shipping_charge");
                            payment_mode = jsonObject_OrderDetails.getString("payment_mode");
                            subtotal = jsonObject_OrderDetails.getString("subtotal");
                            total = jsonObject_OrderDetails.getString("total");
                            delivery_date = jsonObject_OrderDetails.getString("delivery_date");
                            timeSlot = jsonObject_OrderDetails.getString("timeSlot");

                            String Order_details = jsonObject_OrderDetails.getString("Order_details");

                            JSONArray jsonArray_order = new JSONArray(Order_details);

                            for (int j = 0;j<jsonArray_order.length();j++){

                                JSONObject jsonObject1_order = jsonArray_order.getJSONObject(j);

                                productId = jsonObject1_order.getString("id");
                                productName = jsonObject1_order.getString("name");
                                productQuantity = jsonObject1_order.getString("qty");
                                productImage = jsonObject1_order.getString("img");
                                productPrice = jsonObject1_order.getString("price");

                                ProductDetails_ModelClass productDetails_modelClass = new ProductDetails_ModelClass(
                                        productId,productName,productQuantity,productImage,productPrice
                                );

                                product.add(productDetails_modelClass);

                            }

                            OrderDetails_ModelClass orderDetails_modelClass = new OrderDetails_ModelClass(
                                    order_id,order_status,shiping_type,shipping_charge,payment_mode,
                                    subtotal,total,delivery_date,timeSlot,product
                            );


                            orderDetails.add(orderDetails_modelClass);
                        }

                        linearLayoutManager = new LinearLayoutManager(MyOrderDetails.this,LinearLayoutManager.VERTICAL,false);
                        orderDetailsadapter = new OrderDetailsadapter(MyOrderDetails.this,orderDetails);
                        recyclerOrder.setHasFixedSize(true);
                        recyclerOrder.setLayoutManager(linearLayoutManager);
                        recyclerOrder.setAdapter(orderDetailsadapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("user_id",UserId);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(MyOrderDetails.this);
        requestQueue.add(stringRequest);
    }


}