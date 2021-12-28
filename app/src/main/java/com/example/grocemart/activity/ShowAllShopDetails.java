package com.example.grocemart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.grocemart.adapter.ShowAllHomePageProductAdapter;
import com.example.grocemart.adapter.ShowAllHomePageShopeAdapter;
import com.example.grocemart.adapter.ShowHomeProduct;
import com.example.grocemart.modelclass.MeatShop_Modelclass;
import com.example.grocemart.modelclass.ShowAllHomeShop;
import com.example.grocemart.url.APPURLS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowAllShopDetails extends AppCompatActivity {

    TextView text_name;
    RecyclerView productRecycler;
    ImageView img_Back;

    ArrayList<ShowAllHomeShop> showHomeShop = new ArrayList<>();
    String category_id,productName,cityId,pincodeId;
    GridLayoutManager gridLayoutManager;
    ShowAllHomePageShopeAdapter showAllHomePageShopeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_shop_details);

        text_name = findViewById(R.id.text_name);
        productRecycler = findViewById(R.id.productRecycler);
        img_Back = findViewById(R.id.img_Back);

        Intent intent = getIntent();
        productName = intent.getStringExtra("productName");
        category_id = intent.getStringExtra("category_id");
        cityId = intent.getStringExtra("cityId");
        pincodeId = intent.getStringExtra("pincodeId");
        text_name.setText(productName);

        getProductDeatils(category_id,cityId,pincodeId);

    }

    public void getProductDeatils(String categoryId,String cityid,String pincodeid){

        ProgressDialog progressDialog = new ProgressDialog(ShowAllShopDetails.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Show Product Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, APPURLS.getSingleShope, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");
                    String All_shope = jsonObject.getString("All_shope");

                    if(message.equals("true")){

                        //Retrive Grocery Products In Homne Page

                        JSONArray jsonArray_Shop = new JSONArray(All_shope);

                        for (int j = 0; j < jsonArray_Shop.length(); j++) {

                            JSONObject jsonObject_Shop = jsonArray_Shop.getJSONObject(j);

                            String shop_id = jsonObject_Shop.getString("shop_id");
                            String shop_name = jsonObject_Shop.getString("shop_name");
                            String shop_banner = jsonObject_Shop.getString("shop_banner");
                            String shop_logo = jsonObject_Shop.getString("shop_logo");

                            ShowAllHomeShop showAllShopDetails = new ShowAllHomeShop(
                                    shop_id,shop_name,shop_banner,shop_logo
                            );

                            showHomeShop.add(showAllShopDetails);
                        }

                    }

                    Log.d("showHomeProduct",showHomeShop.toString());

                    gridLayoutManager = new GridLayoutManager(ShowAllShopDetails.this, 2,GridLayoutManager.VERTICAL, false);
                    showAllHomePageShopeAdapter = new ShowAllHomePageShopeAdapter(ShowAllShopDetails.this, showHomeShop,cityId);
                    productRecycler.setLayoutManager(gridLayoutManager);
                    productRecycler.setHasFixedSize(true);
                    productRecycler.setAdapter(showAllHomePageShopeAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();
                Toast.makeText(ShowAllShopDetails.this, "Product Not Found", Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("category_id",categoryId);
                params.put("pin_id",pincodeid);
                params.put("city_id",cityid);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ShowAllShopDetails.this);
        requestQueue.add(stringRequest);
    }
}