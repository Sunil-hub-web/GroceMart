package com.example.grocemart;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocemart.adapter.GroceryAdapter;
import com.example.grocemart.adapter.RestaurantAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView text_AddressDetails,text_Address;
    RelativeLayout rel_SerachLocation;
    String address;
    private int FINE_LOCATION_ACCESS_REQUEST_CODE = 10001;
    LocationManager locationManager;
    FusedLocationProviderClient fusedLocationProviderClient;
    BottomNavigationView bottomNavigationView;
    Button btn_GoToShop2;
    GroceryAdapter groceryAdapter;
    RestaurantAdapter restaurantAdapter;
    RecyclerView recyclerView,recyclerView1;
    LinearLayoutManager linearLayoutManager,linearLayoutManager1;
    int Images[]={R.drawable.royal,R.drawable.biriyani,R.drawable.biriyani};
    String [] name ={"BB Royal Organic-Toor Dal","Aachi Biriyani Masala","Aachi Biriyani Masala"};

    int Images1[]={R.drawable.chicken,R.drawable.chicken,R.drawable.chicken};
    String [] name1 ={"Hyderabadi Chicken Biriyani","Hyderabadi Chicken Biriyani","Hyderabadi Chicken Biriyani"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //text_AddressDetails = findViewById(R.id.addressdetails);
        text_Address = findViewById(R.id.address);
        rel_SerachLocation = findViewById(R.id.serachLocation);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        btn_GoToShop2 = findViewById(R.id.gotoshop);


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

        Intent intent = getIntent();
        String item = intent.getStringExtra("item");

        if(item != null){

            text_Address.setText(item);

        }else{

            text_Address.setText("Bhubanesware");
        }

        recyclerView = findViewById(R.id.recycler);
        recyclerView1 = findViewById(R.id.recycler1);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false);
        groceryAdapter = new GroceryAdapter (MainActivity.this,Images,name);
        recyclerView.setLayoutManager (linearLayoutManager);
        recyclerView.setHasFixedSize (true);
        recyclerView.setAdapter (groceryAdapter);

        linearLayoutManager1 = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false);
        restaurantAdapter = new RestaurantAdapter (MainActivity.this,Images1,name1);
        recyclerView1.setLayoutManager (linearLayoutManager1);
        recyclerView1.setHasFixedSize (true);
        recyclerView1.setAdapter (restaurantAdapter);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {

                switch(item.getItemId()){

                    case R.id.dashboard :
                        startActivity(new Intent(getApplicationContext(),UserDashboard.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home :
                        return true;

                    case R.id.cart:
                        startActivity(new Intent(getApplicationContext(),CartPage.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(),SerachPage.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });

        rel_SerachLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,SelectLocation.class);
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
        bottomNavigationView.setSelectedItemId(R.id.home);
    }
}