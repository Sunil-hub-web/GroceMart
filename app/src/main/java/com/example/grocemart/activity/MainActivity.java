package com.example.grocemart.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
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
import com.example.grocemart.SharedPrefManager;
import com.example.grocemart.adapter.BannerAdapter;
import com.example.grocemart.adapter.CategoryAdapter;
import com.example.grocemart.adapter.GroceryAdapter;
import com.example.grocemart.adapter.MeatsAdapter;
import com.example.grocemart.adapter.RestaurantAdapter;
import com.example.grocemart.modelclass.Banner_ModelClass;
import com.example.grocemart.modelclass.Category_Modelcalss;
import com.example.grocemart.modelclass.Grocery_ModelClass;
import com.example.grocemart.modelclass.Meats_ModelClass;
import com.example.grocemart.modelclass.Restaurant_ModelClass;
import com.example.grocemart.url.APPURLS;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextView text_AddressDetails, text_Address;
    RelativeLayout rel_SerachLocation;
    String cityName, cityId, pincodeId, userId;
    private int FINE_LOCATION_ACCESS_REQUEST_CODE = 10001;
    LocationManager locationManager;
    FusedLocationProviderClient fusedLocationProviderClient;
    BottomNavigationView bottomNavigationView;
    Button btn_GoToShop2;

    BannerAdapter bannerAdapter;
    GroceryAdapter groceryAdapter;
    RestaurantAdapter restaurantAdapter;
    MeatsAdapter meatsAdapter;
    CategoryAdapter categoryAdapter;

    RecyclerView grocery_RecyclerView, restaurant_RecyclerView,
            banner_RecyclerView, meats_RecyclerView,category_RecyclerView;
    LinearLayoutManager grocery_LinearLayoutManager, restaurant_LinearLayoutManager,
            banner_LinearLayoutManager, meats_LinearLayoutManager, category_LinearLayoutManager;

    ArrayList<Banner_ModelClass> home_Banner = new ArrayList<>();
    ArrayList<Grocery_ModelClass> home_Grocery = new ArrayList<>();
    ArrayList<Restaurant_ModelClass> home_Restaurant = new ArrayList<>();
    ArrayList<Meats_ModelClass> home_Meats = new ArrayList<>();
    ArrayList<Category_Modelcalss> home_Category = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //text_AddressDetails = findViewById(R.id.addressdetails);
        text_Address = findViewById(R.id.address);
        rel_SerachLocation = findViewById(R.id.serachLocation);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        btn_GoToShop2 = findViewById(R.id.gotoshop);
        banner_RecyclerView = findViewById(R.id.bannerRecycler);
        grocery_RecyclerView = findViewById(R.id.groceryRecycler);
        restaurant_RecyclerView = findViewById(R.id.restaurantRecycler);
        meats_RecyclerView = findViewById(R.id.meatsRecycler);
        category_RecyclerView = findViewById(R.id.categoryRecycler);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

      /*  if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            //Write Function To enable gps
            enableUserLocation();
            locationPermission();

        } else {

            //GPS is already On then
            getLocation();

        }*/

        userId = SharedPrefManager.getInstance(MainActivity.this).getUser().getId();

        Intent intent = getIntent();
        cityName = intent.getStringExtra("item");
        cityId = intent.getStringExtra("City_id");
        pincodeId = intent.getStringExtra("Pincode_id");

        if (cityName != null) {

            text_Address.setText(cityName);

        } else {

            text_Address.setText("Bhubanesware");
        }

        homePageBanner(cityId, pincodeId, userId);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:
                        return true;

                    case R.id.cart:
                        startActivity(new Intent(getApplicationContext(), CartPage.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), SerachPage.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });

        rel_SerachLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SelectLocation.class);
                startActivity(intent);
            }
        });
    }


    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                //initialize location
                Location location = task.getResult();

                if (location != null) {

                    try {
                        //initialize geocoder
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        //initialize AddressList
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        //set address On Text View
                        //text_AddressDetails.setText(addresses.get(0).getAddressLine(0));
                        //text_Address.setText(addresses.get(0).getFeatureName());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }

    private void enableUserLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            //Ask for permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //We need to show user a dialog for displaying why the permission is needed and then ask for the permission...
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_LOCATION_ACCESS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //We have the permission
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                        (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            } else {
                //We do not have the permission..
            }
        }
    }

    public void locationPermission() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Enable Your GPS Location").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                getLocation();
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        bottomNavigationView.setSelectedItemId(R.id.home);

        Intent intent = new Intent(MainActivity.this, SelectLocation.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPrefManager.getInstance(MainActivity.this).isLoggedIn()) {
        } else {
            Intent intent = new Intent(MainActivity.this, SigninPage.class);
            startActivity(intent);
        }
    }

    public void homePageBanner(String cityId, String pincodeId, String userId) {

        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Retrive Data Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APPURLS.getHomeDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");
                    String banner = jsonObject.getString("All_banner");
                    String Grocery = jsonObject.getString("All_Grocery");
                    String Restaurant = jsonObject.getString("All_Restaurant");
                    String Meats = jsonObject.getString("All_Meats");
                    String category = jsonObject.getString("All_category");

                    if (message.equals("true")) {

                        //Rterive Banner For Home page

                        JSONArray jsonArray_Banner = new JSONArray(banner);

                        for (int i = 0; i < jsonArray_Banner.length(); i++) {

                            JSONObject jsonObject_banner = jsonArray_Banner.getJSONObject(i);

                            Banner_ModelClass banner_modelClass = new Banner_ModelClass(
                                    jsonObject_banner.getString("banner_id"),
                                    jsonObject_banner.getString("title"),
                                    jsonObject_banner.getString("img")
                            );

                            home_Banner.add(banner_modelClass);
                        }

                        banner_LinearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        bannerAdapter = new BannerAdapter(MainActivity.this, home_Banner);
                        banner_RecyclerView.setLayoutManager(banner_LinearLayoutManager);
                        banner_RecyclerView.setHasFixedSize(true);
                        banner_RecyclerView.setAdapter(bannerAdapter);

                       /* final int interval_time = 2000;
                        Handler handler = new Handler();
                        Runnable runnable = new Runnable() {
                            int count = 0;
                            @Override
                            public void run() {

                                if(count<home_Banner.size()){

                                    banner_RecyclerView.scrollToPosition(count++);
                                    handler.postDelayed(this,interval_time);

                                    if(count==home_Banner.size()){

                                        count=0;
                                    }
                                }
                            }
                        };
                        handler.postDelayed(runnable,interval_time);*/


                        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
                        linearSnapHelper.attachToRecyclerView(banner_RecyclerView);

                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {

                                if (banner_LinearLayoutManager.findLastCompletelyVisibleItemPosition() < (bannerAdapter.getItemCount() - 1)) {
                                    banner_LinearLayoutManager.smoothScrollToPosition(banner_RecyclerView,
                                            new RecyclerView.State(), banner_LinearLayoutManager.findLastVisibleItemPosition() + 1);

                                } else if (banner_LinearLayoutManager.findLastCompletelyVisibleItemPosition() < (bannerAdapter.getItemCount() + 1)) {
                                    banner_LinearLayoutManager.smoothScrollToPosition(banner_RecyclerView,
                                            new RecyclerView.State(), 0);
                                }

                            }
                        }, 0, 3000);

                        //Retrive Grocery Products In Homne Page

                        JSONArray jsonArray_Grocery = new JSONArray(Grocery);

                        for (int j = 0; j < jsonArray_Grocery.length(); j++) {

                            JSONObject grocery = jsonArray_Grocery.getJSONObject(j);

                            Grocery_ModelClass grocery_modelClass = new Grocery_ModelClass(
                                    grocery.getString("product_id"),
                                    grocery.getString("product_name"),
                                    grocery.getString("product_description"),
                                    grocery.getString("img")
                            );

                            home_Grocery.add(grocery_modelClass);
                        }


                        grocery_LinearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        groceryAdapter = new GroceryAdapter(MainActivity.this, home_Grocery);
                        grocery_RecyclerView.setLayoutManager(grocery_LinearLayoutManager);
                        grocery_RecyclerView.setHasFixedSize(true);
                        grocery_RecyclerView.setAdapter(groceryAdapter);

                        // Retrive Restaurant name in hone page;

                        JSONArray jsonArray_Restaurant = new JSONArray(Restaurant);

                        for (int k = 0; k < jsonArray_Restaurant.length(); k++) {

                            JSONObject jsonObject_Restaurant = jsonArray_Restaurant.getJSONObject(k);

                            Restaurant_ModelClass restaurant_modelClass = new Restaurant_ModelClass(
                                    jsonObject_Restaurant.getString("product_id"),
                                    jsonObject_Restaurant.getString("product_name"),
                                    jsonObject_Restaurant.getString("product_description"),
                                    jsonObject_Restaurant.getString("img")
                            );

                            home_Restaurant.add(restaurant_modelClass);
                        }

                        restaurant_LinearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        restaurantAdapter = new RestaurantAdapter(MainActivity.this, home_Restaurant);
                        restaurant_RecyclerView.setLayoutManager(restaurant_LinearLayoutManager);
                        restaurant_RecyclerView.setHasFixedSize(true);
                        restaurant_RecyclerView.setAdapter(restaurantAdapter);

                        //Retrive Meats Products In Homne Page

                        JSONArray jsonArray_Meats = new JSONArray(Meats);

                        for (int l = 0; l < jsonArray_Meats.length(); l++) {

                            JSONObject jsonObject_Meats = jsonArray_Meats.getJSONObject(l);

                            Meats_ModelClass meats_modelClass = new Meats_ModelClass(
                                    jsonObject_Meats.getString("product_id"),
                                    jsonObject_Meats.getString("product_name"),
                                    jsonObject_Meats.getString("product_description"),
                                    jsonObject_Meats.getString("img")
                            );

                            home_Meats.add(meats_modelClass);
                        }

                        meats_LinearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        meatsAdapter = new MeatsAdapter(MainActivity.this, home_Meats);
                        meats_RecyclerView.setLayoutManager(meats_LinearLayoutManager);
                        meats_RecyclerView.setHasFixedSize(true);
                        meats_RecyclerView.setAdapter(meatsAdapter);

                        // Retrive category in Home Page;


                        JSONArray jsonArray_category = new JSONArray(category);

                        for (int h = 0; h < jsonArray_category.length(); h++) {

                            JSONObject jsonObject_Category = jsonArray_category.getJSONObject(h);

                            Category_Modelcalss category_modelcalss = new Category_Modelcalss(

                                    jsonObject_Category.getString("category_id"),
                                    jsonObject_Category.getString("category_name"),
                                    jsonObject_Category.getString("img")
                            );

                            home_Category.add(category_modelcalss);

                        }

                        Log.d("array",home_Category.toString());

                        category_LinearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        categoryAdapter = new CategoryAdapter(MainActivity.this, home_Category);
                        category_RecyclerView.setLayoutManager(category_LinearLayoutManager);
                        category_RecyclerView.setHasFixedSize(true);
                        category_RecyclerView.setAdapter(categoryAdapter);

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

                params.put("city_id", cityId);
                params.put("pin_id", pincodeId);
                params.put("user_id", userId);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);

    }
}