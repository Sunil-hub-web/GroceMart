package com.example.grocemart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.grocemart.R;
import com.example.grocemart.adapter.CategoryNameAdapter;
import com.example.grocemart.modelclass.AllShopDetails_ModelClass;
import com.example.grocemart.modelclass.CategoryName_ModelClass;
import com.example.grocemart.url.APPURLS;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopDetailsPage extends AppCompatActivity {

   RecyclerView recycler_CategoryName;
   GridLayoutManager gridLayoutManager;
   CategoryNameAdapter categoryNameAdapter;
   TextView shopName,ShopAddress;

   ArrayList<AllShopDetails_ModelClass> allShop;
   ArrayList<CategoryName_ModelClass> categoryName;
   private static final String TAG = "ShopDetailsPage";
   String cityId,shop_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details_page);

        recycler_CategoryName = findViewById(R.id.recycler_CategoryName);
        shopName = findViewById(R.id.shopName);
        ShopAddress = findViewById(R.id.ShopAddress);

        Intent intent = getIntent();

        cityId = intent.getStringExtra("cityId");
        shop_Id = intent.getStringExtra("shopId");

        getShopDetails(cityId,shop_Id);

    }

    public void getShopDetails(String city_Id,String shop_Id){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, APPURLS.getSingleShopDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");

                    if(message.equals("true")){

                        String shop_Name = jsonObject.getString("shop_name");
                        shopName.setText(shop_Name);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                Log.d("Ranjeet_error",e.toString());
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

                Map<String,String> param = new HashMap<>();

                param.put("city_id",city_Id);
                param.put("shop_id",shop_Id);

                return param;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,5,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ShopDetailsPage.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }
}