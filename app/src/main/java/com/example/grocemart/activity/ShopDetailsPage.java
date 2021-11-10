package com.example.grocemart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.grocemart.R;
import com.example.grocemart.adapter.CategoryNameAdapter;
import com.example.grocemart.adapter.SubcategoryNameAdapter;
import com.example.grocemart.modelclass.AllProductVariation_ModelClass;
import com.example.grocemart.modelclass.AllProduct_ModelClass;
import com.example.grocemart.modelclass.AllShopDetails_ModelClass;
import com.example.grocemart.modelclass.CategoryName_ModelClass;
import com.example.grocemart.modelclass.ProductShop_ModelClass;
import com.example.grocemart.modelclass.SubCategoryName_ModelClass;
import com.example.grocemart.modelclass.SubCategory_ModelClass;
import com.example.grocemart.modelclass.Variation_ModelClass;
import com.example.grocemart.url.APPURLS;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopDetailsPage extends AppCompatActivity {

    RecyclerView recycler_CategoryName,recycler_SubCategoryName;
    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    CategoryNameAdapter categoryNameAdapter;
    SubcategoryNameAdapter subcategoryNameAdapter;
    TextView shopName, ShopAddress;

    ArrayList<AllShopDetails_ModelClass> allShop;
    ArrayList<CategoryName_ModelClass> categoryName;
    ArrayList<SubCategoryName_ModelClass> allsubCategory;
    ArrayList<AllProduct_ModelClass> allProductList;
    ArrayList<AllProductVariation_ModelClass> allProductVariationList;

    private static final String TAG = "ShopDetailsPage";
    String cityId, shop_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details_page);

        recycler_CategoryName = findViewById(R.id.recycler_CategoryName);
        recycler_SubCategoryName = findViewById(R.id.recycler_SubCategoryName);
        shopName = findViewById(R.id.shopName);
        ShopAddress = findViewById(R.id.ShopAddress);

        Intent intent = getIntent();

        cityId = intent.getStringExtra("cityId");
        shop_Id = intent.getStringExtra("shopId");

        getShopDetails(cityId, shop_Id);

    }

    public void getShopDetails(String city_Id, String shop_Id) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APPURLS.getSingleShopDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");

                    if (message.equals("true")) {

                        allShop = new ArrayList<>();

                        String shopId = jsonObject.getString("shop_id");
                        String shop_name = jsonObject.getString("shop_name");
                        String shop_img = jsonObject.getString("shop_img");
                        String shop_address = jsonObject.getString("shop_address");

                        shopName.setText(shop_name);
                        ShopAddress.setText(shop_address);

                        if (shop_address.equals("")) {

                            ShopAddress.setVisibility(View.GONE);
                        }

                        categoryName = new ArrayList<>();

                        allsubCategory = new ArrayList<>();

                        allProductList = new ArrayList<AllProduct_ModelClass>();

                        JSONArray jsonArray_AllShop = jsonObject.getJSONArray("All_singleshop_product");

                        for (int i = 0; i < jsonArray_AllShop.length(); i++) {

                            JSONObject jsonObject_AllShop = jsonArray_AllShop.getJSONObject(i);

                            String category_Name = jsonObject_AllShop.getString("Category_name");
                            CategoryName_ModelClass categoryName_modelClass = new CategoryName_ModelClass(category_Name);

                            //allProductList = new ArrayList<AllProduct_ModelClass>();

                            JSONArray jsonarray_subcategory = jsonObject_AllShop.getJSONArray("all_subCategory");


                            for (int j = 0; j < jsonarray_subcategory.length(); j++) {

                                JSONObject jsonObjectSubcategory = jsonarray_subcategory.getJSONObject(j);

                                String sub_categoryname = jsonObjectSubcategory.getString("sub_categoryname");

                                SubCategoryName_ModelClass subCategoryName_modelClass = new SubCategoryName_ModelClass(sub_categoryname);

                                Log.d("allsubCategory",allsubCategory.toString());

                                //allProductList = new ArrayList<AllProduct_ModelClass>();

                                JSONArray jsonArray_AllProduct = jsonObjectSubcategory.getJSONArray("all_product");

                                for (int k = 0; k<jsonArray_AllProduct.length(); k++){

                                    JSONObject jsonObject_AllProduct = jsonArray_AllProduct.getJSONObject(k);

                                    String productid = jsonObject_AllProduct.getString("product_id");
                                    String productname = jsonObject_AllProduct.getString("product_name");
                                    String productdesc = jsonObject_AllProduct.getString("product_description");
                                    String productimage = jsonObject_AllProduct.getString("product_img");
                                    String productshopId = jsonObject_AllProduct.getString("shop_id");
                                    String shopName = jsonObject_AllProduct.getString("shop_name");
                                    String shopAddress = jsonObject_AllProduct.getString("shop_address");

                                    allProductVariationList = new ArrayList<>();

                                    JSONArray jsonArray_variation = jsonObject_AllProduct.getJSONArray("All_variation");
                                    if (jsonArray_variation.length() == 0) {

                                    } else {

                                        for (int l = 0; l < jsonArray_variation.length(); l++) {

                                            JSONObject jsonObject_variation = jsonArray_variation.getJSONObject(l);

                                            String variationId = jsonObject_variation.getString("variation_id");
                                            String unit = jsonObject_variation.getString("unit");
                                            String mrpPrice = jsonObject_variation.getString("mrp_price");
                                            String salesPrice = jsonObject_variation.getString("sale_price");
                                            String discount = jsonObject_variation.getString("discount");

                                            AllProductVariation_ModelClass allProductVariation_modelClass = new AllProductVariation_ModelClass(
                                                    variationId, unit, mrpPrice, salesPrice, discount);

                                            allProductVariationList.add(allProductVariation_modelClass);

                                        }

                                        Log.d("variations",allProductVariationList.size()+"");

                                    }

                                        AllProduct_ModelClass allProduct_modelClass = new AllProduct_ModelClass(
                                                productid, productname, productdesc, productimage, productshopId, shopName,
                                                shopAddress, "", "", "", "","" ,allProductVariationList
                                        );

                                        allProductList.add(allProduct_modelClass);

                                        Log.d("allProductList",allProductList.toString());

                                }

                                allsubCategory.add(subCategoryName_modelClass);

                            }

                            categoryName.add(categoryName_modelClass);
                        }

                        AllShopDetails_ModelClass allShopDetails_modelClass = new AllShopDetails_ModelClass(
                                shopId, shop_name, shop_img, shop_address, categoryName
                        );

                        allShop.add(allShopDetails_modelClass);

                        linearLayoutManager = new LinearLayoutManager(ShopDetailsPage.this,LinearLayoutManager.VERTICAL, false);
                        subcategoryNameAdapter = new SubcategoryNameAdapter(ShopDetailsPage.this, allsubCategory,allProductList);
                        recycler_SubCategoryName.setLayoutManager(linearLayoutManager);
                        recycler_SubCategoryName.setHasFixedSize(true);
                        recycler_SubCategoryName.setAdapter(subcategoryNameAdapter);

                        gridLayoutManager = new GridLayoutManager(ShopDetailsPage.this, 2, GridLayoutManager.VERTICAL, false);
                        categoryNameAdapter = new CategoryNameAdapter(ShopDetailsPage.this, categoryName);
                        recycler_CategoryName.setLayoutManager(gridLayoutManager);
                        recycler_CategoryName.setHasFixedSize(true);
                        recycler_CategoryName.setAdapter(categoryNameAdapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();

                param.put("city_id", city_Id);
                param.put("shop_id", shop_Id);

                return param;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ShopDetailsPage.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }
}