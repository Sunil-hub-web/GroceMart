package com.example.grocemart.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.grocemart.R;
import com.example.grocemart.adapter.SerachAdapter;
import com.example.grocemart.modelclass.Serach_ModelClass;
import com.example.grocemart.url.APPURLS;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SerachPage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    SerachAdapter serachAdapter;
    EditText edit_Serach;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    String cityName, cityId, pincodeId, userId;


    ArrayList<Serach_ModelClass> serachProduct = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach_page);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        edit_Serach = findViewById(R.id.serach);
        recyclerView = findViewById(R.id.serachRecycler);

        SharedPreferences sp = getSharedPreferences("details",MODE_PRIVATE);
        cityName = sp.getString("City_Name",null);
        pincodeId = sp.getString("Pincode_id",null);
        cityId = sp.getString("City_id",null);

        bottomNavigationView.setSelectedItemId(R.id.search);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {

                switch(item.getItemId()){

                    case R.id.dashboard :
                        startActivity(new Intent(getApplicationContext(),UserDashboard.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.search:
                        return true;

                    case R.id.cart:
                        startActivity(new Intent(getApplicationContext(),CartPage.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });

        Log.d("item",serachProduct.toString());

        edit_Serach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                serachAdapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(SerachPage.this,MainActivity.class);
        startActivity(intent);
    }

    public void serachProduct(String keyword){

        ProgressDialog progressDialog = new ProgressDialog(SerachPage.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("View Address Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, APPURLS.serachProduct, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");
                    if(message.equals("true")){

                        String match_product = jsonObject.getString("match_product");
                        JSONArray jsonArray_matchproduct = new JSONArray(match_product);

                        for (int i=0;i<jsonArray_matchproduct.length();i++){

                            JSONObject jsonObject_matchproduct = jsonArray_matchproduct.getJSONObject(i);

                            String product_id = jsonObject_matchproduct.getString("product_id");
                            String product_name = jsonObject_matchproduct.getString("product_name");
                            String img = jsonObject_matchproduct.getString("img");
                            String product_description = jsonObject_matchproduct.getString("product_description");

                            Serach_ModelClass serach_modelClass = new Serach_ModelClass(
                                    product_id,product_name,"","",product_description,img
                            );

                            serachProduct.add(serach_modelClass);
                        }
                    }
                    gridLayoutManager = new GridLayoutManager(SerachPage.this,2,GridLayoutManager.VERTICAL,false);
                    serachAdapter = new SerachAdapter(SerachPage.this,serachProduct,cityId);
                    recyclerView.setLayoutManager (gridLayoutManager);
                    recyclerView.setHasFixedSize (true);
                    recyclerView.setAdapter (serachAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace ();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("keyWard",keyword);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(SerachPage.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }
}