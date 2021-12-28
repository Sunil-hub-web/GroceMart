package com.example.grocemart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.example.grocemart.SharedPrefManager;
import com.example.grocemart.adapter.ShowAllHomePageProductAdapter;
import com.example.grocemart.adapter.ShowHomeProduct;
import com.example.grocemart.modelclass.Grocery_ModelClass;
import com.example.grocemart.url.APPURLS;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowAllProductDetails extends AppCompatActivity {

    TextView text_name;
    RecyclerView productRecycler;
    ImageView img_Back;

    ArrayList<ShowHomeProduct> showHomeProduct = new ArrayList<>();
    String category_id,productName,cityId;
    GridLayoutManager gridLayoutManager;
    ShowAllHomePageProductAdapter showAllHomePageProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_product_details);

        text_name = findViewById(R.id.text_name);
        productRecycler = findViewById(R.id.productRecycler);
        img_Back = findViewById(R.id.img_Back);

        Intent intent = getIntent();
        productName = intent.getStringExtra("productName");
        category_id = intent.getStringExtra("category_id");
        cityId = intent.getStringExtra("cityId");
        text_name.setText(productName);

        getProductDeatils(category_id);

        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(ShowAllProductDetails.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }

    public void getProductDeatils(String categoryId){

        ProgressDialog progressDialog = new ProgressDialog(ShowAllProductDetails.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Show Product Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, APPURLS.getSingleProduct, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");
                    String All_Product = jsonObject.getString("All_Product");

                    if(message.equals("true")){

                        //Retrive Grocery Products In Homne Page

                        JSONArray jsonArray_Grocery = new JSONArray(All_Product);

                        for (int j = 0; j < jsonArray_Grocery.length(); j++) {

                            JSONObject grocery = jsonArray_Grocery.getJSONObject(j);

                            ShowHomeProduct showHome_Product = new ShowHomeProduct(
                                    grocery.getString("product_id"),
                                    grocery.getString("product_name"),
                                    grocery.getString("product_description"),
                                    grocery.getString("img")
                            );

                            showHomeProduct.add(showHome_Product);
                        }

                    }

                    Log.d("showHomeProduct",showHomeProduct.toString());

                    gridLayoutManager = new GridLayoutManager(ShowAllProductDetails.this, 2,GridLayoutManager.VERTICAL, false);
                    showAllHomePageProductAdapter = new ShowAllHomePageProductAdapter(ShowAllProductDetails.this, showHomeProduct,cityId);
                    productRecycler.setLayoutManager(gridLayoutManager);
                    productRecycler.setHasFixedSize(true);
                    productRecycler.setAdapter(showAllHomePageProductAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();
                Toast.makeText(ShowAllProductDetails.this, "Product Not Found", Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("category_id",categoryId);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ShowAllProductDetails.this);
        requestQueue.add(stringRequest);
    }
}